<%@ page contentType="text/html; charset=UTF-8" %>
<%
    response.setHeader("Cache-Control", "max-age=31536000, must-revalidate");
%>
<html>
<head>
  <title text=${title}>Default Title</title>
  <meta name="description" content=${description} />
  <link rel="canonical" href=${canonicalUrl} />
  <meta property="og:title" content="${title}" />
  <meta property="og:description" content="${description}" />
  <meta property="og:image" content="https://localhost/images/burger.jpg" />
  <meta property="og:url" content="${canonicalUrl}" />
  
  <meta name="twitter:card" content="summary_large_image" />
  <meta name="twitter:title" content="${title}" />
  <meta name="twitter:description" content="${description}" />
  <meta name="twitter:image" content="https://localhost/images/burger.jpg" />
  
</head>
<script type="application/ld+json">
  {
    "@context": "https://schema.org/",
    "@type": "Product",
    "name": "${title}",
    "image": "https://localhost/images/burger.jpg",
    "description": "${description}",
    "brand": {
      "@type": "Brand",
      "name": "SpringBootBrand"
    }
  }
</script>  
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
.box {
  width: 150px;
  height: 100px;
  padding: 15px;
  margin: 20px;
  border: 3px solid black;
  text-align: center;
  line-height: 70px; /* Center text vertically */
  font-weight: bold;
}

.box1 {
  background-color: lightcoral;
}

.box2 {
  background-color: lightgreen;
}

.box3 {
  background-color: lightblue;
}

.outer-box {
  width: 300px;
  height: 200px;
  background-color: lightyellow;
  padding: 20px;
  border: 5px solid darkorange;
  margin: 30px auto;
}

.inner-box {
  width: 100px;
  height: 50px;
  margin: 10px auto;
  text-align: center;
  line-height: 50px;
  border: 2px solid black;
}
</style>
<body>
  <h1>This is the body of the index view for Ivan</h1>
  <p>${message}</p>
  <h2>${description}</h2>
  <div class="box">這是一個盒模型</div>
  <div class="box box1">Box 1</div>
  <div class="box box2">Box 2</div>
  <div class="box box3">Box 3</div>
  <div class="outer-box">
    <div class="inner-box box1">Box 1</div>
    <div class="inner-box box2">Box 2</div>
  </div>
  <a target="_blank" href="https://www.gtalent.com.tw">點擊這裡</a>
  <img src="/images/burger.jpg" width="200" height="150" alt="示例圖片">
  <form action="/model/register" method="post">
    <label for="name">姓名：</label>
    <input type="text" id="name" name="name">
    <button type="submit">送出</button>
  </form>  
</body>
</html>