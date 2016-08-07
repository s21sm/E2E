package servicepoint;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;

import info.sswap.api.model.RIG;
import info.sswap.api.model.SSWAPGraph;
import info.sswap.api.model.SSWAPObject;
import info.sswap.api.model.SSWAPPredicate;
import info.sswap.api.model.SSWAPProperty;
import info.sswap.api.model.SSWAPResource;
import info.sswap.api.model.SSWAPSubject;
import info.sswap.api.servlet.MapsTo;

public class SSWAPService extends MapsTo {
	RIG rigGraph = null;
	int bed;
	int place;
	int city_distance;
	int lake_distance;
	int S;
	int E;
	int flex;
	String city;
	String error = null;
	int randomNum;

	int numberofresult;
	String customer;
	String cottage_name;
	String cottage_url;
	String AmountOfBeds;
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

	@Override
	protected void initializeRequest(RIG rig) {

		rigGraph = rig;

		SSWAPResource resource = rigGraph.getResource();
		SSWAPGraph graph = resource.getGraph();
		SSWAPSubject subject = graph.getSubject();

		Iterator<SSWAPProperty> iterator = subject.getProperties().iterator();
		while (iterator.hasNext()) {
			SSWAPProperty property = iterator.next();
			SSWAPPredicate predicate = rigGraph.getPredicate(property.getURI());

			if (predicate
					.getURI()
					.toString()
					.equals("http://localhost:8080/FakeService/onto/Cottageinputonontology/CustomerName")) {
				customer = property.getValue().asString();

			}

			if (predicate
					.getURI()
					.toString()
					.equals("http://localhost:8080/FakeService/onto/Cottageinputonontology/hasBed")) {
				bed = Integer.parseInt(property.getValue().asString(), 10);

			}

			if (predicate
					.getURI()
					.toString()
					.equals("http://localhost:8080/FakeService/onto/Cottageinputonontology/hasPlaces")) {
				place = Integer.parseInt(property.getValue().asString(), 10);

			}
			if (predicate
					.getURI()
					.toString()
					.equals("http://localhost:8080/FakeService/onto/Cottageinputonontology/hasFlexi")) {
				flex = Integer.parseInt(property.getValue().asString(), 10);

			}

			if (predicate
					.getURI()
					.toString()
					.equals("http://localhost:8080/FakeService/onto/Cottageinputonontology/hasCityDistance")) {
				city_distance = Integer.parseInt(
						property.getValue().asString(), 10);

			}

			if (predicate
					.getURI()
					.toString()
					.equals("http://localhost:8080/FakeService/onto/Cottageinputonontology/hasLakeDisatance")) {
				lake_distance = Integer.parseInt(
						property.getValue().asString(), 10);

			}

			if (predicate
					.getURI()
					.toString()
					.equals("http://localhost:8080/FakeService/onto/Cottageinputonontology/isSituatedIn")) {
				city = property.getValue().asString();

			}

			if (predicate
					.getURI()
					.toString()
					.equals("http://localhost:8080/FakeService/onto/Cottageinputonontology/StartDate")) {
				S = Integer.parseInt(property.getValue().asString(), 10);

			}

			if (predicate
					.getURI()
					.toString()
					.equals("http://localhost:8080/FakeService/onto/Cottageinputonontology/EndDate")) {
				E = Integer.parseInt(property.getValue().asString(), 10);

			}

		}

		// start of processing

		QueryStringBuilder obj = new QueryStringBuilder();
		obj.StringBuilder(city, place, bed, city_distance, lake_distance, S, E,
				flex);
		Query query = obj.query;
		OntModel model1 = obj.model1;

		int i = 0;
		if (query.isSelectType()) {

			QueryExecution qe = QueryExecutionFactory.create(query, model1);
			com.hp.hpl.jena.query.ResultSet results = qe.execSelect();

			if (!results.hasNext()) {

				System.out
						.println("No Result found According your search, kindly change input parameter");

			}

			else {

				int max = 10000000;
				int min = 5000000;
				Random rand = new Random();
				randomNum = rand.nextInt((max - min) + 1) + min;

				while (results.hasNext()) {

					i = i + 1;

					// ResultSetFormatter.out(System.out, results, query);
					QuerySolution row = results.nextSolution();
					String x = row.getLiteral("start").getString();
					String y = row.getLiteral("end").getString();
					int start = Integer.parseInt(x);
					int end = Integer.parseInt(y);

					BookingDateCalculation obj8 = new BookingDateCalculation();
					obj8.cal(flex, start, end, S, E);

					BOOKING = obj8.bookingPeriod;
					BOOKINGDATE.add(BOOKING);

					cottage_name = row.getLiteral("Name").getString();
					CTGNAME.add(cottage_name);

					cottage_url = row.getLiteral("url").getString();
					WEB.add(cottage_url);

					AmountOfBeds = row.getLiteral("AmountOfBeds").getString();
					BED.add(AmountOfBeds);

					AmountOfPeople = row.getLiteral("people").getString();
					PEOPLE.add(AmountOfPeople);

					Address = row.getLiteral("address").getString();
					ADDRESS.add(Address);

					CityName = row.getLiteral("cityname").getString();
					CITY.add(CityName);

					CityDistance = row.getLiteral("city_distance").getString();
					CDISTANCE.add(CityDistance);

					LakeDistance = row.getLiteral("lake_distance").getString();
					LDISTANCE.add(LakeDistance);

				}

			}

			qe.close();

		}

		numberofresult = i;

	}

