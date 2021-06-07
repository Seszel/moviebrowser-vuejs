<template>
  <section>
    <form @submit.prevent="sendMovieName">
      <div>
        <label>Czego szukasz?</label>
      </div>
      <div>
        <input type="text" placeholder="Tytuł filmu" v-model="enteredName" />
        <base-button>Pokaż filmy</base-button>
      </div>
    </form>
  </section>
</template>

<script>
export default {
  emits: ["movie-name"],
  data() {
    return {
      enteredName: "",
    };
  },
  methods: {
    sendMovieName() {
      this.$emit("movie-name", this.enteredName);
    },
  },
};
</script>

<style scoped>
form {
  margin: 0.5rem auto;
  padding: 0.5rem;
  max-width: 30rem;
  text-align: center;
}
form div {
  margin: 0.5rem;
}
label {
  color: white;
  font-weight: bold;
  width: 20rem;
  /* display: inline-block; */
  font-size: large;
}
form input {
  background-color: white;
  width: 100%;
  /* height: 30px; */
  font-size: large;
  color: black;
  border: none;
  padding: 4px;
  margin: 8px;
  border-radius: 4px;
}

</style>
