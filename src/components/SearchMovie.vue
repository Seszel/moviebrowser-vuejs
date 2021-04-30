<template>
  <section>
    <form @submit.prevent="sendMovieName">
      <div>
        <label>Czego szukasz?</label>
      </div>
      <div>
        <input type="text" placeholder=" Tytuł filmu" v-model="enteredName" />
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
  margin-right: 1rem;
  width: 20rem;
  display: inline-block;
  font-size: large;
}
form input {
  background-color: white;
  width: 100%;
  height: 30px;
  font-size: large;
  color: rgb(124, 23, 23);
  outline: none;
}
input[type="text"]:focus {
  border: 3px solid rgb(199, 169, 2);
}
</style>
