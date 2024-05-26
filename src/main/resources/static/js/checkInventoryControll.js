document.addEventListener('DOMContentLoaded', function(){

    fetch('/checkInventoryList')
            .then(response => response.json())
            .then(data => {
                const tableBody = document.getElementById('adjustInventoryTable').getElementsByTagName('tbody')[0];
                tableBody.innerHTML = ''; // 清空现有的表格内容
                data.forEach(product => {

                    let input = document.createElement('input');
                    input.type = 'number';
                    input.max=product.inventoryAmount;
                    input.min=0;
                    input.value = product.inventoryAmount;
                    input.onchange=function(){
                        if (input.value > product.inventoryAmount) {
                            input.value = product.inventoryAmount;
                        }
                        if (input.value < 0) {
                            input.value = 0;
                        }
                    }
                    input.name = 'inventoryAmount';
                    
                    let row = tableBody.insertRow();
                    let cell1 = row.insertCell(0);
                    let cell2 = row.insertCell(1);
                    let cell3 = row.insertCell(2);

                    cell1.textContent = product.productIndex;
                    cell1.appendChild(document.createElement('input')).type = 'hidden';
                    cell1.lastChild.value = product.productIndex;
                    cell1.lastChild.name = 'productIndex';

                    cell2.textContent = product.productName;
                    cell2.appendChild(document.createElement('input')).type = 'hidden';
                    cell2.lastChild.value = product.productName;
                    cell2.lastChild.name = 'productName';
                    
                    //cell3.textContent = product.inventoryAmount;
                    cell3.appendChild(input);
                });
            })
            .catch(error => console.error('Error fetching product data:', error));


})

function submitCheckInventoryForm() {
    const form = document.getElementById('checkInventoryForm');
    const formData = new FormData(form);

    // 使用 fetch 提交表单数据
    fetch('/adjustInventoryList', {
        method: 'POST',
        body: new URLSearchParams(formData)
    })
    .then(response => response.text())
    .then(data => {
        alert("已成功新增銷貨至存貨清單！");
        window.location.href = "http://localhost:8080/checkInventory";
    })
    .catch(error => {
        console.error('Error submitting form:', error);
    });
}

function maxLimit(input,limit) {
    if (input.value > limit.value) {
      input.value = limit.value;
    }
  }