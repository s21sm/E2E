package Alignment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "alignServlet", urlPatterns = { "/res" })
public class alignServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ArrayList<String> list = (ArrayList<String>) request.getSession()
				.getAttribute("un_rdg");

		ArrayList<String> rdg = (ArrayList<String>) request.getSession()
				.getAttribute("rdg");
		ArrayList<String> onto = (ArrayList<String>) request.getSession()
				.getAttribute("onto");

		String name = (String) request.getSession().getAttribute("name");

		for (int i = 0; i < rdg.size(); i++) {

			String str = request.getParameter(rdg.get(i));
			if (!str.equals("SEL")) {

				rdg.set(i, rdg.get(i));
				onto.set(i, str);
			}

		}

		for (int i = 0; i < list.size(); i++) {

			String str = request.getParameter(list.get(i));
			if (!str.equals("SEL")) {
				rdg.add(list.get(i));
				onto.add(str);
			}

		}

		request.getSession().setAttribute("RDG", rdg);
		request.getSession().setAttribute("ONTO", onto);

		ArrayList<String> csv = new ArrayList<String>();

		for (int i = 0; i < rdg.size(); i++) {

			String st = onto.get(i) + "," + rdg.get(i);
			csv.add(st);

		}
		// File file = new File("D:\\EE\\" + name);
//		File path = new File("alignment");
//		path.mkdir();
		try {
			File file = new File("alignment", name);
			FileOutputStream fop = new FileOutputStream(file);
			PrintWriter output = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(fop, "UTF-8")), true);

			for (int i = 0; i < csv.size(); i++) {

				output.write(csv.get(i).toString() + "\n");
			}
			output.flush();
			output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		
		RequestDispatcher rd = request.getRequestDispatcher("/BookingForm.jsp");
		rd.forward(request, response);

	}

}
