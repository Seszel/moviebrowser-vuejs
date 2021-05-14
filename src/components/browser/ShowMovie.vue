<template>
  <section>
    <div class="movieview">
      <ul>
        <li>
          <img
            v-if="!isNotValid.poster"
            :src="'https://image.tmdb.org/t/p/w500' + movie.poster_path"
          />
          <img
            v-else
            :src="'https://critics.io/img/movies/poster-placeholder.png'"
          />
        </li>
        <li>
          <strong>{{ movie.title }}</strong>
        </li>
        <li>
          <p>Popularność: {{ movie.popularity }}</p>
        </li>
        <li>
          <p>Liczba głosów: {{ movie.vote_count }}</p>
        </li>
      </ul>
      <div class="btn-holder">
        <base-button @click="toggleDetails" :mode="!logIn ? '' : 'details'">Pokaż szczegóły</base-button>
        <base-button v-if="logIn" @click="addToFavourites" :mode="isFavourite ? 'favourite' : 'notfavourite'"
          >{{ isFavourite ? "Usuń z " : "Dodaj do " }}ulubionych</base-button
        >
      </div>
    </div>
    <base-dialog v-if="modal" :title="movie.title" @close="confirmDetails">
      <template #default>
        <p class="messageblack" v-if="isLoading">Ładowanie informacji...</p>
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
            <div>
              <p>{{ genres(movieDet.genres) }}</p>
            </div>
          </li>
          <li>
            <strong>Opis:</strong><br />
            <p v-if="!isNotValid.overview" id="overview">
              {{ movie.overview }}
            </p>
            <p v-else>Brak informacji</p>
          </li>
          <li>
            <strong>Kraj produkcji:</strong>
            <div>
              <p>{{ language(movieDet.production_countries) }}</p>
            </div>
          </li>
        </ul>
      </template>
      <template #actions>
        <base-button @click="confirmDetails">Ukryj szczegóły</base-button>
      </template>
    </base-dialog>
  </section>
</template>

<script>
import env from "@/env.js";
import * as data from "@/countries_translate.json";

export default {
  props: {
    movie: {
      type: Object,
      required: true,
    },
    logIn: {
      type: Boolean,
      required: false,
    },
  },
  data() {
    return {
      detailsAreVisible: false,
      movieDet: [],
      isNotValid: {
        poster: false,
        overview: false,
        link: false,
      },
      isLoading: false,
      modal: false,
      favouriteMovie: {
        poster: "",
        title: "",
        popularity: "",
        vote_count: "",
        overview: "",
        link: "",
        genres: "",
        countries: "",
        id: null,
      },
      isFavourite: false,
    };
  },
  methods: {
    toggleDetails() {
      this.detailsAreVisible = !this.detailsAreVisible;
      if (this.detailsAreVisible === true) {
        // this.searchDetails();
        this.modal = true;
      }
    },
    searchDetails() {
      this.isLoading = true;
      const api_key = "?api_key=" + env.apikey;
      const language = "&language=pl-PL";
      let url =
        "https://api.themoviedb.org/3/movie/" +
        this.$props.movie.id +
        api_key +
        language;
      fetch(url)
        .then((response) => response.json())
        .then((data) => {
          this.isLoading = false;
          this.movieDet = data;
        });
      this.checkIfValid("o", this.$props.movie.overview, "");
      this.checkIfValid("l", this.$props.movie.id, undefined);
    },
    checkIfValid(what, arg, type) {
      if (arg === type) {
        if (what === "o") {
          this.isNotValid.overview = true;
        } else if (what === "p") {
          this.isNotValid.poster = true;
        } else if (what === "l") {
          this.isNotValid.link = true;
        }
      }
    },
    language(prodCount) {
      let name_pl = "";
      const countries = data.default;
      if (prodCount.length > 0) {
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
      } else name_pl = "Brak informacji";
      return name_pl;
    },
    genres(gen) {
      let gen_str = "";
      if (gen.length > 0) {
        gen.forEach((g, index) => {
          if (index === gen.length - 1) {
            gen_str += g.name;
          } else {
            gen_str += g.name + ", ";
          }
        });
      } else gen_str = "Brak informacji";
      return gen_str;
    },
    confirmDetails() {
      this.modal = false;
      this.detailsAreVisible = false;
    },
    addToFavourites() {
      this.isFavourite = !this.isFavourite;
      if (this.isFavourite) {
        this.favouriteMovie.id = this.$props.movie.id;
        this.favouriteMovie.title = this.$props.movie.title;
        if (this.isNotValid.poster === false) {
          this.favouriteMovie.poster =
            "https://image.tmdb.org/t/p/w500" + this.$props.movie.poster_path;
        } else {
          this.favouriteMovie.poster =
            "https://critics.io/img/movies/poster-placeholder.png";
        }
        this.favouriteMovie.popularity = this.$props.movie.popularity;
        this.favouriteMovie.vote_count = this.$props.movie.vote_count;
        if (this.isNotValid.overview === false) {
          this.favouriteMovie.overview = this.$props.movie.overview;
        } else {
          this.favouriteMovie.overview = "Brak informacji";
        }
        if (this.isNotValid.link === false) {
          this.favouriteMovie.link =
            "https://www.themoviedb.org/movie/" + this.$props.movie.id;
        } else {
          this.favouriteMovie.link = "Brak linku";
        }
        this.favouriteMovie.genres = this.genres(this.movieDet.genres);
        this.favouriteMovie.countries = this.language(
          this.movieDet.production_countries
        );
        if (!this.MOVIES.includes(this.favouriteMovie)) {
          this.MOVIES.push(this.favouriteMovie);
          console.log(this.MOVIES);
        }
      } else {
        this.removeElement(this.MOVIES, this.favouriteMovie);
        console.log(this.MOVIES);
      }
      // this.$parent.$emit("favourite-movie", this.favouriteMovie);
    },
    removeElement(array, elem) {
      var index = array.indexOf(elem);
      if (index > -1) {
        array.splice(index, 1);
      }
    },
  },
  beforeMount() {
    this.MOVIES.forEach((element) => {
      if (element.id === this.$props.movie.id) {
        console.log(element.id);
        this.favouriteMovie = element;
        this.isFavourite = true;
      }
    });
    this.checkIfValid("p", this.$props.movie.poster_path, null);
    this.searchDetails();
  },
};
</script>

<style scoped>
.movieview {
  /* color: rgb(228, 221, 218); */
  color: white;
  /* background-color: rgb(199, 169, 2); */
  background-color: rgb(193, 180, 174, 0.4);
  transition: background-color 0.3s;
  border-radius: 6px;
  padding: 0.75rem;
  margin: 1rem;
  width: 100%;
  max-width: 16rem;
  text-align: center;
  height: 33rem;
  justify-content: space-between;
  flex-direction: column;
  display: flex;
}
.movieview:hover {
  background-color: rgb(193, 180, 174, 0.7);
  color: black;
}
.movieview li strong {
  width: 12rem;
  display: inline-block;
}
.movieview .btn-holder {
  justify-content: center;
  align-content: center;
  display: flex;
}
img {
  width: 100%;
  height: auto;
  border-radius: 6px;
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
