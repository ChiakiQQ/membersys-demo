<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<jsp:include page="/include/head.jsp" />
<body class="d-flex justify-content-center align-items-center vh-100">
    <div class="login-page">
        <h2 class="text-center mb-4">會員註冊</h2>
        <c:if test="${param.success != null}">
            <script>
                alert("註冊成功！");
                window.location.href = "${pageContext.request.contextPath}/admin/list";
            </script>
        </c:if>

        <c:if test="${param.success == null}">
            <form method="post" action="${pageContext.request.contextPath}/register" onsubmit="return disableSubmit()">
                <div class="mb-3">
                    <label for="username" class="form-label">帳號：</label>
                    <input type="text" id="username" name="username" class="form-control" required />
                </div>

                <div class="mb-3">
                    <label for="email" class="form-label">Email：</label>
                    <input type="email" id="email" name="email" class="form-control" required />
                </div>

                <div class="mb-3">
                    <label for="password" class="form-label">密碼：</label>
                    <input type="password" id="password" name="password" class="form-control" required />
                </div>

                <button type="submit" class="btn btn-success w-100">送出註冊</button>
                <button type="button" class="btn btn-secondary w-100 mt-2" onclick="window.location.href='${pageContext.request.contextPath}/admin/list'">返回列表</button>
            </form>
        </c:if>
    </div>
</body>
</html>