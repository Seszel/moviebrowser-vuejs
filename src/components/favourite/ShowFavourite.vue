<template>
  <div>
    <p v-if="empty">
        Nie masz żadnych ulubionych filmów.
        <br />Przejdź do sekcji wyszukiwarki i dodaj tu swoje perełki.
      </p>
    <ul class="favfilmlist" v-else>
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
  props: {
    empty: {
      type: Boolean,
      required: true,
    }
  },
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
p {
  color: rgb(193, 180, 174, 1);
  text-align: center;
}
</style>
