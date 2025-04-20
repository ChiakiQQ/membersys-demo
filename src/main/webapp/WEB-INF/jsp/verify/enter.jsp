<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/include/head.jsp" />
<html>
<head><title>輸入驗證碼</title></head>
<body class="d-flex justify-content-center align-items-center vh-100">
  <div class="login-page">
    <form method="post" action="${pageContext.request.contextPath}/verify/submit">
      <input type="hidden" name="email" value="${email}">
      <div class="mb-3">
        <label for="code" class="form-label">輸入驗證碼：</label>
        <input type="text" id="code" name="code" required maxlength="4" class="form-control">
      </div>
      <button type="submit" class="btn btn-primary w-100">驗證</button>
    </form>
  </div>
</body>
</html>