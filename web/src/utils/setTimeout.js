/**
 * @author brook
 * @description 格式化时间
 * @param time
 * @param cFormat
 * @returns {string|null}
 */
var timeoutMap = {}

export function setTimeout(fn, to) {
  if (fn in timeoutMap) {
    if (timeoutMap[fn].id) {
      window.clearTimeout(timeoutMap[fn].id)
      timeoutMap[fn].id = undefined
    }
  }
  timeoutMap[fn] = {
    id: window.setTimeout(fn, to),
    fn: fn,
    tsp: new Date(),
    to: to,
  }
  return fn
}

export function clearTimeout(fn) {
  if (fn in timeoutMap) {
    if (timeoutMap[fn].id) {
      window.clearTimeout(timeoutMap[fn].id)
      timeoutMap[fn].id = undefined
    }
  }
}
