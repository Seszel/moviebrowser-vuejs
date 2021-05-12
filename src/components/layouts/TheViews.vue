<template>
  <section>
    <div v-if="logIn" class="message">
      <base-button @click="setSelectedTab('browser-page')"
        >Wyszukiwanie filmów</base-button
      >
      <base-button @click="setSelectedTab('favourite-page')"
        >Moje ulubione filmy</base-button
      >
    </div>
    <keep-alive>
      <component :is="selectedTab"></component>
    </keep-alive>
  </section>
</template>

<script>
import BrowserPage from "../BrowserPage.vue";
import FavouritePage from "../FavouritePage.vue";

export default {
  components: { BrowserPage, FavouritePage },
  props: {
    logIn: {
      type: Boolean,
      required: true,
    },
  },
  data() {
    return {
      selectedTab: "browser-page",
      fav: Object,
      favouriteMovies: [],
      array: [],
      componentKey: 0,
    };
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
