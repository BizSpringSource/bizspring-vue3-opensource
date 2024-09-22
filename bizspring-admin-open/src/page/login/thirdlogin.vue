<template>
  <div class="thirdparty-container">
    <div class="box" @click="handleClick('wechat')">
      <span class="container" :style="{ backgroundColor: '#6ba2d6' }">
        <i icon-class="wechat" class="iconfont icon-weixin"></i>
      </span>
      <p class="title">微信</p>
    </div>
    <div class="box" @click="handleClick('qq')">
      <span class="container" :style="{ backgroundColor: '#8dc349' }">
        <i icon-class="qq" class="iconfont icon-qq"></i>
      </span>
      <p class="title">QQ</p>
    </div>
  </div>
</template>

<script>
 import { openWindow } from '@/util/index'

export default {
  name: 'thirdparty-signin',
  data:{
  },
  methods: {
    handleClick(thirdpart) { },
  },
}
</script>

<style lang="scss" rel="stylesheet/scss" scoped>
.thirdparty-container {
  margin: 20px 0;
  display: flex;
  align-items: center;
  justify-content: space-around;

  .box {
    cursor: pointer;
  }

  .iconfont {
    color: #fff;
    font-size: 30px;
  }

  .container {
    $height: 50px;
    display: inline-block;
    width: $height;
    height: $height;
    line-height: $height;
    text-align: center;
    border-radius: 4px;
    margin-bottom: 10px;
  }

  .title {
    text-align: center;
  }
}
</style>
