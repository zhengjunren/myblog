<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.filter" class="filter-item" placeholder="关键字" style="width: 200px;" />
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="">搜索</el-button>
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-download" :loading="downloadLoading" @click="downloadExcel">导出</el-button>
    </div>
    <el-table
      v-loading="listLoading"
      :data="list"
      border
      fit
      style="width: 100%">
      <el-table-column align="center" label="序号" min-width="80">
        <template slot-scope="scope">
          <span>{{ scope.$index + (listQuery.page-1) * (listQuery.limit) + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="用户名" min-width="100">
        <template slot-scope="scope">
          <span>{{ scope.row.username }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="昵称" min-width="100">
        <template slot-scope="scope">
          <span>{{ scope.row.nickname }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="浏览器" min-width="120">
        <template slot-scope="scope">
          <span>{{ scope.row.browser }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="IP" min-width="120">
        <template slot-scope="scope">
          <span>{{ scope.row.ip }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="IP来源" min-width="160">
        <template slot-scope="scope">
          <span>{{ scope.row.address }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="登录时间" min-width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.loginTime }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作" min-width="120" fixed="right">
        <template slot-scope="scope">
          <el-popover
            :ref="scope.row.username + scope.row.loginTime"
            placement="top"
            width="200">
            <p>确定踢出吗！</p>
            <div style="text-align: right; margin: 0">
              <el-button size="mini" type="text" @click="$refs[scope.row.username + scope.row.loginTime].doClose()">取消</el-button>
              <el-button :loading="delLoading" type="primary" size="mini" @click="kickOut(scope.row)">确定</el-button>
            </div>
            <el-button slot="reference" size="mini" type="danger"><svg-icon icon-class="kick"/></el-button>
          </el-popover>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="fetchData" />
  </div>
</template>

<script>
import {getOnlineUsers, downloadExcel, kickOut} from '@/api/user'
import {downloadFile} from '@/utils/index'
import Pagination from '@/components/Pagination'
import waves from '@/directive/waves'
export default {
  name: "Online",
  directives: { waves },
  components: { Pagination},
  data() {
    return {
      list: null,
      total: 0,
      listQuery: {
        page: 1,
        limit: 10,
        filter: ''
      },
      listLoading: true,
      downloadLoading: false,
      delLoading: false
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.listLoading = true
      getOnlineUsers(this.listQuery).then(response => {
        this.list = response.data.items
        this.total = response.data.total
        this.listLoading = false;
      })
    },
    kickOut(row) {
      kickOut(row.key).then(response => {
        this.$refs[row.username + row.loginTime].doClose()
        const index = this.list.indexOf(row)
        this.list.splice(index, 1)
        this.total = this.total - 1
        this.$notify({
          title: '成功',
          message: "踢出成功",
          type: 'success',
          duration: 2000
        })
      })
    },
    downloadExcel() {
      this.downloadLoading = true
      downloadExcel().then(result => {
        downloadFile(result, '在线用户列表', 'xlsx')
        this.downloadLoading = false
      })
    }
  }
}
</script>

<style scoped>

</style>
