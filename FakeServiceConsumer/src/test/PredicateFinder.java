package test;

import info.sswap.api.http.HTTPProvider;
import info.sswap.api.model.DataAccessException;
import info.sswap.api.model.RDG;
import info.sswap.api.model.RIG;
import info.sswap.api.model.RRG;
import info.sswap.api.model.SSWAP;
import info.sswap.api.model.SSWAPGraph;
import info.sswap.api.model.SSWAPObject;
import info.sswap.api.model.SSWAPPredicate;
import info.sswap.api.model.SSWAPProperty;
import info.sswap.api.model.SSWAPResource;
import info.sswap.api.model.SSWAPSubject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

public class PredicateFinder {

	ArrayList<String> ontodata;
	ArrayList<String> rdgdata;

	public void Finder(String url, int people, int bedrooms, int lake_distance,
			String city, int city_distance, String startdate, int S, int E,
			int F) throws ClientProtocolException, IOException {

		OntModel model1;
		String err;



		ArrayList<String> list1 = new ArrayList<String>();
		ArrayList<String> list2 = new ArrayList<String>();

		String SOURCE1 = "http://localhost:8080/FakeServiceConsumer/onto/Cottageinputonontology";
		model1 = ModelFactory
				.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
		model1.read(SOURCE1, "RDF/XML");

		ExtendedIterator<DatatypeProperty> First_onto_data = model1
				.listDatatypeProperties();

		while (First_onto_data.hasNext()) {

			DatatypeProperty prop = (DatatypeProperty) First_onto_data.next();
			String propName = prop.getLocalName().toString();

			list1.add(propName);
		}
//		arr1 = list1.toArray(new String[0]);

		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = null;
		try {
			response = client.execute(httpGet);

		} catch (Exception e) {
			System.out.println("Error executing httpGet: " + e);
		}

		RDG rdg = null;

		try {

			URI uri = new URI(url);
			rdg = SSWAP.getResourceGraph(response.getEntity().getContent(),
					RDG.class, uri);
			// rdg.serialize(System.out);

		} catch (DataAccessException e1) {

			e1.printStackTrace();
		} catch (IllegalStateException e1) {

			e1.printStackTrace();
		} catch (URISyntaxException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		SSWAPResource resource = rdg.getResource();
//		System.out.println("Resource name: " + resource.getName());
//		System.out.println("Resource oneline description: "+ resource.getOneLineDescription());
		SSWAPGraph graph = resource.getGraph();
		SSWAPSubject subject = graph.getSubject();

		Iterator<SSWAPProperty> iterator = subject.getProperties().iterator();
		String x;
		while (iterator.hasNext()) {
			SSWAPProperty property = iterator.next();
			SSWAPPredicate predicate = rdg.getPredicate(property.getURI());
			String str = predicate.getURI().toString();

			if (str.contains("#")) {

				x = str.substring((str.lastIndexOf("#") + 1));
			} else {
				x = str.substring((str.lastIndexOf("/") + 1));

			}

			list2.add(x);

		}
		
//		System.out.println(list2);
//		arr2 = list2.toArray(new String[0]);

		if (list1.size() > 0 && list2.size()> 0) {

			Matching obj = new Matching();
			obj.match(list2, list1);
			ontodata = obj.onto;
			rdgdata = obj.rdg;

		} else {

			err = "No Datatype property found";
			System.out.println(err);
		}
		
		Iterator<SSWAPProperty> iterator2 = subject.getProperties().iterator();
		String y;
		while (iterator2.hasNext()) {
			SSWAPProperty property = iterator2.next();
			SSWAPPredicate predicate = rdg.getPredicate(property.getURI());
			String str = predicate.getURI().toString();

			if (str.contains("#")) {

				y = str.substring((str.lastIndexOf("#") + 1));
			} else {
				y = str.substring((str.lastIndexOf("/") + 1));

			}

			String z = mapped(y);

			if (z.equals("hasBedRooms")) {

				subject.setProperty(predicate, Integer.toString(bedrooms));

			}

			if (z.equals("hasCityDistance")) {

				subject.setProperty(predicate, Integer.toString(city_distance));

			}

			if (z.equals("hasPlaces")) {

				subject.setProperty(predicate, Integer.toString(people));

			}

			if (z.equals("hasFlexi")) {

				subject.setProperty(predicate, Integer.toString(F));

			}

			if (z.equals("hasEndDate")) {

				subject.setProperty(predicate, Integer.toString(E));

			}

			if (z.equals("hasLakeDisatance")) {

				subject.setProperty(predicate, Integer.toString(lake_distance));

			}

			if (z.equals("hasStartDate")) {

				subject.setProperty(predicate, Integer.toString(S));

			}

			if (z.equals("isSituatedIn")) {

				subject.setProperty(predicate, city);

			}

		}

		graph.setSubject(subject);
		resource.setGraph(graph);
		RIG rig = resource.getRDG().getRIG();
		rig.serialize(System.out);

		HTTPProvider.RRGResponse response2 = rig.invoke();
		System.out.println(response2.toString());
		RRG rrg = response2.getRRG();
		rrg.serialize(System.out);

		SSWAPResource resource2 = rrg.getResource();
		SSWAPGraph graph2 = resource2.getGraph();
		SSWAPSubject subject2 = graph2.getSubject();

		Iterator<SSWAPObject> iteratorObjects = subject2.getObjects()
				.iterator();
		while (iteratorObjects.hasNext()) {
			SSWAPObject object = iteratorObjects.next();
			Iterator<SSWAPProperty> iteratorProperties = object.getProperties()
					.iterator();
			while (iteratorProperties.hasNext()) {
				SSWAPProperty property2 = iteratorProperties.next();
				SSWAPPredicate predicate2 = rrg
						.getPredicate(property2.getURI());
				System.out.println("testing RRG predicate =  "
						+ predicate2.getURI());
				String lookupValue = getStrValue(object, predicate2);
				System.out.println("testing value  = " + lookupValue);
			}

		}

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

	private String mapped(String p) {
		String value = null;

		for (int i = 0; i < rdgdata.size(); i++) {

			if (p.equals(rdgdata.get(i))) {

				value = ontodata.get(i);

			}

		}

		return value;

	}

}