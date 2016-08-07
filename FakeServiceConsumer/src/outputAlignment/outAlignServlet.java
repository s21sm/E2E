package outputAlignment;

import info.sswap.api.model.RRG;
import info.sswap.api.model.SSWAPGraph;
import info.sswap.api.model.SSWAPObject;
import info.sswap.api.model.SSWAPPredicate;
import info.sswap.api.model.SSWAPProperty;
import info.sswap.api.model.SSWAPResource;
import info.sswap.api.model.SSWAPSubject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "OutalignServlet", urlPatterns = { "/out" })
public class outAlignServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	ArrayList<String> list;
	ArrayList<String> rdg;
	ArrayList<String> onto;

	String name;
	String number;
	String cottage_name;
	String cottage_url;
	String bed;
	String AmountOfPeople;
	String Address;
	String CityName;
	String CityDistance;
	String LakeDistance;
	String BOOKING;

	ArrayList<String> CTGNAME = new ArrayList<String>();
	ArrayList<String> CITY = new ArrayList<String>();
	ArrayList<String> LDISTANCE = new ArrayList<String>();
	ArrayList<String> CDISTANCE = new ArrayList<String>();
	ArrayList<String> ADDRESS = new ArrayList<String>();
	ArrayList<String> WEB = new ArrayList<String>();
	ArrayList<String> BOOKINGDATE = new ArrayList<String>();
	ArrayList<String> BED = new ArrayList<String>();
	ArrayList<String> PEOPLE = new ArrayList<String>();

	@SuppressWarnings("unchecked")
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		list = (ArrayList<String>) request.getSession().getAttribute("UNMRDG");

		rdg = (ArrayList<String>) request.getSession().getAttribute(
				"MATCHEDRDG");
		onto = (ArrayList<String>) request.getSession().getAttribute(
				"MATCHEDONTO");

		String outname = (String) request.getSession().getAttribute("outname");

		for (int i = 0; i < list.size(); i++) {

			String str = request.getParameter(list.get(i));
			if (!str.equals("SEL")) {
				rdg.add(list.get(i));
				onto.add(str);
			}

		}

		for (int i = 0; i < rdg.size(); i++) {

			String str = request.getParameter(rdg.get(i));
			if (!str.equals("SEL")) {

				rdg.set(i, rdg.get(i));
				onto.set(i, str);
			}

		}

		ArrayList<String> csv = new ArrayList<String>();

		for (int i = 0; i < rdg.size(); i++) {

			String st = onto.get(i) + "," + rdg.get(i);
			csv.add(st);

		}

//		File filename = new File("D:\\EF\\" + outname);
//
//		try {
//			FileWriter fw = new FileWriter(filename);
//
//			Writer output = new BufferedWriter(fw);
//			for (int i = 0; i < csv.size(); i++) {
//
//				output.write(csv.get(i).toString() + "\n");
//			}
//			output.close();
//		} catch (Exception e) {
//			JOptionPane.showMessageDialog(null, "can not write");
//		}

		File path = new File("alignment");
//		path.mkdir();
		try {
			File file = new File(path, outname);
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
		
		
		
		
		RRG rrG = (RRG) request.getSession().getAttribute("Rrg");
		SSWAPResource resource2 = rrG.getResource();
		SSWAPGraph graph2 = resource2.getGraph();
		SSWAPSubject subject2 = graph2.getSubject();

		String p;
		Iterator<SSWAPObject> iteratorObjects = subject2.getObjects()
				.iterator();
		while (iteratorObjects.hasNext()) {
			SSWAPObject object = iteratorObjects.next();
			Iterator<SSWAPProperty> iteratorProperties = object.getProperties()
					.iterator();
			while (iteratorProperties.hasNext()) {
				SSWAPProperty property2 = iteratorProperties.next();
				SSWAPPredicate predicate2 = rrG
						.getPredicate(property2.getURI());

				String str = predicate2.getURI().toString();

				if (str.contains("#")) {

					p = str.substring((str.lastIndexOf("#") + 1));
				} else {

					p = str.substring((str.lastIndexOf("/") + 1));

				}

				String z = mapped(p);

				if (z.equals("CustomerName")) {

					name = getStrValue(object, predicate2);
				}

				if (z.equals("hasBookingNumber")) {

					number = getStrValue(object, predicate2);
				}

				if (z.equals("hasBedRooms")) {

					bed = getStrValue(object, predicate2);
					BED.add(bed);
				}

				if (z.equals("hasCityDistance")) {

					CityDistance = getStrValue(object, predicate2);
					CDISTANCE.add(CityDistance);
				}

				if (z.equals("hasPlaces")) {

					AmountOfPeople = getStrValue(object, predicate2);
					PEOPLE.add(AmountOfPeople);
				}

				if (z.equals("hasLakeDisatance")) {

					LakeDistance = getStrValue(object, predicate2);
					LDISTANCE.add(LakeDistance);
				}

				if (z.equals("isSituatedIn")) {

					CityName = getStrValue(object, predicate2);
					CITY.add(CityName);

				}

				if (z.equals("hasCottageName")) {

					cottage_name = getStrValue(object, predicate2);
					CTGNAME.add(cottage_name);
				}

				if (z.equals("hasAddress")) {

					Address = getStrValue(object, predicate2);
					ADDRESS.add(Address);
				}

				if (z.equals("hasBookingDate")) {

					BOOKING = getStrValue(object, predicate2);
					BOOKINGDATE.add(BOOKING);

				}

				if (z.equals("hasurl")) {

					cottage_url = getStrValue(object, predicate2);
					WEB.add(cottage_url);

				}

			}

		}

		int resultflag = 1;
		if (CTGNAME.get(0).trim().isEmpty()) {

			resultflag = 0;
		} else {
			resultflag = 1;
			
		}

		
		request.setAttribute("flag", resultflag);
		
		request.setAttribute("customerName", name);
		request.setAttribute("BookingNumber", number);	
		request.setAttribute("cottageName", CTGNAME);
		request.setAttribute("cottageAddress", ADDRESS);
		request.setAttribute("cottageCity", CITY);
		request.setAttribute("cottageUrl", WEB);
		request.setAttribute("cottageBed", BED);
		request.setAttribute("cottagePeople", PEOPLE);
		request.setAttribute("cottageCityDistance", CDISTANCE);
		request.setAttribute("cottageLakeDistance", LDISTANCE);
		request.setAttribute("cottageBookingdate", BOOKINGDATE);
		
		RequestDispatcher rd = request.getRequestDispatcher("/FinalResult.jsp");
		rd.forward(request, response);

		CTGNAME.clear();
		ADDRESS.clear();
		CITY.clear();
		WEB.clear();
		BED.clear();
		PEOPLE.clear();
		CDISTANCE.clear();
		LDISTANCE.clear();
		BOOKINGDATE.clear();

	}

	private String mapped(String p) {
		String value = null;

		for (int i = 0; i < rdg.size(); i++) {

			if (p.equals(rdg.get(i))) {

				value = onto.get(i);

			}

		}

		return value;

	}

	private String getStrValue(SSWAPObject object, SSWAPPredicate predicate2) {
		String value = null;
		SSWAPProperty sswapProperty = object.getProperty(predicate2);

		if (sswapProperty != null) {

			value = sswapProperty.getValue().asString();

			if (value.isEmpty()) {
				value = null;
			}
		}

		return value;

	}

}
