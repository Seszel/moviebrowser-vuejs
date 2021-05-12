<template>
<section>
    <search-movie @movie-name="setMovieName"></search-movie>
    <base-dialog v-if="dialog" title="Brak filmu w bazie" @close="confirmError">
      <template #default>
        Przykro nam, ale nie ma takiego filmu w naszej bazie 😞.<br />
        Upewnij sie, czy nie popełniłeś literówki i spróbuj ponownie!
      </template>
      <template #actions>
        <base-button @click="confirmError">Rozumiem</base-button>
      </template>
    </base-dialog>
    <p class="message" v-else-if="isLoading">Ładowanie filmów...</p>
    <section v-else-if="movieName !== null && !error" id="searching-result">
      <sort-movies @sort-data="changeOrder" :new_name="movieName"></sort-movies>
      <the-pagination
        @number-page="setPage"
        :total_pages="movies.total_pages"
        :current_page="pageNumber"
      ></the-pagination>
      <ul class="filmlist">
        <show-movie
          v-for="movie in moviesfor"
          :key="movie.id"
          :movie="movie"
        ></show-movie>
      </ul>
      <the-pagination
        :total_pages="movies.total_pages"
        @number-page="setPage"
        :current_page="pageNumber"
      ></the-pagination>
    </section>
    </section>
</template>

<script>
import env from "@/env.js";
import ShowMovie from "./browser/ShowMovie.vue";
import ThePagination from "./browser/ThePagination.vue";
import SortMovies from "./browser/SortMovies.vue";
import SearchMovie from "./browser/SearchMovie.vue";

export default {
  components: {
    ShowMovie,
    ThePagination,
    SortMovies,
    SearchMovie,
  },
  data() {
    return {
      movieName: null,
      movies: [],
      pageNumber: 1,
      moviesfor: [],
      isLoading: false,
      order: "",
      error: false,
      dialog: false,
      fav: Object,
    };
  },
  watch: {
    order() {
      this.sortedProducts();
    },
    movieName() {
      this.pageNumber = 1;
    },
  },
  methods: {
    setMovieName(getname) {
      this.movieName = getname;
      this.order = "";
      this.error = false;
      this.searchMovies();
    },
    searchMovies() {
      this.isLoading = true;
      const api_key = "api_key=" + env.apikey;
      const language = "&language=en-US&language=pl-PL";
      let url =
        "https://api.themoviedb.org/4/search/movie?" +
        api_key +
        language +
        "&page=" +
        this.pageNumber +
        "&query=" +
        this.movieName;
      fetch(url)
        .then((response) => response.json())
        .then((data) => {
          this.isLoading = false;
          this.movies = data;
          this.moviesfor = data.results;
          this.checkIfValid(this.movies.total_pages, 0);
        });
    },
    setPage(getpage) {
      this.pageNumber = getpage;
      this.searchMovies();
    },
    changeOrder(getorder) {
      this.order = getorder;
    },
    sortedProducts() {
      if (this.order === "A") {
        return this.moviesfor.sort((a, b) => a.title.localeCompare(b.title));
      } else if (this.order === "Z") {
        return this.moviesfor.sort((a, b) => b.title.localeCompare(a.title));
      } else if (this.order === "P") {
        return this.moviesfor.sort((a, b) => b.popularity - a.popularity);
      } else if (this.order === "N") {
        return this.moviesfor.sort((a, b) => a.popularity - b.popularity);
      } else {
        return this.moviesfor;
      }
    },
    checkIfValid(arg, type) {
      if (arg === type) {
        this.dialog = true;
      }
    },
    confirmError() {
      this.dialog = false;
      this.error = true;
    },
    setFav(getfav){
      this.fav = getfav;
      console.log(this.fav);
    }
  },
};
</script>

<style scoped>
.filmlist {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
}
.message {
  color: white;
  text-align: center;
}
</style>