import request from '@/utils/request'


export function getFiles(query) {
  return request({
    url: '/qiniu',
    method: 'get',
    params: query
  })
}

export function synchronize() {
  return request({
    url: '/qiniu/synchronize',
    method: 'post'
  })
}

export function del(id) {
  return request({
    url: '/qiniu/' + id,
    method: 'delete'
  })
}

export function downloadExcel() {
  return request({
    responseType: 'blob',
    url: '/qiniu/excel',
    method: 'get'
  })
}
