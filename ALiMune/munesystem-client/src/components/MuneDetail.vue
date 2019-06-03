<template>
  <div>
    <el-table style="width: 100%" height="250" :data="detailList">
      <el-table-column fixed prop="muneName" label="菜名" width="150"></el-table-column>
      <el-table-column fixed prop="price" label="单价" :formatter="formatPrice" width="150"></el-table-column>
      <el-table-column fixed prop="sum" label="数量" width="150"></el-table-column>
      <el-table-column fixed prop="total" :formatter="formatTotal" label="总价" width="150"></el-table-column>
    </el-table>
  </div>
</template>


<<script>
export default {
    props:['detailList'],
    methods: {
        //单价格式化
    formatPrice: function(row, column) {
      let price = row[column.property];
      return (price / 100).toFixed(2) + "元";
    },
    //总价
    formatTotal: function(row, column) {
      let price = row['price'];
      let sum = row['sum'];
      let total=price*sum;
      return (total / 100).toFixed(2) + "元";
    }
    },

}
</script>
