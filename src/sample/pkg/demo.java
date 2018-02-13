package sample.pkg;


import javax.xml.transform.TransformerException;

import org.apache.fop.apps.FOPException;
import org.emr.db.DBHandler;
import org.emr.xsls.Converter;

public class demo {

	public static void main(String[] args) 
	{
		try 
		{
			
			String xml = DBHandler.getPatientDetails("98");
			
				Converter.convertToPDF(xml,"/home/waghi/Desktop/temp.pdf");
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		
		}
	}

}
