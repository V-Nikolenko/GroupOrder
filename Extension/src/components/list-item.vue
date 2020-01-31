<template>
    <li class="list-item">

        <div class="head" v-on:click="isBody = !isBody">
            <span>{{member.name}}</span> 
            <span>{{member.fullPrice}}</span>
        </div>

        <table v-show="isBody" class="body">
            <thead>
                <th height='25'>№</th>
                <th height='25'>Назва</th>
                <th height='25'>Кількість</th>
                <th height='25'>Ціна</th>
            </thead>

            <tbody>
                <tr v-for="(product, prodId) in member.products" v-bind:key="prodId">
                    <td class="text-center">{{prodId}}</td>
                    <td>{{product.name}}</td>
                    <td class="text-center">{{product.count}}</td>
                    <td class="text-center">{{product.price}}</td>
                </tr>
            </tbody>
        </table>
    </li>


</template>

<script>
export default {
    name: 'list-item',
    props: ['member', 'id'],
    data() {
        return {
            isBody: false
        }
    }
}


</script>

<style lang="scss" scoped>
$color1: silver;
$color2: gray;

.head {
    cursor: pointer;
}
.body {
    width: 100%;
}

table {
  border-collapse: collapse;
}

thead {
  border-bottom: 1px solid $color2;
}

thead th {
  min-height: 30px;
}

tr {
  height: 40px;
  border-bottom: 1px solid $color2;
}

tr:nth-child(1) {
  width: 10%;
}

tr:nth-child(2) {
  width: 50%;
}

tr:nth-child(3) {
  width: 20%;
}
tr:nth-child(4) {
  width: 20%;
}


</style>