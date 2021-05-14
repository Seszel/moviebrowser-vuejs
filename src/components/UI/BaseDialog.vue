<template>
  <teleport to="body">
    <div @click="$emit('close')"></div>
    <dialog open>
      <header>
        <slot name="header">
          <h3>{{ title }}</h3>
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
  border-radius: 4px;
}
dialog {
  position: fixed;
  top: 10vh;
  left: 10%;
  width: 80%;
  z-index: 100;
  border: none;
  padding: 0;
  margin: 0;
  overflow: hidden;
  border-radius: 4px;
  background-color: rgba(209, 199, 194, 0.8);
}
header {
  background-color: rgb(94, 20, 24, 0.8);
  color: white;
  width: 100%;
  padding: 0.6rem;
}
header h2 {
  margin: 0;
  color: white;
}
section {
  padding: 0.5rem;
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
    overflow-y: auto;
  }
}
</style>
