import request from '@/utils/request'

export function getConfig() {
  return request({
    url: '/email/config',
    method: 'get'
  })
}

export function updateConfig(data) {
  return request({
    url: '/email/config',
    method: 'put',
    data
  })
}

export function send(data) {
  return request({
    url: '/email',
    method: 'post',
    data
  })
}
