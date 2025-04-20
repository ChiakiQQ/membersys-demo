<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>驗證結果</title>
    <jsp:include page="/include/head.jsp" />
</head>
<body class="d-flex justify-content-center align-items-center vh-100">
  <div class="login-page">
    <c:if test="${success}">
        <p style="color:green;">驗證成功！</p>
    </c:if>
    <c:if test="${not success}">
        <p style="color:red; text-align: center;">驗證失敗，請重新輸入！</p>
    </c:if>
    <p class="text-center mt-4">
        <a href="${pageContext.request.contextPath}/verify" class="btn btn-primary">回驗證頁</a>
    </p>
  </div>
</body>
</html>