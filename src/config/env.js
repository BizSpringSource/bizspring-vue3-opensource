/**
 * Copyright (C) 2021-2023
 * All rights reserved, Designed By www.bizspring.cn
 * 注意：
 * 本软件为www.bizspring.cn开发研制
 * 如需技术支持，请联系（微信电话同号）：16619915737
 */
import {globalBaseUrl} from "/public/config"
const baseUrl = import.meta.env.VITE_BASE_URL
const codeUrl =globalBaseUrl
//前端密码密钥，必须16位，和nacos配置文件base-gateway-dev.yml中的security.encode.key对应
const securityKey = '1234567891234567'
const basicAuth='Basic YWRtaW46YWRtaW4='
export { baseUrl, codeUrl ,securityKey,basicAuth}

