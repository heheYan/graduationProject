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
              <span>待办</span>
              <el-button style="float: right; padding: 3px 0" type="text" @click="$router.push({name: 'dealinstance'})">更多</el-button>
            </div>
            <ul v-for="item in dataList" :key="item.id" class="text item">
              <li @click="openUrl(item.handleUrl)">{{ item.title }}<span style="float: right">{{ item.startDate }}</span></li>
            </ul>
          </el-card>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import echarts from 'echarts'

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
        animalData: []
      }
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
          url: this.$http.adornUrl('/animal/dealinstance/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize
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
      // 统计数据
      getAnalysisByType () {
        this.$http({
          url: this.$http.adornUrl('/analysis/analysisByType'),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.xLabel = this.getValArr(data.data, 'typeName')
            this.yLabel = this.getValArr(data.data, 'cnt')
          }
        }).then(() => {
          this.initChartLine()
        })
        this.$http({
          url: this.$http.adornUrl('/analysis/animalAdoptionAnalysis'),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.animalData = data.data
          }
        }).then(() => {
          this.initAnimalChart()
        })
        this.$http({
          url: this.$http.adornUrl('/analysis/userSituation'),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.userData = data.data
          }
        }).then(() => {
          this.initRoseChart()
        })
      },
      // 折线图
      initChartLine () {
        let chartOption = {
          title: {
            text: '按种类统计动物情况',
            x: 'center'
          },
          toolbox: {
            show: true,
            feature: {
              mark: { show: true },
              dataView: { show: true, readOnly: false },
              saveAsImage: { show: true }
            }
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: { // 坐标轴指示器，坐标轴触发有效
              type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
            },
            textStyle: {
              fontSize: 16
            },
            padding: [5, 10],
            formatter: '{a1}<br />{b1} : {c1}个'
          },
          grid: {
            left: '5%',
            right: '5%',
            top: '18%',
            bottom: '5%',
            containLabel: true
          },
          xAxis: {
            type: 'category',
            axisLabel: {
              interval: 0,
              margin: 10,
              textStyle: {
                fontSize: '14',
                color: 'rgb(116, 221, 255)'
              }
            },
            splitLine: {
              show: false
            },
            axisTick: {
              show: false
            },
            axisLine: {
              show: false
            },
            data: this.xLabel
          },
          yAxis: {
            type: 'value',
            name: '(个)',
            nameTextStyle: {
              fontSize: 14,
              color: 'rgba(116, 221, 255, 0.502)'
            },
            axisLabel: {
              margin: 0,
              textStyle: {
                fontSize: '14',
                color: 'rgb(116, 221, 255)'
              },
              verticalAlign: 'top',
              lineHeight: 20
            },
            splitLine: {
              lineStyle: {
                color: 'rgba(93, 196, 255, .3)'
              }
            },
            axisTick: {
              show: false
            },
            axisLine: {
              show: false
            }
          },
          series: [{
            name: '点',
            type: 'pictorialBar',
            symbol: 'image://data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAB0AAAAdCAYAAABWk2cPAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyNpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNi1jMTQ4IDc5LjE2NDAzNiwgMjAxOS8wOC8xMy0wMTowNjo1NyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIDIxLjAgKFdpbmRvd3MpIiB4bXBNTTpJbnN0YW5jZUlEPSJ4bXAuaWlkOkRCQjhEODNDMDIzQjExRUI4MEZBQUIwRUYxMUI1RDQ2IiB4bXBNTTpEb2N1bWVudElEPSJ4bXAuZGlkOkRCQjhEODNEMDIzQjExRUI4MEZBQUIwRUYxMUI1RDQ2Ij4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6REJCOEQ4M0EwMjNCMTFFQjgwRkFBQjBFRjExQjVENDYiIHN0UmVmOmRvY3VtZW50SUQ9InhtcC5kaWQ6REJCOEQ4M0IwMjNCMTFFQjgwRkFBQjBFRjExQjVENDYiLz4gPC9yZGY6RGVzY3JpcHRpb24+IDwvcmRmOlJERj4gPC94OnhtcG1ldGE+IDw/eHBhY2tldCBlbmQ9InIiPz5K/kJ6AAAGPElEQVR42pRX2W9U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',
            barGap: '-100%',
            symbolPosition: 'end',
            symbolSize: 30,
            symbolOffset: [0, -15],
            data: this.yLabel
          }, {
            name: '按种类统计动物情况',
            type: 'pictorialBar',
            smooth: false,
            legendHoverLink: false,
            symbol: 'path://M0,10 L10,10 C5.5,10 5.5,5 5,0 C4.5,5 4.5,10 0,10 z',
            barCategoryGap: '-30%',
            label: {
              show: true,
              position: 'top',
              distance: 15,
              fontSize: '16',
              fontFamily: 'DIN',
              color: 'rgb(0, 246, 255)',
              formatter: function (params) {
                return params.value
              }
            },
            itemStyle: {
              normal: {
                color: {
                  type: 'linear',
                  x: 0,
                  y: 0,
                  x2: 0,
                  y2: 1,
                  colorStops: [{
                    offset: 0,
                    color: ' rgba(0,245,146,0.5)' //  0%  处的颜色
                  },
                  {
                    offset: 1,
                    color: ' rgba(62,203,255,0.1)' //  100%  处的颜色
                  }
                  ],
                  global: false //  缺省为  false
                }
              },
              emphasis: {
                color: 'rgba(0, 245, 146, .5)'
              }
            },
            data: this.yLabel
          }]
        }
        this.chartLine = echarts.init(document.getElementById('J_chartLineBox'))
        this.chartLine.setOption(chartOption)
        window.addEventListener('resize', () => {
          this.chartLine.resize()
        })
      },
      getValArr: function (arr, key) {
        let val = []
        let len = arr.length
        for (let i = 0; i < len; i++) {
          val.push(arr[i][key])
        }
        return val
      },
      // 用户分析图
      initRoseChart () {
        let option = {
          title: {
            text: '系统账户情况分析',
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
          color: ['#1869A0', '#e98d59', '#5A5476', '#FFC851', '#45C2E0', '#FF9393'],
          legend: {
            orient: 'vertical',
            left: 'left'
          },
          series: [
            {
              name: '用户分析',
              type: 'pie',
              radius: '50%',
              data: this.userData,
              emphasis: {
                itemStyle: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
              }
            }
          ]
        }
        this.chartRose = echarts.init(document.getElementById('chartRoseBox'))
        this.chartRose.setOption(option)
        window.addEventListener('resize', () => {
          this.chartRose.resize()
        })
      },
      // 动物领养情况分析图
      initAnimalChart () {
        let option = {
          title: {
            text: '动物领养情况分析',
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
              name: '动物领养情况分析',
              type: 'pie',
              radius: '50%',
              data: this.animalData,
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
