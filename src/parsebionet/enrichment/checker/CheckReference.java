package parsebionet.enrichment.checker;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import parsebionet.enrichment.Reference;

public class CheckReference {
//	
//	private Reference ref;
//	private HashMap<String, String> keyValueMap=new HashMap<String, String>();
//	private SetAttributeFromDB db;
//	public boolean flag=true;
//	public static final HashSet<String> SUPPORTED_DB = new HashSet<String>()
//	{
//		{
//			add("chebi");
//			add("hmdb");
//			add("kegg.compound");
//			add("pubchem.compound");
//		}
//	};
//	
//	public CheckReference(Reference ref, HashMap<String, String> map) {
//		this.ref=ref;
//		this.keyValueMap=map;
//		String dbName=ref.getDbName();
//
//		if(dbName.equals("chebi")){
//			db= new SetAttributeFromCHEBI(map,ref.getId());
//		}else if(dbName.equals("hmdb")){
//			db= new SetAttributeFromHMDB(map,ref.getId());
//		}else if(dbName.equals("kegg.compound")){
//			db= new SetAttributeFromKEGG(map,ref.getId());
//		}else if(dbName.equals("pubchem.compound")){
//			db= new SetAttributeFromPCC(map,ref.getId());
//		}else{
//			System.err.println("[Warning] unsupported DB");
//			System.err.println("useable DB: "+SUPPORTED_DB);
//		}
//		if(!db.flag){
//			System.err.println("can't access to "+ref.getDbName()+" "+ref.getId());
//			this.flag=false;
//		}
//		
//	}
//	
//	/**
//	 * check if the formula in DB entry is equal to {@link BioPhysicalEntity}'s formula
//	 **/
//	public boolean checkFormula(){
//		if(!this.flag){
//			return false;
//		}
//		if(!db.getSupportedFeatures().contains(SetAttributeFromDB.FORMULA)){
//			return false;
//		}
//		
//		String formulaFromEntity = entity.getChemicalFormula();
//		if(formulaFromEntity==null){
//			return false;
//		}
//		
//		String formulaFromRef = (String) db.getAttributeFromDB("formula");
//		if(formulaFromRef==null){
//			return false;
//		}
////		System.out.println(formulaFromEntity+" <-> "+formulaFromRef);
//		return formulaFromRef.equals(formulaFromEntity);
//	}
//	
//	/**
//	 * check if the inchi in DB entry is equal to {@link BioPhysicalEntity}'s inchi
//	 **/
//	public boolean checkInChI(){
//		if(!this.flag){
//			System.err.println("can't access to "+ref.getDbName()+" "+ref.getId());
//			return false;
//		}
//		if(!db.getSupportedFeatures().contains(SetAttributeFromDB.INCHI)){
//			return false;
//		}
//		
//		String inchiFromEntity = entity.getInchi();
//		if(inchiFromEntity==null){
//			return false;
//		}
//		
//		String inchiFromRef = (String) db.getAttributeFromDB("inchi");
//		if(inchiFromRef==null){
//			return false;
//		}
////		System.out.println(inchiFromEntity+" <-> "+inchiFromRef);
//		return inchiFromRef.equals(inchiFromEntity);
//	}
//
//	/**
//	 * check if the main part of an inchi in DB entry is equal to same part of {@link BioPhysicalEntity}'s inchi
//	 * (ignore charge and version levels in inchi)
//	 **/
//	public boolean checkTruncatedInChI(){
//		if(!this.flag){
//			System.err.println("can't access to "+ref.getDbName()+" "+ref.getId());
//			return false;
//		}
//		if(!db.getSupportedFeatures().contains(SetAttributeFromDB.INCHI)){
//			return false;
//		}
//		
//		String inchiFromEntity = entity.getInchi();
//		if(inchiFromEntity==null){
//			return false;
//		}
//		String regex="InChI=1S?/([^/]*/[^/]*/[^/]*).*";			
//		Matcher m=Pattern.compile(regex).matcher(inchiFromEntity);
//		if(m.matches()){
//			inchiFromEntity=m.group(1);
//			
//			String inchiFromRef = (String) db.getAttributeFromDB("inchi");
//			if(inchiFromRef==null){
//				return false;
//			}
//			String regex2="InChI=1S?/([^/]*/[^/]*/[^/]*).*";
//			Matcher m2=Pattern.compile(regex2).matcher(inchiFromRef);
//			if(m2.matches()){
//				inchiFromRef=m2.group(1);
////				System.out.println(inchiFromEntity+" <-> "+inchiFromRef);
//				return inchiFromRef.equals(inchiFromEntity);
//			}
//			
//		}
//		return false;
//		
//	}
//	
//	/**
//	 * check if the name (or synonyms) in DB entry is equal to {@link BioPhysicalEntity}'s name
//	 **/
//	public boolean checkName(){
//		if(!this.flag){
//			System.err.println("can't access to "+ref.getDbName()+" "+ref.getId());
//			return false;
//		}
//		if(!db.getSupportedFeatures().contains(SetAttributeFromDB.NAME)){
//			return false;
//		}
//		
//		String nameFromEntity = entity.getName();
//		if(nameFromEntity==null){
//			return false;
//		}
//		
//		String nameFromRef = (String) db.getAttributeFromDB("name");
//		if(nameFromRef==null){
//			return false;
//		}
//		if(nameFromRef.equalsIgnoreCase(nameFromEntity)){
//			return true;
//		}
//		if(!db.getSupportedFeatures().contains(SetAttributeFromDB.SYNONYMS)){
//			return false;
//		}
//		if(entity.getSynonyms()!=null && entity.getSynonyms().contains(nameFromRef)){
//			return true;
//		}
//		return false;
//	}
//	
//	/**
//	 * check if the charge in DB entry is equal to {@link BioPhysicalEntity}'s charge
//	 **/
//	public boolean checkCharge(){
//		if(!this.flag){
//			System.err.println("can't access to "+ref.getDbName()+" "+ref.getId());
//			return false;
//		}
//		if(!db.getSupportedFeatures().contains(SetAttributeFromDB.CHARGE)){
//			return false;
//		}
//		
//		String chargeFromEntity = keyValueMap.get("Charge");
//		if(chargeFromEntity==null){
//			return false;
//		}
//
//		String chargeFromRef = (String) db.getAttributeFromDB("charge");
//		if(chargeFromRef==null){
//			return false;
//		}
////		System.out.println(chargeFromEntity+" <-> "+chargeFromRef);
//		return chargeFromRef.equals(chargeFromEntity);
//	}
//	
//	/**
//	 * return confidence level:
//	 * same name or same InChI between {@link BioPhysicalEntity} and DB entry : 1
//	 * same formula between {@link BioPhysicalEntity} and DB entry : 2
//	 * else: 3
//	 **/
//	public int getConfidenceLevel(){
//		if(!this.flag){
//			return 3;
//		}
//		if(this.checkName()){
//			return 1;
//		}else if(this.checkInChI()){
//			return 1;
//		}else if(this.checkTruncatedInChI()){
//			return 2;
//		}else if(this.checkFormula()){
//			return 2;
//		}else{
//			return 3;
//		}
//	}
//	
//	public boolean getFlag(){
//		return this.flag;
//	}
//	
//	public static void main(String[] args){
//		BioPhysicalEntity entity = new BioPhysicalEntity("M_test","Icosanoic acid");
//		entity.setCharge("0");
//		entity.setChemicalFormula("C20H40O2");
//		entity.setInchi("InChI=1S/C20H40O2/c1-2-3-4-5-6-7-8-9-10-11-12-13-14-15-16-17-18-19-20(21)22/h2-19H2,1H3,(H,21,22)");
//		ExtendRefs extRef = new ExtendRefs(entity, true);
//		extRef.extendFromName();
//		
//		entity.addRef("hmdb","HMDB02037",4,"is","Inferred from my crystal ball");
//		for(String db : entity.getRefs().keySet()){
//			if(SUPPORTED_DB.contains(db)){
//				Set<BioRef> set = entity.getRefs().get(db);
//				for(BioRef ref : set){
//					CheckRef check = new CheckRef(ref, entity);
//					System.out.println(ref.getId());
//					System.out.println("\tcharge: "+check.checkCharge());
//					System.out.println("\tinchi: "+check.checkInChI());
//					System.out.println("\tformula: "+check.checkFormula());
//					System.out.println("\tname: "+check.checkName());
//					System.out.println("\ttruncInchi: "+check.checkTruncatedInChI());
//					System.out.println("\tconfidenceLevel: "+check.getConfidenceLevel());
//				}
//			}
//		}
//	}
}
