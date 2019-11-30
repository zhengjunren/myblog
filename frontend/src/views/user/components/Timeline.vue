<template>
  <div class="block">
    <el-timeline :reverse="false">
      <el-timeline-item v-for="(item,index) of timeline" :key="index" :timestamp="item.updateTime" placement="top">
        <el-card>
          <router-link :to="'/article/edit/'+item.id+'/'+ item.nickname" class="link-type">
            <h4>{{ item.title }}</h4>
          </router-link>
          <p>{{ item.summary }}</p>
        </el-card>
      </el-timeline-item>
    </el-timeline>
  </div>
</template>

<script>
  import { getLatestArticle } from "@/api/article";
export default {
  props: {
    username: {
      required: true,
      type: String
    },
  },
  created() {
    this.fetchLatestArticle()
  },
  methods: {
    fetchLatestArticle() {
      getLatestArticle(3).then(response => {
        this.timeline = response.data
      })
    }
  },
  data() {
    return {
      timeline: null
    }
  }
}
</script>
