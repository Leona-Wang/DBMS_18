function submitData() {
    var data = document.getElementById('inputData').value;
    fetch('/', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ data: data })
    }).then(response => response.text())
      .then(message => alert("Server says: " + message));
}