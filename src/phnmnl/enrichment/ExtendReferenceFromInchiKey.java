/*******************************************************************************
 * Copyright INRA
 * 
 * 
 * This software is governed by the CeCILL license under French law and
 * abiding by the rules of distribution of free software.  You can  use,
 * modify and/ or redistribute the software under the terms of the CeCILL
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info".
 * 
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability.
 *  In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or
 * data to be ensured and,  more generally, to use and operate it in the
 * same conditions as regards security.
 *  The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL license and that you accept its terms.
 ******************************************************************************/
/**
 * class to expand references to remote database using CDK, converting ids between several database. 
 * (Steinbeck, C., Han, Y., Kuhn, S., Horlacher, O., Luttmann, E., & Willighagen, E. (2003). 
 * The Chemistry Development Kit (CDK): an open-source Java library for Chemo- and Bioinformatics. 
 * Journal of Chemical Information and Computer Sciences, 43(2), 493–500. doi:10.1021/ci025584y)
 * 
 */
package phnmnl.enrichment;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;



/**
 * @author bmerlet adapted from frainay
 * 16-04-2014
 * expand references to remote database using CDK, converting ids between several db
 *  
 */
public class ExtendReferenceFromInchiKey {

	private boolean check=true;
	private HashMap<String, Set<Reference>> refs = null;
	private Reference inchiKeyRef;


	public ExtendReferenceFromInchiKey() {
		this.refs=new HashMap<String, Set<Reference>>();
	}

	public ExtendReferenceFromInchiKey(boolean c) {
		this.refs=new HashMap<String, Set<Reference>>();
		this.check=c;
	}

	public ExtendReferenceFromInchiKey(HashMap<String,Set<Reference>> refs) {
		this.setRefs(refs);
	}

	public ExtendReferenceFromInchiKey(HashMap<String,Set<Reference>> refs, boolean c) {
		this.setRefs(refs);
		this.check=c;
	}

	/**
	 * Retrieves the inchikey ref from existing ref, then use this inchikey to query cts webservice
	 * @return
	 */
	public int findAndExtendFromInChiKey(){
		int added = 0;

		if (this.getInchiKeyRef()==null){
			added+=this.addInchiKeyRefs();
		}

		Reference ref=this.getInchiKeyRef();

		if(ref!=null){
			IdsConvertor2 idc = new IdsConvertor2(ref.getDbName(), ref.getId(),null);
			for(JsonElement j:idc.get()){
				String outdb=((JsonObject)j).get("dbname").getAsString();
				if( ! this.hasRef(outdb, ((JsonObject)j).get("id").getAsString())){

					this.addRef(outdb, ((JsonObject)j).get("id").getAsString(),	1);
					added++;
				}else{
					getSingleRef(outdb, ((JsonObject)j).get("id").getAsString()).setConfidenceLevel(1);
				}
			}
		}else{
			this.extendAll();
		}
		return added;
	}

	/**
	 * Retrieves the inchikey ref from existing ref, then use this inchikey to query cts webservice 
	 * and only retrieve the targeted databases
	 * @return
	 */
	public int findAndExtendFromInChiKey(String... targetDbList){
		int added = 0;

		if (this.getInchiKeyRef()==null){
			added+=this.addInchiKeyRefs();
		}
		HashSet<String> dbSet=new HashSet<String>(Arrays.asList(targetDbList));

		Reference ref;

		if((ref=this.getInchiKeyRef())!=null){
			IdsConvertor2 idc = new IdsConvertor2(ref.getDbName(), ref.getId(),null);
			for(JsonElement j:idc.get()){
				String outdb=((JsonObject)j).get("dbname").getAsString();
				if(dbSet.contains(outdb)
						&& ! this.hasRef(outdb, ((JsonObject)j).get("id").getAsString())){

					this.addRef(outdb, ((JsonObject)j).get("id").getAsString(),	1);
					added++;
				}else if(dbSet.contains(outdb)
						&& this.hasRef(outdb, ((JsonObject)j).get("id").getAsString())){
					getSingleRef(outdb, ((JsonObject)j).get("id").getAsString()).setConfidenceLevel(1);
				}
			}
		}else{
			this.extend(targetDbList);
		}
		return added;
	}

