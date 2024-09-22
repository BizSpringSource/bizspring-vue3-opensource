<template>
  <div
    class="avue-searchs"
    @click.self="handleEsc">
    <div class="avue-searchs__title">菜单搜索</div>
    <div class="avue-searchs__content">
      <div class="avue-searchs__form">
        <el-input
          v-model="value"
          placeholder="搜索"
          @keydown.esc="handleEsc">
          <template #append>
            <el-button icon="el-icon-search"></el-button>
          </template>

        </el-input>
        <p>
          <el-tag>你可以使用快捷键esc关闭</el-tag>
        </p>
      </div>
      <div class="avue-searchs__list">
        <el-scrollbar class="avue-searchs__scrollbar">
          <div
            v-for="(item,index) in menus"
            :key="index"
            class="avue-searchs__item"
            @click="handleSelect(item)">
            <i :class="[item[iconKey],'avue-searchs__item-icon']"></i>
            <span class="avue-searchs__item-title">{{ item[labelKey] }}</span>
            <div class="avue-searchs__item-path">
              {{ item[pathKey] }}
            </div>
          </div>
        </el-scrollbar>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  data() {
    return {
      value: '',
      menus: [],
      menuList: []
    }
  },
  computed: {
    labelKey() {
      return this.website.menu.label
    },
    pathKey() {
      return this.website.menu.path
    },
    iconKey() {
      return this.website.menu.icon
    },
    childrenKey() {
      return (
        this.website.menu.children
      )
    },
    ...mapGetters(['menu'])
  },
  watch: {
    value() {
      this.querySearch()
    },
    menu() {
      this.getMenuList()
    }
  },
  created() {
    this.getMenuList()
  },
  methods: {
    handleEsc() {
      this.$store.commit('SET_IS_SEARCH', false)
    },
    getMenuList() {
      const findMenu = (list = []) => {
        for (let i = 0; i < list.length; i++) {
          const ele = Object.assign({}, list[i])
          if (this.validatenull(ele[this.childrenKey])) {
            this.menuList.push(ele)
          } else {
            findMenu(ele[this.childrenKey])
          }
        }
      }
      this.menuList = []
      findMenu(this.menu)
      this.menus = this.menuList
    },
    querySearch() {
      var restaurants = this.menuList
      var queryString = this.value
      this.menus = queryString
        ? this.menuList.filter(this.createFilter(queryString))
        : restaurants
    },
    createFilter(queryString) {
      return restaurant => {
        return (
          restaurant.label.toLowerCase().indexOf(queryString.toLowerCase()) ===
                    0
        )
      }
    },
    handleSelect(item) {
      this.value = ''
      this.$router.push({
        path: item[this.pathKey],
        query: item.query
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.avue-searchs {
  padding-top: 50px;
  width: 100%;
  height: 100%;
  background-color: #fff;
  z-index: 1024;

  &__title {
    margin-bottom: 60px;
    text-align: center;
    font-size: 42px;
    font-weight: bold;
    letter-spacing: 2px;
    text-indent: 2px;
  }

  &__form {
    margin: 0 auto 50px auto;
    width: 50%;
    text-align: center;

    p {
      margin-top: 20px;
    }
  }

  &__scrollbar {
    height: 400px;
  }

  &__list {
    box-sizing: border-box;
    padding: 20px 30px;
    margin: 0 auto;
    width: 70%;
    border-radius: 4px;
    border: 1px solid #ebeef5;
    background-color: #fff;
    overflow: hidden;
    color: #303133;
    transition: 0.3s;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  }

  &__item {
    padding: 5px 0;
    border-bottom: 1px dashed #eee;

    &-icon {
      margin-right: 5px;
      font-size: 18px;
    }

    &-title {
      font-size: 20px;
      font-weight: 500;
      color: #333;
    }

    &-path {
      line-height: 30px;
      color: #666;
    }
  }
}
</style>
