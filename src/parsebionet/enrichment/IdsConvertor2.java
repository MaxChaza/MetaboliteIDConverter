package parsebionet.enrichment;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import parsebionet.enrichment.webservices.CTSWebService;


public class IdsConvertor2 {
	
	public HashMap<String,Object> result;
	public String input;
	public String inputType;
	public String outputType;
	public static final Map<String, String> IDENTIFIER_TO_CDK = new HashMap<String, String>(){
		/**
		 * 
		 */
		private static final long serialVersionUID = -2017973929947419381L;

		{
	        put("chebi","ChEBI");
	        put("kegg.compound","KEGG");
	        put("pubchem.substance","Pubchem SID");
	        put("pubchem.compound","PubChem CID");
	        put("hmdb","Human Metabolome Database");
	        put("chemspider","ChemSpider");
	        put("inchi","InChI Code");
	        put("inchikey","InChIKey");
	        put("cas","CAS");
	    }
	};
	public static final Map<String, String> CDK_TO_IDENTIFIER = new HashMap<String, String>(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 8827127165719451710L;

		{
	        put("ChEBI","chebi");
	        put("KEGG","kegg.compound");
	        put("PubChem CID","pubchem.compound");
	        put("Human Metabolome Database","hmdb");
	        put("ChemSpider","chemspider");
	        put("InChI Code","inchi");
	        put("InChIKey","inchikey");
	        put("CAS","cas");
	    }
	};
		
	public IdsConvertor2(String inputType, String input, String outputType) {
		
		this.input=input;
		this.inputType=inputType;
		this.outputType=outputType;
		
		
	}
	
	public JsonArray get(){
		CTSWebService cts;
		
		JsonArray output;
		
		if(this.inputType.equals("InChIKey")){
			cts=new CTSWebService("http://cts.fiehnlab.ucdavis.edu/service/",this.input);
			
			output=this.getidsfromInchikeyResult(cts.getData());
			
			
		}else{
			
			cts=new CTSWebService("http://cts.fiehnlab.ucdavis.edu/service/", this.inputType, this.outputType, this.input);
			
			ArrayList<String> inputTypes=cts.getFromValues()
				,outputTypes=cts.getToValues();
			
			if(! inputTypes.contains(this.inputType)){
				System.err.println("[Error] bad input type format : '"+inputType+"'");
				System.err.println("input format allowed:\n"+inputTypes.toString());
				return null;
			}else if(! outputTypes.contains(this.outputType)){
				System.err.println("[Error] bad output type format : '"+outputType+"'");
				System.err.println("output format allowed:\n"+outputTypes.toString());
				return null;
			}
			
			output=this.getSimpleConversionResult(cts.getData());
			
		}
		return output;
		
		
	}

	private JsonArray getidsfromInchikeyResult(JsonObject data) {
		
		JsonArray ja=new JsonArray();
		
		JsonObject jo=new JsonObject();
		jo.addProperty("dbname", "InChI Code");
		jo.addProperty("id", data.get("inchicode").getAsString());
		ja.add(jo);
		
		jo=new JsonObject();
		jo.addProperty("dbname", "InChIKey");
		jo.addProperty("id", data.get("inchikey").getAsString());
		ja.add(jo);
		
		for(JsonElement j:data.get("externalIds").getAsJsonArray()){
			
			String dbname=((JsonObject)j).get("name").getAsString();
			
			if(CDK_TO_IDENTIFIER.containsKey(dbname)  ){
				jo=new JsonObject();
				
				jo.addProperty("dbname", ((JsonObject)j).get("name").getAsString());
				jo.addProperty("id", ((JsonObject)j).get("value").getAsString());
				ja.add(jo);
			}
			
		}
		
		return ja;
	}

	private JsonArray getSimpleConversionResult(JsonObject data) {
		
		JsonArray ja=new JsonArray();
		
		for(JsonElement j:data.get("result").getAsJsonArray()){
			JsonObject jo=new JsonObject();
			
			jo.addProperty("dbname", data.get("toIdentifier").getAsString());
			jo.addProperty("id", j.getAsString());
			ja.add(jo);
		}
		
		return ja;
	}

}
