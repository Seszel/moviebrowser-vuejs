<template>
    <button :class="mode">
        <slot></slot>
    </button>
</template>

<script>
export default {
    props: ['mode'],
}
</script>

<style scoped>
button {
  cursor: pointer;
  border: 1px solid black;
  background-color: rgb(199, 169, 2);
  transition: background-color 0.3s;
  color: black;
  padding: 0.15rem 0.3rem;
  box-shadow: 1px 1px 2px rgba(0, 0, 0, 0.26);
  display: block;
  text-align: center;
  display: inline;
  font-size: large;
  margin: 0.5rem;
}
button:hover {
  background-color: rgb(124, 23, 23);
  color: white;
  border: 1px solid white;
}
.gray {
  color: white;
  background-color: rgb(59, 56, 56);
}
.gray:hover {
  background-color: rgb(124, 23, 23);
}
</style>