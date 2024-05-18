
function submitForm() {
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;

    var data = new URLSearchParams();
    data.append('username', username);
    data.append('password', password);

    fetch('/', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: data
    }).then(response => response.json())
        .then(data => {
            if (data.success) {
                alert(data.message);
                window.location.href = "http://localhost:8080/mainPage";
            } else {
                alert(data.message);
            }
        })
        .catch(error => console.error('Error:', error));
}
