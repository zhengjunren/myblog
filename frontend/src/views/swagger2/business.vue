<template>
  <div class="app-container">
    <Iframe :src="swagger" />
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
                swagger: process.env.VUE_APP_BASE_API + "/swagger-ui.html"
            }
        }
    }
</script>

<style scoped>

</style>
