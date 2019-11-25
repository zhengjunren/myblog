<template>
  <div class="app-container">
    <el-tabs value="business" style="padding-left: 5px;">
      <el-tab-pane label="业务文档" name="business">
        <Business/>
      </el-tab-pane>
      <el-tab-pane label="系统文档" name="system">
        <System/>
      </el-tab-pane>
    </el-tabs>/
  </div>
</template>

<script>
  import Business from "./business";
  import System from "./system";
    export default {
        name: "index",
        components: {
            Business,
            System
        }
    }
</script>

<style scoped>

</style>
