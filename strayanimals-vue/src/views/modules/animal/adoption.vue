<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="参数名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      style="width: 100%;">
      <el-table-column
        prop="animalInfo.name"
        header-align="center"
        align="center"
        label="动物名称"
        width="100">
      </el-table-column>
      <el-table-column
        prop="animalInfo.description"
        header-align="center"
        align="center"
        label="动物简介"
        :show-overflow-tooltip="true"
        width="180">
      </el-table-column>
      <el-table-column
        header-align="center"
        align="center"
        width="200"
        label="图片">
        <template slot-scope="scope">
          <viewer>
            <img
              :src="scope.row.animalInfo.imgurl"
              style="width: 120px;height: 80px; border:none;cursor:pointer">
          </viewer>
        </template>
      </el-table-column>
      <el-table-column
        prop="animalInfo.typename"
        header-align="center"
        align="center"
        label="种类"
        width="100">
      </el-table-column>
      <el-table-column
        prop="userName"
        header-align="center"
        align="center"
        label="领养人"
        width="130">
      </el-table-column>
      <el-table-column
        prop="reason"
        header-align="center"
        align="center"
        label="申请原因">
      </el-table-column>
      <el-table-column
        prop="applyDate"
        header-align="center"
        align="center"
        label="申请时间"
        width="180">
      </el-table-column>
      <el-table-column
        prop="status"
        header-align="center"
        align="center"
        label="审核状态"
        width="150"
        :formatter="adoptStatusFormatter">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="170"
        label="操作">
        <template slot-scope="scope">
          <el-button v-if="!isDeal" type="text" size="small" @click="dealHandle(scope.row.id,scope.row.animalInfo.id)">处理</el-button>
          <el-button type="text" size="small" @click="dealLineHandle(scope.row.animalInfo.id)">查看流程</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <dealline v-if="deallineVisible" ref="dealline" @refreshDataList="getDataList"></dealline>
    <dealform v-if="dealVisible" ref="dealform" @refreshDataList="getDataList"></dealform>
  </div>
</template>

<script>
  import Dealline from './dealline'
  import Dealform from './adoption-deal'
  export default {
    data () {
      return {
        dataForm: {
          key: ''
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        deallineVisible: false,
        dealVisible: false,
        fileList: [],
        isDeal: false
      }
    },
    components: {
      Dealline,
      Dealform
    },
    activated () {
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/myAdoption/listAll'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'key': this.dataForm.key
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
      adoptStatusFormatter (row, column) {
        if (row.status === 1) {
          this.isDeal = true
          return '审核通过'
        } else if (row.status === 2) {
          this.isDeal = true
          return '审核未通过'
        } else {
          return '审核中'
        }
      },
      dealLineHandle (id) {
        this.deallineVisible = true
        this.$nextTick(() => {
          this.$refs.dealline.init(id)
        })
      },
      dealHandle (id, animalId) {
        this.dealVisible = true
        this.$nextTick(() => {
          this.$refs.dealform.init(id, animalId)
        })
      }
    }
  }
</script>
