<template>
  <div class="app-container">
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
      <el-table-column label="任务名" prop="jobName" align="center" width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.jobName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="任务组" prop="jobGroup" align="center" width="100">
        <template slot-scope="scope">
          <span>{{ scope.row.jobGroup }}</span>
        </template>
      </el-table-column>
      <el-table-column label="任务类名" prop="jobClassName" align="center" width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.jobClassName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="触发器名" prop="triggerName" align="center" width="200">
        <template slot-scope="scope">
          <span>{{ scope.row.triggerName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="触发器组" prop="triggerGroup" align="center" width="100">
        <template slot-scope="scope">
          <span>{{ scope.row.triggerGroup }}</span>
        </template>
      </el-table-column>
      <el-table-column label="表达式" prop="cronExpression" align="center" width="100">
        <template slot-scope="scope">
          <span>{{ scope.row.cronExpression }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" prop="triggerState" align="center" width="120">
        <template slot-scope="scope">
          <span>{{ scope.row.triggerState | statusFilter1}}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="280" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button type="primary" size="mini" @click="handleUpdate(row)">
            修改
          </el-button>
          <el-button v-if="row.triggerState!== 'WAITING'" size="mini" type="success" @click="resume(row)">
            恢复
          </el-button>
<!--          <el-button v-if="row.triggerState!== 'ACQUIRED'" size="mini" type="warning" @click="">-->
<!--            恢复-->
<!--          </el-button>-->
          <el-button v-if="row.triggerState!== 'PAUSED'" size="mini" type="warning" @click="pause(row)">
            暂停
          </el-button>
          <el-button size="mini" type="danger" @click="">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog title="修改" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="jobForm" label-position="left" label-width="120px"
               style="width: 400px; margin-left:50px;">
        <el-form-item label="cron表达式" prop="cronExpression">
          <el-input v-model="jobForm.cronExpression"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          取消
        </el-button>
        <el-button type="primary" @click="update">
          确定
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
    import { getTaskList, resumeTask, pauseTask, updateTask } from "@/api/system";
    export default {
        name: "TaskList",
        filters: {
            statusFilter1(status) {
                const statusMap = {
                    'WAITING': '运行中',
                    'ACQUIRED': '运行中',
                    'PAUSED': '暂停中'
                }
                return statusMap[status]
            }
        },
        data() {
            return {
                tableKey: 0,
                list:null,
                listLoading: true,
                listQuery: {
                    page: 1,
                    limit: 10,
                },
                dialogFormVisible: false,
                rules: {
                    type: [{required: true, message: 'type is required', trigger: 'change'}],
                    timestamp: [{type: 'date', required: true, message: 'timestamp is required', trigger: 'change'}],
                    title: [{required: true, message: 'title is required', trigger: 'blur'}]
                },
                temp: {
                    jobName: '',
                    jobGroup: '',
                    jobClassName: '',
                    triggerName: '',
                    triggerGroup: '',
                    cronExpression: '',
                    timeZoneId: '',
                    triggerState: ''
                },
                jobForm: {
                    jobClassName: '',
                    jobGroupName: '',
                    cronExpression: ''
                }
            }
        },
        created() {
            this.getList()
        },
        methods :{
            getList() {
                this.listLoading = true
                getTaskList(this.listQuery).then(response => {
                    this.list = response.data.items
                    console.log(response.data.items)
                    this.total = response.data.total
                    // 模拟请求时间
                    setTimeout(() => {
                        this.listLoading = false
                    }, 500)
                })
            },
            resume(row) {
                this.setValue(row,this.jobForm)
                resumeTask(this.jobForm).then(response => {
                    this.$message({
                        message: response.message,
                        type: 'success'
                    })
                    row.triggerState="WAITING"
                })
            },
            pause(row) {
                // this.jobForm.cronExpression = row.cronExpression
                // this.jobForm.jobClassName = row.jobClassName
                // this.jobForm.jobGroupName = row.jobGroup
                this.setValue(row, this.jobForm)
                pauseTask(this.jobForm).then(response => {
                    this.$message({
                        message: response.message,
                        type: 'success'
                    })
                    row.triggerState="PAUSED"
                })
            },
            handleUpdate(row) {
                this.setValue(row, this.jobForm)
                this.temp = Object.assign({}, row) // copy obj
                this.temp.cronExpression = this.jobForm.cronExpression
                this.dialogFormVisible = true
                this.$nextTick(() => {
                    this.$refs['dataForm'].clearValidate()
                })
            },
            update() {
                this.$refs['dataForm'].validate((valid) => {
                    if (valid) {
                        this.temp.cronExpression = this.jobForm.cronExpression
                        const tempData = Object.assign({}, this.jobForm)
                        updateTask(tempData).then(response => {
                            for (const v of this.list) {
                                if (v.jobName === this.temp.jobName) {
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
            setValue(row, jobForm) {
                jobForm.cronExpression = row.cronExpression
                jobForm.jobClassName = row.jobClassName
                jobForm.jobGroupName = row.jobGroup
            }

        }
    }
</script>

<style scoped>

</style>
