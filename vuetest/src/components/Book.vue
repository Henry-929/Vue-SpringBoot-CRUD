<template>
  <div>
    <table>
      <tr>
        <td>编号</td>
        <td>图书名称</td>
        <td>作者</td>
      </tr>
      <tr v-for="item in books">
        <td>{{item.id}}</td>
        <td>{{item.name}}</td>
        <td>{{item.author}}</td>
      </tr>
    </table>
  </div>
</template>

<script>
export default {
  name: "Book.vue",
  data(){
    return{
      msg:"hello ,world",
      books:[
        {
          id: 1,
          name: 'java零基础',
          author: '宁南'
        },
        {
          id: 2,
          name: 'vue零基础',
          author: '张三'
        },
        {
          id: 3,
          name: 'spring零基础',
          author: '李四'
        }
      ]
    }
  },
  created() {
    this.axios.get('http://localhost:8181/book/selectAll').then((resp)=>{
      console.log(resp);
      this.books=resp.data
    })
  }
}
</script>

<style scoped>

</style>
