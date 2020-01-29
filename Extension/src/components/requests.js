let path = 'http://localhost:8080/orders/'

export async function SendAllUsersDishesRequest(data) {
    return await fetch(path, {
        method: 'POST',
        body: JSON.stringify(data)
    });
}

export async function SendGetOrdersListRequest(code) {
    return fetch(path + code + '/show-group-order', {});
}

export async function SendGetAllDishesRequest(code) {
    return fetch(path + code + '/form-group-order', {});
}

export async function SendConnectWithCodeRequest(code) {
    return fetch (path + code + '/connect', {
        method: 'POST'
    });
}
