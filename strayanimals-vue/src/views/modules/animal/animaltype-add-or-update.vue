<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="80px">
      <el-form-item label="名称" prop="name">
        <el-input v-model="dataForm.name" placeholder="名称"></el-input>
      </el-form-item>
      <el-form-item label="上级分类" prop="parentId">
        <el-popover
          ref="typeListPopover"
          placement="bottom-start"
          trigger="click">
          <el-tree
            :data="typeList"
            :props="typeListTreeProps"
            node-key="id"
            ref="typeListTree"
            @current-change="typeListTreeCurrentChangeHandle"
            :default-expand-all="true"
            :highlight-current="true"
            :expand-on-click-node="false">
          </el-tree>
        </el-popover>
        <el-input v-model="dataForm.parentName" v-popover:typeListPopover :readonly="true" placeholder="点击选择上级分类"
                  class="type-list__input"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { treeDataTranslate } from '@/utils'
export default {
  data () {
    return {
      visible: false,
      dataForm: {
        id: 0,
        name: '',
        parentId: 0,
        parentName: ''
      },
      typeList: [],
      typeListTreeProps: {
        label: 'name',
        children: 'children'
      },
      dataRule: {
        name: [
          {required: true, message: '名称不能为空', trigger: 'blur'}
        ],
        parentName: [
          {required: true, message: '上级分类不能为空', trigger: 'change'}
        ]
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
      }).then(() => {
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
        })
      }).then(() => {
        if (!this.dataForm.id) {
          // 新增
          this.typeListTreeSetCurrentNode()
        } else {
          this.$http({
            url: this.$http.adornUrl(`/animal/animaltype/info/${this.dataForm.id}`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataForm.name = data.animalType.name
              this.dataForm.parentId = data.animalType.parentId
              this.dataForm.parentName = data.animalType.parentName
              this.typeListTreeSetCurrentNode()
            }
          })
        }
      })
    },
    // 菜单树选中
    typeListTreeCurrentChangeHandle (data, node) {
      this.dataForm.parentId = data.id
      this.dataForm.parentName = data.name
    },
    // 父类树设置当前选中节点
    typeListTreeSetCurrentNode () {
      this.$refs.typeListTree.setCurrentKey(this.dataForm.parentId)
      this.dataForm.parentName = (this.$refs.typeListTree.getCurrentNode() || {})['name']
    },
    // 表单提交
    dataFormSubmit () {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl(`/animal/animaltype/${!this.dataForm.id ? 'save' : 'update'}`),
            method: 'post',
            data: this.$http.adornData({
              'id': this.dataForm.id || undefined,
              'name': this.dataForm.name,
              'parentId': this.dataForm.parentId,
              'createId': this.dataForm.createId,
              'createDate': this.dataForm.createDate
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
