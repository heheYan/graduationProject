<template>
  <el-dialog
    title="快速生成"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="140px">
      <el-form-item label="层数" prop="level">
        <el-input-number v-model="dataForm.level" :min="1" :max="1000" label="层数"></el-input-number>
      </el-form-item>
      <el-form-item label="每层房间数" prop="roomNum">
        <el-input-number v-model="dataForm.roomNum" :min="1" :max="1000" label="每层房间数"></el-input-number>
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
          roomNum: 1,
          buildId: '',
          level: 1
        },
        dataRule: {
          level: [
            { required: true, message: '楼层数不能为空', trigger: 'blur' }
          ],
          roomNum: [
            { required: true, message: '每层房间数不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.buildId = id
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/realestate/basebuild/initRoom`),
              method: 'post',
              data: this.$http.adornData({
                'level': this.dataForm.level,
                'buildId': this.dataForm.buildId,
                'roomNum': this.dataForm.roomNum
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
