import request from '@/utils/request'

export function login(data) {
    return request({
        url: 'http://119.3.222.119:8000/user/login',
        method: 'post',
        data
    })
}

export function getInfo() {
    return request({
        url: 'http://localhost:9000/user/info',
        method: 'get'
    })
}

export function logout(token) {
    return request({
        url: 'http://119.3.222.119:8000/user/logout',
        method: 'post',
        params: { access_token: token }
    })
}

export function search(data, page, limit) {
    return request({
        url: 'http://119.3.222.119:8500/user/search',
        method: 'post',
        params: { page: page, limit: limit },
        data
    })
}

export function fetchList(query) {
    return request({
        url: '/user/list',
        method: 'get',
        params: query
    })
}

export function updateUser(data) {
    return request({
        url: '/user/',
        method: 'post',
        data
    })
}

export function modifyStatus(username, status) {
    return request({
        url: '/user/status',
        method: 'post',
        data: {
            'value': status,
            'username': username
        }
    })
}

export function updateProfile(data) {
    return request({
        url: '/user/profile',
        method: 'post',
        data
    })
}

export function updateAvatar(data) {
    return request({
        url: '/user/avatar',
        method: 'post',
        data
    })
}

export function getExcel() {
    return request({
        // responseType: 'blob',
        url: '/excel/download',
        method: 'get'
    })
}
