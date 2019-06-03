<template>
  <div>
    <el-menu
      :default-active="this.$route.path"
      router
      class="el-menu-demo"
      mode="horizontal"
      @select="handleSelect"
    >
      <el-menu-item index="/">下单</el-menu-item>
      <el-menu-item index="/order">查询订单</el-menu-item>
      <el-menu-item index="/mune">菜单管理</el-menu-item>
    </el-menu>
    <el-main>
      <router-view></router-view>
    </el-main>
  </div>
</template>

<script>
export default {
  data() {
    return {
    };
  },
  methods: {
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    }
  },
  components: {}
};
</script>

<style scoped>
/* 
 .el-header {
    background-color: #B3C0D1;
    color: #333;
    line-height: 60px;
  }
  
  .el-aside {
    color: #333;
  } */
</style>
