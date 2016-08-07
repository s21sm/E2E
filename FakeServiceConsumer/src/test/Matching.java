package test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;


public class Matching {

	String w;
	public ArrayList<String> rdg = new ArrayList<String>();
	public ArrayList<String> onto = new ArrayList<String>();
	ArrayList<String> arr1 = new ArrayList<String>();
	ArrayList<String> arr2 = new ArrayList<String>();
	ArrayList<String> arr3 = new ArrayList<String>();
	ArrayList<String> arr4 = new ArrayList<String>();
	ArrayList<String> arr5 = new ArrayList<String>();

	public void match(ArrayList<String> p, ArrayList<String> q) {

		ArrayList<String> Clist1 = p; // rdg
		ArrayList<String> Clist2 = q; // onto
		
		System.out.println(Clist1);
		System.out.println(Clist2);

		int n = 0;
		for (int i = 0; i < Clist1.size(); i++) {

			for (int j = 0; j < Clist2.size(); j++) {

				if (Clist1.get(i).equals(Clist2.get(j))) {

					n = n + 1;
					float level = (float) 1.0;
					String relation = "Class";
					arr1.add(Integer.toString(n));
					arr2.add(Clist1.get(i));
					arr3.add(Clist2.get(j));
					arr4.add(relation);
					arr5.add(Float.toString(level));

				} else {

					if (Clist1.get(i).equalsIgnoreCase(Clist2.get(j))) {

						n = n + 1;
						float level = (float) 0.95;
						String relation = "Same Class (case insensitively)";
						arr1.add(Integer.toString(n));
						arr2.add(Clist1.get(i));
						arr3.add(Clist2.get(j));
						arr4.add(relation);
						arr5.add(Float.toString(level));
					}

					// } else if (Clist1.get(i).toLowerCase()
					// .contains(Clist2.get(j).toLowerCase())
					// || Clist2.get(j).toLowerCase()
					// .contains(Clist1.get(i).toLowerCase())) {
					//
					// n = n + 1;
					// float level = (float) 0.7;
					// String relation = "Partial Match";
					// arr1.add(Integer.toString(n));
					// arr2.add(Clist1.get(i));
					// arr3.add(Clist2.get(j));
					// arr4.add(relation);
					// arr5.add(Float.toString(level));
					//
					// }

					else {

						n = n + 1;

						float level = (float) 0.1;
						String relation = "75% matching";
						arr1.add(Integer.toString(n));
						arr2.add(Clist1.get(i));
						arr3.add(Clist2.get(j));
						arr4.add(relation);
						arr5.add(Float.toString(level));

					}

				}

			}

		}

		 System.out.println(arr2); // rdg
		 System.out.println(arr3); // onto
		// System.out.println(arr5);

		HashSet<String> set = new HashSet<>(arr2);
		ArrayList<String> result = new ArrayList<>(set);
		
		
		
		ArrayList<String> com = new ArrayList<String>();
		ArrayList<String> cal = new ArrayList<String>();

		for (int k = 0; k < result.size(); k++) {
			for (int l = 0; l < arr2.size(); l++) {

				if (result.get(k).equals(arr2.get(l))) {

					// System.out.println(result.get(k) + "   " + arr2.get(l));

					String u = arr5.get(l);

					com.add(u);

				}

			}
			w = Collections.max(com);
			cal.add(w);
		}

		ArrayList<String> unmatched = new ArrayList<String>();

		for (int k = 0; k < result.size(); k++) {
			for (int l = 0; l < arr2.size(); l++) {

				if (result.get(k).equals(arr2.get(l))
						&& cal.get(k).equals(arr5.get(l))) {

					if (Float.valueOf(arr5.get(l)) > 0.5) {

						rdg.add(arr2.get(l));
						onto.add(arr3.get(l));

					} else {

						unmatched.add(arr2.get(l));

					}

				}

			}

		}

		Clist2.removeAll(onto);

//		HashSet<String> set2 = new HashSet<>(unmatched);
//		ArrayList<String> unmtch = new ArrayList<>(set2);

//		if (unmtch.size() > 0) {
//
//			System.out.println("Following RDG properties");
//			System.out.println(unmtch);
//			System.out.println("need to be manually mapped with");
//			System.out.println(Clist2);
//			for (int k = 0; k < unmtch.size(); k++) {
//
//				
//				System.out
//						.println("Kindly inset the property name with what u want to match = "
//								+ unmtch.get(k));
//
//				Scanner scanner = new Scanner(System.in);
//				String str = scanner.nextLine();
//				if(Clist2.contains(str)){
//					rdg.add(unmtch.get(k));
//					onto.add(str);
//					
//				}else{
//					
//					System.out.println("Check spelling");
//				}
//
//
//
//			}
//
//		}

	}

}
