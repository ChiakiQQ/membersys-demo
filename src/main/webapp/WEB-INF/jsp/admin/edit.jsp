<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><title>編輯會員</title></head>
<body>
<h2>編輯會員</h2>
<form method="post" action="${pageContext.request.contextPath}/admin/edit">
    <input type="hidden" name="id" value="${member.id}" />

    <label for="username">帳號：</label>
    <input type="text" id="username" name="username" value="${member.username}" required /><br/>

    <label for="email">Email：</label>
    <input type="email" id="email" name="email" value="${member.email}" required /><br/>

    <label for="password">密碼：</label>
    <input type="password" id="password" name="password" /><br/>

    <button type="submit">送出修改</button>
    <button type="button" onclick="window.location.href='${pageContext.request.contextPath}/admin/list'">返回列表</button>
</form>
</body>
</html>