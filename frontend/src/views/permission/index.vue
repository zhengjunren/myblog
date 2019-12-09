<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="6" :xs="24">
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
          <div class="my-search-container">
            <el-button type="primary" size="small" icon="el-icon-plus" @click="handleCreate">新增</el-button>
          </div>
          <el-table
            v-loading="listLoading"
            :data="list"
            border
            fit
            highlight-current-row
            @current-change="handleCurrentChange"
            style="width: 100%">
            <el-table-column align="center" label="ID" width="80">
              <template slot-scope="scope">
                <span>{{ scope.$index + (listQuery.page-1) * (listQuery.limit) + 1 }}</span>
              </template>
            </el-table-column>
            <el-table-column width="180px" align="center" label="名称">
              <template slot-scope="scope">
                <span>{{ scope.row.name}}</span>
              </template>
            </el-table-column>
            <el-table-column width="180px" align="center" label="角色权限">
              <template slot-scope="scope">
                <span>{{ scope.row.enname}}</span>
              </template>
            </el-table-column>
            <el-table-column width="180px" align="center" label="创建时间">
              <template slot-scope="scope">
                <span>{{ scope.row.created}}</span>
              </template>
            </el-table-column>
            <el-table-column min-width="180px" align="center" label="操作">
              <template slot-scope="scope">
                <el-button type="primary" size="small" icon="el-icon-edit" @click="handleUpdate(scope.row)">
                  编辑
                </el-button>
                <el-popover
                  :ref="scope.$index"
                  placement="top"
                  width="180">
                  <p>确定删除吗？</p>
                  <div style="text-align: right; margin: 0">
                    <el-button size="mini" type="text" @click="$refs[scope.$index].doClose()">取消</el-button>
                    <el-button type="primary" size="mini" @click="">确定</el-button>
                  </div>
                  <el-button slot="reference" size="small" type="danger" icon="el-icon-delete">删除</el-button>
                </el-popover>
              </template>
            </el-table-column>
          </el-table>
          <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />
        </el-card>
      </el-col>
    </el-row>
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :model="temp" label-position="left" label-width="70px"
               style="width: 400px; margin-left:50px;">
        <el-form-item label="名称">
          <el-input v-model=temp.name />
        </el-form-item>
        <el-form-item label="英文">
          <el-input v-model=temp.enname />
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model=temp.description />
        </el-form-item>
        <div v-if="this.dialogStatus==='update'">
          <el-form-item label="创建时间">
            <el-input v-model=temp.created disabled />
          </el-form-item>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          取消
        </el-button>
        <el-button type="primary" @click="dialogStatus==='create'?createRoleData():updateRoleData()">
          确定
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import { getPermissionTree, getRoleList, getPermissionByRoleEnName, updateRolePermission, updateRoleData, createRoleData } from '@/api/system'
  import {parseTime} from '@/utils/index'
  import Pagination from '@/components/Pagination'
  export default {
    name: "index",
    components: { Pagination},
    data() {
      return {
        showButton: false,
        defaultProps: {
          children: 'children',
          label: 'label'
        },
        list: [],
        listLoading: true,
        total: 0,
        dialogStatus: '',
        dialogFormVisible: false,
        textMap: {
          update: '编辑',
          create: '创建'
        },
        permissions: [],
        permissionsByRole: [],
        permissionIds: [],
        listQuery: {
          page: 1,
          limit: 10
        },
        currentRoleEnName: '',
        temp: {
          id: undefined,
          parentId: 0,
          name: '',
          enname: '',
          description: '',
          created: ''
        }
      }
    },
    created() {
      this.getPermission()
      this.getList()
    },
    methods: {
      getList() {
        this.listLoading = true
        getRoleList(this.listQuery).then(response => {
          this.list = response.data.items
          this.total = response.data.total
          // 模拟请求时间
          setTimeout(() => {
            this.listLoading = false
          }, 500)
        })
      },
      getPermission() {
        getPermissionTree().then(response => {
          this.permissions = response.data;
        })
      },
      handleCurrentChange(val) {
        if (val) {
          const _this = this
          // _this.permissionIds = []
          this.$refs.permission.setCheckedKeys([])
          const ids = []
          this.currentRoleEnName = val.enname
          getPermissionByRoleEnName(val.enname).then(response => {
            this.permissionsByRole = response.data
            this.permissionsByRole.forEach(function(data, index) {
              ids.push(data.id)
            })
            this.showButton = true
            _this.permissionIds = ids
          })
        }
      },
      handleUpdate(row) {
        this.temp = Object.assign({}, row) // copy obj
        this.dialogStatus = 'update'
        this.dialogFormVisible = true
      },
      updateRoleData() {
        updateRoleData(this.temp).then(response => {
          for (const v of this.list) {
            if (v.id === this.temp.id) {
              const index = this.list.indexOf(v)
              this.list.splice(index, 1, this.temp)
              break
            }
          }
          this.dialogFormVisible = false
          this.$notify({
            title: '成功',
            message: response.message,
            type: 'success',
            duration: 2000
          })
        })
      },
      handleCreate() {
        this.resetTemp()
        this.dialogStatus = 'create'
        this.dialogFormVisible = true
      },
      createRoleData() {
        createRoleData(this.temp).then(response => {
          this.temp.created = parseTime(new Date().toDateString(), "yyyy-MM-dd HH:mm:ss")
          this.list.unshift(this.temp)
          this.dialogFormVisible = false
          this.$notify({
            title: '成功',
            message: response.message,
            type: 'success',
            duration: 2000
          })
        })
      },
      resetTemp() {
        this.temp = {
          id: undefined,
          parentId: 0,
          name: '',
          enname: '',
          description: '',
          created: ''
        }
      },
      savePermission() {
        const permissionIds = []
        this.$refs.permission.getCheckedNodes().forEach(function(data, index) {
          if(data.children === undefined){
            permissionIds.push(data.id)
          }
        })
        // console.log(this.permissions)
        // console.log(permissionIds)
        updateRolePermission({
          currentRoleEnName: this.currentRoleEnName,
          permissionIds: permissionIds
        }).then(response => {
          this.$notify({
            title: '成功',
            message: response.message,
            type: 'success',
            duration: 2000
          })
        })
      }
    }
  }
</script>

<style scoped>

</style>
