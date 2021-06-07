<template>
  <section>
    <div class="movieview">
      <ul>
        <li>
          <img :src="mov.poster" />
        </li>
        <li>
          <strong>{{ mov.title }}</strong>
        </li>
        <li>
          <p>Popularność: {{ mov.popularity }}</p>
        </li>
        <li>
          <p>Liczba głosów: {{ mov.vote_count }}</p>
        </li>
      </ul>
      <div class="layout">
        <base-button @click="changeTitle" mode="small">Zmień tytuł</base-button>
        <div class="btn-holder">
          <base-button @click="toggleDetails" mode="small"
            >Pokaż szczegóły</base-button
          >
          <base-button @click="removeFromFavourites(MOVIES, mov)" mode="small"
            >Usuń z ulubionych</base-button
          >
        </div>
      </div>
    </div>
    <base-dialog v-if="modal" :title="mov.title" @close="confirmDetails">
      <template #default>
        <ul class="details">
          <li>
            <strong>Opis:</strong><br />
            <p id="overview">
              {{ mov.overview }}
            </p>
          </li>
          <li>
            <strong>Gatunki:</strong><br />
            <div>
              <p>{{ mov.genres }}</p>
            </div>
          </li>
          <li>
            <strong>Kraj produkcji:</strong>
            <div>
              <p>{{ mov.countries }}</p>
            </div>
          </li>
          <li>
            <a :href="mov.link">Link do IMDB</a>
          </li>
        </ul>
      </template>
      <template #actions>
        <base-button @click="confirmDetails" mode="gray"
          >Ukryj szczegóły</base-button
        >
      </template>
    </base-dialog>
    <base-dialog v-if="change" title="Zmień tytuł filmu" @close="confirmTitle">
      <template #default>
        <form @submit.prevent="changeTitleName">
          <div>
            <label>Wpisz nowy tytuł</label>
          </div>
          <div>
            <input
              type="text"
              placeholder="Tytuł filmu"
              v-model="enteredTitle"
            />
            <base-button mode="gray">Zmień</base-button>
          </div>
        </form>
      </template>
      <template #actions>
        <base-button @click="confirmTitle" mode="gray">Zamknij</base-button>
      </template>
    </base-dialog>
  </section>
</template>

<script>
import BaseDialog from "../UI/BaseDialog.vue";
export default {
  components: { BaseDialog },
  emits: ["remove-movie"],
  props: {
    mov: {
      type: Object,
      required: false,
    },
  },
  data() {
    return {
      modal: false,
      change: false,
      enteredTitle: "",
    };
  },
  methods: {
    confirmDetails() {
      this.modal = false;
    },
    toggleDetails() {
      this.modal = true;
    },
    removeFromFavourites(array, elem) {
      var index = array.indexOf(elem);
      if (index > -1) {
        array.splice(index, 1);
      }
      this.$parent.$emit("remove-movie", true);
    },
    changeTitle() {
      this.change = true;
    },
    confirmTitle() {
      this.change = false;
    },
    changeTitleName() {
      this.MOVIES.forEach((element, i) => {
        if (element.id === this.$props.mov.id) {
          this.MOVIES[i].title = this.enteredTitle;
        }
      });
      this.change = false;
    },
  },
};
</script>

<style scoped>
.movieview {
  color: white;
  background-color: rgb(193, 180, 174, 0.4);
  transition: background-color 0.3s;
  border-radius: 6px;
  padding: 0.75rem;
  margin: 1rem;
  width: 100%;
  max-width: 16rem;
  text-align: center;
  height: 36rem;
  justify-content: space-between;
  flex-direction: column;
  display: flex;
}
.movieview:hover {
  background-color: rgb(193, 180, 174, 0.7);
  color: black;
}
.movieview li strong {
  width: 12rem;
  display: inline-block;
}
.movieview .btn-holder {
  justify-content: center;
  align-content: center;
  display: flex;
}
.movieview .layout {
  display: grid;
}
input {
  border-radius: 4px;
  border: none;
  padding: 4px;
  margin: 4px;
  width: 65%;
}
label {
  margin: 4px;
}
img {
  width: 100%;
  height: auto;
  border-radius: 6px;
}
ul {
  list-style: none;
}

a {
  color: #5e1418;
}
.details {
  text-align: left;
}
.details li {
  margin: 8px;
}
#overview {
  text-align: justify;
}
#genres,
#country {
  display: inline;
}
.messageblack {
  color: black;
}
.fav {
  width: 20%;
  cursor: pointer;
}
</style>
