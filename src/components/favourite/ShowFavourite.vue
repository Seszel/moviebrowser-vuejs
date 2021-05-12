<template>
  <div>
    <ul v-for="mov in MOVIES" :key="mov.title">
      <li style="color: yellow;">
        {{ mov.title }}
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  data() {
    return {
    };
  },
};
</script>
