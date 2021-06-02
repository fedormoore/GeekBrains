<%@ page import="java.util.List" %>
<%@ page import="ru.moore.models.Product" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    List<Product> products = (List<Product>) request.getAttribute("products");
    for (Product product:products) {
%><h3><%=product.getTitle()+" "+product.getTitle()%><h3/>
<br><%
    }
%>
</body>
</html>
