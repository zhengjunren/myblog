<template>
  <div class="app-container">
    <div v-if="user">
      <el-row :gutter="20">
        <el-col :span="6" :xs="24">
          <user-card :user="user" />
        </el-col>

        <el-col :span="18" :xs="24">
          <el-card>
            <el-tabs v-model="activeTab">
              <el-tab-pane label="Activity" name="activity">
                <activity />
              </el-tab-pane>
              <el-tab-pane label="最新发布" name="timeline">
                <timeline :username="user.nickname" />
              </el-tab-pane>
              <el-tab-pane label="账户" name="account">
                <account :user="user" />
              </el-tab-pane>
            </el-tabs>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
  import UserCard from './components/UserCard'
  import Activity from './components/Activity'
  import Timeline from './components/Timeline'
  import Account from './components/Account'
  import { getInfo } from "@/api/user"
  export default {
    name: "index",
    components: { UserCard, Activity, Timeline, Account },
    data() {
      return {
        user: {
          name: '',
          nickname:'',
          email:'',
          url:'',
          avatar:'',
          lastLoginTime:'',
          registerTime:'',
          status:'',
        },
        activeTab: 'activity'
      }
    },
    created() {
      this.getUser()
    },
    methods: {
      getUser() {
        getInfo().then(response => {
          this.user = response.data
        })
      }
    }
  }
</script>

<style scoped>

</style>
