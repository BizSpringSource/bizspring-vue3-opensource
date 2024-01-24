<template>
  <el-color-picker   class="theme-picker" popper-class="theme-picker-dropdown" :predefine="predefine"
    v-model="themeVal"></el-color-picker>
</template>

<script>
import color from '@/mixins/color'
export default {
  name: 'topColor',
  mixins: [color()],
  data() {
    return {
      chalk: '',
      predefine: [
        '#409EFF',
        '#67C23A',
        '#E6A23C',
        '#F56C6C',
        '#909399',
        '#C71539',
      ],
    }
  },
}
</script>

<style>
.theme-picker .el-color-picker__trigger {
  vertical-align: middle;
}

.theme-picker-dropdown .el-color-dropdown__link-btn {
  display: none;
}
</style>
