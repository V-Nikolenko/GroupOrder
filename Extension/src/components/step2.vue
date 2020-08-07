<template>
    <div class="addOrder">
        <step-header
                v-bind:title="step.title"
                v-bind:isDone="step.isDone"
                v-bind:isActive="step.isActive"
        ></step-header>

        <div v-if="step.isActive" class="active-block">

            <div class="nameContainer">
                <input v-model="name" 
                    maxlength="30"
                    type="text" 
                    name="name" 
                    v-bind:class="[{'input_error': isNameError}, 'input']" 
                    placeholder="Ваше ім'я"
                    v-on:input="nameError">
                <p v-show="isNameError" class="error">
                    Ім'я має бути більше 3  та менше 30 символів!
                </p>
            </div>

            <div class="emailContainer">
                <input v-model="email" 
                    type="text" 
                    name="email" v-bind:class="[{'input_error': isEmailError}, 'input']" 
                    placeholder="Ваш email"
                    v-on:input="emailError">
                <p v-show="isEmailError" class="error">
                    Довжина email повинна бути не меншою ніж 6 символів, 
                    а також містити символ '@'!;
                </p>
            </div>

            <div class="btnContainer">
                <button 
                    v-bind:class="[btn_disabled, 'btn']" 
                    v-on:click="sendOrder()"
                    v-bind:disabled="(isEmailError || isNameError || !email || !name || isDisabled)">
                    Доповнити групове замовлення
                </button>

                <p v-show="isSendingError" class="error">{{ sendingError }}</p>
            </div>

        </div>

        
        <div v-else-if="step.isDone" class="step step-result-container step-result">
        
            <span class="done-name">{{ service.steps[1].data.name }}</span>
            <span>{{ service.steps[1].data.userFullPrice}} грн.</span>
            <font-awesome-icon icon="trash-alt" alt="Видалити замовлення" title="Вийти" class="img img-trash"  v-on:click="removeMember()"/>

        </div>

    </div>
</template>

<script>
import stepHeader from './stepHeader.vue';
import {sendMemberOrder, sendRemoveMemberFromOrder} from './requests.js';
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
            fullPrice: null,
            isNameError: false,
            isEmailError: false,
            isSendingError: false,
            sendingError: null,
            isDisabled: false
        }
    },

    computed: {
        btn_disabled() {
            return {
                btn_disabled: (this.isEmailError || this.isNameError || !this.email || !this.name)
            }
        }
    },

    methods: {
        nameError() {
            if ( !(3 < this.name.length && this.name.length < 30) ) {
                this.isNameError = true;
            } else this.isNameError = false;
        },

        emailError() {
            function isEmailValid(email) {
                const re = /^(([^<>()\[\]\.,;:\s@\"]+(\.[^<>()\[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
                return re.test(email)
            } 
            if (!isEmailValid(this.email)) {
                this.isEmailError = true;
            } else this.isEmailError = false;
        },


        sendOrder: function() {
            this.isDisabled = true;
            chrome.tabs.query({active: true, currentWindow: true}, (tabs) => {
                chrome.tabs.sendMessage(tabs[0].id, {type: 'getOrders'}, (response) => {
                    
                    if (!response.items.length) {
                        this.isDisabled = false;
                        this.isSendingError = true;
                        this.sendingError = 'Замовлення не повинно бути пусте!';
                        return;
                    }

                    response.email = this.email;
                    response.name = this.name;
                    if (!!response.optionValue) {
                        response.optionValue = 'NEMAYE';
                    }
                    
                    sendMemberOrder(this.service.getCode(), response)
                    .then((resp)=> {
                        
                        if (resp.status === 200) {
                            this.step.data.userFullPrice = response.fullPrice;
                            this.step.data.email = this.email;
                            this.step.data.name = this.name;
                            
                            this.isDisabled = false;

                            return resp.text() // make it json

                        } else {
                            this.isSendingError = true; 
                            
                            if (resp.status === 423){
                                this.sendingError = 'Замовлення заблоковано';    
                            }
                        }

                    })
                    .then((resp) => {
                    
                    console.log(resp);
                        // console.log(this.step.data.id);
                        this.step.data.id = resp// id
                    
                        // console.log(this.step.data.id);

                        this.$emit('next', this.step);
                    }).catch((error)=> {
                        console.log(error);
                        this.isDisabled = false;
                        this.isSendingError = true;
                        this.sendingError = 'Спробуйте ще раз';
                    })
                })
            })
        },
        
        removeMember: function() {
            sendRemoveMemberFromOrder(this.service.getCode(), this.service.getId())
            .then((resp) => {
                if (resp.status === 200) {
                    this.$emit('remove');
                }
                // add smth to show result if error
            })
        }
    } 

}
</script>

<style lang="scss" scoped>
@import '../styles/colors.scss';

.addOrder {
    display: flex;
    flex-direction: column;
}

.active-block {
    display: flex;
    justify-content: space-around;
    align-items: center;
    flex-direction: column;
    min-height: 180px;
}

.btn,
.input {
    width: 200px;
}

.btn {
    min-height: 40px;
    margin: 5px 0;
}

.input {
    min-height: 35px;
    margin: 5px 0;
}

.step {

    &-result-container {
        display: flex;
        justify-content: space-between;
        color: $color3;
        font-size: 14px;
    }
}

.nameContainer,
.emailContainer,
.btnContainer{
    display: flex;
    align-items: center;
    flex-direction: column;
}

.nameContainer {
    min-height: 60px;
}
 
.emailContainer {
    min-height: 75px;
}

.btnContainer {
    min-height: 70px;
}

.img {
    &-trash {
        color: $main-font-color;
    }
}

.error {
    margin: 0;
}

.done-name {
    max-width: 50%;
    word-break: break-all;
}

</style>