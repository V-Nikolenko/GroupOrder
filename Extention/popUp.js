btn1.addEventListener('click', () => {
    let name = newOrdinp.value;
    // alert(name);
    chrome.tabs.query({active: true, currentWindow: true}, function(tabs) {
        chrome.tabs.sendMessage(tabs[0].id, {type: "getOrders"}, function(response){
            response.name = name;
            SendOrders(response);
        });
    });
});

async function SendOrders(resp) {
    console.log(typeof resp)
    console.log(typeof JSON.stringify(resp))
    await fetch("http://localhost:8080/orders", {
        headers: {

        },
        // mode: "no-cors",
        method: "post",
        body: JSON.stringify(resp)
    })
}