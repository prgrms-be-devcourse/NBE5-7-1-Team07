
const cart = {};

function updateSummary() {
    const summary = document.getElementById('summary-items');
    const totalPriceElem = document.getElementById('total-price');
    summary.innerHTML = '';
    let total = 0;

    for (const [name, { count, price, id }] of Object.entries(cart)) {
        total += count * price;
        const item = document.createElement('div');
        item.classList.add('row');
        item.innerHTML = `
      <h6>${name} <span class="badge bg-dark">${count}개</span>
      <button class="btn btn-sm btn-outline-danger ms-2 remove-btn" data-name="${name}">-</button></h6>
    `;
        summary.appendChild(item);
    }

    totalPriceElem.textContent = `${total}원`;
    updateHiddenInputs();
}

function updateHiddenInputs() {
    const container = document.getElementById('hidden-order-inputs');
    container.innerHTML = '';

    const entries = Object.entries(cart);
    entries.forEach(([name, { id, count }], index) => {
        const inputId = document.createElement('input');
        inputId.type = 'hidden';
        inputId.name = `products[${index}].productId`;
        inputId.value = id;
        container.appendChild(inputId);

        const inputQty = document.createElement('input');
        inputQty.type = 'hidden';
        inputQty.name = `products[${index}].quantity`;
        inputQty.value = count;
        container.appendChild(inputQty);
    });
}

document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('product-list').addEventListener('click', function (e) {
        if (e.target.classList.contains('add-btn')) {
            const item = e.target.closest('li');
            const name = item.dataset.name;
            const price = parseInt(item.dataset.price);
            const id = parseInt(item.dataset.id);
            if (!cart[name]) cart[name] = { count: 0, price, id };
            cart[name].count++;
            updateSummary();
        }
    });

    document.getElementById('summary-items').addEventListener('click', function (e) {
        if (e.target.classList.contains('remove-btn')) {
            const name = e.target.dataset.name;
            if (cart[name]) {
                cart[name].count--;
                if (cart[name].count <= 0) delete cart[name];
                updateSummary();
            }
        }
    });
});

document.addEventListener("DOMContentLoaded", () => {
    const orderForm = document.getElementById("order-form");
    const totalPrice = document.getElementById("total-price");
    const confirmTotal = document.getElementById("confirm-total-price");
    const confirmItems = document.getElementById("confirm-items");
    const confirmButton = document.getElementById("confirm-submit");

    orderForm.addEventListener("submit", function (e) {
        e.preventDefault(); // 기본 제출 막기

        if (Object.keys(cart).length === 0) {
            alert("주문할 상품을 1개 이상 선택해주세요.");
            return;
        }


        confirmItems.innerHTML = document.getElementById("summary-items").innerHTML;
        confirmTotal.innerText = totalPrice.innerText;


        const modal = new bootstrap.Modal(document.getElementById('confirmModal'));
        modal.show();


        confirmButton.onclick = function () {
            orderForm.submit();
        };
    });
});