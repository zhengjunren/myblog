import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'
import router from "../router";

// create an axios instance
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests
  timeout: 5000 // request timeout

})

// request interceptor
service.interceptors.request.use(
  config => {
    // do something before request is sent

    if (store.getters.token) {
      config.headers['Authorization'] = 'Bearer ' + getToken()
    }
    return config
  },
  error => {
    // do something with request error
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
  /**
   * If you want to get http information such as headers or status
   * Please return  response => response
  */

  /**
   * Determine the request status by custom code
   * Here is just an example
   * You can also judge the status by HTTP Status Code
   */
  response => {
    const status = response.status
    if (status < 200 || status > 300) {
      Notification.error({
        title: response.message
      })
      return Promise.reject('error')
    } else {
      const res = response.data
      if(res.code == null){
        return res
      }
      else if (res.code !== 20000) {
        Message({
          message: res.message || '错误',
          type: 'error',
          duration: 5 * 1000
        })

        if (res.code === 30000) {
          this.$alert(res.message, '警告', {
            confirmButtonText: '确定',
            callback: action => {
              this.$message({
                type: 'warning ',
                message: `action: ${ action }`
              });
            }
          });
        }
        // 50008: Illegal token; 50012: Other clients logged in; 50014: Token expired;
        if (res.code === 50008 || res.code === 50012 || res.code === 50014) {
          // to re-login
          MessageBox.confirm('你已经退出当前登录, 你可以取消留在当前界面或者再次登录', '确认注销', {
            confirmButtonText: 'Re-Login',
            cancelButtonText: 'Cancel',
            type: 'warning'
          }).then(() => {
            store.dispatch('user/resetToken').then(() => {
              location.reload()
            })
          })
        }
        return Promise.reject(new Error(res.message || 'Error'))
      }
      else {
        return res
      }

    }
  },
  error => {
    let code = 0
    console.log(error.response)
    try {
      code = error.response.status
    }catch (e) {
      console.log('err' + error) // for debug
      Message({
        message: error.message,
        type: 'error',
        duration: 5 * 1000
      })
      return Promise.reject(error)
    }
    if (code === 401) {
      MessageBox.confirm(
        '登录状态已过期，您可以继续留在该页面，或者重新登录',
        '系统提示',
        {
          confirmButtonText: '重新登录',
          cancelButtonText: '取消',
          type: 'warning'
        }
      ).then(() => {
        store.dispatch('LogOut').then(() => {
          location.reload() // 为了重新实例化vue-router对象 避免bug
        })
      })
    } else if (code === 403) {
      this.$notify.error({
        title: '操作失败',
        message: '你没有权限进行该操作或浏览该页！如有不满请联系你领导哈'
      });
      // router.push({ path: '/401' })
    } else {
      const errorMsg = error.response.data.message
      if (errorMsg !== undefined) {
        Notification.error({
          title: errorMsg,
          duration: 3000
        })
      }
    }
  }
)

export default service
