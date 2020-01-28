let addOrder = document.getElementById('menu-addOrder');
let showOrders = document.getElementById('menu-showOrder');
let formOrder = document.getElementById('menu-formOrder');
let code = document.getElementById('menu-code');
let main = document.getElementById('menu-content');
let responseContainer = document.createElement('div');

let ordersContainer = document.createElement('div');
ordersContainer.classList.add('menu__orders-container')


code.textContent = localStorage.code;

addOrder.addEventListener('click', () => {
    chrome.tabs.query({active: true, currentWindow: true}, function(tabs) {
        chrome.tabs.sendMessage(tabs[0].id, {type: "getOrders"}, function(response){
            response.name = localStorage.name;

            console.log(response)
            SendOrders(response, localStorage.code)
            .then((resp) => {
                // if (responseContainer.firstChild) {
                //     Clear(responseContainer, false)
                // }
                Clear(ordersContainer, false);
                
                let info = document.createElement('p');
                
                if (resp.status === 200) {
                    info.textContent = 'Заказ додано!';
                    info.classList.add('menu__text-success');    
                } else {
                    info.textContent = 'Виникла помилка!'
                    info.classList.add('menu__text-error');
                }
                responseContainer.append(info);
                setTimeout(() => {
                    info.remove()
                }, 1000);
                main.after(responseContainer);
            })
            .catch((error)=> { console.log(error) })
        });
    });
});

showOrders.addEventListener('click', () => {

    GetOrdersList(localStorage.code)
    .then((resp) => {
        return resp.json();
    }).then((resp)=> {    
        // if (document.getElementById('menu-content').firstChild) {
        //     alert()
        // }

        Clear(ordersContainer, false);

        let list = document.createElement('ol');
        list.classList.add('menu__list')
        
        resp.members.forEach((order)=> {
            // console.log(order)
            let item = document.createElement('li');
            item.classList.add('show-order__user');

            let header = document.createElement('div');
            let name = document.createElement('span');
            let bill = document.createElement('span');

            name.textContent = order.name;
            // bill.textContent = order.bill;
            header.append(name/*, bill*/);

            let body = document.createElement('ol');
            body.classList.add('menu__list');

            order.products.forEach((item) => {
                let dish = document.createElement('li');
                dish.textContent = item.name;
                body.append(dish);
            });
            
            body.classList.add('display_none');

            header.addEventListener('click', () => {
                body.classList.toggle('display_none');
            });

            item.append(header, body);
            list.append(item);    
        })
        
        ordersContainer.append(list);
        showOrders.after(ordersContainer)

    })
    .catch((error) => { console.log(error) })
})


formOrder.addEventListener('click', () => {

    let confirmation = document.createElement('div');
    confirmation.classList.add('confirmation');

    let confirmationWindow = document.createElement('div');
    confirmationWindow.classList.add('confirmation__window');

    let title = document.createElement('h2');
    title.classList.add('heading', 'heading_size_md', 'confirm__heading');
    title.textContent = 'Ви дійсно бажаєте сформувати замовлення?';
    
    let exit = document.createElement('span');
    exit.innerHTML = '&#10006';
    exit.classList.add('confirm__exit');
    
    let btnsContainer = document.createElement('div');
    btnsContainer.classList.add('confirm__btns-container');

    let confirmationConfirm = document.createElement('button');
    confirmationConfirm.classList.add('btn', 'confirmation__btn');
    confirmationConfirm.textContent = 'Так';

    let confirmationCancel = document.createElement('button');
    confirmationCancel.classList.add('btn', 'confirmation__btn');
    confirmationCancel.textContent = 'Ні';


    confirmationConfirm.addEventListener('click', async () => {
        Clear(confirmation, true);
        
        let menuContent = document.getElementById('menu-content');
        menuContent.classList.add('display_none');

        let loader = document.createElement('div')
        
        for (let i = 0; i < 4; i++) {
            let circle = document.createElement('div');
            loader.append(circle);
        }

        loader.classList.add('lds-ellipsis');
        
        let allDishes = await GetAllDishes(localStorage.code).then((resp) => resp.json())
        
        chrome.tabs.query({active: true, currentWindow: true}, function(tabs) {
            chrome.tabs.sendMessage(tabs[0].id, {type: 'formOrder', allDishes}, function(response){
                window.close();
            });
        });

        document.body.append(loader);

    });    

    confirmationCancel.addEventListener('click', () => {
        Clear(confirmation, true);
    });

    exit.addEventListener('click', () => {
        Clear(confirmation, true);
    });

    btnsContainer.append(confirmationConfirm, confirmationCancel)
    confirmationWindow.append(title, exit, btnsContainer);
    confirmation.append(confirmationWindow);
    document.body.append(confirmation);

});


async function SendOrders(resp, code) {
    return await fetch('http://localhost:8080/orders/'+code+'/add-order', {
        headers: {
            'Content-Type': 'application/json'
        },
        method: 'POST',
        body: JSON.stringify(resp)
    }).then((resp) => resp);
}


async function GetOrdersList(code) {
    return fetch('http://localhost:8080/orders/' + code + '/show-group-order', {})
}


async function GetAllDishes(code) {
    return fetch('http://localhost:8080/orders/' + code + '/form-group-order', {})
}


function Clear(root, includeItself) {
    while (root.firstChild) {
        root.removeChild(root.firstChild);
    }
    if (includeItself) {
        root.parentNode.removeChild(root)
    }
}
