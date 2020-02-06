<template>
<div class="receipt">
    
    <h1 class="receipt-heading">Чек № {{code}} <span class="exit" v-on:click="$emit('showAllOrders')">&#10140;</span> </h1>
    
    <ul class="list scroll">
    
        <li v-for="(member, id) in members" class="list-item"  v-bind:key='id'>

            <div class="head" v-on:click="showBody(id)">
                <div class="user-info">
                    <img v-bind:src="'https://www.gravatar.com/avatar/' + getHash(member.email) + '?s=20&d=mp'" 
                    v-bind:alt="member.name"
                    class="user-img">
                    <span>{{member.name}}</span> 
                </div>

                <span class="head-price">{{member.fullPrice}} грн.</span>
                <span v-bind:class="[ {'anime': !selected.includes(id)},'item-ico']"></span>
            </div>

            <table v-bind:class="[{body_none: !selected.includes(id)},'body']">

                <thead>
                    <th 
                        v-for="(header, index) in tableHeaders" 
                        v-bind:key="index">
                        {{ header }}
                    </th>
                </thead>


                <tbody>
                    <tr v-for="(product, prodId) in member.products" v-bind:key="prodId">
                        <td >{{prodId + 1 + '.'}}</td>
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
import { MD5 } from './md5.js';
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
        },
        getHash: function(str) {
            return MD5(str);
        } 
    }
}
</script>

<style lang="scss" scoped>
@import '../styles/colors.scss';

.receipt {
    padding: 10px;
}
.head {
    font-weight: 600;
    cursor: pointer;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 5px;
    background-color: $color1;
    border: 1px solid $color4;
    border-top: none;
    position: relative;

    &-price {
        position: absolute;
        right: 15%;

        text-align: center;
    }
}


.list {
    height: 360px;
    overflow: auto;
}

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
    border-bottom: 1px solid $color4;
}

.exit {
    position: absolute;
    right: 8px;
    cursor: pointer;
    transition: .1s;

    &:hover {
        transform: scale(1.1);
    }

    &:active {
        transform: translateX(5px)
    }
}

.user {
    width: 40%;

    &-info {
        display: flex;
        align-items: center;
    }

    &-img {
        border-radius: 50%;
        margin-right: 10px; 
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
    text-align: center;
}

thead, tbody {
    border-right: 1px solid $color4;
    border-left: 1px solid $color4; 
}

thead {
    border-bottom: 1px solid $color4;
}



thead th {
    height: 30px;
    color: $color3
}

tr {
    height: 40px;
    border-bottom: 1px solid $color4;
}

td:nth-child(1) {
    width: 10%;
}

td:nth-child(2) {
    // text-align: left;
    width: 44%;
}

td:nth-child(3) {
  width: 15%;
}

td:nth-child(4) {
  width: 20%;
}

td:nth-child(5) {
    width: 11%;
}


</style>