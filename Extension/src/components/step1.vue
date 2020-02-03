<template>
<div class="chooseRestaurant">

    <step-header 
        v-bind:title="step.title"
        v-bind:isDone="step.isDone"
        v-bind:isActive="step.isActive">
    </step-header>


    <section v-show="step.isActive" class="active-block">
        
        <ol class="list">
            <li v-for="(subStep, index) in subSteps" class="list-item" v-bind:key="index">{{ '1.' + (index + 1) + ". " + subStep }}</li>
        </ol>


        <div class="container">

            <div class="newOrderContainer">
                <button class="btn" v-on:click="newOrder()">Створити замовлення</button>
                <p class="error">{{ newOrderError }}</p>
            </div>

            <span class="delimeter">або</span>
            
            <div class="conncetionContainer">
                <input v-model="inputCode" 
                    type="text" 
                    name="code"
                    v-bind:class="[{'input_error': isConnectionError}, 'input']"
                    placeholder="Код замовлення"
                    v-on:focus="isConnectionError = false"
                    >
                    <!-- v-show="isConnectionError" -->
                <p  class="error">{{ newConnectionError }}</p>
            </div>

            <button class="btn" v-on:click="connectWithCode(inputCode)">
                Приєднатися до існуючого замовлення
            </button>
        
        </div>

    </section>

    
    <section v-show="step.isDone" class="step step-result-container">

        <a href="#" class="link">{{ step.data.restaurant }}({{ step.data.code}}) </a>
        <!-- TODO: add restaurat -->
        <div>
            <img src="/images/copy.png" alt="Копіювати" title="Копіювати" class="img copy-img" v-on:click="copy">
            <img src="/images/logout.png" alt="Вийти" title="Вийти" class="img logout-img" v-on:click="logOut"> 
        </div>

    </section>
    
</div>
</template>

<script>
import {sendConnectWithCodeRequest, sendCreateNewOrderRequest} from './requests.js';
import stepHeader from './stepHeader.vue';
import {stepFactory} from './stepService.js';

export default {
    name: 'step1',
    props: ['step'],
    components: {
        stepHeader
    },
    
    data() {
        return {
            service: stepFactory.service,
            inputCode: '',
            subSteps: ['Додати страви', 'Перевірити всі страви'],
            isNewOrderError: false,
            isConnectionError: false,
        }
    },

    computed: {
        newOrderError() {
            if(this.isNewOrderError) {
                return 'Виникла помилка. Спробуйте ще раз';
            } 
        },

        newConnectionError() {
            if (this.isConnectionError) {
                return 'Введено неправильний код';
            }
        }
    },

    methods: {
        copy: function() {
            let el = document.createElement('textarea');
            el.value = this.step.data.url;
            document.body.appendChild(el);
            el.select();
            document.execCommand('copy');
            document.body.removeChild(el);
        },

        logOut: function() {
            this.$emit('logOut')
        },

        newOrder: function() {
            chrome.tabs.query({active: true, currentWindow: true}, (tabs) => {
                chrome.tabs.sendMessage(tabs[0].id, {type: 'restaurant'}, (response) => {
                    
                    sendCreateNewOrderRequest(response)
                    .then((resp) => {
                        // console.log(resp)
                        if(resp.status === 200) {
                            return resp.text();
                        } else {
                            throw new Error(resp)
                        }
                    }).then((resp) => {
                        this.step.data.url = response.url + '?code=' + resp;
                        this.step.data.restaurant = response.name;
                        this.step.data.code = resp;
                        
                        this.inputCode = '';
                        this.isNewOrderError =  false;
                        this.isConnectionError = false;

                        this.$emit('next', this.step); 
                    })
                    .catch((error)=> {
                        this.isNewOrderError = true;
                        console.log(error)
                    });
                });                
            });    
        },

        connectWithCode: function(code) {
            sendConnectWithCodeRequest(code)
            .then((resp) => {
                if(resp.status === 200) {
                    this.step.data.code = this.inputCode;
                    return resp.json();

                } else throw new Error(resp);
            })
            .then((resp) => {
                    this.step.data.restaurant = resp.restaurantName;
                    this.step.data.url = resp.restaurantUrl;

                    this.inputCode = '';
                    this.isNewOrderError =  false;
                    this.isConnectionError = false;

                    this.$emit('next', this.step);
            })
            .catch((error) => {
                this.isConnectionError = true;
                console.log(error)
            })
            // this.inputCode = null;
        }
    }
}
</script>

<style lang="scss" scoped>
@import '../styles/colors.scss';

.chooseRestaurant{
    display: flex;
    flex-direction: column;
}

.active-block {
    display: flex;
    flex-direction: column;
    min-height: 230px;
}

.list-item {
    font-size: 12px;
    padding-left: 3px;
    color: $color3;
}

.container {
    display: flex;
    flex-direction: column;
    align-items: center;
    flex: 1;
}

.delimeter {
    &::after,
    &::before {
        content: '';
        display: inline-block;
        border-top: 2px solid $color4;
        width: 100px;
        position: relative;
        top: -2px;
        transform: translate(0%, -50%);
    }

    &::after {
        transform: translateX(10px)
    }

    &::before {
        transform: translateX(-10px)
    }
}

.btn,
.input {
    width: 200px;
}

.btn {
    margin: 5px 0;
    min-height: 40px;;
}

.input {
    min-height: 35px;
}

.link {
    text-decoration: none;
    color: $color2;
    cursor: default;
}


.copy-img {
    margin-right: 5px;
}

.step {

    &-result-container {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
}

.conncetionContainer,
.newOrderContainer {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
}

.conncetionContainer {
    min-height: 55px;
    margin: 10px 0 5px 0;
}
.newOrderContainer {
    margin: 5px 0 5px 0;
    min-height:  65px;
}

.error {
    margin: 0;
}

</style>