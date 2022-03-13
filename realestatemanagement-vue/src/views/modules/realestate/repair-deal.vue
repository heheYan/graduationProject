<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="房间号" prop="roomId">
        <template>
          <el-cascader v-model="roomIds"
                       disabled
                       placeholder="试试搜索：1001" style="min-width: 500px"
                       :options="options"
                       filterable></el-cascader>
        </template>
      </el-form-item>
    <el-form-item label="业主名称" prop="userName">
      <el-input v-model="dataForm.userName" placeholder="业主名称" disabled></el-input>
    </el-form-item>
      <el-form-item label="公共设施" prop="facilityId">
        <template>
          <el-select v-model="dataForm.facilityId" filterable clearable placeholder="请选择公共设施" disabled>
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
      <el-input type="textarea" v-model="dataForm.reason" placeholder="原因"disabled></el-input>
    </el-form-item>
      <el-form-item label="维修人员" prop="repairId">
        <el-select v-model="dataForm.repairId" placeholder="请选择">
          <el-option
            v-for="item in userList"
            :key="item.userId"
            :label="item.nickName"
            :value="item.userId">
          </el-option>
        </el-select>
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
          buildId: '',
          userId: '',
          userName: '',
          applyTime: '',
          reason: '',
          status: '',
          repairId: '',
          repairName: '',
          remark: '',
          repairTime: ''
        },
        roomIds: [],
        options: [],
        userList: [],
        facilityList: [],
        dataRule: {
          repairId: [
            { required: true, message: '维修人员必选', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.listDropData()
        this.listUserData()
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
      listUserData () {
        this.$http({
          url: this.$http.adornUrl('/realestate/repair/getRepairUserList'),
          method: 'get',
          params: this.$http.adornParams({})
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.userList = data.userList
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
                'repairId': this.dataForm.repairId,
                'repairName': this.dataForm.repairName,
                'remark': this.dataForm.remark
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
