import request from '@/utils/request'

export const BASE_URL = process.env.VUE_APP_SYSTEM_URL

export function fetchLogList(query) {
  return request({
    url: '/log/list',
    method: 'get',
    params: query,
    baseURL: BASE_URL
  })
}

export function countLog() {
  return request({
    url: '/log/count',
    method: 'get',
    baseURL: BASE_URL
  })
}

export function getLogExcel(query) {
  return request({
    responseType: 'blob',
    url: '/excel/log',
    method: 'get',
    params: query
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
    url: '/job/delete',
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
    url: '/email/config',
    baseURL: BASE_URL,
    method: 'get',
  })
}

export function updateEmailConfig(data) {
  return request({
    url: '/email/update/config',
    method: 'put',
    baseURL: BASE_URL,
    data
  })
}


export function sendMail(data) {
  return request({
    url: '/email/',
    method: 'post',
    baseURL: BASE_URL,
    data
  })
}

export function getPermissionTree() {
  return request({
    url: '/permission/',
    method: 'get',
    baseURL: BASE_URL
  })
}

export function getPermissionByRoleId(roleId) {
  return request({
    url: '/permission/' + roleId,
    method: 'get',
    baseURL: BASE_URL
  })
}

export function getRoleList(query) {
  return request({
    url: '/role/list',
    method: 'get',
    params: query,
    baseURL: BASE_URL
  })
}


export function updateRolePermission(data) {
  return request({
    url: '/role/permission',
    method: 'put',
    data,
    baseURL: BASE_URL
  })
}

export function getOnlineUser(query) {
  return request({
    url: '/online/list',
    method: 'get',
    params: query,
    baseURL: BASE_URL
  })
}

export function kickOutOnlineUser(token) {
  return request({
    url: '/online/'+ token,
    method: 'delete',
    baseURL: BASE_URL
  })
}
