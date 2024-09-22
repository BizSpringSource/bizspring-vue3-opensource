<template>
	<div class="app-container calendar-list-container">
		<basic-container>
			<avue-crud ref="crud" @on-load="getPage" :option="tableOption" :data="tableData"
				:permission="permissionList" :table-loading="tableLoading" :before-open="beforeOpen" v-model="form"
				@refresh-change="refreshChange" @row-update="handleUpdate" @row-save="handleSave" @row-del="handleDel">
			</avue-crud>
		</basic-container>
	</div>
</template>

<script>
	import {
		addObj,
		delObj,
		fetchTree,
		putObj
	} from '@/api/upms/organ'
	import {
		tableOption
	} from '@/const/crud/upms/organ'
	import {
		mapGetters
	} from 'vuex'

	export default {
		name: 'organ',
		data() {
			return {
				form: {},
				tableOption: tableOption,
				tableData: [],
				tableLoading: false,
			}
		},
		created() {},
		computed: {
			...mapGetters(['permissions']),
			permissionList() {
				return {
					addBtn: this.permissions['sys:organ:add'] ? true : false,
					delBtn: this.permissions['sys:organ:del'] ? true : false,
					editBtn: this.permissions['sys:organ:edit'] ? true : false,
					viewBtn: this.permissions['sys:organ:get'] ? true : false,
				}
			},
		},
		methods: {
			getPage() {
				this.tableLoading = true
				fetchTree()
					.then((response) => {
						this.tableData = response.data.data
						this.$refs.crud.DIC.parentId = this.tableData
						this.tableLoading = false
					})
					.catch(() => {
						this.tableLoading = false
					})
			},
			handleDel: function(row, index) {
				var _this = this
				this.$confirm('是否确认删除此数据', '提示', {
						confirmButtonText: '确 定',
						cancelButtonText: '取消',
						type: 'warning',
					})
					.then(function() {
						return delObj(row.id)
					})
					.then((data) => {
						_this.$message({
							showClose: true,
							message: '已删除',
							type: 'success',
						})
						this.getPage(this.page)
					})
					.catch(function(err) {})
			},
			/**
			 * @title 数据更新
			 * @param row 为当前的数据
			 * @param index 为当前更新数据的行数
			 * @param done 为表单关闭函数
			 *
			 **/
			handleUpdate: function(row, index, done, loading) {
				putObj(row)
					.then((data) => {
						this.$message({
							showClose: true,
							message: '更新成功',
							type: 'success',
						})
						done()
						this.getPage(this.page)
					})
					.catch(() => {
						loading()
					})
			},
			/**
			 * @title 数据添加
			 * @param row 为当前的数据
			 * @param done 为表单关闭函数
			 *
			 **/
			handleSave: function(row, done, loading) {
				addObj(row)
					.then((data) => {
						this.$message({
							showClose: true,
							message: '保存成功',
							type: 'success',
						})
						done()
						this.getPage(this.page)
					})
					.catch(() => {
						loading()
					})
			},
			/**
			 * 刷新回调
			 */
			refreshChange(page) {
				this.getPage(this.page)
			},
			beforeOpen(done, type) {
				window.openType = type
				done()
			},
		},
	}
</script>