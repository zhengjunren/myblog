import request from '@/utils/request'

const BASE_URL = "http://localhost:8600"

export function fetchLogList(query) {
  return request({
    url: '/log/list',
    method: 'get',
    params: query,
    baseURL: BASE_URL
  })
}



export function getTaskList(query) {
  return request({
    url: '/job',
    method: 'get',
    params: query,
    baseURL: BASE_URL
  })
}

export function resumeTask(data) {
  return request({
    url: '/job/resume',
    method: 'put',
    baseURL: BASE_URL,
    data
  })
}

export function pauseTask(data) {
  return request({
    url: '/job/pause',
    method: 'put',
    baseURL: BASE_URL,
    data
  })
}

export function updateTask(data) {
  return request({
    url: '/job/cron',
    method: 'put',
    baseURL: BASE_URL,
    data
  })
}

export function deleteTask(data) {
  return request({
    url: '/job/',
    method: 'delete',
    baseURL: BASE_URL,
    data
  })
}

export function createTask(data) {
  return request({
    url: '/job/',
    method: 'post',
    baseURL: BASE_URL,
    data
  })
}

export function fetchEmailConfig() {
  return request({
    url: '/email/',
    baseURL: BASE_URL,
    method: 'get',
  })
}

export function updateEmailConfig(data) {
  return request({
    url: '/email/',
    method: 'put',
    baseURL: BASE_URL,
    data
  })
}
