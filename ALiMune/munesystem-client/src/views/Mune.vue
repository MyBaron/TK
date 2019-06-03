<template>
  <div>
    <!-- 增加菜单按钮 -->
    <el-row>
      <el-col :span="10">
        <router-link to="/mune_edit">
          <el-button type="primary">添加菜单</el-button>
        </router-link>
      </el-col>
    </el-row>
    <!-- 菜单table -->
    <el-row>
      <el-col :span="12">
        <el-table :data="tableData" style="width: 100%">
          <el-table-column label="图片" width="100">
            <template slot-scope="scope">
              <el-image style="width: 100px; height: 100px" :src="scope.row.imgPath" fit="fill"></el-image>
            </template>
          </el-table-column>
          <el-table-column label="菜名" width="180">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.muneName }}</span>
            </template>
          </el-table-column>
          <el-table-column label="单价" width="180">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.price}}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="300">
            <template slot-scope="scope">
              <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
              <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
  </div>
</template>


<script>
import { get, deletes } from "@/utils/http.js";
export default {
  data() {
    return {
      tableData: [{ imgPath: "" }]
    };
  },
  methods: {
    getList() {
      const mythis = this;
      var response = get("http://localhost:8080/mune/");
      response.then(response => {
        const MuneData = response.data.list;
        MuneData.forEach(element => {
          element.price = (element.price / 100).toFixed(2);
          element.imgPath = process.env.VUE_APP_PATH + "img/" + element.imgPath;
        });
        mythis.tableData = MuneData;
        console.log(mythis.tableData);
        mythis.num = Array(MuneData.length).fill(0);
      });
    },
    handleEdit(index, row) {},
    handleDelete(index, row) {
      let id = row.id;
      this.$confirm("确认删除吗？")
        .then(_ => {
          deletes("http://localhost:8080/mune/" + id).then(res => {
            this.$message.success("删除成功");
            this.$router.go(0);
          });
        })
        .catch(_ => {});
    }
  },
  mounted() {
    this.getList();
  }
};
</script>


<style>
</style>
