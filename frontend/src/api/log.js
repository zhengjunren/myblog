import request from '@/utils/request'

export function fetchList(query) {
  return request({
    url: 'http://localhost:8600/log/list',
    method: 'get',
    params: query
  })
}
