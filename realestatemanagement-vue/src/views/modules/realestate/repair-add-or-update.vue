<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="房间号" prop="roomId">
        <template>
          <el-cascader v-model="roomIds"
                       placeholder="试试搜索：1001" style="min-width: 500px"
                       :options="options"
                       filterable></el-cascader>
        </template>
      </el-form-item>
    <el-form-item label="业主名称" prop="userName">
      <el-input v-model="dataForm.userName" placeholder="业主名称"></el-input>
    </el-form-item>
    <el-form-item label="公共设施" prop="facilityId">
      <template>
        <el-select v-model="dataForm.facilityId" filterable clearable placeholder="请选择公共设施" :disabled="dataForm.id">
          <el-option
            v-for="item in facilityList"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </template>
    </el-form-item>
    <el-form-item label="报修原因" prop="reason">
      <el-input type="textarea" v-model="dataForm.reason" placeholder="原因"></el-input>
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
          userId: '',
          userName: '',
          applyTime: '',
          reason: '',
          status: '',
          repairId: '',
          repairName: '',
          remark: '',
          facilityId: '',
          facilityName: '',
          repairTime: ''
        },
        roomIds: [],
        options: [],
        facilityList: [],
        dataRule: {
          userName: [
            { required: true, message: '业主名称不能为空', trigger: 'blur' }
          ],
          reason: [
            { required: true, message: '原因不能为空', trigger: 'blur' }
          ]
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
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/realestate/repair/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.userId = data.repair.userId
                this.dataForm.userName = data.repair.userName
                this.dataForm.applyTime = data.repair.applyTime
                this.dataForm.reason = data.repair.reason
                this.dataForm.status = data.repair.status
                this.dataForm.repairId = data.repair.repairId
                this.dataForm.repairName = data.repair.repairName
                this.dataForm.remark = data.repair.remark
                this.dataForm.repairTime = data.repair.repairTime
                this.dataForm.facilityId = data.repair.facilityId
                this.roomIds = [data.repair.buildId, data.repair.roomId]
              }
            })
          } else {
            // 获取当前信息
            this.$http({
              url: this.$http.adornUrl('/sys/user/info'),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.userId = data.user.userId
                this.dataForm.userName = data.user.username
                if (data.user.nickName != null) {
                  this.dataForm.userName = data.user.nickName
                }
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
        this.$http({
          url: this.$http.adornUrl('/realestate/basefacility/dropData'),
          method: 'get',
          params: this.$http.adornParams({})
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.facilityList = data.data
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/realestate/repair/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'roomId': this.roomIds[1],
                'buildId': this.roomIds[0],
                'userName': this.dataForm.userName,
                'reason': this.dataForm.reason,
                'facilityId': this.dataForm.facilityId
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
