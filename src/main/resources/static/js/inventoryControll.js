

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
        fetch('/inventoryInventoryList')
            .then(response => response.json())
            .then(data => {
                const tableBody = document.getElementById('inventoryTable').getElementsByTagName('tbody')[0];
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

    document.getElementById('seeInventoryButton').addEventListener('click', function() {
        document.getElementById('inventoryTable').style.display = 'block';
        document.getElementById('addInventory').style.display = 'none';
        document.getElementById('deductInventory').style.display = 'none';
        fetch('/inventoryInventoryList')
            .then(response => response.json())
            .then(data => {
                const tableBody = document.getElementById('inventoryTable').getElementsByTagName('tbody')[0];
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

    document.getElementById('addInventoryButton').addEventListener('click', function() {
        document.getElementById('inventoryTable').style.display = 'none';
        document.getElementById('addInventory').style.display = 'block';
        document.getElementById('deductInventory').style.display = 'none';
        fetch('/inventoryOptions')
        .then(response => response.json())
        .then(data => {
            // 获取下拉式选单的元素
            const dropdown = document.getElementById('addInventoryOption[]');

            // 遍历数据并创建选项元素
            data.forEach(option => {
                const optionElement = document.createElement('option');
                optionElement.value = option;
                optionElement.textContent = option;
                dropdown.appendChild(optionElement);
            });
        })
        .catch(error => {
            console.error('Error fetching data:', error);
        });
    });

    document.getElementById('deductInventoryButton').addEventListener('click', function() {
        document.getElementById('inventoryTable').style.display = 'none';
        document.getElementById('addInventory').style.display = 'none';
        document.getElementById('deductInventory').style.display = 'block';
        fetch('/inventoryOptions')
        .then(response => response.json())
        .then(data => {
            // 获取下拉式选单的元素
            const dropdown = document.getElementById('deductInventoryOption[]');

            // 遍历数据并创建选项元素
            data.forEach(option => {
                const optionElement = document.createElement('option');
                optionElement.value = option;
                optionElement.textContent = option;
                dropdown.appendChild(optionElement);
            });
        })
        .catch(error => {
            console.error('Error fetching data:', error);
        });
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

function submitAddProductForm() {
    const form = document.getElementById('addProductListForm');
    form.submit();
    window.alert("已成功新增產品至產品清單！");
    window.location.href = "http://localhost:8080/inventory";
}

function addAddInventoryInput() {
    const formContainer = document.getElementById('addInventory-input-container');
    const inputGroup = document.createElement('div');
    inputGroup.classList.add('input-group');

    // 创建下拉式选单
    const dropdown = document.createElement('select');
    dropdown.name = 'addInventoryOption[]';

    fetch('/inventoryOptions')
        .then(response => response.json())
        .then(data => {
            data.forEach(option => {
                const optionElement = document.createElement('option');
                optionElement.value = option;
                optionElement.textContent = option;
                dropdown.appendChild(optionElement);
            });
        })
        .catch(error => {
            console.error('Error fetching data:', error);
        });

    // 创建文字输入框
    const textInput = document.createElement('input');
    textInput.type = 'text';
    textInput.name = 'addInventoryBox[]';
    textInput.placeholder = '請輸入新增數量(單位：箱)';

    // 将下拉式选单和文字输入框添加到输入组
    inputGroup.appendChild(dropdown);
    inputGroup.appendChild(textInput);

    // 将输入组添加到表单容器
    formContainer.appendChild(inputGroup);
}   

function submitAddInventoryForm() {
    const form = document.getElementById('addInventoryForm');
    const formData = new FormData(form);

    // 使用 fetch 提交表单数据
    fetch('/addInventory', {
        method: 'POST',
        body: new URLSearchParams(formData)
    })
    .then(response => response.text())
    .then(data => {
        alert("已成功新增存貨至存貨清單！");
        window.location.href = "http://localhost:8080/inventory";
    })
    .catch(error => {
        console.error('Error submitting form:', error);
    });
}

function addDeductInventoryInput() {
    const formContainer = document.getElementById('deductInventory-input-container');
    const inputGroup = document.createElement('div');
    inputGroup.classList.add('input-group');

    // 创建下拉式选单
    const dropdown = document.createElement('select');
    dropdown.name = 'deductInventoryOption[]';

    fetch('/inventoryOptions')
        .then(response => response.json())
        .then(data => {
            data.forEach(option => {
                const optionElement = document.createElement('option');
                optionElement.value = option;
                optionElement.textContent = option;
                dropdown.appendChild(optionElement);
            });
        })
        .catch(error => {
            console.error('Error fetching data:', error);
        });

    // 创建文字输入框
    const textInput = document.createElement('input');
    textInput.type = 'text';
    textInput.name = 'deductInventoryBox[]';
    textInput.placeholder = '請輸入新增數量(單位：瓶、包)';

    // 将下拉式选单和文字输入框添加到输入组
    inputGroup.appendChild(dropdown);
    inputGroup.appendChild(textInput);

    // 将输入组添加到表单容器
    formContainer.appendChild(inputGroup);
}

function submitDeductInventoryForm() {
    const form = document.getElementById('deductInventoryForm');
    const formData = new FormData(form);

    // 使用 fetch 提交表单数据
    fetch('/deductInventory', {
        method: 'POST',
        body: new URLSearchParams(formData)
    })
    .then(response => response.text())
    .then(data => {
        alert("已成功新增銷貨至存貨清單！");
        window.location.href = "http://localhost:8080/inventory";
    })
    .catch(error => {
        console.error('Error submitting form:', error);
    });
}