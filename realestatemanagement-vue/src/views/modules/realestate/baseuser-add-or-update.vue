<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="120px">
      <el-form-item label="房间号" prop="roomId">
        <template>
          <el-cascader v-model="roomIds"
                       placeholder="试试搜索：1001" style="min-width: 500px"
                       :options="options"
                       filterable></el-cascader>
        </template>
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input v-model="dataForm.name" placeholder="姓名"></el-input>
      </el-form-item>
      <el-form-item label="身份证号" prop="idcard">
        <el-input v-model="dataForm.idcard" placeholder="身份证号"></el-input>
      </el-form-item>
      <el-form-item label="手机号" prop="mobile">
        <el-input v-model="dataForm.mobile" placeholder="手机号"></el-input>
      </el-form-item>
      <el-form-item label="是否户主" prop="isMaster">
        <template>
          <el-radio-group v-model="dataForm.isMaster">
            <el-radio :label=1>是</el-radio>
            <el-radio :label=0>否</el-radio>
          </el-radio-group>
        </template>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import {isMobile} from '@/utils/validate'

export default {
  data () {
    let validateMobile = (rule, value, callback) => {
      if (!isMobile(value)) {
        callback(new Error('手机号格式错误'))
      } else {
        callback()
      }
    }
    return {
      visible: false,
      roomIds: [],
      dataForm: {
        id: 0,
        roomId: '',
        name: '',
        loginid: '',
        idcard: '',
        mobile: '',
        isMaster: 0
      },
      dataRule: {
        roomIds: [
          {required: true, message: '房间不能为空', trigger: 'blur'}
        ],
        name: [
          {required: true, message: '姓名不能为空', trigger: 'blur'}
        ],
        idcard: [
          {required: true, message: '身份证号不能为空', trigger: 'blur'},
          {pattern: /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/, message: '你的身份证格式不正确'}
        ],
        mobile: [
          {required: true, message: '手机号不能为空', trigger: 'blur'},
          {validator: validateMobile, message: '请输入11位手机号', trigger: 'blur'}
        ]
      },
      options: [],
      pickerOptions: {
        disabledDate (time) {
          return time.getTime() > Date.now()
        },
        shortcuts: [{
          text: '今天',
          onClick (picker) {
            picker.$emit('pick', new Date())
          }
        }, {
          text: '昨天',
          onClick (picker) {
            const date = new Date()
            date.setTime(date.getTime() - 3600 * 1000 * 24)
            picker.$emit('pick', date)
          }
        }, {
          text: '一周前',
          onClick (picker) {
            const date = new Date()
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7)
            picker.$emit('pick', date)
          }
        }]
      }
    }
  },
  methods: {
    init (id) {
      this.dataForm.id = id || 0
      this.visible = true
      this.listDropData()
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        this.roomIds = []
        if (this.dataForm.id) {
          this.$http({
            url: this.$http.adornUrl(`/realestate/baseuser/info/${this.dataForm.id}`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataForm.roomId = data.baseUser.roomId
              this.dataForm.name = data.baseUser.name
              this.dataForm.loginid = data.baseUser.loginid
              this.dataForm.idcard = data.baseUser.idcard
              this.dataForm.mobile = data.baseUser.mobile
              this.dataForm.isMaster = data.baseUser.isMaster
              this.roomIds = [data.baseUser.buildId, data.baseUser.roomId]
            }
          })
        }
      })
    },
    listDropData () {
      this.$http({
        url: this.$http.adornUrl('/realestate/baseroom/dropData'),
        method: 'get',
        params: this.$http.adornParams({})
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.options = data.data
        }
      })
    },
    // 表单提交
    dataFormSubmit () {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl(`/realestate/baseuser/${!this.dataForm.id ? 'save' : 'update'}`),
            method: 'post',
            data: this.$http.adornData({
              'id': this.dataForm.id || undefined,
              'roomId': this.roomIds[1],
              'buildId': this.roomIds[0],
              'name': this.dataForm.name,
              'loginid': this.dataForm.loginid,
              'idcard': this.dataForm.idcard,
              'mobile': this.dataForm.mobile,
              'isMaster': this.dataForm.isMaster
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
<style>
.el-cascader-menu {
  min-width: 240px;
}
</style>
