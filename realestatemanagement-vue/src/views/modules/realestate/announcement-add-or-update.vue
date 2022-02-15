<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="发布用户id" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="发布用户id"></el-input>
    </el-form-item>
    <el-form-item label="发布用户姓名" prop="userName">
      <el-input v-model="dataForm.userName" placeholder="发布用户姓名"></el-input>
    </el-form-item>
    <el-form-item label="发布状态，1：已发布，0：已下架" prop="status">
      <el-input v-model="dataForm.status" placeholder="发布状态，1：已发布，0：已下架"></el-input>
    </el-form-item>
    <el-form-item label="发布的内容" prop="content">
      <el-input v-model="dataForm.content" placeholder="发布的内容"></el-input>
    </el-form-item>
    <el-form-item label="发布时间" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="发布时间"></el-input>
    </el-form-item>
    <el-form-item label="是否删除，0：未删除，1：已删除" prop="isDelete">
      <el-input v-model="dataForm.isDelete" placeholder="是否删除，0：未删除，1：已删除"></el-input>
    </el-form-item>
    <el-form-item label="更新时间" prop="updateTime">
      <el-input v-model="dataForm.updateTime" placeholder="更新时间"></el-input>
    </el-form-item>
    <el-form-item label="标题" prop="title">
      <el-input v-model="dataForm.title" placeholder="标题"></el-input>
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
          status: '',
          content: '',
          createTime: '',
          isDelete: '',
          updateTime: '',
          title: ''
        },
        dataRule: {
          userId: [
            { required: true, message: '发布用户id不能为空', trigger: 'blur' }
          ],
          userName: [
            { required: true, message: '发布用户姓名不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '发布状态，1：已发布，0：已下架不能为空', trigger: 'blur' }
          ],
          content: [
            { required: true, message: '发布的内容不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '发布时间不能为空', trigger: 'blur' }
          ],
          isDelete: [
            { required: true, message: '是否删除，0：未删除，1：已删除不能为空', trigger: 'blur' }
          ],
          updateTime: [
            { required: true, message: '更新时间不能为空', trigger: 'blur' }
          ],
          title: [
            { required: true, message: '标题不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/realestate/announcement/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.userId = data.announcement.userId
                this.dataForm.userName = data.announcement.userName
                this.dataForm.status = data.announcement.status
                this.dataForm.content = data.announcement.content
                this.dataForm.createTime = data.announcement.createTime
                this.dataForm.isDelete = data.announcement.isDelete
                this.dataForm.updateTime = data.announcement.updateTime
                this.dataForm.title = data.announcement.title
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
              url: this.$http.adornUrl(`/realestate/announcement/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'userId': this.dataForm.userId,
                'userName': this.dataForm.userName,
                'status': this.dataForm.status,
                'content': this.dataForm.content,
                'createTime': this.dataForm.createTime,
                'isDelete': this.dataForm.isDelete,
                'updateTime': this.dataForm.updateTime,
                'title': this.dataForm.title
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
