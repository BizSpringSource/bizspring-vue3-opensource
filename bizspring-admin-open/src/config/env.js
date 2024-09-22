import {globalBaseUrl} from "/public/config"
const codeUrl =globalBaseUrl
const baseUrl = import.meta.env.VITE_BASE_URL
//前端密码密钥，必须16位，和nacos配置文件base-gateway-dev.yml中的security.encode.key对应
const securityKey = '1234567891234567'
const basicAuth='Basic YWRtaW46YWRtaW4='

export { baseUrl, codeUrl ,securityKey,basicAuth}
