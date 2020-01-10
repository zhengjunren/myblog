<template>
  <div class="app-container">
    <el-table
      v-loading="listLoading"
      :data="treeData"
      style="width: 100%;margin-bottom: 20px;"
      row-key="id"
      border
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
      <el-table-column label="名称" prop="name" width="180px">
        <template slot-scope="scope">
          <span>{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="排序" align="center" prop="updateTime" width="60px">
        <template slot-scope="scope">
          <span>{{ scope.row.sort }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" prop="updateTime" width="200px">
        <template slot-scope="scope">
          <span>{{ scope.row.updateTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="200px">
        <template slot-scope="scope">
          <el-button-group>
            <el-button size="mini" type="primary" icon="el-icon-edit" @click=""/>
            <el-popover
              :ref="scope.row.id"
              placement="top"
              width="200">
              <p>确定删除吗,如果存在下级节点则一并删除，此操作不能撤销！</p>
              <div style="text-align: right; margin: 0">
                <el-button size="mini" type="text" @click="$refs[scope.row.id].doClose()">取消</el-button>
                <el-button :loading="delLoading" type="primary" size="mini" @click="">确定</el-button>
              </div>
              <el-button slot="reference" type="danger" icon="el-icon-delete" size="mini"/>
            </el-popover>
          </el-button-group>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { getArticleCategories } from '@/api/article'
export default {
  name: "ArticleCategory",
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
  methods: {
    fetchData() {
      this.listLoading = true
      getArticleCategories().then(response => {
        this.treeData = response.data.content
        this.listLoading = false
      })
    },
  }
}
</script>

<style scoped>

</style>
