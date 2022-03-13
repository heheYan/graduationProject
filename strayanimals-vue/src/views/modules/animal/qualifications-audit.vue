<template>
  <el-dialog
    title="审核"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :rules="dataRule" :model="dataForm" ref="dataForm" @keyup.enter.native="onSubmit(1)" label-width="80px" size="mini">
      <el-form-item label="用户昵称" prop="name">
        <el-input v-model="dataForm.name" disabled></el-input>
      </el-form-item>
      <el-form-item label="真实姓名" prop="realname">
        <el-input v-model="dataForm.realname" disabled></el-input>
      </el-form-item>
      <el-form-item label="联系电话" prop="mobile">
        <el-input v-model="dataForm.mobile" disabled></el-input>
      </el-form-item>
      <el-form-item label="住址" prop="address">
        <el-input v-model="dataForm.address" disabled></el-input>
      </el-form-item>
      <el-form-item label="申请理由" prop="reason">
        <el-input type="textarea" v-model="dataForm.reason" :row="3" placeholder="请简要填写申请理由，不超过200字" disabled></el-input>
      </el-form-item>

      <el-form-item label="审核意见" prop="remark">
        <el-input type="textarea" v-model="dataForm.remark" :row="3" placeholder="请简要填写审核意见，不超过200字"></el-input>
      </el-form-item>
    </el-form>

    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="onSubmit(1)">审核通过</el-button>
      <el-button type="danger" @click="onSubmit(2)">审核不通过</el-button>
    </span>
  </el-dialog>
</template>

<script>
export default {
  data () {
    return {
      dataForm: {
        id: '',
        name: '',
        realname: '',
        mobile: '',
        address: '',
        reason: '',
        remark: '',
        userId: ''
      },
      visible: false,
      isSubmit: false,
      isAudit: false,
      dataRule: {
        remark: [
          {required: true, message: '审核意见不能为空', trigger: 'blur'}
        ]
      }
    }
  },
  computed: {
    myHeader () {
      return {
        'token': this.$cookie.get('token')
      }
    }
  },
  methods: {
    init (id) {
      this.visible = true
      this.$http({
        url: this.$http.adornUrl('/pualifications/detail/' + id),
        method: 'get',
        params: this.$http.adornParams()
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.dataForm.id = data.data.id
          this.dataForm.name = data.data.name
          this.dataForm.realname = data.data.realName
          this.dataForm.mobile = data.data.mobile
          this.dataForm.address = data.data.address
          this.dataForm.reason = data.data.reason
          this.dataForm.remark = data.data.remark
          this.dataForm.userId = data.data.userId
        }
      })
    },
    onSubmit (status) {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl('/pualifications/update'),
            method: 'post',
            data: this.$http.adornData({
              'id': this.dataForm.id || undefined,
              'remark': this.dataForm.remark,
              'status': status,
              'userId': this.dataForm.userId
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
