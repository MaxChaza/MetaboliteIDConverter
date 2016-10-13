package parsebionet.enrichment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import parsebionet.enrichment.webservices.CTSWebService;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class IdsConvertor2 {
	
	public HashMap<String,Object> result;
	public String input;
	public String inputType;
	public String outputType;

	public static final List<String> USUAL_DB_NAME = new ArrayList<String>(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 8827127165719451710L;

		{
	        add("InChIKey");
	        add("ChEBI");
	        add("InChI Code");
	        add("KEGG");
	        add("PubChem CID");
	        add("Human Metabolome Database");
	        add("ChemSpider");
	        add("CAS");
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
			
			if(USUAL_DB_NAME.contains(dbname)  ){
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
