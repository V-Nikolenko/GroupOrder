<template>
    <div class="step3">
        <step-header
                v-bind:title="step.title"
                v-bind:isDone="step.isDone"
                v-bind:isActive="step.isActive"
        ></step-header>

        <div v-if="step.isActive" class="step3__active-block">

            <div class="items-container">
                <button class="btn" v-on:click="showCheckBlock">{{ showCheckText }}</button>
                <!-- <img src="/images/lock.png" class='img img-lock' alt="Заблокувати замовлення" title="Заблокувати замовлення"> -->
            </div>
            
            <div class="btn-container"> 

                <button class="step3__btn" v-on:click="formOrder()">Зібрати</button>

            </div>

        </div>

        <div v-else-if="step.isDone" class="step doneStep">
            Сума: {{ step.data.fullPrice }} грн.
        </div>
    </div>
</template>

<script>
import stepHeader from "./stepHeader";
import { sendGetAllDishesRequest } from "./requests";
import { stepFactory } from './stepService.js'

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
        }
    },

    computed: {
        showCheckText: function () {
            return this.showCheck ? 'Показати' : 'Сховати';
        }
    },

    methods: {
        showCheckBlock() {
            this.showCheck = !this.showCheck;
            this.$emit('display')
        },
        formOrder: function () {
            // replace to getCode()
            sendGetAllDishesRequest(this.service.steps[0].data.code)
            .then((resp)=> {
                
                if (resp.status === 200) {
                    return resp.json();
                } else throw new Error();

            })
            .then((resp)=> {

                chrome.tabs.query({active: true, currentWindow: true}, (tabs) => {
                    chrome.tabs.sendMessage(tabs[0].id, {type: 'formOrder', resp}, (response) => {
                    
                        if (response) {
                            chrome.tabs.query({active: true, currentWindow: true}, (tabs) => {
                                chrome.tabs.sendMessage(tabs[0].id, {type: 'reload'})

                                this.step.data.fullPrice = resp.fullPrice;
                                if (!this.showCheck) {
                                    this.showCheckBlock();
                                }
                                this.$emit('next', this.step);
                            })
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
    $color1: #f5f5f5;
    $color2: #6658f3;
    $color3: white;

    .step3 {
        display: flex;
        flex-direction: column;

        &__list {
            padding: 0px;
            margin: 0;
            list-style-type: none;
        }

        &__container {
            display: flex;
            flex-direction: column;
            align-items: center;
            flex: 1;
            justify-content: space-around
        }

        &__active-block {
            display: flex;
            flex-direction: column;
            border: 1px solid silver;
            border-top: none;
            padding: 3px;
            min-height: 200px;
        }

        &__delimeter {

            &::after,
            &::before {
                content: '';
                display: inline-block;
                border-top: 2px solid silver;
                width: 120px;
                position: relative;
                top: -2px;
                transform: translate(0%, -50%);
            }

            &::after {
                transform: translateX(15px)
            }

            &::before {
                transform: translateX(-15px)
            }
        }

        &__btn {
            height: 50px;
            color: $color2;
            border: 1px solid $color2;
            cursor: pointer;
            border-radius: 10px;
            background-color: $color3;
            transition-duration: 0.1s;
            margin: auto auto;

            &:hover {
                color: $color3;
                background-color: $color2;
            }
        }
        &__btn,
        &__input {
            box-sizing: border-box;
            width: 200px;
            padding: 5px;

        }

        &__input {
            min-height: 35px;
            border-radius: 5px;
            border: 1px solid $color1;

            &:focus {
                outline: none;
                box-shadow: 0 0 1px 2px $color1;
            }
        }

    }

.items-container {
    padding: 0 0 0 8px; 
    margin: 10px 0;
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
}

.btn-container {
    display: flex;
    flex: 1;
    justify-content: center;
    align-items: center;
}

.img-lock {
    margin-right: 3px;
}

.check {
    position: absolute;
    z-index: 0;
}

.step {
    display: flex;
    align-items: center;
}
.btn {
    color: $color2;
    border: none;
    background: none;
    cursor: pointer;
    padding: 0px;

    &:focus {
        outline: none;
    }
}
</style>