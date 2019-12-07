<template>
  <div class="app-container">
    <el-table
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
      <el-table-column label="用户名" align="center" min-width="110">
        <template slot-scope="scope">
          <span>{{ scope.row.username }}</span>
        </template>
      </el-table-column>
      <el-table-column label="IP" align="center" min-width="110">
        <template slot-scope="scope">
          <span>{{ scope.row.ip }}</span>
        </template>
      </el-table-column>
      <el-table-column label="地址" align="center" min-width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.address }}</span>
        </template>
      </el-table-column>
      <el-table-column label="浏览器" align="center" min-width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.browser }}</span>
        </template>
      </el-table-column>
      <el-table-column label="登录时间" align="center" min-width="250">
        <template slot-scope="scope">
          <span>{{ scope.row.loginTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="200">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="kickOut(scope.row)">踢出</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
  import Pagination from '@/components/Pagination'
  import {getOnlineUser, kickOutOnlineUser} from "@/api/system";
  export default {
    name: "online",
    components: {Pagination},
    data() {
      return {
        list: null,
        total: 0,
        listLoading: true,
        listQuery: {
          page: 1,
          limit: 10,
        },
      }
    },
    created() {
      this.getList()
    },
    methods: {
      getList() {
        this.listLoading = true
        getOnlineUser().then(response => {
          this.list = response.data.items
          this.total = response.data.total
          // 模拟请求时间
          setTimeout(() => {
            this.listLoading = false
          }, 500)
        })
      },
      kickOut(row) {
        kickOutOnlineUser(row.key).then(response => {
          const index = this.list.indexOf(row)
          this.list.splice(index, 1)
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
