<template>
<div class="showDebt">
    <step-header
            v-bind:title="step.title"
            v-bind:isDone="step.isDone"
            v-bind:isActive="step.isActive">
    </step-header>

    <div v-if="step.isActive" class="active-block">
        <div class="container">
            <div class="title"><span>Борги</span>
            <img src="/images/export.png" alt="export" title="Експортувати" class="img" v-on:click="copy">
        </div>
        
            <bills-list v-on:bills="saveBills"></bills-list>
        
        </div>
    </div>
</div>
</template>

<script>
import stepHeader from "./stepHeader";
import { sendGetAllDishesRequest, sendGetSplitBillData } from './requests';
import { stepFactory } from "./stepService";
import billsList from './bills';

export default {
    name: 'step4',
    props: ['step'],
    components: {
        stepHeader,
        billsList
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
}
</script>

<style lang="scss" scoped>
@import '../styles/colors.scss';

.showDebt {
    display: flex;
    flex-direction: column;
}

.title{
    margin-bottom: 10px; 
    font-size: 16px;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.active-block {
    display: flex;
    flex-direction: column;
    min-height: 120px;
}
</style>