<template>
  <el-dialog :visible.sync="dialog" :close-on-click-modal="false" :before-close="cancel" :title="isAdd ? '新增角色' : '编辑角色'" append-to-body width="520px">
    <el-form ref="form" :inline="true" :model="form" :rules="rules" size="small" label-width="60px">
      <el-form-item label="名称" prop="name">
        <el-input v-model="form.name" style="width: 160px;"/>
      </el-form-item>
      <el-form-item label="描述" prop="description">
        <el-input v-model="form.description" style="width: 160px;"/>
      </el-form-item>
      <el-form-item label="创建">
        <el-input v-model="form.createTime" disabled style="width: 160px;"/>
      </el-form-item>
      <el-form-item label="更新">
        <el-input v-model="form.updateTime" disabled style="width: 160px;"/>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="cancel">取消</el-button>
      <el-button :loading="loading" type="primary" @click="">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
export default {
  name: "roleForm",
  props: {
    isAdd: {
      type: Boolean,
      required: true
    }
  },
  data() {
    return {
      loading: false,
      dialog: false,
      form :{ name: '', description: '', createTime: '', updateTime: ''},
      rules: {
        name: [
          { required: true, message: '请输入名称', trigger: 'blur' }
        ],
        description: [
          { required: true, message: '请输入描述', trigger: 'blur' }
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
      this.form = { name: '', description: '', createTime: '', updateTime: ''}
    },
  }
}
</script>

<style scoped>

</style>
