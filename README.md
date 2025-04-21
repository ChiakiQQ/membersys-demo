## 專案環境說明

- Java 版本：17
- Spring Boot 版本：2.7.18

## Demo使用說明
1. 啟用mySQL和redis
2. 放上sql/內的ddl和init
3. 啟動[Application](src%2Fmain%2Fjava%2Fcom%2Fcaitlyn%2Fmembersysdemo%2FApplication.java)後，可以使用以下方法跳轉
   * [StartupBrowserOpener.java](src%2Fmain%2Fjava%2Fcom%2Fcaitlyn%2Fmembersysdemo%2FStartupBrowserOpener.java)
   * [StartupRedis.java](src%2Fmain%2Fjava%2Fcom%2Fcaitlyn%2Fmembersysdemo%2FStartupRedis.java)
   * [StartupVerify.java](src%2Fmain%2Fjava%2Fcom%2Fcaitlyn%2Fmembersysdemo%2FStartupVerify.java)

---


## JSTL `<c:...>` 常用語法  
> 以下標籤屬於 JSTL Core 標籤庫，使用前請在 JSP 開頭加入：  
> `<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>`

| 標籤              | 功能描述               | 範例                                                       |
|-----------------|--------------------|----------------------------------------------------------|
| `<c:if>`        | 條件判斷               | `<c:if test="\${user != null}">Hi</c:if>`                |
| `<c:choose>`    | 多條件選擇 (類似 switch)  | `<c:choose><c:when ... /><c:otherwise /></c:choose>`     |
| `<c:when>`      | 配合 `<c:choose>` 使用 | `<c:when test="\${flag}">是</c:when>`                     |
| `<c:otherwise>` | 配合 `<c:choose>` 使用 | `<c:otherwise>不是</c:otherwise>`                          |
| `<c:forEach>`   | 迴圈處理               | `<c:forEach var="item" items="\${list}">...</c:forEach>` |
| `<c:set>`       | 設定變數               | `<c:set var="count" value="5" />`                        |
| `<c:out>`       | 輸出變數（具備 XSS 避免功能）  | `<c:out value="\${user.name}" />`                        |


## JSTL `<fmt:...>` 日期與格式化語法  

| 標籤                   | 功能描述      | 範例                                                                  |
|----------------------|-----------|---------------------------------------------------------------------|
| `<fmt:formatDate>`   | 日期格式化     | `<fmt:formatDate value="\${now}" pattern="yyyy/MM/dd HH:mm:ss"/>`   |
| `<fmt:parseDate>`    | 將字串轉為日期格式 | `<fmt:parseDate value="2024-01-01" pattern="yyyy-MM-dd" var="d" />` |
| `<fmt:formatNumber>` | 數字格式化     | `<fmt:formatNumber value="1234567.89" type="currency" />`           |

---

## 常用 JSP 語法

| 功能             | 範例語法                                                 |
|----------------|------------------------------------------------------|
| 輸出變數           | `${user.name}`                                       |
| 判斷 null        | `<c:if test="\${user == null}">空值</c:if>`            |
| 多頁導入           | `<%@ include file="header.jsp" %>`                   |
| 轉址             | `<c:redirect url="/login.jsp"/>`                     |
| 設定 response 編碼 | `<%@ page contentType="text/html; charset=UTF-8" %>` |
