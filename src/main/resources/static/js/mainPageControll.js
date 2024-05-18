document.addEventListener("DOMContentLoaded", function() {
    fetch('/mainPage')
        .then(response => response.text()) // 使用 .text() 處理 HTML
        .then(html => {
            document.getElementById('content').innerHTML = html;
        })
        .catch(error => console.error('Error:', error));
});