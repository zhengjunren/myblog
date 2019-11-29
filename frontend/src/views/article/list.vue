<template>
  <div class="app-container">
    <el-table v-loading="listLoading" :data="list" border fit highlight-current-row style="width: 100%">
      <el-table-column align="center" label="ID" width="80">
        <template slot-scope="scope">
          <span>{{ scope.$index + (listQuery.page-1) * (listQuery.limit) + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column width="180px" align="center" label="创建时间">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime}}</span>
        </template>
      </el-table-column>
      <el-table-column width="120px" align="center" label="作者">
        <template slot-scope="scope">
          <span>{{ scope.row.nickname }}</span>
        </template>
      </el-table-column>
      <el-table-column width="120px" align="center" label="允许评论">
        <template slot-scope="scope">
          <el-tag :type="scope.row.isComment | commentFilter2">
            {{ scope.row.isComment | commentFilter }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column width="100px" align="center" label="点赞数">
        <template slot-scope="scope">
          <span>{{scope.row.likeCount}}</span>
        </template>
      </el-table-column>
      <el-table-column min-width="300px" label="标题">
        <template slot-scope="{row}">
          <router-link :to="'/article/edit/'+row.id+'/'+ row.nickname" class="link-type">
            <span>{{ row.title }}</span>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column width="110" class-name="status-col" label="状态">
        <template slot-scope="{row}">
          <el-tag :type="row.status | statusFilter">
            {{ row.status | statusFilter2}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作" width="200" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <router-link :to="'/article/edit/'+scope.row.id + '/'+scope.row.nickname">
            <el-button type="primary" size="small" icon="el-icon-edit">
              编辑
            </el-button>
          </router-link>
          &nbsp;&nbsp;
          <el-popover
            :ref="scope.$index"
            placement="top"
            width="180">
            <p>确定删除吗？</p>
            <div style="text-align: right; margin: 0">
              <el-button size="mini" type="text" @click="$refs[scope.$index].doClose()">取消</el-button>
              <el-button type="primary" size="mini" @click="">确定</el-button>
            </div>
            <el-button slot="reference" size="small" type="danger" icon="el-icon-delete">删除</el-button>
          </el-popover>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />
  </div>
</template>

<script>
  import {fetchList} from "@/api/article";
  import Pagination from '@/components/Pagination'
  export default {
    name: "ArticleList",
    components: {
      Pagination
    },
    filters: {
      statusFilter(status) {
        const statusMap = {
          1:"success",
          2:"warning",
          3:"danger"
        }
        return statusMap[status]
      },
      statusFilter2(status) {
        const statusMap = {
          1:"已发布",
          2:"草稿",
          3:"已删除"
        }
        return statusMap[status]
      },
      commentFilter(comment) {
        const statusMap = {
          0:"禁止",
          1:"允许",
        }
        return statusMap[comment]
      },
      commentFilter2(comment) {
        const statusMap = {
          1:"success",
          0:"danger",
        }
        return statusMap[comment]
      },
    },
    data() {
      return {
        visible:false,
        list: null,
        total: 0,
        listLoading: true,
        listQuery: {
          page: 1,
          limit: 10
        }
      }
    },
    created() {
      this.getList()
    },
    methods:{
      getList() {
        this.listLoading = true
        fetchList(this.listQuery).then(response => {
          this.list = response.data.items
          this.total = response.data.total
          // 模拟请求时间
          setTimeout(() => {
            this.listLoading = false
          }, 500)
        })
      }
    }
  }
</script>

<style scoped>

</style>
