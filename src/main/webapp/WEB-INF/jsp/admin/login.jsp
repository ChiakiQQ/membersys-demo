<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--localhost:8080/admin/login--%>

<html>
<jsp:include page="/include/head.jsp" />
<body class="d-flex justify-content-center align-items-center vh-100">
    <div class="login-page">
        <h2 class="text-center mb-4">後台登入</h2>
        <form method="post" action="${pageContext.request.contextPath}/admin/login">
            <div class="mb-3">
                <label for="name" class="form-label">帳號：</label>
                <input type="text" id="name" name="name" class="form-control" required />
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">密碼：</label>
                <input type="password" id="password" name="password" class="form-control" required />
            </div>
            <button type="submit" class="btn btn-primary w-100">登入</button>
        </form>
        <c:if test="${param.error != null}">
            <p class="text-danger mt-3 text-center">帳號或密碼錯誤</p>
        </c:if>
    </div>
</body>
</html>
