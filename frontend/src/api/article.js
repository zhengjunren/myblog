import request from '@/utils/request'

//****************文章管理****************

export function getArticles(query) {
  return request({
    url: '/articles',
    method: 'get',
    params: query
  })
}

export function updateComment(id) {
  return request({
    url: '/articles/comment/' + id,
    method: 'put'
  })
}

export function updateStatus(id, status) {
  return request({
    url: '/articles/status/' + id,
    method: 'put',
    params: {status:status}
  })
}

export function fetchArticle(id) {
  return request({
    url: '/articles/' + id,
    method: 'get'
  })
}


export function postArticle(data) {
  return request({
    url: '/articles',
    method: 'post',
    data
  })
}

export function updateArticle(data) {
  return request({
    url: '/articles',
    method: 'put',
    data
  })
}

//****************分类管理****************

export function getArticleCategoryTree() {
  return request({
    url: '/article/categories/tree',
    method: 'get'
  })
}

export function getArticleCategories() {
  return request({
    url: '/article/categories/list',
    method: 'get'
  })
}

export function createArticleCategory(data) {
  return request({
    url: '/article/categories',
    method: 'post',
    data
  })
}

export function updateArticleCategory(data) {
  return request({
    url: '/article/categories',
    method: 'put',
    data
  })
}
