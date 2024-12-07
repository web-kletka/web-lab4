<script>
import { defineComponent } from "vue";
import Header from "@/components/parts/header.vue";
import { useUserStore } from "@/stores/user";

export default defineComponent({
  components: { Header },
  setup() {
    const userStore = useUserStore();
    return { userStore }; // Делаем хранилище доступным в шаблоне
  }
});
</script>

<template>
  <body>
  <Header
      :login="userStore.currentUser?.user?.login || 'Гость'"
      :show_button="true"
      :buttons="[
        { text: '<', route: '/'}
      ]"
  />
  </body>
</template>

<style>
</style>
