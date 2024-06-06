function submitAddExpenseForm() {
    const form = document.getElementById('addExpenseForm');
    const formData = new FormData(form);

    // 使用 fetch 提交表单数据
    fetch('/addExpenseList', {
        method: 'POST',
        body: new URLSearchParams(formData)
    })
    .then(response => response.text())
    .then(data => {
        alert("已成功新增費用至費用清單！");
        window.location.href = "http://localhost:8080/addExpense";
    })
    .catch(error => {
        console.error('Error submitting form:', error);
    });
}

function minLimit(input){
    if (input.value < 0) {
        input.value = 0;
    }
}