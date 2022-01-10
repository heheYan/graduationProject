<template>
  <el-dialog
    title="领养申请审核"
    :close-on-click-modal="false"
    :visible.sync="visible">

      <!--  动物基本信息    -->
      <el-card shadow="hover">
        <div slot="header" class="clearfix">
          <span>基本信息</span>
        </div>
        <el-form :model="animalInfo" ref="animalInfo" label-width="80px">
          <el-form-item label="领养人" prop="userName">
            <el-input v-model="dataForm.userName" :disabled="true" style="width: 80%;"></el-input>
            <el-button @click="viewQual()">领养资格核对</el-button>
          </el-form-item>
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
    <!--  申请意见表单    -->
    <el-card shadow="hover">
      <el-form :model="dataForm" :rules="rules" ref="dataForm"
               label-width="80px">
        <el-form-item label="审核意见" prop="opinion">
          <el-input v-model="dataForm.opinion" placeholder="请输入审核意见" type="textarea"></el-input>
        </el-form-item>
      </el-form>
    </el-card>
    <span slot="footer" class="dialog-footer">
        <el-button @click="visible = false">取消</el-button>
        <el-button type="danger" @click="dataFormSubmit(2)">审核不通过</el-button>
        <el-button type="primary" @click="dataFormSubmit(1)">审核通过</el-button>
    </span>
    <qualifications v-if="qVisible" ref="qualifications" :visible.sync="qVisible"></qualifications>
  </el-dialog>
</template>

<script>
import Qualifications from './qualifications-dialog'
export default {
  data () {
    return {
      qVisible: false,
      visible: false,
      dataList: [],
      dataForm: {
        id: '',
        opinion: '',
        userName: '',
        userId: '',
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
        opinion: [
          {required: true, message: '请输入审核意见', trigger: 'blur'},
          {min: 2, max: 500, message: '长度在 2 到 500 个字符', trigger: 'blur'}
        ]
      }
    }
  },
  components: {
    Qualifications
  },
  methods: {
    init (id, animalId) {
      this.dataForm.id = id
      this.animalInfo.id = animalId
      this.$http({
        url: this.$http.adornUrl(`/myAdoption/info/${this.dataForm.id}`),
        method: 'get',
        params: this.$http.adornParams()
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.animalInfo.name = data.myAdoption.animalInfo.name
          this.animalInfo.imgurl = data.myAdoption.animalInfo.imgurl
          this.animalInfo.type = data.myAdoption.animalInfo.type
          this.animalInfo.typename = data.myAdoption.animalInfo.typename
          this.animalInfo.description = data.myAdoption.animalInfo.description

          this.dataForm.reason = data.myAdoption.reason
          this.dataForm.userId = data.myAdoption.userId
          this.dataForm.userName = data.myAdoption.userName
        }
      })
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        this.$http({
          url: this.$http.adornUrl('/animal/dealinstance/list/' + animalId),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          this.dataList = data.dataList
        })
      })
    },
    // 表单提交
    dataFormSubmit (status) {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl('/myAdoption/audit'),
            method: 'post',
            data: this.$http.adornData({
              'id': this.dataForm.id,
              'animalId': this.animalInfo.id,
              'opinion': this.dataForm.opinion,
              'status': status
            })
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '处理成功',
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
    },
    viewQual () {
      this.qVisible = true
      this.$nextTick(() => {
        this.$refs.qualifications.init(this.dataForm.userId)
      })
    }
  }
}
</script>
