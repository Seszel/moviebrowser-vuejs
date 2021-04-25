<template>
  <section>
    <page-header></page-header>
    <search-movie @movie-name="setMovieName"></search-movie>
    <sort-movies v-if="movieName !== ''" @sort-data="changeOrder"></sort-movies>
    <movie-page
      :total_pages="movies.total_pages"
      @number-page="setPage"
      :current_page="pageNumber"
      v-if="movieName !== ''"
    ></movie-page>
    <ul id="filmlist">
      <show-movie
        v-for="movie in movies.results"
        :key="movie.id"
        :id="movie.id"
        :title="movie.title"
        :popularity="movie.popularity"
        :vote_count="movie.vote_count"
        :poster_path="movie.poster_path"
        :overview="movie.overview"
        @show-details="hideA"
      ></show-movie>
    </ul>
    <movie-page
      :total_pages="movies.total_pages"
      @number-page="setPage"
      :current_page="pageNumber"
      v-if="movieName !== ''"
    ></movie-page>
  </section>
</template>

<script>
export default {
  data() {
    return {
      movieName: "",
      movies: [],
      pageNumber: 1,
      order: "",
      moviesfor: []
    };
  },
  watch: {
    order(){
      this.sortedProducts();
    }
  },
  methods: {
    setMovieName(getname) {
      this.movieName = getname;
      this.searchMovies();
    },
    searchMovies() {
      const api_key = "api_key=41bd29c17951314ef43a94fc57c7c88d";
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
      if (this.order === 'A') {
        return this.moviesfor.sort((a, b) => a.title.localeCompare(b.title));
      }
      else if (this.order === 'Z') {
        return this.moviesfor.sort((a, b) => b.title.localeCompare(a.title));
      }
      else if (this.order === 'P') {
        return this.moviesfor.sort((a, b) => a.popularity - b.popularity);
      }
      else if (this.order === 'N'){
        return this.moviesfor.sort((a, b) => b.popularity - a.popularity);
      }
      else {
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
#app search-movie {
  text-align: center;
}
#app button {
  cursor: pointer;
  border: 1px solid black;
  background-color: rgb(199, 169, 2);
  transition: background-color 0.3s;
  color: black;
  padding: 0.15rem 0.3rem;
  box-shadow: 1px 1px 2px rgba(0, 0, 0, 0.26);
  display: block;
  text-align: center;
  display: inline;
  font-size: large;
  margin: 0.5rem;
}
#app button:hover {
  background-color: rgb(124, 23, 23);
  color: white;
  border: 1px solid white;
}
#app ul {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
}
</style>
