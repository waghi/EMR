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
 * Servlet implementation class ShowDetails
 */
@WebServlet("/ShowDetails")
public class ShowDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowDetails() 
    {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("ShowDetails");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String prn=null;
		Map<String,String[]>map=request.getParameterMap();
		for(String str:map.keySet())
		{
			System.out.println(str);
			prn=str;
			
		}
				
		System.out.println(prn);
		String xml = DBHandler.getPatientDetails(prn);
		String tmp = null;
		
		try {
			System.out.println(xml);
			tmp = Converter.convertDetailsToHtml(xml);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.getWriter().append(tmp);
		
		
	}

}
