<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>JavaScript 資料型別示例</title>
    <script>
        function showDataTypes() {
            let str = "Hello World"; // 字串
            let num = 42;           // 數字
            let isTrue = true;      // 布林值
            let nothing = null;     // 空值

            alert(`String: ${"${str}"}\nNumber: ${"${num}"}\nBoolean: ${"${isTrue}"}\nNull: ${"${nothing}"}`);
        }
        function checkGrade() {
            let score = prompt("請輸入您的分數:");
            let grade;

            if (score >= 90) {
                grade = "A";
            } else if (score >= 80) {
                grade = "B";
            } else if (score >= 70) {
                grade = "C";
            } else {
                grade = "F";
            }

            alert(`您的等級是: ${"${grade}"}`);
        }
        function showFruits() {
            let fruits = ["蘋果", "香蕉", "櫻桃"];
            let list = "";

            for (let i = 0; i < fruits.length; i++) {
                list += `<li>${"${fruits[i]}"}</li>`;
            }

            document.getElementById("fruitList").innerHTML = list;
        }
        function showUser() {
            let user = {
                name: "Alice",
                age: 25,
                greet: function () {
                    return `Hello, ${"${this.name}"}!`
                }
            };

            alert(user.greet());
        }
        function testScope() {
            let localVar = "我是區域變數";
            alert(localVar); // 顯示區域變數
            alert(globalVar); // 顯示全域變數
        }

        function testGlobal() {
            globalVar = "我是全域變數";
            alert(localVar); // 顯示區域變數
            alert(globalVar); // 顯示全域變數
        }
        function createCounter() {
            let count = 0;
            return function () {
                count++;
                document.getElementById("counter").textContent = count;
            };
        }

        const counter = createCounter();
    </script>
</head>
<body>
    <button onclick="showDataTypes()">顯示資料型別</button>
    <button onclick="checkGrade()">檢查分數等級</button>
    <button onclick="showFruits()">顯示水果</button>
    <ul id="fruitList"></ul>
    <button onclick="showUser()">顯示用戶資訊</button>
    <button onclick="testScope()">測試區域作用域</button>
    <button onclick="testGlobal()">測試全域作用域</button>
    <button onclick="counter()">點擊計數</button>
    <div>計數器值: <span id="counter">0</span></div>
</body>
</html>
