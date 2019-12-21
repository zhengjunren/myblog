import request from '@/utils/request'

// 获取所有的菜单树
export function getRoles(query) {
  return request({
    url: '/roles',
    method: 'get',
    params: query
  })
}

export function updatePermission(data) {
  return request({
    url: '/roles/permission',
    method: 'put',
    data
  })
}
