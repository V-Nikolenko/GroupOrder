// let path = 'http://localhost:8080';
let path = 'https://group-order.herokuapp.com';

export async function sendCreateNewOrderRequest(obj) {
    return await fetch(path + '/orders', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json' 
        },
        body: JSON.stringify({
            restaurantUrl: obj.url,
            restaurantName: obj.name,
            restaurantId: obj.companyId
        })
    });
}


export async function sendConnectWithCodeRequest(code) {
    return fetch (path  + '/orders/' + code + '/connect', {
        method: 'POST'
    });
}


export async function sendMemberOrder(code, body) {
    return fetch(path + '/orders/' + code + '/add-member-order', {
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(body)
    });
}


export async function sendGetOrdersListRequest(code) {
    return fetch(path + '/orders/' + code + '/show-group-order');
}


export async function sendGetAllDishesRequest(code) {
    return fetch(path + '/orders/' + code + '/form-group-order');
}


export async function sendRemoveMemberFromOrder(code, id) {
    return fetch (path + '/orders/' + code + '/' +id, {
        method: 'DELETE',
    });
}


export async function sendLockOrderRequest() {
   return fetch (path + '/orders/' + code +'/lock-group-order', {
       method: 'POST'
   });
}


export async function sendGetSplitBillData(code) {
    return fetch (path + '/orders/' + code + '/group-order-debt');
}

export async function sendOrderStateRequest(code, isLocked, method = null) {
    console.log(method)
    if(method === null) {
        // let method;
        if (isLocked) method = 'DELETE';
        else method = 'PUT';
    }

    return fetch(path + '/orders/' + code + '/lock-group-order', {
        method: method
    });
}