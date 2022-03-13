<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="120px">
    <el-form-item label="投诉人" prop="userName">
      <el-input v-model="dataForm.userName" placeholder="投诉人" disabled></el-input>
    </el-form-item>
    <el-form-item label="类型" prop="type">
      <template>
        <el-radio-group v-model="dataForm.type">
          <el-radio :label=1>投诉</el-radio>
          <el-radio :label=2>举报</el-radio>
        </el-radio-group>
      </template>
    </el-form-item>
    <el-form-item label="投诉举报内容" prop="content">
      <el-input type="textarea" :rows="10" v-model="dataForm.content" placeholder="投诉举报内容"></el-input>
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
          type: '',
          content: '',
          handleId: '',
          handleName: '',
          opinions: '',
          handleDate: '',
          status: '',
          complaintDate: ''
        },
        dataRule: {
          userName: [
            { required: true, message: '投诉人不能为空', trigger: 'blur' }
          ],
          type: [
            { required: true, message: '类型不能为空', trigger: 'blur' }
          ],
          content: [
            { required: true, message: '投诉举报内容不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/realestate/complaints/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.userId = data.complaints.userId
                this.dataForm.userName = data.complaints.userName
                this.dataForm.type = data.complaints.type
                this.dataForm.content = data.complaints.content
                this.dataForm.handleId = data.complaints.handleId
                this.dataForm.handleName = data.complaints.handleName
                this.dataForm.opinions = data.complaints.opinions
                this.dataForm.handleDate = data.complaints.handleDate
                this.dataForm.status = data.complaints.status
                this.dataForm.complaintDate = data.complaints.complaintDate
              }
            })
          } else {
            // 获取当前信息
            this.$http({
              url: this.$http.adornUrl('/sys/user/info'),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.userId = data.user.userId
                this.dataForm.userName = data.user.username
                if (data.user.nickName != null) {
                  this.dataForm.userName = data.user.nickName
                }
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
              url: this.$http.adornUrl(`/realestate/complaints/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'userId': this.dataForm.userId,
                'userName': this.dataForm.userName,
                'type': this.dataForm.type,
                'content': this.dataForm.content
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
