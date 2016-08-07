package servicepoint;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Day2Date {

	 
	public String date;
	public void Day (int x) {
		
		
		
		int dayOfYear = x;
		
	    Calendar calendar = Calendar.getInstance();
	    calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);
	    
	    SimpleDateFormat dateOnly = new SimpleDateFormat("dd/MM/yyyy");
	    
	     date = dateOnly.format(calendar.getTime());
	  
	}
	
	
}
