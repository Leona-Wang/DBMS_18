document.addEventListener('DOMContentLoaded', function(){

    showImportTable();
    showExportTable();
    showExpenseTable();
    sumAllAfterDelay();
    
    
})

function showImportTable(){
    fetch('/showImportTable')
        .then(response => response.json())
        .then(data => {
            const tableBody = document.getElementById('importTable').getElementsByTagName('tbody')[0];
            tableBody.innerHTML = ''; // 清空现有的表格内容
            data.forEach(inventory => {
                const newSpan = document.createElement('span');
                let row = tableBody.insertRow();
                let cell1 = row.insertCell(0);
                let cell2 = row.insertCell(1);
                let cell3 = row.insertCell(2);
                let cell4 = row.insertCell(3);
                let cell5 = row.insertCell(4);
                let cell6 = row.insertCell(5);
                let cell7 = row.insertCell(6);
                cell1.textContent = inventory.inventoryIndex;
                cell2.textContent = inventory.date;
                cell3.textContent = inventory.inventoryName;
                newSpan.textContent = inventory.number;
                cell4.appendChild(newSpan);
                cell5.textContent = inventory.totalMoney;

                var modify = document.createElement("button");
                modify.textContent = "修改資料";
                modify.onclick = function() {
                    importModifyStart(this);
                };
                cell6.appendChild(modify);

                var deleteData = document.createElement("button");
                deleteData.textContent = "刪除資料";
                deleteData.onclick = function() {
                    importDelete(this);
                };
                cell7.appendChild(deleteData);
            });
            const sum = sumColumn('importTable', 4);
            const costText = document.getElementById('importTotalCost');
            costText.textContent = sum;
            
            
        })
        .catch(error => console.error('Error fetching product data:', error));
}
function showExportTable(){
    fetch('/showExportTable')
        .then(response => response.json())
        .then(data => {
            const tableBody = document.getElementById('exportTable').getElementsByTagName('tbody')[0];
            tableBody.innerHTML = ''; // 清空现有的表格内容
            data.forEach(inventory => {
                const newSpan = document.createElement('span');
                let row = tableBody.insertRow();
                let cell1 = row.insertCell(0);
                let cell2 = row.insertCell(1);
                let cell3 = row.insertCell(2);
                let cell4 = row.insertCell(3);
                let cell5 = row.insertCell(4);
                let cell6 = row.insertCell(5);
                let cell7 = row.insertCell(6);
                cell1.textContent = inventory.inventoryIndex;
                cell2.textContent = inventory.date;
                cell3.textContent = inventory.inventoryName;
                newSpan.textContent = inventory.number;
                cell4.appendChild(newSpan);
                cell5.textContent = inventory.totalMoney;

                var modify = document.createElement("button");
                modify.textContent = "修改資料";
                modify.onclick = function() {
                    exportModifyStart(this);
                };
                cell6.appendChild(modify);

                var deleteData = document.createElement("button");
                deleteData.textContent = "刪除資料";
                deleteData.onclick = function() {
                    exportDelete(this);
                };
                cell7.appendChild(deleteData);
            });
            const sum = sumColumn('exportTable', 4);
            const revenueText = document.getElementById('exportTotalRevenue');
            revenueText.innerText = sum; 
            
        })
        .catch(error => console.error('Error fetching product data:', error));
}

function showExpenseTable(){
    fetch('/showExpenseTable')
        .then(response => response.json())
        .then(data => {
            const tableBody = document.getElementById('expenseTable').getElementsByTagName('tbody')[0];
            tableBody.innerHTML = ''; // 清空现有的表格内容
            data.forEach(otherExpense => {
                const newSpan = document.createElement('span');
                let row = tableBody.insertRow();
                let cell1 = row.insertCell(0);
                let cell2 = row.insertCell(1);
                let cell3 = row.insertCell(2);
                let cell4 = row.insertCell(3);
                let cell5 = row.insertCell(4);
                let cell6 = row.insertCell(5);
                cell1.textContent = otherExpense.index;
                cell2.textContent = otherExpense.date;
                cell3.textContent = otherExpense.type;
                newSpan.textContent = otherExpense.cost;
                cell4.appendChild(newSpan);
                
                var modify = document.createElement("button");
                modify.textContent = "修改資料";
                modify.onclick = function() {
                    expenseModifyStart(this);
                };
                cell5.appendChild(modify);

                var deleteData = document.createElement("button");
                deleteData.textContent = "刪除資料";
                deleteData.onclick = function() {
                    expenseDelete(this);
                };
                cell6.appendChild(deleteData);
            });
            const sum = sumColumn('expenseTable', 3);
            const expenseText = document.getElementById('expenseTotalCost');
            expenseText.innerText = sum; 
            
        })
        .catch(error => console.error('Error fetching product data:', error));
}

