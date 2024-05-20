document.addEventListener('DOMContentLoaded', function() {
    fetch('/otherExpenseList')
        .then(response => response.json())
        .then(data => {
            const tableBody = document.getElementById('expenseTable').getElementsByTagName('tbody')[0];
            tableBody.innerHTML = ''; // 清空现有的表格内容
            data.forEach(otherExpense => {
                let row = tableBody.insertRow();
                let cell1 = row.insertCell(0);
                let cell2 = row.insertCell(1);
                let cell3 = row.insertCell(2);
                cell1.textContent = otherExpense.date;
                cell2.textContent = otherExpense.type;
                cell3.textContent = otherExpense.cost;
            });
        })
        .catch(error => console.error('Error fetching product data:', error));
    

    document.getElementById('seeExpenseButton').addEventListener('click', function() {
        document.getElementById('expenseTable').style.display = 'block';
        document.getElementById('addExpense').style.display = 'none';
        fetch('/otherExpenseList')
        .then(response => response.json())
        .then(data => {
            const tableBody = document.getElementById('expenseTable').getElementsByTagName('tbody')[0];
            tableBody.innerHTML = ''; // 清空现有的表格内容
            data.forEach(otherExpense => {
                let row = tableBody.insertRow();
                let cell1 = row.insertCell(0);
                let cell2 = row.insertCell(1);
                let cell3 = row.insertCell(2);
                cell1.textContent = otherExpense.date;
                cell2.textContent = otherExpense.type;
                cell3.textContent = otherExpense.cost;
            });
        })
        .catch(error => console.error('Error fetching product data:', error));
    });

    document.getElementById('addExpenseButton').addEventListener('click', function() {
        document.getElementById('expenseTable').style.display = 'none';
        document.getElementById('addExpense').style.display = 'block';
        
    });
})  

function addExpenseInput() {
    // 获取表单
    const form = document.getElementById('addExpenseForm');
    
    // 创建一个新的输入框组
    const inputContainer = document.createElement('div');
    inputContainer.classList.add('input-container');

    // 创建名字输入框
    const expenseDateInput = document.createElement('input');
    expenseDateInput.type = 'date';
    expenseDateInput.name = 'expenseDate[]'; // 使用数组形式提交多个名字
    expenseDateInput.value = '2024-01-05';
    expenseDateInput.min = '2024-01-01';
    expenseDateInput.max = '2024-12-31';
    
    // 创建名字输入框
    const expenseTypeInput = document.createElement('input');
    expenseTypeInput.type = 'text';
    expenseTypeInput.name = 'expenseType[]'; // 使用数组形式提交多个名字
    expenseTypeInput.placeholder = '輸入項目';

    // 创建学号输入框
    const expenseCostInput = document.createElement('input');
    expenseCostInput.type = 'text';
    expenseCostInput.name = 'expenseCost[]'; // 使用数组形式提交多个学号
    expenseCostInput.placeholder = '花費';

    // 将输入框添加到输入框组中
    inputContainer.appendChild(expenseDateInput);
    inputContainer.appendChild(expenseTypeInput);
    inputContainer.appendChild(expenseCostInput);

    // 将输入框组添加到表单中
    form.appendChild(inputContainer);
}

function submitAddExpenseForm() {
    const form = document.getElementById('addExpenseForm');
    const formData = new FormData(form);

    // 使用 fetch 提交表单数据
    fetch('/addExpense', {
        method: 'POST',
        body: new URLSearchParams(formData)
    })
    .then(response => response.text())
    .then(data => {
        alert("已成功新增銷貨至存貨清單！");
        window.location.href = "http://localhost:8080/otherExpense";
    })
    .catch(error => {
        console.error('Error submitting form:', error);
    });
}