<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>會員註冊</title>
    <script>
        function disableSubmit() {
            const button = document.querySelector("button[type='submit']");
            button.disabled = true;
            button.textContent = "送出中...";
            return true;
        }
    </script>
</head>
<body>
<h2>會員註冊</h2>
<c:if test="${param.success != null}">
    <script>
        alert("註冊成功！");
        window.location.href = "${pageContext.request.contextPath}/admin/list";
    </script>
</c:if>

<c:if test="${param.success == null}">
    <form method="post" action="${pageContext.request.contextPath}/register" onsubmit="return disableSubmit()">
        <label for="username">帳號：</label>
        <input type="text" id="username" name="username" required /><br/>

        <label for="email">Email：</label>
        <input type="email" id="email" name="email" required /><br/>

        <label for="password">密碼：</label>
        <input type="password" id="password" name="password" required /><br/>

        <button type="submit">送出註冊</button>
        <button type="button" onclick="window.location.href='${pageContext.request.contextPath}/admin/list'">返回列表</button>
    </form>
</c:if>
</body>
</html>