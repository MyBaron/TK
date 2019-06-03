<template>
  <div>
    <el-table :data="tableData" style="width: 100%">
      <el-table-column label="菜名" width="180">
        <template slot-scope="scope">
          <!-- <i class="el-icon-time"></i> -->
          <span style="margin-left: 10px">{{ scope.row.muneName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="单价" width="180">
        <template slot-scope="scope">
          <!-- <i class="el-icon-time"></i> -->
          <span style="margin-left: 10px">{{ scope.row.price}}</span>
        </template>
      </el-table-column>
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
import { get } from "@/utils/http.js";
export default {
  name: "MuneTable",
  data() {
    return {
      tableData: [
        // {
        //   id: "0",
        //   muneName: "酸甜排骨",
        //   price: "25元",
        //   number: ""
        // },
        // {
        //   id: "1",
        //   muneName: "烤鱼",
        //   price: "80元",
        //   number: ""
        // }
      ],
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
      var response = get("http://localhost:8080/mune/");
      response.then(response => {
        const MuneData = response.data.list;
        MuneData.forEach(element => {
          element.price = (element.price / 100).toFixed(2);
        });
        mythis.tableData = MuneData;
        mythis.num = Array(MuneData.length).fill(0);
      });
    }
  },
  mounted() {
    this.getList();
  }
};
</script>


<style>
</style>
