import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/auth/login',
    method: 'post',
    data
  })
}

export function getCode() {
  return request({
    url: '/auth/code',
    method: 'get'
  })
}

export function getInfo() {
  return request({
    url: '/auth/info',
    method: 'get'
  })
}

export function logout() {
  return request({
    url: '/auth/logout',
    method: 'post'
  })
}

export function getOnlineUsers(query) {
  return request({
    url: '/online',
    method: 'get',
    params: query
  })
}

export function kickOut(key) {
  return request({
    url: '/online',
    method: 'delete',
    params: {
      key: key
    }
  })
}

export function downloadExcel() {
  return request({
    responseType: 'blob',
    url: '/online/excel',
    method: 'get'
  })
}
