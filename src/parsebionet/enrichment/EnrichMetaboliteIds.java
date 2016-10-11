package parsebionet.enrichment;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.StringArrayOptionHandler;

public class EnrichMetaboliteIds {

	
	@Option(name="-inFile", usage="Input file in tsv file format ")
	public String inFile = null;
	
	@Option(name="-inId", usage="Input ID to convert")
	public String inId = null;
	
	@Option(name="-inDB", usage="Input database to convert from", required=true)
	public String inDB = null;
	
	@Option(name="-outFile", usage="output file name.", required=true)
	public String outFile ;
	
	@Option(name="-outDB", handler=StringArrayOptionHandler.class, usage="Output databases to convert to")
	public String[] outDB;
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		EnrichMetaboliteIds enricher=new EnrichMetaboliteIds();
		
		CmdLineParser parser = new CmdLineParser(enricher);
		
		try {
			parser.parseArgument(args);
		} catch (CmdLineException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("testing build");
		
		for (String s:enricher.outDB){
			System.out.println(s);
		}
	}

	
	public static void fileToFile(){
		
	}
	
	public static void idToFile(){
		
	}
}
