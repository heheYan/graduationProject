<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="用户标识" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="用户标识"></el-input>
    </el-form-item>
    <el-form-item label="考勤日期" prop="kqDate">
      <el-input v-model="dataForm.kqDate" placeholder="考勤日期"></el-input>
    </el-form-item>
    <el-form-item label="考勤时间" prop="kqTime">
      <el-input v-model="dataForm.kqTime" placeholder="考勤时间"></el-input>
    </el-form-item>
    <el-form-item label="是否迟到" prop="isLate">
      <el-input v-model="dataForm.isLate" placeholder="是否迟到"></el-input>
    </el-form-item>
    <el-form-item label="是否早退" prop="isEarly">
      <el-input v-model="dataForm.isEarly" placeholder="是否早退"></el-input>
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
          kqDate: '',
          kqTime: '',
          isLate: '',
          isEarly: ''
        },
        dataRule: {
          userId: [
            { required: true, message: '用户标识不能为空', trigger: 'blur' }
          ],
          kqDate: [
            { required: true, message: '考勤日期不能为空', trigger: 'blur' }
          ],
          kqTime: [
            { required: true, message: '考勤时间不能为空', trigger: 'blur' }
          ],
          isLate: [
            { required: true, message: '是否迟到不能为空', trigger: 'blur' }
          ],
          isEarly: [
            { required: true, message: '是否早退不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/realestate/attendance/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.userId = data.attendance.userId
                this.dataForm.kqDate = data.attendance.kqDate
                this.dataForm.kqTime = data.attendance.kqTime
                this.dataForm.isLate = data.attendance.isLate
                this.dataForm.isEarly = data.attendance.isEarly
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
              url: this.$http.adornUrl(`/realestate/attendance/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'userId': this.dataForm.userId,
                'kqDate': this.dataForm.kqDate,
                'kqTime': this.dataForm.kqTime,
                'isLate': this.dataForm.isLate,
                'isEarly': this.dataForm.isEarly
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
