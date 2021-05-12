<template>
  <section>
    <h1 style="color: yellow;">FAVOURITE</h1>
    <show-favourite :key="componentKey"></show-favourite>
  </section>
</template>

<script>
import ShowFavourite from './favourite/ShowFavourite.vue';
export default {
  components: {
    ShowFavourite
  },
  data() {
    return {
      movies: [],
      componentKey:0,
    };
  },
  watch: {
  },
  activated(){
    this.componentKey += 1;
  }
};
</script>
