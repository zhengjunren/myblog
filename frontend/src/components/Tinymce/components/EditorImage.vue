<template>
  <div class="upload-container">
    <el-button :style="{background:color,borderColor:color}" icon="el-icon-upload" size="mini" type="primary" @click="toggleShow">
      上传
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
        img-format="png"></image-cropper>
  </div>
</template>

<script>
// import { getToken } from 'api/qiniu'
import ImageCropper from 'vue-image-crop-upload';
import { getToken } from '@/utils/auth'

export default {
  name: 'EditorSlideUpload',
  components: { ImageCropper },
  props: {
    color: {
      type: String,
      default: '#1890ff'
    }
  },
  data() {
    return {
      show: false,
      url: process.env.VUE_APP_BASE_API + 'upload',
      dialogVisible: false,
      listObj: {},
      fileList: [],
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
          this.$emit('successCBK', jsonData.data.path)
      },
      cropUploadFail(status, field){
          this.$message({
              message: response.message,
              type: 'warning'
          })
      },
    handleSubmit() {
      const arr = Object.keys(this.listObj).map(v => this.listObj[v])
      if (!this.checkAllSuccess()) {
        this.$message('Please wait for all images to be uploaded successfully. If there is a network problem, please refresh the page and upload again!')
        return
      }
      this.$emit('successCBK', arr)
      this.listObj = {}
      this.fileList = []
      this.dialogVisible = false
    }
  }
}
</script>

<style lang="scss" scoped>
.editor-slide-upload {
  margin-bottom: 20px;
  /deep/ .el-upload--picture-card {
    width: 100%;
  }
}
</style>
