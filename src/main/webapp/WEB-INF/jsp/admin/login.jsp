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
            <div class="mb-3">
                <label for="captcha" class="form-label">驗證碼：</label>
                <input type="text" id="captcha" name="captcha" class="form-control" required />
                <img src="${pageContext.request.contextPath}/admin/captcha"
                     onclick="this.src='${pageContext.request.contextPath}/captcha?d=' + Math.random()"
                     title="點擊刷新"
                     style="cursor:pointer;margin-top:0.5rem;">
            </div>
            <button type="submit" class="btn btn-primary w-100">登入</button>
        </form>
    </div>
    <!-- Captcha 錯誤提示 Modal -->
    <div class="modal fade" id="captchaErrorModal" tabindex="-1" aria-labelledby="captchaErrorModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="captchaErrorModalLabel">錯誤</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="關閉"></button>
          </div>
          <div class="modal-body text-center text-danger">
            驗證碼錯誤，請重新輸入
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary w-100" data-bs-dismiss="modal">確定</button>
          </div>
        </div>
      </div>
    </div>
</body>
</html>
