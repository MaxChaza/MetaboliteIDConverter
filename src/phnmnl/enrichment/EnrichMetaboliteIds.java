package phnmnl.enrichment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.StringArrayOptionHandler;

import phnmnl.enrichment.webservices.CTSWebService;

public class EnrichMetaboliteIds {

	private File oFile;


	@Option(name="-inFile", usage="Input file in tsv file format.")
	public String inFile = null;
	
	@Option(name="-headers", usage="use this if the input file has database names on the first line")
	public boolean hdrs = false;

	@Option(name="-inId", usage="Input ID to convert.")
	public String inId = null;

	@Option(name="-inDB", usage="[Required] Input database to convert from.", required=true)
	public String inDB = null;

	@Option(name="-outFile", usage="[Required] Output file name.", required=true)
	public String outFile ;

	@Option(name="-outDB", handler=StringArrayOptionHandler.class, usage="Output databases to convert to.")
	public String[] outDB=new String[0];

//	@Option(name="-check", usage="Check identifiers by crossing references between databases. This will take more time.")
//	public boolean check = false;


	private BufferedWriter bw;


	/**
	 * @param args
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {

		EnrichMetaboliteIds enricher = new EnrichMetaboliteIds();

		CmdLineParser parser = new CmdLineParser(enricher);

		try {
			parser.parseArgument(args);

			if( (enricher.inFile==null && enricher.inId==null) || (enricher.inFile!=null && enricher.inId!=null)){
				throw new CmdLineException("-inFile and -inId cannot be both set or null at the same time");
			}
			CTSWebService cts=new CTSWebService("http://cts.fiehnlab.ucdavis.edu/service/");

			ArrayList<String> inputDB = cts.getFromValues();

			if(!inputDB.contains(enricher.inDB)){
				throw new CmdLineException("Input database (-inDB) must be supported by the CTS service.\nThe available" +
						" databases are :\n"+inputDB.toString());

			}

		} catch (CmdLineException e) {
			System.err.println(e.getLocalizedMessage());
			System.err.println();
			System.err.println("Application Usage:");

			parser.printUsage(System.err);
			System.exit(1);
		}




		try {
			enricher.extendRef();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}


	public void extendRef() throws IOException{

		this.oFile=new File(this.outFile);
		if (!this.oFile.exists()) {
			this.oFile.createNewFile();
		}
		FileWriter fw = new FileWriter(this.oFile.getAbsoluteFile());
		this.bw = new BufferedWriter(fw);

		this.writeHeaders();


		if(this.inFile!=null){
			this.fileToFile();
		}else if(this.inId!=null){
			this.idToFile();
		}


		this.closeBuffer();
	}


	/**
	 * @throws IOException 
	 * 
	 */
	public void fileToFile() throws IOException{
		
		BufferedReader br= new BufferedReader(new FileReader(this.inFile));
		
		for(String s:this.getIdlist(br)){
			this.idToFile(s);
		}
	}

	/**
	 * 
	 * @throws IOException
	 */
	public void idToFile() throws IOException{
		this.idToFile(this.inId);
	}

	/**
	 * 
	 * @param id
	 * @throws IOException
	 */
	public void idToFile(String id) throws IOException{

		HashMap<String, Set<Reference>> map = new HashMap<String, Set<Reference>>();
		Reference initialRef = new Reference( inDB, id, 1);
		map.put(inDB,new HashSet<Reference>());
		map.get(inDB).add(initialRef);

		ExtendReferenceFromInchiKey exRef = new ExtendReferenceFromInchiKey(map);

		exRef.findAndExtendFromInChiKey();

		writeReferencesToFile(exRef.getRefs());
	}

	/**
	 * Finds the list of Ids in the file
	 * @param br 
	 * @return
	 * @throws IOException 
	 */
	private ArrayList<String> getIdlist(BufferedReader br) throws IOException {
		int col=1;
		if (this.hdrs){
			col=this.findDatabaseColumn(br);
		}
		
		if(col==0){
			System.err.println("Input database not found in the given file. Please check your file.");
			System.exit(1);
		}
		
		ArrayList<String> idList=new ArrayList<String>();
		String sCurrentLine;
		
		while ((sCurrentLine = br.readLine()) != null) {
			String[] arr=sCurrentLine.split("\t");
			
			if(arr[col-1].contains(",")){
				for(String s:arr[col-1].split(","))	
					idList.add(s.replaceAll("\\s", ""));
			}else{
				idList.add(arr[col-1]);
			}
					
		}
		
		if (br != null)br.close();
		
		return idList;
	}


	/**
	 * If the file has more than one column, finds the column corresponding to the input database
	 */
	private int findDatabaseColumn(BufferedReader br) throws IOException {
		
		String header=br.readLine();
		
		int i=0;
		
		for(String s:header.split("\t")){
			i++;
			if(s.equalsIgnoreCase(inDB)){
				return i;
			}
		}
		
		return 0;
	}


	/**
	 * 
	 * @throws IOException
	 */
	private void writeHeaders() throws IOException {
		this.bw.write(inDB);

		if(this.outDB.length!=0){
			for(String DBName: this.outDB){
				if(!DBName.equalsIgnoreCase(inDB)){
					this.bw.write("\t"+DBName);
				}
			}
		}else{
			for(String DBName:IdsConvertor2.USUAL_DB_NAME){
				if(!DBName.equalsIgnoreCase(inDB)){
					this.bw.write("\t"+DBName);
				}
			}
		}
		this.bw.write("\n");
	}


	private void writeReferencesToFile(HashMap<String, Set<Reference>> refMap) throws IOException{

		//first write the ids of the input database

		Set<Reference> initialSet=refMap.get(inDB);
		int i=0;
		for(Reference r: initialSet){
			if(i==0){
				this.bw.write(r.id);
			}else{
				this.bw.write(","+r.id);
			}
			i++;
		}

		if(this.outDB.length!=0){
			for(String DBName: this.outDB){
				if(!DBName.equalsIgnoreCase(inDB) ){
					this.bw.write("\t");
					if(refMap.containsKey(DBName)){
						Set<Reference> set=refMap.get(DBName);
						i=0;
						for(Reference r: set){
							if(i==0){
								this.bw.write(r.id);
							}else{
								this.bw.write(", "+r.id);
							}
							i++;
						}
					}

				}
			}
		}else{
			for(String DBName:IdsConvertor2.USUAL_DB_NAME){
				if(!DBName.equalsIgnoreCase(inDB)  ){
					this.bw.write("\t");
					if(refMap.containsKey(DBName)){
						Set<Reference> set=refMap.get(DBName);
						i=0;
						for(Reference r: set){
							if(i==0){
								this.bw.write(r.id);
							}else{
								this.bw.write(", "+r.id);
							}
							i++;
						}
					}
				}
			}
		}		

		this.bw.write("\n");
	}


	private void closeBuffer() throws IOException {
		this.bw.close();

		System.out.println("OutputFile Written");

	}
}
