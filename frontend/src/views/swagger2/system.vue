<template>
  <div class="app-container">
    <Iframe :src="swagger" />
  </div>
</template>

<script>
    import Iframe from "@/components/Iframe";
    import {BASE_URL} from "@/api/system"
    export default {
        name: "system",
        components: {
            Iframe
        },
        data() {
            return {
                swagger: BASE_URL + "/swagger-ui.html"
            }
        }
    }
</script>

<style scoped>

</style>
