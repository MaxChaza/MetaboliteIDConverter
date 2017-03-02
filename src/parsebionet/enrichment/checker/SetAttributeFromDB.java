/*******************************************************************************
 * Copyright INRA
 * 
 *  Contact: ludovic.cottret@toulouse.inra.fr
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
 * Abstract class to set Attributes from a remote DB
 * 
 * 14-01-2014
 */
package parsebionet.enrichment.checker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

//import parsebionet.enrichment.Reference;



/**
 * @author frainay
 * 14-01-2014
 * abstract class to set attributes from DB
 */
public abstract class SetAttributeFromDB {
//	public HashMap<String, String> e;
//	public String dbId;
//	public Boolean flag = true;
//	public int confidenceLevel;
//	
//	public static final String INCHI = "inchi";
//	public static final String SMILES = "smiles";
//	public static final String FORMULA = "formula";
//	public static final String MASS = "mass";
//	public static final String SYNONYMS = "synonyms";
//	public static final String CHARGE = "charge";
//	public static final String CAS = "cas";
//	public static final String NAME = "name";
//	public static final String INCHIKEY = "inchiKey";
//	/**
//	 * @param HashMap<String, String> e, the compound to modify
//	 * @param String dbId, identifier corresponding to e in the DB
//	 */
//	public SetAttributeFromDB(HashMap<String, String> e, String dbId) {		
//		this.setMap(e);
//		this.setDbId(dbId);
//		flag=getDBEntry();
//		confidenceLevel = 3;
//	}
//	
//	public SetAttributeFromDB(HashMap<String, String> e, String dbId, int confidenceLevel) {		
//		this.setMap(e);
//		this.setDbId(dbId);
//		this.flag=getDBEntry();
//		this.confidenceLevel=confidenceLevel;
//	}
//	
//	public SetAttributeFromDB(HashMap<String, String> e, Reference ref){
//		this.setMap(e);
//		this.setDbId(ref.getId());
//		this.flag=getDBEntry();
//		this.confidenceLevel=ref.getConfidenceLevel();
//	}
//	
//	/**
//	 * get the corresponding compound in the DB
//	 * @return boolean
//	 */
//	public abstract Boolean getDBEntry();
//		
//	public abstract Object getAttributeFromDB(String attribute);
//	
//	/**
//	 * Modifying e
//	 * @param attribute, attribute to modify.
//	 */
//	public void setAttribute(String attribute) {
//		if (!flag){
//			System.out.println("Unable to access entry");
//			return;
//		}
//		if(attribute.equalsIgnoreCase(INCHI)){
//			String value = (String) getAttributeFromDB(INCHI);
//			if(value != null){
//				e.setInchi(value);
//				if(!e.hasRef("inchi", value)){
//					e.addRef(new BioRef("Inferred from id", "inchi", value,confidenceLevel));
//				}
//			}
//		} else if (attribute.equalsIgnoreCase(SMILES)){
//			String value = (String) getAttributeFromDB(SMILES);
//			if(value != null){
//				e.setSmiles(value);				
//			}
//		} else if (attribute.equalsIgnoreCase(FORMULA)){
//			String value = (String) getAttributeFromDB(FORMULA);
//			if(value != null){
//				e.setChemicalFormula(value);
//			}
//		} else if (attribute.equalsIgnoreCase(MASS)){
//			String value = (String) getAttributeFromDB(MASS);
//			if(value != null){
//				e.setMolecularWeight(value);
//			}
//		} else if (attribute.equalsIgnoreCase(SYNONYMS)){
//			ArrayList<String> value = (ArrayList<String>) getAttributeFromDB(SYNONYMS);
//			if(value != null){
//				ArrayList<String> synonyms = e.getSynonyms();
//				if(synonyms==null || synonyms.isEmpty()){
//					e.setSynonyms(value);
//				}
//				else{
//					for(String element : value){
//						if(!synonyms.contains(element)){
//							synonyms.add(element);
//						}
//					}
//					e.setSynonyms(synonyms);
//				}
//			}
//		} else if (attribute.equalsIgnoreCase(CHARGE)){
//			String value = (String) getAttributeFromDB(CHARGE);
//			if(value != null){
//				e.setCharge(value);
//			}
//		} else if (attribute.equalsIgnoreCase(NAME)){
//			String value = (String) getAttributeFromDB(NAME);
//			if(value != null){
//				e.setName(value);
//			}
//		} else if (attribute.equalsIgnoreCase(CAS)){
//			String value = (String) getAttributeFromDB(CAS);
//			if(value != null){
//				e.setCaas(value);
//			}
//		} else if (attribute.equalsIgnoreCase(INCHIKEY)){
//			String value = (String) getAttributeFromDB(INCHIKEY);
//			if(value != null && !e.hasRef("inchikey", value)){
//				e.addRef(new BioRef("Inferred from id", "inchikey", value, confidenceLevel));
//			}
//		} else {
//			System.err.println("[Error] unknown attribute for "+e.getId()+"!");
//			System.err.println("\tattributes allowed : "+
//			CAS+", "+
//			CHARGE+", "+
//			FORMULA+", "+
//			INCHI+", "+
//			INCHIKEY+", "+
//			MASS+", "+
//			NAME+", "+
//			SMILES+", "+
//			SYNONYMS);
//		}
//	}
//	
//	public HashMap<String, String> getMap() {
//		return e;
//	}
//
//	public void setMap(HashMap<String, String> e) {
//		this.e = e;
//	}
//
//	public String getDbId() {
//		return dbId;
//	}
//	
//	public HashSet<String> getSupportedFeatures(){
//		return null;
//	}
//
//	public void setDbId(String dbId) {
//		this.dbId = dbId;
//	}
//	
}
