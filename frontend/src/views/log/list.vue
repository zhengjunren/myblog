<template>
  <div class="app-container">
    <div class="my-search-container">
      <el-date-picker
        v-model="date"
        type="daterange"
        range-separator=":"
        class="el-range-editor--small filter-item"
        style="height: 30.5px;width: 220px"
        value-format="yyyy-MM-dd HH:mm:ss"
        start-placeholder="开始日期"
        end-placeholder="结束日期"/>
      <el-button v-waves class="my-search-item" type="primary" icon="el-icon-search" @click="search">
        搜索
      </el-button>
      <el-button v-waves :loading="downloadLoading" class="my-search-item" type="primary" icon="el-icon-download" @click="handleDownload">
        导出
      </el-button>
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
      <el-table-column label="序号" prop="id" align="center" width="70">
        <template slot-scope="scope">
          <span>{{ scope.$index + (listQuery.page-1) * (listQuery.limit) + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作者" prop="username" align="center" width="150">
        <template slot-scope="scope">
          <span>{{ scope.row.username }}</span>
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
      <el-table-column label="描述" prop="description" align="center" min-width="150">
        <template slot-scope="scope">
          <span>{{ scope.row.description }}</span>
        </template>
      </el-table-column>
      <el-table-column label="浏览器" prop="browser" align="center" width="150">
        <template slot-scope="scope">
          <span>{{ scope.row.browser }}</span>
        </template>
      </el-table-column>
      <el-table-column label="耗时" class-name="status-col" align="center" min-width="100">
        <template slot-scope="{row}">
          <el-tag :type="row.time | statusFilter2">
            {{ row.time  | statusFilter}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" prop="createTime" align="center" width="200">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>

    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit"
                @pagination="getList"/>
  </div>
</template>

<script>
    import {fetchLogList, getLogExcel} from "@/api/system";
    import waves from '@/directive/waves'
    import {downloadFile} from '@/utils/index'
    import Pagination from '@/components/Pagination'
    export default {
      name: "LogList",
      components: {Pagination},
      directives: { waves },
      filters: {
        statusFilter(time) {
          return time+"ms"
        },
        statusFilter2(time) {
          if (time < 500){
            return "success"
          }
          else if (time<1000&&time>500) {
            return "warning"
          }
          else {
            return "danger"
          }
        }
      },
      data() {
        return {
          tableKey: 0,
          list: null,
          total: 0,
          listLoading: true,
          listQuery: {
            page: 1,
            limit: 10,
            start: undefined,
            end: undefined
          },
          date: undefined,
          downloadLoading: false,
        }
      },
      created() {
        this.getList()
      },
      methods: {
        getList() {
          this.listLoading = true
          fetchLogList(this.listQuery).then(response => {
            this.list = response.data.items
            this.total = response.data.total
            // 模拟请求时间
            setTimeout(() => {
              this.listLoading = false
            }, 500)
          })
        },
        handleDownload(){
          const date = this.date
          if(date !== undefined && date !== null) {
            this.listQuery.start = date[0]
            this.listQuery.end = date[1]
          }
          getLogExcel(this.listQuery).then(result => {
            downloadFile(result, '日志列表', 'xlsx')
            this.downloadLoading = false
          })
        },
        search(){
          const date = this.date
          if(date !== undefined && date !== null) {
            this.listQuery.start = date[0]
            this.listQuery.end = date[1]
          }

          fetchLogList(this.listQuery).then(response => {
            this.list = response.data.items
            this.total = response.data.total
            this.listQuery.start = undefined
            this.listQuery.end = undefined
            // 模拟请求时间
            setTimeout(() => {
              this.listLoading = false
            }, 500)
          })
        }
      }
    }
</script>

<style>
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
</style>
