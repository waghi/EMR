package org.emr.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.emr.db.DBHandler;

/**
 * Servlet implementation class addVisit
 */
@WebServlet("/addVisit")
public class addVisit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addVisit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String hosp=request.getParameter("hrn");
		String doc=request.getParameter("drn");
		String visitd=request.getParameter("date");
		String symp=request.getParameter("symp");
		String presd=request.getParameter("presd");
		String mediname=request.getParameter("mediname");
		String dose=request.getParameter("dose");
		String prn=request.getParameter("prn");
		String testr=request.getParameter("testr");
		String testn=request.getParameter("testn");
		String con=request.getParameter("c");
		String diag=request.getParameter("d");
		con=(con!=null&&con.length()>0)?con:null;
		diag=(diag!=null&&diag.length()>0)?diag:null;
		//System.out.println("abcd");
		if(DBHandler.searchpatient(prn))
		{
			response.getWriter().append("1");
		}
		else if(DBHandler.searchdoc(doc))
		{
			response.getWriter().append("2");
		}
		else if(DBHandler.searchhosp(hosp))
		{
			response.getWriter().append("3");
		}
		else
		{
			boolean op=DBHandler.addvisit(prn,hosp, doc, visitd, symp, presd, mediname, dose,con,diag,testn,testr);
			if(op)
			response.getWriter().append("0");
			else
				response.getWriter().append("-1");
		}
			
		
		
				
		
	}

}
