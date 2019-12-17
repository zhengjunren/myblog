import request from '@/utils/request'

export function buildMenus() {
  return request({
    url: '/menus/build',
    method: 'get'
  })
}
