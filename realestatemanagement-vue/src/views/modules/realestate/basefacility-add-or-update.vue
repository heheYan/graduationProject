<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="120px">
    <el-form-item label="设备编号" prop="no">
      <el-input v-model="dataForm.no" placeholder="设备编号"></el-input>
    </el-form-item>
    <el-form-item label="设备名称" prop="name">
      <el-input v-model="dataForm.name" placeholder="设备名称"></el-input>
    </el-form-item>
    <el-form-item label="设备类型" prop="type">
      <el-select v-model="dataForm.type" filterable clearable placeholder="请选择设备类型">
        <el-option
          v-for="item in typeData"
          :key="item.value"
          :label="item.name"
          :value="item.value">
        </el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="所属楼栋" prop="buildId">
      <el-select v-model="dataForm.buildId" filterable clearable placeholder="请选择楼栋">
        <el-option
          v-for="item in buildData"
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
        dataForm: {
          id: 0,
          no: '',
          name: '',
          type: '',
          buildId: ''
        },
        buildData: [],
        typeData: [],
        dataRule: {
          no: [
            { required: true, message: '设备编号不能为空', trigger: 'blur' }
          ],
          name: [
            { required: true, message: '设备名称不能为空', trigger: 'blur' }
          ],
          type: [
            { required: true, message: '设备类型不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        // 初始化楼栋选项
        this.getBuildDataList()
        // 初始化设备类型
        this.getTypeList()
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/realestate/basefacility/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.no = data.baseFacility.no
                this.dataForm.name = data.baseFacility.name
                this.dataForm.type = data.baseFacility.type
                this.dataForm.buildId = data.baseFacility.buildId
                this.dataForm.status = data.baseFacility.status
                this.dataForm.createTime = data.baseFacility.createTime
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
      // 获取楼栋数据列表
      getTypeList () {
        this.$http({
          url: this.$http.adornUrl('/realestate/basefacility/getTypeList'),
          method: 'get',
          params: this.$http.adornParams({})
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.typeData = data.typeList
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/realestate/basefacility/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'no': this.dataForm.no,
                'name': this.dataForm.name,
                'type': this.dataForm.type,
                'buildId': this.dataForm.buildId,
                'status': this.dataForm.status,
                'createTime': this.dataForm.createTime
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
