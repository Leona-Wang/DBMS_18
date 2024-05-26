document.addEventListener('DOMContentLoaded', function(){

    fetch('/showExpenseList')
            .then(response => response.json())
            .then(data => {
                const tableBody = document.getElementById('expenseTable').getElementsByTagName('tbody')[0];
                tableBody.innerHTML = ''; // 清空现有的表格内容
                data.forEach(expense => {
                    let row = tableBody.insertRow();
                    let cell1 = row.insertCell(0);
                    let cell2 = row.insertCell(1);
                    let cell3 = row.insertCell(2);
                    let cell4 = row.insertCell(3);
                    cell1.textContent = expense.index;
                    cell2.textContent = expense.date;
                    cell3.textContent = expense.type;
                    cell4.textContent = expense.cost;
                });
            })
            .catch(error => console.error('Error fetching product data:', error));


})