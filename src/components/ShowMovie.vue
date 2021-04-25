<template>
  <section>
    <div id="movie-view">
      <ul>
        <li><img :src="'https://image.tmdb.org/t/p/w500' + poster_path" /></li>
        <li>
          <strong>{{ title }}</strong>
        </li>
        <li>
          <p>Popularity: {{ popularity }}</p>
        </li>
        <li>
          <p>Number of votes: {{ vote_count }}</p>
        </li>
      </ul>
      <button @click="toggleDetails">
        {{ detailsAreVisible ? "Hide" : "Show" }} Details
      </button>
      <ul v-if="detailsAreVisible">
        <li>
          <strong>Something:</strong>
          {{ sth }}
        </li>
        <li>
          <strong>Something:</strong>
          {{ sth }}
        </li>
      </ul>
    </div>
  </section>
</template>

<script>
export default {
  props: {
    id: {
      type: Number,
      required: true,
    },
    title: {
      type: String,
      required: true,
    },
    popularity: {
      type: Number,
      required: true,
    },
    vote_count: {
      type: Number,
      required: true,
    },
    poster_path: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      detailsAreVisible: false,
    };
  },
  methods: {
    toggleDetails() {
      this.detailsAreVisible = !this.detailsAreVisible;
    },
  },
};
</script>

<style>
#movie-view {
  color: black;
  background-color: rgb(199, 169, 2);
  padding: 1rem;
  margin: 1rem;
  width: 100%;
  max-width: 15rem;
  text-align: center;
}
img {
  width: 100%;
  height: auto;
}
#movie-view li strong {
  width: 12rem;
  display: inline-block;
}
ul {
  list-style: none;
}
#movie-view button {
  vertical-align: top;
}
</style>
