<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="车位编号" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-select v-model="dataForm.status" placeholder="请选择车位状态" @change="getDataList">
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
        <el-button v-if="isAuth('realestate:basebuild:save')" type="warning" @click="quickHandle()">快速新增车位</el-button>
      </el-form-item>
    </el-form>
    <div v-for="item in dataList">
      <div :class="changeImg(item.status)" :title="changeTitle(item.status)" @click="addOrUpdateHandle(item.id)">
        <p>{{item.no}}</p>
      </div>
    </div>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
    <!-- 弹窗, 快速初始化 -->
    <carspaceInit v-if="quickVisible" ref="quickInit" @refreshDataList="getDataList"></carspaceInit>
  </div>
</template>

<script>
  import AddOrUpdate from './carspace-add-or-update'
  import CarspaceInit from './carspace-quickinit'
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
            'label': '未售'
          },
          {
            'value': 1,
            'label': '购买'
          },
          {
            'value': 2,
            'label': '出租'
          }
        ],
        dataList: [],
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false,
        quickVisible: false
      }
    },
    components: {
      AddOrUpdate,
      CarspaceInit
    },
    activated () {
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/realestate/carspace/listAll'),
          method: 'get',
          params: this.$http.adornParams({
            'key': this.dataForm.key,
            'status': this.dataForm.status
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataList = data.list
          } else {
            this.dataList = []
          }
          this.dataListLoading = false
        })
      },
      // 快速初始化
      quickHandle () {
        this.quickVisible = true
        this.$nextTick(() => {
          this.$refs.quickInit.init()
        })
      },
      // 新增 / 修改
      addOrUpdateHandle (id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      changeTitle (status) {
        let _txt = '未售'
        if (status === 1) {
          _txt = '已出售'
        } else if (status === 2) {
          _txt = '已出租'
        }
        return _txt
      },
      changeImg (status) {
        let _txt = 'card img'
        if (status !== 0) {
          _txt = 'card img' + status
        }
        return _txt
      }
    }
  }
</script>
<style slot-scope="scoped">
.card {
  display: block;
  width: 9%;
  height: 180px;
  border: 1px solid;
  float: left;
  margin: 2px;
  background: #EEE url('~@/assets/img/car.png') bottom no-repeat;
  background-size: contain;
}
.img {
  background-image: url('~@/assets/img/car.png');
}
.img1{
  background-image: url('~@/assets/img/car1.png');
}
.img2{
  background-image: url('~@/assets/img/car2.png');
}
p {
  text-align: center;
  color: green;
  font-weight: bold;
  font-size: 17px;
  padding-bottom: 73px;
}
</style>
