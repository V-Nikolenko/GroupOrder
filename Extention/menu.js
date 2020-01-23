let addOrder = document.getElementById('menu-addOrder');
let showOrders = document.getElementById('menu-showOrder');
let formOrder = document.getElementById('menu-formOrder');
let code = document.getElementById('menu-code');
let respContainer = document.getElementById('menu-responseContainer');
code.textContent = localStorage.code

addOrder.addEventListener('click', () => {
    chrome.tabs.query({active: true, currentWindow: true}, function(tabs) {
        chrome.tabs.sendMessage(tabs[0].id, {type: "getOrders"}, function(response){
            response.name = localStorage.name;

            console.log(response)
            SendOrders(response, localStorage.code)
            .then((resp) => resp.json())
            .then((resp) => {
                console.log(resp)
                Clear(respContainer, false);
                let info = document.createElement('div');
                info.textContent = resp.message //'заказ (не) добавлено'//resp
                respContainer.append(info);
                document.body.append(respContainer)
            })
            .catch((error)=> { console.log(error) })
        });
    });
});

showOrders.addEventListener('click', () => {
    GetOrdersList()
    .then((resp) => {
        // if (resp.status)
        
        Clear(respContainer, false);
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

    let confirmation = document.createElement('div');
    confirmation.classList.add('confirmation');

    let confirmationWindow = document.createElement('div');
    confirmationWindow.classList.add('confirmation__window');

    let title = document.createElement('h2');
    title.classList.add('heading');
    title.textContent = 'Ви дійсно бажаєте сформувати заказ?'

    let confirmationConfirm = document.createElement('button');
    confirmationConfirm.classList.add('btn');
    confirmationConfirm.textContent = 'Так';

    let confirmationCancel = document.createElement('button');
    confirmationCancel.classList.add('btn');
    confirmationCancel.textContent = 'Ні';


    confirmationConfirm.addEventListener('click', () => {
        chrome.tabs.query({active: true, currentWindow: true}, function(tabs) {
            chrome.tabs.sendMessage(tabs[0].id, {type: 'formOrder', code: localStorage.code}, function(response){
            });
        });
        Clear(confirmation, true);
    });    

    confirmationCancel.addEventListener('click', () => {
        Clear(confirmation, true);
    })

    confirmationWindow.append(title, confirmationConfirm, confirmationCancel)
    confirmation.append(confirmationWindow)
    document.body.append(confirmation);
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

async function SendOrders(resp, code) {
    return await fetch('http://localhost:8080/orders/'+code+'/add-order', {//add-orders
        method: 'POST',
        body: JSON.stringify(resp)
    }).then((resp) => resp);
}

function Clear(root, includeItself) {
    while (root.firstChild) {
        root.removeChild(root.firstChild);
    }
    if (includeItself) {
        root.parentNode.removeChild(root)
    }
}

