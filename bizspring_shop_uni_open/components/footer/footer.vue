<template>
<view>
<view class="pt_footer">
    <navigator v-for="(item, index) in tabnavlist" :key="index" :class="'item ' + (item.on ? 'on' : '')" :url="item.url">
        <text :class="'iconfont ' + item.icon"></text>
        <text class="txt">{{item.name}}</text>
    </navigator>
</view>
<view style="height: 100rpx;"></view>
</view>
</template>

<script>

export default {
  data() {
    return {
      toastShow: false
    };
  },

  components: {},
  props: {
    tabnavlist: {
      // 属性名
      type: Array,
      default: [{
        icon: 'icon-shouye',
        name: '首页',
        url: '/pages/index/index',
        on: true
      }]
    }
  },
  options: {
    multipleSlots: true // 在组件定义时的选项中启用多slot支持

  },
  methods: {}
};
</script>
<style>
/* components/footer/footer.wxss */
@import "../../iconfont.css";
.pt_footer{
    width: 100%;
    height: 100rpx;
    background: #ffffff;
    position: fixed;
    z-index: 10;
    left: 0;
    bottom: 0;
    display: flex;
    box-shadow: 0 0 20rpx #a5a3a3;
}
.pt_footer .item{
    flex: 1;
    text-align: center;
    display: flex;
    flex-direction: column;
    color: #aaaaaa;
}
.pt_footer .item .iconfont{
    font-size: 48rpx !important;
}
.pt_footer .on{
    color: #ff0000;
}
</style>