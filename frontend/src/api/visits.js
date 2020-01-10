import request from '@/utils/request'

export function count() {
  return request({
    url: '/visits',
    method: 'post'
  })
}

export function get() {
  return request({
    url: '/visits',
    method: 'get'
  })
}

export function getChartData() {
  return request({
    url: '/visits/chartData',
    method: 'get'
  })
}
