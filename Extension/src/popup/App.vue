<template>
<div class="container">

  <div v-if="isLoaded">
    
    <div v-bind:class="[isDisplay ? 'receipt' : 'receipt_none']">
      
      <h2 class="receipt-heading">{{steps[0].data.code}}</h2>

      <ul class="list">

        <list-item v-for="(member,id) in members" 
          v-bind:key='id' 
          v-bind:member="member"
          v-bind:id="id">
        </list-item>

        <!-- <li v-for="(member,id) in members" v-bind:key='id' class="list-item">
          
          <div class="head" v-on:click="isBody">
            <span>{{member.name}}</span> 
            <span>{{member.fullPrice}}</span>
          </div>

          <table class="body">
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

        </li> -->


      </ul>
    </div>
  
  </div>
  
  <div class='extension'>

    <header>
      <h1 class="extension__title">Group Order</h1>
    </header>

    <div v-if="isLoaded">
      
      <step1 v-bind:step=steps[0] v-on:next='nextStep'></step1>    
      
      <step2 v-bind:step=steps[1] v-on:next='nextStep'></step2> 

      <step3 v-bind:step=steps[2] v-on:next='nextStep' v-on:display="isDisplay = !isDisplay"></step3>

      <step4 v-bind:step=steps[3] v-on:next='nextStep'></step4>
      
    </div>

  </div>

</div>
</template>

<script>
import step1 from '../components/step1';
import step2 from '../components/step2';
import step3 from '../components/step3';
import step4 from '../components/step4';
import listItem from '../components/list-item'
import {stepFactory} from '../components/stepService';
import {sendGetOrdersListRequest} from '../components/requests'

const STEPS = [
        {
          isActive: true,
          isDone: false,
          title: '1. Обрати заклад',
          data: {
            code: null
          }
        },
        
        {
          isActive: false,
          isDone: false,
          title: '2. Доповнити замовлення',
          data: {
            name: null,
            email: null
          }
        },
        
        {
          isActive: false,
          isDone: false,
          title: '3. Зібрати замовлення',
          data: {
            fullPrice: null
          }
        },

        {
          isActive: false,
          isDone: false,
          title: '4. Показати борги',
          data: {
          
          }
        }

      ];

export default {
  name: "app",
  components: {
    step1,
    step2, 
    step3, 
    step4,
    listItem
  },

  data () {
    return {
      isDisplay: false,
      stepService: null,
      members: null
    }
  },

  computed: {
    isLoaded() {
      return !!this.stepService
    },

    steps() {
      if (this.isLoaded) {
        return this.stepService.steps;
      } else return [];  
    }
    
  },

  watch: {
    isDisplay: function(){

      sendGetOrdersListRequest(this.stepService.steps[0].data.code)
      .then((resp) => {
        return resp.json();

        // this.members = resp.members;
        
      }).then((resp)=> {
        this.members = Object.values(resp.members)
      })
    }
  },

  methods: {
    nextStep: function(step) {
      this.stepService.nextStep(step);
    }
  },

  created() {
    chrome.storage.sync.get('steps', (result) => {
      this.stepService = result.steps !== undefined
                        ? stepFactory.create(result.steps) 
                        : stepFactory.create(STEPS);
      
      // stepService.subscribe( function(steps) {
      //   chrome.storage.sync.set({steps});
      // });
    });
  }

}
</script>

<style lang="scss">
$color1: silver;
$color2: gray;

body {
  margin: 0;
  padding: 0;
  font-family: 'Roboto', sans-serif;
}

.extension {
  display: flex;
  flex-direction: column;
  width: 400px;
  float: right;

  &__title {
    margin: 0;
    width: 100%;
    display: flex;
    align-items: center;
    border: 1px solid $color2;
    box-sizing: border-box;
    min-height: 40px;
    font-size: 22px;
  }
}

.container {
  display: flex;
  justify-content: space-between;
}

.doneStep {
  font-size: 16px;
  border: 1px solid $color2;
  border-top: none;
}

.step {
  padding: 3px;
  min-height: 35px;
  width: 100%;
  box-sizing: border-box;
}

.img {
  width: 25px;
  height: 25px;
  cursor: pointer;
}

.receipt_none {
  width: 0px;
  overflow: hidden;
  transition: 2s ease-in-out;
} 
.receipt {
  width: 300px;
  transition: 2s ease-in-out;
}

.receipt-heading {
  min-height: 40px;
  margin: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  border: 1px solid $color2;
  padding: 3px;
  box-sizing: border-box;
}

.head {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 5px;
  background-color: $color1;
  border: 1px solid $color2;
}


.list {
  list-style-type: none;
  padding: 0;
  margin: 0;

  &-item {

  }
}

.text-center {
  text-align: center;
}


</style>

