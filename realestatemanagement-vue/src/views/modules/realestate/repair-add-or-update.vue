<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="房间id" prop="roomId">
      <el-input v-model="dataForm.roomId" placeholder="房间id"></el-input>
    </el-form-item>
    <el-form-item label="业主id" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="业主id"></el-input>
    </el-form-item>
    <el-form-item label="业主名称" prop="userName">
      <el-input v-model="dataForm.userName" placeholder="业主名称"></el-input>
    </el-form-item>
    <el-form-item label="报修时间" prop="applyTime">
      <el-input v-model="dataForm.applyTime" placeholder="报修时间"></el-input>
    </el-form-item>
    <el-form-item label="原因" prop="reason">
      <el-input v-model="dataForm.reason" placeholder="原因"></el-input>
    </el-form-item>
    <el-form-item label="处理状态" prop="status">
      <el-input v-model="dataForm.status" placeholder="处理状态"></el-input>
    </el-form-item>
    <el-form-item label="维修人id" prop="repairId">
      <el-input v-model="dataForm.repairId" placeholder="维修人id"></el-input>
    </el-form-item>
    <el-form-item label="维修人名称" prop="repairName">
      <el-input v-model="dataForm.repairName" placeholder="维修人名称"></el-input>
    </el-form-item>
    <el-form-item label="备注" prop="remark">
      <el-input v-model="dataForm.remark" placeholder="备注"></el-input>
    </el-form-item>
    <el-form-item label="处理时间" prop="repairTime">
      <el-input v-model="dataForm.repairTime" placeholder="处理时间"></el-input>
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
          roomId: '',
          userId: '',
          userName: '',
          applyTime: '',
          reason: '',
          status: '',
          repairId: '',
          repairName: '',
          remark: '',
          repairTime: ''
        },
        dataRule: {
          roomId: [
            { required: true, message: '房间id不能为空', trigger: 'blur' }
          ],
          userId: [
            { required: true, message: '业主id不能为空', trigger: 'blur' }
          ],
          userName: [
            { required: true, message: '业主名称不能为空', trigger: 'blur' }
          ],
          applyTime: [
            { required: true, message: '报修时间不能为空', trigger: 'blur' }
          ],
          reason: [
            { required: true, message: '原因不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '处理状态不能为空', trigger: 'blur' }
          ],
          repairId: [
            { required: true, message: '维修人id不能为空', trigger: 'blur' }
          ],
          repairName: [
            { required: true, message: '维修人名称不能为空', trigger: 'blur' }
          ],
          remark: [
            { required: true, message: '备注不能为空', trigger: 'blur' }
          ],
          repairTime: [
            { required: true, message: '处理时间不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/realestate/repair/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.roomId = data.repair.roomId
                this.dataForm.userId = data.repair.userId
                this.dataForm.userName = data.repair.userName
                this.dataForm.applyTime = data.repair.applyTime
                this.dataForm.reason = data.repair.reason
                this.dataForm.status = data.repair.status
                this.dataForm.repairId = data.repair.repairId
                this.dataForm.repairName = data.repair.repairName
                this.dataForm.remark = data.repair.remark
                this.dataForm.repairTime = data.repair.repairTime
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
              url: this.$http.adornUrl(`/realestate/repair/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'roomId': this.dataForm.roomId,
                'userId': this.dataForm.userId,
                'userName': this.dataForm.userName,
                'applyTime': this.dataForm.applyTime,
                'reason': this.dataForm.reason,
                'status': this.dataForm.status,
                'repairId': this.dataForm.repairId,
                'repairName': this.dataForm.repairName,
                'remark': this.dataForm.remark,
                'repairTime': this.dataForm.repairTime
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
