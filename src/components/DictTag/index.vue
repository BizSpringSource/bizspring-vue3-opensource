<template>
  <div>
    <template v-for="(item, index) in options">
      <template v-if="values.includes(item.value)">
        <el-tag :key="item.value" :disable-transitions="true" :index="index" :type="item.type || ''">
          {{ item.label }}
        </el-tag>
      </template>
    </template>
  </div>
</template>

<script>
export default {
  name: 'DictTag',
  props: {
    options: {
      type: Array,
      default: null
    },
    value: {
      type: [Number, String, Array],
      default: null
    }
  },
  computed: {
    values() {
      if (this.value !== null && typeof this.value !== 'undefined') {
        return Array.isArray(this.value) ? this.value : [String(this.value)]
      } else {
        return []
      }
    }
  }
}
</script>
<style scoped>
.el-tag+.el-tag {
  margin-left: 10px;
}
</style>
