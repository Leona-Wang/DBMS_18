document.addEventListener('DOMContentLoaded', function(){

    fetch('/showProductList')
            .then(response => response.json())
            .then(data => {
                const tableBody = document.getElementById('productTable').getElementsByTagName('tbody')[0];
                tableBody.innerHTML = ''; // 清空现有的表格内容
                data.forEach(product => {
                    let row = tableBody.insertRow();
                    let cell1 = row.insertCell(0);
                    let cell2 = row.insertCell(1);
                    let cell3 = row.insertCell(2);
                    let cell4 = row.insertCell(3);
                    let cell5 = row.insertCell(4);
                    let cell6 = row.insertCell(5);
                    cell1.textContent = product.productIndex;
                    cell2.textContent = product.productName;
                    cell3.textContent = product.productImportPrice;
                    cell4.textContent = product.productUnitPerBox;
                    cell5.textContent = product.productExportUnitPrice;
                    cell6.textContent = product.productAlertNumber;
                });
            })
            .catch(error => console.error('Error fetching product data:', error));


})