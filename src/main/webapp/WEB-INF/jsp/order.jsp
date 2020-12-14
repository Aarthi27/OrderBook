<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.math.BigDecimal"%>
<html>
<head>

<title>Live Order Book</title>
<style>
body {
	background-color: lightblue;
}

h1 {
	color: white;
	text-align: center;
}

#wrapper {
	height: 100%;
	margin: 0;
	align-self: center;
}

#asks-div {
	height: 50%;
	margin: 0;
}

#buy-div {
	height: 50%;
	margin: 0;
}

table {
	display: table;
	border-collapse: separate;
	border-spacing: 2px;
	border-color: gray;
	width: 400px;
	
}

th, td {
	padding: 10px;
	text-align: left;
	width: 400px;
}

tr:nth-child(even) {
	background-color: #f2f2f2;
}

tr:nth-child(odd) {
	background-color: white;
}


thead {
    display: inline-block;
    width: 100%;
    height: 20px;
}
tbody {
    height: 200px;
    display: inline-block;
    width: 100%;
    overflow: auto;
}

</style>
</head>

<body>
	<%
		response.setIntHeader("Refresh", 1);
	%>
	<h1>Order Book</h1>


	<div id="wrapper">
		<div id="asks-div", style="overflow-x:auto!important;">
			<center>
				<h2>BUY</h2>

				<table>
					<tr>
						<th>Price</th>
						<th>Quantity</th>
					</tr>
					<c:forEach items="${buy}" var="buyMap">

						<tr>
							<td>${buyMap.key}</td>
							<td>${buyMap.value}</td>
						</tr>
					</c:forEach>
				</table>
			</center>
		</div>

		<hr
			style="height: 2px; border-width: 0; color: gray; background-color: gray">
		<div id="buy-div" , style="overflow-x:auto!important;">
			<center>



				<table>
					<tr>
						<th>Price</th>
						<th>Quantity</th>
					</tr>
					<c:forEach items="${sell}" var="sellMap">

						<tr>
							<td>${sellMap.key}</td>
							<td>${sellMap.value}</td>
						</tr>
					</c:forEach>
				</table>
				<h2>SELL</h2>
			</center>
		</div>
	</div>
</body>

</html>