<template>
  <el-form :label-position="labelPosition" label-width="100px">
    <el-form-item label="用户名">
      <el-input v-model.trim="user.username" :disabled="true" />
    </el-form-item>
    <el-form-item label="昵称">
      <el-input v-model.trim="user.nickname" />
    </el-form-item>
    <el-form-item label="邮箱">
      <el-input v-model.trim="user.email" />
    </el-form-item>
    <el-form-item label="手机">
      <el-input v-model.trim="user.phone" />
    </el-form-item>
    <el-form-item label="手机">
      <el-input v-model.trim="user.birthday" />
    </el-form-item>
    <el-form-item label="状态">
      <el-radio-group v-model.trim="user.status" :disabled="true">
        <el-radio label="正常" />
        <el-radio label="冻结" />
        <el-radio label="注销" />
      </el-radio-group>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submit">更新</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
export default {
  name: "Account",
  props: {
    user: {
      type: Object,
      default: () => {
        return {
          username: '',
          nickname:'',
          email:'',
          avatar:'',
          phone: '',
          status: 1,
          birthday: '',
          createTime: ''
        }
      }
    }
  },
  data() {
    return {
      labelPosition: 'right'
    }
  },
  methods: {
    submit() {
      // updateProfile(this.user).then(response => {
      //   this.$message({
      //     message: response.message,
      //     type: 'success',
      //     duration: 5 * 1000
      //   })
      // }).catch(() => {
      //   this.$message({
      //     message: "网络错误",
      //     type: 'success'
      //   })
      // })
    }
  }
}
</script>

<style scoped>

</style>
