<template>
  <div class="app-container">
    <el-form
      ref="form"
      v-loading="formLoading"
      :data="form"
      element-loading-text="加载中..."
      :model="form"
      label-width="120px"
    >
      <el-form-item label="头像">
        <div class="avatar-container">
          <div class=" avatar-wrapper">
            <img :src="form.avatar+'?imageView2/1/w/60/h/60'" width="60" height="60" class="user-avatar">
          </div>
        </div>
      </el-form-item>
      <el-form-item label="用户名">
        <el-input v-model="form.name" :disabled="true" />
      </el-form-item>
      <el-form-item label="昵称">
        <el-input v-model="form.nickname" />
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="form.email" />
      </el-form-item>
      <el-form-item label="首页">
        <el-input v-model="form.url" />
      </el-form-item>
      <el-form-item label="上次登录时间">
        <el-input v-model="form.lastLoginTime" :disabled="true"/>
      </el-form-item>
      <el-form-item label="注册时间">
        <el-input v-model="form.registerTime" :disabled="true"/>
      </el-form-item>
      <el-form-item label="状态">
        <el-radio-group v-model="form.status" :disabled="true">
          <el-radio label="正常" />
          <el-radio label="冻结" />
          <el-radio label="注销" />
        </el-radio-group>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
    import { getInfo, updateProfile } from "@/api/user";
    export default {
        name: "profile",
        data() {
            return {
                formLoading: true,
                form:{
                    name:'',
                    nickname:'',
                    email:'',
                    url:'',
                    avatar:'',
                    lastLoginTime:'',
                    registerTime:'',
                    status:'',
                }
            }
        },
        created() {
          this.getProfile()
        },
        methods: {
            getProfile() {
              getInfo().then(response => {
                  this.form = response.data
                  this.formLoading = false
              })
            },
            onSubmit() {
                this.formLoading = true
                updateProfile(this.form).then(response => {
                    this.formLoading = false
                    this.$message({
                        message: response.message,
                        type: 'success'
                    })
                }).catch(() => {
                    this.formLoading = false
                })
            }
        }
    }
</script>

<style lang="scss" scoped>
  .avatar-container {
    margin-right: 0;

    .avatar-wrapper {
      margin-top: 5px;
      position: relative;

      .user-avatar {
        cursor: pointer;
        width: 60px;
        height: 60px;
        border-radius: 10px;
      }
    }
  }
</style>
