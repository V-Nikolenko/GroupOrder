btn1.addEventListener('click', () => {
    let name = newOrdinp.value;
    chrome.tabs.query({active: true, currentWindow: true}, function(tabs) {
        chrome.tabs.sendMessage(tabs[0].id, {type: "getOrders"}, function(response){
            
            response.name = name;
            SendOrders(response).then((resp) => {
                console.log(resp);
                
                // if(resp.status === 200) {
                //     let codeContainer = document.createElement('p');
                //     codeContainer.textContent = //resp.code
                //     document.body.after(codeContainer)
                // }
            });

            // fetch("")
        });
    });
});

async function SendOrders(resp) {
    // console.log(typeof resp)
    // console.log(typeof JSON.stringify(resp))
    return await fetch("http://localhost:8080/orders", {
        headers: {},
        // mode: "no-cors",
        method: "post",
        body: JSON.stringify(resp)
    }).then((resp)=> resp);
}


function validateInp(inp) {
    if (!inp.value) {
        
    }
}

