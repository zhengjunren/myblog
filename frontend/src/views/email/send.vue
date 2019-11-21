<template>
  <div class="app-container">
      <el-form ref="form" :model="form" :rules="rules">
        <el-form-item label="邮件标题" prop="subject">
          <el-input v-model="form.subject"  style="width: 40%"/>
        </el-form-item>
        <el-form-item
          v-for="(domain, index) in tos"
          :label="'收件邮箱' + (index === 0 ? '': index)"
          :key="domain.key">
          <el-input v-model="domain.value" style="width: 31%"/>
          <el-button icon="el-icon-plus" @click="addDomain" />
          <el-button style="margin-left:0px;" icon="el-icon-minus" @click.prevent="removeDomain(domain)"/>
        </el-form-item>
        <div>
          <tinymce v-model="form.content" :height="400" />
        </div>
        <el-button :loading="loading" style="margin-top: 10px" size="medium" type="primary" @click="doSubmit">发送邮件</el-button>
      </el-form>

    <div class="editor-content" v-html="form.content"></div>
  </div>
</template>

<script>
    import Tinymce from '@/components/Tinymce'
    import { validatEmail } from '@/utils/validate'
    import { sendMail } from "@/api/system";
    export default {
        name: "SendMail",
        components: { Tinymce },
        data() {
            return{
                loading: false,
                form : {
                    subject: '', tos: [], content: ''
                },
                tos: [{
                    value: ''
                }],
                rules: {
                    subject: [
                        { required: true, message: '标题不能为空', trigger: 'blur' }
                    ]
                }
            }
        },
        methods:{
            removeDomain(item) {
                var index = this.tos.indexOf(item)
                if (index !== -1 && this.tos.length !== 1) {
                    this.tos.splice(index, 1)
                } else {
                    this.$message({
                        message: '请至少保留一位联系人',
                        type: 'warning'
                    })
                }
            },
            addDomain() {
                this.tos.push({
                    value: '',
                    key: Date.now()
                })
            },
            doSubmit() {
                const _this = this
                this.$refs['form'].validate((valid) => {
                    this.form.tos = []
                    if (valid) {
                        let sub = false
                        this.tos.forEach(function(data, index) {
                            if (data.value === '') {
                                _this.$message({
                                    message: '收件邮箱不能为空',
                                    type: 'warning'
                                })
                                sub = true
                            } else if (validatEmail(data.value)) {
                                _this.form.tos.push(data.value)
                            } else {
                                _this.$message({
                                    message: '收件邮箱格式错误',
                                    type: 'warning'
                                })
                                sub = true
                            }
                        })
                        if (sub) { return false }
                        this.loading = true
                        sendMail(this.form).then(res => {
                            this.$notify({
                                title: '发送成功',
                                type: 'success',
                                duration: 2500
                            })
                            this.loading = false
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
  .editor-content{
    margin-top: 20px;
  }
</style>
