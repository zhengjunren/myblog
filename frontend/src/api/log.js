import request from '@/utils/request'

export function getInfoLog(query) {
  return request({
    url: '/logs/INFO',
    method: 'get',
    params: query
  })
}

export function getErrorLog(query) {
  return request({
    url: '/logs/ERROR',
    method: 'get',
    params: query
  })
}

export function getErrorLogDetail(id) {
  return request({
    url: '/logs/error/' + id,
    method: 'get'
  })
}

export function deleteLogs(type) {
  return request({
    url: '/logs/' + type,
    method: 'delete'
  })
}

export function downloadErrorLogExcel() {
  return request({
    responseType: 'blob',
    url: '/logs/error/excel',
    method: 'get'
  })
}

export function downloadInfoLogExcel() {
  return request({
    responseType: 'blob',
    url: '/logs/info/excel',
    method: 'get'
  })
}
