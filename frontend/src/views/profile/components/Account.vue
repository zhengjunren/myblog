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
    <el-form-item label="生日">
      <el-date-picker
        v-model="user.birthday"
        :default-time="[user.birthday]"
        type="date"
        placeholder="选择日期">
      </el-date-picker>
    </el-form-item>
    <el-form-item label="性别">
      <el-radio-group v-model.trim="user.sex">
        <el-radio :label = 1>男</el-radio>
        <el-radio :label = 0>女</el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item label="状态">
      <el-radio-group v-model.trim="user.status" :disabled="true">
        <el-radio :label = 1>正常</el-radio>
        <el-radio :label = 0>异常</el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submit">更新</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import {update} from "@/api/profile";

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
          sex: 1,
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
      update(this.user).then(response => {
        this.$message({
          message: '更新成功',
          type: 'success',
          duration: 5 * 1000
        })
      }).catch(() => {
        this.$message({
          message: "网络错误",
          type: 'success'
        })
      })
    }
  }
}
</script>

<style scoped>

</style>
