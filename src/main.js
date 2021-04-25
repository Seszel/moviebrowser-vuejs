import { createApp } from 'vue';

import App from './App.vue';
import SearchMovie from './components/SearchMovie.vue';
import ShowMovie from './components/ShowMovie.vue';

const app = createApp(App);

app.component('search-movie', SearchMovie);
app.component('show-movie', ShowMovie);

app.mount('#app');
