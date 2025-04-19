<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--localhost:8080/admin/login--%>

<html>
<head><title>管理員登入</title></head>
<body>
<h2>後台登入</h2>
<form method="post" action="${pageContext.request.contextPath}/admin/login">
    <label for="name">帳號：</label>
    <input type="text" id="name" name="name" /><br/>
    <label for="password">密碼：</label>
    <input type="password" id="password" name="password" /><br/>
    <button type="submit">登入</button>
</form>
<c:if test="${param.error != null}">
    <p style="color:red;">帳號或密碼錯誤</p>
</c:if>
</body>
</html>

