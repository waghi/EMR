package org.emr.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.emr.db.DBHandler;

/**
 * Servlet implementation class addPatient
 */
@WebServlet("/addPatient")
public class addPatient extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addPatient() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("add Patient");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String pname=request.getParameter("pname");
		String dob=request.getParameter("dob");
		String padd=request.getParameter("padd");
		String pn=request.getParameter("pn");
		String gidname=request.getParameter("gidname");
		String gidnum=request.getParameter("gidnum");
		String gen=request.getParameter("gen");
		String gdname=request.getParameter("gdname");
		String prn=System.currentTimeMillis()+"";
		Boolean op=DBHandler.putPatient(prn, pname, dob, gen, pn, padd, gidname, gidnum, gdname);
		response.getWriter().append(op.toString());
		
	}

}
