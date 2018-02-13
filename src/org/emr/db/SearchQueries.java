package org.emr.db;

public class SearchQueries 
{
	
	private static String namespace="declare default element namespace 'http://www.emr.org'; ";
		
	public static String getPatientwithVisits(String id)
	{
		String sam = namespace+
				"let $x := doc('Ins.xml') "
				+ "let $d := $x/EMR/Doctors/Doctor "
				+ "let $p := $x/EMR/Patients/Patient "
				+ "let $h := $x/EMR/Hospitals/Hospital "
				+ "for $tmp in $p "
				+ "where $tmp//RegistrationNumber/text() = \""+id+"\""
				+ "return"
				+ "<root>"
				+ "{"
				+ "	<data>"
				+ "	{ $tmp/PatientDetails }"
				+ "	<Visits> { for $v in $tmp//Visit"
				+ "			return"
				+ "			<Visit>"
				+ "			{"
				+ "				<vroot>"
				+ "				{$v//VisitDate}"
				+ "				<Doctor>"
				+ "				{for $w in $d "
				+ "					where $w//DoctorRegistrationNumber=$v//Doctor"
				+ "					return $w//DoctorName/text()"
				+ "				}"
				+ "				</Doctor>"
				+ "				<Hospital>"
				+ "				{for $w in $h "
				+ "					where $w//HospitalRegistrationNumber=$v//Hospital"
				+ "					return $w//Name/text()"
				+ "				}"
				+ "				</Hospital>"
				+ "				{$v//VisitDetails}"
				+ "				</vroot>"
				+ "			}"
				+ "			</Visit>"
				+ "	}</Visits>"
				+ "	</data>"
				+ "}"
				+ "</root>";
		return sam;

	}
	
	public static String findDetails(String phone,String name,String id,String date)
	{
		String tmp = namespace+
				"<data> "
				+ "{let $p := doc('Ins.xml')//Patient"
				+ "				for $t in $p"
				+ "				where $t//Phone='"+phone+"' or contains(lower-case($t//Name/text()),lower-case('"+name+"')) "
						+ "or $t//IdNumber='"+id+"'"
								+ "or matches(string($t//DOB//text()),'"+date+"')"
						+ "				return"
						+ "				<root>"
						+ "				{$t//PatientDetails}"
						+ "				</root>"
						+ "}"
						+ "</data>";

		return tmp;
	}
	
	public static String listPatients(String drn)
	{
		String tmp = namespace+" let $x:= doc('Ins.xml')"
				+ " let $d := $x//Doctor "
				+ "let $p := $x//Patient"
				+ " "
				+ "for $tmp in $d"
				+ " where $tmp//DoctorRegistrationNumber="+drn+""
						+ " return"
						+ "<root>"
						+ "	{$tmp}"
						+ "	<data>"
						+ "	{	for $t in $p"
						+ "		where $t//Doctor = $tmp//DoctorRegistrationNumber"
						+ "		return $t//PatientDetails	}"
						+ "	</data>"
						+ "</root>";
		
		System.out.println(tmp);
		
		return tmp;
	}
	
}
