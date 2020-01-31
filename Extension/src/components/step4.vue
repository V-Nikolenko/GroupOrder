<template>
    <div class="step4">
        <step-header
                v-bind:title="step.title"
                v-bind:isDone="step.isDone"
                v-bind:isActive="step.isActive">
        </step-header>

        <div v-if="step.isActive" class="step4__active-block">
            <div class="step4__container">
                <div class="step4__title"><span>Борги</span>
                <img src="/images/export.png" alt="export" title="Експортувати" class="img" v-on:click="copy">
            </div>
                <list-bills v-on:bills="saveBills"></list-bills>
                
                <!-- <ul class="list">
                    <li v-for="(member, index) in data.items" v-bind:key="index" class="list-item">
                        <span>{{ index + 1 +') ' + member.name}}</span>
                        <span>{{member.price + 'грн.'}}</span>
                    </li>
                </ul> -->

            </div>
        </div>
    </div>
</template>

<script>
import stepHeader from "./stepHeader";
import { sendGetAllDishesRequest, sendGetSplitBillData } from './requests';
import { stepFactory } from "./stepService";
import listBills from './bills';

export default {
    name: 'step4',
    props: ['step'],
    components: {
        stepHeader,
        listBills
    },

    data() {
        return {
            service: stepFactory.service,
            data: null,
        }
    },
    computed: {
        
    },
    methods: {
        saveBills: function(data) {
            this.data = data;
        },

        copy: function() {
            let el = document.createElement('textarea');
            el.value = JSON.stringify(this.data);
            document.body.appendChild(el);
            el.select();
            document.execCommand('copy');
            document.body.removeChild(el);
        },
    },
    // watch: {
    //     isActive: function() {
    //         if(this.step.isActive) {
    //             sendGetSplitBillData(this.service.steps[0].data.code)
    //             .then((resp) => {
    //                 if (resp.status === 200) {
    //                     return resp.json();
    //                 } else throw new Error();
    //             })
    //             .then((resp)=> {
    //                 this.data = resp;
    //             })
    //             .catch((error) => { console.log(error) });
    //         }
    //     }
    // }
}
</script>

<style lang="scss" scoped>
$color1: #f5f5f5;
$color2: #6658f3;
$color3: white;

.step4 {
    display: flex;
    flex-direction: column;

    &__list {
        padding: 0px;
        margin: 0;
        list-style-type: none;
    }

    &__title{
        margin-bottom: 10px; 
        padding: 0 0 0 8px;
        font-size: 16px;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    &__container {
        padding: 3px;
        // margin: 0 auto;
    }

    &__active-block {
        display: flex;
        flex-direction: column;
        border: 1px solid silver;
        border-top: none;
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


.list {
    width: 100%;

    &-item {
        padding: 0 0 0 8px; 
        display: flex;
        justify-content: space-between;
        width: 100%;
    }
}



</style>