<template>
  <el-dialog
    :title="dataForm.status === 1 ? '查看处理意见' : '添加处理意见'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="120px">
    <el-form-item label="投诉人" prop="userName">
      <el-input v-model="dataForm.userName" placeholder="投诉人" disabled></el-input>
    </el-form-item>
    <el-form-item label="类型" prop="type">
      <template>
        <el-radio-group v-model="dataForm.type" disabled>
          <el-radio :label=1>投诉</el-radio>
          <el-radio :label=2>举报</el-radio>
        </el-radio-group>
      </template>
    </el-form-item>
    <el-form-item label="投诉举报内容" prop="content">
      <el-input type="textarea" :rows="10" v-model="dataForm.content" disabled placeholder="投诉举报内容"></el-input>
    </el-form-item>
    <el-form-item label="处理意见" prop="content">
      <el-input type="textarea" :rows="10" v-model="dataForm.opinions" :disabled="dis" placeholder="请输入处理意见"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button v-if="!dis" type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dis: false,
        dataForm: {
          id: 0,
          userId: '',
          userName: '',
          type: '',
          content: '',
          opinions: ''
        },
        dataRule: {
          opinions: [
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
                if (data.complaints.status === 1) {
                  this.dis = true
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
              url: this.$http.adornUrl(`/realestate/complaints/handle`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'opinions': this.dataForm.opinions
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
