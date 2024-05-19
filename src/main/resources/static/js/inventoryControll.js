document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('productListButton').addEventListener('click', function() {
        document.getElementById('hiddenProductList').style.display = 'block';
        /*fetch('/inventory')
            .then(response => response.json())
            .then(data => {
                const tableBody = document.getElementById('userTable').getElementsByTagName('tbody')[0];
                tableBody.innerHTML = ''; // 清空現有的表格內容
                data.forEach(user => {
                    let row = tableBody.insertRow();
                    let cell1 = row.insertCell(0);
                    let cell2 = row.insertCell(1);
                    cell1.textContent = product.productName;
                    cell2.textContent = product.productPrice;
                });
            })
            .catch(error => console.error('Error fetching user data:', error));*/
    });
});