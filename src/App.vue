<template>
  <section>
    <the-header></the-header>
    <the-views></the-views>
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
