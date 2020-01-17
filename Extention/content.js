// "matches": ["*://misteram.com.ua/*/cart*"],
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
        return resp
    });
};
