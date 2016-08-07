package servicepoint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class bookingdates {

	public int StartDate;
//	public String EndDate;
	
	public String UserStartDate;
	public String UserEndDate;

	public void datecreator(String startdate, int days) {

		// 2010-10-10T00:00:00Z

		String ex = startdate;
//		StartDate = startdate + "T00:00:00Z";

		String year = ex.substring(0, 4);
		int YEAR = Integer.parseInt(year, 10);
	

		String month = ex.substring(5, 7);
		int MONTH = Integer.parseInt(month, 10) - 1;
		

		String date = ex.substring(8, 10);
		int DATE = Integer.parseInt(date, 10);
		
		
		
		
	     GregorianCalendar gc = new GregorianCalendar();
	     gc.set(Calendar.DAY_OF_MONTH, DATE); // you asked for 21st Sept but put 8
	     gc.set(Calendar.MONTH, MONTH); // you aksed for 21st Sept but put JUNE
	     gc.set(Calendar.YEAR, YEAR);
	     StartDate = gc.get(Calendar.DAY_OF_YEAR);
	     
//	     System.out.println("Test" + StartDate);
	     
	  
	     
		
		
		UserStartDate =  date+" "+month+" "+year;
//		
//		System.out.println( "Check in"+ UserStartDate );

		SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, DATE);
		cal.set(Calendar.MONTH, MONTH);
		cal.set(Calendar.YEAR, YEAR);
		cal.add(Calendar.DAY_OF_MONTH, days-1);
		String output = sdf.format(cal.getTime());

		UserEndDate = output;
		
//		System.out.println(" Check out " + output);

	}

}
