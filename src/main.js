import { createApp } from 'vue';

import App from './App.vue';

import SearchMovie from './components/SearchMovie.vue';
import BaseButton from './components/UI/BaseButton.vue'

const app = createApp(App);

app.component('search-movie', SearchMovie);
app.component('base-button', BaseButton);

app.mount('#app');
