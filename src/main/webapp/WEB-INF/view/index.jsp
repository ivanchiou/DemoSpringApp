<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
</head>
<style>
body {
  background-color: lightblue;
}
h1 {
  color: navy;
  text-align: center;
}
p {
  margin: 20px;
  padding: 10px;
}
</style>
<body>
  <h1>This is the body of the index view for Ivan</h1>
  <p>${message}</p>
  <h2>${description}</h2>
  <a target="_blank" href="https://www.gtalent.com.tw">點擊這裡</a>
  <img src="/images/burger.jpg" width="200" height="150" alt="示例圖片">
  <form action="/model/register" method="post">
    <label for="name">姓名：</label>
    <input type="text" id="name" name="name">
    <button type="submit">送出</button>
  </form>  
</body>
</html>