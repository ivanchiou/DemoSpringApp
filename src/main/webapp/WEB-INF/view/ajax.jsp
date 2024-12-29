
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AJAX Example</title>
</head>
<body>
    <h1>AJAX 示例 - FetchAPI</h1>
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
            fetch(`http://localhost:8080/model?id=${"${encodeURIComponent(id)}"}`)
                .then((response) => {
                    if (!response.ok) throw new Error("網絡連線問題");
                    return response.json(); // 將回應轉換為 JSON
                })
                .then((data) => {
                    document.getElementById("result").innerHTML = `
                        <h2>${"${data.id}"}</h2>
                        <p>${"${data.name}"}</p>
                    `;
                })
                .catch((error) => {
                    document.getElementById("result").textContent = "無法取得數據：" + error.message;
                });
        });
          
    </script>
</body>
</html>
