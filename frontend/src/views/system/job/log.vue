<template>
  <el-dialog :visible.sync="dialog" append-to-body top="0px" title="执行日志" width="88%">
    <div class="filter-container">
      <el-date-picker
        v-model="time"
        type="daterange"
        range-separator=":"
        class="filter-item"
        value-format="yyyy-MM-dd HH:mm:ss"
        start-placeholder="开始日期"
        end-placeholder="结束日期"/>
      <el-select v-model="listQuery.isSuccess" placeholder="日志状态" clearable class="filter-item" style="width: 110px" @change="">
        <el-option v-for="item in enabledTypeOptions" :key="item.key" :label="item.display_name" :value="item.key"/>
      </el-select>
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="fetchData">搜索</el-button>
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-download" :loading="downloadLoading" @click="downloadExcel">导出</el-button>
      <!--      <el-button v-waves class="filter-item" type="danger" icon="el-icon-delete" @click="">清空</el-button>-->
    </div>
    <el-table
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%">
      <el-table-column align="center" label="序号" width="60">
        <template slot-scope="scope">
          <span>{{ scope.$index + (listQuery.page-1) * (listQuery.limit) + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="任务名" width="150">
        <template slot-scope="scope">
          <span>{{ scope.row.jobName }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="执行时间" width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="Bean名称" min-width="120">
        <template slot-scope="scope">
          <span>{{ scope.row.beanName }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="执行方法" width="100">
        <template slot-scope="scope">
          <span>{{ scope.row.methodName }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="参数" width="100">
        <template slot-scope="scope">
          <span>{{ scope.row.params }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="cron表达式" min-width="120">
        <template slot-scope="scope">
          <span>{{ scope.row.cronExpression }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="异常" width="120">
        <template slot-scope="scope">
          <span>{{ scope.row.exceptionDetail }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="状态" width="120">
        <template slot-scope="scope">
          <el-tag :type="scope.row.isSuccess | tagTypeFilter">
            {{ scope.row.isSuccess | statusFilter }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" label="耗时" width="100">
        <template slot-scope="scope">
          <span>{{ scope.row.time | timeFilter}}</span>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="fetchData" />
  </el-dialog>
</template>

<script>
import {getJobsLogs, downloadJobLogExcel} from '@/api/job'
import waves from '@/directive/waves'
import Pagination from '@/components/Pagination'
import {downloadFile} from '@/utils/index'
export default {
  name: "log",
  directives: { waves },
  components: { Pagination },
  filters: {
    timeFilter(time) {
      return time + " ms"
    },
    tagTypeFilter(status) {
      return status ? "success" : "warning"
    },
    statusFilter(status){
      return status ? "成功" : "失败"
    }
  },
  data() {
    return {
      time: undefined,
      enabledTypeOptions: [
        { key: 'true', display_name: '成功' },
        { key: 'false', display_name: '失败' }
      ],
      listQuery: {
        page: 1,
        limit: 10,
        start: undefined,
        end: undefined,
        isSuccess: undefined
      },
      list: null,
      total: 0,
      dialog: false, delLoading: false, listLoading: true, downloadLoading: false
    }
  },
  methods: {
    fetchData() {
      const date = this.time
      console.log(this.time)
      if(date !== undefined && date !== null) {
        this.listQuery.start = date[0]
        this.listQuery.end = date[1]
      }
      console.log(this.listQuery)
      getJobsLogs(this.listQuery).then(response => {
        this.list = response.data.items
        this.total = response.data.total
        this.listQuery.start = undefined
        this.listQuery.end = undefined
        this.listLoading = false
      })
    },
    downloadExcel() {
      this.downloadLoading = true
      downloadJobLogExcel().then(result => {
        downloadFile(result, '任务执行日志列表', 'xlsx')
        this.downloadLoading = false
      })
    },
  }
}
</script>

<style scoped>

</style>
