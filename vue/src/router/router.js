import Vue       from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path     : '/',
    name     : 'blog',
    component: () => import('../views/Blog.vue')
  },
  {
    path     : '/bookmark',
    name     : 'bookmark',
    component: () => import('../views/Bookmark.vue')
  },
  {
    path     : '/dictionary',
    name     : 'dictionary',
    component: () => import('../views/Dictionary.vue')
  },
  {
    path     : '/manager',
    name     : 'manager',
    component: () => import('../views/Manager.vue')
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
