<template>
<div class="formOrder">
    
    <step-header
            v-bind:title="step.title"
            v-bind:isDone="step.isDone"
            v-bind:isActive="step.isActive">
    </step-header>

    
    <div v-if="step.isActive" class="active-block">

        <div class="container">
        
            <button class="show-items-btn" v-on:click="$emit('showAllOrders')">Показати</button>
            <span v-bind:class="[{'unlocked': !step.data.isLocked}, 'lock']" v-on:click="changeLockState(null)"></span>
        
        </div>
        
        <div class="btn-container"> 

            <button v-on:click="formOrder()" v-bind:class="[btn_disabled, 'btn']" v-bind:disabled="isDisabled">Зібрати</button>
             
            
            <loading size="0.3" v-show="(isSending && !isCleared)"></loading>

            <progress
                v-show="(isSending && isCleared)" 
                v-bind:value="progressValue"
                v-bind:max="progressMax"
                class="progress">
            </progress>

        </div>

    </div>


    <div v-else-if="step.isDone" class="step step-result-container">
        <span>Загальна сума: {{ step.data.fullPrice }} грн.</span>
        <button class="show-items-btn" v-on:click="$emit('showAllOrders')">Показати</button>
    </div>

</div>
</template>

<script>
import stepHeader from "./stepHeader.vue";
import { sendGetAllDishesRequest, sendLockOrderRequest, sendOrderStateRequest } from "./requests.js";
import { stepFactory } from './stepService.js';
import loading from './loading.vue'

export default {
    name: 'step3',
    props: ['step'],
    components: {
        stepHeader, 
        loading
    },

    data() {
        return {
            service: stepFactory.service,
            showCheck: true,
            isCleared: false,
            isSending: false,
            progressMax: null,
            progressValue: 0,
            isDisabled: false
        }
    },

    computed: {
        btn_disabled() {
            return {
                btn_disabled: this.isDisabled
            }
        },
    
    },

    methods: {
        changeLockState(method) {
            console.log(method);
            return sendOrderStateRequest(this.service.getCode(), this.step.data.isLocked, method)
            .then((resp) => {
                if (resp.status === 200) {
                    this.step.data.isLocked = !this.step.data.isLocked;
                    this.service.saveSteps();
                } else {
                    throw new Error('cant lock/unclock');
                }
                // show result to user;
            }).catch((error) => {
                console.log(error)
            }) 
        },
 
        formOrder() {

            this.isDisabled = true;

            this.changeLockState("PUT")
            .then((resp) => {
                this.step.data.isLocked = true;
            });


            sendGetAllDishesRequest(this.service.getCode())
            .then((resp)=> {
                
                if (resp.status === 200) {
                    return resp.json();
                } else throw new Error();

            })
            .then((resp)=> {
      
                chrome.runtime.onMessage.addListener((request, sender, sendResponse) => {
                    if (request.type === 'quantity') {
                        this.progressMax = request.quantity;
                    }
                    
                    if (request.type === 'added') {
                        this.progressValue++;
                    }
                    
                    if (request.type === 'cleared') {
                        this.isCleared = true;
                    }
                });
 
                
                chrome.tabs.query({active: true, currentWindow: true}, (tabs) => {
                    this.isSending = true;

                    chrome.tabs.sendMessage(tabs[0].id, {type: 'formOrder', resp}, (response) => {
                        
                        if (response) {
                            
                            chrome.tabs.query({active: true, currentWindow: true}, (tabs) => {
                                chrome.tabs.sendMessage(tabs[0].id, {type: 'reload'});

                                this.step.data.fullPrice = resp.fullPrice;
                                
                                if (!this.showCheck) {
                                    this.showCheckBlock();
                                }

                                this.$emit('next', this.step);
                            
                                this.isDisabled = false;
                            });
                        }
                    })
                })        
            })
            .catch((error)=> {
                console.log(error);
                this.isDisabled = false;
            });
        }
    }
}
</script>

<style lang="scss" scoped>
@import '../styles/colors.scss';

.formOrder {
    display: flex;
    flex-direction: column;
}

.active-block {
    height: 140px;
    display: flex;
    flex-direction: column;
}

.container {
    display: flex;  
    justify-content: space-between;
    align-items: center;
    height: 40px;
}

.img-reset {
    margin-right: 5px;
}

.btn {
    min-height: 40px;
    min-width: 150px;
}

.show-items-btn {
    border: none;
    background: none;
    color: $color2;
    cursor: pointer;
    font-weight: 600;
    padding: 0;
    position: relative;

    &:focus {
        outline: none;
    }

    &:before {
        content: "";
        position: absolute;
        width: 100%;
        height: 1px;
        bottom: 0;
        left: 0;
        background-color: $color2;
        visibility: hidden;
        transform: scaleX(0);
        transition: 0.2s ease-in-out;
        }

        &:hover:before {
        visibility: visible;
        transform: scaleX(1);
    }
}

.step {
    display: flex;
    justify-content: space-between;
    
    &-result-container {
        font-size: 14px;
        color: $color3;
    }
}

.btn {
    margin: 10px;

    &-container {
        display: flex;
        flex: 1;
        align-items: center;
        flex-direction: column;
    }
}

.progress {
    margin: 10px;
    width: 150px;
    -webkit-appearance: none;
    appearance: none;
    
    &::-webkit-progress-bar {
        background-color: #eee;
        border-radius: 3px;
        box-shadow: 0 2px 5px rgba(0, 0, 0, 0.25) inset;
    }
    &::-webkit-progress-value {
    background-image:
	   -webkit-linear-gradient(-45deg, 
	                           transparent 33%, rgba(0, 0, 0, .1) 33%, 
	                           rgba(0,0, 0, .1) 66%, transparent 66%),
	   -webkit-linear-gradient(top, 
	                           rgba(255, 255, 255, .25), 
	                           rgba(0, 0, 0, .25)),
	   -webkit-linear-gradient(left, $color3, $color2);

    border-radius: 2px; 
    background-size: 35px 20px, 100% 100%, 100% 100%;
    }
}

.lock {
    width: 15px;
    height: 12px;
    border: 3px solid $main-font-color;
    border-radius: 5px;
    position: relative;
    cursor: pointer;
    -webkit-transition: all 0.1s ease-in-out;
    transition: all 0.1s ease-in-out;
    
    &:after {
        content: "";
        display: block;
        background: $main-font-color;
        width: 3px;
        height: 7px;
        position: absolute;
        top: 50%;
        left: 50%;
        margin: -3.5px 0 0 -2px;
        -webkit-transition: all 0.1s ease-in-out;
        transition: all 0.1s ease-in-out;
    }

    &:before {
        content: "";
        display: block;
        width: 7px;
        height: 9px;
        bottom: 100%;
        position: absolute;
        left: 60%;
        margin-left: -8px;
        border: 3px solid $main-font-color;
        border-top-right-radius: 50%;
        border-top-left-radius: 50%;
        border-bottom: 0;
        -webkit-transition: all 0.1s ease-in-out;
        transition: all 0.1s ease-in-out;
    }

    &:hover:before {
        height: 10px;
    }
}


.unlocked {
    &:before {
        bottom: 120%;
        left: 45%;
        margin-left: -11.5px;
        transform: rotate(-40deg);
    }
    
    &:before {
        border-color: $main-font-color;
    }

    &:after {
        background: $main-font-color;
    }

    &:hover:before {
        height: 9px;
        left: 52%;
        bottom: 120%;
        transform: rotate(-30deg);
    }
}

</style>