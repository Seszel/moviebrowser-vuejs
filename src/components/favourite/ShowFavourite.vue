<template>
  <div>
    <ul class="favfilmlist">
      <favourite-movie
        v-for="mov in MOVIES"
        :key="mov.id"
        :mov="mov"
      ></favourite-movie>
    </ul>
  </div>
</template>

<script>
import FavouriteMovie from "./ShowFavouriteMovie.vue";
export default {
  components: { FavouriteMovie },
  data() {
    return {};
  },
};
</script>

<style scoped>
.favfilmlist {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
}
</style>
