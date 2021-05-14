<template>
  <section>
    <h1>Ulubione filmy</h1>
    <show-favourite
      :key="componentKey"
      @remove-movie="removed"
      :empty="empty"
    ></show-favourite>
  </section>
</template>

<script>
import ShowFavourite from "./favourite/ShowFavourite.vue";
export default {
  components: {
    ShowFavourite,
  },
  data() {
    return {
      componentKey: 0,
      empty: true,
    };
  },
  methods: {
    removed() {
      this.componentKey += 1;
      if (this.MOVIES.length > 0) {
        this.empty = false;
      } else {
        this.empty = true;
      }
    },
  },
  activated() {
    this.componentKey += 1;
    if (this.MOVIES.length > 0) {
      this.empty = false;
    } else {
      this.empty = true;
    }
  },
};
</script>

<style scoped>
h1 {
  color: white;
  text-align: center;
  font-size: xx-large;
  font-weight: bold;
  margin: 2rem;
}
</style>
