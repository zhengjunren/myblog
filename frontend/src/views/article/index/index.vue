<template>
  <div class="app-container">
    <div class="filter-container">
      <el-date-picker
        v-model="time"
        type="daterange"
        range-separator=":"
        style="width: 250px"
        class="filter-item"
        value-format="yyyy-MM-dd HH:mm:ss"
        start-placeholder="开始日期"
        end-placeholder="结束日期"/>
      <el-select v-model="listQuery.status" placeholder="文章状态" clearable class="filter-item" style="width: 110px" @change="">
        <el-option v-for="item in enabledTypeOptions" :key="item.key" :label="item.display_name" :value="item.key"/>
      </el-select>
      <el-input class="filter-item" v-model="listQuery.username" placeholder="作者名" style="width: 100px" />
      <treeselect v-model="listQuery.categoryId" :options="categories" class="filter-item" style="width: 250px;" placeholder="选择分类" />
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="fetchData">搜索</el-button>
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-refresh" @click="reset">重置</el-button>
    </div>
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
            <el-form-item label="评论数">
              <span>{{ props.row.commentCount }}</span>
            </el-form-item>
            <el-form-item label="点赞数">
              <span>{{ props.row.likeCount }}</span>
            </el-form-item>
            <el-form-item label="浏览数">
              <span>{{ props.row.viewCount }}</span>
            </el-form-item>
            <el-form-item v-if="!(props.row.summary === null || props.row.summary ==='')" label="摘要">
              <span>{{ props.row.summary }}</span>
            </el-form-item>
            <el-form-item label="编辑时间">
              <span>{{ props.row.updateTime }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column align="center" label="序号" width="80">
        <template slot-scope="scope">
          <span>{{ scope.$index + (listQuery.page-1) * (listQuery.limit) + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="作者" min-width="80">
        <template slot-scope="scope">
          <span>{{ scope.row.username }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="所属分类" min-width="80">
        <template slot-scope="scope">
          <span>{{ scope.row.categoryName }}</span>
        </template>
      </el-table-column>
      <el-table-column align="left" label="标题" min-width="220">
        <template slot-scope="scope">
          <el-link @click="push(scope.row.id)">{{ scope.row.title }}</el-link>
        </template>
      </el-table-column>
      <el-table-column align="center" label="状态" min-width="80">
        <template slot-scope="scope">
            <el-tag :type="scope.row.status | tagTypeFilter">
              {{ scope.row.status | statusFilter }}
            </el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" label="允许评论" min-width="60">
        <template slot-scope="scope">
          <el-switch @change="updateComment(scope.row)" v-model="scope.row.isComment"/>
        </template>
      </el-table-column>
      <el-table-column align="center" label="创建时间" min-width="140">
        <template slot-scope="scope">
          <span>{{scope.row.createTime}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作" min-width="120" fixed="right">
        <template slot-scope="scope">
          <el-popover
            :ref="scope.row.id"
            placement="top"
            width="200">
            <p>确定踢出吗！</p>
            <div style="text-align: right; margin: 0">
              <el-button size="mini" type="text" @click="$refs[scope.row.id].doClose()">取消</el-button>
              <el-button :loading="delLoading" type="primary" size="mini" @click="">确定</el-button>
            </div>
            <el-button slot="reference" size="mini" type="danger"><svg-icon icon-class="kick"/></el-button>
          </el-popover>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="fetchData" />
  </div>
</template>

<script>
import {getArticles, getArticleCategoryTree, updateComment} from '@/api/article'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import Pagination from '@/components/Pagination'
import waves from '@/directive/waves'
export default {
  name: "index",
  directives: { waves },
  components: { Pagination, Treeselect},
  filters: {
    tagTypeFilter(status) {
      const tagTypeMap = {
        1: 'success',
        2: 'info',
        3: 'danger'
      }
      return tagTypeMap[status]
    },
    statusFilter(status){
      const statusMap = {
        1: '已发布',
        2: '草稿文',
        3: '已删除'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      list: null,
      categories: [],
      total: 0,
      time: '',
      listQuery: {
        page: 1,
        limit: 10,
        categoryId: 0,
        username: undefined,
        status: undefined,
        start: undefined,
        end: undefined
      },
      enabledTypeOptions: [
        { key: 1, display_name: '已发布' },
        { key: 2, display_name: '草稿文' },
        { key: 3, display_name: '已删除' }
      ],
      dialog: false,
      listLoading: true,
      delLoading: false
    }
  },
  created() {
    this.getCategories()
    this.fetchData()
  },
  methods: {
    fetchData() {
      const date = this.time
      if(date !== undefined && date !== null) {
        this.listQuery.start = date[0]
        this.listQuery.end = date[1]
      }
      getArticles(this.listQuery).then(response => {
        this.list = response.data.items
        this.total = response.data.total
        this.listQuery.end = undefined
        this.listQuery.start = undefined
        this.listLoading = false
      })
    },
    getCategories() {
      getArticleCategoryTree().then(res => {
        this.categories = []
        const category = { id: 0, label: '顶级类目', children: [] }
        category.children = res.data
        this.categories.push(category)
      })
    },
    updateComment(row) {
      updateComment(row.id).then(response => {
        var flag  = row.isComment
        this.$notify({
          title: flag ? '允许评论成功' : '禁止评论成功',
          type: 'success',
          duration: 2500
        })
      })
    },
    reset() {
      this.listQuery = {page: 1, limit: 10, categoryId: 0, username: undefined, status: undefined, start: undefined, end: undefined}
      this.fetchData()
    },
    push(id){
      this.$router.push({
        name: 'Edit',
        params: {
          id: id
        }
      })
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
</style>
