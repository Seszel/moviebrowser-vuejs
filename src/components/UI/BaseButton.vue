<template>
  <button :class="mode">
    <slot></slot>
  </button>
</template>

<script>
export default {
  props: ["mode"],
};
</script>

<style scoped>
button {
  cursor: pointer;
  border: none;
  background-color: rgb(193, 180, 174, 1);
  transition: background-color 0.3s;
  color: black;
  padding: 0.35rem 0.35rem;
  box-shadow: 1px 1px 2px rgba(0, 0, 0, 0.26);
  display: block;
  text-align: center;
  display: inline;
  font-size: medium;
  margin: 4px;
  border-radius: 4px;
}
button:hover {
  background-color: rgb(193, 180, 174, 0.4);
  color: white; 
}
.gray {
  color: white;
  background-color: rgb(59, 56, 56);
}
.gray:hover {
  background-color: rgb(124, 23, 23);
}
.notfavourite{
  padding: 2px;
  font-size: small;
}
.favourite{
  padding: 2px;
  font-size: small;
  background-color: rgb(193, 180, 174, 0.5);
}
.details {
  padding: 2px;
  text-align: center;
}
.small {
  font-size: small;
}
</style>
