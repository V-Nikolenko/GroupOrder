<template>
  <div>
    <step1 v-bind:step=steps[0]></step1>    
    
    <step2 v-bind:step=steps[1]></step2> 

    <step3 v-bind:step=steps[2]></step3>    

    <step4 v-bind:step=steps[3]></step4>    

    
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
          isDone: false
        },
        
        {
          isActive: false,
          isDone: false
        },
        
        {
          isActive: false,
          isDone: false
        },

        {
          isActive: false,
          isDone: false
        }
      ]
    }
  }
}
</script>

<style lang="scss">
$color1: silver;

body {
  margin: 0;
  padding: 0;
  font-family: 'Roboto', sans-serif;
}

.extension {
  display: flex;
  flex-direction: column;
  width: 400px;

  &__step-heading,
  &__title {
    margin: 0;
    width: 100%;
    display: flex;
    align-items: center;
    padding: 3px;
    border: 1px solid $color1;
    box-sizing: border-box;
  }

  &__title {
    min-height: 40px;
    font-size: 22px;
  }

  &__step-heading {
    min-height: 35px;
    font-size: 18px;
    border-top: none;

    &_disabled {
      color: $color1;
    }
  }
   
  
}

h1 {
  width: 300px
}

p {
  font-size: 20px;
}

</style>

