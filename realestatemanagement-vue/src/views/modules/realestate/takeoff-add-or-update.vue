<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="用户id" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="用户id"></el-input>
    </el-form-item>
    <el-form-item label="用户名称" prop="userName">
      <el-input v-model="dataForm.userName" placeholder="用户名称"></el-input>
    </el-form-item>
    <el-form-item label="开始时间" prop="startDate">
      <el-input v-model="dataForm.startDate" placeholder="开始时间"></el-input>
    </el-form-item>
    <el-form-item label="结束时间" prop="endDate">
      <el-input v-model="dataForm.endDate" placeholder="结束时间"></el-input>
    </el-form-item>
    <el-form-item label="请假原因" prop="reason">
      <el-input v-model="dataForm.reason" placeholder="请假原因"></el-input>
    </el-form-item>
    <el-form-item label="状态" prop="status">
      <el-input v-model="dataForm.status" placeholder="状态"></el-input>
    </el-form-item>
    <el-form-item label="处理人标识" prop="dealUserId">
      <el-input v-model="dataForm.dealUserId" placeholder="处理人标识"></el-input>
    </el-form-item>
    <el-form-item label="处理人名称" prop="dealUserName">
      <el-input v-model="dataForm.dealUserName" placeholder="处理人名称"></el-input>
    </el-form-item>
    <el-form-item label="处理备注" prop="remark">
      <el-input v-model="dataForm.remark" placeholder="处理备注"></el-input>
    </el-form-item>
    <el-form-item label="处理时间" prop="dealTime">
      <el-input v-model="dataForm.dealTime" placeholder="处理时间"></el-input>
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
          userId: '',
          userName: '',
          startDate: '',
          endDate: '',
          reason: '',
          status: '',
          dealUserId: '',
          dealUserName: '',
          remark: '',
          dealTime: ''
        },
        dataRule: {
          userId: [
            { required: true, message: '用户id不能为空', trigger: 'blur' }
          ],
          userName: [
            { required: true, message: '用户名称不能为空', trigger: 'blur' }
          ],
          startDate: [
            { required: true, message: '开始时间不能为空', trigger: 'blur' }
          ],
          endDate: [
            { required: true, message: '结束时间不能为空', trigger: 'blur' }
          ],
          reason: [
            { required: true, message: '请假原因不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '状态不能为空', trigger: 'blur' }
          ],
          dealUserId: [
            { required: true, message: '处理人标识不能为空', trigger: 'blur' }
          ],
          dealUserName: [
            { required: true, message: '处理人名称不能为空', trigger: 'blur' }
          ],
          remark: [
            { required: true, message: '处理备注不能为空', trigger: 'blur' }
          ],
          dealTime: [
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
              url: this.$http.adornUrl(`/realestate/takeoff/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.userId = data.takeoff.userId
                this.dataForm.userName = data.takeoff.userName
                this.dataForm.startDate = data.takeoff.startDate
                this.dataForm.endDate = data.takeoff.endDate
                this.dataForm.reason = data.takeoff.reason
                this.dataForm.status = data.takeoff.status
                this.dataForm.dealUserId = data.takeoff.dealUserId
                this.dataForm.dealUserName = data.takeoff.dealUserName
                this.dataForm.remark = data.takeoff.remark
                this.dataForm.dealTime = data.takeoff.dealTime
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
              url: this.$http.adornUrl(`/realestate/takeoff/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'userId': this.dataForm.userId,
                'userName': this.dataForm.userName,
                'startDate': this.dataForm.startDate,
                'endDate': this.dataForm.endDate,
                'reason': this.dataForm.reason,
                'status': this.dataForm.status,
                'dealUserId': this.dataForm.dealUserId,
                'dealUserName': this.dataForm.dealUserName,
                'remark': this.dataForm.remark,
                'dealTime': this.dataForm.dealTime
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
