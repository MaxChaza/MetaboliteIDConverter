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
 * class to set Attributes from HMDB using HMDB id
 * 
 * 
 */
package parsebionet.enrichment.checker;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



/**
 * @author frainay
 * 14-01-2014
 *
 */
public class SetAttributeFromHMDB extends SetAttributeFromDB{
//	
//	public Element e;
//	public String url;
//	private HashSet<String> supportedFeatures = new HashSet<String>(){
//		{
//			this.add(INCHI);
//			this.add(SMILES);
//			this.add(INCHIKEY);
//			this.add(NAME);
//			this.add(FORMULA);
//			this.add(SYNONYMS);
//			this.add(CAS);
//		}
//	};
//	
//	/**
//	 * 
//	 * @param colId : number of the id column
//	 */
//	public SetAttributeFromHMDB(BioPhysicalEntity e, String dbId) {		
//		super(e,dbId);
//	}
//	
//	public Boolean getDBEntry(){	
//		
//		this.url = "http://www.hmdb.ca/metabolites/"+this.dbId+".xml";
//		try {
//			Document hmdbXml = XMLUtils.open(url);
//			NodeList metabolite = hmdbXml.getElementsByTagName("metabolite");
//
//	        if (metabolite == null){
//	        	System.out.println("Incorrect Format: Can't find node "+this.dbId);
//	        	return false;
//	        }
//	        if (metabolite.getLength() > 1){
//	        	System.out.println("Incorrect Format: More than one node at "+url);
//	        	return false;
//	        }
//	        e = ((Element)metabolite.item(0));							        
//	        return true;
//		} catch (IOException e) {
//			e.printStackTrace();
//			return false;
//		} catch (SAXException e) {
//			e.printStackTrace();
//			return false;
//		}  catch (Exception exc4){
//			exc4.printStackTrace();
//			return false;
//		}
//	}
//		
//	public Object getAttributeFromDB(String attribute){
//		Object value = null;
//		Node node;
//		try{
//			if(attribute.equals(INCHI)){
//				node = e.getElementsByTagName("inchi").item(0);
//				value = node.getTextContent();
//			} else if (attribute.equals(SMILES)){
//				node = e.getElementsByTagName("smiles").item(0);
//				value = node.getTextContent();
//			} else if (attribute.equals(CAS)){
//				node = e.getElementsByTagName("cas_registry_number").item(0);
//				value = node.getTextContent();
//			} else if(attribute.equals(INCHIKEY)){
//				node = e.getElementsByTagName("inchikey").item(0);
//				value = node.getTextContent();
//			} else if(attribute.equals(FORMULA)){
//				node = e.getElementsByTagName("chemical_formula").item(0);
//				value = node.getTextContent();
//			} else if(attribute.equals(NAME)){
//				node = e.getElementsByTagName("name").item(0);
//				value = node.getTextContent();
//			} else if(attribute.equals(SYNONYMS)){
//				NodeList nodes = e.getElementsByTagName("synonyms");
//				ArrayList<String> synonyms = new ArrayList<String>();
//				for(int i=0; i<nodes.getLength();i++){
//					synonyms.add(nodes.item(i).getTextContent());
//				}
//				value = synonyms;
//			} else if(attribute.equals("OMIM")){
//				NodeList diseases = e.getElementsByTagName("disease");
//				ArrayList<String> omimIds = new ArrayList<String>();
//				for(int i=0; i<diseases.getLength();i++){
//					Element disease = ((Element)diseases.item(i));
//					Node omimNode = disease.getElementsByTagName("omim_id").item(0);
//					String id = omimNode.getTextContent();
//					if(!(id==null || id.isEmpty() || id.equals(""))){
//						omimIds.add(id);
//					}
//				}
//				value = omimIds;
//			} else {
//				System.err.println("[Warning] attribute "+attribute+" not available on HMDB or not implemented yet...");
//				System.err.println("allowed attributes : "+this.supportedFeatures);
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
//		SetAttributeFromHMDB hmdbSearch = new SetAttributeFromHMDB(e, "HMDB00122");
//		hmdbSearch.setAttribute("inchi");
//		hmdbSearch.setAttribute("smiles");
//		hmdbSearch.setAttribute("cas");
//		hmdbSearch.setAttribute("formula");
//		hmdbSearch.setAttribute("name");
//		System.out.println(e.getInchi());
//		System.out.println(e.getSmiles());
//		System.out.println(e.getCaas());
//		System.out.println(e.getChemicalFormula());
//		System.out.println(e.getName());
//		System.out.println(hmdbSearch.getAttributeFromDB("inchiKey"));
//	}
}