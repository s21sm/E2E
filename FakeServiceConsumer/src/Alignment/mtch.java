package Alignment;

import info.sswap.api.model.DataAccessException;
import info.sswap.api.model.RDG;
import info.sswap.api.model.SSWAP;
import info.sswap.api.model.SSWAPGraph;
import info.sswap.api.model.SSWAPPredicate;
import info.sswap.api.model.SSWAPProperty;
import info.sswap.api.model.SSWAPResource;
import info.sswap.api.model.SSWAPSubject;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
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

import csv.csvReader;
import apiAlignment.api;

public class mtch {

	public String pre;
	public ArrayList<String> ontodata;
	public ArrayList<String> rdgdata;
	public ArrayList<String> unmtch_onto = new ArrayList<String>();
	public ArrayList<String> unmtch_rdg = new ArrayList<String>();
	public ArrayList<String> output = new ArrayList<String>();
	public ArrayList<Float> Rating = new ArrayList<Float>();
	String prefix;

	public void Finder(String url, String ontotype)
			throws ClientProtocolException, IOException {

		OntModel model1;
		OntModel model2;
		String err;

		ArrayList<String> list1 = new ArrayList<String>();
		ArrayList<String> list2 = new ArrayList<String>();

		String SOURCE1 = "http://localhost:8080/FakeServiceConsumer/onto/Cottageinputonontology";
		model1 = ModelFactory
				.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
		model1.read(SOURCE1, "RDF/XML");

		String SOURCE2 = "http://localhost:8080/FakeServiceConsumer/onto/Cottageoutputonontology";
		model2 = ModelFactory
				.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
		model2.read(SOURCE2, "RDF/XML");

		ExtendedIterator<DatatypeProperty> First_onto_data = model1
				.listDatatypeProperties();

		ExtendedIterator<DatatypeProperty> Second_onto_data = model2
				.listDatatypeProperties();

		while (First_onto_data.hasNext()) {

			DatatypeProperty prop = (DatatypeProperty) First_onto_data.next();
			String propName = prop.getLocalName().toString();

			list1.add(propName);
		}

		while (Second_onto_data.hasNext()) {

			DatatypeProperty prop = (DatatypeProperty) Second_onto_data.next();
			String propName = prop.getLocalName().toString();

			output.add(propName);
		}

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
		SSWAPGraph graph = resource.getGraph();
		SSWAPSubject subject = graph.getSubject();

		Iterator<SSWAPProperty> iterator = subject.getProperties().iterator();
		String x;

		while (iterator.hasNext()) {
			SSWAPProperty property = iterator.next();
			SSWAPPredicate predicate = rdg.getPredicate(property.getURI());
			String str = predicate.getURI().toString();

			if (str.contains("#")) {

				prefix = str.substring(0, str.lastIndexOf("#"));
				x = str.substring((str.lastIndexOf("#") + 1));
			} else {

				prefix = str.substring(0, str.lastIndexOf("/"));
				x = str.substring((str.lastIndexOf("/") + 1));

			}

			list2.add(x);

		}

		pre = URLEncoder.encode(prefix, "UTF-8");
		File[] listOfFiles = null;
		File filepath = new File("alignment");
		System.out.println(filepath.getAbsolutePath());
		if (filepath.exists()) {
			listOfFiles = filepath.listFiles();
		} else {
			filepath.mkdir();
			listOfFiles = filepath.listFiles();
		}

		if (listOfFiles.length > 0) {

			for (int i = 0; i < listOfFiles.length; i++) {

				for (File file : listOfFiles) {
					if (file.isFile()) {
						String fname = file.getName();
						String path = file.getAbsolutePath();	
									
						if (fname.equals(pre)) {
							csvReader obj = new csvReader();
							obj.run(path);
							ontodata = obj.csvonto;
							rdgdata = obj.csvrdg;
							unmtch_onto.clear();
							unmtch_rdg.clear();
							Rating.clear();
							
							break;

						} else {

							if (ontotype.equals("our")) {

								if (list1.size() > 0 && list2.size() > 0) {

									algnmnt obj = new algnmnt();
									obj.match(list2, list1);
									ontodata = obj.onto;
									rdgdata = obj.rdg;
									unmtch_onto = obj.unmatched_onto;
									unmtch_rdg = obj.unmatched_rdg;
									Rating = obj.rating;

								} else {

									err = "No Datatype property found 2";
									System.out.println(err);
								}

							}

							if (ontotype.equals("api")) {

								api ob = new api();
								ob.alignment(prefix, SOURCE1);
								ontodata = ob.ontodata;
								rdgdata = ob.rdgdata;
								unmtch_onto = ob.unmtch_onto;
								unmtch_rdg = ob.unmtch_rdg;
								Rating = ob.rating;

							}

						}

					}
				}
			}
		} else {

			if (ontotype.equals("our")) {

				if (list1.size() > 0 && list2.size() > 0) {

					algnmnt obj = new algnmnt();
					obj.match(list2, list1);
					ontodata = obj.onto;
					rdgdata = obj.rdg;
					unmtch_onto = obj.unmatched_onto;
					unmtch_rdg = obj.unmatched_rdg;
					Rating = obj.rating;

				} else {

					err = "No Datatype property found 2";
					System.out.println(err);
				}

			}

			if (ontotype.equals("api")) {

				api ob = new api();
				ob.alignment(prefix, SOURCE1);
				ontodata = ob.ontodata;
				rdgdata = ob.rdgdata;
				unmtch_onto = ob.unmtch_onto;
				unmtch_rdg = ob.unmtch_rdg;
				Rating = ob.rating;

			}

		}

	}
}