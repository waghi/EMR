package org.emr.db;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.CompiledExpression;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.modules.XQueryService;

public class Entry 
{
		protected static final String URI = "xmldb:exist://localhost:8080/exist/xmlrpc";
		public static CompiledExpression compiled;
		public static XQueryService xqs;
		public static Collection col=null;
		public static final String driver = "org.exist.xmldb.DatabaseImpl";
		
		
		
	public static void  puthosp()
	{
		try
		{
			ResourceSet result;
		
		
	
		if(searchhosp()==true)
		{
		String query="declare default element namespace 'http://www.emr.org';update insert <Hospital><HospitalRegestrationNumber>"+""+"</HospitalRegestrationNumber><Name>"
				+" " +"</Name><Address>"+""+"</Address></Hospital>  into doc('Ins.xml')/*:EMR/*:Hospitals";
		compiled= xqs.compile(query);
		result = xqs.execute(compiled);
		}
		else
		{
			System.out.println("Data Already Exist");
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void putdoc()
	{
		try
		{
			ResourceSet result;
			
			
			
			if(searchdoc()==true)
			{
			String query="declare default element namespace 'http://www.emr.org';update insert <Doctor><DoctorRegistrationNumber>"+"12365464545" +"</DoctorRegistrationNumber><DoctorName>"
            		+"Dr ram"+"</DoctorName><DoctorSpecialization>"+"MBBS"+"</DoctorSpecialization><Timings><slots><Day>"+"Monday"+"</Day>"
            		+ "<Time>"+"09:14:52"+"</Time></slots></Timings></Doctor> into doc('Ins.xml')/*:EMR/*:Doctors";
			compiled= xqs.compile(query);
			result=xqs.execute(compiled);
			}
			else
			{
				System.out.println("Data Already Exist");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void putpatient()
	{
		try
		{
			ResourceSet result;
			
			;
			if(searchpatient()==true)
			{
				
			String query="declare default element namespace 'http://www.emr.org';update insert <Patient><PatientDetails>"
					+"<Name>"+"string" +"</Name><DOB>"+" "+"</DOB>"+
				"<Gender>"+" "+"</Gender><Phone>"+" "+"</Phone><Address>"+" "+"</Address><RegistrationNumber>"+"2345"+"</RegistrationNumber><GovtId>"+
				" "+"</GovtId></PatientDetails></Patient> into doc('Ins.xml')/EMR/Patients";
			compiled= xqs.compile(query);
			result =xqs.execute(compiled);
			}
			else
			{
				System.out.println("Data Already Exist");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static boolean searchdoc()
	{
		try
		{
		ResourceSet result;
		String search="declare default element namespace 'http://www.emr.org';for $doc in doc('Ins.xml')/*:EMR/*:Doctors/*:Doctor where $doc/DoctorRegistrationNumber=125 return $doc";
		compiled= xqs.compile(search);
		result =xqs.execute(compiled);
		if(result.getSize()==0)
		{
		return true;
		}
		else
		{
			 return false;
		}
		}
		catch(Exception e)
		{
			
			e.printStackTrace();
			return false;
		}
	}
	public static boolean searchhosp()
	{
		try
		{
		ResourceSet result;
		String search="declare default element namespace 'http://www.emr.org';for $hosp in doc('Ins.xml')/*:EMR/*:Hospitals/*:Hospital where $hosp/HospitalRegistrationNumber=687 return $hosp";
		compiled= xqs.compile(search);
		result = xqs.execute(compiled);
		if(result.getSize()==0)
		{
		return true;
		}
		else
		{
			 return false;
		}
		}
		catch(Exception e)
		{
			
			e.printStackTrace();
			return false;
		}

	}
	public static boolean searchpatient()
	{
		try
		{
		ResourceSet result;
		String search="declare default element namespace 'http://www.emr.org';for $patient in doc('Ins.xml')/EMR/Patients/Patient/PatientDetails where $patient/RegistrationNumber=9876541 return $patient";
		compiled= xqs.compile(search);
		 result =xqs.execute(compiled);
		if(result.getSize()==0)
		{
		return true;
		}
		else
		{
			 return false;
		}
		}
		catch(Exception e)
		{
			
			e.printStackTrace();
			return false;
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
        putdoc();
        col.close();
        
               
	}
        
        

}
