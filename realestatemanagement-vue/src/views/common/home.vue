<template>
  <div class="mod-home">
    <el-row :gutter="20">
      <el-col :span="16">
        <div class="grid-content bg-purple">
          <el-card>
            <div id="J_chartLineBox" class="chart-box"></div>
          </el-card>

          <el-card>
            <el-row :gutter="20">
              <el-col :span="12">
                <div id="chartRoseBox" class="chart-box"></div>
              </el-col>
              <el-col :span="12">
                <div id="animalInfoBox" class="chart-box"></div>
              </el-col>
            </el-row>
          </el-card>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="grid-content bg-purple">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>社区公示栏</span>
              <el-button style="float: right; padding: 3px 0" type="text" @click="$router.push({name: 'realestateforum'})">更多</el-button>
            </div>
            <ul v-for="item in dataList" :key="item.id" class="text item">
              <li @click="detailHandle(item.id)">{{ item.title }}<span style="float: right">{{ item.createTime }}</span></li>
            </ul>
          </el-card>
        </div>
      </el-col>
    </el-row>
    <detail v-if="detailVisible" ref="detail" @refreshDataList="getDataList"></detail>
  </div>
</template>

<script>
  import echarts from 'echarts'
  import Detail from '../modules/realestate/realestateforum-detail'

  export default {
    data () {
      return {
        dataList: [],
        pageIndex: 1,
        pageSize: 15,
        dataListLoading: false,
        chartLine: null,
        chartRose: null,
        chartAnimal: null,
        xLabel: [],
        yLabel: [],
        userData: [],
        carSpaceData: [],
        facilityCnt: [],
        maxData: [],
        yezhuCnt: [],
        zuhuCnt: [],
        percent: [],
        detailVisible: false
      }
    },
    components: {
      Detail
    },
    activated () {
      this.getDataList()
      // 由于给echart添加了resize事件, 在组件激活时需要重新resize绘画一次, 否则出现空白bug
      if (this.chartLine) {
        this.chartLine.resize()
      }
    },
    methods: {
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/realestate/realestateforum/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': 1,
            'limit': 15,
            'stat': '1'
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataList = data.page.list
          } else {
            this.dataList = []
            this.totalPage = 0
          }
          this.dataListLoading = false
        })

        this.getAnalysisByType()
      },
      openUrl (url) {
        this.$router.push(url)
      },
      // 查看详情
      detailHandle (id) {
        this.detailVisible = true
        this.$nextTick(() => {
          this.$refs.detail.init(id)
        })
      },
      // 统计数据
      getAnalysisByType () {
        this.$http({
          url: this.$http.adornUrl('/analysis/facility'),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.facilityCnt = data.data.cntArr
            this.percent = data.data.perArr
          }
        }).then(() => {
          this.initChartLine()
        })
        this.$http({
          url: this.$http.adornUrl('/analysis/carSpaceAnalysis'),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.carSpaceData = data.data
          }
        }).then(() => {
          this.initCarSpaceChart()
        })
        this.$http({
          url: this.$http.adornUrl('/analysis/userAnalysisByBuild'),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.maxData = data.data.maxdata
            this.yezhuCnt = data.data.yezhu
            this.zuhuCnt = data.data.zuhu
          }
        }).then(() => {
          this.initRoseChart()
        })
      },
      // 公共设施分析
      initChartLine () {
        let option = {
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross',
              crossStyle: {
                color: '#999'
              }
            }
          },
          title: {
            text: '公共设施分析',
            left: 'center'
          },
          toolbox: {
            feature: {
              dataView: { show: true, readOnly: false },
              magicType: { show: true, type: ['line', 'bar'] },
              restore: { show: true },
              saveAsImage: { show: true }
            }
          },
          xAxis: [
            {
              type: 'category',
              data: ['电梯', '路灯', '健身设施', '儿童设施', '大门'],
              axisPointer: {
                type: 'shadow'
              }
            }
          ],
          yAxis: [
            {
              type: 'value',
              name: '数量',
              min: 0,
              max: 30,
              interval: 6,
              axisLabel: {
                formatter: '{value} 个'
              }
            },
            {
              type: 'value',
              name: '故障率',
              min: 0,
              max: 100,
              interval: 20,
              axisLabel: {
                formatter: '{value} %'
              }
            }
          ],
          series: [
            {
              name: '公共设施',
              type: 'bar',
              tooltip: {
                valueFormatter: function (value) {
                  return value + ' 个'
                }
              },
              data: this.facilityCnt
            },
            {
              name: '故障率',
              type: 'line',
              yAxisIndex: 1,
              tooltip: {
                valueFormatter: function (value) {
                  return value + ' %'
                }
              },
              data: this.percent
            }
          ]
        }
        this.chartLine = echarts.init(document.getElementById('J_chartLineBox'))
        this.chartLine.setOption(option)
        window.addEventListener('resize', () => {
          this.chartLine.resize()
        })
      },
      // 入住用户分析图
      initRoseChart () {
        let option = {
          title: {
            text: '入住用户分析'
          },
          legend: {
            data: ['业主', '租户']
          },
          color: ['green', 'red'],
          radar: {
            // shape: 'circle',
            indicator: this.maxData
          },
          series: [
            {
              name: '入住情况',
              type: 'radar',
              emphasis: {
                lineStyle: {
                  width: 4
                }
              },
              tooltip: {
                trigger: 'item'
              },
              data: [
                {
                  value: this.yezhuCnt,
                  name: '业主'
                },
                {
                  value: this.zuhuCnt,
                  name: '租户'
                }
              ]
            }
          ]
        }
        this.chartRose = echarts.init(document.getElementById('chartRoseBox'))
        this.chartRose.setOption(option)
        window.addEventListener('resize', () => {
          this.chartRose.resize()
        })
      },
      // 车位分析图
      initCarSpaceChart () {
        let option = {
          title: {
            text: '车位情况分析',
            left: 'center'
          },
          tooltip: {
            trigger: 'item'
          },
          toolbox: {
            show: true,
            feature: {
              mark: { show: true },
              saveAsImage: { show: true }
            }
          },
          legend: {
            orient: 'vertical',
            left: 'left'
          },
          series: [
            {
              name: '车位情况分析',
              type: 'pie',
              radius: '50%',
              data: this.carSpaceData,
              emphasis: {
                itemStyle: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgb(62,181,31)'
                }
              }
            }
          ]
        }
        this.chartAnimal = echarts.init(document.getElementById('animalInfoBox'))
        this.chartAnimal.setOption(option)
        window.addEventListener('resize', () => {
          this.chartAnimal.resize()
        })
      }
    }
  }
</script>

<style>
  .mod-home {
    line-height: 1.5;
  }

  .el-card {
    margin-bottom: 20px;
    transition: all .5s;
  }
  .chart-box {
    min-height: 360px;
  }
  .box-card {
    min-height: 825px;
  }
</style>
