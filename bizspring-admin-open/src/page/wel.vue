<template>
  <div>
    <basic-container>
      <div class="banner-text">
          <el-collapse v-model="activeNames">
            <div class="wel-title" >产品优势</div>
            <el-collapse-item name="1" title="全端语言国际化">
              <div> 1、 对于国际化电商、跨境电商及海外商运营者，实现电商语言国际化是必要的工作。他们通常着眼于全球范围内贸易和客户，这样的贸易不仅让本国国民可以购买外国商品，也让外国消费者可以选择其本国商品实现双向贸易，只有具有逾越各种语言和文化习惯的用户群，才能取得更大的市场份额</div>
              <div> 2、 对于地方化语言电商运营者、多语言平台可以帮助企业实现地方化、区域化业务运营及扩展。通过多种语言平台，企业可以更好地与来自不同文化背景的客户进行贸易，增加销售额和盈利能力。</div>
              <div> 3、 多语言电商平台可以提高用户体验和满意度。通过提供用户习惯使用的语言版本，用户可以更方便地使用平台和应用程序，减少沟通障碍和误解，提高用户忠诚度。</div>
              <div> 4、 多语言平台还可以帮助企业或组织增强其国际形象和声誉。提供多种语言版本可以显示企业对全球市场的重视和尊重，使其更具吸引力。</div>
            </el-collapse-item>
            <el-collapse-item name="2" title="电商业务完全掌上操作">
              <div style="font-weight: bold" >BizSpring移动全端是基于ElementPlus + uniapp开发的移动端多平台同步构建的电商建设解决方案。此架构一次开发发布到多个平台（H5、公众号、头条、抖音、小程序、iOS、Android 等），实现跨平台应用的快速建设和落地。</div>
              <div> 1、 最新的 UI 界面设计</div>
              <div> 2、 一次开发，跨平台发布，投入少、产出高</div>
              <div> 3、 移动全端，使用便捷、业务扩展快</div>
            </el-collapse-item>
            <el-collapse-item name="3" title="商城业务基于多服务架构">
              <div style="font-weight: bold" >BizSpring电商平台使用业内优秀的架构设计理念，将电商整套流程包括商品（goods）、订单(order)，店铺(store)、营销(marketing)分别建设为不同的服务，降低了电商系统业务的耦合性，各服务可单独部署及集群化，提升了平台综合抗压能力，架构的灵活性和扩展性为产品后期升级服务提供捷径。</div>
              <div> 1、 Elasticsearch搜素引擎</div>
              <div> 2、 Redis缓存集群</div>
              <div> 3、 Nacos注册中心</div>
              <div> 4、 OpenFeign Ribbon负载均衡</div>
            </el-collapse-item>
            <div class="wel-title" >核心技术</div>
            <el-collapse-item name="4" title="BizSpring核心技术">
              <div>核心框架:spring boot、spring cloud、spring cloud alibaba</div>
              <div>注册中心:nacos</div>
              <div>负载均衡:Spring Cloud Load balancer</div>
              <div>服务调用:open feign</div>
              <div>服务容错:Alibaba sentinel</div>
              <div>api网关：spring cloud gateway</div>
              <div>分布式事务:seata</div>
              <div>持久层框架：MyBatis-plus</div>
              <div>高性能缓存：redis redisson </div>
              <div>文件管理：Alibaba oss</div>
              <div>SMS短信: Alibaba SMS</div>
              <div>安全：oauth2+jwt</div>
              <div>数据库：mysql8+</div>
              <div>API管理：swagger</div>
              <div>搜索引擎：elasticsearch</div>
              <div>PC端前段框架：vue3+elementPlus</div>
              <div>移动端框架：Uniapp(移动全端)</div>
            </el-collapse-item>
            <div class="wel-title" >选择我们</div>
            <el-collapse-item name="5" title="BizSpring客户体验">
              <div> 1、系统优势：专业的项目管理团队，有多年电商项目经验。技术团队成熟，产品质量保证，项目交付准时，售后服务完善。</div>
              <div> 2、产品优势：采用行业前沿开发技术，前后端分离，多端开发、负载均衡、性能优越，支持集群部署，耦合性低，可追溯性强。</div>
              <div> 3、技术优势：客户私有服务器部署，帮助客户部署系统，标准化产品，部署完即可正常使用和运营。</div>
              <div> 4、应用优势：APP、小程序、H5移动全端支持，多种语言切换。</div>
              <div> 5、售后优势：项目终身迭代，一次购买终身免费升级。</div>
          
            </el-collapse-item>
            <div class="wel-title" >联系我们</div>
            <el-collapse-item name="6" title="商务客服 - 微信：16619915737">
              <div>扫描下方二维码，了解更多商城平台信息。</div>
              <div><img src="/img/qr.jpg"/></div>
            </el-collapse-item>
          </el-collapse>
      </div>

    </basic-container>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'wel',
  data() {
    return {
      activeNames: ['1', '2', '3'],
      DATA: [],
      text: '',
      actor: '',
      count: 0,
      isText: false
    }
  },
  computed: {
    ...mapGetters(['website'])
  },
  methods: {
    getData() {
      if (this.count < this.DATA.length - 1) {
        this.count++
      } else {
        this.count = 0
      }
      this.isText = true
      this.actor = this.DATA[this.count]
    },
    setData() {
      let num = 0
      let count = 0
      let active = false
      const timeoutstart = 5000
      const timeoutend = 1000
      const timespeed = 10
      setInterval(() => {
        if (this.isText) {
          if (count === this.actor.length) {
            active = true
          } else {
            active = false
          }
          if (active) {
            num--
            this.text = this.actor.substr(0, num)
            if (num === 0) {
              this.isText = false
              setTimeout(() => {
                count = 0
                this.getData()
              }, timeoutend)
            }
          } else {
            num++
            this.text = this.actor.substr(0, num)
            if (num === this.actor.length) {
              this.isText = false
              setTimeout(() => {
                this.isText = true
                count = this.actor.length
              }, timeoutstart)
            }
          }
        }
      }, timespeed)
    }
  }
}
</script>

<style scoped="scoped" lang="scss">
.wel-title{
  font-size: 20px;
}

.wel-contailer {
  position: relative;
}

.banner-text {
  position: relative;
  padding: 0 20px;
  font-size: 30px;
  line-height: 30px;
  text-align: left;
  color: #333;
}

.banner-img {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  opacity: 0.8;
  display: none;
}

.actor {
  height: 250px;
  overflow: hidden;
  font-size: 18px;
  color: #333;
}

.actor:after {
  content: '';
  width: 3px;
  height: 25px;
  vertical-align: -5px;
  margin-left: 5px;
  background-color: #333;
  display: inline-block;
  animation: blink 0.4s infinite alternate;
}

.typeing:after {
  animation: none;
}

@keyframes blink {
  to {
    opacity: 0;
  }
}
</style>
