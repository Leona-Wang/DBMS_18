document.addEventListener("DOMContentLoaded", function() {
    fetch('/mainPage')
        .then(response => response.text()) // 使用 .text() 處理 HTML
        .then(html => {
            document.getElementById('content').innerHTML = html;
        })
        .catch(error => console.error('Error:', error));
});

function gotoInventory(){
    window.location.href = "http://localhost:8080/inventory";
}

function gotoOtherExpense(){
    window.location.href = "http://localhost:8080/otherExpense";
}

function gotoReport(){
    window.location.href = "http://localhost:8080/report";
}