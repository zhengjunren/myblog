<template>
  <Iframe :src="swagger" />
</template>

<script>
import Iframe from "@/components/Iframe"
export default {
  name: "Swagger",
  components: { Iframe },
  data() {
    return {
      swagger: process.env.VUE_APP_BASE + "/doc.html"
    }
  }
}
</script>

<style scoped>

</style>
