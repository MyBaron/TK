import axios from 'axios';

//创建axios实例
const service = axios.create({
    timeout: 30000 //超时时间
})

//添加request拦截器
service.interceptors.request.use(config => {
    return config;
}, error => {
    Promise.reject(error);
})

//添加response拦截器
service.interceptors.response.use(
    response => {
        let res = {};
        res.status = response.status;
        res.data = response.data;
        return res;
    },
    error => {
        if (error.response && error.response.status == 404) {
            //跳转到xx页面
            console.log("404错误")
        }
        return Promise.reject(error.response)
    }
)

//封装get接口
// params={} 是设置默认值
export function get(url, params = {}) {
    params.t = new Date().getTime(); //get方法加一个时间参数,解决ie下可能缓存问题.
    return service({
        url: url,
        method: 'get',
        headers: {
        },
        params
    })
}

//封装post请求
export function post(url, data = {}) {
    //默认配置
    let sendObject = {
        url: url,
        method: "post",
        headers: {
            'Content-Type': 'application/json;charset=UTF-8'
        },
        data: data
    };
    sendObject.data = JSON.stringify(data);
    return service(sendObject)
}

//封装put方法 (resfulAPI常用)
function put(url, data = {}) {
    return service({
        url: url,
        method: 'put',
        headers: {
            'Content-Type': 'application/json;charset=UTF-8'
        },
        data: JSON.stringify(data)
    })
}

//删除方法(resfulAPI常用)
function deletes(url) {
    return service({
        url: url,
        method: 'delete',
        headers: {}
    })
}

//patch方法(resfulAPI常用)
function patch(url) {
    return service({
        url: url,
        method: 'patch',
        headers: {}
    })
}

//处理url有格式化字段 (/demo/{id}）
function render(url, data) {
    var re = /{([^]+)?}/
    var match = ''
    while ((match = re.exec(url))) {
        url = url.replace(match[0], data[match[1]])
    }
    return url
}

const fetch = (options) => {
    let url = process.env.VUE_APP_PATH + options.url;
    url = render(url, options.data)
    switch (options.method.toUpperCase()) {
        case 'GET':
            return get(url, options.data)
        case 'POST':
            return post(url, options.data)
        case 'PUT':
            return put(url, options.data)
        case 'DELETE':
            return deletes(url, options.data)
        case 'PATCH':
            return patch(url, options.data)
        default:
            return service(options)
    }
}

/**
 * 提供一个http方法
 * url 访问路径 不包括域名和项目名
 * data 参数对象
 * method 请求方式
 *  */
export function http(url = '', data = {}, method = "GET") {
    const options = { url: url, data: data, method: method }
    return fetch(options).catch(error => {
        console.log(error)
        throw error
    })
}

/**
 * 构造url的方法
 */
export function getUrl(url = '') {
    return process.env.VUE_APP_PATH + url;
}

