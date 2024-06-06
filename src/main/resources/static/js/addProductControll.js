function submitAddProductForm() {
    const form = document.getElementById('addProductListForm');
    const formData = new FormData(form);

    // 使用 fetch 提交表单数据
    fetch('/addProductList', {
        method: 'POST',
        body: new URLSearchParams(formData)
    })
    .then(response => response.text())
    .then(data => {
        alert("已成功新增貨物種類至產品清單！");
        window.location.href = "http://localhost:8080/addProduct";
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