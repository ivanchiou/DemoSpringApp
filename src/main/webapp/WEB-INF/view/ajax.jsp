
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AJAX Example</title>
</head>
<body>
    <h1>AJAX 示例</h1>
    <button id="fetchButton">點擊取得資料</button>
    <div id="result"></div>
    <script>
        document.getElementById("fetchButton").addEventListener("click", function () {
            // 創建 XMLHttpRequest 對象
            const xhr = new XMLHttpRequest();

            // 配置請求類型和目標 URL
            xhr.open("GET", "http://localhost:8080/model?id=1", true);

            // 設置當請求完成時的回調函數
            xhr.onload = function () {
                if (xhr.status === 200) {
                    const data = JSON.parse(xhr.responseText); // 將 JSON 字串轉換為物件
                    document.getElementById("result").innerHTML = `
                        <h2>${"${data.id}"}</h2>
                        <p>${"${data.name}"}</p>
                    `;
                } else {
                    document.getElementById("result").textContent = "無法取得資料";
                }
            };

            // 發送請求
            xhr.send();
        });
          
    </script>
</body>
</html>
