<script>
export default {
    name: "header",
    data(){
      return{
        login: ""
      }
    },
    methods:{
      handleClick(button) {
        if (button.route) {
          this.$router.push(button.route);
        } else if (button.onClick) {
          button.onClick();
        }
      },
    },
    props: {
      login: {
        type: String,
        default: "",
      },
      show_button: {
        type: Boolean,
        required: true
      },
      buttons: {
        type: Array,
        default: () => [], // [{ text: '...', route: '/...', onClick: () => { ... } }]
      },
    },
}
</script>

<template>
  <div class="header" style="font-family: fantasy">
    <div class="left_menu">
      <button
          v-if="show_button"
          v-for="(button, index) in buttons"
          :key="index"
          class="menu_but"
          @click="handleClick(button)"
      >
        {{ button.text }}
      </button>
      <h2>{{login}}</h2>
    </div>
    <h2>Автор: Захарченко Роман P3231</h2>
  </div>
</template>

<style scoped>

  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    border-bottom: 1px solid #555;
    padding-bottom: 1em;
    margin-bottom: 2em;
    padding-top: 2em;
    margin-left: 2em;
    margin-right: 2em;
  }

  .header .left_menu{
    display: inline-flex;
    flex-direction: row;
    align-items: center;
  }

  .header .menu_but{
    padding: 10px;
    width: 100%;
    font-size: 1.1em;
    background: #444444;
    color: white;
    border: none;
    border-radius: 8px;
    cursor: pointer;
    text-decoration: none;
    transition: background-color 0.3s ease;
    margin-right: 0.3em;
  }

</style>