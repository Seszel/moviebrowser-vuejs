import { createApp } from 'vue';

import App from './App.vue';
import PageHeader from './components/PageHeader.vue'
import SearchMovie from './components/SearchMovie.vue';
import ShowMovie from './components/ShowMovie.vue';
import MoviePage from './components/MoviePage.vue';
import SortMovies from './components/SortMovies.vue';


const app = createApp(App);

app.component('page-header',PageHeader);
app.component('search-movie', SearchMovie);
app.component('show-movie', ShowMovie);
app.component('movie-page', MoviePage);
app.component('sort-movies',SortMovies);

app.mount('#app');
