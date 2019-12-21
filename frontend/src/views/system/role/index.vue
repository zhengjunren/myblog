<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="6" :xs="24">
        <el-card style="margin-bottom:20px;">
          <div slot="header" class="clearfix">
            <span>菜单分配</span>
            <el-button
              :disabled="!showButton"
              icon="el-icon-check"
              size="mini"
              style="float: right; padding: 6px 9px"
              type="primary"
              @click="saveMenu">保存</el-button>
          </div>
          <el-tree
            ref="menu"
            :data="menus"
            :default-checked-keys="menuIds"
            :props="defaultProps"
            accordion
            show-checkbox
            node-key="id"
            :check-strictly = 'true'
            highlight-current
          >
          </el-tree>
        </el-card>
        <el-card style="margin-bottom:20px;">
          <div slot="header" class="clearfix">
            <span>权限分配</span>
            <el-button
              :disabled="!showButton"
              icon="el-icon-check"
              size="mini"
              style="float: right; padding: 6px 9px"
              type="primary"
              @click="savePermission">保存</el-button>
          </div>
          <el-tree
            ref="permission"
            :data="permissions"
            :default-checked-keys="permissionIds"
            :props="defaultProps"
            accordion
            show-checkbox
            node-key="id"
            highlight-current
          >
          </el-tree>
        </el-card>
      </el-col>
      <el-col :span="18" :xs="24">
        <el-card style="margin-bottom:20px;">
          <div slot="header" class="clearfix">
            <span>角色列表</span>
          </div>
          <div class="filter-container">
            <el-button v-waves class="filter-item" type="primary" icon="el-icon-plus" @click="add">新增</el-button>
            <el-button v-waves class="filter-item" type="primary" icon="el-icon-download" :loading="downloadLoading" @click="downloadExcel">导出</el-button>
          </div>
          <el-table
            v-loading="listLoading"
            :data="list"
            border
            fit
            highlight-current-row
            @current-change="handleCurrentChange"
            style="width: 100%">
            <el-table-column align="center" label="序号" width="80">
              <template slot-scope="scope">
                <span>{{ scope.$index + (listQuery.page-1) * (listQuery.limit) + 1 }}</span>
              </template>
            </el-table-column>
            <el-table-column align="center" label="名称" width="120">
              <template slot-scope="scope">
                <span>{{ scope.row.name }}</span>
              </template>
            </el-table-column>
            <el-table-column align="center" label="描述" width="150">
              <template slot-scope="scope">
                <span>{{ scope.row.description }}</span>
              </template>
            </el-table-column>
            <el-table-column align="center" label="创建时间" width="185">
              <template slot-scope="scope">
                <span>{{ scope.row.createTime }}</span>
              </template>
            </el-table-column>
            <el-table-column align="center" label="创建时间" width="185">
              <template slot-scope="scope">
                <span>{{ scope.row.updateTime }}</span>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="160px" align="center" fixed="right">
              <template slot-scope="scope">
                <el-button size="mini" type="primary" icon="el-icon-edit" @click="edit(scope.row)"/>
                <el-popover
                  :ref="scope.row.id"
                  placement="top"
                  width="200">
                  <p>确定删除吗,此操作不能撤销！</p>
                  <div style="text-align: right; margin: 0">
                    <el-button size="mini" type="text" @click="$refs[scope.row.id].doClose()">取消</el-button>
                    <el-button :loading="delLoading" type="primary" size="mini" @click="subDelete(scope.row.id)">确定</el-button>
                  </div>
                  <el-button slot="reference" type="danger" icon="el-icon-delete" size="mini"/>
                </el-popover>
              </template>
            </el-table-column>
          </el-table>
          <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="fetchData" />
        </el-card>
        <eForm ref="form" :is-add="isAdd"/>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getRoles, updatePermission, updateMenu, del,downloadExcel } from '@/api/role'
import { getPermissionTree } from '@/api/permission'
import { getMenusTree } from '@/api/menu'
import {downloadFile} from '@/utils/index'
import Pagination from '@/components/Pagination'
import waves from '@/directive/waves'
import eForm from './form'
export default {
  name: "index",
  directives: { waves },
  components: { Pagination, eForm},
  data() {
    return {
      list: null,
      total: 0,
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      listQuery: {
        page: 1,
        limit: 10,
      },
      permissions: [],
      permissionIds: [],
      menus: [],
      menuIds: [],
      currentId: undefined,
      isAdd: false,
      listLoading: true,
      showButton: false,
      delLoading: false,
      downloadLoading: false
    }
  },
  created() {
    this.getPermissionTree()
    this.getMenuTree()
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.listLoading = true
      getRoles(this.listQuery).then(response => {
        this.list = response.data.items
        this.total = response.data.total
        this.listLoading = false;
      })
    },
    getPermissionTree() {
      getPermissionTree().then(response => {
        this.permissions = response.data;
      })
    },
    getMenuTree() {
      getMenusTree().then(response => {
        this.menus = response.data;
      })
    },
    edit(data) {
      this.isAdd = false
      const _this = this.$refs.form
      _this.deptIds = []
      _this.form = { id: data.id, name: data.name, description: data.description, createTime: data.createTime, updateTime: data.updateTime}
      _this.dialog = true
    },
    add() {
      this.isAdd = true
      this.$refs.form.dialog = true
    },
    subDelete(id) {
      this.delLoading = true
      del(id).then(res => {
        this.delLoading = false
        this.$refs[id].doClose()
        this.fetchData()
        this.$notify({
          title: '删除成功',
          type: 'success',
          duration: 2500
        })
      }).catch(err => {
        console.log(err)
        this.delLoading = false
        this.$refs[id].doClose()
        this.$notify({
          title: '删除失败',
          type: 'danger',
          duration: 2500
        })
      })
    },

    handleCurrentChange(val) {
      if (val) {
        const _this = this
        // 清空菜单的选中
        this.$refs.menu.setCheckedKeys([])
        this.$refs.permission.setCheckedKeys([])
        // 保存当前的角色id
        this.currentId = val.id
        // 点击后显示按钮
        this.showButton = true
        // 初始化
        this.menuIds = []
        this.permissionIds = []
        // 菜单数据需要特殊处理
        val.menus.forEach(function(data, index) {
          _this.menuIds.push(data.id)
        })
        val.permissions.forEach(function(data, index) {
          _this.permissionIds.push(data.id)
        })
      }
    },
    savePermission() {
      const permissionIds = []
      this.$refs.permission.getCheckedNodes().forEach(function(data, index) {
        if(data.children === undefined){
          permissionIds.push(data.id)
        }
      })
      updatePermission({
        currentRoleId: this.currentId,
        permissionIds: permissionIds
      }).then(response => {
        this.fetchData()
        this.$notify({
          title: '成功',
          message: response.message,
          type: 'success',
          duration: 2000
        })
      })
    },
    saveMenu() {
      const menuIds = []
      this.$refs.menu.getCheckedKeys().forEach(function(data, index) {
        menuIds.push(data)
      })
      updateMenu({
        currentRoleId: this.currentId,
        menuIds: menuIds
      }).then(response => {
        this.fetchData()
        this.$notify({
          title: '成功',
          message: response.message,
          type: 'success',
          duration: 2000
        })
      })
    },
    downloadExcel() {
      downloadExcel().then(result => {
        downloadFile(result, '角色列表', 'xlsx')
        this.downloadLoading = false
      })
    }
  }
}

</script>

<style scoped>

</style>
