<template>
  <section>
    <header>
      <h2>Movie browser</h2>
      <p>
        For me, the cinema is not slice of life, but a piece of cake.<sub>
          ~ Alfred Hitchcock</sub
        >
      </p>
    </header>
    <search-movie @movie-name="setMovieName"></search-movie>
    <ul id="grid">
      <show-movie
        v-for="movie in movies.results"
        :key="movie.id"
        :id="movie.id"
        :title="movie.title"
        :popularity="movie.popularity"
        :vote_count="movie.vote_count"
        :poster_path="movie.poster_path"
        :overview="movie.overview"
        :total_pages="movies.total_pages"
      ><show-details></show-details></show-movie>
    </ul>
  </section>
</template>

<script>
import ShowDetails from './components/ShowDetails.vue';
export default {
  components: { ShowDetails },
  data() {
    return {
      movieName: "",
      movies: [],
    };
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
        '&page=${pageNumber}' +
        "&query=" +
        this.movieName;
      fetch(url)
        .then((response) => response.json())
        .then((data) => {
          this.movies = data;
        });
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
header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px 16px;
  background-color: rgb(124, 23, 23);
  color: white;
  font-size: large;
}
header p {
  font-size: medium;
}
header p sub {
  font-size: small;
}
#app search-movie {
  text-align: center;
}
#app button {
  cursor: pointer;
  border: 1px solid black;
  background-color: rgb(199, 169, 2);
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
  display:flex;
  flex-wrap: wrap; 
  justify-content: center;
  align-items: center;
}
</style>
