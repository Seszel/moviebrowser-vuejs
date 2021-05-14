<template>
  <div class="wrapper">
    <select v-model="selected">
      <option value="" disabled>Kolejność domyślna</option>
      <option
        v-for="option in options"
        :value="option.value"
        :key="option.value"
      >
        {{ option.text }}
      </option>
    </select>
  </div>
</template>

<script>
export default {
  props: {
    new_name: {
      type: String,
      required: true,
    },
  },
  emits: ["sort-data"],
  data() {
    return {
      selected: "",
      options: [
        { text: "Sortuj od a do z", value: "A" },
        { text: "Sortuj od z do a", value: "Z" },
        { text: "Sortuj według największej popularności", value: "P" },
        { text: "Sortuj według najmniejszej popularności", value: "N" },
      ],
    };
  },
  watch: {
    selected() {
      this.$emit("sort-data", this.selected);
    },
    new_name() {
      this.selected = "";
      this.$emit("sort-data", this.selected);
    },
  },
};
</script>

<style scoped>
.wrapper {
  color: white;
  justify-content: center;
  align-items: center;
  text-align: center;
  padding: 1rem;
}
.wrapper select {
  height: 1.5rem;
  font-size: medium;
  /* text-align: center; */
}
.wrapper select option {
  font-size: medium;
  /* text-align: center; */
}

@media (max-width: 600px) {
  .wrapper select option {
    font-size: small;
  }
}
</style>
