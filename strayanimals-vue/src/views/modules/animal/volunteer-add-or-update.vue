<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="用户id" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="用户id"></el-input>
    </el-form-item>
    <el-form-item label="用户名" prop="username">
      <el-input v-model="dataForm.username" placeholder="用户名"></el-input>
    </el-form-item>
    <el-form-item label="申请日期" prop="applyDate">
      <el-input v-model="dataForm.applyDate" placeholder="申请日期"></el-input>
    </el-form-item>
    <el-form-item label="申请状态，0：已申请，1：审核通过，2：审核退回" prop="status">
      <el-input v-model="dataForm.status" placeholder="申请状态，0：已申请，1：审核通过，2：审核退回"></el-input>
    </el-form-item>
    <el-form-item label="处理人id" prop="handlerId">
      <el-input v-model="dataForm.handlerId" placeholder="处理人id"></el-input>
    </el-form-item>
    <el-form-item label="处理人" prop="handlerName">
      <el-input v-model="dataForm.handlerName" placeholder="处理人"></el-input>
    </el-form-item>
    <el-form-item label="处理时间" prop="handleDate">
      <el-input v-model="dataForm.handleDate" placeholder="处理时间"></el-input>
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
          username: '',
          applyDate: '',
          status: '',
          handlerId: '',
          handlerName: '',
          handleDate: ''
        },
        dataRule: {
          userId: [
            { required: true, message: '用户id不能为空', trigger: 'blur' }
          ],
          username: [
            { required: true, message: '用户名不能为空', trigger: 'blur' }
          ],
          applyDate: [
            { required: true, message: '申请日期不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '申请状态，0：已申请，1：审核通过，2：审核退回不能为空', trigger: 'blur' }
          ],
          handlerId: [
            { required: true, message: '处理人id不能为空', trigger: 'blur' }
          ],
          handlerName: [
            { required: true, message: '处理人不能为空', trigger: 'blur' }
          ],
          handleDate: [
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
              url: this.$http.adornUrl(`/animal/volunteer/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.userId = data.volunteer.userId
                this.dataForm.username = data.volunteer.username
                this.dataForm.applyDate = data.volunteer.applyDate
                this.dataForm.status = data.volunteer.status
                this.dataForm.handlerId = data.volunteer.handlerId
                this.dataForm.handlerName = data.volunteer.handlerName
                this.dataForm.handleDate = data.volunteer.handleDate
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
              url: this.$http.adornUrl(`/animal/volunteer/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'userId': this.dataForm.userId,
                'username': this.dataForm.username,
                'applyDate': this.dataForm.applyDate,
                'status': this.dataForm.status,
                'handlerId': this.dataForm.handlerId,
                'handlerName': this.dataForm.handlerName,
                'handleDate': this.dataForm.handleDate
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
