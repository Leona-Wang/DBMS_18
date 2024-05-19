document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('productListButton').addEventListener('click', function() {
        document.getElementById('hiddenProductList').style.display = 'block';
        fetch('/inventoryProductList')
            .then(response => response.json())
            .then(data => {
                const tableBody = document.getElementById('productTable').getElementsByTagName('tbody')[0];
                tableBody.innerHTML = ''; // 清空现有的表格内容
                data.forEach(product => {
                    let row = tableBody.insertRow();
                    let cell1 = row.insertCell(0);
                    let cell2 = row.insertCell(1);
                    cell1.textContent = product.productName;
                    cell2.textContent = product.productPrice;
                });
            })
            .catch(error => console.error('Error fetching product data:', error));
    });

    document.getElementById('addProductListButton').addEventListener('click', function() {
        document.getElementById('productTable').style.display = 'none';
        
    });
});