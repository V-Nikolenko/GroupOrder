chrome.runtime.onMessage.addListener(
    function(message, sender, sendResponse) {
        switch(message.type) {
            case 'getOrders':
                console.log('get')
                GetOrders()
                .then(sendResponse);  
                break;
            case 'addOrder':
                console.log('add')
                 
        }
        return true;
    }
)

async function GetOrders() {
    return await fetch("https://misteram.com.ua/api/cart/get?lang=ua")
    .then((resp) => {
        if (resp.status === 200) {
            return resp.json();
        } else throw new Error("bad response from get orders")
    })
    .then((resp) => resp)
    .catch((error) => { console.log(error) })
};


async function SendAddDishRequest(dish) {
    let bodyoObj = {
        force: false,
        action: "add",
        dishId: dish.id,
        optionValue: dish.optionValue,
        optionId: dish.optionId,
        measure: dish.measure,
        measureType: dish.measureType,
        packagePrice: dish.packagePrice,
        maxCountPositionInPackage: dish.maxCountPositionInPackage
    }

    await fetch("https://misteram.com.ua/api/cart/add?lang=ua", {
        headers: {
            "content-type": "application/json;charset=UTF-8"
        },
        method: "POST",
        body: JSON.stringify(bodyoObj)
    });
}

// SendAddDishRequest()

async function ClearCurrentOrder() {
 
    GetOrders()
    .then((resp) => {
        resp.items.forEach(element => {
            
            setTimeout(function request() {
                if (element.count !== 0){
                    element.count--;
                    setTimeout(request, 50);
                }
                RemoveDish(element)
            }, 50);
        });
    })
}

async function RemoveDish(dish) {
    let obj = {
        action: "remove",
        dishId: dish.id,
        optionId: dish.optionId,
        optionValue: dish.optionValue
    }

    return await fetch('https://misteram.com.ua/api/cart/remove?lang=ua', {
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

// ClearCurrentOrder()