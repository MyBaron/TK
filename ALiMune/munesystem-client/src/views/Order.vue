<template>
  <div>
    <div>
      <el-row type="flex" justify="end">
        <el-col :span="2">
          <el-select v-model="value" placeholder="查询条件" @change="changeSelect">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-col>
        <el-col :span="6">
          <el-date-picker
            v-model="orderDate"
            :type="selectDateType"
            :value-format="selectDateFormat"
            @change="selectDate"
            placeholder="选择日期"
          ></el-date-picker>
        </el-col>
      </el-row>
    </div>
    <el-table
      :data="tableData.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
      style="width: 100%"
      @expand-change="enpandChange"
    >
      <el-table-column type="expand">
        <mune-detail v-bind:detailList="detailList" slot-scope="props"></mune-detail>
      </el-table-column>
      <el-table-column label="订单号" prop="orderId"></el-table-column>
      <el-table-column label="日期" :formatter="dateFormat" prop="createTime"></el-table-column>
      <el-table-column style="max-width:150px" label="桌号" prop="tableNumber"></el-table-column>
      <el-table-column
        style="max-width:150px"
        label="金额"
        :formatter="moneyFormat"
        prop="totalMoney"
      ></el-table-column>
    </el-table>
  </div>
</template>

<script>
import { get } from "@/utils/http.js";
import muneDetail from "@/components/MuneDetail.vue";
import moment from "moment";
export default {
  name: "orderData",
  components: {
    muneDetail: muneDetail
  },
  data() {
    return {
      detailList: [],
      options: [
        {
          value: "0",
          label: "按天查询",
          format: "yyyy-MM-dd",
          type:"date"
        },
        {
          value: "1",
          label: "按月查询",
          format: "yyyy-MM",
          type:"month"
        },
        {
          value: "2",
          label: "按年查询",
          format: "yyyy",
          type:"year"
        }
        // {
        //   value: "3",
        //   label: "全部查询",
        //   format:"yyyy-MM-dd"
        // }
      ],
      value: "",
      page: "",
      orderDate: "",
      tableData: [],
      search: "",
      selectDateFormat: "yyyy-MM-dd",
      selectDateType:"date"
    };
  },
  methods: {
    changeSelect(value) {
      console.log("当前选中的值" + value);
      this.selectDateType = this.options[value].type;
      this.selectDateFormat = this.options[value].format;
      this.orderDate="";
    },
    selectDate() {
      let type = this.value;
      if (type == "" || type == undefined) {
        type = 0;
      }
      let obj = {
        date: this.orderDate,
        type: type
      };
      get("http://localhost:8080/order/", obj).then(res => {
        this.tableData = [];
        this.tableData = res.data.content;
        this.page = res.data.page;
      });
    },
    //时间格式化
    dateFormat: function(row, column) {
      var date = row[column.property];
      if (date == undefined) {
        return "";
      }
      //用的是monent.js
      return moment(date).format("YYYY-MM-DD HH:mm:ss");
    },

    moneyFormat(row, column) {
      var price = row[column.property];
      if (price == undefined) {
        return "";
      }
      return (price / 100).toFixed(2) + "元";
    },
    enpandChange(row, expandedRows) {
      console.log(row);
      console.log(expandedRows);
      console.log("---");
      if (row === expandedRows[0]) {
        console.log("yes");
        let obj = {
          id: row.orderId
        };
        get("http://localhost:8080/order/order_deatil", obj).then(res => {
          console.log(res.data);
          this.detailList = res.data.detail;
        });
      } else {
        expandedRows.splice(0, 1);
        console.log("删除了一行");
      }
    }
  },
  mounted() {
    get("http://localhost:8080/order/").then(response => {
      this.tableData = response.data.content;
      this.page = response.data.page;
    });
  }
};
</script>

<style  scoped>
.cell {
  max-width: 150px;
}
</style>
