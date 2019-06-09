<template>
  <div>
    <el-table :data="tableData" style="width: 100%">
      <el-table-column label="图片" width="180">
        <template slot-scope="scope">
          <el-image
            style="width: 100px; height: 100px"
            :src="imgFormatter(scope.row.imgPath)"
            fit="fill"
          ></el-image>
        </template>
      </el-table-column>
      <el-table-column label="菜名" width="180" prop="muneName"></el-table-column>
      <el-table-column label="单价" width="180" prop="price" :formatter="priceFormatter"></el-table-column>
      <el-table-column label="数量">
        <!-- 数量 -->
        <template slot-scope="scope">
          <el-input-number
            v-model="num[scope.$index]"
            @change="handleChange(scope.$index)"
            :min="0"
            :max="10"
            label="描述文字"
          ></el-input-number>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
  



<script>
import { getMuneData } from "@/api/mune.js";
export default {
  name: "MuneTable",
  data() {
    return {
      tableData: [],
      num: []
    };
  },
  methods: {
    handleChange() {
      //改动都会计算价格
      var total = 0;
      var map = new Map();
      const tableData = this.tableData;
      //计算价格
      for (var i = 0; i < tableData.length; i++) {
        total += tableData[i].price * this.num[i];
        map.set(tableData[i], this.num[i]);
      }
      var muneJson = JSON.stringify([...map]);
      this.$emit("getSum", total, muneJson);
    },
    getList() {
      const mythis = this;
      var response = getMuneData();
      response.then(response => {
        const MuneData = response.data.list;
        mythis.tableData = MuneData;
        mythis.num = Array(MuneData.length).fill(0);
      });
    },
    priceFormatter(row, column, cellValue) {
      return (cellValue / 100).toFixed(2) + "元";
    },
    imgFormatter(cellValue) {
      return process.env.VUE_APP_IMG_PATH + cellValue;
    }
  },
  mounted() {
    this.getList();
  }
};
</script>


<style>
</style>
