<template>
  <div class="components-container">
    <pan-thumb :image="image" />
    <el-button type="primary" icon="upload" style="position: absolute;bottom: 15px;margin-left: 40px;" @click="toggleShow">
      上传头像
    </el-button>

    <image-cropper
               field="multipartFile"
               @crop-success="cropSuccess"
               @crop-upload-success="cropUploadSuccess"
               @crop-upload-fail="cropUploadFail"
               v-model="show"
               :width="300"
               :height="300"
               :url=url
               :params="params"
               :headers="headers"
               img-format="png" />

  </div>
</template>

<script>
    import PanThumb from '@/components/PanThumb'
    import ImageCropper from 'vue-image-crop-upload';
    import { getToken } from '../../utils/auth'
    import { updateAvatar } from '@/api/user'
    export default {
        name: "avatar",
        components: { ImageCropper, PanThumb },
        data() {
            return {
                url: process.env.VUE_APP_BASE_API + 'upload',
                show: false,
                image: this.$store.getters.avatar,
                params: {
                    access_token: getToken()
                },
                headers: {
                    smail: '*_~'
                }
            }
        },
        methods: {
            toggleShow() {
                this.show = !this.show;
            },
            cropSuccess(image, field){
                console.log('-------- crop success --------');
                this.image = image;
            },
            cropUploadSuccess(jsonData, field){
                updateAvatar({
                    username: this.$store.getters.name,
                    path: jsonData.data.path
                }).then(response => {
                    this.$message({
                        message: response.message,
                        type: 'success'
                    })
                    this.$store.dispatch('user/setAvatar', jsonData.data.path)
                }).catch(() => {
                    this.formLoading = false
                })
                console.log('field: ' + field);
            },
            cropUploadFail(status, field){
                this.$message({
                    message: response.message,
                    type: 'warning'
                })
            }
        }
    }
</script>

<style scoped>

</style>
