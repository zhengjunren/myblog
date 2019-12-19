import request from '@/utils/request'

export function buildMenus() {
  return request({
    url: '/menus/build',
    method: 'get'
  })
}

export function getMenus() {
  return request({
    url: '/menus',
    method: 'get'
  })
}
