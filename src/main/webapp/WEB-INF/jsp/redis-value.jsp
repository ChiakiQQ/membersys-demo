<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--http://localhost:8080/redis/value?key=@@@--%>

<html>
<head><title>Redis Value</title></head>
<body>
<h2>Key: ${redisKey}</h2>
<p><strong>Value:</strong> ${redisValue}</p>
<p><a href="${pageContext.request.contextPath}/redis/keys">← Back to Keys</a></p>
</body>
</html>