<template>
  <div class="dashboard-container">
<!--    <div class="dashboard-text">name: {{ name }}</div>-->
    <panel-group @handleSetLineChartData="handleSetLineChartData" />
    <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <line-chart :chart-data="lineChartData" />
    </el-row>
<!--    <countTo class="example" :prefix='prefix' :suffix="suffix" :startVal='startVal' :endVal='endVal' :duration='duration'></countTo>-->
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import countTo from 'vue-count-to';
import { countLog } from '@/api/system'
import PanelGroup from './components/PanelGroup'
import LineChart from './components/LineChart'

const lineChartData = {
    newVisitis: {
        expectedData: [100, 120, 161, 134, 105, 160, 165],
        actualData: [120, 82, 91, 154, 162, 140, 145]
    },
    messages: {
        expectedData: [200, 192, 120, 144, 160, 130, 140],
        actualData: [180, 160, 151, 106, 145, 150, 130]
    },
    purchases: {
        expectedData: [80, 100, 121, 104, 105, 90, 100],
        actualData: [120, 90, 100, 138, 142, 130, 130]
    },
    shoppings: {
        expectedData: [130, 140, 141, 142, 145, 150, 160],
        actualData: [120, 82, 91, 154, 162, 140, 130]
    }
}

export default {
    name: 'Dashboard',
    components : {
        countTo,
        PanelGroup,
        LineChart
    },
    computed: {
        ...mapGetters([
            'name'
        ])
    },
    data() {
        return {
            lineChartData: lineChartData.newVisitis
        }
    },
    mounted() {
    },
    methods : {
        handleSetLineChartData(type) {
            this.lineChartData = lineChartData[type]
        }
    }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
.example {
  font-size: 50px;
  color: #F6416C;
  display: block;
  margin: 10px 0;
  text-align: center;
  font-size: 80px;
  font-weight: 500;
}
</style>
