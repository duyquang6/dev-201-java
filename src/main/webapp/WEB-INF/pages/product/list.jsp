<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="vn.kms.launch.basicwebapp.model.Product"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Test Product List</title>
</head>
<body>
    <h2>Test Product - List</h2>
    <%
        List<Product> list = (List) request.getAttribute("list");
    %>
    <ul>
        <c:forEach items="${list}" var="product">
            <li><a
                href='<c:url value="/app/product/get?productId=${product.id}"/>'><c:out
                        value="${product.id}" /> - <c:out
                        value="${product.name}" /></a></li>
        </c:forEach>
    </ul>
</body>
</html>