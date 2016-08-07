package test;

import java.util.ArrayList;

public class tableReturn {

	public String tab;

	public void table(ArrayList<String> rdg, ArrayList<String> onto,
			ArrayList<String> level) {

		String returnTable = null;

		returnTable = "<table border=1>";
		returnTable = returnTable + "<tr>";

		returnTable = returnTable + "<td>" + "RDG" + "</td>";
		returnTable = returnTable + "<td>" + "Ontology" + "</td>";
		returnTable = returnTable + "<td>" + "Status" + "</td>";
		returnTable = returnTable + "<tr>";
		returnTable = returnTable + "<tr>";

		for (int l = 0; l < rdg.size(); l++) {

			returnTable = returnTable + "<td>" + rdg.get(l) + "</td>";
			returnTable = returnTable + "<td>" + onto.get(l) + "</td>";

			if ((Float.valueOf(level.get(l))) >= 0.80) {

				returnTable = returnTable + "<td>" + "Confirm" + "</td>";

			} else {

				returnTable = returnTable + "<td>" + "    <input type="
						+ "radio" + " name=" + "select" + " value="
						+ onto.get(l) + "><br>   " + "</td>";

			}

			returnTable = returnTable + "<tr>";

		}

		tab = returnTable;

	}

}
