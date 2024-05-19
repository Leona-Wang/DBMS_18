

document.addEventListener('DOMContentLoaded', function() {


    document.getElementById('productListButton').addEventListener('click', function() {
        document.getElementById('hiddenProductList').style.display = 'block';
        document.getElementById('hiddenInventory').style.display = 'none';
        document.getElementById("productTable").style.display="block";
        document.getElementById("addProduct").style.display="none";
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

    document.getElementById('checkProductListButton').addEventListener('click', function() {
        document.getElementById("productTable").style.display="block";
        document.getElementById("addProduct").style.display="none";
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
        document.getElementById("productTable").style.display="none";
        document.getElementById("addProduct").style.display="block";
    });

    document.getElementById('inventoryButton').addEventListener('click', function() {
        document.getElementById('hiddenProductList').style.display = 'none';
        document.getElementById('hiddenInventory').style.display = 'block';
        document.getElementById('inventoryTable').style.display = 'block';
        document.getElementById('addInventory').style.display = 'none';
        document.getElementById('deductInventory').style.display = 'none';
    });

    document.getElementById('seeInventoryButton').addEventListener('click', function() {
        document.getElementById('inventoryTable').style.display = 'block';
        document.getElementById('addInventory').style.display = 'none';
        document.getElementById('deductInventory').style.display = 'none';
    });

    document.getElementById('addInventoryButton').addEventListener('click', function() {
        document.getElementById('inventoryTable').style.display = 'none';
        document.getElementById('addInventory').style.display = 'block';
        document.getElementById('deductInventory').style.display = 'none';
    });

    document.getElementById('deductInventoryButton').addEventListener('click', function() {
        document.getElementById('inventoryTable').style.display = 'none';
        document.getElementById('addInventory').style.display = 'none';
        document.getElementById('deductInventory').style.display = 'block';
    });
});


function addProductInput() {
    // 获取表单
    const form = document.getElementById('addProductListForm');
    
    // 创建一个新的输入框组
    const inputContainer = document.createElement('div');
    inputContainer.classList.add('input-container');
    
    // 创建名字输入框
    const productNameInput = document.createElement('input');
    productNameInput.type = 'text';
    productNameInput.name = 'productName[]'; // 使用数组形式提交多个名字
    productNameInput.placeholder = '產品名稱';

    // 创建学号输入框
    const productPriceInput = document.createElement('input');
    productPriceInput.type = 'text';
    productPriceInput.name = 'productPrice[]'; // 使用数组形式提交多个学号
    productPriceInput.placeholder = '價格';

    // 将输入框添加到输入框组中
    inputContainer.appendChild(productNameInput);
    inputContainer.appendChild(productPriceInput);

    // 将输入框组添加到表单中
    form.appendChild(inputContainer);
}

function submitForm() {
    const form = document.getElementById('addProductListForm');
    form.submit();
    window.alert("已成功新增產品至產品清單！");
    window.location.href = "http://localhost:8080/inventory";
}