import request from '@/utils/request'

export function post(module, method, data, config) {
  return request({
    url: '/' + module + '/' + method,
    method: 'post',
    data,
    ...(config || {}),
  })
}

export function createRequest(module, method) {
  return (data, config) => {
    let newData = {}
    for (let p in data) {
      if (p == 'pageSize') {
        newData.page_size = data[p]
      } else if (p == 'pageNo') {
        newData.page_no = data[p]
      } else {
        newData[p] = data[p]
      }
    }

    // console.log('request', module, method, newData, data, config)
    return request({
      url: '/' + module + '/' + method,
      method: 'post',
      data: newData,
      ...(config || {}),
    }).then((data) => {
      if ('body' in data) {
        data.data = data.body
        delete data.body
      }
      return data
    })
  }
}
