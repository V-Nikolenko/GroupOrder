let url = new URL (window.location.href);

if (url.searchParams.get('code')) {
    
    localStorage.setItem('connectCode', url.searchParams.get('code'))
}

chrome.runtime.onMessage.addListener(
    function(message, sender, sendResponse) {
        switch(message.type) {
            case 'getOrders':
                SendGetOrdersRequest().then(sendResponse);  
                break;

            case 'formOrder':
                FormOrder(message.resp.items).then(sendResponse);
                break;

            case 'reload': 
                window.location.reload();
                break;

            case 'restaurant': 
                getRestaurant().then(sendResponse);
                break;

            case 'getURLcode':
                let code = localStorage.connectCode;
                localStorage.connectCode = "";
                sendResponse(code);
                break;
        }
        return true;
    }
)

let misteramPath = 'https://misteram.com.ua';

async function SendGetOrdersRequest() {
    return await fetch(misteramPath + '/api/cart/get?lang=ua')
    .then((resp) => {
        if (resp.status === 200) {
            return resp.json();
        } else throw new Error("bad response from get orders")
    })
    .then((resp) => {
        resp.url = window.location.href;
        return resp
    })
    .catch((error) => { console.log(error) })
};


async function SendAddDishRequest(dish) {
    let bodyoObj = {
        action: "add",
        dishId: dish.id
    }

    return fetch( misteramPath + '/api/cart/add?lang=ua', {
        headers: {
            "content-type": "application/json;charset=UTF-8"
        },
        method: "POST",
        body: JSON.stringify(bodyoObj)
    });
}

async function SendRemoveDishRequest(dish) {
    let obj = {
        action: "remove",
        dishId: dish.id,
        optionId: dish.optionId,
        optionValue: dish.optionValue
    }
    return fetch(misteramPath + '/api/cart/remove?lang=ua', {
        headers: {
            "content-type": "application/json;charset=UTF-8",  
        },
        method: 'POST',
        body: JSON.stringify(obj)
    })
    .then((resp) => {
        if (resp.status === 200) {
            return resp.json();
        } else throw new Error('bad response from remove dish');
    })
    .then((resp) => resp)
    .catch((error)=> { console.log(error) })
}

async function ClearCurrentOrder() {
    let order = await SendGetOrdersRequest()
    for (let i = 0; i < order.items.length; i++) {
        let item = order.items[i];
        for(let j = 0; j < item.count; j++) {
            await SendRemoveDishRequest(item);
        }
    }
}

async function FormOrder(items) {
    try {

        await ClearCurrentOrder()

        //do this at backend
        let quantity = 0;
        for (let i = 0; i < items.length; i++) {
            quantity += items[i].count;
        }

        chrome.runtime.sendMessage({type: "quantity", quantity: quantity})
        

        for (let i = 0; i < items.length; i++) { 
            let item = items[i];

            for (let j = 0; j < item.count; j++) {
                await SendAddDishRequest(item);
                chrome.runtime.sendMessage({type: "added"})
            }
        }
        return true;
        
    } catch(error) {
        console.log(error)
        return false;
    }
}

async function getRestaurant() {
    let url = window.location.href
    let companyContainer = document.getElementsByClassName('company-name')[0];
    let name;

    if (url.includes('cart')) {
        name = companyContainer.getElementsByTagName("a")[0].textContent;
        url = companyContainer.getElementsByTagName('a')[0].href;
    } else {
        name = companyContainer.getElementsByTagName('p')[0].textContent;
    }
    
    return {url: url, name: name}
}