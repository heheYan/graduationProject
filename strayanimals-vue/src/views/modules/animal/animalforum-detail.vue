<template>
  <el-dialog
    title="信息详情"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <h1 style="text-align: center" v-model="dataForm.title"></h1>
    <h3 style="text-align: center"><p>发帖人：<span>{{dataForm.userName}}</span></p><p>最后修改时间：<span>{{dataForm.updateTime}}</span></p></h3>
    <p v-html="dataForm.content" style="max-width: 100%"></p>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          id: 0,
          userId: '',
          userName: '',
          status: '',
          content: '',
          createTime: '',
          isDelete: '',
          title: '',
          updateTime: ''
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/animal/animalforum/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.content = data.animalForum.content
                this.dataForm.title = data.animalForum.title
                this.dataForm.updateTime = data.animalForum.updateTime
                this.dataForm.userName = data.animalForum.userName
              }
            })
          }
        })
      }
    }
  }
</script>
