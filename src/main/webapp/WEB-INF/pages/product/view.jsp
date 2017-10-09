<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="vn.kms.launch.basicwebapp.model.Product"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Test Product Detail</title>
</head>
<body>
    <h2>Test Product - View</h2>
    <%
        Product product = (Product) request.getAttribute("product");
    %>
    <c:choose>
        <c:when test="${product != null}">
        Name: <%=product.getName()%><br>
        Price: ${product.price}<br>
        Category: ${product.category}<br>
        Description: ${product.description}<br>
        </c:when>
        <c:otherwise>
        Cannot fetch Product with ID = <%=request.getParameter("productId")%>
        </c:otherwise>
    </c:choose>
</body>
</html>