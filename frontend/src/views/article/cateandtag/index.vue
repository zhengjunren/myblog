<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="10.5" :xs="24">

      </el-col>
      <el-col :span="13.5" :xs="24">
        <el-card>
          <category/>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import category from "./category";
export default {
  name: "index",
  components: {category}
}
</script>

<style scoped>

</style>
