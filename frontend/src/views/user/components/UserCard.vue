<template>
  <el-card style="margin-bottom:20px;">
    <div slot="header" class="clearfix">
      <span>关于我</span>
    </div>
    <image-cropper
      v-model="show"
      field="multipartFile"
      :width="300"
      :height="300"
      :url="imageUrl"
      :params="params"
      :headers="headers"
      img-format="png"
      @crop-success="cropSuccess"
      @crop-upload-success="cropUploadSuccess"
      @crop-upload-fail="cropUploadFail"
    />
    <el-dialog :visible.sync="dialog" :close-on-click-modal="false" :before-close="cancel" :title="title" append-to-body width="500px" @close="cancel">
      <el-form ref="form" :model="form" :rules="rules" size="small" label-width="88px">
        <el-form-item label="旧密码" prop="oldPassword">
          <el-input v-model="form.oldPassword" type="password" auto-complete="on" style="width: 370px;"/>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input v-model="form.newPassword" type="password" auto-complete="on" style="width: 370px;"/>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="form.confirmPassword" type="password" auto-complete="on" style="width: 370px;"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="text" @click="cancel">取消</el-button>
        <el-button :loading="loading" type="primary" @click="doSubmit">确认</el-button>
      </div>
    </el-dialog>
    <div class="user-profile">
      <div class="box-center">
        <pan-thumb :image="user.avatar" :height="'100px'" :width="'100px'" :hoverable="false">
          <div>Hello</div>
        </pan-thumb>
      </div>
      <div class="box-center">
        <div class="user-name text-center">{{ user.nickname }}</div>
<!--        <div class="user-role text-center text-muted">{{ user.role | uppercaseFirst }}</div>-->
      </div>
    </div>

    <div class="user-bio">
      <div class="user-education user-bio-section">
        <div class="user-bio-section-header"><i class="el-icon-time"/><span>上次登录时间</span></div>
        <div class="user-bio-section-body">
          <div class="text-muted">
            {{user.lastLoginTime}}
          </div>
        </div>
      </div>

      <div class="user-skills user-bio-section">
        <div class="user-bio-section-header"><svg-icon icon-class="skill" /><span>其他</span></div>
        <div class="user-bio-section-body">
          <ul class="user-info">
            <li style="border-top: 0;">用户名称 <div class="user-right">{{ user.name }}</div></li>
            <li>用户邮箱 <div class="user-right">{{ user.email }}</div></li>
            <li>注册时间 <div class="user-right">{{ user.registerTime }}</div></li>
            <li>状态 <div class="user-right" style="margin-top: -5px"><el-tag :type="'success'">{{user.status}}</el-tag></div></li>
            <li>
              修改头像
              <div class="user-right">
                <a @click="toggleShow">点击上传</a>
              </div>
            </li>
            <li>
              安全设置
              <div class="user-right">
                <a @click="dialog=true">修改密码</a>
              </div>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </el-card>
</template>

<script>
import ImageCropper from 'vue-image-crop-upload'
import PanThumb from '@/components/PanThumb'
import { getToken } from '@/utils/auth'
import { updateAvatar, updatePassword } from '@/api/user'
import { timeToNow } from  '@/utils/index'
export default {
  components: { PanThumb, ImageCropper },
  props: {
    user: {
      type: Object,
      default: () => {
        return {
          name: '',
          nickname:'',
          email:'',
          url:'',
          avatar:'',
          lastLoginTime:'',
          registerTime:'',
          status:'',
        }
      }
    }
  },
  data() {
    const confirmPassword = (rule, value, callback) => {
      if (value) {
        if (this.form.newPassword !== value) {
          callback(new Error('两次输入的密码不一致'))
        } else {
          callback()
        }
      } else {
        callback(new Error('请再次输入密码'))
      }
    }
    return {
      loading: false, dialog: false, title: '修改密码',
      form: { oldPassword: '', newPassword: '', confirmPassword: '' },
      imageUrl: process.env.VUE_APP_BASE_API + '/upload',
      show: false,
      params: {
        access_token: getToken()
      },
      headers: {
        smail: '*_~'
      },
      image: this.$store.getters.avatar,
      rules: {
        oldPassword: [
          { required: true, message: '请输入旧密码', trigger: 'blur' }
        ],
        newPassword: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
          { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, validator: confirmPassword, trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    cancel() {
      this.resetForm()
    },
    doSubmit() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.loading = true
          updatePassword(this.form).then(response => {
            this.resetForm()
            this.$notify({
              title: response.message,
              type: 'success',
              duration: 1500
            })
            setTimeout(() => {
              this.$store.dispatch('user/logout').then(() => {
                location.reload() // 为了重新实例化vue-router对象 避免bug
              })
            }, 1500)
          }).catch(err => {
            this.loading = false
            console.log(err.response.data.message)
          })
        } else {
          return false
        }
      })
    },
    resetForm() {
      this.dialog = false
      this.$refs['form'].resetFields()
      this.form = { oldPassword: '', newPassword: '', confirmPassword: '' }
    },
    toggleShow() {
      this.show = !this.show
    },
    /**
     *
     * @param image
     * @param field
     */
    cropSuccess(image, field) {
      console.log('-------- crop success --------')
      this.image = image
    },
    /**
     * 上传成功
     * @param jsonData 服务器返回数据，已进行 JSON 转码
     * @param field
     */
    cropUploadSuccess(jsonData, field) {
      console.log('-------- upload success --------')
      // 更新头像
      updateAvatar({
        username: this.$store.getters.name,
        path: jsonData.data.path
      }).then(response => {
        this.$message({
          message: response.message,
          type: 'success'
        })
        // 更新 vuex 中的头像
        this.$store.dispatch('user/setAvatar', jsonData.data.path)
        this.user.avatar = jsonData.data.path
      }).catch(() => {
      })
    },
    /**
     * 上传失败
     * @param status 服务器返回的失败状态码
     * @param field
     */
    cropUploadFail(status, field) {
      console.log('-------- upload fail --------')
    }
  }
}
</script>

<style lang="scss" scoped>
 .box-center {
   margin: 0 auto;
   display: table;
 }

 .text-muted {
   color: #777;
 }

 .user-profile {
   .user-name {
     font-weight: bold;
   }

   .box-center {
     padding-top: 10px;
   }

   .user-role {
     padding-top: 10px;
     font-weight: 400;
     font-size: 14px;
   }

   .box-social {
     padding-top: 30px;

     .el-table {
       border-top: 1px solid #dfe6ec;
     }
   }

   .user-follow {
     padding-top: 20px;
   }
 }

 .user-bio {
   margin-top: 20px;
   color: #606266;

   span {
     padding-left: 4px;
   }

   .user-bio-section {
     font-size: 14px;
     padding: 15px 0;

     .user-bio-section-header {
       border-bottom: 1px solid #dfe6ec;
       padding-bottom: 10px;
       margin-bottom: 10px;
       font-weight: bold;
     }
   }
 }
 .user-info {
   padding-left: 0px;
   list-style: none;
   li{
     border-bottom: 1px solid #F0F3F4;
     border-top: 1px solid #F0F3F4;
     padding: 11px 0px;
     font-size: 13px;
   }
   .user-right {
     float: right;

     a{
       color: #317EF3;
     }
   }
 }
</style>
