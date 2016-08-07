package csv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class csvReader {

	public ArrayList<String> csvonto = new ArrayList<String>();
	public ArrayList<String> csvrdg= new ArrayList<String>();


	public  void run(String path) {

		String csvFile = path;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {

			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {

				
				String[] word = line.split(cvsSplitBy);
				
				
				String x = word[0];
				csvonto.add(x);
				String y = word[1];
				csvrdg.add(y);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}

