<%--
  Created by IntelliJ IDEA.
  User: priscafehiarisoadama
  Date: 10/12/2023
  Time: 17:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>luminosite</title>
</head>
<body>
<h1>luminosite</h1>
<form class="forms-sample" action="${pageContext.request.contextPath}/getCoupures" method="post">
    <input type="date" name="dates">
    <input type="submit" value="voir les coupures">
</form>
</body>
</html>
