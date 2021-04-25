<template>
  <div class="wrapper">
    <select v-model="currentOrder">
      <option value="nameaz" disabled>Sortowanie filmów</option>
      <option value="nameaz">Sortuj od a do z</option>
      <option value="nameza">Sortuj od z do a</option>
      <option value="value">Sortuj według największej popularności</option>
      <option value="value">Sortuj według najmniejszej popularności</option>
    </select>
  </div>
</template>

<script>
export default {
  data() {
    return {
    };
  },
  methods: {
   
  },
};
</script>

<style>
#app .wrapper {
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
