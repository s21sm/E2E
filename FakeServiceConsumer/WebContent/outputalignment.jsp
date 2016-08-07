
<%@page import="java.util.ArrayList"%>
<%!@SuppressWarnings("unchecked")%>
<!DOCTYPE HTML>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Everything to Everyting Interface</title>
</head>
<body style="">
	<form method="post" action="out">


		<%
			ArrayList<String> onto = (ArrayList<String>) request
					.getAttribute("mtch_onto");

			ArrayList<String> rdg = (ArrayList<String>) request
					.getAttribute("mtch_rdg");

			ArrayList<String> un_onto = (ArrayList<String>) request
					.getAttribute("unmtch_onto");

			ArrayList<String> un_rdg = (ArrayList<String>) request
					.getAttribute("unmtch_rdg");

			ArrayList<String> fullont = (ArrayList<String>) request
					.getAttribute("FON");
			
			ArrayList<String> FULLRDG = (ArrayList<String>) request
					.getAttribute("FRDG");
			
			
			ArrayList<Float> rating = (ArrayList<Float>) request
					.getAttribute("lvl");
			
		%>


		<h1>Output Alignment</h1>

		<table border="1">
			<tbody>
				<tr>
					<td>RDG</td>
					<%if(rating.size()>0){%><td>Confidence Level</td><%} %>		
					<td>Ontology</td>
					<td>Option</td>

				</tr>


				<%
					for (int i = 0; i < rdg.size(); i++) {
				%>
				<tr>

					<td><%=rdg.get(i)%></td>
					<%if(rating.size()>0){%><td><%=rating.get(i)%></td><%} %>	
					<td><%=onto.get(i)%></td>
					<td><select name=<%=rdg.get(i)%>>
							<option value="SEL">SELECT</option>
							<%
							for (int j = 0; j < fullont.size(); j++) {
						%>

							<option value=<%=fullont.get(j)%>>
								<%=fullont.get(j)%>
							</option>


							<%
							}
						%>

					</select></td>


					<%
						}
					%>

				</tr>
		</table>


		<%if (un_rdg.size()>0) { %>
		
		
		 <h3>Following items need Manual mapping</h3>

		<table border="1">
			<tr>
				<td>RDG</td>
				<td>Ontology</td>

			</tr>


			<%
				for (int i = 0; i < un_rdg.size(); i++) {
			%>
			<tr>

				<td><%=un_rdg.get(i)%></td>
				<td><select name=<%=un_rdg.get(i)%>>
						<option value="SEL">SELECT</option>

						<%
							for (int j = 0; j < fullont.size(); j++) {
						%>

						<option value=<%=fullont.get(j)%>>
							<%=fullont.get(j)%>
						</option>


						<%
							}
						%>


				</select></td>



				<%
					}
				%>

			</tr>


			</tbody>
		</table>


		<%
							}
						%>




		<input type="submit" />
	</form>
</body>
</html>
