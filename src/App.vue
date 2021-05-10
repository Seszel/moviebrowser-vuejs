<template>
  <section>
    <the-header @log-info="setLogInfo"></the-header>
    <the-views :logIn="logInfo"></the-views>
  </section>
</template>

<script>

import TheHeader from "./components/layouts/TheHeader.vue";
import TheViews from "./components/layouts/TheViews.vue"

export default {
  components: {
    TheHeader,
    TheViews
  },
  data() {
    return {
      logInfo: false
    }
  },
  methods:{
    setLogInfo(getinfo) {
      this.logInfo = getinfo;
    },
  }
};
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: Georgia, "Times New Roman", Times, serif;
}
html {
  background-color: black;
  background-position: bottom;
}
</style>
