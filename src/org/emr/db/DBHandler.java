package org.emr.db;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.CompiledExpression;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.modules.XQueryService;

public class DBHandler {
	private static final String URI = "xmldb:exist://localhost:8080/exist/xmlrpc/db/apps/";
	private static final String driver = "org.exist.xmldb.DatabaseImpl";
	private static final String collection = "Sample/emrDB/";
	private static Database db;
	private static Collection coll;
	public static XQueryService xqs;
	private static CompiledExpression compiled;

	static {
		try {
			db = (Database) Class.forName(driver).newInstance();
			DatabaseManager.registerDatabase(db);
			coll = DatabaseManager.getCollection(URI + collection, "admin", "wasitme29");
			xqs = (XQueryService) coll.getService("XQueryService", "3.0");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static boolean putDoctor(String drn, String name, String ds) {
		try {

			if (searchdoc(drn) == true) {
				String query = "declare default element namespace 'http://www.emr.org';update insert <Doctor><DoctorRegistrationNumber>"
						+ drn + "</DoctorRegistrationNumber><DoctorName>" + name + "</DoctorName><DoctorSpecialization>"
						+ ds + "</DoctorSpecialization><Timings><slots><Day>" + "Monday" + "</Day>" + "<Time>"
						+ "09:14:52" + "</Time></slots></Timings></Doctor> into doc('Ins.xml')/*:EMR/*:Doctors";
				compiled = xqs.compile(query);
				xqs.execute(compiled);
				return true;
			} else {
				System.out.println("Data Already Exist");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean putHospital(String hrn, String name, String addr) {
		try {

			if (searchhosp(hrn) == true) {
				String query = "declare default element namespace 'http://www.emr.org';update insert <Hospital><HospitalRegistrationNumber>"
						+ hrn + "</HospitalRegistrationNumber><Name>" + name + "</Name><Address>" + addr
						+ "</Address></Hospital>  into doc('Ins.xml')/*:EMR/*:Hospitals";
				compiled = xqs.compile(query);
				xqs.execute(compiled);
				return true;
			} else {
				System.out.println("Data Already Exist");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean putPatient(String prn, String name, String DOB, String gender, String phone, String addr,
			String gname, String gid, String gdname) {
		try {
			
			if (searchpatient(prn) == true) {

				String query = "declare default element namespace 'http://www.emr.org';update insert <Patient><PatientDetails>"
						+ "<Name>" + name + "</Name><DOB>" + DOB + "</DOB>" + "<Gender>" + gender + "</Gender><Phone>"
						+ phone + "</Phone><Address>" + addr + "</Address><RegistrationNumber>" + prn
						+ "</RegistrationNumber><GovtId><IdName>" +gname+"</IdName><IdNumber>"+gid+"</IdNumber></GovtId>"
						+ "</PatientDetails></Patient><Visits></Visits> into doc('Ins.xml')/EMR/Patients";
				compiled = xqs.compile(query);
				xqs.execute(compiled);
				return true;
			} else {
				System.out.println("Data Already Exist");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean searchdoc(String did) {
		try {
			ResourceSet result;
			String search = "declare default element namespace 'http://www.emr.org';for $doc in doc('Ins.xml')/*:EMR/*:Doctors/*:Doctor where $doc/DoctorRegistrationNumber="
					+ did + " return $doc";
			compiled = xqs.compile(search);
			result = xqs.execute(compiled);
			if (result.getSize() == 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {

			e.printStackTrace();
			return false;
		}
	}

	public static boolean searchhosp(String hrn) {
		try {
			ResourceSet result;
			String search = "declare default element namespace 'http://www.emr.org';for $hosp in doc('Ins.xml')/*:EMR/*:Hospitals/*:Hospital where $hosp/HospitalRegistrationNumber="
					+ hrn + " return $hosp";
			compiled = xqs.compile(search);
			result = xqs.execute(compiled);
			if (result.getSize() == 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {

			e.printStackTrace();
			return false;
		}

	}

	public static boolean searchpatient(String prn) {
		try {
			ResourceSet result;
			String search = "declare default element namespace 'http://www.emr.org';for $patient in doc('Ins.xml')/EMR/Patients/Patient/PatientDetails where $patient/RegistrationNumber="
					+ prn + " return $patient";
			compiled = xqs.compile(search);
			result = xqs.execute(compiled);
			if (result.getSize() == 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {

			e.printStackTrace();
			return false;
		}

	}

	public static String getPatientDetails(String prn)
	{
		StringBuffer sb = new StringBuffer();
		try
		{
			String q = SearchQueries.getPatientwithVisits(prn);
			compiled = xqs.compile(q);
			ResourceSet res = xqs.execute(compiled);
			
			ResourceIterator itr = res.getIterator();
			while(itr.hasMoreResources())
			{
				sb.append(itr.nextResource().getContent());
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return sb.toString();
	}

	public static String getPatientListforDoc(String drn) 
	{
		StringBuffer sb =new  StringBuffer();
		try {
			String s = SearchQueries.listPatients(drn);
			compiled = xqs.compile(s);
			ResourceSet res = xqs.execute(compiled);
			ResourceIterator itr = res.getIterator();
			while(itr.hasMoreResources())
			{
				sb.append(itr.nextResource().getContent());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return sb.toString();
	}

	public static String getPRN(String name,String phone,String DOB,String id)
	{
		StringBuffer sb = new StringBuffer();
		try
		{
			String tmp = SearchQueries.findDetails(phone, name, id, DOB);
			compiled = xqs.compile(tmp);
			ResourceSet res = xqs.execute(compiled);
			
			
			ResourceIterator itr = res.getIterator();
			while(itr.hasMoreResources())
			{
				sb.append(itr.nextResource().getContent());
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return sb.toString();
	}
	public static boolean addvisit(String prn,String hosp,String doc,String visitDate,String symptoms,String presDate,String medname,String dose,String con,String diag,String testn,String testr)
	{
		ResourceSet result;
		boolean consult=true,diagnose=true;
		try
		{
			String query="declare default element namespace 'http://www.emr.org';"
					+ "update insert <Visit><Hospital>"+hosp+"</Hospital><Doctor>"+doc+"</Doctor><VisitDate>"+visitDate+"</VisitDate></Visit> into doc('Ins.xml')/EMR/Patients/Patient/PatientDetails[RegistrationNumber='"+prn+"']/../Visits";
			compiled= xqs.compile(query);
			result=xqs.execute(compiled);
			if(con!=null && diag!=null )
			{
			 consult=addconsult(prn,symptoms,presDate,medname,dose,visitDate);
			 diagnose=adddiagnose(prn,visitDate,testn,testr);
			
			}
			else if(diag!=null)
			{
				 diagnose=adddiagnose(prn,visitDate,testn,testr);
				
			}
			else
			{
				 consult=addconsult(prn,symptoms,presDate,medname,dose,visitDate);
			}
			return consult&diagnose;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public static boolean addconsult(String prn,String symptoms,String presDate,String medname,String dose,String visdate)
	{
		ResourceSet result;
		try
		{
			String query="declare default element namespace 'http://www.emr.org';update insert"+
						"<VisitDetails><Consultancy><Symptoms>"+symptoms+"</Symptoms>"
					+"<Prescription><PrescriptionDate>"+presDate+"</PrescriptionDate><Medication>"
					+"<MedicineName>"+medname+"</MedicineName><Dosage>"+dose+"</Dosage></Medication>"
					+"</Prescription></Consultancy></VisitDetails> into doc('Ins.xml')/EMR/Patients/Patient/PatientDetails[RegistrationNumber='"+prn+"']/../Visits/Visit[VisitDate='"+visdate+"']";
			compiled= xqs.compile(query);
			result=xqs.execute(compiled);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
		
	}
	public static boolean adddiagnose(String prn,String visitd,String testn,String testr)
	{
		ResourceSet result;
		
		try
		{
			String query="declare default element namespace 'http://www.emr.org';update insert"+
					"<VisitDetails><Diagnostics><TestName>"+testn+"</TestName><TestResults>"+testr+"</TestResults></Diagnostics></VisitDetails>"
					+"into doc('Ins.xml')/EMR/Patients/Patient/PatientDetails[RegistrationNumber='"+prn+"']/../Visits/Visit[VisitDate='"+visitd+"']";
			compiled= xqs.compile(query);
			result=xqs.execute(compiled);
		return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

}

