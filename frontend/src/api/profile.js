import request from '@/utils/request'

export function getOwnInfo() {
  return request({
    url: '/profile',
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
