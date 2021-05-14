<template>
  <section>
    <the-header @log-info="setLogInfo" @which-page="setPage"></the-header>
    <the-views :logIn="logInfo" :page="page" :key="logInfo"></the-views>
  </section>
</template>

<script>
import TheHeader from "./components/layouts/TheHeader.vue";
import TheViews from "./components/layouts/TheViews.vue";



export default {
  components: {
    TheHeader,
    TheViews,
  },
  data() {
    return {
      logInfo: false,
      page: 'browser-page'
    };
  },
  methods: {
    setLogInfo(getinfo) {
      this.logInfo = getinfo;
    },
    setPage(getPage){
      this.page = getPage;
    }
  },
};
</script>

<style>
* {
  margin: 0;
  padding: 0;
  font-family: 'Merriweather', serif;
  -webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	box-sizing: border-box; 
}
html {
  background: url(assets/felix-mooneeram-evlkOfkQ5rE-unsplash.jpg) no-repeat
    center center fixed;
  -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-size: cover;
}
</style>
