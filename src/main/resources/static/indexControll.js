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
    }).then(response => response.text())
      .then(message => alert("Server says: " + message));
}