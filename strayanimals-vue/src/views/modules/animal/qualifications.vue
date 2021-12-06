<template>
  <div style="display: block;">
    <el-form :rules="dataRule" :model="dataForm" ref="dataForm" @keyup.enter.native="onSubmit()" label-width="80px" size="mini">
      <el-form-item label="用户昵称" prop="name">
        <el-input v-model="dataForm.name" disabled></el-input>
      </el-form-item>
      <el-form-item label="真实姓名" prop="realname">
        <el-input v-model="dataForm.realname" :disabled="isSubmit"></el-input>
      </el-form-item>
      <el-form-item label="联系电话" prop="mobile">
        <el-input v-model="dataForm.mobile" :disabled="isSubmit"></el-input>
      </el-form-item>
      <el-form-item label="住址" prop="address">
        <el-input v-model="dataForm.address" :disabled="isSubmit"></el-input>
      </el-form-item>
      <el-form-item label="申请理由" prop="reason">
        <el-input type="textarea" v-model="dataForm.reason" :row="3" placeholder="请简要填写申请理由，不超过200字" :disabled="isSubmit"></el-input>
      </el-form-item>
      <el-form-item label="审核意见" prop="remark" v-if="isAudit">
        <el-input type="textarea" v-model="dataForm.remark" :row="3" disabled></el-input>
      </el-form-item>
      <el-form-item size="large" v-if="!isSubmit">
        <el-button type="primary" @click="onSubmit">提交</el-button>
      </el-form-item>
    </el-form>
    <div v-if="isSubmit" style="position: absolute;top: 200px; right: 50px;">
      <img style="width:120px;height:120px;border:none;" :src="reqPngUrl" alt=""/>
    </div>
  </div>
</template>

<script>
import {isMobile} from '@/utils/validate'

export default {
  name: 'qualifications',
  data () {
    var validateMobile = (rule, value, callback) => {
      if (!isMobile(value)) {
        callback(new Error('手机号格式错误'))
      } else {
        callback()
      }
    }
    return {
      dataForm: {
        id: '',
        name: '',
        realname: '',
        mobile: '',
        address: '',
        reason: '',
        remark: ''
      },
      isSubmit: false,
      isAudit: false,
      png: '',
      dataRule: {
        realname: [
          {required: true, message: '真实姓名不能为空', trigger: 'blur'}
        ],
        mobile: [
          {required: true, message: '联系电话不能为空', trigger: 'blur'},
          {validator: validateMobile, trigger: 'blur'}
        ]
      }
    }
  },
  computed: {
    reqPngUrl: function () {
      return require('../../../assets/img/' + this.png + '.png')
    }
  },
  activated () {
    this.getData()
  },
  methods: {
    getData () {
      this.$http({
        url: this.$http.adornUrl('/pualifications/detail'),
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
          switch (data.data.status) {
            case 0:
              this.png = 'default'
              this.isSubmit = true
              break
            case 1:
              this.png = 'pass'
              this.isSubmit = true
              this.isAudit = true
              break
            case 2:
              this.png = 'nopass'
              this.isSubmit = true
              this.isAudit = true
              break
            default:
              this.png = 'default'
              this.isSubmit = false
              break
          }
        }
      })
    },
    onSubmit () {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl('/pualifications/save'),
            method: 'post',
            data: this.$http.adornData({
              'id': this.dataForm.id || undefined,
              'realName': this.dataForm.realname,
              'mobile': this.dataForm.mobile,
              'address': this.dataForm.address,
              'reason': this.dataForm.reason
            })
          }).then(() => {
            this.$message.success('提交成功，请耐心等待内部审核')
            this.getData()
          }).catch(() => {
            this.$message.error('系统异常，稍后再试')
          })
        }
      })
    }
  }
}
</script>
