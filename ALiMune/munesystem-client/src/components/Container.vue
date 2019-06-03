<template>
  <div>
    <el-menu
      :default-active="$route.path"
      class="el-menu-demo"
      mode="horizontal"
      @select="handleSelect"
    >
      <el-menu-item index="/">下单</el-menu-item>
      <el-menu-item index="/order">查询订单</el-menu-item>
    </el-menu>
    <el-main>
      <router-view></router-view>
    </el-main>
  </div>
</template>

<script>
import mune from "./Mune.vue";
export default {
  data() {
    return {
      activeIndex: "1",
      activeIndex2: "2"
    };
  },
  methods: {
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    }
  },
  components: {
    mune: mune
  }
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
