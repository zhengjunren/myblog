import request from '@/utils/request'

export const BASE_URL = process.env.VUE_APP_BASE

export function getServer() {
  return request({
    url: '/server',
    method: 'get'
  })
}
