function submitAddImportForm() {
    const form = document.getElementById('addImportForm');
    const formData = new FormData(form);

    // 使用 fetch 提交表单数据
    fetch('/addImport', {
        method: 'POST',
        body: new URLSearchParams(formData)
    })
    .then(response => response.text())
    .then(data => {
        alert("已成功新增存貨至存貨清單！");
        window.location.href = "http://localhost:8080/addInventory";
    })
    .catch(error => {
        console.error('Error submitting form:', error);
    });
}

fetch('/inventoryOptions')
        .then(response => response.json())
        .then(data => {
            // 获取下拉式选单的元素
            const dropdown = document.getElementById('importName');

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

function minLimit(input){
    if (input.value < 0) {
        input.value = 0;
    }
}