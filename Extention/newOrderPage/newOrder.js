let newOrderBtn = document.getElementById('newOrderBtn')
let newOrderNameInput = document.getElementById('newOrderInp')

newOrderBtn.addEventListener('click', () => {
    if (isValidInp(newOrderNameInput, 3)) {
        chrome.tabs.query({active: true, currentWindow: true}, function(tabs) {
            chrome.tabs.sendMessage(tabs[0].id, {type: "getOrders"}, function(response){
                response.name = newOrderNameInput.value;

                if (isValidInp(newOrderNameInput, 3)) {
                    SendOrders(response).then((resp) => {
                        if (resp.status === 200) {
                            return resp.json();
                        }
                    }).then((resp) => {
                        localStorage.code = resp.code
                        localStorage.name = newOrderNameInput.value;
                        window.location.href = '../menuPage/menu.html';
                    }).catch((error)=> { console.log(error) });
                }
            });
        });
    } else {
        if (!isValidInp(newOrderNameInput, 3)) {
            ShowWarning(newOrderNameInput, 'Ім\'я має бути більше 3 символів');
        }
    }
});

async function SendOrders(resp) {
    return await fetch('http://localhost:8080/orders', {
        method: 'post',
        body: JSON.stringify(resp)
    }).then((resp) => resp);
}


newOrderNameInput.addEventListener('blur', () => {
    if (!isValidInp(newOrderNameInput, 3)) {
        ShowWarning(newOrderNameInput, 'Ім\'я має бути більше 3 символів');
    } 
    else {
        if(newOrderNameInput.classList.contains('input_invalid')) {
            DeleteWarning(newOrderNameInput);
        }
    }
})


function isValidInp(inp, min) {
    console.log(inp);
    console.log(inp.value);
    if (inp.value.length > min) {
        return true;
    }
    return false;
}

function ShowWarning(inp, text) {
    if (inp.classList.contains('input_invalid')) {
        DeleteWarning(inp);
    }
    
    inp.classList.add('input_invalid');
    let warning = document.createElement('span');
    warning.classList.add('warning-text')
    warning.textContent = text;
    inp.after(warning);
}

function DeleteWarning(inp) {
    inp.classList.remove('input_invalid')
    inp.nextSibling.remove();
}