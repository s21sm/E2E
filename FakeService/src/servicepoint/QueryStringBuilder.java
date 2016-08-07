package servicepoint;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class QueryStringBuilder {

	public String querystring;
	public Query query;
	public OntModel model1;

	public void StringBuilder(String city, int people, int bedrooms,
			int city_distance, int lake_distance, int S, int E, int flex) {

		String SOURCE = "http://users.jyu.fi/~syibkhan/15.rdf";

		model1 = ModelFactory
				.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
		// read the RDF/XML file
		model1.read(SOURCE, "RDF/XML");

		String CITY = "\"" + city + "\"";

		String queryString = "PREFIX g: <http://www.semanticweb.org/ibrahim/ontologies/2015/2/untitled-ontology-128#>\n"
				+ "PREFIX owl:<http://www.w3.org/2002/07/owl#> \n"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>  \n"
				+ "PREFIX xml:<http://www.w3.org/XML/1998/namespace> \n"
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+

				"SELECT  ?cottege ?Name ?freeperiod ?start ?end ?url ?AmountOfBeds  ?people ?address ?city_distance ?lake_distance ?cityname  "
				+

				"WHERE { \n"
				+ "{?cottege  g:hasName ?Name;\n "

				+ "g:hasAddress ?address;\n"

				+ "g:hasUrl ?url;\n"

				+ "g:hasBeds ?AmountOfBeds;\n"

				+ "g:hasPlaces ?people;\n"

				+ "g:hasCityDistance ?city_distance;\n"

				+ "g:hasLakeDistance  ?lake_distance. \n "

				+ "?cottege  g:isSituatedIn ?city.\n"

				+ "?city  g:hasName ?cityname.\n"

				+ "?cottege  g:hasFreePeriod ?freeperiod.\n"
				+ "?freeperiod g:startdate ?start.\n"
				+ "?freeperiod g:enddate ?end.\n" +

				"FILTER ( ("
				+ (S - flex)
				+ " >= ?start && "
				+ (E - flex)
				+ " <= ?end ) || ("
				+ (S + flex)
				+ " >= ?start && "
				+ (E + flex)
				+ " <= ?end ) || ("
				+ S
				+ " >= ?start && "
				+ E
				+ " <= ?end ) )."
				+

				"FILTER (?people >= "
				+ people
				+ "). \n"
				+ "FILTER (?AmountOfBeds >= "
				+ bedrooms
				+ ").\n"
				+ "FILTER (?city_distance >= "
				+ city_distance
				+ "). \n"
				+ "FILTER (?lake_distance  >= "
				+ lake_distance
				+ ").\n"
				+ "FILTER (?cityname = "
				+ CITY
				+ "^^xsd:string).\n "
				+ "}\n"
				+ "}\n";

		query = QueryFactory.create(queryString);

//		 System.out.println(query);

	}
}
