<template>
  <div class="app-container">
    <div class="filter-container">
      <el-button-group>
        <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-plus" @click="add">
          新增
        </el-button>
        <el-button v-waves :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download" @click="downloadExcel">
          导出
        </el-button>
      </el-button-group>
    </div>
    <el-table
      v-loading="listLoading"
      :data="treeData"
      style="width: 100%;margin-bottom: 20px;"
      row-key="id"
      border
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
      <el-table-column label="名称" prop="name" width="150px">
        <template slot-scope="scope">
          <span>{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="icon" label="图标" align="center" width="80px">
        <template slot-scope="scope">
          <svg-icon :icon-class="scope.row.icon" />
        </template>
      </el-table-column>
      <el-table-column prop="sort" align="center" width="90px" label="排序">
        <template slot-scope="scope">
          {{ scope.row.sort }}
        </template>
      </el-table-column>
      <el-table-column :show-overflow-tooltip="true" width="100px" prop="path" label="路由地址" />
      <el-table-column :show-overflow-tooltip="true" min-width="150px" label="组件路径">
        <template slot-scope="scope">
          {{ scope.row.component === null || scope.row.component === "" ? "目录" : scope.row.component }}
        </template>
      </el-table-column>
      <el-table-column prop="iframe" align="center" label="外链" width="90px">
        <template slot-scope="scope">
          <span v-if="scope.row.iframe">是</span>
          <span v-else>否</span>
        </template>
      </el-table-column>
      <el-table-column prop="iframe" align="center" label="缓存" width="90px">
        <template slot-scope="scope">
          <span v-if="scope.row.cache">是</span>
          <span v-else>否</span>
        </template>
      </el-table-column>
      <el-table-column prop="iframe" align="center" label="可见" width="90px">
        <template slot-scope="scope">
          <span v-if="scope.row.hidden">否</span>
          <span v-else>是</span>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" align="center" label="创建日期" width="200px">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="160px" align="center" fixed="right">
        <template slot-scope="scope">
          <el-button-group>
            <el-button size="mini" type="primary" icon="el-icon-edit" @click="edit(scope.row)"/>
            <el-popover
              :ref="scope.row.id"
              placement="top"
              width="200">
              <p>确定删除吗,如果存在下级节点则一并删除，此操作不能撤销！</p>
              <div style="text-align: right; margin: 0">
                <el-button size="mini" type="text" @click="$refs[scope.row.id].doClose()">取消</el-button>
                <el-button :loading="delLoading" type="primary" size="mini" @click="subDelete(scope.row.id)">确定</el-button>
              </div>
              <el-button slot="reference" type="danger" icon="el-icon-delete" size="mini"/>
            </el-popover>
          </el-button-group>
        </template>
      </el-table-column>
    </el-table>
    <eForm ref="form" :is-add="isAdd"/>
  </div>
</template>

<script>
import { getMenus, downloadExcel } from '@/api/menu'
import { del } from '@/api/menu'
import {downloadFile} from '@/utils/index'
import waves from '@/directive/waves'
import eForm from './form'
export default {
  name: "index",
  directives: { waves },
  components: { eForm },
  data() {
    return {
      treeData: [],
      isAdd: false,
      delLoading: false,
      listLoading: true,
      downloadLoading: false
    }
  },
  created() {
    this.fetchData()
  },
  methods :{
    fetchData() {
      this.listLoading = true
      getMenus().then(response => {
        this.treeData = response.data.content
        this.listLoading = false
      })
    },
    edit(data) {
      this.isAdd = false
      const _this = this.$refs.form
      _this.getMenus()
      _this.form = { id: data.id, component: data.component, componentName: data.componentName, name: data.name, sort: data.sort, parentId: data.parentId, path: data.path, iframe: data.iframe.toString(), roles: [], icon: data.icon, cache: data.cache, hidden: data.hidden, type: data.type, permission: data.permission }
      _this.dialog = true
    },
    add() {
      this.isAdd = true
      this.$refs.form.getMenus()
      this.$refs.form.dialog = true
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
    downloadExcel() {
      downloadExcel().then(result => {
        downloadFile(result, '菜单列表', 'xlsx')
        this.downloadLoading = false
      })
    }
  }

}
</script>

<style scoped>

</style>
