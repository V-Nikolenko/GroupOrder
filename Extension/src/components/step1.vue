<template>
<div class="chooseRestaurant">

    <step-header 
        v-bind:title="step.title"
        v-bind:isDone="step.isDone"
        v-bind:isActive="step.isActive">
    </step-header>


    <section v-if="step.isActive" class="active-block">
        
        <ol class="list">
            <li v-for="(subStep, index) in subSteps" class="list-item" v-bind:key="index">{{ '1.' + (index + 1) + ". " + subStep }}</li>
        </ol>


        <div class="container">

            <div class="newOrderContainer">
                <button class="btn" v-on:click="newOrder()" v-bind:disabled="isDisabled">Створити замовлення</button>
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
                    
                <p  class="error">{{ newConnectionError }}</p>
            </div>

            <button class="btn" v-on:click="connectWithCode(inputCode)" v-bind:disabled="isDisabled">
                Приєднатися до існуючого замовлення
            </button>
        
        </div>

    </section>

    
    <section v-else-if="step.isDone" class="step step-result-container">

        <p class="restaurant">{{ step.data.restaurant }}({{ service.getCode()}}) </p>
        
        <div>
            <font-awesome-icon icon="copy" alt="Копіювати"  title="Копіювати" v-on:click="copy" class=" img mg_right_5"/>
            <font-awesome-icon icon="sign-out-alt" alt="Вийти" title="Вийти" v-on:click="logOut" class="img"/>
        </div>

    </section>
    
</div>
</template>

<script>
import {sendConnectWithCodeRequest, sendCreateNewOrderRequest} from './requests.js';
import stepHeader from './stepHeader.vue';
import { stepFactory } from './stepService.js';
import { copyToClipboard } from './copy.js'

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
            isDisabled: false
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
            copyToClipboard(this.step.data.url);
        },

        logOut: function() {
            this.$emit('logOut')
        },

        newOrder: function() {
            this.isDisabled = true;
            chrome.tabs.query({active: true, currentWindow: true}, (tabs) => {
                chrome.tabs.sendMessage(tabs[0].id, {type: 'restaurant'}, (response) => {
                    // console.log(response)
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
                        
                        this.$emit('next', this.step); 
                        this.isDisabled = false;
                    })
                    .catch((error)=> {
                        this.isDisabled = false;

                        this.isNewOrderError = true;
                        console.log(error)
                    });
                });                
            });    
        },

        connectWithCode: function(code) {
            this.isDisabled = true;

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

                    this.$emit('next', this.step);
                    this.isDisabled = false;
            })
            .catch((error) => {
                this.isDisabled = false;
                this.isConnectionError = true;
                console.log(error)
            })
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
    min-height: 40px;
}

.input {
    min-height: 35px;
}

.restaurant {
    font-size: 14px; 
    text-decoration: none;
    color: $color2;
    cursor: default;
    margin: 0;
}


.mg_right_5 {
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