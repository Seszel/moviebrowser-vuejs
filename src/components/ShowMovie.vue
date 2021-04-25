<template>
  <section>
    <div id="movieview">
      <ul>
        <li><img :src="'https://image.tmdb.org/t/p/w500' + poster_path" /></li>
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
      <button @click="toggleDetails">
        {{ detailsAreVisible ? "Ukryj" : "Pokaż" }} szczegóły
      </button>

      <ul v-if="detailsAreVisible" id="details"> 
        <li>
          <strong>Gatunki:</strong>
          <p v-for="genre in movie.genres" :key="genre.id" id="genres">{{ genre.name }}. </p>
        </li>
        <li>
          <a :href="'https://www.themoviedb.org/movie/'+movie.id">Link do IMDB</a>
        </li>
        <li>
          <strong>Opis:</strong>
            <p>{{movie.overview}}</p>
        </li>
        <li>
          <strong>Kraj produkcji:</strong>
          <p v-for="country in movie.production_countries" :key="country.id" id="country">{{country.name}}. </p>
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
      movie: [],
    };
  },
  methods: {
    toggleDetails() {
      this.detailsAreVisible = !this.detailsAreVisible;
      if(this.detailsAreVisible===true){
        this.searchDetails();
      }
    },
    searchDetails() {
      const api_key = "?api_key=41bd29c17951314ef43a94fc57c7c88d";
      const language = "&language=en-US&language=pl-PL";
      var url =
        "https://api.themoviedb.org/3/movie/" +
        this.$props.id +
        api_key +
        language;
      fetch(url)
        .then((response) => response.json())
        .then((data) => {
          this.movie = data;
        });
    },
  },
};
</script>

<style>
#movieview {
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
#movieview li strong {
  width: 12rem;
  display: inline-block;
}
ul {
  list-style: none;
}
#movieview button {
  vertical-align: top;
}
#genres, #country{
  display:inline
}
#details {

}
</style>
