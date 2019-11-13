import request from '@/utils/request'

export function login(data) {
  return request({
    url: 'http://localhost:8000/user/login',
    method: 'post',
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
    url: 'http://localhost:8000/user/logout',
    method: 'post',
    params: { access_token: token }
  })
}
