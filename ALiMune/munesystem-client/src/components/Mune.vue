<template>
  <div>
    <el-form :inline="true" :model="formInline" ref="formInline" class="demo-form-inline">
      <el-form-item
        label="桌号"
        prop="sitNumber"
        :rules="
             [{ required: true,type:'number', message: '必须输入数字桌号',trigger:'blur' }]
             "
      >
        <el-input v-model.number="formInline.sitNumber" placeholder="桌号"></el-input>
      </el-form-item>
      <div>
        <mune-table @getSum="getSum"></mune-table>
      </div>
      <el-row :gutter="20" type="flex" class="row-bg" justify="end">
        <el-form-item class="mune-pay-button" :label="sumStr">
          <el-button label-position="right" type="primary" @click="open('formInline')">结账</el-button>
        </el-form-item>
      </el-row>
    </el-form>
  </div>
</template>

<script>
import { saveOrderData } from "@/api/order.js";
import MuneTable from "./MuneTable.vue";
import { initWebSocket } from "@/utils/webSocket.js";
export default {
  name: "Mune",
  data() {
    return {
      formInline: {
        sitNumber: ""
      },
      rules: {
        sitNumber: [{ required: true, message: "请输入活动名称" }]
      },
      sum: "0",
      orderData: [],
      alterOrderMgs: ""
    };
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      this.websock = initWebSocket();
      this.websock.onopen = this.websocketonopen;
      this.websock.onerror = this.websocketonerror;
      this.websock.onmessage = this.websocketonmessage;
      this.websock.onclose = this.websocketclose;
    },
    websocketonopen: function() {
      console.log("WebSocket连接成功");
    },
    websocketonerror: function(e) {
      console.log("WebSocket连接发生错误", e);
    },
    websocketonmessage: function(e) {
      var da = JSON.parse(e.data);
      console.log(da);
      this.message = da;
    },
    websocketclose: function(e) {
      console.log("connection closed (" + e.code + ")");
    },
    onSubmit() {},
    getSum(total, muneJson) {
      var arr = JSON.parse(muneJson);
      this.sum = (total/100).toFixed(2);
      this.orderData = [];
      this.alterOrderMgs = "";
      //遍历获取下单
      for (var i = 0; i < arr.length; i++) {
        var obj = arr[i][0];
        var sum = arr[i][1];
        var muneId = obj.id;
        if (sum > 0) {
          this.orderData.push({ muneId, sum });
          this.alterOrderMgs += "<p>" + obj.muneName + "     " + sum + "份</p>";
        }
      }
      this.alterOrderMgs += "<p>" + this.sumStr + "</p>";
    },
    open(formInline) {
      this.$refs[formInline].validate(valid => {
        if (valid) {
          this.myconfirm();
        }
      });
      return false;
    },
    myconfirm() {
      this.$confirm(
        this.alterOrderMgs,
        "第" + this.formInline.sitNumber + "号的账单",
        {
          dangerouslyUseHTMLString: true,
          confirmButtonText: "结账",
          cancelButtonText: "取消"
        }
      )
        .then(() => {
          var mydata = this.orderData;
          var orderData = new Object();
          orderData.orderData = mydata;
          orderData.table = this.formInline.sitNumber;
          saveOrderData(orderData);
          this.$message({
            type: "success",
            message: "结账成功!"
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消下单"
          });
        });
    }
  },
  components: {
    MuneTable: MuneTable
  },
  computed: {
    sumStr: function() {
      return "总价:" + this.sum + " 元";
    }
  }
};
</script>

<style  scoped>
.mune-font {
  text-align: center;
  line-height: 62px;
}
</style>


