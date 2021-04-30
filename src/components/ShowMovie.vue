<template>
  <section>
    <div class="movieview">
      <ul>
        <li>
          <img
            v-if="!isNotValid.poster"
            :src="'https://image.tmdb.org/t/p/w500' + poster_path"
          />
          <img
            v-else
            :src="'https://critics.io/img/movies/poster-placeholder.png'"
          />
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
      <ul v-else-if="detailsAreVisible" class="details">
        <li>
          <a
            v-if="!isNotValid.link"
            :href="'https://www.themoviedb.org/movie/' + movie.id"
            >Link do IMDB</a
          >
          <a v-else>Brak linku</a>
        </li>
        <li>
          <strong>Gatunki:</strong><br />
          <div v-if="!isNotValid.genres">
            <p>{{ genres(movie.genres) }}</p>
          </div>
          <p v-else>Brak informacji</p>
        </li>
        <li>
          <strong>Opis:</strong><br />
          <p v-if="!isNotValid.overview" id="overview">{{ movie.overview }}</p>
          <p v-else>Brak informacji</p>
        </li>
        <li>
          <strong>Kraj produkcji:</strong>
          <div v-if="!isNotValid.country">
            <p>{{ language(movie.production_countries) }}</p>
          </div>
          <p v-else>Brak informacji</p>
        </li>
      </ul>
    </div>
  </section>
</template>

<script>
import env from "@/env.js";
import * as data from "../assets/countries_translate.json";

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
        country: false,
        genres: false,
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
      const language = "&language=pl-PL";
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
      this.checkIfValid("l", this.$props.id, undefined);
      this.checkIfValid("c", this.movie.production_countries, []);
      this.checkIfValid("g", this.movie.genres, []);
    },
    checkIfValid(what, arg, type) {
      if (arg === type) {
        if (what === "o") {
          this.isNotValid.overview = true;
        } else if (what === "p") {
          this.isNotValid.poster = true;
        } else if (what === "l") {
          this.isNotValid.link = true;
        } else if (what === "c") {
          this.isNotValid.country = true;
        } else if (what === "g") {
          this.isNotValid.genres = true;
        }
      }
    },
    language(prodCount) {
      var name_pl = [];
      const countries = data.default;
      prodCount.forEach((country, index) => {
        const name_pl_str = countries.find(
          (c) => c.code === country.iso_3166_1
        );
        if (index === prodCount.length - 1) {
          name_pl += name_pl_str.name_pl;
        } else {
          name_pl += name_pl_str.name_pl + ", ";
        }
      });
      return name_pl;
    },
    genres(gen) {
      var gen_str = "";
      gen.forEach((g, index) => {
        if (index === gen.length - 1) {
          gen_str += g.name;
        } else {
          gen_str += g.name + ", ";
        }
      });
      return gen_str;
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
</style>
