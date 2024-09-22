
// prod 生产 dev 开发
const env = "test"

const prod = {
  url: 'http://192.168.1.1:9999', // 线上环境'
  appKey: 'bizspring_merchant_uni'
}
const dev = {
  url: 'http://192.168.1.1:9999', // 生产环境'
  appKey: 'bizspring_merchant_uni'
}
const test = {
	url: 'http://192.168.1.1:9979', // 测试环境' home 
  appKey: 'bizspring_merchant_uni'
}

//前端密码密钥，必须16位，和nacos配置文件base-gateway-dev.yml中的security.encode.key对应
let securityKey = '1234567891234567'

const config = {
  dev,
  prod,
  test,
}

let baseURL = config[env].url;
let appKey = config[env].appKey

export  {
  baseURL,
  appKey,
  securityKey
}