	/**
	 * add cross references from existing ones
	 */
	public int extendAll(){
		int count=0;
		ArrayList<Reference> refs = getOrderedRefsList();

		for(Reference ref : refs){
			String id=ref.getId();
			String dbIn=ref.getDbName();


			for(String CdkDbOut : IdsConvertor2.USUAL_DB_NAME){

				if(!( dbIn.equals(CdkDbOut) )){
					IdsConvertor2 idc = new IdsConvertor2(dbIn,id,CdkDbOut);
					for(JsonElement j:idc.get()){
						int confidenceLevel = 4;
						if(! this.hasRef(CdkDbOut, ((JsonObject)j).get("id").getAsString())){
							this.addRef(CdkDbOut, ((JsonObject)j).get("id").getAsString(),	confidenceLevel);
							count++;
						}
					}
				}
			}	
		}

		return count;
	}

	/**
	 * add cross references to DB listed in argument
	 */
	public int extend(String... targetDbList){
		int count=0;
		ArrayList<Reference> refs = getOrderedRefsList();

		for(Reference ref : refs){
			String id=ref.getId();
			String dbIn=ref.getDbName();



			for(String dbOut : targetDbList){

				if(!(dbIn.equals(dbOut)) && IdsConvertor2.USUAL_DB_NAME.contains(dbOut)){

					IdsConvertor2 idc = new IdsConvertor2(dbIn,id,dbOut);
					for(JsonElement j:idc.get()){
						int confidenceLevel = 4;

						if(! this.hasRef(dbOut, ((JsonObject)j).get("id").getAsString())){
							this.addRef(dbOut, ((JsonObject)j).get("id").getAsString(),	confidenceLevel);
							count++;
						}
					}
				}
			}	
		}
		return count;

	}
	

	/**
	 * Get inchiKey as indentifier.org ref
	 */
	public int addInchiKeyRefs(){
		int added = 0;
		ArrayList<Reference> refs = getOrderedRefsList();

		int i=0;
		while(this.getRefs().get("InChIKey")==null && i!=refs.size()){
			Reference ref = refs.get(i);
			i++;
			IdsConvertor2 idc = new IdsConvertor2(ref.getDbName(), ref.getId(),"InChIKey");
			JsonArray result = idc.get();

			if(result != null && result.size()!=0){
				JsonObject j=result.get(0).getAsJsonObject();

				Reference InChIKeyref = new Reference("InChIKey",j.get("id").getAsString(),ref.getConfidenceLevel());
				this.addRef(InChIKeyref); 
				this.setInchiKeyRef(InChIKeyref);

				added=1;
			}
		}

		return added;
	}

