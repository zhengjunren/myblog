<template>
  <div class="app-container">
    <div v-if="user">
      <el-row :gutter="20">
        <el-col :span="6" :xs="24">
          <user-card :user="user" />
        </el-col>
        <el-col :span="18" :xs="24">

        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import UserCard from './components/UserCard'
import {getOwnInfo} from '@/api/profile'
export default {
  name: "index",
  components: { UserCard },
  data() {
    return {
      user: {
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
  },
  methods: {
    getInfo() {
      getOwnInfo().then(response => {
        this.user = response.data
      })
    }
  },
  created() {
    this.getInfo()
  }
}
</script>

<style scoped>

</style>
