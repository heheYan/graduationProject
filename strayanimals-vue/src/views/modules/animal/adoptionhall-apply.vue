<template>
  <el-dialog
    title="提交领养申请"
    :close-on-click-modal="false"
    :visible.sync="visible">
      <!--  申请意见表单    -->
      <el-card shadow="hover">
        <el-form :model="dataForm" :rules="rules" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
                 label-width="80px">
          <el-form-item label="申请理由" prop="reason">
            <el-input v-model="dataForm.reason" placeholder="申请理由" type="textarea"></el-input>
          </el-form-item>
        </el-form>
      </el-card>
      <!--  动物基本信息    -->
      <el-card shadow="hover">
        <div slot="header" class="clearfix">
          <span>基本信息</span>
        </div>
        <el-form :model="animalInfo" ref="animalInfo" label-width="80px">
          <el-form-item label="动物名称" prop="name">
            <el-input v-model="animalInfo.name" placeholder="请输入动物名称" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="描述" prop="description">
            <el-input v-model="animalInfo.description" placeholder="请输入描述" type="textarea" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="所属类型" prop="type">
            <el-input v-model="animalInfo.typename" :readonly="true" :disabled="true"></el-input>
          </el-form-item>
        </el-form>
      </el-card>
      <!--  处理记录    -->
      <el-card shadow="hover">
        <div slot="header" class="clearfix">
          <span>处理记录</span>
        </div>
        <el-steps :active="dataList.length" align-center>
          <el-step v-for="(item, index) in dataList" :key="item.id" :title="item.workitem">
            <template slot="description">
              <p>处理时间：{{ item.opDate }}</p>
              <p>处理人员：{{ item.opName }}</p>
            </template>
          </el-step>
        </el-steps>
      </el-card>
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
      dataList: [],
      dataForm: {
        animalId: '',
        reason: ''
      },
      animalInfo: {
        id: 0,
        name: '',
        imgurl: '',
        type: 1,
        description: '',
        typename: ''
      },
      rules: {
        reason: [
          {required: true, message: '请输入申请理由', trigger: 'blur'},
          {min: 3, max: 500, message: '长度在 3 到 500 个字符', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    init (id) {
      this.dataForm.animalId = id
      this.dataForm.reason = ''
      this.$http({
        url: this.$http.adornUrl(`/animal/animalinfo/info/${this.dataForm.animalId}`),
        method: 'get',
        params: this.$http.adornParams()
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.animalInfo.name = data.animalInfo.name
          this.animalInfo.imgurl = data.animalInfo.imgurl
          this.animalInfo.type = data.animalInfo.type
          this.animalInfo.typename = data.animalInfo.typename
          this.animalInfo.description = data.animalInfo.description
        }
      })
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        this.$http({
          url: this.$http.adornUrl('/animal/dealinstance/list/' + id),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          this.dataList = data.dataList
        })
      })
    },
    // 表单提交
    dataFormSubmit () {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl('/myAdoption/apply'),
            method: 'post',
            data: this.$http.adornData({
              'animalId': this.dataForm.animalId || undefined,
              'reason': this.dataForm.reason
            })
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '申请成功，请等待审核',
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
