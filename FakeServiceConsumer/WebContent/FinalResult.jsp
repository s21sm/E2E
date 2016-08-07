
<%@page import="java.util.ArrayList"%>
<%!@SuppressWarnings("unchecked")%>
<!DOCTYPE HTML>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Booking Result</title>
</head>
<body style="">



	<%
		int result = (Integer) request.getAttribute("flag");

		String name = (String) request.getAttribute("customerName");

		String number = (String) request.getAttribute("BookingNumber");

		ArrayList<String> cotnam = (ArrayList<String>) request
				.getAttribute("cottageName");
		ArrayList<String> cotadd = (ArrayList<String>) request
				.getAttribute("cottageAddress");
		ArrayList<String> cotcity = (ArrayList<String>) request
				.getAttribute("cottageCity");
		ArrayList<String> coturl = (ArrayList<String>) request
				.getAttribute("cottageUrl");

		ArrayList<String> cotbed = (ArrayList<String>) request
				.getAttribute("cottageBed");
		ArrayList<String> cotppl = (ArrayList<String>) request
				.getAttribute("cottagePeople");
		ArrayList<String> cotcityd = (ArrayList<String>) request
				.getAttribute("cottageCityDistance");
		ArrayList<String> cotlaked = (ArrayList<String>) request
				.getAttribute("cottageLakeDistance");

		ArrayList<String> cotbook = (ArrayList<String>) request
				.getAttribute("cottageBookingdate");
	%>


	<h1>Booking Result</h1>


	<%
		if (result == 1) {
	%>
	
	
	<h4>Customer Name :</h4> <p> <%=name%>  </p>
	<h4>Booking Number:</h4> <p> <%=number%>  </p>
	

	<table border="1">
		<tbody>
			<tr>
				<td>Cottage Name</td>
				<td>Cottage Address</td>
				<td>Nearby City</td>
				<td>URL</td>
				<td>Amount of Bed</td>
				<td>Amount of place</td>
				<td>Distance from City</td>
				<td>Distance from lake</td>
				<td>Booking Dates</td>
			</tr>


			<%
				for (int i = 0; i < cotnam.size(); i++) {
			%>
			<tr>

				<td><%=cotnam.get(i)%></td>
				<td><%=cotadd.get(i)%></td>
				<td><%=cotcity.get(i)%></td>
				<td><%=coturl.get(i)%></td>
				<td><%=cotbed.get(i)%></td>
				<td><%=cotppl.get(i)%></td>
				<td><%=cotcityd.get(i)%></td>
				<td><%=cotlaked.get(i)%></td>
				<td><%=cotbook.get(i)%></td>


				<%
					}
				%>
			</tr>
	</table>

	<%
		}
	%>


	<%
		if (result == 0) {
	%>


	<p>No Result Found according your given parameter, kindly change input values!</p>


	<%
		}
	%>

</body>
</html>
