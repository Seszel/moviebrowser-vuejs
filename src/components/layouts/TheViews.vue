<template>
  <section>
    <div v-if="logIn" class="message">
      <base-button @click="setSelectedTab('home-page')"
        >Wyszukiwanie filmów</base-button
      >
      <base-button @click="setSelectedTab('favourite-movies')"
        >Moje ulubione filmy</base-button
      >
    </div>
    <keep-alive>
      <component :is="selectedTab"></component>
    </keep-alive>
  </section>
</template>

<script>
import HomePage from "../BrowserPage.vue";
import FavouriteMovies from "../FavouritePage.vue";

export default {
  components: { HomePage, FavouriteMovies },
  props: {
    logIn: {
      type: Boolean,
      required: true,
    },
  },
  data() {
    return {
      selectedTab: "home-page",
    };
  },
  computed: {
    SearchMoviesButtonMode() {
      return this.selectedTab === "search-movie" ? null : "flat";
    },
    FavMoviesButtonMode() {
      return this.selectedTab === "favourite-movies" ? null : "flat";
    },
  },
  methods: {
    setSelectedTab(tab) {
      this.selectedTab = tab;
    },
  },
};
</script>

<style scoped>
.message {
  color: white;
  text-align: center;
}
</style>
