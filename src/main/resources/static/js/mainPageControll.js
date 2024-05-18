document.addEventListener("DOMContentLoaded", function() {
    fetch('/mainPage')
        .then(response => response.text())
        .then(data => {
            document.getElementById('username').textContent = data;
        })
        .catch(error => console.error('Error:', error));
});