package org.emr.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.emr.db.DBHandler;
/**
 * Servlet implementation class addDoctor
 */
@WebServlet("/addDoctor")
public class addDoctor extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addDoctor() {
        super();
        // TODO Auto-generated constructor stub
    }

	
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {    	
    	System.out.println("addDoctor");
    }
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("DO POST");
		String drn = request.getParameter("drn");
		String dname = request.getParameter("dname");
		String ds = request.getParameter("ds");
//		String day = request.getParameter("day");
//		String time = request.getParameter("time");
		Boolean op = DBHandler.putDoctor(drn, dname, ds);
		response.getWriter().append(op.toString());
	}

}
