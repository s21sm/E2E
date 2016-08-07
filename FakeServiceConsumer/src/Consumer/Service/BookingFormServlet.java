package Consumer.Service;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("unchecked")
@WebServlet(name = "Servlet", urlPatterns = { "/result" })
public class BookingFormServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String username = request.getParameter("name");
		int people = Integer.parseInt(request.getParameter("people"), 10);
		int bedrooms = Integer.parseInt(request.getParameter("bedrooms"), 10);
		int lake_distance = Integer.parseInt(request.getParameter("lake_distance"), 10);
		String city = request.getParameter("city");
		int city_distance = Integer.parseInt(request.getParameter("city_disance"), 10);
		String startdate = request.getParameter("startdate");
		int days = Integer.parseInt(request.getParameter("days"), 10);
		int flex = Integer.parseInt(request.getParameter("flex"), 10);		
		String url = (String)request.getSession().getAttribute("url");
		String ontotype = (String)request.getSession().getAttribute("ontotype");
		
		bookingdates obju = new bookingdates();
		obju.datecreator(startdate, days);
		int S = obju.StartDate;
		int E = obju.StartDate + days - 1;
		

		ArrayList<String> rdg = (ArrayList<String>) request.getSession()
				.getAttribute("RDG");
		
		ArrayList<String> onto = (ArrayList<String>) request.getSession()
				.getAttribute("ONTO");
		
		
		ArrayList<String> output = (ArrayList<String>) request.getSession()
				.getAttribute("output");
		
		
		ArrayList<String> fullOutonto = new ArrayList<String>();
		ArrayList<String> fullOutrdg = new ArrayList<String>();
		
		operation obj = new operation();
		obj.Finder (username, url,  people, bedrooms, lake_distance, city, city_distance, startdate, S, E, flex, rdg, onto, output, ontotype);
				
		fullOutonto.addAll(obj.out_mtch_onto);
		fullOutonto.addAll(obj.out_unmtch_onto);
		fullOutrdg.addAll(obj.out_mtch_rdg);
		fullOutrdg.addAll(obj.out_unmtch_rdg);
				
		request.setAttribute("mtch_onto", obj.out_mtch_onto);
		request.setAttribute("mtch_rdg", obj.out_mtch_rdg);
		request.setAttribute("unmtch_onto", obj.out_unmtch_onto);
		request.setAttribute("unmtch_rdg", obj.out_unmtch_rdg);
		request.setAttribute("FON", fullOutonto);
		request.setAttribute("FRDG", fullOutrdg);
		request.setAttribute("lvl", obj.rat);
		
		request.getSession().setAttribute("outname", obj.pr);		
		request.getSession().setAttribute("UNMRDG", obj.out_unmtch_rdg );
		request.getSession().setAttribute("MATCHEDONTO", obj.out_mtch_onto);
		request.getSession().setAttribute("MATCHEDRDG", obj.out_mtch_rdg);		
		request.getSession().setAttribute("Rrg", obj.rrg);
			
		
		RequestDispatcher rd = request.getRequestDispatcher("/outputalignment.jsp");
		rd.forward(request, response);
		
//		obj.out_mtch_onto.clear();
//		obj.out_mtch_rdg.clear();
//		obj.out_unmtch_onto.clear();
//		obj.out_unmtch_rdg.clear();
//		fullOutonto.clear();
//		fullOutrdg.clear();
//		obj.rat.clear();
		
		
		
	}

}
