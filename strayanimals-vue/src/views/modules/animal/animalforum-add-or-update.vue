<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false" :before-close="handleClose"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="0">
      <el-form-item label="" prop="title">
        <el-input v-model="dataForm.title" placeholder="请输入标题"></el-input>
      </el-form-item>
      <ueditor v-model="dataForm.content" ref="editor"></ueditor>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="destroyDialog">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  import Ueditor from './editorcom'

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
          title: '',
          updateTime: ''
        },
        dataRule: {
          title: [
            { required: true, message: '标题不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    components: {
      Ueditor
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/animal/animalforum/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.content = data.animalForum.content
                this.dataForm.title = data.animalForum.title
                if (data.animalForum.content) {
                  this.$refs.editor.setContent(this.dataForm.content)
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
            this.dataForm.content = this.$refs.editor.getContent()
            if (this.dataForm.content !== '') {
              this.$http({
                url: this.$http.adornUrl(`/animal/animalforum/${!this.dataForm.id ? 'save' : 'update'}`),
                method: 'post',
                data: this.$http.adornData({
                  'id': this.dataForm.id || undefined,
                  'status': this.dataForm.status,
                  'content': this.dataForm.content,
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
                      this.$refs.editor.destroyed()
                      this.$emit('refreshDataList')
                    }
                  })
                } else {
                  this.$message.error(data.msg)
                }
              })
            }
          }
        })
      },
      destroyDialog () {
        this.visible = false
        this.$refs.editor.destroyed()
      },
      handleClose () {
        this.destroyDialog()
      }
    }
  }
</script>
