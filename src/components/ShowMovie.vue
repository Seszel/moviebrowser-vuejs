<template>
  <section>
    <div class="movieview">
      <ul>
        <li>
          <img
            v-if="!isNotValid.poster"
            :src="'https://image.tmdb.org/t/p/w500' + poster_path"
          />
          <p v-else>Brak plakatu filmu</p>
        </li>
        <li>
          <strong>{{ title }}</strong>
        </li>
        <li>
          <p>Popularność: {{ popularity }}</p>
        </li>
        <li>
          <p>Liczba głosów: {{ vote_count }}</p>
        </li>
      </ul>
      <base-button @click="toggleDetails">
        {{ detailsAreVisible ? "Ukryj" : "Pokaż" }} szczegóły
      </base-button>
      <p class="message" v-if="isLoading">Ładowanie informacji...</p>
      <ul v-else-if="detailsAreVisible">
        <li>
          <strong>Gatunki:</strong>
          <p v-for="genre in movie.genres" :key="genre.id" id="genres">
            {{ genre.name }}.
          </p>
        </li>
        <li>
          <a
            v-if="!isNotValid.link"
            :href="'https://www.themoviedb.org/movie/' + movie.id"
            >Link do IMDB</a
          >
          <a v-else>Brak linku</a>
        </li>
        <li>
          <strong>Opis:</strong>
          <p v-if="!isNotValid.overview">{{ movie.overview }}</p>
          <p v-else>Brak opisu filmu</p>
        </li>
        <li>
          <strong>Kraj produkcji:</strong>
          <p
            v-for="country in movie.production_countries"
            :key="country.id"
            id="country"
          >
            {{ country.name }}.
          </p>
        </li>
      </ul>
    </div>
  </section>
</template>

<script>
import env from "@/env.js";

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
      required: false,
    },
    overview: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      detailsAreVisible: false,
      movie: [],
      isNotValid: {
        poster: false,
        overview: false,
        link: false,
      },
      isLoading: false,
    };
  },
  methods: {
    toggleDetails() {
      this.detailsAreVisible = !this.detailsAreVisible;
      if (this.detailsAreVisible === true) {
        this.searchDetails();
      }
    },
    searchDetails() {
      this.isLoading = true;
      const api_key = "?api_key=" + env.apikey;
      const language = "&language=en-US&language=pl-PL";
      var url =
        "https://api.themoviedb.org/3/movie/" +
        this.$props.id +
        api_key +
        language;
      fetch(url)
        .then((response) => response.json())
        .then((data) => {
          this.isLoading = false;
          this.movie = data;
        });
      this.checkIfValid("o", this.$props.overview, "");
      this.checkIfValid("link", this.movie.link, undefined);
    },
    checkIfValid(what, arg, type) {
      if (arg === type) {
        if (what === "o") {
          this.isNotValid.overview = true;
        } else if (what === "p") {
          this.isNotValid.poster = true;
        } else {
          this.isNotValid.link = true;
        }
      }
      //console.log(this.isNotValid.arg, arg, type);
    },
  },
  beforeMount() {
    this.checkIfValid("p", this.$props.poster_path, null);
  },
};
</script>

<style scoped>
.movieview {
  color: black;
  background-color: rgb(199, 169, 2);
  padding: 1rem;
  margin: 1rem;
  width: 100%;
  max-width: 15rem;
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
#genres,
#country {
  display: inline;
}
</style>
