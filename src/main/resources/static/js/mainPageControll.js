document.addEventListener("DOMContentLoaded", function() {
    fetch('/mainPage')
        .then(response => response.text()) // 使用 .text() 處理 HTML
        .then(html => {
            document.getElementById('content').innerHTML = html;
        })
        .catch(error => console.error('Error:', error));
        fetchRandomSentence();
        
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

async function fetchRandomSentence(){
    try {
        const response = await fetch('/random-sentence');
        if (!response.ok) {
            throw new Error('Network response was not ok ' + response.statusText);
        }
        const sentence = await response.text();
        document.getElementById('sentence-container').innerText = sentence;
    } catch (error) {
        document.getElementById('sentence-container').innerText = 'Failed to load sentence: ' + error;
    }
    
}