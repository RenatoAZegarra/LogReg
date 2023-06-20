<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- c:out ; c:forEach etc. --> 
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!-- Formatting (dates) --> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<!-- form:form -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- for rendering errors on PUT routes -->
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Loged In!</title>
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
 <!-- change to match your file/naming structure -->
    <script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
<!-- change to match your file/naming structure -->
</head>
<body>
   <h1> Welcome, <c:out value="${userName}"/> !</h1>
   <p>This is your dashboard. Nothing to see here yet.</p>
   <a href="/logout">Log Out</a>
</body>
</html>

