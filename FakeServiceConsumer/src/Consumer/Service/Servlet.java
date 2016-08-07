package Consumer.Service;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Alignment.mtch;

public class Servlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ArrayList<String> fullonto = new ArrayList<String>();
		ArrayList<String> fullrdg = new ArrayList<String>();

		String str = request.getParameter("url");
		String Service_url = str.replaceAll("\\s", "");
		String ontotype = request.getParameter("altype");  
		mtch obj = new mtch();
		obj.Finder(Service_url, ontotype);

		fullonto.addAll(obj.ontodata);
		fullonto.addAll(obj.unmtch_onto);
		fullrdg.addAll(obj.rdgdata);
		fullrdg.addAll(obj.unmtch_rdg);

		request.setAttribute("fullonto", fullonto);
		request.setAttribute("fullrdg", fullrdg);

		request.setAttribute("onto", obj.ontodata);
		request.setAttribute("rdg", obj.rdgdata);
		request.setAttribute("un_onto", obj.unmtch_onto);
		request.setAttribute("un_rdg", obj.unmtch_rdg);
		request.setAttribute("level", obj.Rating);


		request.getSession().setAttribute("fullonto", fullonto);
		request.getSession().setAttribute("fullrdg", fullonto);

		request.getSession().setAttribute("url", Service_url);
		request.getSession().setAttribute("name", obj.pre);
		request.getSession().setAttribute("ontotype", ontotype);

		request.getSession().setAttribute("un_rdg", obj.unmtch_rdg);
		request.getSession().setAttribute("onto", obj.ontodata);
		request.getSession().setAttribute("rdg", obj.rdgdata);

		request.getSession().setAttribute("output", obj.output);

		RequestDispatcher rd = request.getRequestDispatcher("/alignment.jsp");
		rd.forward(request, response);

	}
}