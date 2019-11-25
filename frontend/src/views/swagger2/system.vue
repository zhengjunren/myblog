<template>
  <div class="app-container">
    <Iframe :src="swagger" />
  </div>
</template>

<script>
    import Iframe from "@/components/Iframe";
    export default {
        name: "system",
        components: {
            Iframe
        },
        data() {
            return {
                swagger:"http://localhost:8600/swagger-ui.html"
            }
        }
    }
</script>

<style scoped>

</style>
