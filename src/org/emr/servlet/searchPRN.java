package org.emr.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.emr.db.DBHandler;
import org.emr.xsls.Converter;

/**
 * Servlet implementation class searchPRN
 */
@WebServlet("/searchPRN")
public class searchPRN extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public searchPRN() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("pname");
		String phone = request.getParameter("pn");
		String idnum = request.getParameter("idnum");
		String dob = request.getParameter("dob");
		name=(name!=null&&name.length()>0)?name:null;
		phone=(phone!=null&&phone.length()>0)?phone:null;
		idnum=(idnum!=null&&idnum.length()>0)?idnum:null;
		dob=(dob!=null&&dob.length()>0)?dob:null;
		
		
		System.out.println(name+" "+phone+" "+idnum+" "+dob);
		
		try
		{
			String tmp = DBHandler.getPRN(name, phone, dob, idnum);
			
//			System.out.println(tmp);
			
			
			String op = Converter.searchPRN(tmp);

//			System.out.println(op);
			
			response.getWriter().append(op);
		}
		catch(Exception ex)
		{
			
		}
		
		
		
		
		
		
		
	}

}
