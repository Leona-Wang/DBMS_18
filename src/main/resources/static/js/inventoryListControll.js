document.addEventListener('DOMContentLoaded', function(){

    fetch('/showInventoryList')
            .then(response => response.json())
            .then(data => {
                const tableBody = document.getElementById('inventoryTable').getElementsByTagName('tbody')[0];
                tableBody.innerHTML = ''; // 清空现有的表格内容
                data.forEach(product => {
                    let row = tableBody.insertRow();
                    let cell1 = row.insertCell(0);
                    let cell2 = row.insertCell(1);
                    let cell3 = row.insertCell(2);
                    cell1.textContent = product.productIndex;
                    cell2.textContent = product.productName;
                    cell3.textContent = product.inventoryAmount;
                });
            })
            .catch(error => console.error('Error fetching product data:', error));


})