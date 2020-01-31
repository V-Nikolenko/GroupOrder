<template>
    <div class="step1">
        <div>{{service}}</div>
        <step-header 
            v-bind:title="step.title"
            v-bind:isDone="step.isDone"
            v-bind:isActive="step.isActive">
        </step-header>

        <section v-show="step.isActive" class="step1__active-block">
            
            <ol class="step1__list">
                <li v-for="(subStep, index) in subSteps" v-bind:key="index">{{ '1.' + (index + 1) + ". " + subStep }}</li>
            </ol>

            <div class="step1__container">
                <button class="step1__btn" v-on:click="newOrder()">Створити замовлення</button>

                <span class="step1__delimeter">або</span>

                <input v-model="inputCode" type="text" name="code" class="step1__input" placeholder="Код замовлення">

                <button class="step1__btn" v-on:click="connectWithCode()">Приєднатися до існуючого замовлення</button>
            </div>

        </section>
        
        <section v-show="step.isDone" class="doneStep doneStep-step1 step">
            <a href="#" >{{ step.data.code }}</a>
            <!-- TODO: add restaurat -->
            <img src="/images/copy.png" alt="Копіювати" title="Копіювати" class="img copy-img">
            <img src="/images/logout.png" alt="Вийти" title="Вийти" class="img logout-img"> 
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
            inputCode: null,
            subSteps: ['Додати страви', 'Перевірити всі страви']
        }
    },

    computed: {
        
    },

    methods: {
        newOrder: function() {
            sendCreateNewOrderRequest()
            .then((resp) => {
                console.log(resp)
                if(resp.status === 200) {
                    return resp.text()
                } else {
                    throw new Error(resp.text)
                }
            }).then((resp) => {
                this.step.data.code = resp;
                this.$emit('next', this.step); 
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
                    this.step.data.code = this.inputCode;
                    this.$emit('next', this.step);
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


.copy-im,
.logout-img {
    top: 50%;
    transform: translateY(-50%);
}

.copy-img {
    position: absolute;
    right: 45px;
}

.logout-img {
    position: absolute;
    right: 5px
}
.doneStep-step1 {
    position: relative;
    display: flex;
    justify-content: space-between;
    align-items: center;
}
</style>