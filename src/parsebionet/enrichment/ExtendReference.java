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
package parsebionet.enrichment;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import parsebionet.enrichment.checker.CheckReference;


/**
 * @author frainay
 * 16-04-2014
 * expand references to remote database using CDK, converting ids between several db
 *  
 */
@SuppressWarnings("unchecked")
public class ExtendReference {


	private HashMap<String, Set<Reference>> refs = null;

	public ExtendReference() {
		this.refs=new HashMap<String, Set<Reference>>();
	}


	public ExtendReference(HashMap<String,Set<Reference>> refs) {
		this.setRefs(refs);
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
			
			if(IdsConvertor.IDENTIFIER_TO_CDK.containsKey(dbIn)){
				String CdkDbIn=IdsConvertor.IDENTIFIER_TO_CDK.get(dbIn);				

				for(String CdkDbOut : IdsConvertor.CDK_TO_IDENTIFIER.keySet()){
					String dbOut=IdsConvertor.CDK_TO_IDENTIFIER.get(CdkDbOut);
//					
					if(!( dbIn.equalsIgnoreCase(dbOut) )){
//						
						IdsConvertor idc = new IdsConvertor(CdkDbIn,id,CdkDbOut);
						ArrayList<Object> res = (ArrayList<Object>)idc.get();
						if(res != null && !res.isEmpty()){
							int confidenceLevel = 4;
							
							for(Object resId:res){
								if(!this.hasRef(dbOut, (String)resId)){
									Reference newRef = new Reference( dbOut, (String)resId, confidenceLevel);
//									if( CheckReference.SUPPORTED_DB.contains(dbOut)){
//										CheckReference check = new CheckReference(newRef, (BioPhysicalEntity)e);
//										if(check.getFlag()){
//											newRef.setConfidenceLevel(check.getConfidenceLevel());
//											e.addRef(newRef);count++;
//										}
//									}else{
										this.addRef(newRef);
										count++;
//									}
								}	
							}
						}
					}
				}
				
				count+=this.addInchiRefs();
				count+=this.addInchiKeyRefs();
			}	
		}
		return count;
	}

	/**
	 * add cross references to DB listed in argument
	 */
	public int extendAll(String... targetDbList){
		int count=0;
		ArrayList<Reference> refs = getOrderedRefsList();

		for(Reference ref : refs){
			String id=ref.getId();
			String dbIn=ref.getDbName();
			
			if(IdsConvertor.IDENTIFIER_TO_CDK.containsKey(dbIn)){
				String CdkDbIn=IdsConvertor.IDENTIFIER_TO_CDK.get(dbIn);				

				for(String dbOut : targetDbList){
					
					if(dbOut.equalsIgnoreCase("inchi")){
						count+=this.addInchiRefs();
					}else if(dbOut.equalsIgnoreCase("inchikey")){
						count+=this.addInchiKeyRefs();							
					}else if(!(dbIn.equals(dbOut))){
						
						String CdkDbOut=IdsConvertor.IDENTIFIER_TO_CDK.get(dbOut);
						IdsConvertor idc = new IdsConvertor(CdkDbIn,id,CdkDbOut);
						ArrayList<Object> res = (ArrayList<Object>)idc.get();
						if(res != null && !res.isEmpty()){
							int confidenceLevel = 4;
							
							for(Object resId:res){
								if(!this.hasRef(dbOut, (String)resId)){
									Reference newRef = new Reference(dbOut, (String)resId, confidenceLevel);
//									if(e.getClass().equals(BioPhysicalEntity.class) && CheckRef.SUPPORTED_DB.contains(dbOut)){
//										CheckRef check = new CheckRef(newRef, (BioPhysicalEntity)e);
//										if(check.getFlag()){
//											ref.setConfidenceLevel(check.getConfidenceLevel());
//											e.addRef(newRef);count++;
//										}
//									}else{
										this.addRef(newRef);
										count++;
//									}
								}	
							}
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
//	public int extendAll(String targetDb){
//		int count=0;
////		if(IdsConvertor.IDENTIFIER_TO_CDK.containsKey(targetDb)){
////			String dbOut = targetDb;
////			String CdkDbOut=IdsConvertor.IDENTIFIER_TO_CDK.get(dbOut);
////
////			ArrayList<Reference> refs = getOrderedRefsList();
////
////			for(Reference ref : refs){
////				String dbIn=ref.getDbName();
////				String id=ref.getId();
////				//				System.out.println(id+" ("+dbIn+")");
////				if(IdsConvertor.IDENTIFIER_TO_CDK.containsKey(dbIn)){
////					String CdkDbIn=IdsConvertor.IDENTIFIER_TO_CDK.get(dbIn);				
////
////
////					IdsConvertor idc = new IdsConvertor(CdkDbIn,id,CdkDbOut);
////					ArrayList<Object> res = (ArrayList<Object>)idc.get();
////					if(res != null && !res.isEmpty()){
////						int confidenceLevel = 4;
////						//						if(ref.getConfidenceLevel()==1){
////						//							confidenceLevel = 2;
////						//						}else{
////						//							confidenceLevel = 3;
////						//						}
////						for(Object resId:res){
////							if(!e.hasRef(dbOut, (String)resId)){
////								Reference newRef = new Reference("Inferred from id", dbOut, (String)resId, confidenceLevel);
////								if(e.getClass().equals(BioPhysicalEntity.class) && CheckRef.SUPPORTED_DB.contains(dbOut)){
////									CheckRef check = new CheckRef(newRef, (BioPhysicalEntity)e);
////									if(check.getFlag()){
////										ref.setConfidenceLevel(check.getConfidenceLevel());
////										this.addRef(newRef);count++;
////									}
////								}else{
////									e.addRef(newRef);count++;
////								}
////							}	
////						}
////					}
////				}
////			}
////		}
//		return count;
//	}

	/**
	 * add cross references from existing ones and newly added ones, until all supported DB is referenced or no more links can be added
	 * @return 
	 */
	public int extendUntilComplete(){
		int count=0;
		Stack<Reference> stack = new Stack<Reference>();
		ArrayList<Reference> refs = getOrderedRefsList();
		for(Reference ref : refs){
			stack.push(ref);
		}

		boolean full = false;
//		while (!stack.isEmpty() && !full){
//			full=true;
//			for(String db : IdsConvertor.CDK_TO_IDENTIFIER.keySet()){
//				if(!this.getRefs().containsKey(db)){
//					full = false;
//				}
//			}
//
//			Reference ref=stack.pop();
//			String id=ref.getId();
//			String dbIn=ref.getDbName();
//
//			if(IdsConvertor.IDENTIFIER_TO_CDK.containsKey(dbIn)){
//				String CdkDbIn=IdsConvertor.IDENTIFIER_TO_CDK.get(dbIn);				
//
//				for(String CdkDbOut : IdsConvertor.CDK_TO_IDENTIFIER.keySet()){
//					String dbOut=IdsConvertor.CDK_TO_IDENTIFIER.get(CdkDbOut);
//					if(!(dbIn.equals(dbOut) || (onlyEmpty && this.getRefs().keySet().contains(dbOut)))){
//
//						IdsConvertor idc = new IdsConvertor(CdkDbIn,id,CdkDbOut);
//						ArrayList<Object> res = (ArrayList<Object>)idc.get();
//						if(res != null && !res.isEmpty()){
//							int confidenceLevel = 4;
//							//							if(ref.getConfidenceLevel()==1){
//							//								confidenceLevel = 2;
//							//							}else{
//							//								confidenceLevel = 3;
//							//							}
//							for(Object resId:res){
//								if(!this.hasRef(dbOut,(String)resId)){
//									Reference newRef = new Reference("Inferred from id", dbOut, (String)resId, confidenceLevel);
//									boolean flag=true;
//									if(e.getClass().equals(BioPhysicalEntity.class) && CheckRef.SUPPORTED_DB.contains(dbOut)){
//										CheckRef check = new CheckRef(newRef, (BioPhysicalEntity)e);
//										if(check.getFlag()){
//											newRef.setConfidenceLevel(check.getConfidenceLevel());
//											e.addRef(newRef);count++;
//											stack.push(newRef);
//										}
//									}else{
//										e.addRef(newRef);count++;
//										stack.push(newRef);
//									}
//
//								}	
//							}
//						}
//					}
//				}
//
//			}	
//		}
		return count;
	}

	/**
	 * create cross link from name
	 */
	public int extendFromName(String name){
		int count=0;
//		for(String CdkDbOut : IdsConvertor.CDK_TO_IDENTIFIER.keySet()){
//			String dbOut=IdsConvertor.CDK_TO_IDENTIFIER.get(CdkDbOut);
//			if(!(onlyEmpty && e.getRefs().keySet().contains(dbOut))){
//				//				System.out.println("\t"+dbOut);
//				//				String CdkDbOut=IdsConvertor.IDENTIFIER_TO_CDK.get(dbOut);
//				IdsConvertor idc = new IdsConvertor("Chemical Name",name,CdkDbOut);
//				ArrayList<Object> res = (ArrayList<Object>)idc.get();
//				if(res != null && !res.isEmpty()){
//					for(Object resId:res){
//						if(!e.hasRef(dbOut, (String)resId)){
//							Reference ref = new Reference( dbOut,(String)resId, 4);
//							if( CheckReference.SUPPORTED_DB.contains(dbOut)){
//								CheckReference check = new CheckReference(ref, (BioPhysicalEntity)e);
//								if(check.getFlag()){
//									ref.setConfidenceLevel(check.getConfidenceLevel());
//									e.addRef(ref);count++;
//								}
//							}else{
//								e.addRef(ref);count++;
//							}
//						}	
//					}
//				}
//			}
//		}
		return count;
	}

	
	/**
	 * Get inchi as indentifier.org ref
	 */
	
	public int addInchiRefs(){
		int added=0;
		ArrayList<Reference> refs = getOrderedRefsList();
		int i=0;
		while(this.getRefs().get("inchi")==null && i!=refs.size()){
			Reference ref = refs.get(i);
			i++;
			IdsConvertor idc = new IdsConvertor(IdsConvertor.IDENTIFIER_TO_CDK.get(ref.getDbName()),ref.getId(),"InChI Code");
			ArrayList<Object> res = (ArrayList<Object>)idc.get();
			if(res != null && !res.isEmpty()){
				
				Reference InChIref = new Reference( "inchi",(String)res.get(0), ref.getConfidenceLevel());
				this.addRef(InChIref); 
				added=1;
			}
		}
		return added;
	}

	/**
	 * Get inchiKey as indentifier.org ref
	 */
	public int addInchiKeyRefs(){
		int added = 0;
		ArrayList<Reference> refs = getOrderedRefsList();

		int i=0;
		while(this.getRefs().get("inchikey")==null && i!=refs.size()){
			Reference ref = refs.get(i);
			i++;
			IdsConvertor idc = new IdsConvertor(IdsConvertor.IDENTIFIER_TO_CDK.get(ref.getDbName()), ref.getId(),"InChIKey");
			ArrayList<Object> res = (ArrayList<Object>)idc.get();
			if(res != null && !res.isEmpty()){
				Reference InChIKeyref = new Reference("inchikey",(String)res.get(0),ref.getConfidenceLevel());
				this.addRef(InChIKeyref); 
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
			if(IdsConvertor.IDENTIFIER_TO_CDK.containsKey(dbName)){
				for(Reference ref : this.getRefs().get(dbName)){
					refs.add(ref);
				}
			}				
		}
		Collections.sort(refs);
		return refs;
	}

	public HashMap<String,Set<Reference>> getRefs(){
		return refs;
	}

	public void setRefs(HashMap<String, Set<Reference>> refs) {
		this.refs = refs;
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

		Reference kegg = new Reference( "kegg.compound", "C14748", 1);
		map.put("kegg.compound",new HashSet<Reference>());
		map.get("kegg.compound").add(kegg);
		System.out.println("extending Xrefs...");
		ExtendReference exRef = new ExtendReference(map);

//		System.out.println(exRef.extendAll()+" Xrefs added");
//		System.out.println(exRef.extendAll("chebi")+" Xrefs added");

		String[] s =new String[]{"chemspider","chebi","inchi"};
		System.out.println(exRef.extendAll(s)+" Xrefs added");

//		System.out.println(exRef.extendFromName()+" Xrefs added from name");

//		System.out.println(exRef.extendFromName("Hydroxyeicosatetraenoic acid")+" Xrefs added from name");
//		System.out.println(exRef.extendUntilComplete()+" Xrefs added");

//		System.out.println("inchiKey added: "+exRef.addInchiKeyRefs());
//		System.out.println("inchiKey added: "+exRef.addInchiRefs());

		for(String db:exRef.getRefs().keySet()){
			System.out.println(db);
			for(Reference ref:exRef.getRefs().get(db)){
				System.out.println("\t"+ref.getId()+"\t"+ref.getConfidenceLevel());
			}
		}
	}
}