	/**
	 * return list of references in order of confidence level
	 */
	public ArrayList<Reference> getOrderedRefsList(){

		ArrayList<Reference> refs = new ArrayList<Reference>();
		for(String dbName : this.getRefs().keySet()){
			for(Reference ref : this.getRefs().get(dbName)){
				refs.add(ref);
			}
		}
		Collections.sort(refs);
		return refs;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public HashMap<String,Set<Reference>> getRefs(){
		return refs;
	}

	public void setRefs(HashMap<String, Set<Reference>> refs) {
		this.refs = refs;
	}

	/**
	 * return the Inchikey reference used to retrieve ids from cts webservice
	 * @return
	 */
	public Reference getInchiKeyRef() {
		if(this.inchiKeyRef!=null){
			return this.inchiKeyRef;
		}else if(this.refs==null || this.refs.isEmpty() || !this.refs.containsKey("InChIKey")){
			return null;
		}else if (this.refs.get("InChIKey").size()!=1){
			return null;
		}else{
			for(Reference r:this.refs.get("InChIKey")){
				this.setInchiKeyRef(r);
				return r;
			}
		}
		return null;

	}

	public void setInchiKeyRef(Reference inchikeyRef) {
		this.inchiKeyRef = inchikeyRef;
	}

	public void setInchiKeyRef(String inchikeyString) {
		this.inchiKeyRef = new Reference("InChIKey", inchikeyString, 1);
	}

	public void addRef(String dbName, String dbId, int confidenceLevel){

		Reference ref = new Reference(dbName, dbId, confidenceLevel);
		if (this.refs.containsKey(dbName)){
			refs.get(dbName).add(ref);
		} else {
			Set<Reference> refList = new HashSet<Reference>();
			refList.add(ref);
			this.refs.put(dbName, refList);
		}
	}


	/**
	 * 
	 * @param ref
	 */
	public void addRef(Reference ref){
		String dbName = ref.getDbName();
		String dbId = ref.getId();
		if(!this.hasRef(dbName, dbId)){
			if (this.refs.containsKey(dbName)){
				refs.get(dbName).add(ref);
			} else {
				Set<Reference> refList = new HashSet<Reference>();
				refList.add(ref);
				this.refs.put(dbName, refList);
			}
		}
	}


	/**
	 * Retrieves the {@link Reference} object corresponding to the parameters
	 * @param dbName
	 * @param dbId
	 * @return
	 */
	public Reference getSingleRef(String dbName, String dbId) {
		if(this.refs==null || !this.refs.containsKey(dbName)){
			return null;
		}
		for(Reference ref:this.refs.get(dbName)){
			if(ref.getId().equals(dbId)){
				return ref;
			}
		}
		return null;
	}



	/**
	 * 
	 * @param dbName
	 * @param dbId
	 * @return
	 */
	public boolean hasRef(String dbName, String dbId) {
		if(this.refs==null || !this.refs.containsKey(dbName)){
			return false;
		}
		for(Reference ref:this.refs.get(dbName)){
			if(ref.getId().equals(dbId)){
				return true;
			}
		}
		return false;
	}

	public boolean hasRef(Reference unkRef){

		if(this.refs==null || !this.refs.containsKey(unkRef.dbName)){
			return false;
		}
		for(Reference ref:this.refs.get(unkRef.dbName)){
			if(ref.getId().equals(unkRef.id)){
				return true;
			}
		}
		return false;
	}


	public static void main(String[] args){
		HashMap<String, Set<Reference>> map = new HashMap<String, Set<Reference>>();

		Reference kegg = new Reference( "KEGG", "C14748", 1);
		map.put("KEGG",new HashSet<Reference>());
		map.get("KEGG").add(kegg);

		long startTime = System.nanoTime();
		System.out.println("extending Xrefs...");
		ExtendReferenceFromInchiKey exRef = new ExtendReferenceFromInchiKey(map);


		System.out.println(exRef.findAndExtendFromInChiKey()+" Xrefs added");
		//				System.out.println(exRef.extendAll()+" Xrefs added");
		//		System.out.println(exRef.extendAll("chebi")+" Xrefs added");

		//		String[] s =new String[]{"chemspider","chebi","inchi"};
		//		System.out.println(exRef.extendAll(s)+" Xrefs added");

		//		System.out.println(exRef.extendFromName()+" Xrefs added from name");

		//		System.out.println(exRef.extendFromName("Hydroxyeicosatetraenoic acid")+" Xrefs added from name");
		//		System.out.println(exRef.extendUntilComplete()+" Xrefs added");

		//		System.out.println("inchiKey added: "+exRef.addInchiKeyRefs());
		//		System.out.println("inchiKey added: "+exRef.addInchiRefs());

		long elapsedTime = System.nanoTime() - startTime;

		if (elapsedTime/1000000000==0){
			System.out.println("Time to retrieve references: "+ elapsedTime/1000000+ " milliseconds");
		}
		else{
			System.out.println("Time to retrieve references: "+ elapsedTime/1000000000+ " seconds");
		}


		for(String db:exRef.getRefs().keySet()){
			System.out.println(db);
			for(Reference ref:exRef.getRefs().get(db)){
				System.out.println("\t"+ref.getId()+"\t"+ref.getConfidenceLevel());
			}
		}
	}
}
