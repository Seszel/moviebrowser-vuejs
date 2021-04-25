<template>
  <div class="wrapper">
    <select v-model="selected">
      <option value=''>Kolejność przypadkowa</option>
      <option v-for="option in options" :value="option.value" :key="option.value">
        {{ option.text }}
      </option>
    </select>
  </div>
</template>

<script>
export default {
  emits: ['sort-data'],
  data() {
    return {
      selected: '',
      options: [
        { text: "Sortuj od a do z", value: "A" },
        { text: "Sortuj od z do a", value: "Z" },
        { text: "Sortuj według największej popularności", value: "P" },
        { text: "Sortuj według najmniejszej popularności", value: "N" },
      ],
    };
  },
  watch: {
    selected(){
      this.$emit('sort-data',this.selected);
    }
  },
  methods: {},
};
</script>

<style>
#app .wrapper {
  color: white;
  justify-content: center;
  align-items: center;
  text-align: center;
  padding: 1rem;
}
#app .wrapper select {
  height: 1.5rem;
  font-size: medium;
}
#app .wrapper select option {
  font-size: medium;
}
</style>
