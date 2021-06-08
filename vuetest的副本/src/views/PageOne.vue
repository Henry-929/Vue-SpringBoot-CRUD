<template>
  <div>
    <el-table
      :data="tableData"
      border
      style="width: 100%">
      <el-table-column
        fixed
        prop="id"
        label="id"
        width="200">
      </el-table-column>
      <el-table-column
        prop="name"
        label="书名"
        width="250">
      </el-table-column>
      <el-table-column
        prop="author"
        label="作者"
        width="250">
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        width="250">
        <template slot-scope="scope">
          <el-button type="primary" icon="el-icon-edit" @click="editor(scope.row)"></el-button>
          <el-button type="primary" icon="el-icon-delete" @click="deteleById(scope.row)"></el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      background
      layout="prev, pager, next"
      :page-size="pageSize"
      :total="total"
      @current-change="page">
    </el-pagination>
  </div>
</template>

<script>
export default {
  methods: {
    editor(row) {
      // console.log(row);
      this.$router.push({
        path: '/pageThree',
        query: {
          id: row.id
        }
      })
    },
    deteleById(row) {
      // console.log(row)
      this.axios.delete('http://localhost:8181/book/deteleById/'+row.id).then((resp)=>{
        // console.log(resp)
        this.$confirm('《'+ row.name +'》将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          callback: action => {
            window.location.reload();
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
          }
        })
      })
    },
    page(currentPage){
      this.axios.get('http://localhost:8181/book/selectAll/'+ currentPage +'/'+ this.pageSize +'').then((resp)=>{
        // console.log(resp)
        this.tableData=resp.data.content
        this.total=resp.data.totalElements
      })
    }
  },

  data() {
    return {
      pageSize: 6,
      total:null,
      tableData:null
    }
  },

  created() {
    this.axios.get('http://localhost:8181/book/selectAll/1/'+ this.pageSize +'').then((resp)=>{
      // console.log(resp)
      this.tableData=resp.data.content
      this.total=resp.data.totalElements
    })
  }
}
</script>

<style scoped>

</style>
