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
            <img src="/images/lock.png" class='img img-lock' alt="Заблокувати замовлення" title="Заблокувати замовлення">
        </div>
        
        <div class="btn-container"> 

            <button class="btn" v-on:click="formOrder()">Зібрати</button>
             
            <progress
                v-show="isSending" 
                v-bind:value="progressValue" 
                v-bind:max="progressMax"
                class="progress">
            </progress>

        </div>

    </div>


    <div v-else-if="step.isDone" class="step step-result-container">
        <span>Загальна сума: {{ step.data.fullPrice }} грн.</span>
        <button class="show-items-btn" v-on:click="showCheckBlock">{{ showCheckText }}</button>
    </div>
</div>
</template>

<script>
import stepHeader from "./stepHeader";
import { sendGetAllDishesRequest } from "./requests";
import { stepFactory } from './stepService.js';

export default {
    name: 'step3',
    props: ['step'],
    components: {
        stepHeader
    },

    data() {
        return {
            service: stepFactory.service,
            showCheck: true,
            isSending: false,
            progressMax: null,
            progressValue: 0
        }
    },

    computed: {
    },

    methods: {
        
        formOrder() {
            // replace to getCode()
            sendGetAllDishesRequest(this.service.steps[0].data.code)
            .then((resp)=> {
                
                if (resp.status === 200) {
                    return resp.json();
                } else throw new Error();

            })
            .then((resp)=> {
                console.log(this.step.data.progressValue)
                chrome.runtime.onMessage.addListener((request, sender, sendResponse) => {
                    if (request.type === 'quantity') {
                        this.progressMax = request.quantity;
                    }
                    
                    if (request.type === 'added') {
                        this.progressValue++;
                    }

                });
 
                
                chrome.tabs.query({active: true, currentWindow: true}, (tabs) => {
                    this.isSending = true;

                    chrome.tabs.sendMessage(tabs[0].id, {type: 'formOrder', resp}, (response) => {
                        
                        if (response) {
                            
                            chrome.tabs.query({active: true, currentWindow: true}, (tabs) => {
                                chrome.tabs.sendMessage(tabs[0].id, {type: 'reload'})

                                this.step.data.fullPrice = resp.fullPrice;
                                if (!this.showCheck) {
                                    this.showCheckBlock();
                                }
                                this.$emit('next', this.step);
                                this.isSending = false;
                                this.progressValue = 0;
                                this.progressMax = null;        
                            });
                        }
                    })
                })        
            })
            .catch((error)=> { console.log(error) });
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

    &:focus {
        outline: none;
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

.btn-container {
    display: flex;
    flex: 1;
    align-items: center;
    flex-direction: column;
}

.btn {
    margin: 20px;
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
</style>