<template>
  <header>
    <h2 @click="reloadPage">Movie browser</h2>
    <div class="user">
      <div class="logedin" v-if="logIn">
        <p v-if="logIn">
          Witaj <strong>{{ nick }}!</strong>
        </p>
        <ul>
          <li class="dropdown">
            <base-button class="dropbtn">Wybierz stronę</base-button>
            <div class="dropdown-content">
              <p @click="whichPage('browser-page')">
                Wyszukiwarka filmów
              </p>
              <p @click="whichPage('favourite-page')">
                Ulubione filmy
              </p>
              <p @click="logInOut">Wyloguj się</p>
            </div>
          </li>
        </ul>
      </div>
      <form @submit.prevent="logInOut">
        <div v-if="!logIn">
          <label>Zaloguj się, aby odkryć nowe funkcje</label>
          <input
            v-if="!logIn"
            type="text"
            placeholder="Twój nick"
            v-model="nick"
          />
          <base-button>Zaloguj się</base-button>
        </div>
      </form>
    </div>
  </header>
</template>

<script>
export default {
  emits: ["log-info", "which-page"],
  data() {
    return {
      logIn: false,
      nick: "",
    };
  },
  methods: {
    reloadPage() {
      window.location.reload();
    },
    logInOut() {
      this.logIn = !this.logIn;
      this.sendLogInfo();
      if (this.logIn === false) this.nick = "";
      if (this.nick === "") this.nick = "user";
      this.$emit("which-page", 'browser-page');
    },
    sendLogInfo() {
      this.$emit("log-info", this.logIn);
    },
    whichPage(getPage) {
      this.$emit("which-page", getPage);
    },
  },
};
</script>

<style scoped>
header {
  padding: 4px 8px;
  background-color: #5e1418;
  color: white;
  display: flex;
  align-items: center;
  justify-content: space-between;
}
h2 {
  font-size: x-large;
  cursor: pointer;
}
.user {
  display: flex;
  align-content: space-around;
  text-align: center;
}
.user label {
  font-size: medium;
  margin: 4px;
}
.user input {
  border-radius: 4px;
  border: none;
  padding: 4px;
  margin: 4px;
}
.user p {
  padding: 8px;
}

ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
  overflow: hidden;
  background-color: #5e1418;
  left: 0;
}

li {
  float: left;
}

li p {
  display: inline-block;
  color: black;
  text-align: center;
  padding: 8px;
  text-decoration: none;
  border-radius: 4px;
}

li .dropdown {
  display: inline-block;
}

.dropdown-content {
  display: none;
  position: absolute;
  background-color: white;
  min-width: 160px;
  box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
  z-index: 1;
  border-radius: 4px;
}
.dropdown-content p {
  color: black;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
  text-align: left;
  cursor: pointer;
}
.dropdown-content p:hover {
  background-color: rgb(193, 180, 174, 0.4);
  color: black;
}
.dropdown:hover .dropdown-content {
  display: block;
  left: auto;
  right: 0;
  margin-right: 10px;
}
.logedin {
  display: grid;
  grid-auto-flow: column;
}

@media (max-width: 600px) {
  header {
    flex-wrap: wrap;
    justify-content: space-around;
    text-align: center;
  }
  h2 {
    text-align: center;
    padding-bottom: 8px;
  }
}
</style>
