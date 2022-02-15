<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="150px">
    <el-form-item label="房间号" prop="no">
      <el-input v-model="dataForm.no" placeholder="房间号" :disabled="edit"></el-input>
    </el-form-item>
    <el-form-item label="楼栋" prop="buildId">
      <el-select v-model="dataForm.buildId" filterable clearable placeholder="请选择楼栋" :disabled="edit">
        <el-option
          v-for="item in buildData"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="状态" prop="status">
      <el-select v-model="dataForm.status" filterable clearable placeholder="请选择当前状态">
        <el-option
          v-for="item in statusData"
          :key="item.value"
          :label="item.label"
          :value="item.value">
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
        edit: false,
        dataForm: {
          id: 0,
          no: '',
          buildId: '',
          status: 0
        },
        buildData: [],
        statusData: [
          {
            'value': 0,
            'label': '未出售'
          },
          {
            'value': 1,
            'label': '业主入住'
          },
          {
            'value': 2,
            'label': '租户入住'
          }
        ],
        dataRule: {
          no: [
            { required: true, message: '房间号不能为空', trigger: 'blur' }
          ],
          buildId: [
            { required: true, message: '楼栋id不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '状态不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.getBuildDataList()
        this.dataForm.id = id || 0
        this.visible = true
        if (this.dataForm.id !== 0) {
          this.edit = true
        } else {
          this.edit = false
        }
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/realestate/baseroom/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.no = data.baseRoom.no
                this.dataForm.buildId = data.baseRoom.buildId
                this.dataForm.status = data.baseRoom.status
              }
            })
          }
        })
      },
      // 获取楼栋数据列表
      getBuildDataList () {
        this.$http({
          url: this.$http.adornUrl('/realestate/basebuild/dropDataList'),
          method: 'get',
          params: this.$http.adornParams({})
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.buildData = data.data
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/realestate/baseroom/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'no': this.dataForm.no,
                'buildId': this.dataForm.buildId,
                'status': this.dataForm.status
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
