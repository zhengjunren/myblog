<template>
  <div class="app-container">
    <el-tabs value="sendmail" style="padding-left: 5px;">
      <el-tab-pane label="邮箱配置" name="first">
        <EmailConfig/>
      </el-tab-pane>
      <el-tab-pane label="发送邮件" name="sendmail">
        <EmailSend/>
      </el-tab-pane>
      <el-tab-pane label="使用说明" name="third">
        <div>
          <blockquote class="my-blockquote"> 邮件服务器配置</blockquote>
          <pre>
 # 邮件服务器的SMTP地址，可选，默认为smtp
 # 邮件服务器的SMTP端口，可选，默认465或者25
 # 发件人（必须正确，否则发送失败）
 # 用户名，默认为发件人邮箱前缀
 # 密码（注意，某些邮箱需要为SMTP服务单独设置密码，如QQ和163等等）
 # 是否开启ssl，默认开启</pre>
          <blockquote class="my-blockquote">更多帮助</blockquote>
          <pre>更多帮助请查看文档：<a style="color:#009688" href="http://hutool.mydoc.io/#text_319499" target="_black">hutool工具包</a></pre>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
  import EmailConfig from './config'
  import EmailSend from './send'
    export default {
        name: "EmailIndex",
        components: {
            EmailConfig,
            EmailSend
        }
    }
</script>

<style scoped>

</style>
