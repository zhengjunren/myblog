import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path*',
        component: () => import('@/views/redirect/index')
      }
    ]
  },
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/401',
    component: () => import('@/views/error-page/401'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/error-page/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '首页', icon: 'dashboard', affix: true }
    }]
  },

  {
    path: '/system',
    component: Layout,
    redirect: '/system/task',
    name: 'System',
    meta: { title: '系统管理', icon: 'example' },
    children: [
      {
        path: 'task',
        name: 'TaskListInline',
        component: () => import('@/views/task/inlineList'),
        meta: { title: '定时任务', icon: 'task' }
      },
      {
        path: 'email',
        name: 'SystemEmail',
        component: () => import('@/views/email/index'),
        meta: { title: '邮件工具', icon: 'email' }
      },
      {
        path: 'permission',
        name: 'Permission',
        component: () => import('@/views/permission/index'),
        meta: { title: '角色列表', icon: 'role' }
      },
      {
        path: 'log',
        name: 'SystemLog',
        component: () => import('@/views/log/list'),
        meta: { title: '操作日志', icon: 'log' }
      },
      {
        path: 'swagger2',
        name: 'SystemSwagger',
        component: () => import('@/views/swagger2/index'),
        meta: { title: '接口文档', icon: 'documentation' }
      }
    ]
  },

  {
    path: '/article',
    component: Layout,
    redirect: '/article/list',
    name: 'Article',
    meta: { title: '文章管理', icon: 'documentation' },
    children: [
      {
        path: 'list',
        name: 'ArticleList',
        component: () => import('@/views/article/list'),
        meta: { title: '文章列表', icon: 'list' }
      },
      {
        path: 'edit/:id(\\d+)/:nickname',
        component: () => import('@/views/article/edit'),
        name: 'EditArticle',
        meta: { title: '编辑文章', noCache: true, activeMenu: '/article/list' },
        hidden: true
      },
      {
        path: 'create',
        component: () => import('@/views/article/create'),
        name: 'CreateArticle',
        meta: { title: '创建文章', icon: 'edit' }
      }
    ]
  },

  {
    path: '/user',
    component: Layout,
    redirect: '/user/list',
    name: 'User',
    meta: { title: '用户管理', icon: 'peoples' },
    children: [
      {
        path: 'list',
        name: 'UserList',
        component: () => import('@/views/user/list'),
        meta: { title: '用户列表', icon: 'list' }
      },
      {
        path: 'index',
        name: 'UserProfileIndex',
        component: () => import('@/views/user/profile/index'),
        meta: { title: '个人信息', icon: 'user'},
      },
      {
        path: 'avatar',
        name: 'UserProfileAvatar',
        component: () => import('@/views/user/profile/avatar'),
        meta: { title: '个人头像', icon: 'user'},
        hidden: true
      },
      {
        path: 'center',
        name: 'UserCenter',
        component: ()=> import('@/views/user/center'),
        meta: {title: '用户中心'},
        hidden: true
      },
    ]
  },
  {
    path: '/nested',
    component: Layout,
    redirect: '/nested/menu1',
    name: 'Nested',
    meta: {
      title: 'Nested',
      icon: 'nested'
    },
    children: [
      {
        path: 'menu1',
        component: () => import('@/views/nested/menu1/index'), // Parent router-view
        name: 'Menu1',
        meta: { title: 'Menu1' },
        children: [
          {
            path: 'menu1-1',
            component: () => import('@/views/nested/menu1/menu1-1'),
            name: 'Menu1-1',
            meta: { title: 'Menu1-1' }
          },
          {
            path: 'menu1-2',
            component: () => import('@/views/nested/menu1/menu1-2'),
            name: 'Menu1-2',
            meta: { title: 'Menu1-2' },
            children: [
              {
                path: 'menu1-2-1',
                component: () => import('@/views/nested/menu1/menu1-2/menu1-2-1'),
                name: 'Menu1-2-1',
                meta: { title: 'Menu1-2-1' }
              },
              {
                path: 'menu1-2-2',
                component: () => import('@/views/nested/menu1/menu1-2/menu1-2-2'),
                name: 'Menu1-2-2',
                meta: { title: 'Menu1-2-2' }
              }
            ]
          },
          {
            path: 'menu1-3',
            component: () => import('@/views/nested/menu1/menu1-3'),
            name: 'Menu1-3',
            meta: { title: 'Menu1-3' }
          }
        ]
      },
      {
        path: 'menu2',
        component: () => import('@/views/nested/menu2/index'),
        meta: { title: 'menu2' }
      }
    ]
  },

  {
    path: 'external-link',
    component: Layout,
    children: [
      {
        path: 'https://zhengjunren.github.io',
        meta: { title: '外链', icon: 'link' }
      }
    ]
  },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
