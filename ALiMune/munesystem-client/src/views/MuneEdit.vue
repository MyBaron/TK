<template>
  <div>
    <el-form ref="form" :rules="rules" :model="muneData" label-width="80px">
      <el-form-item label="菜名" prop="muneName">
        <el-col :span="6">
          <el-input v-model="muneData.muneName"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="价格" prop="price">
        <el-col :span="6">
          <!-- .number 是数字类型 -->
          <el-input v-model.number="muneData.price"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="图片">
        <el-upload
          class="avatar-uploader"
          action="http://localhost:8080/upload/img"
          :show-file-list="false"
          :auto-upload="true"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
        >
          <img v-if="imageUrl" :src="imageUrl" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">立即创建</el-button>
          <el-button>取消</el-button>
        </el-form-item>
      </el-form-item>
    </el-form>
  </div>
</template>

<script >
import { post} from "@/utils/http.js";
export default {
  data() {
    return {
      muneData: {
        id: "",
        muneName: "",
        imgPath: ""
      },
      imageUrl: "",
      rules: {
        muneName: [{ required: true, message: "请输入菜名", trigger: "blur" }],
        price: [
          {
            type: "number",
            required: true,
            message: "请输入价格（只接收数字）",
            trigger: "blur"
          }
        ]
      }
    };
  },
  methods: {
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
      this.muneData.imgPath = res.data.path;
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      return isJPG && isLt2M;
    },
    onSubmit() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          post(process.env.VUE_APP_PATH + "/mune", this.muneData).then(res => {
            if (res.data.code == 1) {
              this.$message.success("添加成功");
              this.$router.push('/mune')
            } else {
              this.$message.error("添加失败！！");
            }
          });
        } else {
          return false;
        }
      });
    }
  }
};
</script>

<style scoped>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>