	@Override
	protected void mapsTo(SSWAPSubject translatedSubject) throws Exception {

		SSWAPSubject subject = translatedSubject;
		SSWAPObject object = translatedSubject.getObject();
		SSWAPObject sswapObject = null;

		if (numberofresult > 0) {

			for (int i = 0; i < CTGNAME.size(); i++) {

				sswapObject = assignObject(subject);

				Iterator<SSWAPProperty> iterator = object.getProperties()
						.iterator();

				while (iterator.hasNext()) {
					SSWAPProperty property = iterator.next();
					SSWAPPredicate predicate = rigGraph.getPredicate(property
							.getURI());

					if (predicate
							.getURI()
							.toString()
							.equals("http://localhost:8080/FakeService/onto/Cottageoutputonontology/Customer")) {

						if (i == 0) {

							object.setProperty(predicate, customer);

						}

					}

					if (predicate
							.getURI()
							.toString()
							.equals("http://localhost:8080/FakeService/onto/Cottageoutputonontology/hasBookingNumber")) {

						if (i == 0) {

							object.setProperty(predicate,
									Integer.toString(randomNum));

						}

					}

					if (predicate
							.getURI()
							.toString()
							.equals("http://localhost:8080/FakeService/onto/Cottageoutputonontology/isSituatedIn")) {

						if (i == 0) {

							object.setProperty(predicate, CITY.get(i));

						}
						if (i > 0) {

							sswapObject.addProperty(predicate, CITY.get(i));

						}

					}

					if (predicate
							.getURI()
							.toString()
							.equals("http://localhost:8080/FakeService/onto/Cottageoutputonontology/hasBedRooms")) {

						if (i == 0) {

							object.setProperty(predicate, BED.get(i));

						}
						if (i > 0) {

							sswapObject.addProperty(predicate, BED.get(i));

						}

					}

					if (predicate
							.getURI()
							.toString()
							.equals("http://localhost:8080/FakeService/onto/Cottageoutputonontology/hasPlaces")) {

						if (i == 0) {

							object.setProperty(predicate, PEOPLE.get(i));

						}
						if (i > 0) {

							sswapObject.addProperty(predicate, PEOPLE.get(i));

						}

					}

					if (predicate
							.getURI()
							.toString()
							.equals("http://localhost:8080/FakeService/onto/Cottageoutputonontology/hasCityDistance")) {

						if (i == 0) {

							object.setProperty(predicate, CDISTANCE.get(i));

						}
						if (i > 0) {

							sswapObject
									.addProperty(predicate, CDISTANCE.get(i));

						}

					}

					if (predicate
							.getURI()
							.toString()
							.equals("http://localhost:8080/FakeService/onto/Cottageoutputonontology/hasLakeDisatance")) {

						if (i == 0) {

							object.setProperty(predicate, LDISTANCE.get(i));

						}
						if (i > 0) {

							sswapObject
									.addProperty(predicate, LDISTANCE.get(i));
						}

					}

					if (predicate
							.getURI()
							.toString()
							.equals("http://localhost:8080/FakeService/onto/Cottageoutputonontology/hasBookingDate")) {

						if (i == 0) {
							object.setProperty(predicate, BOOKINGDATE.get(i));
						}
						if (i > 0) {
							sswapObject.addProperty(predicate,
									BOOKINGDATE.get(i));
						}

					}

					if (predicate
							.getURI()
							.toString()
							.equals("http://localhost:8080/FakeService/onto/Cottageoutputonontology/HASURL")) {

						if (i == 0) {
							object.setProperty(predicate, WEB.get(i));
						}
						if (i > 0) {

							sswapObject.addProperty(predicate, WEB.get(i));
						}

					}
					if (predicate
							.getURI()
							.toString()
							.equals("http://localhost:8080/FakeService/onto/Cottageoutputonontology/CottageAddress")) {

						if (i == 0) {

							object.setProperty(predicate, ADDRESS.get(i));
						}
						if (i > 0) {

							sswapObject.addProperty(predicate, ADDRESS.get(i));
						}

					}

					if ((predicate.getURI().toString()
							.equals("http://localhost:8080/FakeService/onto/Cottageoutputonontology/hasCottageName"))) {

						if (i == 0) {

							object.setProperty(predicate, CTGNAME.get(i));
						}
						if (i > 0) {

							sswapObject.addProperty(predicate, CTGNAME.get(i));
						}

					}

					if ((predicate.getURI().toString()
							.equals("http://localhost:8080/FakeService/onto/Cottageoutputonontology/hasBookingNumber"))) {
						sswapObject.addProperty(predicate,
								Integer.toString(randomNum));

					}

				}
				subject.addObject(sswapObject);

			}

		}

	}
}
