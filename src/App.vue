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
  </section>
</template>

<script>
export default {
  data() {
    return {
      movieName: "",
    };
  },
  methods: {
    setMovieName(getname) {
      this.movieName = getname;
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
</style>
