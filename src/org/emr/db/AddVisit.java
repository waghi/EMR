package org.emr.db;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.CompiledExpression;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.modules.XQueryService;

public class AddVisit 
{
	protected static final String URI = "xmldb:exist://localhost:8080/exist/xmlrpc";
	public static CompiledExpression compiled;
	public static XQueryService xqs;
	public static Collection col=null;
	public static final String driver = "org.exist.xmldb.DatabaseImpl";
	public static void addvisit()
	{
		ResourceSet result;
		try
		{
			String query="declare default element namespace 'http://www.emr.org';"
					+ "update insert <Visits><Visit><Hospital>"+"hosp"+"</Hospital><Doctor>"+"doc"+"</Doctor><VisitDate>"+"date"+"</VisitDate></Visit></Visits> into doc('Ins.xml')/EMR/Patients/Patient/PatientDetails[RegistrationNumber='3']/..";
			compiled= xqs.compile(query);
			result=xqs.execute(compiled);
			addconsult();
		}
		catch(Exception e)
		{
			
		}
	}
	
	public static void addconsult()
	{
		ResourceSet result;
		try
		{
			String query="declare default element namespace 'http://www.emr.org';update insert"+
						"<VisitDetails><Consultancy><Symptoms>"+"symptoms"+"</Symptoms>"
					+"<Prescription><PrescriptionDate>"+"presdate"+"</PrescriptionDate><Medication>"
					+"<MedicineName>"+"medicine"+"</MedicineName><Dosage>"+"dose"+"</Dosage></Medication>"
					+"</Prescription></Consultancy></VisitDetails> into doc('Ins.xml')/EMR/Patients/Patient/Visits/Visit[VisitDate='date']";
			compiled= xqs.compile(query);
			result=xqs.execute(compiled);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	public static void adddiagnose()
	{
		ResourceSet result;
		
		try
		{
			String query="declare default element namespace 'http://www.emr.org';update insert"+
					"<VisitDetails><Diagnostics><TestName>"+"tests"+"</TestName><TestResults>"+"res"+"</TestResults></Diagnostics></VisitDetails>"
					+"into doc('Ins.xml')/EMR/Patients/Visits/Visit[VisitDate='date']";
			compiled= xqs.compile(query);
			result=xqs.execute(compiled);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void main(String ar[]) throws Exception
	{
		Class cl = Class.forName(driver);
        Database database = (Database) cl.newInstance();
        database.setProperty("create-database", "true");
        DatabaseManager.registerDatabase(database);
        col = DatabaseManager.getCollection(URI +"/db/apps/hosp/");
		xqs=(XQueryService) col.getService("XQueryService", "1.0");
		addvisit();
		
	}

}
