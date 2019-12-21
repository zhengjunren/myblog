<template>
  <div class="app-container">
    <Iframe :src="server" />
  </div>
</template>

<script>
  import Iframe from "@/components/Iframe";
  export default {
    name: "index",
    components: {
      Iframe
    },
    data() {
      return {
        server: process.env.VUE_APP_BASE_API + "/server/index"
      }
    }
  }
</script>

<style scoped>

</style>
