package parsebionet.enrichment;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

@Deprecated
public class IdsConvertor {
	
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
	    }
	};
		
	public IdsConvertor(String inputType, String input, String outputType) {
		
		this.input=input;
		this.inputType=inputType;
		this.outputType=outputType;
	}
	
	public Object get(){
		
	
		try {		
			ArrayList<String> inputTypes = new ObjectMapper().readValue(new URL("http://cts.fiehnlab.ucdavis.edu/service/conversion/fromValues"),ArrayList.class);
			ArrayList<String> outputTypes = new ObjectMapper().readValue(new URL("http://cts.fiehnlab.ucdavis.edu/service/conversion/toValues"),ArrayList.class);

			if (!inputTypes.contains(inputType)){
				System.err.println("[Error] bad input type format : "+inputType);
				System.err.println("input format allowed:\n"+inputTypes.toString());
				return null;
			}
			if (!outputTypes.contains(outputType) ){
				System.err.println("[Error] bad output type format : "+outputType);
				System.err.println("output format allowed:\n"+outputTypes.toString());
				return null;
			}
			
			String s = "http://cts.fiehnlab.ucdavis.edu/service/convert/"+inputType+"/"+outputType+"/"+input;
			s=s.replaceAll(" ", "%20");
			URL url = new URL(s);
			
			ArrayList resArray = new ArrayList();
			resArray = new ObjectMapper().readValue(url, new TypeReference<List<Map<String, Object>>>() {});
			result = (HashMap<String, Object>) resArray.get(0);
			return result.get("result");
			
		} catch (JsonParseException e) {
			e.printStackTrace();return null;
		} catch (JsonMappingException e) {
			e.printStackTrace();return null;
		} catch (IOException e) {
			e.printStackTrace();return null;
		}
	}

}
