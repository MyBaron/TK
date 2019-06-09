<template>
  <div>
   
    <!-- 菜单table -->
    <el-row >
      <el-col >
        <el-table :data="tableData" style="width: 100%">
          <el-table-column label="图片" style="width:100%">
            <template slot-scope="scope">
              <el-image style="width: 100px; height: 100px" :src="scope.row.imgPath" fit="fill"></el-image>
            </template>
          </el-table-column>
          <el-table-column label="菜名" style="width:100%" prop="muneName">
          </el-table-column>
          <el-table-column label="单价"  style="width:100%" :formatter="moneyFormat" prop="price">
          </el-table-column>
          <el-table-column label="操作" style="width:100%">
            <template slot-scope="scope">
              <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
              <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
     <!-- 增加菜单按钮 -->
    <el-row type="flex" justify="end">
      <el-col :span="3" >
        <router-link to="/mune_edit">
          <el-button type="primary">添加菜单</el-button>
        </router-link>
      </el-col>
    </el-row>
  </div>
</template>


<script>
import { getMuneData ,deleteMuneData} from "@/api/mune.js";
export default {
  data() {
    return {
      tableData: [{ imgPath: "" }]
    };
  },
  methods: {
    moneyFormat(row, column) {
      console.log(column.property);
      var price = row[column.property];
      if (price == undefined) {
        return "";
      }
      return (price / 100).toFixed(2) + "元";
    },
    getList() {
      const mythis = this;
      var response = getMuneData();
      response.then(response => {
        const MuneData = response.data.list;
        MuneData.forEach(element => {
          element.imgPath = process.env.VUE_APP_IMG_PATH + element.imgPath;
        });
        mythis.tableData = MuneData;
        mythis.num = Array(MuneData.length).fill(0);
      });
    },
    handleEdit(index, row) {
      this.$router.push({ name: "mune_edit", params: { id: row.id } });
    },
    handleDelete(index, row) {
      let id = row.id;
      this.$confirm("确认删除吗？")
        .then(() => {
          deleteMuneData({"id":id}).then(() => {
            this.$message.success("删除成功");
            this.$router.go(0);
          });
        })
        .catch(() => {});
    }
  },
  mounted() {
    this.getList();
  }
};
</script>


<style>
</style>
