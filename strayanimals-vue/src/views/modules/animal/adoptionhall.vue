<template>
  <div>
    <el-alert v-if="!adoptStat"
      title="您还不具备领养资格，请先至“我的领养资格”模块提交资料，等待审核通过后才可在本页面进行领养申请。"
      type="warning"
      effect="dark">
    </el-alert>
    <el-row>
      <el-col :span="4" v-for="(o, index) in dataList" :key="o.id" :offset="index%5===0  ? 0 : 1"
              style="margin-top: 10px;">
        <el-card :body-style="{ padding: '0px' }" shadow="hover">
          <viewer>
            <img :src="o.imgurl" class="image" style="cursor:pointer">
          </viewer>
          <div style="padding: 20px;">
            <span>{{ o.name }}</span>
            <div class="bottom clearfix">
              <span class="time">{{ o.typename }}</span>
              <el-button :disabled="!adoptStat" type="text" @click="dealHandle(o.id)" class="button">申请领养</el-button>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[1,3,30, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <!-- 弹窗, 申请表单 -->
    <apply v-if="detailVisible" ref="applyDetail" @refreshDataList="getDataList"></apply>
  </div>
</template>

<script>
  import Apply from './adoptionhall-apply'
  export default {
    data () {
      return {
        dataForm: {
          key: ''
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 30,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        detailVisible: false,
        currentDate: new Date(),
        adoptStat: false
      }
    },
    components: {
      Apply
    },
    activated () {
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        // 判断账号是否具有领养权限
        this.$http({
          url: this.$http.adornUrl('/pualifications/hasAdoptAuthority'),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          if (data && data.data) {
            this.adoptStat = data.data
          }
        })
        this.$http({
          url: this.$http.adornUrl('/animal/animalinfo/canAdoptList'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'key': this.dataForm.key,
            'id': this.$route.query.id
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataList = data.page.list
            this.totalPage = data.page.totalCount
          } else {
            this.dataList = []
            this.totalPage = 0
          }
          this.dataListLoading = false
        })
      },
      // 每页数
      sizeChangeHandle (val) {
        this.pageSize = val
        this.pageIndex = 1
        this.getDataList()
      },
      // 当前页
      currentChangeHandle (val) {
        this.pageIndex = val
        this.getDataList()
      },
      // 多选
      selectionChangeHandle (val) {
        this.dataListSelections = val
      },
      // 申请
      dealHandle (id) {
        this.detailVisible = true
        this.$nextTick(() => {
          this.$refs.applyDetail.init(id)
        })
      }
    }
  }
</script>

<style>
.time {
  font-size: 13px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  padding: 0;
  float: right;
}

.image {
  width: 100%;
  display: block;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}
</style>
