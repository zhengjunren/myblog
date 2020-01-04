import request from '@/utils/request'

export function getOwnInfo() {
  return request({
    url: '/profile',
    method: 'get'
  })
}

export function getOwnLog(number) {
  return request({
    url: '/profile/log/' + number,
    method: 'get'
  })
}

export function updateAvatar(avatar) {
  return request({
    url: '/profile/avatar',
    method: 'put',
    params: avatar
  })
}

export function updatePassword(data) {
  return request({
    url: '/profile/password',
    method: 'put',
    data
  })
}

export function update(data) {
  return request({
    url: '/profile',
    method: 'put',
    data
  })
}
