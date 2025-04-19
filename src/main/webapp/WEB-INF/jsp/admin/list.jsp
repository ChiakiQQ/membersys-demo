<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<jsp:include page="/include/head.jsp" />
<head>
  <title>會員列表</title>
</head>
<body>
<script>
  const tabKey = "admin-tab-opened";
  if (localStorage.getItem(tabKey)) {
    // 其他分頁已開啟，直接跳轉
    window.location.href = "${pageContext.request.contextPath}/admin/lock";
  } else {
    // 標記本分頁為開啟中
    localStorage.setItem(tabKey, "true");
    window.addEventListener("beforeunload", function () {
      localStorage.removeItem(tabKey);
    });
  }
</script>
<div class="list-page">
  <h2>會員列表</h2>
  <div class="list-controls">
    <a href="${pageContext.request.contextPath}/register">新增會員</a>
    <a class="logout-link" href="${pageContext.request.contextPath}/admin/logout">登出</a>
  </div>

<c:choose>
    <c:when test="${not empty members}">
        <table border="1">
            <tr>
                <th>ID</th>
                <th>帳號</th>
                <th>Email</th>
                <th>註冊時間</th>
                <th>操作</th>
            </tr>
            <c:forEach var="m" items="${members}">
                <tr>
                    <td>${m["id"]}</td>
                    <td>${m["username"]}</td>
                    <td>${m["email"]}</td>
                    <td>
                        <script>
                          document.write(new Date(${m.createTime}).toLocaleString('zh-TW', { hour12: false }));
                        </script>
                    </td>
                    <td>
                      <a href="#" class="btn-delete" data-id="${m.id}">刪除</a>
                      |
                      <a href="${pageContext.request.contextPath}/admin/edit?id=${m.id}">編輯</a>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td colspan="4"></td>
                <td style="text-align: right;">
                    <a href="${pageContext.request.contextPath}/admin/export">匯出本頁</a>
                </td>
            </tr>
        </table>
    </c:when>
    <c:otherwise>
        <p style="color:gray;">尚未有會員註冊！</p>
    </c:otherwise>
</c:choose>
</div>

<!-- 刪除確認 Modal -->
<div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="deleteModalLabel">確認刪除</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="關閉"></button>
      </div>
      <div class="modal-body">
        你確定要刪除此會員嗎？
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
        <a id="confirmDeleteBtn" href="#" class="btn btn-danger">確認刪除</a>
      </div>
    </div>
  </div>
</div>


</body>
</html>