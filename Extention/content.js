// "matches": ["*://misteram.com.ua/*/cart*"],

class Parser {
    getDishes() {
        let ordersList = document.getElementsByClassName('item')
        let orders = []
        
        for (let i = 0; i < ordersList.length; i++) {
            let obj = {
                name: ordersList[i].querySelector('.name').textContent.replace(/(^\s*)|(\s*)$/g, ""),
                value: ordersList[i].querySelector('.price').firstChild.nextSibling.textContent     
            }
            orders.push(obj)
        }

        // console.log(orders)
        return orders;
    }

    getPacking() {
        let packing = document.getElementsByClassName('package-block');
        let obj = {
            name: packing[0].querySelector('.description').firstChild.nextSibling.textContent,
            value: packing[0].querySelector('.price').firstChild.nextSibling.textContent
        }
        
        return obj;
    }
}

class Orders {
    constructor(parser) {
        this.parser = parser;
    }
    getOrders() {
        let orders = this.parser.getDishes();
        orders.push(this.parser.getPacking());
        return orders
   }
}

// chrome.runtime.onMessage.addListener(
//     function(message, sender, sendResponse) {
//         console.log(message.type)
//         switch(message.type) {
//             case "parse":
//                 // console.log(new Orders(new Parser()).getOrders())      
//                 document.getElementsByClassName("item")[0].querySelector(".minus")
//                 sendResponse(new Orders(new Parser()).getOrders());
//             break;
//         }
//     }
// );

chrome.runtime.onMessage.addListener(
    function(message, sender, sendResponse) {
        switch(message.type) {
            case "getOrders":
                console.log('get')
                GetOrders()
                // .then((resp) => {
                //     alert(resp);
                //     sendResponse(resp);    
                // });
                .then(sendResponse);    
                // );
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
        console.log(resp)
        return resp
    });
};
