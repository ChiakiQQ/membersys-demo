<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/include/head.jsp" />
<html>
<body class="d-flex justify-content-center align-items-center vh-100">
    <div class="login-page">
        <h2 class="text-center mb-4">產生驗證碼</h2>
        <form method="post" action="${pageContext.request.contextPath}/verify/code">
            <div class="mb-3">
                <label for="email" class="form-label">Email：</label>
                <input type="email" name="email" id="email" class="form-control" required>
            </div>
            <button type="submit" class="btn btn-primary w-100">產生驗證碼</button>
        </form>
    </div>
</body>
</html>