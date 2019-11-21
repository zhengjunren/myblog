<template>
  <div class="app-container">
    <el-table v-loading="listLoading" :data="list" border fit highlight-current-row style="width: 100%">
      <el-table-column label="序号" prop="id" align="center" min-width="50">
        <template slot-scope="scope">
          <span>{{ scope.$index + (listQuery.page-1) * (listQuery.limit) + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column label="任务名" prop="jobName" align="center" min-width="160">
        <template slot-scope="scope">
          <span>{{ scope.row.jobName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="任务组" prop="jobGroup" align="center" min-width="80">
        <template slot-scope="scope">
          <span>{{ scope.row.jobGroup }}</span>
        </template>
      </el-table-column>
      <el-table-column label="任务类名" prop="jobClassName" align="center" width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.jobClassName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="触发器名" prop="triggerName" align="center" width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.triggerName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="触发器组" prop="triggerGroup" align="center" min-width="80">
        <template slot-scope="scope">
          <span>{{ scope.row.triggerGroup }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" prop="triggerState" align="center" min-width="80">
        <template slot-scope="{row}">
          <el-tag :type="row.triggerState | statusFilter1">
            {{ row.triggerState | statusFilter}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column min-width="230px" label="表达式">
        <template slot-scope="{row}">
          <template v-if="row.edit">
            <el-input v-model="row.cronExpression" class="edit-input" size="small" />
            <el-button
              class="cancel-btn"
              size="mini"
              type="warning"
              @click="cancelEdit(row)"
            >
              取消
            </el-button>
          </template>
          <span v-else>{{ row.cronExpression }}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="操作" min-width="250">
        <template slot-scope="{row}">
          <el-button
            v-if="row.edit"
            type="info"
            size="mini"
            icon="el-icon-circle-check-outline"
            @click="confirmEdit(row)"
          >
            确定
          </el-button>
          <el-button
            v-else
            type="primary"
            size="mini"
            @click="row.edit=!row.edit"
          >
            编辑
          </el-button>
          <el-button v-if="row.triggerState!== 'WAITING'" size="mini" type="success" @click="resume(row)">
            恢复
          </el-button>
          <el-button v-if="row.triggerState!== 'PAUSED'" size="mini" type="warning" @click="pause(row)">
            暂停
          </el-button>
          <el-button size="mini" type="danger" @click="">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
    import { getTaskList, resumeTask, pauseTask, updateTask, deleteTask } from "@/api/system";

    export default {
        name: 'InlineEditTable',
        filters: {
            statusFilter(status) {
                const statusMap = {
                    'WAITING': '运行中',
                    'ACQUIRED': '运行中',
                    'PAUSED': '暂停中'
                }
                return statusMap[status]
            },
            statusFilter1(status) {
                const statusMap = {
                    'WAITING': 'success',
                    'ACQUIRED': 'success',
                    'PAUSED': 'warning'
                }
                return statusMap[status]
            }
        },
        data() {
            return {
                list: null,
                listLoading: true,
                listQuery: {
                    page: 1,
                    limit: 10
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
            }
        },
        created() {
            this.getList()
        },
        methods: {
            async getList() {
                this.listLoading = true
                const { data } = await getTaskList(this.listQuery)
                const items = data.items
                this.list = items.map(v => {
                    this.$set(v, 'edit', false) // https://vuejs.org/v2/guide/reactivity.html
                    v.originalCronExpression = v.cronExpression //  will be used when user click the cancel botton
                    return v
                })
                this.listLoading = false
            },
            resume(row) {
                resumeTask({
                    jobClassName:row.jobClassName,
                    jobGroupName:row.jobGroup,
                    cronExpression:row.cronExpression
                }).then(response => {
                    this.$message({
                        message: response.message,
                        type: 'success'
                    })
                    row.triggerState="WAITING"
                })
            },
            pause(row) {
                pauseTask({
                    jobClassName:row.jobClassName,
                    jobGroupName:row.jobGroup,
                    cronExpression:row.cronExpression
                }).then(response => {
                    this.$message({
                        message: response.message,
                        type: 'success'
                    })
                    row.triggerState="PAUSED"
                })
            },
            delete() {
                deleteTask({
                    jobClassName:row.jobClassName,
                    jobGroupName:row.jobGroup,
                    cronExpression:row.cronExpression
                }).then(response => {

                    for (const v of this.list) {
                        if (v.jobName === this.temp.jobName) {
                            const index = this.list.indexOf(v)
                            // this.list.remove(index)
                            this.list.splice(index, 1)
                            break
                        }
                    }
                    this.$message({
                        message: response.message,
                        type: 'success'
                    })
                })
            },
            cancelEdit(row) {
                row.cronExpression = row.originalCronExpression
                row.edit = false
                this.$message({
                    message: '修改已取消',
                    type: 'warning'
                })
            },
            confirmEdit(row) {
                row.edit = false
                row.originalCronExpression = row.cronExpression
                updateTask({
                    jobClassName:row.jobClassName,
                    jobGroupName:row.jobGroup,
                    cronExpression:row.cronExpression
                }).then(response => {
                    this.$message({
                        message: response.message,
                        type: 'success'
                    })
                })
            }
        }
    }
</script>

<style scoped>
  .edit-input {
    padding-right: 100px;
  }
  .cancel-btn {
    position: absolute;
    right: 15px;
    top: 20px;
  }
</style>
