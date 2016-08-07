
<!DOCTYPE HTML>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Everything to Everyting Interface</title>
</head>
<body style="">

	<h1>Cottage Booking Service</h1>
	<hr />
	<form method="post" action="result">
		<table>
			<tbody>
				<tr>
					<td>Enter your Name</td>
					<td><input type="text" name="name" required /></td>
				</tr>

				<tr>
					<td>Enter amount of people</td>
					<td><input type="number" name="people" required></td>
				</tr>
				<tr>
					<td>Require amount of Bedrooms</td>
					<td><input type="number" name="bedrooms" required></td>
				</tr>

				<tr>
					<td>Maximum distance from Lake</td>
					<td><input type="number" name="lake_distance" required>
						Meters</td>
				</tr>
				<tr>
					<td>Select your City</td>
					<td><select name="city">
							<option value="Helsinki">Helsinki</option>
							<option value="Jyvaskyla">Jyvaskyla</option>
							<option value="Turku">Turku</option>

					</select></td>
				</tr>

				<tr>
					<td>Maximum distance from City</td>
					<td><input type="number" name="city_disance" required>
						Meters</td>
				</tr>
				<tr>
					<td>Checkin Date</td>
					<td><input type="text" name="startdate" required>yyyy-mm-dd</td>
				</tr>
				<tr>
					<td>I want to live</td>
					<td><input type="number" name="days" required> days</td>
				</tr>
				<tr>
					<td>I am flexible for</td>
					<td><input type="number" name="flex" required> days</td>
				</tr>

				<tr>
					<td><input type="submit" /></td>

				</tr>

			</tbody>
		</table>

	</form>
</body>
</html>
