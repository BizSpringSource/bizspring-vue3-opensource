import { validatenull } from '@/utils/validate.js'

const keyName = 'BizSpring' + '-';
/**
 * 存储localStorage
 */
export const setStore = (params = {}) => {
  let {
    name,
    content,
    type,
  } = params;
  name = keyName + name
  let obj = {
    dataType: typeof (content),
    content: content,
    type: type,
    datetime: new Date().getTime(),
  }
  uni.setStorageSync(name, obj);
  // if (type){ 
	 //  window.sessionStorage.setItem(name, JSON.stringify(obj))
  // }else{ 
	 //  window.localStorage.setItem(name, JSON.stringify(obj))
  // }
}
/**
 * 获取localStorage
 */

export const getStore = (params = {}) => {
  let {
    name,
    debug
  } = params;
  name = keyName + name
  let obj = {},
    content;
  // obj = window.sessionStorage.getItem(name);
	obj=uni.getStorageSync(name);
  if (validatenull(obj)){ 
	obj=uni.getStorageSync(name);
	  // obj = window.localStorage.getItem(name);
  }
  if (validatenull(obj)){ 
	  return; 
  }
  try {
    obj = JSON.parse(obj);
  } catch{
    return obj;
  }
  if (debug) {
    return obj;
  }
  if (obj.dataType == 'string') {
    content = obj.content;
  } else if (obj.dataType == 'number') {
    content = Number(obj.content);
  } else if (obj.dataType == 'boolean') {
    content = eval(obj.content);
  } else if (obj.dataType == 'object') {
    content = obj.content;
  }
  return content;
}
/**
 * 删除localStorage
 */
export const removeStore = (params = {}) => {
  let {
    name,
    type
  } = params;
  name = keyName + name
  uni.removeStorageSync(name);
  // if (type) {
  //   window.sessionStorage.removeItem(name);
  // } else {
  //   window.localStorage.removeItem(name);
  // }

}

/**
 * 获取全部localStorage
 */
export const getAllStore = (params = {}) => {
  let list = [];
  let {
    type
  } = params;
  if (type) {
	  const res = uni.getStorageInfoSync();	
	for (let i = 0; i <= res.keys.length; i++) {
    // for (let i = 0; i <= window.sessionStorage.length; i++) {
      list.push({
        // name: window.sessionStorage.key(i),
		name:uni.getStorageSync(res.keys[i]),
        content: getStore({
          // name: window.sessionStorage.key(i),
		  name:uni.getStorageSync(res.keys[i]),
          type: 'session'
        })
      })
    }
  } else {
	  const res = uni.getStorageInfoSync();
    for (let i = 0; i <= res.keys.length; i++) {
	// for (let i = 0; i <= window.sessionStorage.length; i++) {	
      list.push({
		name:uni.getStorageSync(res.keys[i]),
        // name: window.localStorage.key(i),
        content: getStore({
			name:uni.getStorageSync(res.keys[i]),
          // name: window.localStorage.key(i),
        })
      })

    }
  }
  return list;

}

/**
 * 清空全部localStorage
 */
export const clearStore = (params = {}) => {
	uni.clearStorageSync();
  // let { type } = params;
  // if (type) {
  //   window.sessionStorage.clear();
  // } else {
  //   window.localStorage.clear()
  // }

}
