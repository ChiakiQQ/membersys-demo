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
    <a href="${pageContext.request.contextPath}/register" class="btn btn-primary btn-sm">新增會員</a>
    <a href="${pageContext.request.contextPath}/admin/logout" class="btn btn-danger btn-sm">登出</a>
  </div>

<c:choose>
    <c:when test="${not empty members}">
        <table class="table table-bordered table-hover">
            <tr>
                <th>序號</th>
                <th>ID</th>
                <th>帳號</th>
                <th>Email</th>
                <th>註冊時間</th>
                <th>操作</th>
            </tr>
            <c:forEach var="m" items="${members}" varStatus="loop">
                <tr>
                    <td>${(currentPage - 1) * limit + loop.index + 1}</td>
                    <td>${m["id"]}</td>
                    <td>${m["username"]}</td>
                    <td>${m["email"]}</td>
                    <td>
                        <script>
                          document.write(new Date(${m.createTime}).toLocaleString('zh-TW', { hour12: false }));
                        </script>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/edit?id=${m.id}" class="btn btn-outline-primary btn-sm ms-1">編輯</a>
                        <a href="#" class="btn btn-danger btn-sm btn-delete" data-id="${m.id}">刪除</a>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <div class="d-flex justify-content-between align-items-center mt-3">
          <form method="get" action="${pageContext.request.contextPath}/admin/list" class="d-flex align-items-center gap-2">
            <label for="limitSelect" class="visually-hidden">每頁筆數</label>
            <select id="limitSelect" name="limit" class="form-select form-select-sm w-auto" onchange="this.form.submit()">
              <option value="5" ${limit == 5 ? 'selected' : ''}>5</option>
              <option value="10" ${limit == 10 ? 'selected' : ''}>10</option>
              <option value="20" ${limit == 20 ? 'selected' : ''}>20</option>
            </select>

            <span>第 ${currentPage} / ${totalPages} 頁</span>

            <c:if test="${currentPage > 1}">
              <button type="submit" name="page" value="${currentPage - 1}" class="btn btn-outline-primary btn-sm">上一頁</button>
            </c:if>
            <c:if test="${currentPage < totalPages}">
              <button type="submit" name="page" value="${currentPage + 1}" class="btn btn-outline-primary btn-sm">下一頁</button>
            </c:if>

            <input type="hidden" name="page" value="${currentPage}"/>
          </form>

          <div class="d-flex gap-3 align-items-center">
            <span class="text-muted small">總筆數：${totalCount}</span>
            <a href="${pageContext.request.contextPath}/admin/export" class="btn btn-success btn-sm">匯出全部</a>
          </div>
        </div>
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