let path = 'http://localhost:8080/orders'

export async function sendCreateNewOrderRequest(obj) {
    return await fetch(path, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json' 
        },
        body: JSON.stringify({
            restaurantUrl: obj.url,
            restaurantName: obj.name
        })
    });
}


export async function sendConnectWithCodeRequest(code) {
    return fetch (path + '/' + code + '/connect', {
        method: 'POST'
    });
}


export async function sendMemberOrder(code, body) {
    return fetch(path + '/' + code + '/add-member-order', {
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(body)
    });
}


export async function sendGetOrdersListRequest(code) {
    return fetch(path + '/' + code + '/show-group-order');
}


export async function sendGetAllDishesRequest(code) {
    return fetch(path + '/' + code + '/form-group-order');
}


export async function sendRemoveMemberFromOrder(code) {
    return fetch (path + '/' + code + '/remove-from-order', {
        method: 'POST',
        body: {} //Name, email, url
    })
}


export async function sendLockOrderRequest() {
   return fetch (path + '/' + code +'/lock-group-order', {
       method: 'POST'
   });
}


export async function sendGetSplitBillData(code) {
    return fetch (path + '/' + code + '/group-order-debt');
}