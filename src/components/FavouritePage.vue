<template>
  <section>
    <h1 style="color: yellow;">FAVOURITE</h1>
    <show-favourite :key="componentKey" @remove-movie="removed"></show-favourite>
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
  methods:{
    removed(){
      this.componentKey += 1;
    }
  },
  activated(){
    this.componentKey += 1;
  }
};
</script>
