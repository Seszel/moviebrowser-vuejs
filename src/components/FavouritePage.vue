<template>
  <section>
    <h1>Ulubione filmy</h1>
    <p v-if="empty">
      Nie masz żadnych ulubionych filmów. <br />Przejdź do sekcji wyszukiwarki i
      dodaj tu swoje perełki.
    </p>
    <show-favourite
      :key="componentKey"
      @remove-movie="removed"
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
    },
  },
  activated() {
    this.componentKey += 1;
    // console.log(this.empty);
    // if (!this.MOVIES.length() === 0) {
    //   this.empty = false;
    // }
  },
};
</script>

<style scoped>
h1 {
  color: rgb(193, 180, 174, 1);
  text-align: center;
  font-size: xx-large;
  font-weight: bold;
  margin: 2rem;
}
p {
  color: white;
  text-align: center;
}
</style>
