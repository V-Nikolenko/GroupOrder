let newOrderBtn = document.getElementById('newOrderBtn')

newOrderBtn.addEventListener('click', () => {
    let name = newOrdinp.value;
    newOrdinp.value = "";
    chrome.tabs.query({active: true, currentWindow: true}, function(tabs) {
        chrome.tabs.sendMessage(tabs[0].id, {type: "getOrders"}, function(response){
            
            response.name = name;
            SendOrders(response).then((resp) => {
                if (resp.status === 200) {
                    return resp.text()
                }
                else throw new Error('bad response')
            }).then((resp) => {
                console.log(resp);
                code.textContent = 'Код вашого заказу: ' + resp;
            }).catch((error)=> { console.log(error) })
        });
    });
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




