<template>
  <section class="pagination">
    <strong>1</strong>
    <base-button mode="gray" type="pagination" @click="firstPage"
      >❮❮</base-button
    >
    <base-button mode="gray" type="pagination" @click="previousPage"
      >❮</base-button
    >
    <strong>{{ current_page }}</strong>
    <base-button mode="gray" type="pagination" @click="nextPage">❯</base-button>
    <base-button mode="gray" type="pagination" @click="lastPage"
      >❯❯</base-button
    >
    <strong>{{ total_pages }}</strong>
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
    },
  },
  emits: ["number-page"],
  data() {
    return {
      page: 1,
    };
  },
  methods: {
    nextPage() {
      if (this.current_page < this.$props.total_pages) {
        this.page = this.current_page + 1;
      } else {
        this.page = 1;
      }
      this.$emit("number-page", this.page);
    },
    previousPage() {
      if (this.current_page > 1) {
        this.page = this.$props.current_page - 1;
      } else {
        this.page = this.total_pages;
      }
      this.$emit("number-page", this.page);
    },
    firstPage() {
      this.page = 1;
      this.$emit("number-page", this.page);
    },
    lastPage() {
      this.page = this.total_pages;
      this.$emit("number-page", this.page);
    },
  },
};
</script>

<style scoped>
.pagination {
  justify-content: center;
  align-items: center;
  text-align: center;
  color: white;
}
</style>
