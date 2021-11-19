<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="80px">
      <el-form-item label="动物名称" prop="name">
        <el-input v-model="dataForm.name" placeholder="请输入动物名称"></el-input>
      </el-form-item>
      <el-form-item label="所属类型" prop="type">
        <el-input v-model="dataForm.type" placeholder=""></el-input>
      </el-form-item>
      <el-form-item label="照片" prop="type">
        <el-upload
          class="upload-demo"
          :action="uploadUrl"
          :headers="myHeader"
          :multiple="false"
          :limit="1"
          :on-exceed="handleExceed"
          :on-remove="handleRemove"
          list-type="picture"
          :file-list="fileList">
          <el-button size="small" type="primary">点击上传</el-button>
          <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
        </el-upload>
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
      uploadUrl: 'http://localhost:8080/stray-anmial/sys/attach/upload',
      fileList: [],
      dataForm: {
        id: 0,
        name: '',
        icon: '',
        imgurl: '',
        type: '',
        register: '',
        registerName: '',
        registerDate: '',
        status: '',
        isAdopt: '',
        adopter: '',
        adopterName: '',
        adoptDate: ''
      },
      dataRule: {
        name: [
          {required: true, message: '不能为空', trigger: 'blur'}
        ],
        icon: [
          {required: true, message: '不能为空', trigger: 'blur'}
        ],
        imgurl: [
          {required: true, message: '不能为空', trigger: 'blur'}
        ],
        type: [
          {required: true, message: '不能为空', trigger: 'blur'}
        ],
        register: [
          {required: true, message: '登记入库人员不能为空', trigger: 'blur'}
        ],
        registerName: [
          {required: true, message: '不能为空', trigger: 'blur'}
        ],
        registerDate: [
          {required: true, message: '不能为空', trigger: 'blur'}
        ],
        status: [
          {required: true, message: '动物状态，0：初始登记，1：已清洗，2：已防疫不能为空', trigger: 'blur'}
        ],
        isAdopt: [
          {required: true, message: '是否被领养，0：否，1：是不能为空', trigger: 'blur'}
        ],
        adopter: [
          {required: true, message: '领养人不能为空', trigger: 'blur'}
        ],
        adopterName: [
          {required: true, message: '不能为空', trigger: 'blur'}
        ],
        adoptDate: [
          {required: true, message: '领养时间不能为空', trigger: 'blur'}
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
              this.dataForm.icon = data.animalInfo.icon
              this.dataForm.imgurl = data.animalInfo.imgurl
              this.dataForm.type = data.animalInfo.type
              this.dataForm.register = data.animalInfo.register
              this.dataForm.registerName = data.animalInfo.registerName
              this.dataForm.registerDate = data.animalInfo.registerDate
              this.dataForm.status = data.animalInfo.status
              this.dataForm.isAdopt = data.animalInfo.isAdopt
              this.dataForm.adopter = data.animalInfo.adopter
              this.dataForm.adopterName = data.animalInfo.adopterName
              this.dataForm.adoptDate = data.animalInfo.adoptDate
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
            url: this.$http.adornUrl(`/animal/animalinfo/${!this.dataForm.id ? 'save' : 'update'}`),
            method: 'post',
            data: this.$http.adornData({
              'id': this.dataForm.id || undefined,
              'name': this.dataForm.name,
              'icon': this.dataForm.icon,
              'imgurl': this.dataForm.imgurl,
              'type': this.dataForm.type,
              'register': this.dataForm.register,
              'registerName': this.dataForm.registerName,
              'registerDate': this.dataForm.registerDate,
              'status': this.dataForm.status,
              'isAdopt': this.dataForm.isAdopt,
              'adopter': this.dataForm.adopter,
              'adopterName': this.dataForm.adopterName,
              'adoptDate': this.dataForm.adoptDate
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
    handleExceed (files, fileList) {
      console.log(files, fileList)
      this.$message.warning('只能选择一张照片')
    },
    handleRemove (files, fileList) {
      console.log(files, fileList)
      this.$message.warning('移除')
    }
  }
}
</script>
