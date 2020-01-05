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
      <el-button-group>
        <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="fetchData">搜索</el-button>
        <el-button v-waves class="filter-item" type="success" icon="el-icon-plus" @click="add">新增</el-button>
        <el-button v-waves class="filter-item" type="info" icon="el-icon-download" :loading="downloadLoading" @click="downloadExcel">导出</el-button>
        <el-button v-waves class="filter-item" type="warning" icon="el-icon-tickets" @click="doLog">日志</el-button>
      </el-button-group>
    </div>
    <Log ref="log"/>
    <el-dialog :visible.sync="dialogVisible" :close-on-click-modal="false" :before-close="cancel" :title="isAdd ? '新增任务' : '编辑任务'" append-to-body width="600px">
      <el-form ref="form" :model="form" :rules="rules" size="small" label-width="100px">
        <el-form-item label="任务名称" prop="jobName">
          <el-input v-model="form.jobName" style="width: 460px;"/>
        </el-form-item>
        <el-form-item label="Bean名称" prop="beanName">
          <el-input v-model="form.beanName" style="width: 460px;"/>
        </el-form-item>
        <el-form-item label="执行方法" prop="methodName">
          <el-input v-model="form.methodName" style="width: 460px;"/>
        </el-form-item>
        <el-form-item label="参数内容">
          <el-input v-model="form.params" style="width: 460px;"/>
        </el-form-item>
        <el-form-item label="Cron表达式" prop="cronExpression">
          <el-input v-model="form.cronExpression" style="width: 460px;"/>
        </el-form-item>
        <el-form-item label="任务状态">
          <el-radio v-model="form.isPause" :label="false">启用</el-radio>
          <el-radio v-model="form.isPause" :label="true" >暂停</el-radio>
        </el-form-item>
        <el-form-item label="任务描述">
          <el-input v-model="form.remark" style="width: 460px;" rows="2" type="textarea"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="text" @click="cancel">取消</el-button>
        <el-button  type="primary" @click="isAdd ? doAdd():doEdit()">确认</el-button>
      </div>
    </el-dialog>
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
      <el-table-column align="center" label="任务名称" min-width="80">
        <template slot-scope="scope">
          <span>{{ scope.row.jobName }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="Bean名称" min-width="90">
        <template slot-scope="scope">
          <span>{{ scope.row.beanName }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="执行方法" min-width="80">
        <template slot-scope="scope">
          <span>{{ scope.row.methodName }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="参数" min-width="100">
        <template slot-scope="scope">
          <span>{{ scope.row.params }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="cron表达式" min-width="120">
      <template slot-scope="scope">
        <span>{{ scope.row.cronExpression }}</span>
      </template>
    </el-table-column>
      <el-table-column align="center" label="状态" min-width="100">
        <template slot-scope="scope">
          <el-tag :type="scope.row.isPause | tagTypeFilter">
            {{ scope.row.isPause | statusFilter }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" label="描述" min-width="190">
        <template slot-scope="scope">
          <span>{{ scope.row.remark }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="创建时间" min-width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作" min-width="200" fixed="right">
        <template slot-scope="scope">
          <el-button-group>
            <el-button v-waves icon="el-icon-edit" type="primary" size="mini" @click="edit(scope.row)"/>
            <el-tooltip content="执行一次" effect="dark" placement="top">
              <el-button v-waves type="success" size="mini" @click="execute(scope.row.id)"><svg-icon icon-class="exec" /></el-button>
            </el-tooltip>
            <el-tooltip v-if="!scope.row.isPause" content="暂停" effect="dark" placement="top">
              <el-button v-waves type="primary" size="mini" @click="updateIsPause(scope.row.id)"><svg-icon icon-class="pause" /></el-button>
            </el-tooltip>
            <el-tooltip v-if="scope.row.isPause" content="恢复" effect="dark" placement="top">
              <el-button  v-waves type="warning" size="mini" @click="updateIsPause(scope.row.id)"><svg-icon icon-class="resume"/></el-button>
            </el-tooltip>
            <el-popover
              :ref="scope.row.id"
              placement="top"
              width="200">
              <p>确定停止并删除该任务吗？</p>
              <div style="text-align: right; margin: 0">
                <el-button size="mini" type="text" @click="$refs[scope.row.id].doClose()">取消</el-button>
                <el-button :loading="delLoading" type="primary" size="mini" @click="del(scope.row)">确定</el-button>
              </div>
              <el-button slot="reference" v-waves type="danger"  size="mini" icon="el-icon-delete"/>
            </el-popover>
          </el-button-group>

        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="fetchData" />
  </div>
</template>

<script>
import { getJobs, edit, del, add, execute, updateIsPause, downloadJobExcel } from '@/api/job'
import waves from '@/directive/waves'
import Pagination from '@/components/Pagination'
import {downloadFile} from '@/utils/index'
import Log from './log'
export default {
  name: "index",
  directives: { waves },
  components: { Pagination, Log },
  filters: {
    tagTypeFilter(status) {
      return status ? "warning" : "success"
    },
    statusFilter(status){
      return status ? "暂停中" : "运行中"
    }
  },
  data() {
    return {
      list: null,
      total: 0,
      time: "",
      listQuery: {
        page: 1,
        limit: 10,
        start: undefined,
        end: undefined
      },
      rules: {
        jobName: [
          { required: true, message: '请输入任务名称', trigger: 'blur' }
        ],
        beanName: [
          { required: true, message: '请输入Bean名称', trigger: 'blur' }
        ],
        methodName: [
          { required: true, message: '请输入方法名称', trigger: 'blur' }
        ],
        cronExpression: [
          { required: true, message: '请输入Cron表达式', trigger: 'blur' }
        ]
      },
      form: {id: undefined, jobName: '', beanName: '', methodName: '', params: '', cronExpression: '', isPause: 'false', remark: '', createTime: undefined },
      isAdd: false,
      listLoading: true,
      downloadLoading: false,
      dialogVisible: false,
      delLoading: false
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
      getJobs(this.listQuery).then(response => {
        this.list = response.data.items
        this.total = response.data.total
        this.listQuery.start = undefined
        this.listQuery.end = undefined
        this.listLoading = false
      })
    },
    cancel() {
      this.resetForm()
    },
    del(row) {
      del([row.id]).then(response => {
        this.$refs[row.id].doClose()
        const index = this.list.indexOf(row)
        this.list.splice(index, 1)
        this.$notify({
          title: '成功',
          message: "删除成功",
          type: 'success',
          duration: 2000
        })
      })
    },
    edit(data) {
      this.isAdd = false
      this.form = Object.assign({}, data)
      this.dialogVisible = true
    },
    doEdit(){
      const tempData = Object.assign({}, this.form)
      edit(tempData).then(response => {
        for (const v of this.list) {
          if (v.id === tempData.id) {
            const index = this.list.indexOf(v)
            this.list.splice(index, 1, tempData)
            break
          }
        }
        this.$notify({
          title: '修改成功',
          type: 'success',
          duration: 2500
        })
        this.cancel()
      })
    },
    add() {
      this.isAdd = true
      this.dialogVisible = true
    },
    doAdd() {
      add(this.form).then(response => {
        this.fetchData()
        this.$notify({
          title: '增加成功',
          type: 'success',
          duration: 2500
        })
      })
    },
    execute(id) {
      execute(id).then(response => {
        this.$notify({
          title: '执行成功',
          type: 'success',
          duration: 2500
        })
      })
    },
    updateIsPause(id) {
      updateIsPause(id).then(response => {
        var flag = false
        for (const v of this.list) {
          if (v.id === id) {
            flag = v.isPause
            v.isPause = !v.isPause
            break
          }
        }
        if (flag) {
          this.$notify({
            title: '恢复成功',
            type: 'success',
            duration: 2500
          })
        }
        else {
          this.$notify({
            title: '暂停成功',
            type: 'success',
            duration: 2500
          })
        }

      })
    },
    doLog() {
      this.$refs.log.dialog = true
      this.$refs.log.fetchData()
    },
    downloadExcel() {
      this.downloadLoading = true
      downloadJobExcel().then(result => {
        downloadFile(result, '任务列表', 'xlsx')
        this.downloadLoading = false
      })
    },
    resetForm() {
      this.dialogVisible = false
      this.$refs['form'].resetFields()
      this.form = {id: undefined, jobName: '', beanName: '', methodName: '', params: '', cronExpression: '', isPause: 'false', remark: '', createTime: undefined }
    }
  }
}
</script>

<style scoped>

</style>
