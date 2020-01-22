let newOrderBtn = document.getElementById('newOrderBtn')
let newOrderNameInput = document.getElementById('newOrderInp')

newOrderBtn.addEventListener('click', () => {
    if (isValidInp(newOrderNameInput, 3)) {
        chrome.tabs.query({active: true, currentWindow: true}, function(tabs) {
            chrome.tabs.sendMessage(tabs[0].id, {type: "getOrders"}, function(response){
                response.name = newOrderNameInput.value;

                if (isValidInp(newOrderNameInput, 3)) {
                    SendOrders(response).then((resp) => {
                        if (resp.ok) {
                            return resp.json();
                        }
                        else {
        
                        }
                    }).then((resp) => {
                        localStorage.code = resp.code
                        localStorage.name = newOrderNameInput.value;
                        window.location.href = './menu.html';
                    }).catch((error)=> { console.log(error) });
                }
            });
        });
    } else {
        ShowWarning(newOrderNameInput, 'Ім\'я повинно містити більше 3 букв');
    }
});

async function SendOrders(resp) {
    return await fetch('http://localhost:8080/orders', {
        method: 'post',
        body: JSON.stringify(resp)
    }).then((resp) => resp);
}

function validateInp(inp) {
    if (!inp.value) {
        
    }
}

function isValidInp(inp, min) {
    if (inp.value.length < min) {
        return false
    }
    return true
}

function ShowWarning(inp, text) {
    if (inp.classList.contains('input_invalid')) {
        inp.classList.remove('input_invalid');
        inp.nextSibling.remove()
    }
    
    inp.classList.add('input_invalid');
    let warning = document.createElement('span');
    warning.textContent = text//"Ім'я повино бути більше 3 букв";
    inp.after(warning);
}