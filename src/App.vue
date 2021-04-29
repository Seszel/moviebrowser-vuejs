<template>
  <section>
    <the-header></the-header>
    <search-movie @movie-name="setMovieName"></search-movie>
    <p class="message" v-if="isLoading">Ładowanie filmów...</p>
    <section v-else-if="movieName !== null" id="searching-result">
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
          :id="movie.id"
          :title="movie.title"
          :popularity="movie.popularity"
          :vote_count="movie.vote_count"
          :poster_path="movie.poster_path"
          :overview="movie.overview"
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
import TheHeader from "./components/layouts/TheHeader.vue";
import ShowMovie from "./components/ShowMovie.vue";
import ThePagination from "./components/layouts/ThePagination.vue";
import SortMovies from "./components/SortMovies.vue";

export default {
  components: {
    TheHeader,
    ShowMovie,
    ThePagination,
    SortMovies,
  },
  data() {
    return {
      movieName: null,
      movies: [],
      pageNumber: 1,
      moviesfor: [],
      isLoading: false,
      order: "",
      error: null,
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
      this.searchMovies();
    },
    searchMovies() {
      this.isLoading = true;
      const api_key = "api_key=" + env.apikey;
      const language = "&language=en-US&language=pl-PL";
      var url =
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
  },
};
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: Georgia, "Times New Roman", Times, serif;
}
html {
  background-color: black;
  background-position: bottom;
}
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
