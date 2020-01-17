chrome.runtime.onMessage.addListener(
    function(message, sender, sendResponse) {
        switch(message.type) {
            case "getOrders":
                console.log('get')
                GetOrders()
                .then(sendResponse);  
                break;
        }
        return true;
    }
)

async function GetOrders() {
    return await fetch("https://misteram.com.ua/api/cart/get?lang=ua")
    .then((resp) => {
        return resp.json()
    })
    .then((resp) => {
        // console.log(resp)
        return resp
    });
};


async function SendAddDishRequest(dish) {
    let bodyoObj = {
        force: false,
        action: "add",
        dishId: 88251,
        optionValue: null,
        optionId: 0,
        measure: "30",
        measureType: "0",
        packagePrice: 0,
        maxCountPositionInPackage: 1
    }

    await fetch("https://misteram.com.ua/api/cart/add?lang=ua", {
        headers: {
            "accept": "application/json, text/plain", // add this
            "content-type": "application/json;charset=UTF-8"
        },
        method: "post",
        body: JSON.stringify(bodyoObj)
    });
}

SendAddDishRequest();