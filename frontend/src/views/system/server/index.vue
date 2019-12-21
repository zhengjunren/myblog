<template>
  <div class="app-container">
    <Iframe :src="server" />
  </div>
</template>

<script>
  import Iframe from "@/components/Iframe";
  export default {
    name: "business",
    components: {
      Iframe
    },
    data() {
      return {
        server: process.env.VUE_APP_BASE + "/static/server.html"
      }
    }
  }
</script>

<style scoped>

</style>
