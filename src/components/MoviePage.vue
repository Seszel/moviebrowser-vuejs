<template>
  <section>
    <button type="paggination" @click="previousPage">Previous</button>
    <strong>{{current_page}}</strong>
    <button type="paggination" @click="nextPage">Next</button>
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
  },
};
</script>

<style scoped>
#app section {
  color: white;
  text-align: center;
}
</style>
