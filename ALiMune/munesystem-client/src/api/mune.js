
import { http, getUrl, getImageUrl } from '@/utils/http.js'


/**
 * 获取菜单信息
 */
export const getMuneData = data => http("/mune", data, "GET")
/**
 * 根据Id获取菜单信息
 */
export const getMuneDataById = data => http("/mune/{id}", data, "GET")
/**
 * 添加菜单
 */
export const saveMuneData = data => http("/mune", data, "POST")
/**
 * 删除菜
 */
export const deleteMuneData = data => http("/mune/{id}", data, "DELETE")

/**
 * 获取上传菜图片路径
 */
export const uploadMuneImageUrl = getUrl("/upload/img")

/**
 * 获取图片存放途径
 */
export const muneImageUrl = url => { return process.env.VUE_APP_IMG_PATH + url }