
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AJAX Example</title>
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.14.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
    <script src="https://code.jquery.com/ui/1.14.1/jquery-ui.js"></script>
</head>
<body>
    <h1>AJAX 示例 - FetchAPI</h1>
    <label for="inputId">輸入 ID:</label>
    <input type="text" id="inputId" placeholder="輸入要查詢的 ID">
    <button id="fetchButton">點擊取得資料</button>
    <div id="result"></div>
    <script>
        $(document).ready(function () {
            $("#fetchButton").click(function () {
                const id = document.getElementById("inputId").value;

                // 檢查是否輸入有效的 ID
                if (!id.trim()) {
                    document.getElementById("result").textContent = "請輸入有效的 ID！";
                    return;
                }
                // 使用 $.ajax 發送請求
                $.ajax({
                    url: `http://localhost:8080/model?id=${"${encodeURIComponent(id)}"}`, // API URL
                    type: "GET", // HTTP 方法
                    dataType: "json", // 預期的反應格式
                    success: function (data) {
                        // 成功呼叫，更新網頁內容
                        document.getElementById("result").innerHTML = `
                            <h2>${"${data.id}"}</h2>
                            <p>${"${data.name}"}</p>
                        `;
                    },
                    error: function (xhr, status, error) {
                        $("#result").text(`無法獲取數據：${error}`);
                    },
                });
            });
        });    
    </script>
</body>
</html>
