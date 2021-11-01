/**
 * @author chuzhixin 1204505056@qq.com （不想保留author可删除）
 * @description router全局配置，如有必要可分文件抽离，其中asyncRoutes只有在intelligence模式下才会用到，vip文档中已提供路由的基础图标与小清新图标的配置方案，请仔细阅读
 */

import Vue from 'vue'
import VueRouter from 'vue-router'
import Layout from '@/layouts'
import EmptyLayout from '@/layouts/EmptyLayout'
import { publicPath, routerMode } from '@/config'

let sdpFiles = require.context('../views', true, /index\.vue$/)
let sdpRoots = {}
var sdpComponents
sdpFiles.keys().forEach((key) => {
  if (key.indexOf('./demo_') == 0 || key.indexOf('./sdp/') == 0) {
    let root = key.split('/')[1]
    let rootWithoutDemo = root
    if (rootWithoutDemo.indexOf('demo_') == 0) {
      rootWithoutDemo = rootWithoutDemo.substring('demo_'.length)
    }
    if (sdpRoots[root]) {
      sdpComponents = sdpRoots[root]
    } else {
      sdpComponents = []
      sdpRoots[root] = sdpComponents
    }
    let name = key.split('/')[2]

    sdpComponents.push({
      path: name,
      name: name,
      component: () => import(`@/views/${root}/${name}/index`),
      meta: { title: name, permissions: [rootWithoutDemo, root, 'admin'] },
    })
  }
})
sdpComponents = []
for (let p in sdpRoots) {
  let rootWithoutDemo = p
  if (rootWithoutDemo.indexOf('demo_') == 0) {
    rootWithoutDemo = rootWithoutDemo.substring('demo_'.length)
  }

  sdpComponents.push({
    path: '/' + p,
    component: Layout,
    redirect: 'noRedirect',
    name: p,
    meta: {
      title: p,
      icon: 'users-cog',
      permissions: [p, rootWithoutDemo, 'admin'],
    },
    children: [...sdpRoots[p]],
  })
}

Vue.use(VueRouter)
export const constantRoutes = [
  {
    path: '/401',
    name: '401',
    component: () => import('@/views/401'),
    hidden: true,
  },
  {
    path: '/404',
    name: '404',
    component: () => import('@/views/404'),
    hidden: true,
  },
]

export const asyncRoutes = [
  {
    path: '/',
    component: Layout,
    redirect: '/sdp/sdp1_workspace',
  },
  ...sdpComponents,
  {
    path: '/error',
    component: EmptyLayout,
    redirect: 'noRedirect',
    name: 'Error',
    meta: { title: '错误页', icon: 'bug', permissions: ['admin'] },
    children: [
      {
        path: '401',
        name: 'Error401',
        component: () => import('@/views/401'),
        meta: { title: '401' },
      },
      {
        path: '404',
        name: 'Error404',
        component: () => import('@/views/404'),
        meta: { title: '404' },
      },
    ],
  },
  {
    path: '*',
    redirect: '/404',
    hidden: true,
  },
]

const router = new VueRouter({
  base: publicPath,
  mode: routerMode,
  scrollBehavior: () => ({
    y: 0,
  }),
  routes: constantRoutes,
})

export function resetRouter() {
  location.reload()
}

export default router
