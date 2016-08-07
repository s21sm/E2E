package Alignment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class algnmnt {

	public ArrayList<String> rdg = new ArrayList<String>() ;
	public ArrayList<String> onto = new ArrayList<String>() ;
	public ArrayList<String> unmatched_rdg= new ArrayList<String>();
	public ArrayList<String> unmatched_onto= new ArrayList<String>();
	public ArrayList<Float> rating = new ArrayList<Float>();

	public void match(ArrayList<String> p, ArrayList<String> q) {
		

		ArrayList<String> arr2 = new ArrayList<String>();
		ArrayList<String> arr3 = new ArrayList<String>();
		ArrayList<Float> arr5 = new ArrayList<Float>();
		ArrayList<String> Clist1 = p; // rdg
		ArrayList<String> Clist2 = q; // onto

		for (int i = 0; i < Clist1.size(); i++) {

			for (int j = 0; j < Clist2.size(); j++) {

				if (Clist1.get(i).equals(Clist2.get(j))) {

					float level = (float) 1.0;
					arr2.add(Clist1.get(i));
					arr3.add(Clist2.get(j));
					arr5.add(level);

				} else {

					if (Clist1.get(i).equalsIgnoreCase(Clist2.get(j))) {

						float level = (float) 0.95;
						arr2.add(Clist1.get(i));
						arr3.add(Clist2.get(j));
						arr5.add(level);
					}

					if (Clist1.get(i).toLowerCase()
							.contains(Clist2.get(j).toLowerCase())
							|| Clist2.get(j).toLowerCase()
									.contains(Clist1.get(i).toLowerCase())) {

						float level = (float) 0.7;
						arr2.add(Clist1.get(i));
						arr3.add(Clist2.get(j));
						arr5.add(level);

					}

					else {

						float level = (float) 0.1;
						arr2.add(Clist1.get(i));
						arr3.add(Clist2.get(j));
						arr5.add(level);

					}

				}

			}

		}

		// System.out.println(arr2); // rdg
		// System.out.println(arr3); // onto

		HashSet<String> set = new HashSet<>(arr2);
		ArrayList<String> result = new ArrayList<>(set); // sorting unique rdg

		ArrayList<Float> com = new ArrayList<Float>();
		ArrayList<Float> cal = new ArrayList<Float>();

		for (int k = 0; k < result.size(); k++) {
			for (int l = 0; l < arr2.size(); l++) {

				if (result.get(k).equals(arr2.get(l))) {

					float u = arr5.get(l);

					com.add(u);

				}

			}

			Collections.sort(com);
			float w = Collections.max(com);
			com.clear();
			cal.add(w);
		}

		ArrayList<String> unmatched = new ArrayList<String>();

		for (int k = 0; k < result.size(); k++) {
			for (int l = 0; l < arr2.size(); l++) {

				if (result.get(k).equals(arr2.get(l))
						&& cal.get(k).equals(arr5.get(l))) {

					if (Float.valueOf(arr5.get(l)) > 0.5) {

						rdg.add(arr2.get(l)); // matched are added to list
						onto.add(arr3.get(l)); // matched are added to list
						rating.add(arr5.get(l));
					}
					if (Float.valueOf(arr5.get(l)) < 0.5) {

						unmatched.add(arr2.get(l));

					}

				}

			}

		}

		Clist2.removeAll(onto); // removing matched entities from ontology side
		HashSet<String> set2 = new HashSet<>(unmatched);
		ArrayList<String> unmtch = new ArrayList<>(set2); // removing matched
															// entities from rdg
															// side

		if (unmtch.size() > 0) {

			for (int k = 0; k < unmtch.size(); k++) {

				unmatched_rdg.add(unmtch.get(k));
//				unmtchrating.add((float) (0.10));

			}

		}

		if (Clist2.size() > 0)
			for (int r = 0; r < Clist2.size(); r++) {

				unmatched_onto.add(Clist2.get(r));

			}

	}

}
