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
 * class to set Attributes from PubChem Compound using the REST-style version of PUG (Power User Gateway) to get entry
 * entry format in json, parsed using jackson
 * 
 */
package parsebionet.enrichment.checker;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;


public class SetAttributeFromPCC extends SetAttributeFromDB{
//	
//	private HashMap<String, Object> entry;
//	private URL url;
//	private static final String PCCLINK = "https://pubchem.ncbi.nlm.nih.gov/rest/pug/compound/cid/";
//	private static final String PROPERTY = "/property/CanonicalSMILES,MolecularWeight,MolecularFormula,InChIKey,InChI";
//	private static final String FORMAT = "/JSON";
//	private HashSet<String> supportedFeatures = new HashSet<String>(){
//		{
//			this.add(INCHI);
//			this.add(SMILES);
//			this.add(INCHIKEY);
//			this.add(MASS);
//			this.add(FORMULA);
//		}
//	};
//	
//	public SetAttributeFromPCC(BioPhysicalEntity e, String dbId) {		
//		super(e,dbId);
//	}
//	
//	public Boolean getDBEntry(){	
//		
//		
//		try {
//			this.url = new URL(PCCLINK+this.dbId+PROPERTY+FORMAT);
//			ObjectMapper mapper = new ObjectMapper();
//			HashMap<String, HashMap<String, ArrayList<HashMap<String, Object>>>> res = new HashMap<String, HashMap<String,ArrayList<HashMap<String,Object>>>>();
//			res = mapper.readValue(url, new TypeReference<Map<String,Map<String,List<Map<String,Object>>>>>() {});
//			if(res.isEmpty()){
//				return false;
//			}
//			entry=res.get("PropertyTable").get("Properties").get(0);
//			if(entry==null){
//				return false;
//			}
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
//		
//	public Object getAttributeFromDB(String attribute){
//		Object value = null;
//
//		try{
//			if (attribute.equals(MASS)){
//				value = entry.get("MolecularWeight").toString();	
//			} else if(attribute.equals(FORMULA)){
//				value = entry.get("MolecularFormula");
//			} else if(attribute.equals(SMILES)){
//				value = entry.get("CanonicalSMILES");
//			} else if(attribute.equals(INCHI)){
//				value = entry.get("InChI");
//			} else if(attribute.equals(INCHIKEY)){
//				value = entry.get("InChIKey");
//			} else {
//				System.err.println("[Warning] attribute "+attribute+" not available on HMDB or not implemented yet...");
//				System.err.println("attributes allowed: "+this.supportedFeatures);
//				return null;
//			}
//			if(value==null){
//				System.err.println("[Warning] no "+attribute+" entry for "+dbId);
//			}
//		}catch (Exception exc){
//			exc.printStackTrace();
//		}
//		return value;
//	}
//	
//	public HashSet<String> getSupportedFeatures(){
//		return this.supportedFeatures;
//	}
//	
//	public static void main(String[] args){
//		BioPhysicalEntity e = new BioPhysicalEntity("M_test");
//		SetAttributeFromPCC cidSearch = new SetAttributeFromPCC(e, "2244");
//		cidSearch.setAttribute("formula");
//		cidSearch.setAttribute("inchi");
//		cidSearch.setAttribute("smiles");
//		cidSearch.setAttribute("mass");
//		System.out.println(e.getChemicalFormula());
//		System.out.println(e.getInchi());
//		System.out.println(e.getSmiles());
//		System.out.println(e.getMolecularWeight());
//		System.out.println(cidSearch.getAttributeFromDB(INCHIKEY));
//	}
}