package Consumer.Service;

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

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import apiAlignment.api;
import csv.csvReader;
import Alignment.algnmnt;

public class operation {

	public ArrayList<String> out_mtch_rdg;
	public ArrayList<String> out_mtch_onto;
	public ArrayList<String> out_unmtch_rdg = new ArrayList<String>();
	public ArrayList<String> out_unmtch_onto = new ArrayList<String>();
	public ArrayList<Float> rat = new ArrayList<Float>();
	public RRG rrg;
	public String pr;
	String prefix;
	String out_onto_url = "http://localhost:8080/FakeServiceConsumer/onto/Cottageoutputonontology";

	ArrayList<String> ontodata;
	ArrayList<String> rdgdata;

	public void Finder(String username, String url, int people, int bedrooms,
			int lake_distance, String city, int city_distance,
			String startdate, int S, int E, int F, ArrayList<String> Rdg,
			ArrayList<String> Onto, ArrayList<String> output, String ontotype)
			throws ClientProtocolException, IOException {

		ontodata = Onto;
		rdgdata = Rdg;
		String err;


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

			if (z.equals("Customer")) {

				subject.setProperty(predicate, username);

			}

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
		rrg = response2.getRRG();
		 rrg.serialize(System.out);

		SSWAPResource resource2 = rrg.getResource();
		SSWAPGraph graph2 = resource2.getGraph();
		SSWAPSubject subject2 = graph2.getSubject();

		String p;
		ArrayList<String> arr2 = new ArrayList<String>();
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

				String str = predicate2.getURI().toString();

				if (str.contains("#")) {
					prefix = str.substring(0, str.lastIndexOf("#"));
					p = str.substring((str.lastIndexOf("#") + 1));
				} else {
					prefix = str.substring(0, str.lastIndexOf("/"));
					p = str.substring((str.lastIndexOf("/") + 1));

				}

				arr2.add(p);

			}

		}


		HashSet<String> set = new HashSet<>(arr2);
		ArrayList<String> result = new ArrayList<>(set);

		pr = URLEncoder.encode(prefix, "UTF-8");
		File[] listOfFiles =null;
		File filepath = new File("alignment");

		
		
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

						if (fname.equals(pr)) {
							
							csvReader obj = new csvReader();
							obj.run(path);
							out_mtch_rdg = obj.csvrdg;
							out_mtch_onto = obj.csvonto;
							out_unmtch_onto.clear();
							out_unmtch_rdg.clear();
							rat.clear();
							
							break;

						} else {

							if (ontotype.equals("our")) {

								if (output.size() > 0 && result.size() > 0) {

									algnmnt obj = new algnmnt();
									obj.match(result, output);
									out_mtch_rdg = obj.rdg;
									out_mtch_onto = obj.onto;
									out_unmtch_onto = obj.unmatched_onto;
									out_unmtch_rdg = obj.unmatched_rdg;
									rat = obj.rating;

								} else {

									err = "No Datatype property found";
									System.out.println(err);
								}

							}

							if (ontotype.equals("api")) {

								api ob = new api();
								ob.alignment(prefix, out_onto_url);
								out_mtch_onto = ob.ontodata;
								out_mtch_rdg = ob.rdgdata;
								out_unmtch_onto = ob.unmtch_onto;
								out_unmtch_rdg = ob.unmtch_rdg;
								rat = ob.rating;

							}

						}

					}
				}
			}

		} else {

			if (ontotype.equals("our")) {

				if (output.size() > 0 && result.size() > 0) {

					algnmnt obj = new algnmnt();
					obj.match(result, output);
					out_mtch_rdg = obj.rdg;
					out_mtch_onto = obj.onto;
					out_unmtch_onto = obj.unmatched_onto;
					out_unmtch_rdg = obj.unmatched_rdg;
					rat = obj.rating;
				} else {

					err = "No Datatype property found";
					System.out.println(err);
				}

			}

			if (ontotype.equals("api")) {

				api ob = new api();
				ob.alignment(prefix, out_onto_url);
				out_mtch_onto = ob.ontodata;
				out_mtch_rdg = ob.rdgdata;
				out_unmtch_onto = ob.unmtch_onto;
				out_unmtch_rdg = ob.unmtch_rdg;
				rat = ob.rating;

			}

		}

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