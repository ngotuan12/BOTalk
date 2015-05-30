<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${username}. </P>

<ul>
<c:forEach var="it" items="${hiList}">
	<li>${it.name}</li>
</c:forEach>
</ul>

</body>
</html>
