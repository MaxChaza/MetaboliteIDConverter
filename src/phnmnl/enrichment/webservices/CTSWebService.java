package phnmnl.enrichment.webservices;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.jersey.api.client.ClientResponse;

public class CTSWebService extends ExtWebService {
	
	public String inchikey;
	public String inputDb;
	public String outputDb;
	public String inputId;

	public CTSWebService(String Url) {
		super(Url);
	}
	
	public CTSWebService(String Url, String InchiKey) {
		super(Url);
		this.inchikey=InchiKey;
	}
	
	public CTSWebService(String Url, String inDB, String outDB, String inId) {
		super(Url);
		this.inputDb=inDB;
		this.outputDb=outDB;
		this.inputId=inId;
	}

	public CTSWebService() {
		super();
	}
	
	public ArrayList<String> getFromValues(){
		
		ArrayList<String> frValues=new ArrayList<String>();
		String data=this.getWebservice().path("conversion").path("fromValues").get(String.class);
		
		JsonParser prser=new JsonParser();
		
		for(JsonElement j:(JsonArray)prser.parse(data)){
			frValues.add(j.getAsString());
		}
		
		return frValues;
	}
	
	public ArrayList<String> getToValues(){
		ArrayList<String> toValues=new ArrayList<String>();
		
		String data=this.getWebservice().path("conversion").path("toValues").get(String.class);
		JsonParser prser=new JsonParser();
		
		for(JsonElement j:(JsonArray)prser.parse(data)){
			toValues.add(j.getAsString());
		}
		
		return toValues;
	}

	@Override
	protected boolean testConnection() {
				
		this.statusCode=this.getWebservice().path("compound").path("QNAYBMKLOCPYGJ-REOHCLBHSA-N").get(ClientResponse.class).getStatus();
		
		if(this.statusCode==200){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public JsonObject getData() {
		
		if(this.testConnection()){
			String data;
			if(this.inchikey!=null){
				data=this.getWebservice().path("compound").path(this.inchikey).get(String.class);
			}else{
				data=this.getWebservice().path("convert")
						.path(this.inputDb.replaceAll(" ", "%20"))
						.path(this.outputDb.replaceAll(" ", "%20"))
						.path(this.inputId.replaceAll(" ", "%20"))
						.get(String.class);
			}
			
			
			JsonParser prser=new JsonParser();
			
			JsonElement j=prser.parse(data);
			if(j.isJsonArray()){
				return ((JsonArray)j).get(0).getAsJsonObject();
			}else if(j.isJsonObject()){
				return (JsonObject)j;
			}else{
				return null;
			}
			
		}
		
		return null;
	}

	
	

}
