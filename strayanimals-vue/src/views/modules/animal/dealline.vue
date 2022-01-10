<template>
  <el-dialog
    title="流程信息"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <div class="block">
      <el-timeline v-for="item in dataList" :key="item.id">
        <el-timeline-item :timestamp="item.opDate" placement="top">
          <el-card>
            <h4>{{item.workitem}}</h4>
            <p v-if="item.opName">{{ item.opName }} 处理于 {{item.opDate}}</p>
          </el-card>
        </el-timeline-item>
      </el-timeline>
    </div>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataList: []
      }
    },
    activated () {
    },
    methods: {
      init (id) {
        this.$http({
          url: this.$http.adornUrl('/animal/dealinstance/list/' + id),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          console.log(data)
          this.dataList = data.dataList
          console.log(this.dataList)
          this.visible = true
        })
      }
    }
  }
</script>
