import { http } from '@/utils/http.js'

/**
 * 添加订单
 */
export const saveOrderData = data => http("/order/add", data, "POST")

/**
 * 获取订单信息
 */
export const getOrderData = data => http("/order", data, "GET")

/**
 * 获取订单详细信息
 */
export const getOrderDetailData = data => http("/order/order_deatil", data, "GET")