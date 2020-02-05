<template>

<div class="container">
  <div v-if="isLoaded">
    <order 
      v-bind:class="[isDisplay ? 'receipt' : 'receipt_none']"
      v-bind:members="members"
      v-bind:code="stepService.steps[0].data.code">
    </order>
  </div>
  
  <div class='extension'>
    <header>
      <h1 class="extension-title">Group Order</h1>
    </header>

  <div>{{stepService}}</div>

    <div v-if="isLoaded">

      <step1 v-bind:step="steps[0]" v-on:next="nextStep" v-on:logOut="logOut"></step1>    
      
      <step2 v-bind:step="steps[1]" v-on:next="nextStep"></step2> 

      <step3 v-bind:step="steps[2]"
        v-on:next='nextStep' 
        v-on:display="isDisplay = !isDisplay">
      </step3>

      <step4 v-bind:step="steps[3]" v-on:next="nextStep"></step4>
      
    </div>

  </div>

</div>

</template>

<script>
import step1 from '../components/step1';
import step2 from '../components/step2';
import step3 from '../components/step3';
import step4 from '../components/step4';
import order from '../components/order';
import listItem from '../components/list-item';
import { stepFactory } from '../components/stepService';
import { sendGetOrdersListRequest, sendConnectWithCodeRequest } from '../components/requests';

const STEPS = [
  {
    isActive: true,
    isDone: false,
    title: '1. Обрати заклад',
    data: {
      code: null,
      restaurant: null,
      url: null 
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
      fullPrice: null,
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
    order,
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
      })
      .then((resp)=> {
        this.members = Object.values(resp.members)
      })

    }
  },

  methods: {
    nextStep: function(step) {
      this.stepService.nextStep(step);
    },

    logOut: function() {
      this.stepService.setData( JSON.parse(JSON.stringify(STEPS)) );
    }
  },

  created() {
    chrome.storage.sync.get('steps', (chromeStorage) => {

      chrome.tabs.query({active: true, currentWindow: true}, (tabs) => {
        chrome.tabs.sendMessage(tabs[0].id, {type: 'getURLcode'}, (response) => {
          console.log(response)

          // if (chromeStorage.steps && response && response !== chromeStorage.steps[0].data.code) {
          if (response) {
            // console.log(chromeStorage.steps)
            // console.log(chromeStorage.steps[0].data.code)
            // console.log(response)
            if (chromeStorage.steps && chromeStorage.steps[0].data.code !== response) {
              this.stepService = stepFactory.create(JSON.parse(JSON.stringify(STEPS)));
            
              // console.log(this.stepService)


              sendConnectWithCodeRequest(response)
              .then((resp) => {
                if (resp.status === 200) {
                
                  return resp.json();
                } else throw new Error();
              })
              .then((resp)=> {

                  this.stepService.steps[0].data.code = response;
                  this.stepService.steps[0].data.restaurant = resp.restaurantName;
                  this.stepService.steps[0].data.url = resp.restaurantUrl;

                  this.nextStep(this.stepService.steps[0]);

              }).catch((error) => {{ console.log(error) }});

              } else {
                // console.log('localstorage match')
                this.stepService = chromeStorage.steps !== undefined
                        ? stepFactory.create(chromeStorage.steps) 
                        : stepFactory.create(JSON.parse(JSON.stringify(STEPS)));
            }
          } else {
            // console.log('no url code')
            this.stepService = chromeStorage.steps !== undefined
                        ? stepFactory.create(chromeStorage.steps) 
                        : stepFactory.create(JSON.parse(JSON.stringify(STEPS)));
          }

        });
      });
    });

  }

}
</script>

<style lang="scss" scoped>
@import "../styles/generalStyles.scss";

.extension {
  display: flex;
  flex-direction: column;
  min-width: 300px;

  &-title {
    margin: 0;
    padding: 5px 0 5px 5px;
    min-height: 40px;
    font-size: 22px;
    box-sizing: border-box;
  }
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

.container {
  display: flex;
  justify-content: space-between;
  height: 400px;
}
</style>

