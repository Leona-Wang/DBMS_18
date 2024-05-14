function sendData() {
    const form = document.getElementById('loginForm');
    const formData = new FormData(form);

    const url = 'http://localhost:8080/';  // 替换成你的Java服务器端点

    fetch(url, {
        method: 'POST',
        body: formData
    })
    .then(response => response.text())
    .then(result => console.log('Success:', result))
    .catch(error => console.error('Error:', error));
}