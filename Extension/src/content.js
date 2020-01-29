chrome.runtime.onMessage.addListener(
    function(message, sender, sendResponse) {
        switch(message.type) {
            case 'getOrders':
                // console.log('get');
                SendGetOrdersRequest().then(sendResponse);  
                break;
            case 'formOrder':
                // console.log('form');
                // console.log(message);
                FormOrder(message.allDishes.items);
                break;
        }
        return true;
    }
)

async function SendGetOrdersRequest() {
    return await fetch("https://misteram.com.ua/api/cart/get?lang=ua")
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

    return fetch("https://misteram.com.ua/api/cart/add?lang=ua", {
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
    return fetch('https://misteram.com.ua/api/cart/remove?lang=ua', {
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
    await ClearCurrentOrder()

    for (let i = 0; i < items.length; i++) { 
        let item =  items[i];
        for (let j = 0; j < item.count; j++) {
            await SendAddDishRequest(item);
        }
    }
}
