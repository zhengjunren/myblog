<template>
  <div class="app-container">
    <div class="filter-container">
      <el-button-group>
        <el-button :disabled="!showButton" v-waves class="filter-item" type="primary" @click="initWebSocket"><svg-icon icon-class="connect" />&nbsp;连接</el-button>
        <el-button :disabled="showButton" v-waves class="filter-item" type="danger" @click="destroyConnect"><svg-icon icon-class="disconnect" />&nbsp;&nbsp;断开</el-button>
      </el-button-group>
    </div>
    <el-row :gutter="20">
      <el-col :span="8" :xs="24">
        <el-card style="margin-bottom:30px;">
          <div slot="header">
            <span>CPU信息</span>
          </div>
          <el-table size="small" border :data="server.cpu" style="width: 100%">
            <el-table-column prop="key" label="属性">
            </el-table-column>
            <el-table-column prop="value" label="值">
            </el-table-column>
          </el-table>
        </el-card>
        <el-card style="margin-bottom:30px;">
          <div slot="header">
            <span>内存信息</span>
          </div>
          <el-table size="small" border :data="server.mem" style="width: 100%">
            <el-table-column prop="key" label="属性">
            </el-table-column>
            <el-table-column prop="value" label="值">
            </el-table-column>
          </el-table>
        </el-card>
        <el-card style="margin-bottom:30px;">
          <div slot="header">
            <span>服务器信息</span>
          </div>
          <el-table size="small" border :data="server.sys" style="width: 100%">
            <el-table-column prop="key" label="属性">
            </el-table-column>
            <el-table-column prop="value" label="值">
            </el-table-column>
          </el-table>
        </el-card>
        <el-card>
          <div slot="header">
            <span>Java虚拟机信息</span>
          </div>
          <el-table size="small" border :data="server.jvm" style="width: 100%">
            <el-table-column prop="key" label="属性">
            </el-table-column>
            <el-table-column prop="value" label="值">
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
      <el-col :span="16" :xs="24">
        <el-card>
          <div slot="header">
            <span>磁盘状态</span>
          </div>
          <div class="sysFile" v-for="(item,index) in server.sysFile" :key="index">
            <el-table size="small" border :data="item" style="width: 100%">
              <el-table-column prop="key" label="属性">
              </el-table-column>
              <el-table-column prop="value" label="值">
              </el-table-column>
            </el-table>
          </div>
        </el-card>
      </el-col>

    </el-row>
    <el-row>

    </el-row>
  </div>
</template>

<script>
import SockJS from  'sockjs-client';
import  Stomp from 'stompjs';
import { getToken } from '@/utils/auth'
import waves from '@/directive/waves'
const wsTopic = "/topic/server";
const wsHost = process.env.VUE_APP_BASE_API + "/notification"
export default {
  directives: { waves },
  data(){
    return {
      showButton: false,
      timer:'',
      isConnected: false,
      stompClient: {},
      socket: {},
      server: {
        cpu: [],
        mem: [],
        jvm: [],
        sys: [],
        sysFile: []
      }
    }
  },
  methods: {
    initWebSocket() {
      this.connection();
      let that= this;
      // 断开重连机制,尝试发送消息,捕获异常发生时重连
      this.timer = setInterval(() => {
        try {
          that.stompClient.send("test");
        } catch (err) {
          that.connection();
        }
      }, 5000);
    },
    connection() {
      // 建立连接对象 http://119.3.222.119
      let socket = new SockJS(wsHost);
      // 获取STOMP子协议的客户端对象
      this.stompClient = Stomp.over(socket);
      // 定义客户端的认证信息,按需求配置
      let headers = {
        Authorization: 'Bearer ' + getToken()
      }
      // 向服务器发起websocket连接
      this.stompClient.connect(headers,() => {
        this.showButton = false
        this.isConnected = true
        this.$message({
          message: '连接成功',
          type: 'success'
        });
        this.stompClient.subscribe(wsTopic, (msg) => { // 订阅服务端提供的某个topic
          this.server = JSON.parse(msg.body);
        },headers);
      }, (err) => {
        // 连接发生错误时的处理函数
      });
    },
    disconnect() {
      if (this.isConnected){
        if (this.stompClient) {
          this.stompClient.disconnect();
          this.showButton = true
          this.isConnected = false
          this.$message('连接断开');
        }
      }
    },
    destroyConnect() {
      this.disconnect();
      clearInterval(this.timer);
    }
  },
  mounted(){
    this.initWebSocket();
  },
  beforeDestroy() {
    // 页面离开时断开连接,清除定时器
    this.destroyConnect()
  }

}
</script>

<style scoped>
  .line{
    text-align: center;
  }
</style>

