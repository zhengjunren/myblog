<template>
  <div class="createPost-container">
    <el-form ref="postForm" :model="postForm" :rules="rules" class="form-container">

      <sticky :z-index="10" :class-name="postForm.status | statusFilter">
        <CommentDropdown v-model="isComment" />
        <el-button v-loading="loading" style="margin-left: 10px;" type="success" @click="submitForm">
          发布
        </el-button>
        <el-button v-loading="loading" type="warning" @click="">
          存为草稿
        </el-button>
      </sticky>
      <div class="createPost-main-container">
        <el-row>
          <el-col :span="24">
            <el-form-item style="margin-bottom: 40px;" prop="title">
              <MDinput v-model="postForm.title" :maxlength="100" name="name" required>
                标题
              </MDinput>
            </el-form-item>
            <div class="postInfo-container">
              <el-row>
                <el-col :span="8">
                  <el-form-item label-width="60px" style="margin-left: -15px" label="分类:" class="postInfo-container-item">
                    <treeselect v-model="postForm.categoryId" :options="categories" style="width: 250px;" placeholder="选择上级类目" />
                  </el-form-item>
                </el-col>
                <el-col :span="10">
                  <el-form-item style="margin-left: -10px" label-width="80px" label="发布时间:" class="postInfo-container-item">
                    <el-date-picker v-model="displayTime" type="datetime" format="yyyy-MM-dd HH:mm:ss" placeholder="选择时间" />
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </el-col>
        </el-row>

        <el-form-item style="margin-bottom: 40px; margin-left: -25px" label-width="70px" label="摘要:">
          <el-input v-model="postForm.summary" :rows="1" type="textarea" class="article-textarea" autosize placeholder="请输入内容" />
          <span v-show="summaryShortLength" class="word-counter">{{ summaryShortLength }} 字</span>
        </el-form-item>
        <el-form-item prop="content" style="margin-bottom: 30px;">
          <Tinymce ref="editor" v-model="postForm.content" :height="400" />
        </el-form-item>
      </div>
    </el-form>
  </div>
</template>

<script>
import Tinymce from '@/components/Tinymce'
import MDinput from '@/components/MDinput'
import Sticky from '@/components/Sticky' // 粘性header组件
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import { fetchArticle, postArticle, updateArticle, getArticleCategoryTree } from "@/api/article";
import {CommentDropdown, PlatformDropdown, SourceUrlDropdown} from './Dropdown'

const defaultForm = {
  status: 1,
  title: '', // 文章题目
  content: '', // 文章内容
  summary: '', // 文章摘要
  id: undefined,
  isComment: undefined,
  categoryId: undefined,
  userId: undefined,
  createTime:undefined
}
export default {
  name: "ArticleDetail",
  components: { Tinymce, MDinput, Sticky, CommentDropdown, PlatformDropdown, SourceUrlDropdown, Treeselect },
  filters: {
    statusFilter(status) {
      const statusMap = {
        1:"published",
        2:"draft",
        3:"deleted"
      }
      return 'sub-navbar '+statusMap[status]
    },
    commentFilter(isComment) {
      const statusMap = {
        0: false,
        1: true
      }
      return statusMap[isComment]
    }
  },
  props: {
    isEdit: {
      type: Boolean,
      default: false
    }
  },
  data() {
    const validateRequire = (rule, value, callback) => {
      if (value === '') {
        this.$message({
          message: rule.field + '为必传项',
          type: 'error'
        })
        callback(new Error(rule.field + '为必传项'))
      } else {
        callback()
      }
    }
    return {
      rules: {
        title: [{ validator: validateRequire }],
        content: [{ validator: validateRequire }],
      },
      isComment:true,
      userListOptions: [],
      postForm: Object.assign({}, defaultForm),
      loading: false,
      tempRoute: {},
      nickname: "",
      categories: []
    }
  },
  computed: {
    summaryShortLength() {
      if (this.postForm.summary != null){
        return this.postForm.summary.length
      }
      return 0
    },
    displayTime: {
      // set and get is useful when the data
      // returned by the back end api is different from the front end
      // back end return => "2013-06-25 06:59:25"
      // front end need timestamp => 1372114765000
      get() {
        return (+new Date(this.postForm.createTime))
      },
      set(val) {
        this.postForm.createTime = new Date(val)
      }
    }
  },
  created() {
    this.getCategories()
    if (this.isEdit) {
      const id = this.$route.params && this.$route.params.id
      this.fetchData(id)
    } else {
      this.postForm = Object.assign({}, defaultForm)
    }
    this.tempRoute = Object.assign({}, this.$route)
  },
  methods: {
    getCategories() {
      getArticleCategoryTree().then(res => {
        this.categories = []
        const category = { id: 0, label: '顶级类目', children: [] }
        category.children = res.data
        this.categories.push(category)
      })
    },
    fetchData(id) {
      fetchArticle(id).then(response => {
        this.postForm = response.data

        // just for test
        // this.postForm.title += `   Article Id:${this.postForm.id}`
        // this.postForm.summary += `   Article Id:${this.postForm.id}`
        this.isComment = this.postForm.isComment !== 0;

        // set tagsview title
        this.setTagsViewTitle()

        // set page title
        this.setPageTitle()
      }).catch(err => {
        console.log(err)
      })
    },
    setTagsViewTitle() {
      const title = '编辑文章'
      const route = Object.assign({}, this.tempRoute, { title: `${title}-${this.postForm.id}` })
      this.$store.dispatch('tagsView/updateVisitedView', route)
    },
    setPageTitle() {
      const title = '编辑文章'
      document.title = `${title} - ${this.postForm.id}`
    },
    submitForm() {
      this.isComment ? this.postForm.isComment = 1 : this.postForm.isComment = 0

      if (this.postForm.id != null) {
        updateArticle(this.postForm).then(response => {
          this.loading = true
          this.$notify({
            title: '成功',
            message: "更新文章",
            type: 'success',
            duration: 2000
          })
          this.postForm.status = 1
          this.loading = false
        })
      }
      else {
        postArticle(this.postForm).then(response => {
          this.loading = true
          this.$notify({
            title: '成功',
            message: "文章发布成功",
            type: 'success',
            duration: 2000
          })
          this.postForm.status = 1
          this.loading = false
        })
      }
    }
  }
}
</script>

<style lang="scss" scoped>
  @import "~@/styles/mixin.scss";

  .createPost-container {
    position: relative;

    .createPost-main-container {
      padding: 40px 45px 20px 50px;

      .postInfo-container {
        position: relative;
        @include clearfix;
        margin-bottom: 10px;

        .postInfo-container-item {
          float: left;
        }
      }
    }

    .word-counter {
      width: 40px;
      position: absolute;
      right: 10px;
      top: 0px;
    }
  }

  .article-textarea /deep/ {
    textarea {
      padding-right: 40px;
      resize: none;
      border: none;
      border-radius: 0px;
      border-bottom: 1px solid #bfcbd9;
    }
  }
</style>

