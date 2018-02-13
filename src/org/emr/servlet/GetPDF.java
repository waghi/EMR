package org.emr.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerException;

import org.apache.fop.apps.FOPException;
import org.emr.db.DBHandler;
import org.emr.xsls.Converter;

/**
 * Servlet implementation class GetPDF
 */
@WebServlet("/GetPDF")
public class GetPDF extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetPDF() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		doPost(request, response);
		System.out.println("inside do get");
		String prn =request.getParameter("prn");
		String xml=DBHandler.getPatientDetails("98");
		try {
			Converter.convertToPDF(xml,"/home/waghi/workspace/EMRApp/WebContent/temp.pdf");
			} catch (FOPException | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		performTask(request, response);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String prn =request.getParameter("prn");
		String xml=DBHandler.getPatientDetails("98");
		try {
			Converter.convertToPDF(xml,"/home/waghi/Music/apache-tomcat-8.5.13/wtpwebapps/EMRApp/temp.pdf");
			} catch (FOPException | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void performTask(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	IOException {

String pdfFileName = "temp.pdf";
String contextPath = getServletContext().getRealPath(File.separator);
System.out.println(contextPath);
File pdfFile = new File(contextPath + pdfFileName);

response.setContentType("application/pdf");
response.addHeader("Content-Disposition", "attachment; filename=" + pdfFileName);
response.setContentLength((int) pdfFile.length());

FileInputStream fileInputStream = new FileInputStream(pdfFile);
OutputStream responseOutputStream = response.getOutputStream();
int bytes;
while ((bytes = fileInputStream.read()) != -1) {
	responseOutputStream.write(bytes);
}

}


}
