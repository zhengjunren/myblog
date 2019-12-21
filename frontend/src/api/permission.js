import request from '@/utils/request'

// 获取所有的菜单树
export function getPermissionTree() {
  return request({
    url: '/permissions/tree',
    method: 'get'
  })
}
