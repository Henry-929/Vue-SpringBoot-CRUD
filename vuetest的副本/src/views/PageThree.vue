<template>
  <div>
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm" style="width: 60%">

      <el-form-item label="id">
        <el-input v-model="ruleForm.id" readonly></el-input>
      </el-form-item>
      <el-form-item label="书名" prop="name">
        <el-input v-model="ruleForm.name"></el-input>
      </el-form-item>
      <el-form-item label="作者" prop="author">
        <el-input v-model="ruleForm.author"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">修改</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "PageThree.vue",
  data() {
    return {
      ruleForm: {
        id: '',
        name: '',
        author: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入图书名称', trigger: 'blur' },
        ],
        author: [
          { required: true, message: '请输入作者姓名', trigger: 'blur' },
        ]
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // console.log(this.ruleForm)
          this.axios.put('http://localhost:8181/book/update/',this.ruleForm).then((resp)=>{
            if ("success"==resp.data){
              this.$router.push('/pageOne')
              this.$message({
                message: '修改成功',
                type: 'success'
              });
            }else {
              this.$message.error('修改失败');
            }
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  },
  created() {
    // alert(this.$route.query.id)
    this.axios.get('http://localhost:8181/book/selectById/'+this.$route.query.id).then((resp)=>{
      // console.log(resp)
      this.ruleForm=resp.data
    })
  }
}
</script>

<style scoped>

</style>
