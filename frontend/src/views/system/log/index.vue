<template>
  <div class="app-container">
    <el-tabs v-model="active"  @tab-click="handleClick">
      <div class="filter-container">
        <el-date-picker
          v-model="time"
          type="daterange"
          range-separator=":"
          class="filter-item"
          value-format="yyyy-MM-dd HH:mm:ss"
          start-placeholder="开始日期"
          end-placeholder="结束日期"/>
        <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="isError ? fetchErrorLog() : fetchData()">搜索</el-button>
        <el-button v-waves class="filter-item" type="primary" icon="el-icon-download" :loading="downloadLoading" @click="isError ? downloadErrorLogExcel() : downloadInfoLogExcel()">导出</el-button>
        <el-button v-waves class="filter-item" type="danger" icon="el-icon-delete" @click="deleteLogs">清空</el-button>
      </div>
      <el-tab-pane label="操作日志" name="INFO">
        <el-table
          v-loading="listLoading"
          :data="list"
          border
          fit
          highlight-current-row
          style="width: 100%">
          <el-table-column type="expand">
            <template slot-scope="props">
              <el-form label-position="left" inline class="demo-table-expand">
                <el-form-item label="请求方法">
                  <span>{{ props.row.method }}</span>
                </el-form-item>
                <el-form-item label="请求参数">
                  <span>{{ props.row.params }}</span>
                </el-form-item>
              </el-form>
            </template>
          </el-table-column>
          <el-table-column align="center" label="序号" width="80">
            <template slot-scope="scope">
              <span>{{ scope.$index + (listQuery.page-1) * (listQuery.limit) + 1 }}</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="描述" min-width="150">
            <template slot-scope="scope">
              <span>{{ scope.row.description }}</span>
            </template>
          </el-table-column>
          <el-table-column label="IP" prop="requestIp" align="center" width="150">
            <template slot-scope="scope">
              <span>{{ scope.row.requestIp }}</span>
            </template>
          </el-table-column>
          <el-table-column label="IP来源" prop="address" align="center" min-width="150">
            <template slot-scope="scope">
              <span>{{ scope.row.address }}</span>
            </template>
          </el-table-column>
          <el-table-column label="浏览器" prop="browser" align="center" width="150">
            <template slot-scope="scope">
              <span>{{ scope.row.browser }}</span>
            </template>
          </el-table-column>
          <el-table-column label="耗时" class-name="status-col" align="center" min-width="100">
            <template slot-scope="{row}">
              <el-tag :type="row.time | tagTypeFilter">
                {{ row.time  | timeFilter}}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作者" prop="username" align="center" width="150">
            <template slot-scope="scope">
              <span>{{ scope.row.username }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作时间" prop="createTime" align="center" width="200">
            <template slot-scope="scope">
              <span>{{ scope.row.createTime }}</span>
            </template>
          </el-table-column>
        </el-table>
        <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="fetchData" />
      </el-tab-pane>
      <el-tab-pane label="异常日志" name="ERROR">
        <el-table
          v-loading="listLoading"
          :data="list"
          border
          fit
          highlight-current-row
          style="width: 100%">
          <el-table-column type="expand">
            <template slot-scope="props">
              <el-form label-position="left" inline class="demo-table-expand">
                <el-form-item label="请求方法">
                  <span>{{ props.row.method }}</span>
                </el-form-item>
                <el-form-item label="请求参数">
                  <span>{{ props.row.params }}</span>
                </el-form-item>
              </el-form>
            </template>
          </el-table-column>
          <el-table-column align="center" label="序号" min-width="80">
            <template slot-scope="scope">
              <span>{{ scope.$index + (listQuery.page-1) * (listQuery.limit) + 1 }}</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="描述" min-width="150">
            <template slot-scope="scope">
              <span>{{ scope.row.description }}</span>
            </template>
          </el-table-column>
          <el-table-column label="IP" prop="requestIp" align="center" min-width="150">
            <template slot-scope="scope">
              <span>{{ scope.row.requestIp }}</span>
            </template>
          </el-table-column>
          <el-table-column label="IP来源" prop="address" align="center" min-width="170">
            <template slot-scope="scope">
              <span>{{ scope.row.address }}</span>
            </template>
          </el-table-column>
          <el-table-column label="浏览器" prop="browser" align="center" min-width="170">
            <template slot-scope="scope">
              <span>{{ scope.row.browser }}</span>
            </template>
          </el-table-column>
          <el-table-column label="耗时" class-name="status-col" align="center" min-width="80">
            <template slot-scope="{row}">
              <el-tag :type="row.time | tagTypeFilter">
                {{ row.time  | timeFilter}}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作者" prop="username" align="center" min-width="120">
            <template slot-scope="scope">
              <span>{{ scope.row.username }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作时间" prop="createTime" align="center" min-width="200">
            <template slot-scope="scope">
              <span>{{ scope.row.createTime }}</span>
            </template>
          </el-table-column>
          <el-table-column label="查看异常" min-width="100px" align="center" fixed="right">
            <template slot-scope="scope">
              <el-button size="mini" type="primary" @click="getErrorDetail(scope.row.id)"><svg-icon icon-class="look" />&nbsp;</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-dialog
          title="异常"
          :visible.sync="dialogVisible"
          append-to-body top="0px" width="85%">
          <pre v-highlightjs="errorDetail"><code class="java" /></pre>
          <span slot="footer" class="dialog-footer">
            <el-button type="primary" @click="dialogVisible = false">确定</el-button>
          </span>
        </el-dialog>
        <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="fetchErrorLog" />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import { getInfoLog, getErrorLog, getErrorLogDetail, deleteLogs,downloadErrorLogExcel, downloadInfoLogExcel } from '@/api/log'
import Pagination from '@/components/Pagination'
import {downloadFile} from '@/utils/index'
import waves from '@/directive/waves'
export default {
  name: "index",
  directives: { waves },
  filters: {
    timeFilter(time) {
      return time + "ms"
    },
    tagTypeFilter(time) {
      if (time < 500){
        return ""
      }
      else if (time<1000&&time>500) {
        return "warning"
      }
      else {
        return "danger"
      }
    }
  },
  components: { Pagination },
  data() {
    return {
      active: "INFO",
      list: null,
      total: 0,
      errorDetail: '',
      time: '',
      listQuery: {
        page: 1,
        limit: 10,
        type: "INFO",
        start: undefined,
        end: undefined
      },
      isError: false,
      downloadLoading: false,
      dialogVisible: false,
      listLoading: true
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      const date = this.time
      if(date !== undefined && date !== null) {
        this.listQuery.start = date[0]
        this.listQuery.end = date[1]
      }
      this.listQuery.type = "INFO"
      getInfoLog(this.listQuery).then(response =>{
        this.list = response.data.items
        this.total = response.data.total
        this.listQuery.start = undefined
        this.listQuery.end = undefined
        this.listLoading = false;
      })
    },
    fetchErrorLog() {
      const date = this.time
      if(date !== undefined && date !== null) {
        this.listQuery.start = date[0]
        this.listQuery.end = date[1]
      }
      this.listQuery.type = "ERROR"
      getErrorLog(this.listQuery).then(response =>{
        this.list = response.data.items
        this.total = response.data.total
        this.listQuery.start = undefined
        this.listQuery.end = undefined
        this.listLoading = false;
      })
    },
    handleClick(tab, event) {
      this.time = ''
      this.resetListQuery()
      if (tab.name === "ERROR"){
        this.fetchErrorLog()
        this.isError = true
      }
      else {
        this.isError = false
        this.fetchData()
      }
    },
    getErrorDetail(id) {
      getErrorLogDetail(id).then(response => {
        this.errorDetail = response.data
        this.dialogVisible = true
      })
    },
    deleteLogs() {
      const type = this.isError === true ? "ERROR":"INFO";
      deleteLogs(type).then(response => {
        this.list = null
        this.total = 0
        this.$notify({
          title: '成功',
          message: "删除成功",
          type: 'success',
          duration: 2000
        })
      })
    },
    downloadInfoLogExcel() {
      this.downloadLoading = true
      downloadInfoLogExcel().then(response => {
        this.downloadLoading = false
        downloadFile(response, 'info日志', 'xlsx')
      })
    },
    downloadErrorLogExcel() {
      this.downloadLoading = true
      downloadErrorLogExcel().then(response => {
        this.downloadLoading = false
        downloadFile(response, 'error日志', 'xlsx')
      })
    },
    resetListQuery() {
      this.listQuery = {
        page: 1,
        limit: 10,
        type: "INFO",
        start: null,
        end: null
      }
    }
  }
}
</script>

<style scoped>
  .demo-table-expand {
    font-size: 0;
  }
  .demo-table-expand label {
    width: 70px;
    color: #99a9bf;
  }
  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 100%;
  }
  .demo-table-expand .el-form-item__content {
    font-size: 15px;

  }
  /deep/ .el-dialog__body {
    padding: 0 20px 10px 20px !important;
  }
  .java.hljs {
    color: #444;
    background: #ffffff !important;
    height: 630px !important;
  }
</style>
