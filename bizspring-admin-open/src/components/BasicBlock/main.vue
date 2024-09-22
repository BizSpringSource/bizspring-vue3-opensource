<template>
  <div
    class="basic-block"
    :style="styleName">
    <div
      class="box"
      :style="boxStyleName">
      <router-link :to="to">
        <span v-text="text"></span>
        <p v-text="dept"></p>
        <i :class="icon"></i>
      </router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: 'basicBlock',
  props: {
    icon: {
      type: String,
      default: null
    },
    background: {
      type: String,
      default: null
    },
    to: {
      type: Object,
      default: () => {
        return {}
      }
    },
    text: {
      type: String,
      default: null
    },
    dept: {
      type: String,
      default: null
    },
    time: {
      type: [Number, String],
      default: null
    },
    gutter: {
      type: [Number, String],
      default: 5
    },
    color: {
      type: String,
      default: null
    },
    width: {
      type: [Number, String],
      default: null
    },
    height: {
      type: [Number, String],
      default: null
    }
  },
  computed: {
    styleName() {
      return {
        animationDelay: `${this.time / 25}s`,
        width: `${this.width}px`,
        height: `${this.height}px`,
        margin: `${this.gutter}px`
      }
    },
    boxStyleName() {
      return {
        backgroundColor: this.color,
        backgroundImage: `url('${this.background}')`

      }

    }
  }
}
</script>

<style lang="scss">
.basic-block {
  opacity: 0;

  box-sizing: border-box;
  color: #fff;
  animation: mymove 1s;
  animation-fill-mode: forwards;

  .box {
    position: relative;
    box-sizing: border-box;
    padding: 15px;
    width: 100%;
    height: 100%;
    transition: all 1s;
    background-size: cover;

    &:hover {
      transform: rotateY(360deg);
    }
  }

  a {
    color: #fff;
  }

  span {
    display: block;
    font-size: 16px;
  }

  p {
    width: 80%;
    font-size: 10px;
    color: #eee;
    line-height: 22px;
  }

  i {
    position: absolute;
    bottom: 15px;
    right: 15px;
    font-size: 50px !important;
  }

  @keyframes mymove {
    from {
      opacity: 0;
      transform: scale(0);
    }
    to {
      opacity: 1;
      transform: scale(1);
    }
  }
}
</style>
