<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--http://localhost:8080/redis/keys--%>

<html>
<head><title>Redis Keys</title></head>
<body>
<h2>Redis Keys</h2>
<ul>
    <c:forEach var="key" items="${redisKeys}">
        <li><a href="${pageContext.request.contextPath}/redis/value?key=${key}">${key}</a></li>
    </c:forEach>
</ul>
</body>
</html>