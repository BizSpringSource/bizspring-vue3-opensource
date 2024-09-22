<template slot-scope="scope" >
      <el-form-item prop="rate">
      <el-row>
        <el-row>
          <el-col :span="24">
              <input  placeholder="请输入" class="e-input"  @change="change"  type='text' v-model="value"  />
          </el-col>
        </el-row>
      </el-row>
      </el-form-item>
</template>
<script>
export default {
  data() {
    return {
      value:''
    };
  },
  props: {
    inputVal:{
      type:Array
    },
    inputIdx:{
      type:Number
    },
  },
  created() {
    // console.log("created inputVal:",this.inputVal);
    // console.log("created inputIdx:",this.inputIdx);
    // console.log("created key:",this.key);
    this.value=this.inputVal[this.inputIdx];
  },
  methods: {
    change(){
      this.$emit('childChange',this.value)
    },
  }
};

</script>
<style>
.e-input{
  -webkit-appearance: none;
  background-color: #FFF;
  background-image: none;
  border-radius: 4px;
  border: 1px solid #DCDFE6;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  color: #606266;
  display: inline-block;
  font-size: inherit;
  height: 40px;
  line-height: 40px;
  outline: 0;
  padding: 0 15px;
  -webkit-transition: border-color .2s cubic-bezier(.645,.045,.355,1);
  transition: border-color .2s cubic-bezier(.645,.045,.355,1);
  width: 100%;
}

</style>
