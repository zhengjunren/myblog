<template>
  <div class="app-container">
    <el-card style="margin-bottom:20px;">
      <div slot="header" class="clearfix">
        <span>权限分配</span>
      </div>
      <el-tree
        ref="permissions"
        :data="permissions"
        :default-checked-keys="permissionIds"
        :props="defaultProps"
        check-strictly
        accordion
        show-checkbox
        node-key="id"/>
    </el-card>
  </div>
</template>

<script>
  import { getPermissionTree } from '@/api/system'
  export default {
    name: "PermissionList",
    data() {
      return {
        defaultProps: {
          children: 'children',
          label: 'label'
        },
        permissions: [],
        permissionIds: []
      }
    },
    created() {
      this.getPermission()
    },
    methods: {
      getPermission() {
        getPermissionTree().then(response => {
          this.permissions = response.data;
        })
      }
    }
  }
</script>

<style scoped>

</style>
