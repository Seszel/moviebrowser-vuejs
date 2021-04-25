import { createApp } from "vue";

import App from "./App.vue";
import SearchMovie from "./components/SearchMovie.vue";

const app = createApp(App);

app.component("search-movie", SearchMovie);

app.mount("#app");
