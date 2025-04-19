<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head><title>會員列表</title></head>
<body>
<h2>會員列表</h2>
<p><a href="${pageContext.request.contextPath}/register">新增會員</a></p>

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
                      <a href="${pageContext.request.contextPath}/admin/edit?id=${m.id}">編輯</a>
                      |
                      <a href="${pageContext.request.contextPath}/admin/delete?id=${m.id}"
                         onclick="return confirm('確定要刪除此會員嗎？');">刪除</a>
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
</body>
</html>