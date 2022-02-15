<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="车位编号" prop="no">
      <el-input v-model="dataForm.no" placeholder="车位编号"></el-input>
    </el-form-item>
    <el-form-item label="车牌号" prop="carNo">
      <el-input v-model="dataForm.carNo" placeholder="车牌号"></el-input>
    </el-form-item>
    <el-form-item label="房间id" prop="roomId">
      <el-input v-model="dataForm.roomId" placeholder="房间id"></el-input>
    </el-form-item>
    <el-form-item label="房间号" prop="roomNo">
      <el-input v-model="dataForm.roomNo" placeholder="房间号"></el-input>
    </el-form-item>
    <el-form-item label="金额" prop="money">
      <el-input v-model="dataForm.money" placeholder="金额"></el-input>
    </el-form-item>
    <el-form-item label="状态。出租、临时、购买" prop="status">
      <el-input v-model="dataForm.status" placeholder="状态。出租、临时、购买"></el-input>
    </el-form-item>
    <el-form-item label="变更时间" prop="lastChangeTime">
      <el-input v-model="dataForm.lastChangeTime" placeholder="变更时间"></el-input>
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
        dataRule: {
          no: [
            { required: true, message: '车位编号不能为空', trigger: 'blur' }
          ],
          carNo: [
            { required: true, message: '车牌号不能为空', trigger: 'blur' }
          ],
          roomId: [
            { required: true, message: '房间id不能为空', trigger: 'blur' }
          ],
          roomNo: [
            { required: true, message: '房间号不能为空', trigger: 'blur' }
          ],
          money: [
            { required: true, message: '金额不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '状态。出租、临时、购买不能为空', trigger: 'blur' }
          ],
          lastChangeTime: [
            { required: true, message: '变更时间不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
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
                this.dataForm.lastChangeTime = data.carspace.lastChangeTime
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
                'roomId': this.dataForm.roomId,
                'roomNo': this.dataForm.roomNo,
                'money': this.dataForm.money,
                'status': this.dataForm.status,
                'lastChangeTime': this.dataForm.lastChangeTime
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
      }
    }
  }
</script>
