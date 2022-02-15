<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="房间id" prop="roomId">
      <el-input v-model="dataForm.roomId" placeholder="房间id"></el-input>
    </el-form-item>
    <el-form-item label="房间号" prop="roomNo">
      <el-input v-model="dataForm.roomNo" placeholder="房间号"></el-input>
    </el-form-item>
    <el-form-item label="姓名" prop="name">
      <el-input v-model="dataForm.name" placeholder="姓名"></el-input>
    </el-form-item>
    <el-form-item label="登录名" prop="loginid">
      <el-input v-model="dataForm.loginid" placeholder="登录名"></el-input>
    </el-form-item>
    <el-form-item label="身份证号" prop="idcard">
      <el-input v-model="dataForm.idcard" placeholder="身份证号"></el-input>
    </el-form-item>
    <el-form-item label="手机号" prop="mobile">
      <el-input v-model="dataForm.mobile" placeholder="手机号"></el-input>
    </el-form-item>
    <el-form-item label="是否户主" prop="isMaster">
      <el-input v-model="dataForm.isMaster" placeholder="是否户主"></el-input>
    </el-form-item>
    <el-form-item label="是否租户" prop="isTenantry">
      <el-input v-model="dataForm.isTenantry" placeholder="是否租户"></el-input>
    </el-form-item>
    <el-form-item label="入住时间" prop="enterTime">
      <el-input v-model="dataForm.enterTime" placeholder="入住时间"></el-input>
    </el-form-item>
    <el-form-item label="离开时间" prop="outTime">
      <el-input v-model="dataForm.outTime" placeholder="离开时间"></el-input>
    </el-form-item>
    <el-form-item label="是否删除" prop="isDelete">
      <el-input v-model="dataForm.isDelete" placeholder="是否删除"></el-input>
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
          roomId: '',
          roomNo: '',
          name: '',
          loginid: '',
          idcard: '',
          mobile: '',
          isMaster: '',
          isTenantry: '',
          enterTime: '',
          outTime: '',
          isDelete: ''
        },
        dataRule: {
          roomId: [
            { required: true, message: '房间id不能为空', trigger: 'blur' }
          ],
          roomNo: [
            { required: true, message: '房间号不能为空', trigger: 'blur' }
          ],
          name: [
            { required: true, message: '姓名不能为空', trigger: 'blur' }
          ],
          loginid: [
            { required: true, message: '登录名不能为空', trigger: 'blur' }
          ],
          idcard: [
            { required: true, message: '身份证号不能为空', trigger: 'blur' }
          ],
          mobile: [
            { required: true, message: '手机号不能为空', trigger: 'blur' }
          ],
          isMaster: [
            { required: true, message: '是否户主不能为空', trigger: 'blur' }
          ],
          isTenantry: [
            { required: true, message: '是否租户不能为空', trigger: 'blur' }
          ],
          enterTime: [
            { required: true, message: '入住时间不能为空', trigger: 'blur' }
          ],
          outTime: [
            { required: true, message: '离开时间不能为空', trigger: 'blur' }
          ],
          isDelete: [
            { required: true, message: '是否删除不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/realestate/baseuser/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.roomId = data.baseUser.roomId
                this.dataForm.roomNo = data.baseUser.roomNo
                this.dataForm.name = data.baseUser.name
                this.dataForm.loginid = data.baseUser.loginid
                this.dataForm.idcard = data.baseUser.idcard
                this.dataForm.mobile = data.baseUser.mobile
                this.dataForm.isMaster = data.baseUser.isMaster
                this.dataForm.isTenantry = data.baseUser.isTenantry
                this.dataForm.enterTime = data.baseUser.enterTime
                this.dataForm.outTime = data.baseUser.outTime
                this.dataForm.isDelete = data.baseUser.isDelete
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
              url: this.$http.adornUrl(`/realestate/baseuser/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'roomId': this.dataForm.roomId,
                'roomNo': this.dataForm.roomNo,
                'name': this.dataForm.name,
                'loginid': this.dataForm.loginid,
                'idcard': this.dataForm.idcard,
                'mobile': this.dataForm.mobile,
                'isMaster': this.dataForm.isMaster,
                'isTenantry': this.dataForm.isTenantry,
                'enterTime': this.dataForm.enterTime,
                'outTime': this.dataForm.outTime,
                'isDelete': this.dataForm.isDelete
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
