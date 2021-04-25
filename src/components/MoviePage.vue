<template>
  <section class="pagination">
    <strong>1</strong>
    <button type="pagination" @click="firstPage">❮❮</button>
    <button type="pagination" @click="previousPage">❮</button>
    <strong>{{current_page}}</strong>
    <button type="pagination" @click="nextPage">❯</button>
    <button type="pagination" @click="lastPage">❯❯</button>
    <strong>{{total_pages}}</strong>
  </section>
</template>

<script>
export default {
  props: {
    total_pages: {
      type: Number,
      required: true,
    },
    current_page: {
      type: Number,
      required: true,
    }
  },
  emits: ["number-page"],
  data() {
    return {
      page: 1,
    };
  },
  methods: {
    nextPage() {
      if (this.page < this.$props.total_pages) {
        this.page += 1;
      } else {
        this.page = 1;
      }
      this.$emit("number-page", this.page);
    },
    previousPage() {
      if (this.page > 1) {
        this.page -= 1;
      } else {
        this.page = this.$props.total_pages;
      }
      this.$emit("number-page", this.page);
    },
    firstPage() {
      this.page = 1;
      this.$emit("number-page", this.page);
    },
    lastPage() {
      this.page = this.$props.total_pages;
      this.$emit("number-page", this.page);
    },
  },
};
</script>

<style scoped>
#app .pagination {
  justify-content: center;
  align-items: center;
  text-align: center;
  color: white;
}
#app .pagination button {
  color: white;
  background-color: rgb(59, 56, 56);
}
#app .pagination button:hover{
  background-color: rgb(124, 23, 23);
}
</style>
