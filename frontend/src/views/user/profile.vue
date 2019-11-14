<template>
  <div class="app-container">
    <el-form ref="form" :model="form" label-width="120px">
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
      <el-form-item label="状态">
        <el-radio-group v-model="form.status" :disabled="true">
          <el-radio label="正常" />
          <el-radio label="冻结" />
          <el-radio label="注销" />
        </el-radio-group>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
    import { getInfo } from "@/api/user";
    export default {
        name: "profile",
        data() {
            return {
                form:{
                    name:'',
                    nickname:'',
                    email:'',
                    url:'',
                    avatar:'',
                    lastLoginTime:'',
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
              })
            }
        }
    }
</script>

<style scoped>

</style>
