package servicepoint;

public class BookingDateCalculation {
	
public String bookingPeriod;
	
	public void cal(int flex, int start, int end, int S,int E){
		
		int IN;
		int OUT;
		String book1 = null;
		String book2 = null;
		String book3 = null;
		String book4 = null;

		
		if (flex >= 1) {

			if (S - flex >= start && E - flex <= end) {

				IN = S - flex;
				OUT = E - flex;

				Day2Date obj2 = new Day2Date();

				obj2.Day(IN);
				String Checkin = obj2.date;

				obj2.Day(OUT);
				String Checkout = obj2.date;

				book1 = (" Booking period = " + Checkin + " to " + Checkout);

			} else {
				book1 = " ";
			}

			if (S >= start && E <= end) {

				IN = S;
				OUT = E;

				Day2Date obj2 = new Day2Date();

				obj2.Day(IN);
				String Checkin = obj2.date;

				obj2.Day(OUT);
				String Checkout = obj2.date;

				book2 = (" Booking period = " + Checkin + " to " + Checkout);

			} else {
				book2 = " ";
			}

			if (S + flex >= start && E + flex <= end) {

				IN = S + flex;
				OUT = E + flex;

				Day2Date obj2 = new Day2Date();

				obj2.Day(IN);
				String Checkin = obj2.date;

				obj2.Day(OUT);
				String Checkout = obj2.date;

				book3 = (" Booking period = " + Checkin + " to " + Checkout);
			} else {
				book3 = " ";
			}

		}

		if (flex == 0) {

			if (S >= start && E <= end) {

				IN = S;
				OUT = E;

				Day2Date obj2 = new Day2Date();

				obj2.Day(IN);
				String Checkin = obj2.date;

				obj2.Day(OUT);
				String Checkout = obj2.date;

				book4 = (" Booking period = " + Checkin + " to " + Checkout);

				// System.out.println(" Booking period =  " +
				// Checkin + " to " + Checkout + "\n");

			} else {
				book4 = " ";
			}

		}
		
		
		
		if (flex >= 1) {
			bookingPeriod = book1 + book2 + book3;
		} else {
			bookingPeriod = book4;
		}
		
		
		
	}

}
