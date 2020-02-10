import Vue from 'vue';
import App from './App';
import '../styles/generalStyles.scss';
import { library } from '@fortawesome/fontawesome-svg-core'
import { faUserSecret, faCopy, faSignOutAlt, faTrashAlt, faTrash, faFileExport, faChevronDown, faChevronCircleDown } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

library.add(faUserSecret, faCopy, faSignOutAlt, faTrashAlt, faFileExport,faChevronCircleDown)

Vue.component('font-awesome-icon', FontAwesomeIcon)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  
  render: h => h(App)
})
