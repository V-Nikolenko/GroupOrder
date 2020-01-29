<template>
  <div>
    <step1 v-bind:step=steps[0] v-on:next='nextStep(steps[0])'></step1>    
    
    <step2 v-bind:step=steps[1] v-on:next='nextStep(steps[1])'></step2> 

    <step3 v-bind:step=steps[2] v-on:next='nextStep(steps[2])'></step3>    

    <step4 v-bind:step=steps[3] v-on:next='nextStep(steps[3])'></step4>    
    
  </div>
</template>

<script>
import step1 from '../components/step1';
import step2 from '../components/step2';
import step3 from '../components/step3';
import step4 from '../components/step4';

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
      steps: [
        {
          isActive: true,
          isDone: false,
          title: '1. Обрати заклад'
        },
        
        {
          isActive: false,
          isDone: false,
          title: '2. Доповнити замовлення'
        },
        
        {
          isActive: false,
          isDone: false,
          title: '3. Зібрати замовлення'
        },

        {
          isActive: false,
          isDone: false,
          title: '4. Показати борги'
        }
      ]
    }
  },
  methods: {
    nextStep: function(step) {
      let currentId =  this.steps.indexOf(step)

      if (currentId === (this.steps.length-1)) {
        return
      } else {
        step.isActive = !step.isActive;
        step.isDone = !step.isDone;
        this.steps[currentId+1].isActive = true;
      }

    }
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

  &__title {
    margin: 0;
    width: 100%;
    display: flex;
    align-items: center;
    padding: 3px;
    border: 1px solid $color2;
    box-sizing: border-box;
  }

  &__title {
    min-height: 40px;
    font-size: 22px;
  }
}

.doneStep {
  box-sizing: border-box;
  width: 100%;
  font-size: 16px;
  min-height: 35px;
  border: 1px solid $color2;
  border-top: none;
}

h1 {
  width: 300px
}

</style>

