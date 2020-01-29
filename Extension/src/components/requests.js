path = 'http://localhost:8080/orders/'

async function SendAllUsersDishes(resp) {
    return await fetch(path, {
        method: 'POST',
        body: JSON.stringify(resp)
    });
}

async function GetOrdersList(code) {
    return fetch(path + code + '/show-group-order', {});
}

async function GetAllDishes(code) {
    return fetch(path + code + '/form-group-order', {});
}

async function ConnectWithCode(code) {
    return fetch (path + code + '/connect', {
        method: 'POST'
    });
}
