<template>
  <section>
    <header>
      <h2 @click="reloadPage">Movie browser</h2>
      <a href="https://pl.wikipedia.org/wiki/Alfred_Hitchcock"
        ><p>
          For me, the cinema is not slice of life, but a piece of cake.<sub>
            ~ Alfred Hitchcock</sub
          >
        </p></a
      >
    </header>
  </section>
</template>

<script>
export default {
  methods: {
    reloadPage() {
      window.location.reload();
    },
  },
};
</script>

<style scoped>
header {
  padding: 10px 16px;
  background-color: rgb(124, 23, 23);
  color: white;
  font-size: large;
  display: flex;
  align-items: center;
  justify-content: space-between;
}
header p {
  font-size: medium;
}
header p sub {
  font-size: small;
}
h2 {
  cursor: pointer;
}
a {
  color: white;
  text-decoration: none;
}
@media (max-width: 600px) {
  header {
    flex-wrap: wrap;
    justify-content: space-around;
    text-align: center;
  }
  header p {
    font-size: small;
  }
  header p sub {
    font-size: x-small;
  }
  h2 {
    text-align: center;
    padding-bottom: 8px;
  }
}
</style>
