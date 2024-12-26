console.log("DOM 已加載完成(使用 defer)");
const button_defer = document.getElementById("btn");
button_defer.addEventListener("click", () => {
    alert("按鈕已被點擊！(使用 defer)");
});