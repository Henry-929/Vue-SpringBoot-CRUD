import Vue from 'vue'
import Router from 'vue-router'
import PageOne from "../views/PageOne";
import PageTwo from "../views/PageTwo";
import PageFour from "../views/PageFour";
import Index from "../views/Index";
import PageThree from "../views/PageThree";

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    // {
    //   path: '/book',
    //   name: 'Book',
    //   component: Book
    // }
    {
      path: '/',
      name: '图书管理',
      component:Index,
      redirect: '/pageOne',
      children:[
        {
          path: '/pageOne',
          name: '查询图书',
          component:PageOne,
          show: true
        },
        {
          path: 'pageTwo',
          name: '添加图书',
          component:PageTwo,
          show: true
        },
        {
          path: '/pageThree',
          name: '修改页面',
          component:PageThree,
          show: false
        },
        {
          path: '/pageFour',
          name: '页面四',
          component:PageFour,
          show: false
        }
      ]
    }
  ]

})
