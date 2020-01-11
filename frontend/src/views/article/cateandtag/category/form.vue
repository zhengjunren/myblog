<template>
  <el-dialog :visible.sync="dialog" :close-on-click-modal="false" :before-close="cancel" :title="isAdd ? '新增分类' : '编辑分类'" append-to-body width="580px">
    <el-form ref="form" :inline="true" :model="form" :rules="rules" size="small" label-width="100px">
      <el-form-item label="分类名：" prop="name">
        <el-input v-model="form.name" style="width: 250px;" placeholder="分类名"/>
      </el-form-item>
      <el-form-item label="排序：">
        <el-input-number style="width: 250px;" v-model="form.sort"/>
      </el-form-item>
      <el-form-item label="上级分类：">
        <treeselect v-model="form.parentId" :options="categories" style="width: 250px;" placeholder="选择分类" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="cancel">取消</el-button>
      <el-button :loading="loading" type="primary" @click="doSubmit">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { getArticleCategoryTree, createArticleCategory, updateArticleCategory } from '@/api/article'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
export default {
  name: "CategoryForm",
  props: {
    isAdd: {
      type: Boolean,
      required: true
    }
  },
  components: { Treeselect },
  data() {
    return {
      loading: false, dialog: false, categories: [],
      form: {id: undefined, name: '', sort: 999,parentId: 0, updateTime: undefined, createTime: undefined},
      rules: {
        name: [
          { required: true, message: '请输入名称', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    cancel() {
      this.resetForm()
    },
    resetForm() {
      this.dialog = false
      this.$refs['form'].resetFields()
      this.form = { id: undefined, name: '', sort: 999,parentId: 0, updateTime: undefined, createTime: undefined}
    },
    getCategories() {
      getArticleCategoryTree().then(res => {
        this.categories = []
        const category = { id: 0, label: '顶级类目', children: [] }
        category.children = res.data
        this.categories.push(category)
      })
    },
    doSubmit() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.loading = true
          console.log(this.isAdd)
          if (this.isAdd) {
            this.doAdd()
          } else this.doEdit()
        } else {
          return false
        }
      })
    },
    doAdd() {
      createArticleCategory(this.form).then(res => {
        this.resetForm()
        this.$notify({
          title: '添加成功',
          type: 'success',
          duration: 2500
        })
        this.loading = false
        this.$parent.fetchData()
      }).catch(() => {
        this.loading = false
      })
    },
    doEdit() {
      updateArticleCategory(this.form).then(res => {
        this.resetForm()
        this.$notify({
          title: '修改成功',
          type: 'success',
          duration: 2500
        })
        this.loading = false
        this.$parent.fetchData()
      }).catch(() => {
        this.loading = false
      })
    },
  }
}
</script>

<style scoped>

</style>
