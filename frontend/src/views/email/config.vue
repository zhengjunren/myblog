<template>
  <div class="app-container">
    <el-form ref="form" :model="form" :rules="rules" style="margin-top: 6px;" size="small" label-width="100px">
      <el-form-item label="发件人邮箱" prop="fromUser">
        <el-input v-model="form.fromUser" style="width: 40%"/>
      </el-form-item>
      <el-form-item label="发件用户名" prop="user">
        <el-input v-model="form.user" style="width: 40%;"/>
      </el-form-item>
      <el-form-item label="邮箱密码" prop="pass">
        <el-input v-model="form.pass" type="password" style="width: 40%;"/>
      </el-form-item>
      <el-form-item label="SMTP地址" prop="host">
        <el-input v-model="form.host" style="width: 40%;"/>
      </el-form-item>
      <el-form-item label="SMTP端口" prop="port">
        <el-input v-model="form.port" style="width: 40%;"/>
      </el-form-item>
      <el-form-item label="">
        <el-button size="medium" type="primary" @click="updateConfig">保存配置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>

    import { fetchEmailConfig, updateEmailConfig } from "@/api/system";
    export default {
        name: "EmailConfig",
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
                }
            }
        },
        created() {
            this.getConfig()
        },
        methods : {
            getConfig() {
                fetchEmailConfig().then(response => {
                    this.form = response.data
                })
            },
            updateConfig() {
                updateEmailConfig(this.form).then(response => {
                    this.$notify({
                        title: '成功',
                        message: response.message,
                        type: 'success',
                        duration: 2000
                    })
                })
            }
        }
    }
</script>

<style scoped>

</style>
