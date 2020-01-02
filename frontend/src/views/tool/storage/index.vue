<template>
  <div class="app-container">
    <div class="filter-container">
      <el-date-picker
        v-model="time"
        type="daterange"
        range-separator=":"
        class="filter-item"
        value-format="yyyy-MM-dd HH:mm:ss"
        start-placeholder="开始日期"
        end-placeholder="结束日期"/>
      <el-input v-model="listQuery.key" class="filter-item" placeholder="文件名" style="width: 200px;" />
      <el-button-group>
        <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="fetchData">搜索</el-button>
        <el-button v-waves class="filter-item" type="info" icon="el-icon-download" :loading="downloadLoading" @click="downloadExcel">导出</el-button>
        <el-button :icon="icon" class="filter-item" type="success" @click="synchronize">同步</el-button>
        <!-- 配置 -->
        <el-button v-waves class="filter-item" type="warning" icon="el-icon-s-tools" @click="doConfig">配置</el-button>
        <el-button v-waves class="filter-item" type="primary" icon="el-icon-upload" @click="dialog = true">上传</el-button>
      </el-button-group>
    </div>
    <el-table
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%">
      <el-table-column align="center" label="序号" min-width="80">
        <template slot-scope="scope">
          <span>{{ scope.$index + (listQuery.page-1) * (listQuery.limit) + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column label="文件名" prop="name" align="left" min-width="240">
        <template slot-scope="scope">
          <el-link type="primary" :href="scope.row.url" target="_blank">{{scope.row.name}}</el-link>
        </template>
      </el-table-column>
      <el-table-column label="大小" prop="size" align="left" min-width="150">
        <template slot-scope="scope">
          <span>{{ scope.row.size }}</span>
        </template>
      </el-table-column>
      <el-table-column label="空间名称" prop="bucket" align="left" min-width="150">
        <template slot-scope="scope">
          <span>{{ scope.row.bucket }}</span>
        </template>
      </el-table-column>
      <el-table-column label="空间类型" prop="bucket" align="left" min-width="150">
        <template slot-scope="scope">
          <span>{{ scope.row.type }}</span>
        </template>
      </el-table-column>
      <el-table-column label="文件类型" prop="suffix" align="left" min-width="150">
        <template slot-scope="scope">
          <span>{{ scope.row.suffix }}</span>
        </template>
      </el-table-column>
      <el-table-column label="上传时间" prop="createTime" align="left" min-width="220">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime === null ? scope.row.updateTime : scope.row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" min-width="120px" align="center" fixed="right">
        <template slot-scope="scope">
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
    <eForm ref="form" />
    <el-dialog :visible.sync="dialog" :close-on-click-modal="false" append-to-body width="500px" @close="">
      <el-upload
        :before-remove="handleBeforeRemove"
        :on-success="handleSuccess"
        :on-error="handleError"
        :file-list="fileList"
        :headers="headers"
        :action="url"
        class="upload-demo"
        multiple>
        <el-button size="small" type="primary">点击上传</el-button>
        <div slot="tip" style="display: block;" class="el-upload__tip">请勿上传违法文件，且文件不超过15M</div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="doSubmit">确认</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {getFiles, synchronize, del, downloadExcel} from '@/api/qiniu'
import { getToken } from '@/utils/auth'
import Pagination from '@/components/Pagination'
import eForm from './form'
import {downloadFile} from '@/utils/index'
import waves from '@/directive/waves'
export default {
  name: "index",
  directives: { waves },
  components: { Pagination, eForm },
  data() {
    return {
      url: process.env.VUE_APP_BASE_API + "/qiniu",
      headers: { 'Authorization': 'Bearer ' + getToken() },
      fileList: [], files: [],
      dialog: false,
      listLoading: false,
      list: null,
      total: 0,
      time: '',
      icon: 'el-icon-refresh',
      listQuery: {
        page: 1,
        limit: 10,
        start: undefined,
        end: undefined,
        key: ''
      },
      dialogVisible: false,
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
      const date = this.time
      if(date !== undefined && date !== null) {
        this.listQuery.start = date[0]
        this.listQuery.end = date[1]
      }
      getFiles(this.listQuery).then(response => {
        this.list = response.data.items
        this.total = response.data.total
        this.listQuery.start = undefined
        this.listQuery.end = undefined
        this.listLoading = false;
      })
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
    synchronize() {
      this.icon = 'el-icon-loading'
      synchronize().then(response => {
        this.icon = 'el-icon-refresh'
        this.$message({
          showClose: true,
          message: '数据同步成功',
          type: 'success',
          duration: 1500
        })
        this.fetchData()
      }).catch(err => {
        this.icon = 'el-icon-refresh'
        console.log(err.response.data.message)
      })
    },
    downloadExcel() {
      this.downloadLoading = true
      downloadExcel().then(result => {
        downloadFile(result, '文件信息列表', 'xlsx')
        this.downloadLoading = false
      })
    },

    doConfig() {
      const _this = this.$refs.form
      _this.fetchData()
      _this.dialog = true
    },

    doSubmit() {
      this.fileList = []
      this.dialog = false
      this.fetchData()
    },

    // 监听上传失败
    handleError(e, file, fileList) {
      const msg = JSON.parse(e.message)
      this.$notify({
        title: msg.message,
        type: 'error',
        duration: 2500
      })
    },
    handleSuccess(response, file, fileList) {
      const uid = file.uid
      const id = response.data.id
      this.files.push({ uid, id })
    },
    handleBeforeRemove(file, fileList) {
      for (let i = 0; i < this.files.length; i++) {
        if (this.files[i].uid === file.uid) {
          del(this.files[i].id).then(res => {})
          return true
        }
      }
    },
  }
}
</script>

<style scoped>

</style>
