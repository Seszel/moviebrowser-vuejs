<template>
  <section>
    <keep-alive>
      <component :is="page" :logIn="logIn"></component>
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
    page:{
      type: String,
      required: false
    }
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
</style>
