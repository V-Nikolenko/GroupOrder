<template>

<div class="container">
  <div v-if="isLoaded">

    <div v-bind:class="[isDisplay ? 'receipt' : 'receipt_none']">
      
      <h2 class="receipt-heading">Чек № {{steps[0].data.code}}</h2>

      <ul class="list">

        <list-item v-for="(member,id) in members" 
          v-bind:key='id' 
          v-bind:member="member"
          v-bind:id="id">
        </list-item>

      </ul>
    </div>
  
  </div>
  
  <div class='extension'>
    <header>
      <h1 class="extension__title pd-left">Group Order</h1>
    </header>

    <div v-if="isLoaded">

      <step1 v-bind:step=steps[0] v-on:next='nextStep' v-on:logOut="logOut"></step1>    
      
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
      email: null,
      userFullPrice: null
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
      return !!this.stepService;
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
    },

    logOut: function() {
      let copy =  STEPS.map((elem) => Object.assign({}, elem));
      this.stepService.setData(copy);
      // window.location.reload();
    }
  },

  created() {
    chrome.storage.sync.get('steps', (result) => {
      let copy = STEPS.map((elem) => Object.assign({}, elem))
      this.stepService = result.steps !== undefined
                        ? stepFactory.create(result.steps) 
                        : stepFactory.create(copy);
      
      // stepService.subscribe( function(steps) {
      //   chrome.storage.sync.set({steps});
      // });
    });
  }

}
</script>

<style lang="scss">
$color1: #f5f5f5;
$color2: #8a8f93;

body {
  margin: 0;
  padding: 0;
  font-family: 'Roboto', sans-serif;
}

.extension {
  display: flex;
  flex-direction: column;
  min-width: 400px;

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
  height: 400px;
}

.doneStep {
  font-size: 16px;
  border: 1px solid $color2;
  border-top: none;
}

.step {
  padding: 0 3px 0 8px;
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
  transition: 1s ease-out;
  overflow-y: auto;
  height: 100vh;
} 
.receipt {
  height: 100vh;
  overflow-y: auto;
  width: 300px;
  transition: 1s ease-in;
  overflow-y: auto;
  overflow-x: hidden;
}

.receipt-heading {
  overflow-x: hidden;
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

}

.text-center {
  text-align: center;
}

.pd-left{
  padding-left: 7px;
}

</style>

