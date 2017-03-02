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
 * class to set Attributes from CHEBI DB using CHEBI id
 * 
 * 
 */
package parsebionet.enrichment.checker;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


/**
 * @author frainay
 * 14-01-2014
 * set Attributes from CHEBI DB using CHEBI id
 */
@SuppressWarnings("serial")
public class SetAttributeFromCHEBI extends SetAttributeFromDB{
//	
//	private ChebiWebServiceClient client;
//	public Entity dbe;
//		private HashSet<String> supportedFeatures = new HashSet<String>(){
//		{
//			this.add(CHARGE);
//			this.add(INCHI);
//			this.add(SMILES);
//			this.add(INCHIKEY);
//			this.add(MASS);
//			this.add(NAME);
//			this.add(FORMULA);
//			this.add(SYNONYMS);
//			this.add(CAS);
//		}
//	};
//
//	/**
//	 * @param BioPhysicalEntity e, the compound to modify
//	 * @param String dbId, identifier corresponding to e in CHEBI
//	 */
//	public SetAttributeFromCHEBI(BioPhysicalEntity e, String dbId) {		
//		super(e,dbId);	
//	}
//	
//	public Boolean getDBEntry(){	
//		try{
//			this.client = new ChebiWebServiceClient();
//			dbe = client.getCompleteEntity(this.dbId);
//			return true;
//		} catch (ExceptionInInitializerError exc1){
//			System.err.println("[Error] unexisting id: "+dbId);
//			exc1.printStackTrace();
//			return false;
//		} catch (ChebiWebServiceFault_Exception exc2){
//			System.err.println("Problem with CHEBI web service");
//			exc2.printStackTrace();
//			return false;
//		} catch (java.lang.NoClassDefFoundError exc3){
//			System.err.println("CHEBI compound probably deleted by the CHEBI Team");
//			return false;
//		} catch (Exception exc4){
//			exc4.printStackTrace();
//			return false;
//		}
//	}
//	
//
//	public Object getAttributeFromDB(String attribute){
//		Object value = null;
//		try{
//			if(attribute.equals(INCHI)){
//				value = dbe.getInchi();
//			} else if (attribute.equals(SMILES)){
//				value = dbe.getSmiles();
//			} 
//			else if (attribute.equals(FORMULA)){
//				List<DataItem> atoms = dbe.getFormulae();
//				value="";
//				for (int i=0; i<atoms.size();i++){
//					DataItem item = atoms.get(i);
//					value=value+item.getData();
//				}
//			} else if (attribute.equals(NAME)){
//				value = dbe.getChebiAsciiName();	
//			} else if (attribute.equals(MASS)){
//				value = dbe.getMass();
//			} else if (attribute.equals(INCHIKEY)){
//				value = dbe.getInchiKey();
//			} else if (attribute.equals(CHARGE)){
//				value = dbe.getCharge();
//			} else if (attribute.equals(CAS)){
//				List<DataItem> registryNumbers = dbe.getRegistryNumbers();
//				for(DataItem num:registryNumbers){
//					if(num.getType().equals("CAS Registry Number")){
//						value=num.getData();
//					}
//				}
//			} else if (attribute.equals(SYNONYMS)){
//				List<DataItem> synonyms = dbe.getSynonyms(); 
//				ArrayList<String> list= new ArrayList<String>();
//				for (int i=0; i<synonyms.size();i++){
//					DataItem item = synonyms.get(i);
//					list.add(item.getData());
//				}
//				value=list;
//			} else {
//				System.err.println("[Warning] attribute "+attribute+" not available on CHEBI or not implemented yet...");
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
//		ArrayList<String> syn = new ArrayList<String>();
//		syn.add("D-Glc");syn.add("toto");
//		e.setSynonyms(syn);
//		SetAttributeFromCHEBI chebiSearch = new SetAttributeFromCHEBI(e, "CHEBI:4167");
//		chebiSearch.setAttribute("inchi");
//		chebiSearch.setAttribute("smiles");
//		chebiSearch.setAttribute("name");
//		chebiSearch.setAttribute("mass");
//		chebiSearch.setAttribute("charge");
//		chebiSearch.setAttribute("formula");
//		chebiSearch.setAttribute("synonyms");
//		chebiSearch.setAttribute("cas");
//		
//		System.out.println(e.getInchi());
//		System.out.println(e.getSmiles());
//		System.out.println(e.getName());
//		System.out.println(e.getCharge());
//		System.out.println(e.getChemicalFormula());
//		System.out.println(e.getSynonyms());
//		System.out.println(e.getCaas());
//		System.out.println(chebiSearch.getAttributeFromDB("inchiKey"));
//	}
}