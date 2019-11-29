<template>
  <div class="createPost-container">
    <el-form ref="postForm" :model="postForm" class="form-container">

      <sticky :z-index="10" :class-name="postForm.status | statusFilter">
        <CommentDropdown v-model="postForm.isComment" />
        <el-button v-loading="loading" style="margin-left: 10px;" type="success" @click="">
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
                  <el-form-item label-width="60px" style="margin-left: -15px" label="作者:" class="postInfo-container-item">
<!--                    <el-select v-model="postForm.author" :remote-method="getRemoteUserList" filterable default-first-option remote placeholder="Search user">-->
<!--                      <el-option v-for="(item,index) in userListOptions" :key="item+index" :label="item" :value="item" />-->
<!--                    </el-select>-->
                  </el-form-item>
                </el-col>
                <el-col :span="10">
                  <el-form-item label-width="120px" label="发布时间:" class="postInfo-container-item">
                    <el-date-picker v-model="displayTime" type="datetime" format="yyyy-MM-dd HH:mm:ss" placeholder="Select date and time" />
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
  import { validURL } from '@/utils/validate'
  import {fetchArticle} from "@/api/article";
  import { CommentDropdown, PlatformDropdown, SourceUrlDropdown } from './Dropdown'
  const defaultForm = {
    status: 1,
    title: '', // 文章题目
    content: '', // 文章内容
    summary: '', // 文章摘要
    id: undefined,
    isComment: undefined,
    userId: undefined,
    createTime:undefined
  }
  export default {
    name: "ArticleDetail",
    components: { Tinymce, MDinput, Sticky, CommentDropdown, PlatformDropdown, SourceUrlDropdown },
    filters: {
      statusFilter(status) {
        const statusMap = {
          1:"published",
          2:"draft",
          3:"deleted"
        }
        return 'sub-navbar '+statusMap[status]
      }
    },
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        userListOptions: [],
        postForm: Object.assign({}, defaultForm),
        loading: false,
        tempRoute: {}
      }
    },
    computed: {
      summaryShortLength() {
        return this.postForm.summary.length
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
      if (this.isEdit) {
        const id = this.$route.params && this.$route.params.id
        this.fetchData(id)
      } else {
        this.postForm = Object.assign({}, defaultForm)
      }

      // Why need to make a copy of this.$route here?
      // Because if you enter this page and quickly switch tag, may be in the execution of the setTagsViewTitle function, this.$route is no longer pointing to the current page
      // https://github.com/PanJiaChen/vue-element-admin/issues/1221
      this.tempRoute = Object.assign({}, this.$route)
    },
    methods: {
      fetchData(id) {
        fetchArticle(id).then(response => {
          this.postForm = response.data

          // just for test
          this.postForm.title += `   Article Id:${this.postForm.id}`
          this.postForm.summary += `   Article Id:${this.postForm.id}`
          this.postForm.isComment = this.postForm.isComment === 1;
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
      getRemoteUserList(query) {
        // searchUser(query).then(response => {
        //   if (!response.data.items) return
        //   this.userListOptions = response.data.items.map(v => v.name)
        // })
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

