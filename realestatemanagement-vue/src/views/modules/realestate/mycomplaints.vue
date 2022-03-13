<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="投诉关键词" clearable></el-input>
      </el-form-item>

      <el-form-item>
        <el-select v-model="dataForm.status" placeholder="请选择处理状态" @change="getDataList">
          <el-option
            v-for="item in statusData"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-select v-model="dataForm.type" placeholder="请选择投诉类型" @change="getDataList">
          <el-option
            v-for="item in typeData"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button type="primary" @click="addOrUpdateHandle()">新增</el-button>
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
        label="投诉人">
      </el-table-column>
      <el-table-column
        prop="type"
        header-align="center"
        align="center"
        label="类型">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.type === 1" size="small" type="success">投诉</el-tag>
          <el-tag v-else-if="scope.row.type === 2" size="small" type="danger">举报</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="complaintDate"
        header-align="center"
        align="center"
        label="投诉时间">
      </el-table-column>
      <el-table-column
        prop="content"
        header-align="center"
        align="center"
        :show-overflow-tooltip="true"
        label="投诉内容">
      </el-table-column>
      <el-table-column
        prop="status"
        header-align="center"
        align="center"
        label="当前状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === 0" size="small" type="danger">未处理</el-tag>
          <el-tag v-else-if="scope.row.status === 1" size="small" type="success">已处理</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="handleName"
        header-align="center"
        align="center"
        label="处理人">
      </el-table-column>
      <el-table-column
        prop="handleDate"
        header-align="center"
        align="center"
        label="处理时间">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button v-if="scope.row.status === 0" type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
          <el-button v-if="scope.row.status === 0" type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
          <el-button v-if="scope.row.status === 1" type="text" size="small" @click="dealHandle(scope.row.id)">查看反馈意见</el-button>
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
    <!-- 弹窗, 处理意见 -->
    <handle v-if="handleVisible" ref="handle" @refreshDataList="getDataList"></handle>
  </div>
</template>

<script>
  import AddOrUpdate from './complaints-add-or-update'
  import Handle from './complaints-handle'
  export default {
    data () {
      return {
        dataForm: {
          key: '',
          status: -1,
          type: -1
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false,
        handleVisible: false,
        statusData: [
          {
            'label': '全部',
            'value': -1
          }, {
            'label': '未处理',
            'value': 0
          }, {
            'label': '已处理',
            'value': 1
          }],
        typeData: [
          {
            'label': '全部',
            'value': -1
          }, {
            'label': '投诉',
            'value': 1
          }, {
            'label': '举报',
            'value': 2
          }]
      }
    },
    components: {
      AddOrUpdate,
      Handle
    },
    activated () {
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/realestate/complaints/mylist'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'key': this.dataForm.key,
            'status': this.dataForm.status,
            'type': this.dataForm.type
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
      // 新增 / 修改
      dealHandle (id) {
        this.handleVisible = true
        this.$nextTick(() => {
          this.$refs.handle.init(id)
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
            url: this.$http.adornUrl('/realestate/complaints/delete'),
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
