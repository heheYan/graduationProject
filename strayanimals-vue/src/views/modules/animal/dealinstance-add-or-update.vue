<template>
  <el-dialog
    title="流程处理"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-steps :active="activeStep"  finish-status="success" align-center style="margin-top: 20px">
      <el-step title="登记入库"></el-step>
      <el-step title="清洗检查" ></el-step>
      <el-step title="防疫除菌"></el-step>
    </el-steps>
    <br>
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="80px">
      <el-form-item label="动物名称" prop="name">
        <el-input v-model="dataForm.name" disabled placeholder="请输入动物名称"></el-input>
      </el-form-item>
      <el-form-item label="所属类型" prop="type">
        <el-input v-model="dataForm.typename" :readonly="true" placeholder="请选择所属类型" disabled></el-input>
      </el-form-item>
      <el-form-item ref="uploadElement" label="照片" prop="imgurl">
        <el-upload
          class="upload-demo"
          :action="uploadUrl"
          :headers="myHeader"
          :multiple="false"
          :limit="1"
          :on-exceed="handleExceed"
          :on-remove="handleRemove"
          :on-success="handleSuccess"
          list-type="picture"
          disabled
          :file-list="fileList">
          <el-button size="small" type="primary">点击上传</el-button>
          <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
        </el-upload>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">提交</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { treeDataTranslate } from '@/utils'
export default {
  data () {
    return {
      visible: false,
      activeStep: 0,
      uploadUrl: 'http://localhost:8080/stray-anmial/sys/attach/upload',
      fileList: [],
      dataForm: {
        id: 0,
        name: '',
        imgurl: '',
        type: 1,
        typename: ''
      },
      typeList: [],
      typeListTreeProps: {
        label: 'name',
        children: 'children'
      },
      dataRule: {
        name: [
          {required: true, message: '不能为空', trigger: 'blur'}
        ],
        imgurl: [
          {required: true, message: '请上传图片', trigger: 'blur'}
        ],
        typeName: [
          {required: true, message: '所属类型不能为空', trigger: 'change'}
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
      this.dataForm.id = id || 0
      this.$http({
        url: this.$http.adornUrl('/animal/animaltype/select'),
        method: 'get',
        params: this.$http.adornParams()
      }).then(({data}) => {
        this.typeList = treeDataTranslate(data.typeList, 'id')
      })
      this.fileList = []
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (this.dataForm.id) {
          this.$http({
            url: this.$http.adornUrl(`/animal/animalinfo/info/${this.dataForm.id}`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataForm.name = data.animalInfo.name
              this.dataForm.imgurl = data.animalInfo.imgurl
              this.dataForm.type = data.animalInfo.type
              this.dataForm.typename = data.animalInfo.typename
              if (data.animalInfo.imgurl) {
                this.fileList.push({'url': data.animalInfo.imgurl})
              }
              this.activeStep = data.animalInfo.status + 1
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
            url: this.$http.adornUrl(`/animal/animalinfo/deal`),
            method: 'post',
            data: this.$http.adornData({
              'id': this.dataForm.id
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
    },
    handleSuccess (data) {
      if (data && data.code === 0) {
        this.dataForm.imgurl = data.filepath
        this.$message.success('上传成功')
        this.$refs.uploadElement.clearValidate()
      }
    },
    handleExceed (files, fileList) {
      this.$message.warning('只能选择一张照片')
    },
    handleRemove (files, fileList) {
      this.$message.warning('移除成功')
      this.dataForm.imgurl = ''
    }
  }
}
</script>
