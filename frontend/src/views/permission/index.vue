<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="6" :xs="24">
        <permission-list/>
      </el-col>
    </el-row>

  </div>
</template>

<script>
  import PermissionList from "./PermissionList";
  export default {
    name: "index",
    components: {PermissionList}
  }
</script>

<style scoped>

</style>
