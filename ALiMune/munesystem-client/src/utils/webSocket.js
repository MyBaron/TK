

const WSUrl=process.env.VUE_APP_WS_PATH


export const initWebSocket = function () {
    // WebSocket与普通的请求所用协议有所不同，ws等同于http，wss等同于https
    return new WebSocket(WSUrl);
}