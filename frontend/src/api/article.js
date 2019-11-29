import request from '@/utils/request'

export function fetchList(query) {
  return request({
    url: '/article/list',
    method: 'get',
    params: query
  })
}
export function fetchArticle(id) {
  return request({
    url: '/article/'+id,
    method: 'get',
  })
}

export function postArticle(data) {
  return request({
    url: '/article',
    method: 'post',
    data
  })
}

export function updateArticle(data) {
  return request({
    url: '/article',
    method: 'put',
    data
  })
}
