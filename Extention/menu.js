let addOrder = document.getElementById('menu-addOrder');
let showOrders = document.getElementById('menu-showOrder');
let formOrder = document.getElementById('menu-formOrder');
let code = document.getElementById('menu-code');
let respContainer = document.getElementById('menu-responseContainer');
code.textContent = localStorage.code

addOrder.addEventListener('click', () => {
    chrome.tabs.query({active: true, currentWindow: true}, function(tabs) {
        chrome.tabs.sendMessage(tabs[0].id, {type: "getOrders"}, function(response){
            // SendOrders(response)
            // .then((resp) => resp.json())
            // .then((resp) => {
                ClearContainer(respContainer);
                let info = document.createElement('div');
                info.textContent = 'заказ (не) добавлено'//resp
                respContainer.append(info);
                document.body.append(respContainer)
            // })
            // .catch((error)=> { console.log(error) })
        });
    });
});

showOrders.addEventListener('click', () => {
    GetOrdersList()
    .then((resp) => {
        // if (resp.status)
        
        ClearContainer(respContainer);
        let list = document.createElement('ol');
        list.classList.add('menu-list')

        resp.forEach((order)=> {
            let item = document.createElement('li');

            let header = document.createElement('div');
            let name = document.createElement('span');
            let bill = document.createElement('span');

            name.textContent = order.name;
            bill.textContent = order.bill;
            header.append(name, bill);

            let body = document.createElement('ol');
            body.classList.add('menu-list')

            order.items.forEach((item) => {
                let dish = document.createElement('li');
                dish.textContent = item.name;
                body.append(dish);
            });
            
            body.classList.add('display_none');

            header.addEventListener('click', () => {
                body.classList.toggle('display_none');
            })
            item.append(header, body);
            list.append(item);    
        })
        respContainer.append(list);

    })
    .catch((error) => { console.log(error) })
})


formOrder.addEventListener('click', () => {
    chrome.tabs.query({active: true, currentWindow: true}, function(tabs) {
        chrome.tabs.sendMessage(tabs[0].id, {type: 'formOrder', code: localStorage.code}, function(response){
        });
    });
});


async function GetOrdersList() {
    // return fetch('', {

    // })
    // .then((resp) => resp)
    // .catch((error) => { console.log(error) })
    return [
        {
            name:'name1',
            bill: '2154',
            items: [
                {name: 'item0'}
            ] 
        },
        {
            name:'name2',
            bill: '2154',
            items: [
                {name: 'item1'},
                {name: 'item2'},
                {name: 'item3'}
            ]
        }
    ]
}

async function SendOrders(resp) {
    return await fetch('http://localhost:8080/orders', {//add-orders
        method: 'post',
        body: JSON.stringify(resp)
    }).then((resp) => resp);
}

function ClearContainer(container) {
    while (container.firstChild) {
        container.removeChild(container.firstChild);
    }
}


