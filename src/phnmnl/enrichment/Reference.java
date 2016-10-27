package phnmnl.enrichment;

import java.util.Comparator;

import org.apache.commons.lang.Validate;

public class Reference implements Comparator<Reference>,Comparable<Reference>{
	
	public String dbName;	
	public String id;
	public int confidenceLevel;
	
	
	public Reference(String dbName,String id,int confidenceLevel){
		Validate.notNull(dbName, "BioRef's database name can't be null");
		Validate.notNull(id, "BioRef's database identifier can't be null");
		
		this.dbName=dbName;
		this.id=id;
		this.confidenceLevel=confidenceLevel;
	}
	
	
	@Override
	public int compareTo(Reference arg0) {
		return compare(this,arg0);
	}

	@Override
	public int compare(Reference o1, Reference o2) {
		return o1.getConfidenceLevel()-o2.getConfidenceLevel();
	}
	
	
	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getConfidenceLevel() {
		return confidenceLevel;
	}
	public void setConfidenceLevel(int i) {
		this.confidenceLevel=i;
	}
}
