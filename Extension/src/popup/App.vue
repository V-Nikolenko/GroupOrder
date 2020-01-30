<template>
<div class="container">
  <div class='extension'>
    <header>
      <h1 class="extension__title">Group Order</h1>
    </header>

    <div v-if="isLoaded">
      
      <step1 v-bind:step=steps[0] v-on:next='nextStep'></step1>    
      
      <step2 v-bind:step=steps[1] v-on:next='nextStep'></step2> 

      <step3 v-bind:step=steps[2] v-on:next='nextStep'></step3>

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
import {stepFactory} from '../components/stepService';

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
    step4
  },

  data () {
    return {
      stepService: null
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

  methods: {
    nextStep: function(step) {
      this.stepService.nextStep(step)
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
}

.receipt_none {
  width: 0px;
  overflow: hidden;
  transition: 2s ease-in-out;
} 
.receipt {
  width: 200px;
  transition: 2s ease-in-out;
}

</style>