function sumColumn(tableId, columnIndex) {
    const table = document.getElementById(tableId);
    let sum = 0;
    const tbody = table.getElementsByTagName('tbody')[0];

    for (let i = 0, row; row = tbody.rows[i]; i++) {
        // Get the cell in the specified column
        const cell = row.cells[columnIndex];
        // Ensure cell is not null and contains a valid number
        if (cell) {
            const cellValue = parseFloat(cell.innerText);
            if (!isNaN(cellValue)) {
                sum += cellValue;
            }
        }
    }
    return sum;
}

function submitSelectDate(){
    const form = document.getElementById('selectDate');
    const formData = new FormData(form);

    // 使用 fetch 提交表单数据
    fetch('/getSelectDate', {
        method: 'POST',
        body: new URLSearchParams(formData)
    })
    .then(response => response.text())
    .then(data => {
        alert("已成功！");
        window.location.href = "http://localhost:8080/checkReport";
    })
    .catch(error => {
        console.error('Error submitting form:', error);
    });
}

function importModifyStart(button){
    var row = button.parentNode.parentNode;
  
    // 获取该行中的进货数量单元格
    var numCell = row.cells[3];
  
    // 获取进货数量单元格中的 span 元素
    var numSpan = numCell.querySelector('span');
  
    // 创建一个输入框
    var numInput = document.createElement('input');
    numInput.type = 'number';
    numInput.value = numSpan.textContent.trim();
  
    // 将输入框替换掉原先的文本
    numCell.removeChild(numSpan);
    numCell.appendChild(numInput);
  
    // 聚焦输入框
    numInput.focus();
  
    // 将按钮的事件改为保存修改
    button.textContent = '保存修改';
    button.onclick = function() {
        importModifyEnd(this);
    };
}

function importModifyEnd(button) {
    // 找到按钮所在的行
    var row = button.parentNode.parentNode;

    // 获取该行中的进货数量单元格
    var numCell = row.cells[3];

    // 获取进货数量输入框
    var numInput = numCell.querySelector('input[type="number"]');

    // 获取输入框中的进货数量值并更新到 span 元素中
    numCell.innerHTML = '<span>' + numInput.value + '</span>';

    // 将按钮的事件改回编辑
    button.textContent = '修改資料';
    button.onclick = function() {
        importModifyStart(this);
    };

    // 传递行对象给 sendImportData 函数
    sendImportData(row);
}

function sendImportData(row) {
    var rowData = {
        column1: row.cells[0].innerText,
        column2: row.cells[1].innerText,
        column3: row.cells[2].innerText,
        column4: row.cells[3].innerText,
        column5: row.cells[4].innerText,
    };

    // 发送数据到后端
    fetch('/getImportData', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(rowData)
    }).then(response => {
        alert("已成功新增存貨至存貨清單！");
        window.location.href = "http://localhost:8080/checkReport";
    }).catch(error => {
        console.error('Error:', error);
    });
}

function importDelete(button){
    var row = button.parentNode.parentNode;
    if (confirm('你確定要刪除嗎？')) {
        // 創建要傳送的資料
        var rowData = {
            column1: row.cells[0].innerText,
            column2: row.cells[1].innerText,
            column3: row.cells[2].innerText,
            column4: row.cells[3].innerText,
            column5: row.cells[4].innerText,
        };

        fetch('/deleteImportData', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(rowData)
        }).then(response => {
            alert("已成功新增存貨至存貨清單！");
            window.location.href = "http://localhost:8080/checkReport";
        }).catch(error => {
            console.error('Error:', error);
        });
    }
}

function exportModifyStart(button){
    var row = button.parentNode.parentNode;
  
    // 获取该行中的进货数量单元格
    var numCell = row.cells[3];
  
    // 获取进货数量单元格中的 span 元素
    var numSpan = numCell.querySelector('span');
  
    // 创建一个输入框
    var numInput = document.createElement('input');
    numInput.type = 'number';
    numInput.value = numSpan.textContent.trim();
  
    // 将输入框替换掉原先的文本
    numCell.removeChild(numSpan);
    numCell.appendChild(numInput);
  
    // 聚焦输入框
    numInput.focus();
  
    // 将按钮的事件改为保存修改
    button.textContent = '保存修改';
    button.onclick = function() {
        exportModifyEnd(this);
    };
}

function exportModifyEnd(button) {
    // 找到按钮所在的行
    var row = button.parentNode.parentNode;

    // 获取该行中的进货数量单元格
    var numCell = row.cells[3];

    // 获取进货数量输入框
    var numInput = numCell.querySelector('input[type="number"]');

    // 获取输入框中的进货数量值并更新到 span 元素中
    numCell.innerHTML = '<span>' + numInput.value + '</span>';

    // 将按钮的事件改回编辑
    button.textContent = '修改資料';
    button.onclick = function() {
        exportModifyStart(this);
    };

    // 传递行对象给 sendImportData 函数
    sendExportData(row);
}

