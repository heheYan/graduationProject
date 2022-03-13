<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="120px">
    <el-form-item label="车位编号" prop="no">
      <el-input v-model="dataForm.no" placeholder="车位编号" disabled></el-input>
    </el-form-item>
    <el-form-item label="车牌号" prop="carNo">
      <el-input v-model="dataForm.carNo" placeholder="车牌号"></el-input>
    </el-form-item>
      <el-form-item label="房间号" prop="roomId">
        <template>
          <el-cascader v-model="roomIds" clearable
                       placeholder="试试搜索：1001" style="min-width: 500px"
                       :options="options"
                       filterable></el-cascader>
        </template>
      </el-form-item>
    <el-form-item label="金额" prop="money">
      <el-input v-model="dataForm.money" placeholder="金额"></el-input>
    </el-form-item>
    <el-form-item label="状态" prop="status">
      <el-select v-model="dataForm.status" filterable clearable placeholder="请选择当前状态">
        <el-option
          v-for="item in statusData"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        roomIds: [],
        dataForm: {
          id: 0,
          no: '',
          carNo: '',
          roomId: '',
          roomNo: '',
          money: '',
          status: '',
          lastChangeTime: ''
        },
        options: [],
        statusData: [
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
        dataRule: {
          no: [
            { required: true, message: '车位编号不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '状态不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.listDropData()
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          this.roomIds = []
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/realestate/carspace/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.no = data.carspace.no
                this.dataForm.carNo = data.carspace.carNo
                this.dataForm.roomId = data.carspace.roomId
                this.dataForm.roomNo = data.carspace.roomNo
                this.dataForm.money = data.carspace.money
                this.dataForm.status = data.carspace.status
                this.roomIds = [data.carspace.buildId, data.carspace.roomId]
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/realestate/carspace/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'no': this.dataForm.no,
                'carNo': this.dataForm.carNo,
                'roomId': this.roomIds[1],
                'buildId': this.roomIds[0],
                'money': this.dataForm.money,
                'status': this.dataForm.status
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      },
      listDropData () {
        this.$http({
          url: this.$http.adornUrl('/realestate/baseroom/dropData'),
          method: 'get',
          params: this.$http.adornParams({})
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.options = data.data
          }
        })
      }
    }
  }
</script>
