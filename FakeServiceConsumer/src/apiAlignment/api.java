package apiAlignment;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;

import org.semanticweb.owl.align.Alignment;
import org.semanticweb.owl.align.AlignmentException;
import org.semanticweb.owl.align.AlignmentProcess;
import org.semanticweb.owl.align.Cell;

import fr.inrialpes.exmo.align.impl.BasicAlignment;
import fr.inrialpes.exmo.align.impl.method.StringDistAlignment;

public class api {

	public ArrayList<String> ontodata = new ArrayList<String>();
	public ArrayList<String> rdgdata = new ArrayList<String>();
	public ArrayList<Float> rating = new ArrayList<Float>();
	public ArrayList<String> unmtch_rdg = new ArrayList<String>();
	public ArrayList<String> unmtch_onto = new ArrayList<String>();

	public void alignment(String prefix, String ownonto) {

		ontodata.clear();
		rdgdata.clear();
		rating.clear();
		unmtch_rdg.clear();
		unmtch_onto.clear();

		ArrayList<String> arr = new ArrayList<String>();
		ArrayList<String> arr2 = new ArrayList<String>();

		Properties params = new Properties();
		try {
			URI foreignOnto = new URI(prefix);
			URI ownOnto = new URI(ownonto);

			// Run two different alignment methods (e.g., ngramdistance and
			// smoa)
			AlignmentProcess a1 = new StringDistAlignment();
			params.setProperty("stringFunction", "smoaDistance");
			a1.init(foreignOnto, ownOnto);
			a1.align((Alignment) null, params);

			AlignmentProcess a2 = new StringDistAlignment();
			a2.init(foreignOnto, ownOnto);
			params = new Properties();
			params.setProperty("stringFunction", "ngramDistance");
			a2.align((Alignment) null, params);

			// Trim above .5 and .7 respectively
			a1.cut(0.5);
			a2.cut(0.7);

			// Clone and merge alignments
			BasicAlignment a1a2 = (BasicAlignment) (a1.clone());
			a1a2.ingest(a2);

			Iterator<Cell> iterator = a1a2.iterator();
			while (iterator.hasNext()) {
				Cell cell = iterator.next();

				String object1 = getReal(cell.getObject1().toString());
				String object2 = getReal(cell.getObject2().toString());
				String strength = "" + cell.getStrength();
				float level = Float.valueOf(strength);

				if (level >= 0.90) {

					if (!rdgdata.contains(object1)) {
						rdgdata.add(object1);
						ontodata.add(object2);
						rating.add(level);
					}

					if (arr.contains(object1)) {
						arr.remove(object1);
						arr2.add(object2);
					}

				} else {

					if (!rdgdata.contains(object1)) {
						arr.add(object1);
						arr2.add(object2);

					}

				}

			}

		} catch (AlignmentException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		HashSet<String> set = new HashSet<>(arr);
		unmtch_rdg = new ArrayList<>(set);

		HashSet<String> set2 = new HashSet<>(arr2);
		unmtch_onto = new ArrayList<>(set2);

	}

	private static String getReal(String str) {

		String x;
		if (str.contains("#")) {

			x = str.substring((str.lastIndexOf("#") + 1));
			x = x.replace(">", "");
		} else {

			x = str.substring((str.lastIndexOf("/") + 1));
			x = x.replace(">", "");
		}

		return x;

	}

}