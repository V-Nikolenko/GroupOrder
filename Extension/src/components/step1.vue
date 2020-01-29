<template>
    <div class="step1">
        <step-header 
            v-bind:title="step.title"
            v-bind:isDone="step.isDone"
            v-bind:isActive="step.isActive"
        ></step-header>

        <section v-show="step.isActive" class="step1__active-block">
            
            <ol class="step1__list">
                <li v-for="(subStep, index) in subSteps" v-bind:key="index">{{ '1.' + (index + 1) + ". " + subStep }}</li>
            </ol>

            <div class="step1__container">
                <button class="step1__btn" v-on:click="newOrder()">Створити замовлення</button>

                <span class="step1__delimeter">або</span>

                <input v-model="code" type="text" name="code" class="step1__input" placeholder="Код замовлення">

                <button class="step1__btn" v-on:click="connectWithCode()">Приєднатися до існуючого замовлення</button>
            </div>

        </section>
        
        <section v-show="step.isDone" class="doneStep doneStep-step1">
            <a href="#">Restaurant</a>
            <!-- TODO: add restaurat -->
            <img src="/images/logout.png" alt="Вийти" title="Вийти" class="logout"> 
        </section>
        
    </div>
</template>

<script>
import {sendConnectWithCodeRequest, sendCreateNewOrderRequest} from './requests.js';
import stepHeader from './stepHeader.vue'

export default {
    name: 'step1',
    props: ['step'],
    components: {
        stepHeader
    },
    data() {
        return {
            code: null,
            subSteps: ['Додати страви', 'Перевірити всі страви']
        }
    },
    computed: {

    },
    methods: {
        newOrder: function() {
            // chrome.storage.sync.set({'sss': 884})
            // chrome.storage.sync.get('sss', function(result) {
                // console.log(result.sss)
            // })
            sendCreateNewOrderRequest()
            .then((resp) => {
                console.log(resp)
                if(resp.status === 200) {
                    return resp.json()
                } else {
                    throw new Error(resp.text)
                }
            }).then((resp) => {
                chrome.storage.sync.set({'code': resp.code})
                this.$emit('next');
            })
            .catch((error)=> {
                //TODO: try again
                console.log(error)
            })
        },

        connectWithCode: function() {
            sendConnectWithCodeRequest(this.code)
            .then((resp) => {
                if(resp.status === 200) {
                    //TODO: call next step function
                    this.$emit('next');
                } else {
                    //TODO: make borders red
                }
            })

            this.code = '';
        }
    }
}
</script>

<style lang="scss" scoped>
$color1: silver;
$color2: gray;
$color3: #4b0082;
$color4: white;

.step1 {
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
        border: 1px solid $color2;
        border-top: none;
        padding: 3px;
        min-height: 250px;
    }

    &__delimeter {

        &::after,
        &::before {
            content: '';
            display: inline-block;
            border-top: 2px solid $color2;
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
        color: $color3;
        border: 1px solid $color3;
        cursor: pointer;
        border-radius: 10px;
        background-color: $color4; 
        transition-duration: 0.1s;

        &:hover {
            color: $color4;
            background-color: $color3;
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
        border: 1px solid $color2;
        
        &:focus {
            outline: none;
            box-shadow: 0 0 1px 2px $color2;
        }
    }

}

.logout {
    width: 25px;
    height: 25px;
}

.doneStep-step1 {
    display: flex;
    justify-content: space-between;
    align-items: center;
}
</style>