package org.emr.xsls;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;




public class Converter 
{
	static String inputXSL = "/home/waghi/Desktop/Xsls/xsl1/change.xsl";
	static String listP = "/home/waghi/Desktop/Xsls/xsl2/docSearch.xsl";
    static String searchPRN = "/home/waghi/Desktop/Xsls/xsl3/patient.xsl";
	static String outputHTML = "/home/waghi/Desktop/Xsls/xsl1/out.html";
    static String pdfxsl ="/home/waghi/Desktop/Xsls/pdfXsl/mypdf.xsl";
	
	
	
	public static String convertDetailsToHtml(String dataXML) throws Exception
	{
		
		
		File f = new File("tmp.xml");
		FileOutputStream fos = new FileOutputStream(f);
		byte [] tmp = dataXML.getBytes();
		fos.write(tmp);
		fos.flush();
		fos.close();
		
    	//Transform the xml to html file
    	
        TransformerFactory factory = TransformerFactory.newInstance();
        StreamSource xslStream = new StreamSource(inputXSL);
        Transformer transformer = factory.newTransformer(xslStream);
        StreamSource in = new StreamSource("tmp.xml");
        StreamResult out = new StreamResult(outputHTML);
        transformer.transform(in, out);
        
        //Read from the file 
        
        File file = new File(outputHTML);
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        String str = new String(data, "UTF-8");
        f.delete();
        file.delete();
        return str;
	}
	
	
	
	public static String convertToPDF(String src,String pdf)  throws IOException, FOPException, TransformerException {
        
		
		
		File f = new File("tmp.xml");
		FileOutputStream fos = new FileOutputStream(f);
		byte [] tmp = src.getBytes();
		fos.write(tmp);
		fos.flush();
		fos.close();
		
		// the XSL FO file
        File xsltFile = new File(pdfxsl);
        // the XML file which provides the input
        
        
        StreamSource xmlSource = new StreamSource(f);
        // create an instance of fop factory
        FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
        // a user agent is needed for transformation
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        // Setup output
        OutputStream out;
        out = new java.io.FileOutputStream(pdf);
    
        try {
            // Construct fop with desired output format
        	Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

            // Setup XSLT
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));

            // Resulting SAX events (the generated FO) must be piped through to FOP
            Result res = new SAXResult(fop.getDefaultHandler());

            // Start XSLT transformation and FOP processing
            // That's where the XML is first transformed to XSL-FO and then 
            // PDF is created
            transformer.transform(xmlSource, res);
        } finally {
            out.close();
        }
        f.delete();
        return pdf;
    }
    
	

    public static void main(String[] args)
    {
        try
        {
        
//        	String src = "/home/agarwal/Desktop/Xsls/pdfXsl/tmp.xml";
//        	String tmp = Converter.convertToPDF(src);
//        	System.out.println(new File(tmp).exists());
        }
        catch(Exception ex)
        {
        	ex.printStackTrace();
        }
    
    }

    
    public static String patientList(String dataXML) throws Exception
    {
		File f = new File("tmp.xml");
		FileOutputStream fos = new FileOutputStream(f);
		byte [] tmp = dataXML.getBytes();
		fos.write(tmp);
		fos.flush();
		fos.close();
		
    	//Transform the xml to html file
    	
        TransformerFactory factory = TransformerFactory.newInstance();
        StreamSource xslStream = new StreamSource(listP);
        Transformer transformer = factory.newTransformer(xslStream);
        StreamSource in = new StreamSource("tmp.xml");
        StreamResult out = new StreamResult(outputHTML);
        transformer.transform(in, out);
        
        //Read from the file 
        
        File file = new File(outputHTML);
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        String str = new String(data, "UTF-8");
        f.delete();
        file.delete();
        return str;
    }

    
    public static String searchPRN(String dataXML) throws Exception
    {
		File f = new File("tmp.xml");
		FileOutputStream fos = new FileOutputStream(f);
		byte [] tmp = dataXML.getBytes();
		fos.write(tmp);
		fos.flush();
		fos.close();
		
    	//Transform the xml to html file
    	
        TransformerFactory factory = TransformerFactory.newInstance();
        StreamSource xslStream = new StreamSource(searchPRN);
        Transformer transformer = factory.newTransformer(xslStream);
        StreamSource in = new StreamSource("tmp.xml");
        StreamResult out = new StreamResult(outputHTML);
        transformer.transform(in, out);
        
        //Read from the file 
        
        File file = new File(outputHTML);
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        String str = new String(data, "UTF-8");
        f.delete();
        file.delete();
        return str;
    }
	
    
    
    
}



