<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head><title>請勿重複登入</title></head>
<body>
<h2 style="color:red; text-align:center; margin-top:100px;">請勿重複開啟頁面！</h2>
<p style="text-align:center;"><a href="#" onclick="tryClose(); return false;">關閉本頁</a></p>

<script>
  function tryClose() {
    window.close();
    setTimeout(function () {
      document.getElementById("closeMsg").style.display = "block";
    }, 300); // 等待一點時間，若未關閉則顯示提示
  }
</script>

<p id="closeMsg" style="display:none; text-align:center; color:gray;">若此分頁未自動關閉，請手動關閉。</p>
</body>
</html>