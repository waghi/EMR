package org.emr.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.emr.db.DBHandler;
import org.emr.xsls.Converter;

/**
 * Servlet implementation class ListPatients
 */
@WebServlet("/ListPatients")
public class ListPatients extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListPatients() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("ListPatients");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String drn=null;
		Map<String,String[]>map=request.getParameterMap();
		for(String str:map.keySet())
		{
			System.out.println(str);
			drn=str;
			
		}
		
		
		
		String xml = DBHandler.getPatientListforDoc(drn);
		String html = null;
		try
		{
			html = Converter.patientList(xml);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		response.getWriter().append(html);
	}

}
