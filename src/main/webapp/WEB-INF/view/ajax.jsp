
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AJAX Example</title>
</head>
<body>
    <h1>AJAX 示例</h1>
    <label for="inputId">輸入 ID:</label>
    <input type="text" id="inputId" placeholder="輸入要查詢的 ID">
    <button id="fetchButton">點擊取得資料</button>
    <div id="result"></div>
    <script>
        document.getElementById("fetchButton").addEventListener("click", function () {
            const id = document.getElementById("inputId").value;

            // 檢查是否輸入有效的 ID
            if (!id.trim()) {
                document.getElementById("result").textContent = "請輸入有效的 ID！";
                return;
            }

            // 創建 XMLHttpRequest 對象
            const xhr = new XMLHttpRequest();

            // 配置請求類型和目標 URL
            xhr.open("GET", `http://localhost:8080/model?id=${"${encodeURIComponent(id)}"}`, true);

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
