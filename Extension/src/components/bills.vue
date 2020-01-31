<template>
    <ul class="list" v-if="!!data">
        
        <li v-for="(member, index) in data.items" v-bind:key="index" class="list-item">
            <span style="font-size: 13px">{{ index + 1 +') ' + member.name}}</span>
            <span style="padding-right: 4px; font-size: 13px;">{{member.price + 'грн.'}}</span>
        </li>

    </ul>
</template>

<script>
import { sendGetSplitBillData } from "./requests";
import { stepFactory } from "./stepService";

export default{
    name: 'list-bills',
    data() {
        return {
            service: stepFactory.service,
            data: null
        }
    },
    created() {
        sendGetSplitBillData(this.service.steps[0].data.code)
        .then((resp) => {
            if (resp.status === 200) {
                return resp.json();
            } else throw new Error();
        })
        .then((resp)=> {
            this.$emit('bills', resp)
            this.data = resp;
        })
        .catch((error) => { console.log(error) });
    }
    // }
    
}
</script>

<style lang="scss" scoped>
.list-item {
    padding: 0 0 0 8px;
    display: flex;
    justify-content: space-between;
}
</style>