<template>
  <div class="app-container">
    <div class="my-search-container">
      <el-input v-model="query.username" placeholder="用户名" style="width: 150px;" class="my-search-item" @keyup.enter.native="" />
      <el-input v-model="query.nickname" placeholder="昵称" style="width: 150px;" class="my-search-item" @keyup.enter.native="" />
      <el-input v-model="query.email" placeholder="邮箱" style="width: 150px;" class="my-search-item" @keyup.enter.native="" />
      <el-select v-model="query.status" placeholder="状态" clearable style="width: 90px" class="my-search-item">
        <el-option v-for="item in statusOptions" :key="item" :label="item" :value="item" />
      </el-select>
      <el-button v-waves class="my-search-item" type="primary" icon="el-icon-search" @click="search">
        搜索
      </el-button>
      <el-button v-waves :loading="downloadLoading" class="my-search-item" type="primary" icon="el-icon-download" @click="handleDownload">
        导出
      </el-button>
      <el-checkbox v-model="showReviewer" class="my-search-item" style="margin-left:15px;" @change="tableKey=tableKey+1">
        待续
      </el-checkbox>
    </div>
    <el-table
      :key="tableKey"
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;"
    >
      <el-table-column label="序号" prop="id" align="center" width="70">
        <template slot-scope="scope">
          <span>{{ scope.$index + (listQuery.page-1) * (listQuery.limit) + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column label="用户名" prop="username" align="center" width="110">
        <template slot-scope="scope">
          <span>{{ scope.row.username }}</span>
        </template>
      </el-table-column>
      <el-table-column label="昵称" prop="nickname" align="center" width="100">
        <template slot-scope="scope">
          <span>{{ scope.row.nickname }}</span>
        </template>
      </el-table-column>
      <el-table-column label="邮箱" prop="email" align="center" min-width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.email }}</span>
        </template>
      </el-table-column>
      <el-table-column label="头像" prop="email" align="center" width="80">
        <template slot-scope="{row}">
          <el-image
            style="width: 40px; height: 40px; border-radius: 10px;"
            :src="row.avatar"
            :preview-src-list="[row.avatar]">
          </el-image>
        </template>
      </el-table-column>
      <el-table-column label="用户首页" prop="url" align="center" min-width="200">
        <template slot-scope="{row}">
          <el-link v-bind:href="row.url" target="_blank" type="primary">{{row.url}}</el-link>
        </template>
      </el-table-column>
      <el-table-column label="上次登录时间" prop="lastLoginTime" align="center" min-width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.lastLoginTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="用户状态" class-name="status-col" align="center" width="80">
        <template slot-scope="{row}">
          <el-tag :type="row.status | statusFilter1">
            {{ row.status}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="280" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button type="primary" size="mini" @click="handleUpdate(row)">
            编辑
          </el-button>
          <el-button v-if="row.status!== '正常'" size="mini" type="success" @click="handleModifyStatus(row,'正常')">
            正常
          </el-button>
          <el-button v-if="row.status!== '冻结'" size="mini" type="warning" @click="handleModifyStatus(row,'冻结')">
            冻结
          </el-button>
          <el-button v-if="row.status!== '注销'" size="mini" type="danger" @click="handleModifyStatus(row,'注销')">
            注销
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <div v-if="isSearch===false">
      <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit"
                  @pagination="getList"/>
    </div>
    <div v-if="isSearch===true">
      <pagination v-show="total>0" :total="total" :page.sync="searchPage" :limit.sync="searchLimit"
                  @pagination="search" />
    </div>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="70px"
               style="width: 400px; margin-left:50px;">
        <el-form-item label="昵称" prop="nickname">
          <el-input v-model="temp.nickname"/>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="temp.email"/>
        </el-form-item>
        <el-form-item label="用户首页" prop="url">
          <el-input v-model="temp.url"/>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="temp.status " class="filter-item" placeholder="请选择">
            <el-option v-for="item in statusOptions" :key="item" :label="item" :value="item"/>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          取消
        </el-button>
        <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">
          确定
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
    import {fetchList, modifyStatus, search, updateUser, getExcel} from "@/api/user";
    import waves from '@/directive/waves'
    import Pagination from '@/components/Pagination'
    import Link from "../../layout/components/Sidebar/Link";
    import {downloadFile} from '@/utils/index'
    import {getToken} from '../../utils/auth'
    export default {
        name: "UserListTable",
        components: {Link, Pagination},
        directives: { waves },
        filters: {
            statusFilter1(status) {
                const statusMap = {
                    '正常': 'success',
                    '冻结': 'warning',
                    '注销': 'danger'
                }
                return statusMap[status]
            },
            typeFilter(type) {
                return calendarTypeKeyValue[type]
            }
        },
        data() {
            return {
                isSearch:false,
                tableKey: 0,
                list: null,
                total: 0,
                listLoading: true,
                listQuery: {
                    page: 1,
                    limit: 10,
                },
                searchPage:1,
                searchLimit:10,
                query:{
                    username:'',
                    status:'',
                    email:'',
                    nickname:''
                },
                statusOptions: ['正常', '冻结', '注销'],
                importanceOptions: [1, 2, 3],
                sortOptions: [{label: 'ID Ascending', key: '+id'}, {label: 'ID Descending', key: '-id'}],
                showReviewer: false,
                temp: {
                    id: undefined,
                    username: '',
                    nickname: '',
                    email: '',
                    url: '',
                    lastLoginTime: '',
                    status: undefined
                },
                dialogFormVisible: false,
                dialogPvVisible: false,
                dialogStatus: '',
                textMap: {
                    update: '编辑',
                    create: 'Create'
                },
                rules: {
                    type: [{required: true, message: 'type is required', trigger: 'change'}],
                    timestamp: [{type: 'date', required: true, message: 'timestamp is required', trigger: 'change'}],
                    title: [{required: true, message: 'title is required', trigger: 'blur'}]
                },
                downloadLoading: false
            }
        },

        created() {
            this.getList()
        },
        methods: {
            getList() {
                this.listLoading = true
                fetchList(this.listQuery).then(response => {
                    this.list = response.data.items
                    this.total = response.data.total
                    // 模拟请求时间
                    setTimeout(() => {
                        this.listLoading = false
                    }, 500)
                })
            },
            handleCreate() {
                this.resetTemp()
                this.dialogStatus = 'create'
                this.dialogFormVisible = true
                this.$nextTick(() => {
                    this.$refs['dataForm'].clearValidate()
                })
            },
            createData() {

            },
            handleUpdate(row) {
                this.temp = Object.assign({}, row) // copy obj
                this.dialogStatus = 'update'
                this.dialogFormVisible = true
                this.$nextTick(() => {
                    this.$refs['dataForm'].clearValidate()
                })
            },
            updateData() {
                this.$refs['dataForm'].validate((valid) => {
                    if (valid) {
                        const tempData = Object.assign({}, this.temp)
                        updateUser(tempData).then(response => {
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
                    }
                })
            },
            handleModifyStatus(row, status) {
                modifyStatus(row.username, status).then(response => {
                    this.$message({
                        message: response.message,
                        type: 'success'
                    })
                    row.status = status
                })
            },

            search() {
                if (this.query.username === "" && this.query.nickname === "" && this.query.status === "" && this.query.email === "") {
                    this.getList()
                    this.isSearch = false
                }
                else {
                    search(this.query, this.searchPage, this.searchLimit).then(response => {
                        this.list = response.data.items
                        this.total = response.data.total
                        // 模拟请求时间
                        setTimeout(() => {
                            this.listLoading = false
                        }, 500)
                    })
                    this.isSearch = true
                }
              },
            handleDownload(){
              getExcel().then(result => {
                downloadFile(result, '用户列表', 'xlsx')
                this.downloadLoading = false
              })
            }
        }
    }
</script>

<style lang="scss" scoped>
  .avatar-container {
    margin-right: 0;

    .avatar-wrapper {
      margin-top: 5px;
      position: relative;

      .user-avatar {
        cursor: pointer;
        width: 40px;
        height: 40px;
        border-radius: 10px;
      }
    }
  }
</style>
