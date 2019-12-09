import request from '@/utils/request'

export const SECURITY_URL = process.env.VUE_APP_SECURITY_URL
export const SEARCH_URL = process.env.VUE_APP_SEARCH_URL

export function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    baseURL: SECURITY_URL,
    data
  })
}

export function getInfo() {
  return request({
    url: '/user/info',
    method: 'get'
  })
}

export function logout(token) {
  return request({
    url: '/user/logout',
    method: 'post',
    baseURL: SECURITY_URL,
    params: { access_token: token }
  })
}

export function search(data, page, limit) {
  return request({
    url: '/search/user',
    method: 'post',
    baseURL: SEARCH_URL,
    params: { page: page, limit: limit },
    data
  })
}

export function fetchList(query) {
    return request({
        url: '/user/list',
        method: 'get',
        params: query
    })
}

export function updateUser(data, oldRoleId) {
  return request({
    url: '/user/',
    method: 'post',
    params: {oldRoleId:oldRoleId},
    data
  })
}

export function modifyStatus(username, status) {
  return request({
    url: '/user/status',
    method: 'post',
    data: {
      'value': status,
      'username': username
    }
  })
}

export function updateProfile(data) {
  return request({
    url: '/user/profile',
    method: 'post',
    data
  })
}

export function updateAvatar(data) {
  return request({
    url: '/user/avatar',
    method: 'post',
    data
  })
}

export function updatePassword(data) {
  return request({
    url: '/user/password',
    method: 'put',
    data
  })
}

export function getExcel() {
  return request({
    responseType: 'blob',
    url: '/excel/user',
    method: 'get'
  })
}
