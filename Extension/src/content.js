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
        console.log(items);
        for (let i = 0; i < items.length; i++) { 
            let item = items[i];
            for (let j = 0; j < item.count; j++) {
                await SendAddDishRequest(item);
            }
        }
        return true;
        
    } catch(error) {
        console.log(error)
    }
}

