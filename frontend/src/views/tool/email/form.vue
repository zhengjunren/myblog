<template>
  <el-dialog :visible.sync="dialog" :close-on-click-modal="false" title="邮件配置" append-to-body width="580px">
    <el-form ref="form" :model="form" :rules="rules" style="margin-top: 6px;" size="small" label-width="100px">
      <el-form-item label="发件人邮箱" prop="fromUser">
        <el-input v-model="form.fromUser" />
      </el-form-item>
      <el-form-item label="发件用户名" prop="user">
        <el-input v-model="form.user" />
      </el-form-item>
      <el-form-item label="邮箱密码" prop="pass">
        <el-input v-model="form.pass" type="password" />
      </el-form-item>
      <el-form-item label="SMTP地址" prop="host">
        <el-input v-model="form.host" />
      </el-form-item>
      <el-form-item label="SMTP端口" prop="port">
        <el-input v-model="form.port"/>
      </el-form-item>
      <el-form-item label="">
        <el-button :loading="loading" size="medium" type="primary" @click="doSubmit">保存配置</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script>
import {getConfig, updateConfig} from '@/api/email'
export default {
  name: "index",
  data() {
    return {
      form: {
        id: 1,
        host:"",
        port:"",
        fromUser:"",
        user:"",
        pass:""
      },
      rules: {
        fromUser: [
          { required: true, message: '请输入您的邮箱', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
        ],
        user: [
          { required: true, message: '请输入发件用户名', trigger: 'blur' }
        ],
        pass: [
          { required: true, message: '密码不能为空', trigger: 'blur' }
        ],
        host: [
          { required: true, message: 'SMTP地址不能为空', trigger: 'blur' }
        ],
        port: [
          { required: true, message: 'SMTP端口不能为空', trigger: 'blur' }
        ]
      },
      dialog: false,
      loading: false
    }
  },
  methods: {
    fetchData() {
      getConfig().then(response => {
        this.form = response.data
      })
    },
    doSubmit() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.loading = true
          updateConfig(this.form).then(res => {
            this.$notify({
              title: '修改成功',
              type: 'success',
              duration: 2500
            })
            this.loading = false
            this.dialog = false
          }).catch(err => {
            this.loading = false
            console.log(err.response.data.message)
          })
        } else {
          return false
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
