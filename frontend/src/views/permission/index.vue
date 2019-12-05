<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="6" :xs="24">
        <el-card style="margin-bottom:20px;">
          <div slot="header" class="clearfix">
            <span>权限分配</span>
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
                <el-button type="primary" size="small" icon="el-icon-edit">
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
  </div>
</template>

<script>
  import { getPermissionTree, getRoleList, getPermissionByRoleId } from '@/api/system'
  import Pagination from '@/components/Pagination'
  export default {
    name: "index",
    components: { Pagination},
    data() {
      return {
        defaultProps: {
          children: 'children',
          label: 'label'
        },
        list: [],
        listLoading: true,
        total: 0,
        permissions: [],
        permissionsByRole: [],
        permissionIds: [],
        listQuery: {
          page: 1,
          limit: 10
        },
        temp: {
          id: 0,
          parentId: 0,
          name: '',
          enname: '',
          created: undefined,
          updated: undefined,
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
          getPermissionByRoleId(val.id).then(response => {
            this.permissionsByRole = response.data
            this.permissionsByRole.forEach(function(data, index) {
              ids.push(data.id)
            })
            _this.permissionIds = ids
          })
        }
      }
    }
  }
</script>

<style scoped>

</style>
