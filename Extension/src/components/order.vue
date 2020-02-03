<template>
<div>
    
    <h1 class="receipt-heading">Чек № {{code}} <span class="exit" v-on:click="$emit('showAllOrders')">&#10006;</span> </h1>

    <ul class="list">
    
        <li v-for="(member, id) in members" class="list-item"  v-bind:key='id'>

            <div class="head" v-on:click="showBody(id)">
                <span class="head-title">{{member.name}}</span> 
                <span class="head-price">{{member.fullPrice}} грн.</span>
                <span v-bind:class="[ {'anime': !selected.includes(id)},'item-ico']"></span>
            </div>

            <table v-bind:class="[{body_none: !selected.includes(id)},'body']">

                <thead>
                    <th 
                        v-for="(header, index) in tableHeaders" 
                        v-bind:key="index" 
                        style="height: 25px"
                        >
                        
                        {{ header }}
                    </th>
                </thead>


                <tbody>
                    <tr v-for="(product, prodId) in member.products" v-bind:key="prodId">
                        <td >{{prodId}}</td>
                        <td>{{product.name}}</td>
                        <td >{{product.count}}</td>
                        <td >{{product.price}}</td>
                        <td></td>
                    </tr>
                </tbody>

            </table>

        </li>

    </ul>

</div>
</template>

<script>

export default {
    name: 'order',
    props: ['members', 'code'],
    data() {
        return {
            tableHeaders: ['№', 'Назва', 'Кількість', 'Ціна', ''],
            selected: []
        }
    },
    methods: {
        showBody: function(el) {
            if (!this.selected.includes(el)) {
                this.selected.push(el);
            } else this.selected.splice(this.selected.indexOf(el), 1)

            // el.currentTarget.parentNode.getElementsByClassName('body')[0].classList.toggle('none')
            // el.currentTarget.getElementsByClassName('item-ico')[0].classList.toggle('anime');
        }
    }
}
</script>

<style lang="scss" scoped>
@import '../styles/colors.scss';

.receipt-heading {
    overflow-x: hidden;
    min-height: 40px;
    margin: 0;
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 5px;
    box-sizing: border-box;
    position: relative;
}

.exit {
    position: absolute;
    right: 8px;
    cursor: pointer;
    transition: .1s;

    &:hover {
        transform: scale(1.1);

    }
}

.head {
    cursor: pointer;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 5px;
    background-color: $color1;
    border: 1px solid $color4;
    border-right: none;
    position: relative;

    &-title {
        width: 30%;
    }
    &-price {
        position: absolute;
        right: 15%;

        text-align: center;
    }
}

.item-ico {
    cursor: pointer;
    width: 20px;
    height: 20px;
    background-image: url('/images/listItem.png');
    background-repeat: no-repeat;
    background-position: center;
    background-size: 18px;
    transition: 0.3s;
    transform: rotate(0deg);
}

.anime {
    transform: rotate(-90deg);
}

.body {
    display: block;
    width: 100%;
    // max-height: 500px;
    // transition: 0.5s ease-in-out;

    &_none {
        display: none;
        // transition: 1s ease-in-out;
        // overflow: hidden;
        // max-height: 0px;
    }
}

.none {
    display: none;
}

table {
    border-collapse: collapse;
}

thead {
    border-bottom: 1px solid $color4;
}

thead th {
    min-height: 30px;
}

th, tr {
    text-align: center;
}

tr {
    height: 40px;
    border-bottom: 1px solid $color4;
}

td:nth-child(1) {
    width: 10%;
}

td:nth-child(2) {
    width: 40%;
}

td:nth-child(3) {
  width: 15%;
}

td:nth-child(4) {
  width: 20%;
}

td:nth-child(5) {
    width: 15%;
}

</style>