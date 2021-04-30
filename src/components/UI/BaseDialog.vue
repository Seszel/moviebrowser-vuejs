<template>
  <teleport to="body">
    <div @click="$emit('close')"></div>
    <dialog open>
      <header>
        <slot name="header">
          <h2>{{ title }}</h2>
        </slot>
      </header>
      <section>
        <slot></slot>
      </section>
      <menu>
        <slot name="actions">
          <base-button @click="$emit('close')">Zamknij</base-button>
        </slot>
      </menu>
    </dialog>
  </teleport>
</template>

<script>
export default {
  props: {
    title: {
      type: String,
      required: false,
    },
  },
  emits: ["close"],
};
</script>

<style scoped>
div {
  position: fixed;
  top: 0;
  left: 0;
  height: 100vh;
  width: 100%;
  background-color: rgba(0, 0, 0, 0.75);
  z-index: 2;
}
dialog {
  position: fixed;
  top: 20vh;
  left: 10%;
  width: 80%;
  z-index: 100;
  border: none;
  padding: 0;
  margin: 0;
  overflow: hidden;
}
header {
  background-color: rgb(124, 23, 23);
  color: white;
  width: 100%;
  padding: 1rem;
}
header h2 {
  margin: 0;
}
section {
  padding: 1rem;
}
menu {
  padding: 1rem;
  display: flex;
  justify-content: flex-end;
  margin: 0;
}

@media (min-width: 600px) {
  dialog {
    left: calc(50% - 20rem);
    width: 40rem;
  }
}
@media (max-width: 600px) {
  dialog {
    left: calc(50% - 7.5rem);
    width: 15rem;
  }
}
</style>
