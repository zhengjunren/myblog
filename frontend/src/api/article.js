import request from '@/utils/request'

export function getArticles(query) {
  return request({
    url: '/articles',
    method: 'get',
    params: query
  })
}

export function getArticleCategoryTree() {
  return request({
    url: '/article/categories/tree',
    method: 'get'
  })
}

export function updateComment(id) {
  return request({
    url: '/articles/comment/' + id,
    method: 'put'
  })
}

