<template>
  <div class="dashboard-editor-container">
    <github-corner class="github-corner" />
    <panel-group @handleSetLineChartData="handleSetLineChartData" />
    <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <line-chart />
    </el-row>
  </div>
</template>

<script>
import GithubCorner from '@/components/GithubCorner'
import PanelGroup from './components/PanelGroup'
import LineChart from './components/LineChart'
import { count } from '@/api/visits'
import { mapGetters } from 'vuex'

/**
 * 记录访问，只有页面刷新或者第一次加载才会记录
 */
count().then(res => {
})

export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters([
      'name',
      'roles'
    ])
  },
  components: {
    GithubCorner,
    PanelGroup,
    LineChart
  },
  // data() {
  //   return {
  //     lineChartData: lineChartData.newVisitis
  //   }
  // },
  // methods: {
  //   handleSetLineChartData(type) {
  //     this.lineChartData = lineChartData[type]
  //   }
  // }
}
</script>

<style lang="scss" scoped>
  .dashboard-editor-container {
    padding: 32px;
    background-color: rgb(240, 242, 245);
    position: relative;

    .github-corner {
      position: absolute;
      top: 0px;
      border: 0;
      right: 0;
    }

    .chart-wrapper {
      background: #fff;
      padding: 16px 16px 0;
      margin-bottom: 32px;
    }
  }

  @media (max-width:1024px) {
    .chart-wrapper {
      padding: 8px;
    }
  }
</style>

