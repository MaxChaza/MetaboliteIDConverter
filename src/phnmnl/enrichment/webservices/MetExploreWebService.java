package phnmnl.enrichment.webservices;

import com.google.gson.JsonObject;
import com.sun.jersey.api.client.ClientResponse;

public class MetExploreWebService extends ExtWebService {

	public MetExploreWebService(String Url) {
		super(Url);
		// TODO Auto-generated constructor stub
	}

	public MetExploreWebService() {
		super("http://vm-metexplore.toulouse.inra.fr:8080/metExploreWebService/");
	}

	@Override
	protected boolean testConnection() {
		
		this.statusCode=this.getWebservice().path("biosources").path("1363").get(ClientResponse.class).getStatus();
		
		if(this.statusCode==200){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	protected JsonObject getData() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public JsonObject map(){
		return null;
		
	}
	
//	public String map(String inchikeyJson){
//		
//		this.getWebservice().path("mapping").path("launchtokenmapping").path("inchikey").path("1363").get(String.class);
//		
//		ClientResponse response = this.getWebservice().type("application/json").post(ClientResponse.class, inchikeyJson);
//		if (response.getStatus() != 200) {
//			throw new RuntimeException("Failed : HTTP error code : "
//			     + response.getStatus());
//		}
//
//		
//		String output = response.getEntity(String.class);
//		System.out.println(output);
//		return output;
//	}

}
