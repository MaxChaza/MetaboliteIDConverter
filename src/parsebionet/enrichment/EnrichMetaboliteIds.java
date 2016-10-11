package parsebionet.enrichment;

import java.io.File;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.StringArrayOptionHandler;

public class EnrichMetaboliteIds {

	
	@Option(name="-inFile", usage="Input file in tsv file format.")
	public String inFile = null;
	
	@Option(name="-inId", usage="Input ID to convert.")
	public String inId = null;
	
	@Option(name="-inDB", usage="[Required] Input database to convert from.", required=true)
	public String inDB = null;
	
	@Option(name="-outFile", usage="[Required] Output file name.", required=true)
	public String outFile ;
	
	@Option(name="-outDB", handler=StringArrayOptionHandler.class, usage="Output databases to convert to.")
	public String[] outDB=new String[0];
	
	@Option(name="-check", usage="Check identifiers by crossing references between databases. This will take more time.")
	public boolean check = false;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		EnrichMetaboliteIds enricher=new EnrichMetaboliteIds();
		
		CmdLineParser parser = new CmdLineParser(enricher);
		
		try {
			parser.parseArgument(args);
		} catch (CmdLineException e) {
			parser.printUsage(System.err);
			System.exit(1);
		}
		
		for (String s:enricher.outDB){
			System.out.println(s);
		}
	}

	
	public void fileToFile(File f){
		
	}
	
	public void idToFile(){
		
	}
	
	private void writeToFile(){
		
	}
}
