<template>
  <section>
    <ul class="favmovieview">
      <li>
        <img :src="mov.poster" />
      </li>
      <li>
        <strong>{{ mov.title }}</strong>
      </li>
      <li>
        <p>Popularność: {{ mov.popularity }}</p>
      </li>
      <li>
        <p>Liczba głosów: {{ mov.vote_count }}</p>
      </li>
    </ul>
  </section>
</template>

<script>
export default {
  props: {
    mov: {
      type: Object,
      required: false,
    },
  },
};
</script>

<style scoped>
.favmovieview {
  color: black;
  background-color: rgb(199, 169, 2);
  padding: 1rem;
  margin: 1rem;
  width: 100%;
  max-width: 16rem;
  text-align: center;
}
.movieview li strong {
  width: 12rem;
  display: inline-block;
}
img {
  width: 100%;
  height: auto;
}
ul {
  list-style: none;
}
a {
  color: rgb(124, 23, 23);
}
.details {
  text-align: left;
}
#overview {
  text-align: justify;
}
#genres,
#country {
  display: inline;
}
.messageblack {
  color: black;
}
.fav {
  width: 20%;
  cursor: pointer;
}
</style>
