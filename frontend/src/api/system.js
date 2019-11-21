import request from '@/utils/request'

export function fetchLogList(query) {
  return request({
    url: 'http://localhost:8600/log/list',
    method: 'get',
    params: query
  })
}



export function getTaskList(query) {
  return request({
    url: 'http://localhost:8600/job',
    method: 'get',
    params: query
  })
}

export function resumeTask(data) {
  return request({
    url: 'http://localhost:8600/job/resume',
    method: 'put',
    data
  })
}

export function pauseTask(data) {
  return request({
    url: 'http://localhost:8600/job/pause',
    method: 'put',
    data
  })
}

export function updateTask(data) {
  return request({
    url: 'http://localhost:8600/job/cron',
    method: 'put',
    data
  })
}

export function deleteTask(data) {
  return request({
    url: 'http://localhost:8600/job/',
    method: 'delete',
    data
  })
}

export function createTask(data) {
  return request({
    url: 'http://localhost:8600/job/',
    method: 'post',
    data
  })
}

export function fetchEmailConfig() {
  return request({
    url: 'http://localhost:8600/email/',
    method: 'get',
  })
}

export function updateEmailConfig(data) {
  return request({
    url: 'http://localhost:8600/email/',
    method: 'put',
    data
  })
}
