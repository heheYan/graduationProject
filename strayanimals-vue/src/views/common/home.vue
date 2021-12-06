<template>
  <div class="mod-home">
    <el-row :gutter="20">
      <el-col :span="16"><div class="grid-content bg-purple"></div></el-col>
      <el-col :span="8"><div class="grid-content bg-purple">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>待办</span>
            <el-button style="float: right; padding: 3px 0" type="text">更多</el-button>
          </div>
          <ul v-for="item in dataList" :key="item.id" class="text item">
            <li @click="openUrl(item.handleUrl)">{{ item.title }}<span style="float: right">{{ item.startDate }}</span></li>
          </ul>
        </el-card>

      </div></el-col>
    </el-row>
  </div>
</template>

<script>
  export default {
    data () {
      return {
        dataList: [],
        pageIndex: 1,
        pageSize: 15,
        dataListLoading: false
      }
    },
    activated () {
      this.getDataList()
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
      },
      openUrl (url) {
        this.$router.push(url)
      }
    }
  }
</script>

<style>
  .mod-home {
    line-height: 1.5;
  }
</style>

