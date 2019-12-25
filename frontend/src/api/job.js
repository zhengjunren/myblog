import request from '@/utils/request'

export function getJobs(query) {
  return request({
    url: '/jobs',
    method: 'get',
    params: query
  })
}

export function add(data) {
  return request({
    url: '/jobs',
    method: 'post',
    data
  })
}

export function edit(data) {
  return request({
    url: '/jobs',
    method: 'put',
    data
  })
}

export function del(data) {
  return request({
    url: '/jobs',
    method: 'delete',
    data
  })
}

export function execute(id) {
  return request({
    url: '/jobs/exec/' + id,
    method: 'put'
  })
}

export function updateIsPause(id) {
  return request({
    url: '/jobs/' + id,
    method: 'put'
  })
}

export function getJobsLogs(query) {
  return request({
    url: '/jobs/logs',
    method: 'get',
    params: query
  })
}

export function downloadJobExcel() {
  return request({
    responseType: 'blob',
    url: '/jobs/excel',
    method: 'get'
  })
}

export function downloadJobLogExcel() {
  return request({
    responseType: 'blob',
    url: '/jobs/logs/excel',
    method: 'get'
  })
}
