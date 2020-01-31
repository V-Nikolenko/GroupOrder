<template>
    <ul class="list" v-if="!!data">
        
        <li v-for="(member, index) in data.items" v-bind:key="index" class="list-item">
            <span>{{ index + 1 +') ' + member.name}}</span>
            <span>{{member.price + 'грн.'}}</span>
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
            this.data = resp;
        })
        .catch((error) => { console.log(error) });
    }
    // }
    
}
</script>

<style lang="scss" scoped>

</style>