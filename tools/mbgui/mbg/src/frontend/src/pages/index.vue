<template>
  <div class="wrapper">
    <nav class="main-header navbar navbar-expand-md navbar-light navbar-white">
      <div class="container">
        <span class="brand-text font-weight-light">Devpl</span>
        <div class="collapse navbar-collapse order-3" id="navbarCollapse">
          <ul class="navbar-nav">
            <li class="nav-item dropdown">
              <router-link to="/table" class="nav-link">Table代码生成</router-link>
            </li>
            <li class="nav-item">
              <router-link to="/sql" class="nav-link">SQL代码生成</router-link>
            </li>
            <li class="nav-item">
              <router-link to="/dbmanage" class="nav-link">连接管理</router-link>
            </li>
            <li class="nav-item">
              <router-link to="/editor" class="nav-link">编辑器</router-link>
            </li>
            <li class="nav-item">
              <a href="https://github.com/davidfantasy/mybatis-plus-generator-ui" class="nav-link">
                <i class="fa fa-github"></i>
                GitHub
              </a>
            </li>
          </ul>
        </div>
      </div>
    </nav>
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper" v-cloak>
      <nuxt-child :key="viewKey"/>
    </div>
  </div>
</template>
<script>

export default {
  props: [],
  beforeDestroy: function () {
  },
  computed: {
    viewKey() {
      return this.$route.name !== undefined
          ? this.$route.name + +new Date()
          : this.$route + new Date();
    }
  },
  data() {
    return {};
  },
  mounted: function () {

  },
  methods: {}
};
</script>
<style>
</style>


