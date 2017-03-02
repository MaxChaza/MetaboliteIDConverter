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
 * class to set Attributes from KEGG using togows to get entry in json format and jackson to parse json
 * 
 * 
 */
package parsebionet.enrichment.checker;


import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;


@SuppressWarnings("serial")
public class SetAttributeFromKEGG extends SetAttributeFromDB{
//	
//	private HashMap<String, Object> entry;
//	private URL url;
//	private static final String KEGGLINK = "http://togows.dbcls.jp/entry/kegg-compound/";
//	private HashSet<String> supportedFeatures = new HashSet<String>(){
//		{
//			this.add(MASS);
//			this.add(NAME);
//			this.add(FORMULA);
//			this.add(SYNONYMS);
//		}
//	};
//	
//	public SetAttributeFromKEGG(BioPhysicalEntity e, String dbId) {		
//		super(e,dbId);
//	}
//	
//	public Boolean getDBEntry(){	
//		
//		
//		try {
//			this.url = new URL(KEGGLINK+this.dbId+".json");
//			ObjectMapper mapper = new ObjectMapper();
//			ArrayList<HashMap<String, Object>> res = new ArrayList<HashMap<String, Object>>();
//			res = mapper.readValue(url, new TypeReference<List<Map<String, Object>>>() {});
//			if(res.isEmpty()){
//				return false;
//			}
//			entry=res.get(0);
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
//			if (attribute.equals(SYNONYMS)){
//				value = entry.get("names");	
//			} else if(attribute.equals(NAME)){
//				value = entry.get("name").toString();
//			} else if(attribute.equals(FORMULA)){
//				value = entry.get("formula").toString();
//			} else if(attribute.equals(MASS)){
//				//value = entry.get("exact_mass");
//				value = entry.get("mol_weight").toString();
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
//		SetAttributeFromKEGG keggSearch = new SetAttributeFromKEGG(e, "C00031");
//		keggSearch.setAttribute("synonyms");
//		keggSearch.setAttribute("formula");
//		keggSearch.setAttribute("name");
//		keggSearch.setAttribute("mass");
//		System.out.println(e.getSynonyms());
//		System.out.println(e.getChemicalFormula());
//		System.out.println(e.getName());
//		System.out.println(e.getMolecularWeight());
//	}
}
