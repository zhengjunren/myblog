<template>
  <div class="navbar">
    <hamburger :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar" />

    <breadcrumb class="breadcrumb-container" />

    <div class="right-menu">
      <template v-if="device!=='mobile'">
        <div class="right-menu-item hover-effect">
          <a href="https://github.com/zhengjunren/myblog" target="_blank">
            <svg-icon icon-class="github" />
          </a>
        </div>
        <Screenfull id="screenfull" class="right-menu-item hover-effect" />
      </template>

      <el-dropdown class="avatar-container" trigger="click">
        <div class="avatar-wrapper">
          <img :src="avatar+'?imageView2/1/w/80/h/80'" class="user-avatar">
          <i class="el-icon-caret-bottom" />
        </div>
        <el-dropdown-menu slot="dropdown" class="user-dropdown">
          <router-link to="/">
            <el-dropdown-item style="text-align: center">
              首页
            </el-dropdown-item>
          </router-link>
          <el-dropdown-item divided>
            <span style="display:block; text-align: center" @click="logout">退出</span>
          </el-dropdown-item>
          <router-link to="/user/center">
            <el-dropdown-item divided>
              个人中心
            </el-dropdown-item>
          </router-link>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'
import Screenfull from '@/components/Screenfull'
import { getInfo } from '@/api/user'

export default {
  components: {
    Breadcrumb,
    Hamburger,
    Screenfull
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'avatar',
      'device'
    ]),
  },
  // mounted() {
  //   this.getInfo()
  // },
  methods: {
    // getInfo() {
    //   console.log(undefined === this.avatar)
    //   if (this.avatar === '' || this.avatar === undefined){
    //     getInfo().then(response => {
    //       this.avatar = response.data.avatar
    //     })
    //   }
    // },
    toggleSideBar() {
      this.$store.dispatch('app/toggleSideBar')
    },
    async logout() {
      // await this.$store.dispatch('user/removeAvatar')
      // await this.$store.avatar = ''
      await this.$store.dispatch('user/logout')
      // await this.$store.dispatch('user/setAvatar', '')
      // await this.$store.dispatch('user/removeAvatar')
      this.$router.push(`/login?redirect=${this.$route.fullPath}`)
    }
  }
}
</script>

<style lang="scss" scoped>
  .navbar {
    height: 50px;
    overflow: hidden;
    position: relative;
    background: #fff;
    box-shadow: 0 1px 4px rgba(0,21,41,.08);

    .hamburger-container {
      line-height: 46px;
      height: 100%;
      float: left;
      cursor: pointer;
      transition: background .3s;
      -webkit-tap-highlight-color:transparent;

      &:hover {
        background: rgba(0, 0, 0, .025)
      }
    }

    .breadcrumb-container {
      float: left;
    }

    .errLog-container {
      display: inline-block;
      vertical-align: top;
    }

    .right-menu {
      float: right;
      height: 100%;
      line-height: 50px;

      &:focus {
        outline: none;
      }

      .right-menu-item {
        display: inline-block;
        padding: 0 8px;
        height: 100%;
        font-size: 18px;
        color: #5a5e66;
        vertical-align: text-bottom;

        &.hover-effect {
          cursor: pointer;
          transition: background .3s;

          &:hover {
            background: rgba(0, 0, 0, .025)
          }
        }
      }

      .avatar-container {
        margin-right: 30px;

        .avatar-wrapper {
          margin-top: 5px;
          position: relative;

          .user-avatar {
            cursor: pointer;
            width: 40px;
            height: 40px;
            border-radius: 10px;
          }

          .el-icon-caret-bottom {
            cursor: pointer;
            position: absolute;
            right: -20px;
            top: 25px;
            font-size: 12px;
          }
        }
      }
    }
  }
</style>
