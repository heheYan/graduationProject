<template>
  <div class="mod-demo-ueditor">
    <script :id="ueId" class="ueditor-box" type="text/plain" style="width: 100%; height: 260px;" v-model="ueContent"></script>
  </div>
</template>

<script>
  import ueditor from 'ueditor'
  export default {
    data () {
      return {
        ue: null,
        ueId: `J_ueditorBox_${new Date().getTime()}`,
        ueContent: ''
      }
    },
    props: {
      value: '',
      config: {}
    },
    mounted () {
      const _this = this
      this.ue = ueditor.getEditor(this.ueId, {
        serverUrl: '//localhost:8080/realestate/ueditor/server', // 服务器统一请求接口路径
        zIndex: 3000
      })
      // 初始化UE
      this.ue.addListener('ready', function () {
        _this.ue.setContent(_this.value)
        // 确保UE加载完成后，放入内容。
      })
    },
    methods: {
      getContent () {
        return this.ue.getContent()
      },
      setContent (content) {
        this.ue.setContent(content)
      },
      destroyed () {
        this.ue.setContent('')
      }
    }
  }
</script>

<style lang="scss">
  .mod-demo-ueditor {
    position: relative;
    z-index: 510;
    > .el-alert {
      margin-bottom: 10px;
    }
  }
</style>
