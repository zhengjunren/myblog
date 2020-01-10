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
