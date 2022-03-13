<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="报修人" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-select v-model="dataForm.status" placeholder="请选择工单状态" @change="getDataList">
          <el-option
            v-for="item in statusData"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button v-if="isAuth('realestate:repair:save')" type="primary" @click="addOrUpdateHandle()">我要报修</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <el-table-column
        prop="userName"
        header-align="center"
        align="center"
        label="业主名称">
      </el-table-column>
      <el-table-column
        prop="roomNo"
        header-align="center"
        align="center"
        label="房间号">
      </el-table-column>
      <el-table-column
        prop="reason"
        header-align="center"
        align="center"
        width="340"
        :show-overflow-tooltip="true"
        label="原因">
      </el-table-column>
      <el-table-column
        prop="applyTime"
        header-align="center"
        align="center"
        label="报修时间">
      </el-table-column>
      <el-table-column
        prop="status"
        header-align="center"
        align="center"
        label="处理状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === 0" size="small" type="danger">待处理</el-tag>
          <el-tag v-else-if="scope.row.status === 1" size="small" type="warning">已分配</el-tag>
          <el-tag v-else-if="scope.row.status === 2" size="small" type="success">已处理</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="facilityName"
        header-align="center"
        align="center"
        label="公共设施名称">
      </el-table-column>
      <el-table-column
        prop="repairName"
        header-align="center"
        align="center"
        label="维修人名称">
      </el-table-column>
      <el-table-column
        prop="remark"
        header-align="center"
        align="center"
        :show-overflow-tooltip="true"
        label="备注">
      </el-table-column>
      <el-table-column
        prop="repairTime"
        header-align="center"
        align="center"
        label="处理时间">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="100"
        label="操作">
        <template slot-scope="scope">
          <el-button v-if="isAuth('realestate:repair:update') && scope.row.status === 0" type="text" size="small" @click="dealHandle(scope.row.id)">分配维修人员</el-button>
          <el-button v-if="isAuth('realestate:repair:update') && scope.row.status === 1" type="text" size="small" @click="feedbackHandle(scope.row.id)">维修反馈</el-button>
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
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
    <!-- 分配处理人员   -->
    <deal v-if="dealVisible" ref="repairDeal" @refreshDataList="getDataList"></deal>
    <!-- 维修人员反馈   -->
    <feedback v-if="feedbackVisible" ref="repairFeedback" @refreshDataList="getDataList"></feedback>
  </div>
</template>

<script>
  import AddOrUpdate from './repair-add-or-update'
  import Deal from './repair-deal'
  import Feedback from './repair-feedback'
  export default {
    data () {
      return {
        dataForm: {
          key: '',
          status: -1
        },
        statusData: [
          {
            'value': -1,
            'label': '全部'
          },
          {
            'value': 0,
            'label': '待分配'
          },
          {
            'value': 1,
            'label': '已分配'
          },
          {
            'value': 2,
            'label': '已处理'
          }
        ],
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false,
        feedbackVisible: false,
        dealVisible: false
      }
    },
    components: {
      AddOrUpdate,
      Deal,
      Feedback
    },
    activated () {
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/realestate/repair/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'key': this.dataForm.key,
            'status': this.dataForm.status
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
      // 新增 / 修改
      addOrUpdateHandle (id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      // 分配人员
      dealHandle (id) {
        this.dealVisible = true
        this.$nextTick(() => {
          this.$refs.repairDeal.init(id)
        })
      },
      // 维修人员反馈处理
      feedbackHandle (id) {
        this.feedbackVisible = true
        this.$nextTick(() => {
          this.$refs.repairFeedback.init(id)
        })
      },
      // 删除
      deleteHandle (id) {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })
        this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/realestate/repair/delete'),
            method: 'post',
            data: this.$http.adornData(ids, false)
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        })
      }
    }
  }
</script>