function sendExportData(row) {
    var rowData = {
        column1: row.cells[0].innerText,
        column2: row.cells[1].innerText,
        column3: row.cells[2].innerText,
        column4: row.cells[3].innerText,
        column5: row.cells[4].innerText,
    };

    // 发送数据到后端
    fetch('/getExportData', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(rowData)
    }).then(response => {
        alert("已成功新增存貨至存貨清單！");
        window.location.href = "http://localhost:8080/checkReport";
    }).catch(error => {
        console.error('Error:', error);
    });
}

function exportDelete(button){
    var row = button.parentNode.parentNode;
    if (confirm('你確定要刪除嗎？')) {
        // 創建要傳送的資料
        var rowData = {
            column1: row.cells[0].innerText,
            column2: row.cells[1].innerText,
            column3: row.cells[2].innerText,
            column4: row.cells[3].innerText,
            column5: row.cells[4].innerText,
        };

        fetch('/deleteExportData', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(rowData)
        }).then(response => {
            alert("已成功新增存貨至存貨清單！");
            window.location.href = "http://localhost:8080/checkReport";
        }).catch(error => {
            console.error('Error:', error);
        });
    }
}

function expenseModifyStart(button){
    var row = button.parentNode.parentNode;
  
    // 获取该行中的进货数量单元格
    var numCell = row.cells[3];
  
    // 获取进货数量单元格中的 span 元素
    var numSpan = numCell.querySelector('span');
  
    // 创建一个输入框
    var numInput = document.createElement('input');
    numInput.type = 'number';
    numInput.value = numSpan.textContent.trim();
  
    // 将输入框替换掉原先的文本
    numCell.removeChild(numSpan);
    numCell.appendChild(numInput);
  
    // 聚焦输入框
    numInput.focus();
  
    // 将按钮的事件改为保存修改
    button.textContent = '保存修改';
    button.onclick = function() {
        expenseModifyEnd(this);
    };
}

function expenseModifyEnd(button) {
    // 找到按钮所在的行
    var row = button.parentNode.parentNode;

    // 获取该行中的进货数量单元格
    var numCell = row.cells[3];

    // 获取进货数量输入框
    var numInput = numCell.querySelector('input[type="number"]');

    // 获取输入框中的进货数量值并更新到 span 元素中
    numCell.innerHTML = '<span>' + numInput.value + '</span>';

    // 将按钮的事件改回编辑
    button.textContent = '修改資料';
    button.onclick = function() {
        expenseModifyStart(this);
    };

    // 传递行对象给 sendImportData 函数
    sendExpenseData(row);
}

function sendExpenseData(row) {
    var rowData = {
        column1: row.cells[0].innerText,
        column2: row.cells[1].innerText,
        column3: row.cells[2].innerText,
        column4: row.cells[3].innerText,
    };

    // 发送数据到后端
    fetch('/getExpenseData', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(rowData)
    }).then(response => {
        alert("已成功新增存貨至存貨清單！");
        window.location.href = "http://localhost:8080/checkReport";
    }).catch(error => {
        console.error('Error:', error);
    });
}

function expenseDelete(button){
    var row = button.parentNode.parentNode;
    if (confirm('你確定要刪除嗎？')) {
        // 創建要傳送的資料
        var rowData = {
            column1: row.cells[0].innerText,
            column2: row.cells[1].innerText,
            column3: row.cells[2].innerText,
            column4: row.cells[3].innerText,
        };

        fetch('/deleteExpenseData', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(rowData)
        }).then(response => {
            alert("已成功新增存貨至存貨清單！");
            window.location.href = "http://localhost:8080/checkReport";
        }).catch(error => {
            console.error('Error:', error);
        });
    }
}

function sumAllAfterDelay() {
    setTimeout(sumAll, 100); // 推遲 100 毫秒後執行 sumAll 函數
}


function sumAll(){
        var num1 = document.getElementById('importTotalCost').innerText.trim();
        var num2 = document.getElementById('exportTotalRevenue').innerText.trim();
        var num3 = document.getElementById('expenseTotalCost').innerText.trim();

        var number1 = Number(num1);
        var number2 = Number(num2);
        var number3 = Number(num3);

        if (isNaN(number1) || isNaN(number2) || isNaN(number3)) {
            document.getElementById('totalRevenue').textContent = 'Error: One or more values are not valid numbers.';
            return;
        }
        else{
            var sum = number2-number1 - number3;
            document.getElementById('totalRevenue').textContent =sum;
        }
    
    

    
}