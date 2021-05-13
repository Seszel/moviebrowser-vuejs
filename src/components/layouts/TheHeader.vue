<template>
  <section>
    <header>
      <h2 @click="reloadPage">Movie browser</h2>
      <div class="user">
        <p v-if="logIn" class="message">{{nick}}</p>
        <form @submit.prevent="logInOut">
          <div v-if="!logIn">
            <label>Wpisz swoją nazwę uzytkownika</label>
          </div>
          <div>
            <input v-if="!logIn" type="text" placeholder="Twój nick" v-model="nick" />
            <base-button
              >{{ !logIn ? "Zaloguj" : "Wyloguj" }} mnie</base-button
            >
          </div>
        </form>
      </div>
    </header>
  </section>
</template>

<script>
export default {
  emits: ["log-info"],
  data() {
    return {
      logIn: false,
      nick: '',
    };
  },
  methods: {
    reloadPage() {
      window.location.reload();
    },
    logInOut() {
      this.logIn = !this.logIn;
      this.sendLogInfo();
      if (this.logIn === false) this.nick = '';
      if (this.nick === '') this.nick = 'user';
    },
    sendLogInfo() {
      this.$emit("log-info", this.logIn);
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
.user {
  display: flex;
  align-content: space-around;
  text-align: center;
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
