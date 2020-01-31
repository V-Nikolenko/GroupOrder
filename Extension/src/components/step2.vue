<template>
    <div class="step2">
        <step-header
                v-bind:title="step.title"
                v-bind:isDone="step.isDone"
                v-bind:isActive="step.isActive"
        ></step-header>

        <div v-if="step.isActive" class="step2__active-block">

            <div class="step2__container">

                <input v-model="name" type="text" name="name" class="step2__input" placeholder="Ваше ім'я">
                <input v-model="email" type="text" name="email" class="step2__input" placeholder="Ваша пошта">

                <button class="step2__btn" v-on:click="sendOrder()">Доповнити групове замовлення</button>

            </div>

        </div>
        
        <div v-else-if="step.isDone" class="step doneStep doneStep2">
        
            <span>{{ name }}</span>
            <span>{{ fullPrice }}</span>
            <img src="/images/delete.png" alt="Видалити замовлення" title="Видалити замовлення" class="img img-reset">
        
        </div>

    </div>
</template>

<script>
import stepHeader from './stepHeader.vue';
import {sendMemberOrder} from './requests.js';
import {stepFactory} from './stepService.js';

export default {
    name: 'step2',
    props: ['step'],
    components: {
        stepHeader
    },

    data() {
        return {
            service: stepFactory.service,
            name: '',
            email: '',
            fullPrice: null
        }
    },

    computed: {

    },

    methods: {
        sendOrder: function() {

            chrome.tabs.query({active: true, currentWindow: true}, (tabs) => {
                chrome.tabs.sendMessage(tabs[0].id, {type: 'getOrders'}, (response) => {
                        // Object.assign(result.user, {'name': this.name, 'email': this.email})
                        // chrome.storage.sync.set({'user': result.user});

                    response.email = this.email;
                    response.name = this.name;

                    this.fullPrice = response.fullPrice

                    //change this.service.steps[0].data.code to getCode() 
                    sendMemberOrder(this.service.steps[0].data.code, response).then((resp)=> {
                        
                        if (resp.status === 200) {
                            this.step.data.email = this.email;
                            this.step.data.name = this.name;
                            this.$emit('next', this.step);
                        } else {
                            throw new Error();
                        }

                        //TODO: add validation, inspect why add orders request does not work
                    }).catch((error)=> {console.log(error)})
                })
            })
        }
    }
}
</script>

<style lang="scss" scoped>
$color1: silver;
$color2: #4b0082;
$color3: white;

.step2 {
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
        min-height: 250px;
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

.doneStep2 {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.img-reset {
    margin-right: 3px;
}

</style>