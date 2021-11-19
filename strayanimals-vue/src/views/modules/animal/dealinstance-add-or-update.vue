<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="" prop="animalId">
      <el-input v-model="dataForm.animalId" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="操作人id" prop="opId">
      <el-input v-model="dataForm.opId" placeholder="操作人id"></el-input>
    </el-form-item>
    <el-form-item label="" prop="opName">
      <el-input v-model="dataForm.opName" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="环节名称" prop="workitem">
      <el-input v-model="dataForm.workitem" placeholder="环节名称"></el-input>
    </el-form-item>
    <el-form-item label="上一步" prop="preWorkitemId">
      <el-input v-model="dataForm.preWorkitemId" placeholder="上一步"></el-input>
    </el-form-item>
    <el-form-item label="当前状态" prop="status">
      <el-input v-model="dataForm.status" placeholder="当前状态"></el-input>
    </el-form-item>
    <el-form-item label="处理意见" prop="opinion">
      <el-input v-model="dataForm.opinion" placeholder="处理意见"></el-input>
    </el-form-item>
    <el-form-item label="标题" prop="title">
      <el-input v-model="dataForm.title" placeholder="标题"></el-input>
    </el-form-item>
    <el-form-item label="接收时间" prop="startDate">
      <el-input v-model="dataForm.startDate" placeholder="接收时间"></el-input>
    </el-form-item>
    <el-form-item label="处理时间" prop="opDate">
      <el-input v-model="dataForm.opDate" placeholder="处理时间"></el-input>
    </el-form-item>
    <el-form-item label="处理地址" prop="handleUrl">
      <el-input v-model="dataForm.handleUrl" placeholder="处理地址"></el-input>
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
          animalId: '',
          opId: '',
          opName: '',
          workitem: '',
          preWorkitemId: '',
          status: '',
          opinion: '',
          title: '',
          startDate: '',
          opDate: '',
          handleUrl: ''
        },
        dataRule: {
          animalId: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          opId: [
            { required: true, message: '操作人id不能为空', trigger: 'blur' }
          ],
          opName: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          workitem: [
            { required: true, message: '环节名称不能为空', trigger: 'blur' }
          ],
          preWorkitemId: [
            { required: true, message: '上一步不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '当前状态不能为空', trigger: 'blur' }
          ],
          opinion: [
            { required: true, message: '处理意见不能为空', trigger: 'blur' }
          ],
          title: [
            { required: true, message: '标题不能为空', trigger: 'blur' }
          ],
          startDate: [
            { required: true, message: '接收时间不能为空', trigger: 'blur' }
          ],
          opDate: [
            { required: true, message: '处理时间不能为空', trigger: 'blur' }
          ],
          handleUrl: [
            { required: true, message: '处理地址不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/animal/dealinstance/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.animalId = data.dealInstance.animalId
                this.dataForm.opId = data.dealInstance.opId
                this.dataForm.opName = data.dealInstance.opName
                this.dataForm.workitem = data.dealInstance.workitem
                this.dataForm.preWorkitemId = data.dealInstance.preWorkitemId
                this.dataForm.status = data.dealInstance.status
                this.dataForm.opinion = data.dealInstance.opinion
                this.dataForm.title = data.dealInstance.title
                this.dataForm.startDate = data.dealInstance.startDate
                this.dataForm.opDate = data.dealInstance.opDate
                this.dataForm.handleUrl = data.dealInstance.handleUrl
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
              url: this.$http.adornUrl(`/animal/dealinstance/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'animalId': this.dataForm.animalId,
                'opId': this.dataForm.opId,
                'opName': this.dataForm.opName,
                'workitem': this.dataForm.workitem,
                'preWorkitemId': this.dataForm.preWorkitemId,
                'status': this.dataForm.status,
                'opinion': this.dataForm.opinion,
                'title': this.dataForm.title,
                'startDate': this.dataForm.startDate,
                'opDate': this.dataForm.opDate,
                'handleUrl': this.dataForm.handleUrl
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
