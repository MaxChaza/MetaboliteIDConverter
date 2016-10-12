package parsebionet.enrichment.webservices;

import java.io.StringReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

import javax.ws.rs.core.UriBuilder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.google.gson.JsonObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public abstract class ExtWebService {
	
	protected ClientConfig config;
	protected Client client;
	protected WebResource Webservice;
	
	protected String url;
	protected int statusCode;
	
	public ExtWebService(String Url){
		
		url=Url;
		
		config = new DefaultClientConfig();
		client = Client.create(this.getConfig());
		
		URI uri=this.getBaseURI();
		Webservice = this.getClient().resource(uri);
	}
		
	public ExtWebService() {
		config = new DefaultClientConfig();
		client = Client.create(this.getConfig());
	}
	
	protected abstract boolean testConnection();
	
	protected abstract JsonObject getData();
	
	
	/**
	 * 
	 * @return URI
	 */
	protected URI getBaseURI() {
		return UriBuilder.fromUri(this.url).build();
	}
	
	
	protected Document loadXMLFromString(String xml) throws Exception
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(xml));
		return builder.parse(is);
	}

	public WebResource getWebservice() {
		return Webservice;
	}

	public String getUrl() {
		return url;
	}

	public void setConfig(ClientConfig config) {
		this.config = config;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public void setWebservice(WebResource webservice) {
		Webservice = webservice;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Client getClient() {
		return client;
	}

	public ClientConfig getConfig() {
		return config;
	}

}
