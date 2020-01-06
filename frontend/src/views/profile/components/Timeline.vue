<template>
  <div class="app-container">
    <el-timeline :reverse="false">
      <el-timeline-item v-for="(item,index) of timeline" :key="index" :timestamp="item.createTime" placement="top">
        <el-card>
          <h4>{{ item.description }}</h4>
          <p>本周共使用了 {{ item.count }} 次</p>
        </el-card>
      </el-timeline-item>
    </el-timeline>
  </div>
</template>

<script>
import { getOwnLog } from '@/api/profile'
export default {
  name: "Timeline",
  data() {
    return {
      timeline: null
    }
  },
  created() {
    this.getOwnLog()
  },
  methods: {
    getOwnLog() {
      getOwnLog(3).then(response => {
        this.timeline = response.data
      })
    },
    load () {
      console.log("hahah")
    }
  }
}
</script>

<style scoped>

</style>
