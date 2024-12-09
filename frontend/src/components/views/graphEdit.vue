<script>
import Header from "@/components/parts/header.vue";
import {useUserStore} from "@/stores/user.js";
import Graph from "@/components/parts/graph.vue";

export default {
  name: "graphEdit",
  components: {Graph, Header},
  setup(){
    const userStore = useUserStore()

    return{
      userStore
    }
  }
}
</script>

<template>
  <Header
      :login="userStore.currentUser?.login || 'Гость'"
      :show_button="true"
      :buttons="[
        { text: '<', route: '/game'}
      ]"
  />

  <div class="main">
    <Graph :width = "600" :height = "600"/>
    <div class="input-func">

    </div>
  </div>

</template>

<style scoped>
@import "../style/body.css";

.main{
  display: flex;
  padding-left: 3em;
  padding-right: 3em;
  flex-direction: row;
}

.input-func{
  margin: 3em;
  width: 100%;
  border: #003366 1px solid;
}
</style>