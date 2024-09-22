<script>
	import {
		refreshToken
	} from '@/utils/login.js'

	import bizRequest from '@/utils/request.js'
	export default {
		globalData: {
			navHeight: 0,
			userInfo: null,
			user: null,
			userType: "",
			navTopHeight: 35,
			navJiaonang: [{
				width: 100,
				height: 80
			}],
			headers: {
				'Authorization': '',
				'username': '',
				'UserId': '',
				'Content-Type': 'application/x-www-form-urlencoded',
				'Access-Control-Max-Age': 86400,
				'messages':null,
			},
			headersJson: {
				'Authorization': '',
				'username': '',
				'UserId': '',
				'Content-Type': 'application/json',
				'messages':null,
			},
			headersFile: {
				'Authorization': '',
				'username': '',
				'UserId': '',
				'messages':null,
			},
			
			messages:null,
			userInfo:null,
		},
		async onLaunch() {
			this.wxUpdateManager();
		},
		onShow: function(options) {
			this.getSystemInfo();
			this.onAuth();
		},
		methods: {
			wxUpdateManager: function() {
				// 版本更新  
				const updateManager = uni.getUpdateManager();
				updateManager.onCheckForUpdate(function(res) {
					// 请求完新版本信息的回调  
					if (res.hasUpdate) {
						this.logoutRemoveStorage()
						updateManager.onUpdateReady(function(res) {
							uni.showToast({
								title: '发现新版本',
								success() {
									updateManager.applyUpdate();
								}
							})
						});
						updateManager.onUpdateFailed(function(res) {
							// 新的版本下载失败  
							// wx.showModal({
							// 	title: '已经有新版本了哟~',
							// 	content: '新版本已经上线啦~，请您删除当前小程序，重新搜索打开哟~'
							// })
						});
					}
				});
			},
			getSystemInfo: function() {
				var that = this;
				uni.getSystemInfo({
					success(res) {
						that.navTopHeight = res.statusBarHeight + 4;
					}
				});
			},
			onAuth: function() {
				const userInfoStorage = uni.getStorageSync('userInfoStorage');
				if (userInfoStorage) {
					let rf_token = userInfoStorage.refresh_token;
					uni.showLoading({
						title: 'Loading...'
					})
					refreshToken(rf_token).then(response => {
						uni.hideLoading();
						if (response.data.user) {
							this.setAuthorization('Bearer ' + response.data.access_token);
							this.setUserId(response.data.user.id);
							this.setUsername(response.data.user.username);
							this.globalData.userInfo = response.data.user;
							this.loginSetStorage(response.data);
						} else {							
							this.logoutRemoveStorage();
						}
						// resolve();
					}).catch(error => {
						console.error("refresh login ==> error", error);
						uni.showToast({
								title: '系统未知错误,请反馈给管理员',
								duration: 2000
						});
					})
				} else {
					this.logoutRemoveStorage();
				}
			},
			

			
			//存储登录storage 信息，保持登录  增
			loginSetStorage: function(e) {
				uni.setStorageSync('userInfoStorage', e);
			},			
			//删除登录storage 信息，删除保持登录  删
			logoutRemoveStorage: function() {
				uni.removeStorageSync('userInfoStorage');
				this.setAuthorization(null);
				this.setUsername(null);				
				this.setUserId(null);
				this.globalData.userInfo=null;
			},			
			setUsername: function(username) {
				this.globalData.headersJson.username = username;
				this.globalData.headers.username = username
				this.globalData.headersFile.username = username;
			},
			setAuthorization: function(authorization) {
				this.globalData.headersJson.Authorization = authorization;
				this.globalData.headers.Authorization = authorization
				this.globalData.headersFile.Authorization = authorization;
			},
			setUserId: function(userId) {
				this.globalData.headersJson.UserId = userId;
				this.globalData.headers.UserId = userId
				this.globalData.headersFile.UserId = userId;
			},
			//将 messages 设置到 hander 头中。
			setHanderMessages: function(messages) {
				this.globalData.headersJson.messages = messages;
				this.globalData.headers.messages = messages;
				this.globalData.headersFile.messages = messages;
			},

		}
	};
</script>
<style>
	@import "./iconfont.css";
	@import './static/app.scss';

	page {
		background: #f4f4f4;
		padding-bottom: 25rpx;
		font-size: 21rpx;
	}

	.container {
		height: 100%;
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: space-between;
		padding: 200rpx 0;
		box-sizing: border-box;
	}

	.df {
		display: -webkit-box;
		display: -webkit-flex;
		display: -ms-flexbox;
		display: flex;
	}

	.df_1 {
		-webkit-box-flex: 1;
		-webkit-flex: 1;
		-ms-flex: 1;
		flex: 1;
		-webkit-tap-highlight-color: transparent;
	}

	.df_2 {
		-webkit-box-flex: 2;
		-webkit-flex: 2;
		-ms-flex: 2;
		flex: 2;
		-webkit-tap-highlight-color: transparent;
	}

	.bg {
		background: #fff;
	}

	.bg_white {
		background: #fff;
	}

	.bg_hui {
		background: #eee;
	}

	.bg_red {
		background: #dd2727;
	}

	.bg_green {
		background: #02bf02;
	}

	.bg_cheng {
		background: #f85;
	}

	.clear {
		clear: both;
	}

	.ovh1 {
		width: 100%;
		overflow: hidden;
		text-overflow: ellipsis;
		display: -webkit-box;
		-webkit-line-clamp: 1;
		-webkit-box-orient: vertical;
	}

	.red {
		color: #dd2727;
	}

	.c3 {
		color: #333;
	}

	.c6 {
		color: #666;
	}

	.c9 {
		color: #999;
	}

	.white {
		color: #fff;
	}

	.font_16 {
		font-size: 24rpx;
	}

	.font_15 {
		font-size: 27rpx;
	}

	.font_14 {
		font-size: 26rpx;
	}

	.font_13 {
		font-size: 24rpx;
	}

	.font_12 {
		font-size: 23rpx;
	}

	.font_10 {
		font-size: 20rpx;
	}

	.l_h20 {
		line-height: 20px;
	}

	/*定位*/
	.pr {
		position: relative;
	}

	.pa {
		position: absolute;
	}

	/*文字超出省略*/
	.ellipsis {
		text-overflow: ellipsis;
		white-space: nowrap;
		overflow: hidden;
		display: block;
	}

	/*对齐*/
	.tl {
		text-align: left;
	}

	.tc {
		text-align: center;
	}

	.tr {
		text-align: right;
	}

	/*间距*/
	.mt1 {
		margin-top: 1px;
	}

	.mt5 {
		margin-top: 5px;
	}

	.mt8 {
		margin-top: 8px;
	}

	/*.mt10{margin-top: 10px;}*/
	.mt15 {
		margin-top: 15px;
	}

	.mt20 {
		margin-top: 20px;
	}

	.mt25 {
		margin-top: 25px;
	}

	.mt30 {
		margin-top: 30px;
	}

	.mt35 {
		margin-top: 35px;
	}

	.mt40 {
		margin-top: 40px;
	}

	.mt60 {
		margin-top: 60px;
	}

	.p_all {
		padding: 3%;
	}

	/*边框*/
	.bte {
		border-top: solid 1px #eee;
	}

	.bbe {
		border-bottom: solid 1px #eee;
	}

	.bre {
		border-right: 1px solid #eee
	}

	/*浮动*/
	.fl_l {
		float: left;
	}

	.fl_r {
		float: right;
	}

	.w10 {
		width: 10%;
	}

	.w20 {
		width: 20%;
	}

	.w30 {
		width: 30%;
	}

	.w40 {
		width: 40%;
	}

	.w50 {
		width: 50%;
	}

	.w60 {
		width: 60%;
	}

	.w70 {
		width: 70%;
	}

	.w80 {
		width: 80%;
	}

	.w90 {
		width: 90%;
	}

	.w100 {
		width: 100%;
	}

	/*边框*/
	.bte {
		border-top: 1px solid #eee;
	}

	.bbe {
		border-bottom: solid 1px #eee;
	}

	.bre {
		border-right: 1px solid #eee;
	}

	.ble {
		border-left: 1px solid #eee;
	}

	.footfixed {
		display: block;
		position: fixed;
		bottom: 0;
		left: 0;
		background: #fff;
		z-index: 100;
		width: 100%;
		font-size: 23rpx;
		border-top: 1px solid #eee;
		line-height: 50px;
	}

	.cl_h {
		clear: both;
		height: 1px;
		margin-top: 60px;
	}

	.shop {
		position: relative;
	}

	.video {
		width: 45px;
		height: 45px;
		display: inline-block;
		position: absolute;
		right: 0;
		top: 0;
		z-index: 2;
	}

	.video image {
		width: 45px;
		height: 45px;
		background-size: 45px;
	}

	.search_no {
		width: 100%;
		display: block;
		color: #666;
		text-align: center;
		font-size: 23rpx;
	}

	.scimg {
		width: 100px;
		height: 100px;
		display: inline-block;
		background-size: 100px;
	}

	.x_right {
		width: 16px;
		height: 18px;
		margin-left: 10px;
	}

	button[type="warn"] {
		color: #FFFFFF;
		background-color: #d9002f;
	}









	/********************************************************************************************************/
	@charset "utf-8";
	/*手机css*/

	input[type=radio] {
		display: inline-block;
		vertical-align: middle;
		width: 20px;
		height: 20px;
		-webkit-appearance: none;
		background-color: transparent;
		border: 0;
		outline: 0 !important;
		line-height: 20px;
		color: #d8d8d8;
	}

	input[type=radio]:after {
		content: "";
		display: block;
		width: 20px;
		height: 20px;
		border-radius: 50%;
		text-align: center;
		line-height: 14px;
		font-size: 24rpx;
		color: #fff;
		border: 3px solid #ddd;
		background-color: #fff;
		box-sizing: border-box;
	}

	input[type=radio]:checked:after {
		content: "L";
		transform: matrix(-0.766044, -0.642788, -0.642788, 0.766044, 0, 0);
		-webkit-transform: matrix(-0.766044, -0.642788, -0.642788, 0.766044, 0, 0);
		border-color: #099414;
		background-color: #099414;
	}

	/************************************************************************************
RESET
*************************************************************************************/
/* 	html {
		height: 100%;
	} */

	body {
		color: #000;
		font-family: "microsoft yahei", Arial, Helvetica, sans-serif;
		background-color: #f5f5f5;
		font-size: 21rpx;
	}

	html,
	body,
	address,
	blockquote,
	div,
	dl,
	form,
	h1,
	h2,
	h3,
	h4,
	h5,
	h6,
	ol,
	p,
	pre,
	table,
	ul,
	dd,
	dt,
	li,
	tbody,
	td,
	tfoot,
	th,
	thead,
	tr,
	button,
	del,
	ins,
	map,
	object,
	a,
	abbr,
	acronym,
	b,
	bdo,
	big,
	br,
	cite,
	code,
	dfn,
	em,
	i,
	img,
	kbd,
	q,
	samp,
	small,
	span,
	strong,
	sub,
	sup,
	tt,
	var,
	legend,
	fieldset {
		margin: 0;
		padding: 0;
	}

	a {
		-webkit-tap-highlight-color: rgba(0, 0, 0, 0);
	}


	ol,
	ul,
	li {
		list-style: none;
		list-style-image: none;
		list-style-position: outside;
		list-style-type: none;
	}

	img,
	fieldset,
	abbr,
	acronym {
		border: 0 none;
	}

	del,
	ins {
		text-decoration: none;
	}

	a {
		text-decoration: none;
		color: #000;
		outline: none;
	}

	i,
	b,
	address {
		text-decoration: none;
		font-style: normal;
		font-weight: lighter;
	}

	caption {
		text-align: left;
	}

	time,
	mark {
		margin: 0px;
		padding: 0px;
		background: none
	}

	doc,
	aside,
	details,
	figcaption,
	figure,
	footer,
	header,
	hgroup,
	menu,
	nav,
	section {
		display: block;
		margin: 0;
		padding: 0;
	}

	sup {
		vertical-align: text-top;
	}

	sub {
		vertical-align: text-bottom;
	}

	.red {
		color: red;
	}

	.mt50 {
		width: 100%;
		height: 45px;
		overflow: hidden;
	}

	.gray {
		color: #999;
	}

	.orange {
		color: #ffa800;
	}

	h1,
	h2,
	h3,
	h4,
	h5,
	h6 {
		font-size: 23rpx;
	}

	form,
	fieldset,
	legend,
	input,
	button,
	textarea,
	p,
	blockquote,
	select {
		font-size: 23rpx;
		font-family: "microsoft yahei", Arial, Helvetica, sans-serif;
	}

	img {
		-ms-interpolation-mode: bicubic;
		max-width: 100%;
	}

	/*防止windows缩放图片时，图像失真*/
	.img_cspan {
		display: table-cell;
		display: block;
		text-align: center;
		position: relative;
		vertical-align: middle;
	}

	.img_cspan p {
		position: absolute;
		top: 50%;
		left: 50%;
	}

	.img_cspan p img {
		position: relative;
		top: -50%;
		left: -50%;
		z-index: 1;
	}

	/*check,radio*/
	input.check_txt[type='checkbox'] {
		display: none;
	}

	input.check_txt[type='checkbox']+label {
		display: block;
		float: left;
		-moz-appearance: none;
		-webkit-appearance: none;
		width: 20px;
		height: 20px;
		background: #fff;
		border: 2px solid #cecece;
		border-radius: 3px;
		cursor: pointer;
	}

	input.check_txt[type='checkbox']:checked+label {
		/* background: #f0f0f0 url("./static/images/check.png") no-repeat center center; */
		background-size: 80%;

	}

	/*提示*/
	.note {
		/* background:#fff url("./static/images/attention.gif") no-repeat 2px 2px; */
		padding-left: 24px;
		color: #000;
		font-size: 21rpx;
		line-height: 20px;
		border: 1px solid #ddd;
		box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.3);
		position: absolute;
		padding-right: 10px;
		left: 50%;
		margin-left: -25%;
	}

	/*分类*/
	.classify {
		width: 100%;
		position: relative;
	}

	.classify li {
		width: 100%;
		height: 50px;
		line-height: 50px;
		border-bottom: 1px dotted #ddd;
	}

	.classify li span {
		display: block;
		position: relative;
		width: 100%;
		text-indent: 1em;
		position: relative
	}

	.level_two {
		position: absolute;
		top: 0px;
		width: 190px;
		height: 100%;
		right: 0px;
		box-shadow: -1px 0px 3px rgba(0, 0, 0, 0.2);
		z-index: 10;
	}

	.class_three {
		/* background:url("./static/images/class_three.png") no-repeat; */
		width: 15px;
		height: 18px;
		overflow: hidden;
		position: absolute;
		right: 190px;
		z-index: 11;
		top: 30%;
		background-size: 100%;
	}

	.level_two_ul {
		width: 100%;
	}

	.level_two_ul li:nth-child(odd) {
		background: #fff;
	}

	.level_two_ul li:nth-child(even) {
		background: #f1f1f1;
	}

	.level_two_ul li {
		height: 50px;
		line-height: 50px;
		border-bottom: 1px dotted #ddd;
		text-indent: 1em;
	}

	.level_two_ul li a {
		display: block;
		height: 100%;
		width: 100%;
	}

	/*空购物车*/
	.car_none {
		min-width: 310px;
		max-width: 330px;
		margin-left: auto;
		margin-right: auto;
		background: #fff;
		overflow: hidden;
		margin-top: 10px;
	}

	.car_none span {
		display: block;
		width: 126px;
		height: 110px;
		margin-left: auto;
		margin-right: auto;
		overflow: hidden;
		margin-top: 50px;
		margin-bottom: 50px;
	}

	.car_none b {
		width: 100%;
		display: block;
		text-align: center;
		font-size: 23rpx;
		color: #575757;
		padding-bottom: 20px;
	}

	.car_none_btn {
		width: 100%;
		margin-top: 10px;
		text-align: center;
	}

	.car_none_btn input {
		background: -o-linear-gradient(top, #eeeeee, #cccccc);
		background: -moz-linear-gradient(top, #eeeeee, #cccccc);
		background: -webkit-linear-gradient(top, #eeeeee, #cccccc);
		background: -ms-linear-gradient(top, #eeeeee, #cccccc);
		background: linear-gradient(top, #eeeeee, #cccccc);
		font-size: 24rpx;
		text-shadow: 1px 1px 1px #fff;
		width: 170px;
		height: 36px;
		border: 1px solid #c1c1c1;
	}

	.car_none_btn input:hover {
		background: -o-linear-gradient(top, #ccc, #eee);
		background: -moz-linear-gradient(top, #ccc, #eee);
		background: -webkit-linear-gradient(top, #ccc, #eee);
		background: -ms-linear-gradient(top, #ccc, #eee);
		background: linear-gradient(top, #ccc, #eee);
	}

	/*空收货地址*/
	.addr_none {
		min-width: 310px;
		max-width: 330px;
		margin-left: auto;
		margin-right: auto;
		background: #fff;
		overflow: hidden;
		margin-top: 10px;
	}

	.addr_none span {
		display: block;
		width: 126px;
		height: 110px;
		margin-left: auto;
		margin-right: auto;
		overflow: hidden;
		margin-top: 10px;
		margin-bottom: 10px;
	}

	.addr_none b {
		width: 100%;
		display: block;
		text-align: center;
		font-size: 23rpx;
		color: #575757;
		padding-bottom: 20px;
	}

	.addr_none_btn {
		width: 100%;
		margin: 10px 0px;
		text-align: center;
	}

	.addr_none_btn input {
		background: -o-linear-gradient(top, #eeeeee, #cccccc);
		background: -moz-linear-gradient(top, #eeeeee, #cccccc);
		background: -webkit-linear-gradient(top, #eeeeee, #cccccc);
		background: -ms-linear-gradient(top, #eeeeee, #cccccc);
		background: linear-gradient(top, #eeeeee, #cccccc);
		font-size: 24rpx;
		text-shadow: 1px 1px 1px #fff;
		width: 170px;
		height: 36px;
		border: 1px solid #c1c1c1;
	}

	.addr_none_btn input:hover {
		background: -o-linear-gradient(top, #ccc, #eee);
		background: -moz-linear-gradient(top, #ccc, #eee);
		background: -webkit-linear-gradient(top, #ccc, #eee);
		background: -ms-linear-gradient(top, #ccc, #eee);
		background: linear-gradient(top, #ccc, #eee);
	}

	/*确认订单*/
	.order_bg {
		background: #edede3;
	}

	.order_box {
		padding: 5px;
	}

	.address_box {
		border: 2px solid #d0d0c4;
		background: #f8fdad;
		margin-top: 10px;
		border-radius: 5px;
		margin-top: 50px;
	}

	.address_box section {
		border: 1px solid #fff;
		padding: 10px;
		border-radius: 5px;
		padding-top: 0px;
		overflow: hidden
	}

	.address_box section h3 {
		height: 40px;
		line-height: 40px;
		color: #444;
		border-bottom: 1px solid #d1d19e;
		font-size: 23rpx;
	}

	.address_box section .fl {
		float: left;
	}

	.address_box section .head_right {
		float: right;
		margin-top: 5px;
	}

	.address_box section aside {
		width: 100%;
		/* background:url("./static/images/right_b.gif") no-repeat right center; */
		display: block;
		overflow: hidden;
	}

	.address_box section aside a {
		display: block;
		width: 100%;
		overflow: hidden;
	}

	.address_box section ul li {
		font-size: 23rpx;
		color: #777;
		width: 90%;
		float: left;
		margin-top: 10px;
	}

	.address_box section ul li b {
		width: 25%;
		float: left;
		height: 20px;
		display: block;
		text-align: justify;
		color: #444;
	}

	.address_box section ul li span {
		width: 75%;
		float: left;
		display: block;
		color: #444;
		font-size: 23rpx;
	}

	.address_box section ul li em {
		width: 75%;
		float: left;
		display: block;
		font-style: normal;
		font-size: 21rpx;
	}

	.pay_box {
		width: 100%;
	}

	.pay_box h3 {
		margin-top: 5px;
		font-size: 23rpx;
		font-weight: normal;
		padding-left: 10px;
	}

	.pay_box section {
		overflow: hidden;
		background: #fff;
		border: 1px solid #ddd;
		padding: 10px;
		border-radius: 5px;
	}

	.pay_box section a {
		display: block;
		width: 99%;
		/* background:url("./static/images/right_b.gif") no-repeat right 50%; */
		overflow: hidden;
	}

	.pay_box section ul li {
		font-size: 23rpx;
		color: #777;
		width: 90%;
		float: left;
		height: 30px;
		line-height: 30px;
	}

	.pay_box section ul li b {
		width: 30%;
		float: left;
		height: 20px;
		display: block;
		text-align: justify;
		color: #444;
		text-indent: 0.3em;
	}

	.pay_box section ul li span {
		width: 70%;
		float: left;
		display: block;
	}

	.pay_box section strong {
		font-size: 23rpx;
		color: #444;
		float: left;
		display: block
	}

	.pay_total_box {
		width: 100%;
		margin-top: 10px;
	}

	.pay_total_box h3 {
		height: 30px;
		line-height: 30px;
		font-size: 23rpx;
		font-weight: lighter;
	}

	.pay_total_box section {
		overflow: hidden;
		background: #fff;
		border: 1px solid #ddd;
		padding: 10px;
		border-radius: 5px;
	}

	.pay_total_box section ul li {
		width: 100%;
		height: 30px;
		line-height: 30px;
		font-size: 23rpx;
		color: #666;
	}

	.pay_total_box section ul li span {
		width: 25%;
		text-align: right;
		display: block;
		float: left;
		height: 30px;
	}

	.pay_total_box section ul li i {
		display: block;
		width: 35%;
		text-align: center;
		float: left;
		height: 30px;
	}

	.pay_total_box section ul li b {
		width: 40%;
		display: block;
		float: left;
		height: 30px;
	}

	.pay_total_box aside {
		width: 100%;
		padding-top: 10px;
		border-top: 1px solid #ddd;
		font-size: 21rpx;
		color: #666;
		text-align: center
	}



	/*表单填写*/
	.info_form {
		max-width: 320px;
		min-width: 300px;
		margin-left: auto;
		margin-right: auto;
	}

	.info_form ul {
		padding-left: 10px;
		float: left;
		color: #000;
	}

	.info_form ul li {
		width: 100%;
		float: left;
		margin-top: 10px;
		font-size: 23rpx;
		margin-bottom: 10px;
	}

	.info_form ul .form_title {
		line-height: 30px;
	}

	.info_form ul .form_size300 input {
		height: 30px;
		box-shadow: 1px 1px 1px 0px rgba(0, 0, 0, 0.1) inset;
		line-height: 30px;
		border: 1px solid #c1c1c1;
		background: #f6f6f6;
		width: 300px;
		border-radius: 3px;
	}

	.info_form ul li span {
		float: left;
		display: block;
		line-height: 30px;
	}

	.form_left {
		width: 75px;
		display: block;
		float: left;
		height: 30px;
	}

	.form_right {
		width: 225px;
		display: block;
		float: left;
	}

	.form_em {
		margin-right: 30px;
		float: left;
		display: block;
		font-style: normal
	}

	.form_i {
		width: 100px;
		display: block;
		float: left;
		margin-right: 10px;
		font-style: normal;
		margin-bottom: 10px;
	}

	.form_select {
		float: left;
		margin-right: 10px;
		width: 123px;
		height: 40px;
	}

	.sec_size123 select {
		width: 123px;
	}

	.sec_size255 select {
		width: 255px;
	}

	.sec_size200 select {
		width: 225px;
	}

	.form_btn {
		display: block;
		width: 100%;
		text-align: center;
	}

	.form_btn input {
		border-radius: 3px;
		height: 35px;
		background: -webkit-linear-gradient(top, #f5f5f5, #e6e6e6);
		background: -moz-linear-gradient(top, #f5f5f5, #e6e6e6);
		background: -ms-linear-gradient(top, #f5f5f5, #e6e6e6);
		background: -o-linear-gradient(top, #f5f5f5, #e6e6e6);
		background: linear-gradient(top, #f5f5f5, #e6e6e6);
		box-shadow: 0px 0px 2px #fff inset, 1px 1px 0px #f1f1f1;
		width: 130px;
		border: 1px solid #c1c1c1;
	}

	.form_radio input[type='radio'],
	.form_check input[type='checkbox'] {
		display: none
	}

	.form_radio input[type='radio']+label,
	.form_check input[type='checkbox']+label {

		position: relative;
		top: 6px;
		margin-right: 5px;
		-moz-appearance: none;
		-webkit-appearance: none;
		display: block;
		float: left;
		width: 20px;
		height: 20px;
		background: #dcdfe0;
		box-shadow: inset 0px 0px 2px rgba(0, 0, 0, 0.3), inset 0 2px 3px rgba(0, 0, 0, 0.2), 0 1px 0 rgba(255, 255, 255, 0.9), 0 -1px 0 rgba(0, 0, 0, 0.4);
		cursor: pointer;
	}

	.form_radio input[type='radio']+label {
		border-radius: 50%;
	}

	.form_check input[type='checkbox']+label {
		border-radius: 3px;
	}

	.form_radio input[type='radio']:checked+label:after {
		content: '';
		width: 10px;
		height: 10px;
		display: block;
		background: #77b1d7;
		position: absolute;
		left: 5px;
		top: 5px;
		border-radius: 50%;
		box-shadow: 0px 1px 0px rgba(255, 255, 255, 0.8);
	}

	.form_check input[type='checkbox']:checked+label {
		/* background: #dcdfe0 url("./static/images/check_blue.gif") no-repeat center center; */
	}

	.form_area textarea {
		width: 225px;
		height: 50px;
		resize: none;
		float: left;
		margin-top: 10px;
		border-radius: 3px;
		background: #f6f6f6;
		box-shadow: 1px 1px 1px 0px rgba(0, 0, 0, 0.1) inset;
		filter: shadow(color=#f1f1f1, direction=255);
		border: 1px solid #ddd;
	}

	/*订单列表*/
	.orderlist_bg {
		background: #eeeeee;
	}

	.order_list {
		max-width: 340px;
		min-width: 320px;
		margin-left: auto;
		margin-right: auto;
		padding-bottom: 60px;
	}

	.order_list_one {
		width: 100%;
		margin-bottom: 10px;
	}

	.order_list_one dt {
		width: 100%;
		background: #fff;
		border-bottom: 1px solid #ddd;
		height: 35px;
		line-height: 35px;
		overflow: hidden;
		font-size: 23rpx;
	}

	.order_list_one dt span {
		float: right;
		margin-left: 5px;
		color: #333;
		margin-right: 5px;
		font-size: 21rpx;
	}

	.order_list_one dt span strong {
		color: #d70d0d;
		float: left;
	}

	.order_list_one dt span b {
		color: #d70d0d;
		font-size: 23rpx;
		float: left;
		font-weight: bold;
	}

	.order_list_one dt span i {
		float: left;
		font-style: normal;
	}

	.order_list_one dt h3 {
		color: #333;
		font-size: 23rpx;
		padding-left: 5px;
		float: left
	}

	.order_list_one dt h3 b {
		color: #0976f0;
	}

	.order_list_one ul {
		background: #fbfbfb;
		width: 100%;
		overflow: hidden
	}

	.order_list_one ul li {
		width: 100%;
		float: left;
		overflow: hidden;
		padding-top: 10px;
		padding-bottom: 10px;
		border-bottom: 1px solid #ddd;
	}

	.order_list_one ul li a {
		width: 97%;
		display: block;
		overflow: hidden;
		/* background:url("./static/images/right_b.gif") no-repeat right center; */
	}

	.order_list_one ul li span {
		width: 55px;
		height: 55px;
		display: block;
		overflow: hidden;
		float: left;
		padding-left: 10px;
		margin-right: 10px;
	}

	.order_list_one ul li h4 {
		font-size: 23rpx;
		font-weight: lighter;
		height: 23px;
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
		width: 220px;
	}

	.order_list_one ul li h5 {
		font-size: 21rpx;
		font-weight: lighter;
		height: 20px;
	}

	.order_list_one ul li h5 b {
		margin-right: 20px;
		color: #999
	}

	.order_list_one ul li h5 i {
		font-style: normal;
		color: #d70d0d
	}

	.order_list_one ul li h6 {
		font-size: 23rpx;
		font-weight: lighter;
		height: 15px;
	}

	.order_list_one ul li h6 b {
		margin-right: 15px;
	}

	.order_list_one ul li h6 i {
		font-style: normal;
		color: #d70d0d;
		font-size: 23rpx;
		font-weight: bold
	}

	.favorite_hd {
		width: 100%;
		height: 40px;
		line-height: 40px;
		background-color: #fff;
		border-top: 1px solid #eee;
		border-bottom: 1px solid #eee;
	}

	.favorite_hd ul li {
		width: 50%;
		float: left;
		text-align: center;
		font-size: 23rpx;
	}

	.favorite_hd ul li a {
		width: 100%;
		float: left;
	}

	.favorite_hd ul li.this a {
		border-bottom: 1px solid #f94d4d;
		color: #f94d4d;
	}

	/*收货地址列表*/
	.address_list {
		max-width: 640px;
		min-width: 300px;
		margin-left: auto;
		margin-right: auto;
		padding-bottom: 10px;
	}

	.address_list dl {
		overflow: hidden;
		margin-top: 10px;
	}

	.address_list dl dt {
		background: #fafafa;
		border: 1px solid #e0e0e0;
		margin: -1px;
		width: 70%;
		float: left;
		position: relative;
		padding-bottom: 10px;
	}

	.address_list dl dt h3 {
		height: 20px;
		line-height: 20px;
		font-size: 23rpx;
		padding-left: 25px;
		padding-top: 15px;
	}

	.address_list dl dt h3 strong {
		float: left;
		display: block;
		width: 105px;
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
	}

	.address_list dl dt h3 b {
		float: right;
		margin-right: 10px;
	}

	.address_list dl dd {
		width: 15%;
		float: left;
		border: 1px solid #d0d0d0;
		margin: -1px;
		height: 95px;
		line-height: 95px;
		text-align: center;
	}

	.address_list dl dt span {
		display: block;
		padding-left: 25px;
		margin-right: 10px;
		font-size: 21rpx;
		color: #666;
		line-height: 20px;
		margin-top: 10px;
		height: 40px;
		clear: both;
	}

	.address_list dl dt i {
		width: 25px;
		height: 25px;
		display: block;
		overflow: hidden;
		position: absolute;
		top: 0px;
		left: 0px;
	}

	.address_list dl dd span {
		width: 25px;
		height: 100%;
		overflow: hidden;
		display: block;
		margin: 0 auto;
		padding: 0px;
	}

	.address_list dl dd b {
		text-align: center;
		font-size: 21rpx;
		color: #999;
	}

	.address_list_edit {
		display: block;
		overflow: hidden;
		height: 100%;
		margin: 0 auto;
		padding: 0px;
		font-size: 23rpx;
		color: #ffffff;
	}

	.address_list_edit:hover {
		/* background:#eaeaea url("./static/images/address_right.gif") no-repeat left 50%; */
		font-size: 23rpx;
		color: #000000;
	}

	/*商品列表*/
	.product_bg {
		background: #e7dfda;
	}

	.shop_top {
		height: 38px;
		width: 100%;
		overflow: hidden;
		/* background:url("./static/images/shop-top_bg.gif") repeat */
	}

	.shop_top ul {
		width: 103%;
		height: 38px;
		margin-left: -1px;
	}

	.shop_top ul li {
		width: 33%;
		float: left;
		height: 36px;
		border-bottom: 2px solid #000;
		line-height: 40px;
		text-align: center;
		border-left: 1px solid #333;
		box-shadow: 0px 0px 1px #ccc;
		font-size: 23rpx;
		color: #fff;
		font-weight: bold;
	}

	.shop_top ul li i {
		height: 20px;
		display: block;
		position: absolute;
		right: 0px;
		width: 6px;
		top: -2px;
	}

	.shop_top ul .this {
		background: #f1f1f1;
		height: 36px;
		border-bottom: 2px solid #c70100;
		color: #c70100;
		text-shadow: 1px 1px 0px #fff;
	}

	.s_top {
		line-height: 0px;
		height: 0px;
		border-width: 6px;
		border-style: solid;
		width: 0px;
		transition: transform 0.2s ease-in 0s;
		border-color: transparent transparent rgb(206, 206, 206);
		position: absolute;
		top: -4px;
		right: 0px;
	}

	.shop_top ul li strong {
		padding-right: 20px;
		position: relative;
	}

	.s_top_this {
		line-height: 0px;
		height: 0px;
		border-width: 6px;
		border-style: solid;
		width: 0px;
		transition: transform 0.2s ease-in 0s;
		border-color: transparent transparent rgb(228, 2, 2);
		position: absolute;
		top: -4px;
		right: 0px;
	}

	.s_bottom {
		line-height: 0px;
		height: 0px;
		border-width: 6px;
		border-style: solid;
		width: 0px;
		transition: transform 0.2s ease-in 0s;
		border-color: rgb(206, 206, 206) transparent transparent;
		position: absolute;
		bottom: -4px;
		right: 0px;
	}

	.s_bottom_this {
		line-height: 0px;
		height: 0px;
		border-width: 6px;
		border-style: solid;
		width: 0px;
		transition: transform 0.2s ease-in 0s;
		border-color: rgb(228, 2, 2) transparent transparent;
		position: absolute;
		bottom: -4px;
		right: 0px;
	}

	.product_list {
		min-width: 310px;
		max-width: 340px;
		column-count: 2;
		-moz-column-count: 2;
		-webkit-column-count: 2;
		-o-column-count: 2;
		padding-top: 10px;
		column-gap: 5px;
		-moz-column-gap: 5px;
		-webkit-column-gap: 5px;
		-o-column-gap: 5px;
	}

	.product_list a {
		display: inline-block;
		margin-bottom: 10px;
	}

	.product_list ul {
		border: 1px solid #ddd;
		overflow: hidden;
		border-radius: 5px;
		background: #fff;
		float: left;
	}

	.product_list ul .product_img {
		max-width: 165x;
		min-width: 150px;
		display: block;
		overflow: hidden;
	}

	.product_list ul .product_img span {
		max-width: 165px;
		min-width: 150px;
		overflow: hidden;
	}

	.product_list ul .procuct_name {
		padding: 5px 8px;
		font-size: 23rpx;
		color: #000;
	}

	.product_list ul .product_price span {
		font-size: 24rpx;
		font-weight: bold;
		color: #c70100;
		margin: 7px 5px 2px;
		border-top: 1px solid #f1f1f1;
		display: block;
		line-height: 30px;
	}

	.product_list ul .product_price span i {
		width: 15px;
		height: 15px;
		display: block;
		float: left;
		/* background:url("./static/images/pro.png") no-repeat; */
		background-size: 100%;
		margin-top: 8px;
		margin-right: 5px;
	}

	.product_list ul .product_ads {
		padding: 10px;
		height: 15px;
		line-height: 15px;
		color: #333;
		font-size: 21rpx;
		background: #f2f2f2;
	}

	.product_list ul .product_ads strong {
		float: right
	}

	.product_list ul .product_ads b {
		font-size: 20rpx;
		font-weight: lighter;
		color: #666;
	}

	/*团购*/
	.group_top {
		height: 40px;
		width: 100%;
		background-color: #fff;
	}

	.group_top ul {
		height: 40px;
		width: 100%;
		margin-left: -1px;
		float: left;
	}

	.group_top ul li {
		width: 33%;
		float: left;
		border-left: 1px solid #ddd;
		border-bottom: 1px solid #ddd;
		height: 40px;
		text-align: center;
		font-size: 23rpx;
		line-height: 40px;
	}

	.group_top ul li.this {
		border-bottom: 1px solid #F00;
	}

	.group_top ul li.this a {
		color: #F00;
	}

	.group_top ul li a {
		display: block;
		width: 90%;
		margin-left: auto;
		margin-right: auto;
		height: 40px;
		color: #555;
	}

	.group_top ul li span {
		padding-left: 20px;
		position: relative;
	}

	.group_top ul li a b {
		display: block;
		width: 15px;
		height: 14px;
		position: absolute;
		left: 0px;
		top: 0px;
	}

	.group_top ul li a b img {
		float: left;
		margin-top: 2px;
	}

	.group_top ul li a i {
		display: block;
		width: 15px;
		height: 14px;
		position: absolute;
		left: 0px;
		top: 0px;
	}

	.group_top ul li a i img {
		float: left;
		margin-top: 1px;
	}

	.group_list {
		width: 100%;
		overflow: hidden;
		margin-bottom: 15px;
	}

	.group_list a {
		display: block;
		overflow: hidden;
	}

	.group_list dl {
		border: 1px solid #ddd;
		/* background:#fff url("./static/images/right_b.gif") no-repeat 97% center; */
		padding: 14px 5px;
		margin-top: 10px;
		border-radius: 4px;
		overflow: hidden
	}

	.group_list dl dt {
		width: 90px;
		height: 90px;
		overflow: hidden;
		float: left;
		margin-right: 10px;
		display: block
	}

	.group_list dl dt img {
		width: 90px;
		height: 90px;
	}

	.group_list dl dd {
		width: 65%;
		float: left
	}

	.group_list dl dd h3 {
		font-size: 23rpx;
		font-weight: lighter;
		color: #000
	}

	.group_list dl dd h3 i {
		font-size: 21rpx;
		padding: 0px 5px;
		overflow: hidden;
		border-radius: 2px;
		background: #6ea724;
		text-align: center;
		line-height: 17px;
		color: #FFF;
		margin-right: 5px;
	}

	.group_list dl dd h3 b {
		display: block;
		line-height: 20px;
		height: 40px;
		font-size: 21rpx;
		overflow: hidden;
	}

	.group_list dl dd span {
		margin-top: 7px;
		font-size: 21rpx;
		color: #7a7a7a;
		float: left
	}

	.group_list dl dd span strong {
		font-size: 24rpx;
		color: #ff3300;
		margin-right: 10px;
		font-weight: normal;
		font-family: Arial, Helvetica, sans-serif;
	}

	.group_list dl dd span b {
		text-decoration: line-through
	}

	.group_list dl dd em {
		font-style: normal;
		float: left;
		font-size: 21rpx;
		color: #7a7a7a;
		width: 100%;
		display: block;
		line-height: 14px;
		margin-top: 10px;
		float: left;
	}

	.group_list dl dd em b {
		font-size: 23rpx;
		color: #fa4903;
		float: right
	}

	/*详细页*/
	.details_index {
		padding-bottom: 60px;
		margin-top: 45px;
	}

	.details_top {
		width: 100%;
		background: #fff;
		overflow: hidden;
		border-bottom: 1px solid #ddd;
	}

	.details_photo {
		width: 320px;
		margin-left: auto;
		margin-right: auto;
		position: relative;
		/* 		border-bottom: 1px solid #eee;
		padding-bottom: 5px; */
	}

	.deta_photo {
		width: 230px;
		margin-left: auto;
		margin-right: auto;
		text-align: center;
		overflow: hidden;
	}

	.details_photo img {
		width: 100%;
	}

	.details_photo span {
		font-size: 23rpx;
		color: #444;
		position: absolute;
		top: 0px;
		left: 5px;
	}

	.details_top section {
		width: 100%;
		padding-top: 10px;
		padding-bottom: 10px;
		overflow: hidden;
	}

	.details_price {
		overflow: hidden
	}

	.details_price ul {
		width: 85%;
		float: left;
	}

	.details_price span {
		display: block;
		/* width: 100rpx; */
		float: left;
		/* border-left: 1px dashed #ddd; */
		text-align: center;
	}

	.details_price ul li {
		width: 100%;
		float: left
	}

	.details_price ul .details_price_money {
		font-size: 23rpx;
		padding-left: 10px;
	}

	.details_price ul .details_price_money strong {
		color: #F24B48;
		font-size: 26rpx;
		font-weight: normal;
		padding-top: 10px;
	}

	.details_price ul .details_price_ads {
		font-size: 23rpx;
		color: #999;
		margin-top: 5px;
		padding-left: 10px;
	}

	.details_price ul .details_price_ads b {
		margin-right: 20px;
	}

	.details_star {
		display: block;
		width: 27px;
		height: 27px;
		/* background:url("./static/images/star_bg.png") no-repeat; */
		background-size: 100%;
		margin-top: 4px;
		float: right;
		margin-right: 5px;
	}

	.details_star img {
		margin-top: -1px;
	}

	.details_box {
		/* padding: 5px; */
		border-top: 1px solid #ddd;
		border-bottom: 1px solid #ddd;
		background: #fff;
		margin-top: 10px;
	}

	.details_name a {
		display: block;
		overflow: hidden;
	}

	.details_name h3 {
		border-bottom: 1px dashed #ddd;
		height: 30px;
		line-height: 30px;
		font-size: 24rpx;
		color: #000;
		padding-bottom: 5px;
		/* background:url("./static/images/details_right.gif") no-repeat right center; */
		font-weight: lighter;
	}

	.details_name p {
		line-height: 20px;
		color: #666;
		margin-top: 10px;
		font-size: 23rpx;
	}

	.standard_active {
		width: 100%;
		float: left;
		/* background:url("./static/images/details_right_t.gif") no-repeat right center; */
		overflow: hidden
	}

	.standard_bottom {
		width: 100%;
		float: left;
		border-top: 1px dashed #ddd;
		margin-top: 10px;
	}

	.standard_bottom li {
		width: 100%;
		margin-bottom: 5px;
		margin-top: 10px;
		float: left
	}

	.standard_bottom li strong {
		font-size: 23rpx;
		color: #333;
		font-weight: lighter
	}

	.standard_check a {
		display: block;
		float: left;
		height: 30px;
		width: 90px;
		margin-right: 5px;
		line-height: 30px;
		text-align: center;
		font-size: 23rpx;
		color: #333;
		border: 1px solid #ddd;
		margin-bottom: 10px;
		overflow: hidden;
		white-space: nowrap;
		text-overflow: ellipsis;
	}

	.standard_check .this {
		border: 1px solid #f00;
		/* background:url("./static/images/ok.png") no-repeat bottom right; */
		background-size: 15%;
	}

	.standard_num span {
		float: left;
		font-weight: lighter;
		color: #333;
		font-size: 23rpx;
		line-height: 27px;
	}

	.details_num {
		float: left;
		height: 30px;
		border: 1px solid #ddd;
		border-right: 0px;
		margin-right: 10px;
	}

	.details_num i {
		border-right: 1px solid #ddd;
		background: #f1f1f1;
		width: 30px;
		height: 30px;
		text-align: center;
		display: block;
		float: left
	}

	.details_num i img {
		margin-top: 7px;
	}

	.details_num input {
		border: 0px;
		border-right: 1px solid #ddd;
		height: 28px;
		text-align: center;
		line-height: 28px;
		font-size: 24rpx;
		color: #333;
		width: 50px;
		float: left;
	}

	.standard_num em {
		font-size: 23rpx;
		color: #666;
		line-height: 27px;
		font-style: normal
	}

	.details_evaluate ul li {
		height: 30px;
		width: 100%;
		padding-top: 10px;
		padding-bottom: 10px;
		font-size: 23rpx;
		line-height: 30px;
	}

	.details_evaluate ul .details_eli {
		/* background:url("./static/images/details_right.gif") no-repeat right center; */
		border-bottom: 1px dashed #ddd;
	}

	.details_evaluate ul li a {
		display: block;
		overflow: hidden;
	}

	.details_evaluate ul li b {
		float: right;
		margin-right: 20px;
		font-size: 23rpx;
		color: #666;
	}

	.details_evaluate ul li b i {
		margin-right: 10px;
		float: left
	}

	.details_evaluate ul li b i em {
		float: left;
		width: 16px;
		height: 16px;
		overflow: hidden;
		margin-right: 5px;
		margin-top: 7px;
	}

	.details_evaluate ul li b i em img {
		float: left;
	}

	.details_evaluate ul li span {
		float: left;
		font-size: 24rpx;
		color: #000;
		height: 30px;
	}

	.details_evaluate ul li strong {
		font-size: 23rpx;
		font-weight: lighter;
		color: #666;
		padding-left: 10px;
	}

	.details_shop a {
		display: block;
		overflow: hidden;
		line-height: 27px;
		font-size: 23rpx;
	}

	.details_shop span {
		float: right;
		font-size: 21rpx;
		border: 1px solid #ddd;
		/* background:#f1f1f1 url("./static/images/right_b.gif") no-repeat 95% center; */
		height: 27px;
		line-height: 27px;
		padding-right: 30px;
		padding-left: 20px;
	}

	.details_total {
		width: 100%;
		background: #f9f9f9;
		position: fixed;
		height: 50px;
		bottom: 0px;
		padding-top: 5px;
		box-shadow: 0px -1px 2px rgba(0, 0, 0, 0.3)
	}

	.details_total .add_car {
		height: 35px;
		line-height: 35px;
		width: 39%;
		margin: 5px 1%;
		text-align: center;
		color: #FFF;
		float: left;
		background: #FBA949;
		border-radius: 4px;
	}

	.details_total .atonce {
		height: 35px;
		line-height: 35px;
		width: 39%;
		margin: 5px 1%;
		text-align: center;
		color: #FFF;
		float: left;
		background: #FD6161;
		border-radius: 4px;
	}

	.details_total .details_enjoy {
		float: right;
		width: 12%;
		margin: 5px 3%;
		height: 35px;
		text-align: center;
		background-size: 100%;
		float: right;
		display: block;
		position: relative;
	}

	.details_total .car_num {
		border: 2px solid #fff;
		position: absolute;
		background: #c90000;
		color: #fff;
		width: 18px;
		height: 18px;
		line-height: 18px;
		overflow: hidden;
		top: -5px;
		right: -10px;
		border-radius: 50%;
		font-size: 20rpx;
		text-align: center;
	}

	.details_top .name {
		padding: 5px;
		overflow: hidden;
		line-height: 24px;
		height: 48px;
		overflow: hidden;
		border-top: 1px solid #eee;
	}

	/**商品详细列表**/
	.pro_intro_tabone {
		width: 98%;
		float: left;
		margin-top: 15px;
		margin-left: 1%;
	}

	.pro_intro_tabone ul {
		width: 100%;
		float: left;
	}

	.pro_intro_tabone ul li {
		float: left;
		width: 48%;
		margin-right: 2%;
		overflow: hidden;
		height: 22px;
		line-height: 22px;
	}

	/** 商品详情 **/
	.datespan {
		line-height: 30px;
		margin-right: 10px;
		text-align: right;
		width: 40px;
	}

	.detailsbottom ul li {
		float: left;
		margin-top: 10px;
	}

	.detailsbottom ul li span {
		display: block;
		float: left;
	}

	.colordate .chosecolor {
		float: left;
		overflow: hidden;
		padding-top: 1px;
	}

	.colordate .datespan {
		float: left;
		overflow: hidden;
		padding-top: 1px;
	}

	.colordate .chosecolor b {
		display: block;
		float: left;
		height: 35px;
		margin-top: 5px;
	}

	.colordate .chosecolor a {
		background: #fff none repeat scroll 0 0;
		border: 1px solid #ccc;
		display: block;
		line-height: 18px;
		float: left;
		margin-right: 10px;
		padding: 7px 8px;
	}

	.colordate .chosecolor a:hover {
		color: #000;
	}

	.colordate .chosecolor .this {
		/* background: rgba(0, 0, 0, 0) url("./static/images/chose.png") no-repeat scroll right bottom; */
		border: 2px solid #f80606;
		margin-top: -1px;
	}

	/**组合**/
	.details_box .act {
		width: 100%;
		border-bottom: 1px solid #eee;
		padding-bottom: 8px;
		overflow: hidden;
		padding-top: 3px;
	}

	.details_box .act dt {
		width: 100%;
		float: left;
		padding: 5px 0px;
	}

	.details_box .act .fl {
		font-size: 23rpx;
	}

	.details_box .act .fl b {
		padding: 2px;
		color: #F24B48;
		border: 1px solid #F29E9E;
		font-size: 21rpx;
		border-radius: 2px;
	}

	.details_box .act dd {
		width: 100%;
		float: left;
		margin-top: 5px;
	}

	.details_box .act .com_box {
		overflow: hidden;
		margin-top: 5px;
	}

	.details_box .act .com_box span {
		width: 15px;
		line-height: 20px;
		height: 62px;
		background-color: #FF6B6B;
		float: left;
		color: #fff;
		text-align: center;
	}

	.details_box .act .com_box ul li {
		float: left;
		width: 60px;
		height: 60px;
		display: block;
		margin-right: 10px;
		border: 1px solid #eee;
	}

	/**规格**/
	.details_box .stand {
		width: 100%;
		overflow: hidden;
		border-bottom: 1px solid #eee;
		padding: 5px;
	}

	.details_box .stand dt {
		width: 100%;
		float: left;
		padding: 5px 0px;
		vertical-align: middle;
	}

	.details_box .stand dt .fl {
		font-size: 23rpx;
	}

	.details_box .stand dt .fl b {
		font-size: 21rpx;
		color: #666;
	}

	.details_box .stand dt .fr {
		margin-top: 5px;
	}

	.details_box .stand dd {
		width: 96%;
		padding-left: 4%;
		float: left;
		color: #666;
	}

	.details_box .stand dd ul li {
		width: 100%;
		display: block;
		float: left;
		line-height: 26px;
		margin-bottom: 5px;
	}

	.details_box .stand dd ul li .fl {
		width: 40px;
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
	}

	.details_box .stand dd ul li p a {
		border: 1px solid #ccc;
		padding: 0px 5px;
		margin-right: 10px;
		margin-bottom: 5px;
		border-radius: 4px;
		font-size: 21rpx;
	}

	.details_box .stand dd ul li p a.this {
		border-color: #FF6B6B;
		color: #fff;
		background-color: #FF6B6B;
	}

	.details_box .stand dd ul li .num {
		float: left;
	}

	.details_box .stand dd ul li .minus,
	.details_box .stand dd ul li .plus {
		width: 32px;
		height: 26px;
		line-height: 20px;
		text-align: center;
		border: 1px solid #ccc;
		background-color: #fff;
		float: left;
		font-size: 26rpx;
	}

	.details_box .stand dd ul li .minus {
		background-color: #f1f1f1;
	}

	.details_box .stand dd ul li input {
		width: 32px;
		height: 26px;
		border: 1px solid #ccc;
		border-left: none;
		border-right: none;
		float: left;
		text-align: center;
		color: #000;
	}

	.detail_chose {
		color: #f60;
		padding-left: 20px;
	}

	/**送至**/
	.details_box .send {
		width: 100%;
		overflow: hidden;
		border-bottom: 1px solid #eee;
		padding-top: 5px;
		padding-bottom: 5px;
	}

	.details_box .send dt {
		width: 100%;
		float: left;
		padding: 5px 0px;
		line-height: 20px;
	}

	.details_box dl:last-of-type {
		border-bottom: 0px;
	}

	.details_box .send dt .sel {
		margin-right: 5px;
	}

	.details_box .send dt .sel1 {
		margin-right: 5px;
	}

	.details_box .send dt .sel select,
	.details_box .send dt .sel1 select {
		width: 100px;
		font-size: 21rpx;
		color: #666;
		margin-left: 2px;
		border: 1px solid #ddd;
	}

	.stand span {
		color: #848689;
	}

	.stand em {
		font-style: normal;
		color: #000;
	}

	.detbt {
		float: left;
		margin-right: 10px;
		text-align: right;
	}

	.logistics {
		float: left;
		line-height: 28px;
		margin-right: 10px;
	}

	.start {
		float: left;
		margin-right: 5px;
	}

	/* .log_cur {
  height:28px;
} */
	.log_cur a {
		display: inline-block;
		float: left;
		line-height: 18px;
	}

	.log_cur em {
		float: left;
	}

	.log_cur a i {
		width: 6px;
		height: 3px;
		margin-top: 7px;
		margin-left: 10px;
		float: left;
		/* background:url("./static/images/icon_3.png") no-repeat right center; */
	}

	.goods_ct {
		background: #faffff none repeat scroll 0 0;
		height: 100%;
		overflow: scroll;
		position: fixed;
		top: 0;
		left: 0;
		width: 100%;
		z-index: 101;
	}

	.goods_ct_list {
		background: #faffff none repeat scroll 0 0;
		height: 100%;
		overflow: scroll;
		position: fixed;
		top: 0;
		right: 0;
		width: 87.5%;
		z-index: 102;
	}

	.goods_ct_mask {
		background: #fafafa none repeat scroll 0 0;
		height: 100%;
		top: 0;
		left: 0;
		min-height: 100%;
		position: fixed;
		width: 100%;
		z-index: 102;
	}

	.goods_ct h3 {
		background: #fff none repeat scroll 0 0;
		border-bottom: 1px solid #bfbfbf;
		/* border-image: url("./static/images/index.gif") 2 0 2 0 round round; */
		border-width: 0 0 1px;
		height: 44px;
		text-align: center;
		position: fixed;
		top: 0;
		width: 87.5%;
		z-index: 111;
	}

	.spacer44 {
		height: 44px;
	}

	.goods_ct .titleback {
		float: left;
		display: inline-block;
		height: 20px;
		margin-left: 15px;
		padding: 12px 0;
		width: 12px;
	}

	.goods_ct .h3_title {
		display: inline-block;
		font-size: 24rpx;
		line-height: 16px;
		padding: 14px 7px;
	}

	.Region {
		height: 100%;
		min-height: 100%;
		overflow-y: auto;
		width: 100%;
	}

	.Region ul {
		float: left;
		overflow-x: hidden;
		width: 100%;
	}

	.Region .item {
		border-bottom: 1px solid #e0e0e0;
		border-width: 0 0 1px;
		font-size: 2rpx;
		height: 43px;
		position: relative;
	}

	.Region ul li a {
		color: #252525;
		font-size: 22rpx;
		height: 43px;
		line-height: 43px;
	}

	.Region ul li span {
		color: #252525;
		font-size: 22rpx;
		height: 43px;
		line-height: 43px;
		padding-left: 16px;
		display: inline-block;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
		width: 83%;
	}

	.Region .Cities {
		background-color: #ddd;
		/* border: 1px solid #e6e6e6; */
		left: 53px;
		padding: 10px;
		position: absolute;
		top: 13px;
		width: 100%;
		z-index: 199;
	}

	.Region .Cities ul {
		float: left;
		width: 100%;
	}

	.Region .Cities ul li {
		float: left;
		border-bottom: 1px solid #e0e0e0;
		border-width: 0 0 1px;
		height: 22px;
		line-height: 22px;
		margin: 5px;
		overflow: hidden;
		white-space: normal;
		width: 22%;
	}

	.Region .Cities ul li a {
		float: left;
		display: block;
		overflow: hidden;
		height: 20px;
		line-height: 20px;
		padding: 0 5px;
		width: 70px;
	}


	.details_box .send dt .sel select option,
	.details_box .send dt .sel1 select option {
		padding: 2px;
	}

	/**成交记录**/
	.details_box .send .eva {
		width: 100%;
		display: block;
		overflow: hidden;
	}

	.details_box .send .eva li {
		width: 49%;
		display: block;
		float: left;
		border-right: 1px solid #eee;
	}

	.details_box .send .eva li a {
		width: 100%;
		display: block;
		text-align: center;
	}

	.details_box .send .eva li:last-of-type {
		border-right: 0px;
	}

	/**好评度**/
	.evaluation_top {
		width: 100%;
		background: #fff;
		overflow: hidden;
		border-bottom: 1px solid #ddd;
		border-top: 1px solid #ddd;
		clear: both;
		margin-top: 8px;
	}

	.evaluation_top_box {
		width: 300px;
		margin-left: auto;
		margin-right: auto;
		padding-top: 20px;
		line-height: 20px;
		padding-bottom: 15px;
		overflow: hidden
	}

	.evaluation_top_box strong {
		color: #bc0000;
		font-size: 28rpx;
		padding-left: 30px;
		float: left;
	}

	.evaluation_top_box b {
		font-size: 23rpx;
		color: #666;
		float: left;
	}

	.evaluation_top_box span {
		width: 150px;
		overflow: hidden;
		height: 10px;
		background: #ccc;
		float: left;
		display: block;
		margin-top: 5px;
	}

	.evaluation_top_box span i {
		display: block;
		float: left;
		height: 10px;
		overflow: hidden;
		background: -webkit-linear-gradient(top, #f00, #ca0000);
		background: -moz-linear-gradient(top, #f00, #ca0000);
		background: -ms-linear-gradient(top, #f00, #ca0000);
		background: -o-linear-gradient(top, #f00, #ca0000);
		background: linear-gradient(top, #f00, #ca0000);
	}

	.evaluation_top ul {
		width: 100%;
		background: #f9f9f9;
		border-top: 1px solid #ddd;
		overflow: hidden
	}

	.evaluation_top ul li {
		width: 33.3%;
		float: left;
		height: 40px;
		font-size: 23rpx;
		text-align: center;
		line-height: 20px;
	}

	.evaluation_top ul li span {
		height: 40px;
		text-align: center;
		display: block;
		border-right: 1px solid #ddd;
	}

	.evaluation_top ul .this span {
		background: #fff;
		color: #bc0000;
	}

	.evaluation_top ul li span i {
		font-size: 21rpx;
	}

	/*关注商品*/
	.attention_title {
		width: 100%;
		margin-top: 10px;
		height: 30px;
		background: #333;
		color: #fff;
		text-indent: 1em;
		line-height: 30px;
		font-size: 23rpx;
		border-radius: 5px;
	}

	.attention_list {
		width: 100%;
	}

	.attention_list a {
		display: block;
		overflow: hidden;
	}

	.attention_list dl {
		box-shadow: 1px 1px 1px #999;
		/* background:#fff url("./static/images/right_b.gif") no-repeat 97% center; */
		padding: 10px;
		overflow: hidden;
		margin-top: 10px;
		border-radius: 5px;
	}

	.attention_list dl dt {
		width: 60px;
		height: 60px;
		overflow: hidden;
		margin-right: 10px;
		float: left
	}

	.attention_list dl dd {
		width: 70%;
		float: left;
		height: 10px;
	}

	.attention_list dl dd h4 {
		font-size: 23rpx;
		font-weight: lighter;
		color: #333;
		line-height: 15px;
	}

	.attention_list dl dd span {
		display: block;
		font-size: 24rpx;
		color: #e70800;
		line-height: 20px;
		font-weight: bold;
		margin-top: 10px;
		float: left;
	}

	/*我的消息*/
	.message_list a {
		display: block;
		overflow: hidden;
	}

	.message_list dl {
		border: 1px solid #fff;
		background: #f3ffd7;
		margin-top: 10px;
		padding: 5px 10px;
		border-radius: 5px;
	}

	.message_list dl dt {
		font-size: 23rpx;
		line-height: 20px;
		border-bottom: 1px solid #fff;
	}

	.message_list dl dt span {
		float: right;
	}

	.message_list dl dt b {
		float: left;
	}

	.message_list dl dd {
		clear: both;
		color: #666;
		/* background:url("./static/images/details_right_b.gif") no-repeat right center; */
		padding-top: 10px;
		height: 48px;
	}

	.message_list dl dd.unfold {
		/* background:url("./static/images/details_right_t.gif") no-repeat right center; */
		height: auto;
	}

	.message_list dl dd p {
		padding-right: 20px;
		font-size: 23rpx;
		line-height: 18px;
	}

	/*我的优惠卷*/
	.sale_red {
		border: 2px dotted #e73c3c;
	}

	.sale_red section {
		background: #e73c3c;
	}

	.sale_green {
		border: 2px dotted #52bd35;
	}

	.sale_green section {
		background: #52bd35;
	}

	.sale_yellow {
		border: 2px dotted #ff9d02;
	}

	.sale_yellow section {
		background: #ff9d02;
	}

	.sale_gray {
		border: 2px dotted #CCC;
	}

	.sale_gray section {
		background: #CCC;
	}

	.sale_list {
		margin-top: 10px;
		height: 120px;
		position: relative;
	}

	.sale_list section {
		overflow: hidden;
		height: 120px;
		width: 100%;
	}

	.sale_left {
		width: 35px;
		float: left;
		font-size: 28rpx;
		color: #FFF;
		font-weight: bold;
		padding-left: 10px;
		text-align: center;
		padding-top: 20px;
	}

	.sale_box {
		float: left;
		background: #fff;
		height: 110px;
		margin-top: 5px;
		width: 200px;
		margin-left: 5px;
	}

	.sale_right {
		float: left;
		color: #fff;
		font-size: 23rpx;
		font-weight: bold;
		height: 120px;
		line-height: 120px;
		text-align: center;
		margin-left: 5px;
	}

	.sale_box h3 {
		text-align: center;
		font-size: 30px;
		color: #fe0202;
		margin-top: 15px;
		font-weight: lighter
	}

	.sale_box h3 sub {
		font-size: 21rpx;
		position: relative;
		top: -5px;
		left: 10px;
	}

	.sale_box span {
		line-height: 20px;
		color: #000;
		font-size: 23rpx;
		text-align: center;
		display: block;
		width: 100%;
		margin-top: 10px;
	}

	.sale_box p {
		width: 100%;
		line-height: 20px;
		margin-top: 3px;
		font-size: 21rpx;
		color: #999;
		text-align: center
	}

	/*错误页面*/
	.error_page {
		width: 100%;
		border-top: 2px solid #8b8b8b;
		position: relative;
		margin-top: 40px;
	}

	.error_page .error_img {
		width: 128px;
		overflow: hidden;
		display: block;
		margin-left: auto;
		margin-right: auto;
		margin-top: -10px;
	}

	.error_page p {
		width: 270px;
		margin-left: auto;
		margin-right: auto;
		line-height: 40px;
		margin-top: 20px;
		font-size: 23rpx;
		text-align: center;
	}

	.error_page p strong {
		font-size: 28rpx;
		color: #c20000;
		padding-left: 10px;
		padding-right: 10px;
	}

	/*成功页面*/
	.error_page1 {
		width: 100%;
		position: relative;
		margin-top: 40px;
	}

	.error_page1 .error_img {
		width: 85px;
		overflow: hidden;
		display: block;
		margin-left: auto;
		margin-right: auto;
		margin-top: -10px;
		text-align: center;
	}

	.error_page1 p {
		width: 270px;
		margin-left: auto;
		margin-right: auto;
		line-height: 40px;
		margin-top: 20px;
		font-size: 23rpx;
		text-align: center;
	}

	.error_page1 p strong {
		font-size: 28rpx;
		color: #c20000;
		padding-left: 10px;
		padding-right: 10px;
	}

	/***选择支付方式***/
	.pd5 {
		padding: 5px;
	}

	.payfor {
		width: 100%;
		float: left;
	}

	.payfor .banktitle {
		background-color: #fff;
		border-bottom: 1px solid #e0e0e0;
		font-size: 23rpx;
		line-height: 40px;
		padding: 0 10px;
	}

	.pay-tip {
		color: #686868;
	}

	.pay-list-item .pay-icon-wx {
		/* background-image: url("./static/images/weixin.png"); */
		float: left;
		height: 35px;
		margin: 10px 10px 0 0;
		width: 35px;
	}

	.pay-list-item .pay-icon-zfb {
		/* background-image: url("./static/images/alipay.png"); */
		float: left;
		height: 35px;
		margin: 10px 10px 0 0;
		width: 35px;
	}

	.pay-list-item .pay-icon-hdfk {
		/* background-image: url("./static/images/payafter.jpg"); */
		float: left;
		height: 35px;
		margin: 10px 10px 0 0;
		width: 35px;
	}

	.bankone {
		padding: 10px 20px;
	}

	.bankcar {}

	.bankcar .list {
		padding-left: 10px;
	}

	.bankcar .list,
	.bankcar .list-item {
		border-bottom: 1px solid #e0e0e0;
		overflow: hidden;
	}

	.pay-list-item {
		height: 55px;
		line-height: 55px;
	}

	.list-link::before {
		content: "";
		position: absolute;
	}

	.list-link::before {
		/* background: rgba(0, 0, 0, 0) url("./static/images/right_arrow.png") no-repeat scroll 0 0 / 9px 15px; */
		height: 15px;
		margin-top: 5px;
		right: 10px;
		top: 50%;
		width: 9px;
	}

	.pay-list-item .title-main {
		color: #232626;
		font-size: 23rpx;
		line-height: 15px;
		margin-top: 10px;
		float: left;
	}

	.pay-list-item .title-vice {
		color: #848689;
		font-size: 21rpx;
		margin: 0 0 0 45px;
		float: left;
	}

	/* .paybot h2 h3 {color:#848689; } */
	.paybot a {
		color: #6a77b6;
		font: normal;
	}

	.bankone .banksp {
		height: 30px;
		line-height: 30px;
		margin: 10px 10px;
	}

	.payment {
		width: 100%;
		margin-top: 10rpx;
		padding-left: 10rpx;
		overflow: hidden;
		color: #222;
	}

	.payment h1 {
		font-size: 24rpx;
		height: 50px;
		line-height: 50px;
	}

	.pay_table {
		width: 100%;
		margin-bottom: 20px;
		background-color: #fafafa;
	}

	.pay_table td {
		padding: 10px 5px;
		border: 1px dashed #eeeeee;
	}

	.pay_table td img {
		vertical-align: middle;
	}

	.pay_table .pay_style {
		width: 100px;
		padding: 5px 15px;
		border: 2px solid #e4e4e4;
		text-align: center;
		background-color: #FFF;
	}

	.pay_table .this {
		border: 2px solid #F00;
		color: #F00;
	}

	.pay_btn {
		width: 100%;
		margin-top: 10px;
		float: left;
	}

	.pay_btn input {
		background-color: #D90600;
		width: 100%;
		float: left;
		text-align: center;
		height: 40px;
		line-height: 40px;
		font-size: 24rpx;
		color: #FFF;
	}

	.pay_detail {
		width: 100%;
		float: left;
		padding: 5px 0px;
	}

	.pay_detail dl {
		width: 100%;
		float: left;
	}

	.pay_detail dl dt {
		width: 25%;
		float: left;
	}

	.pay_detail dl dd {
		width: 75%;
		float: left;
	}

	.pay_detail dl dd li {
		line-height: 30px;
	}

	/***活动页***/
	.activity {
		background-color: #fafafa;
	}

	.activity ul {
		width: 100%;
		border: 1px solid #e4e4e4;
		margin-bottom: 5px;
		background-color: #FFF;
	}

	.activity li {
		font-size: 23rpx;
	}

	.activity .li_center {
		text-align: center;
		font-size: 24rpx;
		line-height: 35px;
		border-bottom: 1px dashed #e4e4e4;
	}

	.activity .li_time {
		color: #999;
		line-height: 35px;
		padding-left: 5px;
	}

	.activity .li_content {
		line-height: 35px;
		padding-left: 5px;
	}

	.activity li img {
		width: 100%;
		height: auto;
	}

	/***分类***/
	.category {
		width: 100%;
		float: left;
		margin-top: 45px;
	}

	.category_center {
		width: 97%;
		margin: 0 auto;
		clear: both;
	}

	.category_center_search {
		height: 32px;
		width: 100%;
		border: 1px solid #ccc;
		border-radius: 3px;
		float: left;
		background: #FFF;
	}

	.category_center_search_input {
		height: 30px;
		line-height: 30px;
		width: 80%;
		border: none;
		float: left;
		padding-left: 3%;
	}

	.category_center_bottom {
		height: 32px;
		width: 15%;
		background: #ebebeb;
		float: right;
		text-align: center;
	}

	.category_center_bottom img {
		margin-top: 6px;
	}

	.category_center_list {
		width: 100%;
		margin-top: 15px;
		float: left;
		margin-bottom: 15px;
	}

	.category_center_list ul {
		width: 100%;
		border-left: 1px solid #ccc;
		border-bottom: 1px solid #ccc;
		float: left;
	}

	.category_center_list li {
		width: 100%;
		height: 42px;
		border-right: 1px solid #ccc;
		border-top: 1px solid #ccc;
		line-height: 42px;
		float: left;
		/* background:url("./static/images/search_list_bg.png") #fff 8px center no-repeat; */
		font-size: 22rpx;
		position: relative;
	}

	.category_center_list ul li.this {}

	.category_center_list_left {
		float: left;
		line-height: 42px;
		color: #6e6e6e;
		text-indent: 23px;
	}

	.category_center_list li a {
		color: #6e6e6e;
	}

	.category_center_list_right {
		width: 100%;
		float: left;
		height: auto;
		text-align: center;
		background: #eaeaea;
		border-top: 1px solid #ccc;
		border-right: 1px solid #ccc;
		font-size: 21rpx;
		color: #6e6e6e
	}

	.category_center_list_right span {
		color: #6e6e6e;
		height: 15px;
		line-height: 15px;
		overflow: hidden;
		padding: 13px 0;
		width: 33%;
		text-align: center;
		float: left;
		/* background:url("./static/images/line.png") no-repeat right; */
	}

	.category_center_list_right span a {
		color: #6e6e6e
	}

	.category_center_list i {
		height: 9px;
		width: 13px;
		float: right;
		position: absolute;
		right: 10px;
	}

	/***三级分类***/
	.category_xx {
		width: 100%;
		float: left;
		margin-top: 40px;
	}

	.category_xx_center {
		width: 97%;
		margin: 0 auto;
		clear: both;
	}

	.category_xx_center_list {
		width: 100%;
		margin-top: 15px;
		float: left;
		margin-bottom: 10px;
	}

	.category_xx_center_list ul {
		width: 100%;
		border-left: 1px solid #ccc;
		border-bottom: 1px solid #ccc;
		float: left;
		background-color: #fff;
	}

	.category_xx_center_list li {
		width: 100%;
		height: 42px;
		border-right: 1px solid #ccc;
		border-top: 1px solid #ccc;
		line-height: 42px;
		float: left;
		font-size: 22rpx;
		position: relative;
	}

	.category_xx_center_list li a {
		width: 100%;
		height: 42px;
		float: left;
	}

	.category_xx_center_list_left {
		float: left;
		line-height: 42px;
		color: #6e6e6e;
		text-indent: 5px;
	}

	.category_xx_center_list li i {
		height: 9px;
		width: 13px;
		float: right;
		position: absolute;
		right: 10px;
	}

	/***加载***/
	.loading {
		width: 100%;
		margin-top: 10px;
	}

	.loading ul li {
		width: 100%;
		display: block;
		height: 35px;
		line-height: 35px;
		color: #999;
		text-align: center;
	}

	.notice {
		width: 93%;
		border: 1px solid #FFDEAD;
		background-color: #FDF5E6;
		padding: 5px;
		color: #F27900;
		line-height: 24px;
	}

	/***支付结果***/
	.pay_finish {
		width: 100%;
		background-color: #FFF;
	}

	.pay_finish ul {
		padding: 20px 10px;
		padding-bottom: 0px;
	}

	.pay_finish ul li {
		line-height: 35px;
		border-bottom: 1px dashed #e4e4e4;
	}

	.pay_finish .gray {
		color: #999;
	}

	.pay_finish_btn {
		padding: 10px;
		padding-bottom: 20px;
		background-color: #FFF;
		overflow: hidden;
	}

	.pay_finish_btn .car_btn {
		padding: 5px 10px;
		background-color: #F00;
		color: #FFF;
		margin-right: 10px;
		float: right;
		cursor: pointer;
	}

	/***评价页***/
	.evaluate {
		width: 100%;
		background-color: #FFF;
	}

	.evaluate .hd {
		padding: 5px;
		overflow: hidden;
	}

	.evaluate .hd b {
		width: 20%;
		height: 60px;
		float: left;
	}

	.evaluate .hd b img {
		width: 60px;
		height: 60px;
	}

	.evaluate .hd span {
		width: 75%;
		height: 50px;
		overflow: hidden;
		float: left;
		padding-left: 10px;
		line-height: 24px;
	}

	.describe {
		padding: 10px 5px;
		overflow: hidden;
	}

	.describe ul {
		width: 100%;
		overflow: hidden;
	}

	.describe ul li {
		width: 100%;
		height: 40px;
		line-height: 40px;
		font-size: 21rpx;
		margin-bottom: 5px;
	}

	.describe ul li span {
		display: block;
		float: left;
	}

	.describe ul li .fl {
		width: 20%;
	}

	.describe ul li .fr {
		width: 80%;
	}

	.describe ul li .fr a {
		width: 40px;
		height: 40px;
		float: left;
		display: block;
		/* background:url("./static/images/five-star.png") no-repeat 0px -40px; */
	}

	.describe ul li .fr a.this {
		/* background:url("./static/images/five-star.png") no-repeat 0px 0px; */
	}

	.describe ul li .fr textarea {
		width: 210px;
		height: 50px;
		resize: none;
		border: 1px solid #e4e4e4;
		box-shadow: inset 0px 1px 3px #e4e4e4;
		font-size: 21rpx;
		font-family: "微软雅黑", Arial, Helvetica, sans-serif;
	}

	.describe ul li .fr_dl {
		width: 100%;
		float: left;
	}

	.describe ul li .fr_dl dt,
	.describe ul li .fr_dl dd {
		float: left;
		display: block;
	}

	.describe ul li .fr_dl dd {
		margin-right: 10px;
		padding-left: 20px;
	}

	.describe ul li .fr_dl .good {
		/* background:url("./static/images/good.png") no-repeat left center; */
	}

	.describe ul li .fr_dl .mid {
		/* background:url("./static/images/mid.png") no-repeat left center; */
	}

	.describe ul li .fr_dl .bad {
		/* background:url("./static/images/bad.png") no-repeat left center; */
	}

	.submit {
		padding: 20px 10px;
		overflow: hidden;
	}

	.submit input {
		width: 100%;
		height: 35px;
		line-height: 30px;
		color: #FFF;
		background-color: #F30;
		border-radius: 4px;
		float: left;
		border: none;
		-webkit-appearance: none;
		cursor: pointer;
	}

	/***用户中心收藏***/
	.attent_dl dd {
		position: relative;
	}

	.attent_dl dd i {
		width: 25px;
		height: 25px;
		position: absolute;
		right: -25px;
		top: 15px;
	}

	/***确认订单页***/
	.enter_order {
		width: 100%;
		background-color: #f8f8f8;
		overflow: hidden;
	}

	.enter_od_box {
		padding: 5px;
		background-color: #fff;
		margin-top: 10px;
		overflow: hidden;
	}

	.enter_od_box h1 {
		height: 40px;
		line-height: 40px;
		font-weight: normal;
		border-bottom: 1px solid #eee;
		font-size: 23rpx;
	}

	.enter_od_ul li {
		width: 100%;
		float: left;
		line-height: 30px;
		padding: 5px 0px;
		border-bottom: 1px solid #eee;
		overflow: hidden;
		/* background:url("./static/images/details_right.gif") no-repeat right center; */
		font-size: 23rpx;
	}

	.enter_od_ul li.ta {
		background: none;
	}

	.enter_od_ul li.ta span {
		width: 30%;
		float: left;
		padding-right: 5px;
	}

	.enter_od_ul li.od_goods {
		background: none;
	}

	.enter_od_ul li .fl {
		float: left;
	}

	.enter_od_ul li .fr {
		float: right;
		padding-right: 15px;
		color: #999;
		font-size: 21rpx;
	}

	.enter_od_ul li dl {
		width: 100%;
		float: left;
	}

	.enter_od_ul li dl dt {
		width: 20%;
		float: left;
	}

	.enter_od_ul li dl dt img {
		width: 100%;
		margin-right: 10px;
	}

	.enter_od_ul li dl dd.midd {
		width: 55%;
		float: left;
		line-height: 24px;
		font-size: 21rpx;
	}

	.enter_od_ul li dl dd.midd span {
		width: 100%;
		float: left;
	}

	.enter_od_ul li dl dd.midd .name {
		height: 48px;
		overflow: hidden;
	}

	.enter_od_ul li dl dd.midd .color {
		color: #999;
	}

	.enter_od_ul li dl dd.price {
		width: 20%;
		float: right;
	}

	.enter_od_ul li dl dd.price span {
		width: 100%;
		float: right;
		text-align: right;
		font-size: 21rpx;
		line-height: 24px;
	}

	.enter_od_ul li textarea {
		width: 60%;
		border: 1px solid #ddd;
		height: 40px;
		box-shadow: 0px 0px 3px #ddd inset;
		resize: none;
		font-size: 21rpx;
		color: #666;
		line-height: 24px;
		padding: 0px 2px;
	}

	/***订单列表页***/
	.order_page {
		width: 100%;
		background-color: #f8f8f8;
		overflow: hidden;
		padding: 45px 0px;
	}

	.order_page_box {
		width: 100%;
		background-color: #fff;
		margin-top: 5px;
		overflow: hidden;
		border-top: 1px solid #ddd;
		border-bottom: 1px solid #ddd;
	}

	.order_page_box h1 {
		height: 35px;
		line-height: 35px;
		padding: 0px 5px;
		font-weight: normal;
		font-size: 23rpx;
		border-bottom: 1px solid #eee;
	}

	.order_page_box h1 .fl {
		float: left;
	}

	.order_page_box h1 .fr {
		float: right;
		color: #cf0000;
	}

	.order_goods {
		border-bottom: 1px solid #eee;
		padding: 10px 5px;
		overflow: hidden;
	}

	.order_goods .dt {
		width: 50px;
		height: 50px;
		border: 1px solid #eee;
		float: left;
		margin-right: 5px;
	}

	.order_goods .dd {
		height: 48px;
		line-height: 24px;
		overflow: hidden;
		font-size: 21rpx;
	}

	.order_goods .dd a {
		color: #666;
	}

	.order_goods ul {
		float: right;
		display: block;
		padding-left: 10px;
	}

	.order_goods ul li {
		text-align: right;
		font-size: 21rpx;
		line-height: 24px;
		color: #666;
	}

	.order_goods ul li b {
		font-size: 23rpx;
		color: #333;
		font-family: Arial;
	}

	.order_total {
		padding: 0px 5px;
		overflow: hidden;
		border-bottom: 1px solid #eee;
		text-align: right;
		font-size: 21rpx;
		line-height: 30px;
		color: #666;
	}

	.order_total b {
		font-size: 23rpx;
		padding-left: 5px;
		font-family: Arial;
		color: #333;
	}

	.order_box_bt {
		padding: 10px 5px;
		overflow: hidden;
		line-height: 24px;
		text-align: right;
	}

	.order_box_bt a {
		border: 1px solid #eee;
		padding: 5px 10px;
		margin-left: 10px;
		font-size: 23rpx;
	}

	.order_box_bt a.bg_orange {
		background-color: #cf0000;
		color: #fff;
		border-color: #c70000;
	}

	.order_goods_much {
		padding: 10px 0px 10px 6px;
		overflow: hidden;
		border-bottom: 1px solid #eee;
	}

	.order_goods_much ul li {
		width: 50px;
		height: 50px;
		border: 1px solid #eee;
		margin-right: 6px;
		display: block;
		float: left;
	}

	.order_goods_much ul li.arrow {
		width: 13px;
		border: 0px;
		padding-top: 12px;
	}

	.order_hd {
		width: 100%;
		height: 40px;
		line-height: 40px;
		background-color: #fff;
		border-top: 1px solid #eee;
		border-bottom: 1px solid #eee;
	}

	.order_hd ul li {
		width: 20%;
		float: left;
		text-align: center;
		font-size: 23rpx;
	}

	.order_hd ul li a {
		width: 100%;
		float: left;
	}

	.order_hd ul li.this a {
		border-bottom: 1px solid #f94d4d;
		color: #f94d4d;
	}

	/***订单详细***/
	.trade_table {
		width: 100%;
		background-color: #310000;
		color: #fff;
	}

	.trade_table td {
		padding: 5px;
	}

	.trade_table .td_left {
		width: 40px;
		text-align: right;
		vertical-align: top;
	}

	.trade_table .td_left img {
		margin-top: 10px;
	}

	.trade_table .td_mid {
		width: 200px;
	}

	.trade_table .td_mid .gray {
		color: #999;
	}

	.trade_table td ul li {
		width: 100%;
		float: left;
		line-height: 24px;
		display: block;
		font-size: 21rpx;
	}

	.trade_table td ul li.big {
		font-size: 23rpx;
		line-height: 30px;
	}

	.fl {
		float: left;
	}

	.fr {
		float: right;
	}

	.trade_table .td_mid .green {
		white-space: nowrap;
		overflow: hidden;
		color: #25ae5f;
	}

	.cnee_table {
		background-color: #fff;
		color: #333;
		border-bottom: 1px solid #ddd;
	}

	.order_goods .dd_01 {
		height: auto;
	}

	.order_goods .dd .name {
		line-height: 26px;
		white-space: nowrap;
		overflow: hidden;
	}

	.order_goods .dd .size {
		color: #999;
		line-height: 20px;
	}

	.fre_table {
		width: 100%;
	}

	.fre_table td {
		line-height: 20px;
		padding: 5px;
		font-size: 21rpx;
	}

	.fre_table td .big {
		font-size: 23rpx;
	}

	.order_log {
		width: 100%;
		overflow: hidden;
		margin-top: 10px;
	}

	.order_log h1 {
		height: 30px;
		line-height: 30px;
		border-bottom: 1px solid #ddd;
		font-size: 23rpx;
		font-weight: normal;
		color: #000;
	}

	.order_log h1 span {
		padding: 0px 10px;
		float: left;
		border-bottom: 1px solid #e50000;
	}

	.order_log_list {
		width: 320px;
		margin: 0px auto;
		padding-bottom: 10px;
		overflow: hidden;
	}

	.order_log_list dl {
		width: 100%;
		float: left;
		padding: 8px 0px;
		border-bottom: 1px dashed #ddd;
	}

	.order_log_list dl dt {
		width: 20px;
		display: block;
		float: left;
		padding: 5px 10px;
	}

	.order_log_list dl dd {
		width: 270px;
		display: block;
		float: left;
		font-size: 21rpx;
		padding-right: 10px;
	}

	.order_log_list dl dd ul li {
		line-height: 22px;
	}

	.order_log_list dl dd ul li.gray {
		color: #999;
		line-height: 30px;
	}

	/***订单详细底部***/
	.order_foot {
		width: 100%;
		position: fixed;
		bottom: 0px;
		padding: 10px 0px;
		background-color: #414141;
		overflow: hidden;
	}

	.order_foot a {
		padding: 5px 15px;
		font-size: 23rpx;
		float: right;
		margin-right: 5px;
		background-color: #fff;
		border: 1px solid #fff;
	}

	.order_foot a.bg_red {
		background-color: #FF3C3C;
		color: #fff;
		padding: 5px 30px;
	}

	/***手机web-2015版开始----------------------------------------------------***/
	/***首页-开始***/
	.phone_main {
		max-width: 640px;
		min-width: 320px;
		margin: 0px auto;
		overflow: hidden;
	}

	.phone_top {
		width: 100%;
		position: relative;
	}

	.phone_top .close {
		width: 30px;
		height: 30px;
		position: absolute;
		top: 5px;
		right: 5px;
		z-index: 1;
	}

	.logo_line {
		padding: 5px 10px;
		height: 30px;
		line-height: 30px;
		overflow: hidden;
	}

	.logo_line img {
		max-width: 150px;
		float: left;
	}

	.logo_line span {
		float: right;
	}

	.logo_line .log_after {
		width: 90px;
		white-space: nowrap;
		float: right;
		font-size: 21rpx;
		text-align: right;
		text-overflow: ellipsis;
		-moz-text-overflow: ellipsis;
		overflow: hidden;
	}

	.logo_line .log {
		border: 1px solid #eee;
		text-align: center;
		font-size: 21rpx;
	}

	.search_line_old {
		text-align: center;
		float: none;
		right: 0px;
		top: 0px;
		width: 100%;
		height: 45px;
		position: fixed;
		background-color: #1AAACD;
		z-index: 1
	}

	/* .search_line span {border:1px solid #eee; display:block; background-color:#fff; padding-left:40px; overflow:hidden;} */
	.search_line input {
		width: 100%;
		height: 100%;
		border: 0px;
		font-size: 27rpx;
		margin-buttom: 30rpx;
		padding-left: 10px;
		color: #666;
		border-radius: 50px;
		border: 0px;
		background-color: #fff
	}

	.search_line b {
		width: 35px;
		height: 35px;
		position: absolute;
		z-index: 1;
		top: 10px;
	}

	/*幻灯*/
	.focusBox {
		position: relative;
		width: 100%;
		height: 150px;
		overflow: hidden;
	}

	.focusBox .pic img {
		width: 100%;
		height: 150px;
		display: block;
	}

	.focusBox .hd {
		overflow: hidden;
		zoom: 1;
		position: absolute;
		bottom: 5px;
		left: 50%;
		margin-left: -50px;
		z-index: 3
	}

	.focusBox .hd li {
		float: left;
		line-height: 15px;
		text-align: center;
		font-size: 21rpx;
		width: 25px;
		height: 5px;
		cursor: pointer;
		overflow: hidden;
		background: #919191;
		margin-left: 4px;
		filter: alpha(opacity=80);
		opacity: .8;
		-webkit-transition: All .5s ease;
		-moz-transition: All .5s ease;
		-o-transition: All .5s ease;
		text-indent: 999px;
	}

	.focusBox .hd .on {
		background: #fff;
		filter: alpha(opacity=100);
		opacity: 1;
	}

	/*导航*/
	.nav {
		width: 100%;
		padding: 5px 0px;
		overflow: hidden;
	}

	.nav ul li {
		width: 25%;
		float: left;
		display: block;
		text-align: center;
		font-size: 23rpx;
		margin: 5px 0px;
	}

	.nav ul li a {
		color: #666;
	}

	.nav ul li a img {
		width: 3em;
		height: 3em;
		display: block;
		margin: 0px auto;
	}

	.nav ul li a p {
		font-size: 23rpx;
	}

	/*推荐*/
	.phone_floor {
		width: 100%;
		overflow: hidden;
		border-top: 1px solid #eee;
		border-bottom: 1px solid #eee;
		background-color: #fff;
		margin: 10px 0px 55px 0px;
	}

	.phone_floor h1 {
		padding: 10px;
		font-weight: normal;
		overflow: hidden;
		font-size: 23rpx;
	}

	.phone_floor h1 .fl {
		border-left: 3px solid #e22c37;
		padding-left: 8px;
		height: 12px;
		line-height: 12px;
		float: left;
		margin-top: 3px;
	}

	.phone_floor h1 .fr {
		float: right;
	}

	.phone_floor h1 .fr a {
		color: #666;
		font-size: 21rpx;
	}

	.recom {
		width: 100%;
		overflow: hidden;
		position: relative;
		background: #f5f5f5;
		padding-bottom: 10px;
	}

	.recom .piclist {
		overflow: hidden;
		width: 100%;
		zoom: 1;
		margin: 1px;
		border-bottom: 1px solid #D0D0D0;
	}

	.recom .ohbox {
		overflow: hidden;
		position: relative;
		min-width: 10px;
		left: 0px
	}

	.recom .list {
		overflow: hidden;
		zoom: 1;
		left: 50%;
		margin-left: -50px;
		position: absolute;
	}

	.recom .list li {
		width: 8px;
		height: 8px;
		border: 1px solid #ddd;
		background-color: #fff;
		float: left;
		display: inline;
		width: 15px;
		height: 15px;
		cursor: pointer;
		margin-left: 10px;
		line-height: 10000px;
		overflow: hidden;
	}

	.recom .list li.on {
		border-color: #e22c37;
		background-color: #e22c37;
	}

	.recom ul {
		width: 100%;
		overflow: hidden;
	}

	.recom ul li {
		width: 50%;
		height: 0;
		padding-bottom: 68%;
		display: block;
		margin: -1px;
		float: left;
		overflow: hidden;
		background: #fff;
		border: 1px solid #F2F2F2;
	}

	.recom ul a li {
		margin: 2px;
		overflow: hidden;
		float: left;
		position: relative;
		overflow: hidden;
	}

	.recom ul a li img {
		max-width: 130px;
	}

	.recom ul a li span {
		width: 100%;
		height: 30px;
		line-height: 30px;
		position: absolute;
		bottom: 0px;
		left: 0px;
		white-space: nowrap;
	}

	.recom ul a li span.bg {
		background-color: #000;
		filter: alpha(opacity=30);
		-moz-opacity: 0.3;
		-khtml-opacity: 0.3;
		opacity: 0.3;
	}

	.re_name {
		color: #dododo;
		font-size: 22rpx;
		z-index: 2;
	}

	/*商城活动*/
	.phone_floor .phone_activity h1 {
		border-bottom: 1px solid #eee;
	}

	.phone_activity {
		width: 100%;
		overflow: hidden;
	}

	.phone_activity ul li {
		width: 33.3%;
		float: left;
		display: block;
	}

	.phone_activity ul li a {
		padding: 10px;
		overflow: hidden;
		display: block;
		border-right: 1px solid #eee;
	}

	.phone_activity ul li:last-of-type a {
		border-right: 0px;
	}

	.phone_activity ul li a img {
		width: 100%;
	}

	.phone_activity ul li p {
		width: 100%;
		overflow: hidden;
		text-align: center;
	}

	.phone_activity ul li p.price {
		font-size: 21rpx;
		color: #666;
		height: 30px;
		line-height: 30px;
	}

	.phone_activity ul li p.price b {
		font-size: 26rpx;
		color: #e22c37;
		font-family: Arial, Helvetica, sans-serif;
	}

	.phone_activity ul li p.dis {
		background-color: #d8505c;
		color: #fff;
		font-size: 23rpx;
	}

	/*超值购*/
	.phone_floor .phone_group h1 {
		border-bottom: 1px solid #eee;
	}

	.phone_group {
		width: 100%;
		overflow: hidden;
	}

	.phone_group ul li {
		width: 50%;
		display: block;
		float: left;
	}

	.phone_group ul li a {
		padding: 5px;
		overflow: hidden;
		float: left;
		border-right: 1px solid #eee;
		border-bottom: 1px solid #eee;
	}

	.phone_group ul li:nth-child(2n) a {
		border-right-color: #fff;
	}

	.phone_group ul li:nth-child(3) a {
		border-bottom: 0px;
	}

	.phone_group ul li:nth-child(4) a {
		border-bottom: 0px;
	}

	.phone_group ul li a img {
		width: 100%;
	}

	.phone_group ul li p {
		width: 100%;
		overflow: hidden;
		height: 30px;
		line-height: 30px;
	}

	.phone_group ul li p.g_name {
		font-size: 21rpx;
		color: #666;
		height: 24px;
		line-height: 24px;
	}

	.phone_group ul li p.price b {
		font-size: 24rpx;
		color: #e22c37;
	}

	.phone_group ul li p.price s {
		color: #999;
		font-size: 21rpx;
	}

	/*品牌街*/
	.phone_brand {
		margin: 0px 10px 5px 10px;
		overflow: hidden;
		padding-top: 1px;
		padding-left: 1px;
	}

	.phone_brand ul li {
		width: 50%;
		display: block;
		float: left;
	}

	.phone_brand ul li a {
		padding: 1px;
		display: block;
		border: 1px solid #eee;
		margin-top: -1px;
		margin-left: -1px;
	}

	.phone_brand ul li a img {
		width: 142px;
		height: 66px;
		margin: 0px auto;
		display: block;
	}

	/*精选市场*/
	.phone_floor .phone_menu h1 {
		border-bottom: 1px solid #eee;
	}

	.phone_menu {
		overflow: hidden;
		padding-bottom: 2%;
	}

	.phone_menu ul li {
		width: 47%;
		margin-left: 2%;
		float: left;
		display: block;
		margin-top: 2%;
	}

	.phone_menu ul li a {
		width: 100%;
		background-color: #f1f1f1;
		float: left;
		padding: 5px 0px;
	}

	.phone_menu ul li .fl {
		max-width: 65px;
		display: block;
		float: left;
		margin-left: 10px;
		line-height: 50px;
		font-size: 24rpx;
		white-space: nowrap;
	}

	.phone_menu ul li .fr {
		display: block;
		float: right;
		margin-right: 10px;
	}

	.phone_menu ul li .fr img {
		width: 50px;
		height: 50px;
		border-radius: 50%;
	}

	/*浏览历史*/
	.browse {
		width: 100%;
		overflow: hidden;
	}

	.browse ul li {
		width: 33.3%;
		float: left;
		display: block;
		overflow: hidden;
	}

	.browse ul li a {
		padding: 5px;
		display: block;
		overflow: hidden;
	}

	.browse ul li a img {
		width: 100%;
	}

	.browse ul li a p {
		height: 28px;
		font-size: 21rpx;
		white-space: nowrap;
		line-height: 28px;
		color: #666;
		text-overflow: ellipsis;
		-moz-text-overflow: ellipsis;
		overflow: hidden;
	}

	.browse ul li a p.price {
		font-size: 24rpx;
		color: #e22c37;
		float: left;
	}

	/*底部*/
	.phone_foot {
		width: 100%;
		border-top: 1px solid #eee;
		overflow: hidden;
		padding-bottom: 30px;
		background-color: #fff;
	}

	.foot_top {
		padding: 0px 10px;
		line-height: 35px;
		overflow: hidden;
		font-size: 23rpx;
		border-bottom: 1px dashed #ddd;
	}

	.foot_top .fl {
		float: left;
	}

	.foot_top .fl a {
		font-size: 23rpx;
		color: #666;
	}

	.foot_top .fl b {
		color: #ccc;
		float: left;
	}

	.foot_top .fl span {
		text-align: center;
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
		-moz-text-overflow: ellipsis;
		float: left;
	}

	.foot_top .fr {
		float: right;
	}

	.foot_top .fr a {
		color: #000;
	}

	.foot_nav {
		width: 100%;
		overflow: hidden;
		padding: 10px 0px;
		font-size: 23rpx;
		text-align: center;
	}

	.foot_nav a {
		color: #000;
		padding: 0px 10px;
	}

	.foot_nav a.this {
		color: #e22c37;
	}

	/*悬浮层*/
	.layer {
		width: 100%;
		height: 50px;
		position: fixed;
		right: 0px;
		bottom: 0px;
		z-index: 4;
	}

	.layer_menu {
		width: 50px;
		height: 50px;
		position: absolute;
		bottom: 0px;
		right: 0px;
		z-index: 5;
	}

	.layer_menu .black_bg {
		width: 40px;
		height: 40px;
		padding: 5px;
		float: left;
		margin-right: 10px;
		background-color: #000000;
		filter: alpha(opacity=50);
		-moz-opacity: 0.5;
		-khtml-opacity: 0.5;
		opacity: 0.5;
	}

	.layer_menu img {
		position: absolute;
		top: 7px;
		right: 7px;
		z-index: 1;
	}

	.layer_show {
		width: 100%;
		height: 50px;
		position: absolute;
		right: 0px;
		bottom: 0px;
	}

	.show_bg {
		width: 100%;
		height: 56px;
		position: absolute;
		right: 0px;
		bottom: 0px;
		background-color: #000000;
		z-index: 4;
	}

	.layer_show ul {
		width: 100%;
		height: 40px;
		padding: 5px 0px;
		position: absolute;
		left: 0px;
		bottom: 0px;
		z-index: 5;
	}

	.layer_show ul li {
		width: 25%;
		float: left;
		display: block;
		text-align: center;
	}

	.layer_show ul li a {
		width: 100%;
		color: #ffffff;
		float: left;
	}

	.layer_show ul li a p {
		font-size: 21rpx;
	}

	/*推荐0元试用*/
	.zero_time {
		float: left;
		margin-left: 10px;
	}

	.zero_time b {
		padding: 3px;
		background-color: #333;
		color: #fff;
		font-size: 23rpx;
		margin-right: 3px;
		border-radius: 4px;
	}

	.zero_time strong {
		margin-right: 3px;
	}

	.zero {
		border-top: 1px solid #eee;
		padding: 5px;
		overflow: hidden;
	}

	.zero ul li {
		width: 100%;
		float: left;
		display: block;
		line-height: 30px;
	}

	.zero ul li a {
		display: block;
	}

	.zero ul li.pic img {
		width: 100%;
	}

	.zero ul li.pic p {
		font-size: 23rpx;
		height: 30px;
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
		-moz-text-overflow: ellipsis;
	}

	.zero ul li.price .fl {
		float: left;
		color: #666;
		font-size: 21rpx;
	}

	.zero ul li.price s {
		color: #e22c37;
		font-size: 24rpx;
	}

	.zero ul li.price a {
		float: right;
		padding: 0px 20px;
		background-color: #e22c37;
		color: #fff;
		font-size: 23rpx;
		border-radius: 4px;
		box-shadow: 0px 2px 0px #ba000b;
	}

	.zero ul li.num {
		padding-top: 10px;
	}

	.zero ul li.num .fl {
		float: left;
		font-size: 21rpx;
	}

	.zero ul li.num .fl b {
		color: #e22c37;
	}

	.zero ul li.num .fr {
		float: left;
	}

	.zero ul li.num .fr a {
		width: 40px;
		height: 40px;
		border: 1px solid #eee;
		float: left;
		margin-left: 5px;
	}

	/*用户中心首页-头部*/
	.user_index {
		width: 100%;
		height: 100%;
		background-color: #f1f1f1;
		overflow: hidden;
	}

	.user_hd {
		width: 100%;
		height: 120px;
		padding: 30px 0px;
		/* background:url("./static/images/usercenter_bg.jpg") repeat top center; */
		position: relative;
	}

	.user_hd dl {
		width: 90%;
		padding: 5px 5%;
		overflow: hidden;
	}

	.user_hd dl dt {
		width: 80px;
		height: 80px;
		padding: 10px;
		background-color: #f3f3f7;
		border: 3px solid #fff;
		float: left;
		border-radius: 50%;
		box-shadow: 0px 3px 3px #333;
		margin-right: 20px;
	}

	.user_hd dl dt img {
		width: 80px;
		height: 80px;
		border-radius: 50%;
	}

	.user_hd dl dd {
		color: #fff;
	}

	.user_hd dl dd p {
		margin-top: 8px;
		font-size: 23rpx;
	}

	.user_hd dl dd p img {
		float: left;
	}

	.user_hd dl dd p span {
		font-size: 23rpx;
		line-height: 40px;
	}

	.user_hd .message {
		position: absolute;
		right: 20px;
		bottom: 20px;
	}

	.user_hd .message b {
		width: 25px;
		height: 25px;
		line-height: 25px;
		border-radius: 50%;
		text-align: center;
		font-size: 23rpx;
		background-color: #e7545d;
		color: #fff;
		z-index: 1;
		position: absolute;
		right: -10px;
		bottom: 16px;
	}

	/*用户中心首页-导航*/
	.user_nav {
		width: 98%;
		overflow: hidden;
		padding-top: 2%;
		padding-left: 2%;
	}

	.user_nav ul li {
		width: 23%;
		display: block;
		float: left;
		margin-bottom: 2%;
		margin-right: 2%;
		background-color: #fff;
		text-align: center;
		padding: 10px 0px;
		position: relative;
		border-radius: 2px;
	}

	.user_nav ul li a img {
		display: block;
		margin: 0px auto;
		width: 35px;
		height: 35px;
	}

	.user_nav ul li a p {
		color: #666;
		font-size: 23rpx;
		line-height: 30px;
	}

	.user_nav ul li b {
		width: 18px;
		height: 25px;
		padding-left: 9px;
		line-height: 15px;
		color: #fff;
		position: absolute;
		top: 0px;
		right: 0px;
		/* background:url("./static/images/num_bg.png") no-repeat; */
		font-size: 21rpx;
	}

	.user_nav ul li.color_01 {
		box-shadow: 0px 2px 0px #dd7474;
	}

	.user_nav ul li.color_02 {
		box-shadow: 0px 2px 0px #e1a76e;
	}

	.user_nav ul li.color_03 {
		box-shadow: 0px 2px 0px #6ec7e1;
	}

	.user_nav ul li.color_04 {
		box-shadow: 0px 2px 0px #6ee1ba;
	}

	.user_nav ul li.color_05 {
		box-shadow: 0px 2px 0px #6eaae1;
	}

	.user_nav ul li.color_06 {
		box-shadow: 0px 2px 0px #e16ebc;
	}

	.user_nav ul li.color_07 {
		box-shadow: 0px 2px 0px #6eaae1;
	}

	.user_nav ul li.color_08 {
		box-shadow: 0px 2px 0px #e1d86e;
	}

	/*用户中心首页-收藏商品*/
	.collect {
		width: 100%;
		margin-top: 10px;
		border-top: 1px solid #eee;
		border-bottom: 1px solid #eee;
		overflow: hidden;
		background-color: #fff;
		margin-bottom: 10px;
	}

	.collect .tab {
		width: 100%;
		height: 36px;
		line-height: 36px;
		font-size: 23rpx;
		overflow: hidden;
	}

	.collect .tab ul li {
		width: 33%;
		height: 35px;
		line-height: 35px;
		display: block;
		float: left;
		text-align: center;
	}

	.collect .tab ul li a {
		width: 99%;
		float: left;
		border-right: 1px solid #eee;
		overflow: hidden;
		border-bottom: 1px solid #eee;
	}

	.collect .tab ul li.this a {
		color: #e7545d;
		border-bottom: 1px solid #e7545d;
	}

	.collect .tab ul li:last-of-type a {
		border-right: 1px solid #fff;
	}

	.collect_list {
		width: 98%;
		padding-left: 2%;
		padding-top: 2%;
		overflow: hidden;
	}

	.collect_ul li {
		width: 31%;
		margin-right: 2%;
		float: left;
		display: block;
		margin-top: 5px;
	}

	.collect_ul li a {
		width: 100%;
		float: left;
	}

	.collect_ul li a .name {
		font-size: 21rpx;
		color: #666;
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
		-moz-text-overflow: ellipsis;
	}

	.collect_ul li a .price {
		color: #999;
		font-size: 21rpx;
	}

	.collect_ul li a .price b {
		font-size: 24rpx;
		color: #e7545d;
	}

	.collect_store {
		width: 100%;
		overflow: hidden;
		float: left;
	}

	.collect_store li {
		width: 31%;
		margin-right: 2%;
		float: left;
		display: block;
	}

	.collect_store li a {
		width: 100%;
		float: left;
	}

	.collect_store li a img {
		border: 1px solid #eee;
	}

	.collect_store li p {
		text-align: center;
		line-height: 30px;
		font-size: 21rpx;
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
		-moz-text-overflow: ellipsis;
	}

	.see_more {
		width: 80%;
		padding: 10px 10%;
		border-top: 1px dashed #eee;
		margin-top: 5px;
		overflow: hidden;
		background-color: #fff;
		text-align: center;
		font-size: 21rpx;
	}

	/*通用head*/
	.phone_hd {
		width: 100%;
		height: 45px;
		line-height: 45px;
		position: fixed;
		top: 0px;
		left: 0px;
		text-align: center;
		background-color: #1AAACD;
		border-bottom: 1px solid #ffffff;
		font-size: 24rpx;
		color: #fff;
		z-index: 9;
	}

	.phone_hd .back {
		width: 25px;
		height: 25px;
		position: absolute;
		top: 10px;
		left: 10px;
	}

	.phone_hd .city {
		padding: 0px 5px;
		font-size: 21rpx;
		color: #fff;
	}

	.user_bar {
		width: 100%;
		background-color: #e9e9e9;
		height: 45px;
		line-height: 45px;
		position: fixed;
		top: 0px;
		left: 0px;
		font-size: 24rpx;
		border-bottom: 1px solid #d6d6d6;
		z-index: 2;
	}

	.user_bar .back {
		float: left;
		margin-top: 8px;
		margin-left: 10px;
	}

	.user_bar .fr {
		margin-right: 10px;
		font-size: 21rpx;
	}

	.user_bar .fr a {
		padding: 5px 10px;
		border: 1px solid #ddd;
		background-color: #f9f9f9;
		border-radius: 2px;
	}

	.user_bar .del {
		padding: 5px 10px;
		border: 1px solid #ddd;
		background-color: #f9f9f9;
		border-radius: 2px;
		margin-right: 10px;
		float: right;
		margin-top: 5px;
	}

	/*积分专区列表页*/
	.phone_inte {
		width: 100%;
		/* background:url("./static/images/inte_bg.png") repeat-x left bottom #e22c37; */
		height: 100px;
		padding: 20px 0px;
		margin-top: 45px;
	}

	.phone_inte .before_log {
		width: 100%;
		height: 100px;
	}

	.phone_inte .before_log table {
		width: 100%;
		height: 100%;
	}

	.phone_inte .before_log table td {
		color: #fff;
		font-size: 23rpx;
		padding: 5px;
	}

	.phone_inte .before_log table td a {
		padding: 5px 15px;
		background-color: #fff;
		color: #e22c37;
		border-radius: 4px;
	}

	.phone_inte .before_log .inte_bg {
		width: 80px;
		height: 90px;
		line-height: 90px;
		white-space: nowrap;
		overflow: hidden;
		padding-left: 10px;
		/* background:url("./static/images/inte_01.png") no-repeat left center; */
	}

	.phone_inte .after_log .inte_bg {
		/* background:url("./static/images/inte_02.png") no-repeat left center; */
		color: #fff;
	}

	.inte_tab {
		width: 100%;
		line-height: 35px;
	}

	.inte_tab ul li {
		width: 33%;
		height: 34px;
		display: block;
		float: left;
		text-align: center;
		position: relative;
		border-bottom: 1px solid #ddd;
		background-color: #fff;
	}

	.inte_tab ul li a {
		width: 100%;
		color: #666;
		font-size: 21rpx;
	}

	.inte_tab ul li.this {
		border-bottom-color: #e22c37;
	}

	.inte_tab ul li.this a {
		color: #e22c37;
	}

	.inte_tab ul li.this i {
		width: 8px;
		height: 8px;
		position: absolute;
		left: 50%;
		margin-left: -5px;
		bottom: -8px;
		z-index: 1;
	}

	.inte_list {
		width: 96%;
		overflow: hidden;
		padding: 0px 2%;
		clear: both;
		margin-top: 5px;
	}

	.inte_list dl {
		width: 100%;
		padding: 5px 0px;
		display: block;
		float: left;
		border-bottom: 1px solid #ddd;
	}

	.inte_list dl dt {
		width: 25%;
		display: block;
		float: left;
		margin-right: 2%;
	}

	.inte_list dl dd {
		width: 73%;
		display: block;
		float: left;
	}

	.inte_list dl dt img {
		width: 100%;
	}

	.inte_list dl dd {
		font-size: 21rpx;
		line-height: 24px;
	}

	.inte_list dl dd a {
		color: #333;
	}

	.inte_list dl dd ul li {
		width: 100%;
		float: left;
	}

	.inte_list dl dd ul li.name {
		height: 24px;
		overflow: hidden;
		text-overflow: ellipsis;
		-moz-text-overflow: ellipsis;
		white-space: nowrap;
	}

	.inte_list dl dd ul li img {
		float: left;
		margin-right: 5px;
		margin-top: 2px;
	}

	.inte_list dl dd ul li .fl {
		float: left;
	}

	.inte_list dl dd ul li .fr {
		float: right;
	}

	.inte_list dl dd ul li .fr a {
		background-color: #e22c37;
		color: #fff;
		font-size: 21rpx;
		float: left;
		padding: 0px 2em;
		border-radius: 4px;
		box-shadow: 0px 2px 0px #af1b24;
	}

	/*积分专区详细页*/
	.integral_view {
		width: 100%;
		margin-top: 45px;
		background-color: #fff;
		overflow: hidden;
		border-bottom: 2px dashed #F5F5F5;
	}

	.integral_view ul li {
		width: 100%;
		line-height: 35px;
		font-size: 23rpx;
		border-bottom: 1px dashed #ccc;
		display: block;
		float: left;
		padding: 2px 0px;
	}

	.integral_view ul li:last-of-type {
		border-bottom: 0px;
	}

	.integral_view ul li.img {
		position: relative;
		line-height: 0px;
		padding: 0;
	}

	.integral_view ul li.img img {
		max-width: 100%;
		margin: 0px auto;
		display: block;
	}

	.integral_view ul li.img span {
		width: 100%;
		height: 30px;
		line-height: 30px;
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
		background-color: rgba(0, 0, 0, 0.5);
		color: #fff;
		text-indent: 5px;
		position: absolute;
		top: 0px;
		left: 0px;
	}

	.integral_view ul li .fl {
		width: 90px;
		float: left;
		text-align: center;
		padding-left: 5px;
		color: #666;
		border-right: 1px dotted #ddd;
		margin: 2px 5px 2px 0px;
	}

	.integral_view ul li .fr {
		float: left;
		text-align: left;
	}

	.integral_view ul li .orange {
		color: #f60
	}

	.integral_view ul li .fr b {
		padding: 2px 10px;
		background-color: #ff8400;
		color: #fff;
		border-radius: 50%;
	}

	.integral_view ul li .ip50 {
		width: 50px;
		height: 24px;
		border: 1px solid #ddd;
		text-align: center;
		margin-top: 5px;
	}

	.integral_view ul li.btn a {
		width: 200px;
		height: 35px;
		line-height: 35px;
		font-size: 23rpx;
		border-radius: 4px;
		margin: 10px auto;
		background-color: #f60;
		color: #fff;
		text-align: center;
		display: block;
	}

	.integral_con {
		width: 100%;
		overflow: hidden;
		margin-top: 10px;
	}

	.title {
		height: 40px;
		line-height: 40px;
		background-color: #fff;
		font-size: 23rpx;
		padding: 0px 10px;
		font-weight: normal;
		border-bottom: 1px solid #F94D4D;
	}

	.integral_infro {
		padding: 5px;
		font-size: 21rpx;
	}

	.integral_infro img {
		max-width: 100%;
	}

	/*积分专区订单页*/
	.integral_order {
		width: 100%;
		margin-top: 45px;
		overflow: hidden;
	}

	.integral_order ul li {
		padding: 8px 5px;
		overflow: hidden;
		background-color: #fff;
		margin-top: 8px;
		border-bottom: 1px dashed #f5f5f5;
	}

	.integral_order ul li .check {
		width: 25px;
		height: 25px;
		display: block;
		float: left;
		margin-top: 20px;
	}

	.integral_order ul li .img {
		width: 65px;
		height: 65px;
		display: block;
		float: left;
		border: 1px solid #eee;
		margin-right: 5px;
	}

	.integral_order ul li .infro {
		width: 40%;
		float: left;
	}

	.integral_order ul li .infro p {
		font-size: 21rpx;
		color: #666;
		height: 34px;
		overflow: hidden;
	}

	.integral_order ul li .infro .num {
		margin-top: 5px;
		height: auto;
	}

	.integral_order ul li .minus,
	.integral_order ul li .plus {
		width: 32px;
		height: 26px;
		line-height: 20px;
		text-align: center;
		border: 1px solid #ccc;
		background-color: #fff;
		float: left;
		font-size: 26rpx;
	}

	.integral_order ul li .minus {
		background-color: #f1f1f1;
	}

	.integral_order ul li input {
		width: 32px;
		height: 26px;
		border: 1px solid #ccc;
		border-left: none;
		border-right: none;
		float: left;
		text-align: center;
		color: #000;
	}

	.integral_order ul li .fr {
		width: 25%;
		text-align: right;
	}

	.integral_order ul li .fr .score {
		border-radius: 10px 0px 0px 10px;
		background-color: #ff8400;
		color: #fff;
		padding: 2px 10px;
		font-size: 21rpx;
		white-space: nowrap;
		float: right;
		margin-right: -10px;
	}

	.integral_order ul li .fr .del {
		width: 100%;
		float: right;
		margin-top: 10px;
	}

	.integral_order ul li .ems {
		width: 100%;
		border-top: 1px solid #eee;
		height: 30px;
		line-height: 30px;
		text-align: right;
		color: #999;
		font-size: 21rpx;
		overflow: hidden;
		margin-top: 8px;
		float: left;
	}

	.integral_order ul li.this {
		position: relative;
	}

	.integral_order ul li.this .over {
		width: 120px;
		height: 68px;
		position: absolute;
		top: 50%;
		left: 50%;
		margin-left: -50px;
		margin-top: -30px;
		z-index: 1;
	}

	/*收货地址*/
	.add_address {
		background-color: #fff;
		margin-top: 45px;
		padding: 20px 10px;
		overflow: hidden;
	}

	.add_address a {
		display: block;
		border: 1px solid #E4393C;
		color: #E4393C;
		font-size: 23rpx;
		height: 35px;
		text-align: center;
		line-height: 35px;
	}

	.phone_address {
		width: 100%;
		background-color: #fff;
		border-top: 1px solid #ddd;
		border-bottom: 1px solid #ddd;
		margin-top: 10px;
		overflow: hidden;
	}

	.phone_address ul li {
		padding: 5px;
		overflow: hidden;
		font-size: 23rpx;
		line-height: 20px;
	}

	.phone_address ul li.tel span {
		padding-left: 10px;
		font-family: Arial, Helvetica, sans-serif;
	}

	.phone_address ul li.site {
		color: #666;
		white-space: nowrap;
		text-overflow: ellipsis;
		-moz-text-overflow: ellipsis;
	}

	.phone_address ul li.bd {
		border-bottom: 1px dashed #ddd;
	}

	.phone_address ul li.check i {
		width: 20px;
		height: 20px;
		float: left;
		margin-right: 5px;
	}

	.phone_address ul li.check a {
		float: left;
		color: #666;
		margin-left: 5px;
	}

	.red_submit {
		padding: 5px 10px;
		margin-top: 10px;
		overflow: hidden;
	}

	.red_submit a,
	.red_submit input {
		background-color: #f94d4d;
		width: 100%;
		border-radius: 2px;
		color: #fff;
		font-size: 24rpx;
		height: 40px;
		line-height: 40px;
		float: left;
		text-align: center;
	}

	.red_submit textarea {
		width: 98%;
		border: 1px solid #ddd;
		display: block;
		background-color: #fff;
		height: 50px;
	}

	.red_submit .back {
		width: 44%;
		margin: 0px 3%
	}

	.phone_address .order_ul {
		width: 90%;
		display: block;
		float: left;
	}

	.phone_address .more {
		width: 8%;
		float: right;
		padding-right: 5px;
		text-align: right;
	}

	.phone_address .more img {
		margin-top: 15px;
	}

	/*用户中心-团购消费码页*/
	.group_code {
		width: 96%;
		padding: 5px 2%;
		overflow: hidden;
	}

	.group_code dl {
		background-color: #fff;
		display: block;
		margin-top: 10px;
		border-left: 2px dashed #f5f5f5;
		overflow: hidden;
	}

	.group_code dl dt {
		width: 68%;
		display: block;
		float: left;
		padding-left: 2%;
	}

	.group_code dl dd {
		width: 28%;
		height: 90px;
		display: block;
		float: right;
		/* background:url("./static/images/code.png") no-repeat center center #f94d4d; */
		border-right: 2px dashed #f5f5f5;
	}

	.group_code dl dt ul li {
		width: 100%;
		overflow: hidden;
		font-size: 21rpx;
		line-height: 30px;
	}

	.group_code dl dt ul li .fl {
		width: 30%;
		display: block;
		float: left;
		text-align: justify;
		color: #666;
	}

	.group_code dl dt ul li .fr {
		width: 70%;
		float: left;
		white-space: nowrap;
		height: 30px;
		overflow: hidden;
		text-overflow: ellipsis;
		-moz-text-overflow: ellipsis;
	}

	.group_code dl dd p {
		color: #e0e0e0;
		text-align: center;
		line-height: 30px;
		font-size: 23rpx;
	}

	.group_code dl dd p.price {
		font-size: 26rpx;
		padding-top: 15px;
		color: #fff;
	}

	.group_code dl.gray_bg dd {
		background-color: #a6a6a6;
	}

	.group_code dl.orange_bg dd {
		background-color: #ffa200;
	}

	/*用户中心-0元试用列表*/
	.user_free {
		width: 100%;
		margin-top: 45px;
	}

	.user_free_tab {
		width: 100%;
		background-color: #fff;
		border-bottom: 1px solid #eee;
		overflow: hidden;
	}

	.user_free_tab ul li {
		width: 25%;
		display: block;
		float: left;
		text-align: center;
		height: 35px;
		line-height: 35px;
		font-size: 21rpx;
	}

	.user_free_tab ul li a {
		width: 100%;
		float: left;
		color: #666;
	}

	.user_free_tab ul li.this {
		border-bottom: 1px solid #f60;
	}

	.user_free_tab ul li.this a {
		color: #f60;
	}

	.user_free_list {
		width: 100%;
		overflow: hidden;
	}

	.user_free_list dl {
		padding: 5px;
		border-bottom: 1px solid #ddd;
		overflow: hidden;
	}

	.user_free_list dl dt {
		width: 150px;
		height: 62px;
		margin-right: 5px;
		display: block;
		float: left;
	}

	.user_free_list dl p {
		font-size: 21rpx;
		line-height: 18px;
		color: #666;
		margin-bottom: 3px;
	}

	.user_free_list dl .name {
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
		color: #000;
	}

	.user_free_list dl .green {
		color: #00c441;
	}

	.user_free_list dl .bt {
		width: 100%;
		display: block;
		float: left;
		font-size: 21rpx;
		line-height: 30px;
		color: #666;
	}

	.user_free_list dl .bt span {
		width: 50%;
		display: block;
		float: left;
	}

	.user_free_list dl .see {
		padding: 2px 15px;
		background-color: #f60;
		border-radius: 2px;
		color: #fff;
	}

	.user_free_tb {
		width: 100%;
		border-top: 1px solid #fff;
	}

	.user_free_tb td {
		padding: 5px;
		line-height: 24px;
		font-size: 21rpx;
		border-bottom: 1px solid #fff;
	}

	.user_free_tb .td_left {
		border-right: 1px solid #fff;
	}

	.user_free_tb textarea {
		width: 98%;
		border: 1px solid #eee;
		background-color: #fff;
		resize: none;
	}

	.user_free_tb .submit {
		border: none;
		padding: 4px 25px;
		background-color: #f60;
		color: #fff;
		margin-top: 5px;
		border-radius: 2px;
	}

	/*团购列表页*/
	.group_box {
		width: 100%;
		float: left;
		margin-top: 45px;
		padding-bottom: 10px;
	}

	.group_tab {
		width: 100%;
		background-color: #fff;
		border-bottom: 1px solid #ddd;
		overflow: hidden;
	}

	.group_tab ul {
		display: table;
		width: 100%;
	}

	.group_tab ul li {
		width: 25%;
		height: 40px;
		line-height: 40px;
		display: table-cell;
		vertical-align: middle;
		border-right: 1px solid #ddd;
		text-align: center;
		font-size: 21rpx;
	}

	.group_tab ul li a {
		width: 100%;
		float: left;
	}

	.group_tab ul li.this a {
		border-bottom: 1px solid #f94d4d;
		color: #f94d4d;
	}

	.group_tab ul li:last-of-type {
		border-right: 0px;
	}

	.group_ul li {
		width: 96%;
		padding: 10px 2%;
		display: block;
		float: left;
		margin-top: 5px;
		background-color: #fff;
	}

	.group_ul li a {
		width: 100%;
		display: block;
		float: left;
	}

	.group_ul li .left {
		width: 140px;
		height: 93px;
		display: inline-block;
		float: left;
		margin-right: 5px;
		position: relative;
	}

	.group_ul li .left img {
		width: 140px;
		height: 93px;
	}

	.group_ul li .left .over {
		width: 100%;
		height: 100%;
		line-height: 93px;
		position: absolute;
		background: rgba(0, 0, 0, .4);
		color: #fff;
		font-size: 23rpx;
		top: 0px;
		left: 0px;
		text-align: center;
		z-index: 2;
	}

	.group_ul li .right {
		display: block;
		overflow: hidden;
	}

	.group_ul li .right h4 {
		height: 40px;
		line-height: 20px;
		font-size: 23rpx;
		font-weight: normal;
		color: #333;
		overflow: hidden;
	}

	.group_ul li .right .price {
		display: block;
		overflow: hidden;
		font-size: 28rpx;
		line-height: 30px;
		font-family: "宋体";
		color: #f94d4d;
	}

	.group_ul li .right .num {
		display: block;
		color: #999;
		font-size: 20rpx;
		margin-top: 5px;
	}

	.group_ul li .right .num s {
		padding-right: 5px;
		border-right: 1px solid #eee;
	}

	.group_ul li .right .num span {
		padding: 0px 5px;
		border-right: 1px solid #eee;
	}

	.group_ul li .right .num .purson {
		border-right: 0px;
	}

	/*团购详情*/
	.group_infro {
		width: 100%;
		margin-top: 45px;
		overflow: hidden;
		background-color: #fff;
	}

	.group_infro .pic {
		text-align: center;
		position: relative;
	}

	.group_infro .pic span {
		width: 96%;
		z-index: 1;
		height: 40px;
		line-height: 40px;
		padding: 0px 2%;
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
		-moz-text-overflow: ellipsis;
		position: absolute;
		bottom: 3px;
		left: 0px;
		background: rgba(0, 0, 0, .4);
		color: #fff;
		font-size: 23rpx;
	}

	.group_infro .pic img {
		max-width: 100%;
	}

	.group_infro .date {
		padding: 0px 5px;
		margin-top: 5px;
		overflow: hidden;
		line-height: 30px;
	}

	.group_infro .date {
		font-size: 21rpx;
	}

	.group_infro .date .fl {
		color: #999;
	}

	.group_infro .date .fl i {
		width: 20px;
		height: 20px;
		float: left;
		margin-top: 4px;
		margin-right: 3px;
	}

	.group_infro .date .fl b {
		font-size: 23rpx;
		color: #000;
		padding: 0px 2px;
	}

	.group_infro .price {
		padding: 5px;
		margin-top: 5px;
		overflow: hidden;
	}

	.group_infro .price .fl {
		font-size: 21rpx;
		color: #999;
	}

	.group_infro .price .fl b {
		font-size: 28rpx;
		font-family: Arial, Helvetica, sans-serif;
		color: #f94d4d;
	}

	.group_infro .price .fr a {
		padding: 5px 40px;
		background-color: #f94d4d;
		color: #fff;
		font-size: 23rpx;
		border-radius: 4px;
	}

	.group_infro .price .over a {
		background-color: #ccc;
	}

	.group_infro .old_price {
		padding: 0px 5px;
		overflow: hidden;
		border-top: 1px solid #eee;
		border-bottom: 1px solid #eee;
		margin-top: 5px;
	}

	.group_infro .old_price ul li {
		width: 32%;
		text-align: center;
		line-height: 30px;
		display: block;
		float: left;
		border-right: 1px dotted #eee;
	}

	.group_infro .old_price ul li:last-of-type {
		border-right: 0px;
	}

	.group_infro .old_price ul li p {
		font-size: 23rpx;
		color: #666;
	}

	.group_infro .old_price ul li p.gray {
		color: #999;
	}

	.group_infro_con {
		width: 100%;
		overflow: hidden;
		margin-top: 8px;
	}

	.group_infro_tab {
		width: 100%;
		background-color: #fff;
		overflow: hidden;
	}

	.group_infro_tab ul li {
		width: 33.3%;
		height: 40px;
		line-height: 40px;
		border-top: 1px solid #eee;
		border-bottom: 1px solid #eee;
		display: block;
		float: left;
		text-align: center;
		font-size: 21rpx;
	}

	.group_infro_tab ul li a {
		display: block;
		border-right: 1px solid #eee;
	}

	.group_infro_tab ul li:last-of-type a {
		border-right: 0px;
	}

	.group_infro_tab ul li.this a {
		color: #f94d4d;
		border-bottom: 1px solid #f94d4d;
	}

	/*评价*/
	.group_infro_box {
		padding: 5px;
		background-color: #fff;
	}

	.group_infro_box img {
		max-width: 100%;
	}

	.group_infro_box .hd {
		width: 300px;
		margin: 0px auto;
		overflow: hidden;
		display: block;
	}

	.group_infro_box .hd dt {
		width: 70px;
		display: block;
		float: left;
		text-align: center;
		margin-top: 10px;
	}

	.group_infro_box .hd dt p {
		font-size: 23rpx;
	}

	.group_infro_box .hd dt .score {
		height: 40px;
		line-height: 40px;
		color: #f94d4d;
		font-size: 28rpx;
		font-family: Arial, Helvetica, sans-serif;
	}

	.group_infro_box .hd dd {
		width: 220px;
		margin-left: 10px;
		float: left;
		display: block;
		font-size: 23rpx;
	}

	.group_infro_box .hd dd ul li {
		width: 100%;
		line-height: 30px;
		display: block;
		float: left;
	}

	.group_infro_box .hd dd ul li .num {
		width: 90px;
		float: left;
		display: block;
	}

	.group_infro_box .hd dd ul li .num b {
		color: #999;
	}

	.group_infro_box .hd dd ul li .fl {
		width: 110px;
		height: 10px;
		background-color: #ccc;
		margin-top: 10px;
	}

	.group_infro_box .hd dd ul li .fl i {
		height: 10px;
		background-color: #E4393C;
		float: left;
	}

	.phone_eva {
		margin-top: 5px;
		border-top: 2px solid #eee;
		overflow: hidden;
		padding: 5px;
	}

	.phone_eva dl {
		width: 100%;
		margin-top: 10px;
		border-bottom: 1px solid #eee;
		overflow: hidden;
	}

	.phone_eva dl dt {
		width: 20%;
		display: block;
		float: left;
	}

	.phone_eva dl dt img {
		width: 50px;
		height: 50px;
		border: 1px solid #eee;
	}

	.phone_eva dl dd {
		width: 79%;
		display: block;
		float: left;
		padding-left: 1%;
	}

	.phone_eva dl dd p {
		width: 100%;
		overflow: hidden;
		float: left;
		font-size: 21rpx;
		line-height: 24px;
	}

	.phone_eva dl dd p .fr {
		color: #999;
	}

	.phone_eva dl dd p.eva {
		max-height: 48px;
	}

	.phone_eva dl dd p.pic {
		padding: 5px 0px;
	}

	.phone_eva dl dd p.pic img {
		margin-right: 5px;
		margin-bottom: 5px;
	}

	.phone_eva dl dd.reply {
		width: 100%;
		height: 30px;
		line-height: 30px;
	}

	.phone_eva dl dd.reply .gray {
		width: 20%;
		display: block;
		float: left;
	}

	.group_infro_box table {
		width: 100%;
		border-collapse: collapse;
	}

	.group_infro_box table th {
		background-color: #f0f0f0;
		border: 1px solid #eee;
		border-bottom: 2px solid #eee;
		font-size: 21rpx;
		line-height: 24px;
		font-weight: normal;
	}

	.group_infro_box table td {
		border: 1px solid #eee;
		font-size: 21rpx;
		line-height: 24px;
		padding: 2px;
	}

	/*选择城市*/
	.select_city {
		width: 100%;
		margin-top: 45px;
		overflow: hidden;
		background-color: #fff;
	}

	.city_box {
		margin-bottom: 10px;
	}

	.select_city h2 {
		background-color: #ccc;
		color: #000;
		font-family: Arial, Helvetica, sans-serif;
		font-size: 24rpx;
		height: 40px;
		line-height: 40px;
		text-align: center;
		font-weight: normal;
	}

	.select_city ul li {
		width: 100%;
		display: block;
		height: 45px;
		line-height: 45px;
		border-bottom: 1px solid #ddd;
	}

	.select_city ul li a {
		padding: 0px 20px;
		white-space: nowrap;
		overflow: hidden;
		font-size: 23rpx;
	}

	/*分类弹出层*/
	.phone_hd .menu {
		width: 30px;
		height: 30px;
		position: absolute;
		top: 8px;
		right: 10px;
	}

	.menu_overly {
		width: 100%;
		height: 100%;
		position: fixed;
		top: 45px;
		right: 0px;
	}

	.menu_overly .menu_bg {
		width: 100%;
		height: 100%;
		background: rgba(0, 0, 0, .3);
		z-index: 3;
		position: absolute;
		top: 0px;
		left: 0px;
	}

	.menu_overly .menu_ul {
		width: 140px;
		height: 100%;
		overflow-y: auto;
		background-color: #fff;
		position: absolute;
		top: 0px;
		right: 0px;
		z-index: 4;
		border: 1px solid #ddd;
	}

	.menu_overly .menu_ul li {
		font-size: 23rpx;
		line-height: 40px;
		border-bottom: 1px solid #eee;
		display: block;
		width: 100%;
		overflow: hidden;
	}

	.menu_overly .menu_ul li a {
		width: 100%;
		float: left;
	}

	.menu_overly .menu_ul li.this a {
		background-color: #F94D4D;
		color: #fff;
	}

	/*0元试用列表页*/
	.free_box {
		width: 100%;
		margin-top: 45px;
		padding-bottom: 10px;
		overflow: hidden;
	}

	.free_box ul li {
		width: 96%;
		padding: 3px 2%;
		display: block;
		border-top: 1px solid #eee;
		border-bottom: 1px solid #eee;
		background-color: #fff;
		margin-top: 5px;
		float: left;
	}

	.free_box ul li a {
		width: 100%;
		float: left;
	}

	.free_box ul li p {
		overflow: hidden;
		font-size: 21rpx;
	}

	.free_box ul li p.img {
		width: 100%;
		position: relative;
	}

	.free_box ul li p.img img {
		width: 100%;
	}

	.free_box ul li p.img .time {
		width: 100%;
		height: 30px;
		line-height: 30px;
		position: absolute;
		bottom: 0px;
		left: 0px;
		background: rgba(0, 0, 0, .3);
		color: #ddd;
		font-size: 23rpx;
		z-index: 2;
	}

	.free_box ul li p.img .time i {
		width: 25px;
		height: 25px;
		float: left;
		margin-left: 10px;
		margin-right: 10px;
		margin-top: 3px;
	}

	.free_box ul li p.img .time b {
		color: #fff;
	}

	.free_box ul li p.img .purson {
		position: absolute;
		bottom: 0px;
		right: 10px;
		z-index: 2;
		color: #fff;
		font-size: 21rpx;
		height: 30px;
		line-height: 30px;
	}

	.free_box ul li p.img .purson b {
		color: #f94d4d;
	}

	.free_box ul li p.name {
		width: 100%;
		font-size: 23rpx;
		line-height: 30px;
		height: 30px;
		text-overflow: ellipsis;
		-moz-text-overflow: ellipsis;
		color: #000;
		white-space: nowrap;
		overflow: hidden;
	}

	.free_box ul li p.price {
		padding-bottom: 10px;
		height: 30px;
		line-height: 30px;
		font-size: 21rpx;
		color: #666;
	}

	.free_box ul li p.price s {
		color: #f94d4d;
		font-size: 26rpx;
	}

	.free_box ul li p.price .fr {
		padding: 2px 10%;
		background-color: #f94d4d;
		color: #fff;
		font-size: 23rpx;
		box-shadow: 2px;
		box-shadow: 0px 3px #a91f1f;
	}

	.free_box ul li p.price .fr a {
		color: #fff;
	}

	.free_box ul li .time {
		padding: 5px 0px;
		font-size: 21rpx;
		color: #666;
	}

	.free_box ul li .time i {
		width: 25px;
		height: 25px;
		float: left;
		margin-right: 3px;
	}

	.free_box ul li .time .fl {
		margin-top: 2px;
		font-size: 23rpx;
	}

	.free_box ul li .time b {
		font-size: 24rpx;
		color: #F94D4D;
		padding: 0px 2px;
	}

	.free_box ul li .num {
		padding: 2px 0px;
	}

	.free_box ul li .num .fl {
		color: #666;
		width: 62px;
	}

	.free_box ul li .num .fr {
		float: left;
		width: 77%;
	}

	.free_box ul li .num .fr a {
		width: 42px;
		height: 42px;
		float: left;
		margin-left: 5px;
		margin-bottom: 5px;
	}

	.free_box ul li .num .fr img {
		width: 40px;
		height: 40px;
		border: 1px solid #eee;
	}

	.free_box ul li.over p.price .fr {
		background-color: #ccc;
		box-shadow: 0px 3px #999;
	}

	.free_con {
		width: 100%;
		overflow: hidden;
		background-color: #fff;
		border-top: 1px solid #eee;
	}

	.car_total {
		width: 100%;
		background: #fff;
		position: fixed;
		height: 50px;
		bottom: 0px;
		box-shadow: 0px -1px 2px rgba(0, 0, 0, 0.3)
	}

	.car_btn {
		width: 100px;
		border: 0px;
		float: right;
		margin-right: 10px;
		height: 32px;
		color: #FFF;
		font-size: 23rpx;
		border-radius: 3px;
		background: #e02626;
		margin-top: 10px;
		-webkit-appearance: none;
	}

	.car_btn_gray {
		width: 100px;
		border: 0px;
		float: right;
		margin-right: 10px;
		height: 32px;
		color: #FFF;
		font-size: 23rpx;
		border-radius: 3px;
		background: #999;
		margin-top: 10px;
		-webkit-appearance: none;
	}

	.car_total span {
		padding-left: 10px;
		font-size: 21rpx;
		color: #333;
		line-height: 30px;
		margin-top: 10px;
		display: block;
		float: left;
	}

	.car_total span b {
		font-size: 28rpx;
		font-weight: bold;
		color: #c30808;
	}

	.car_total span i {
		float: left;
	}

	.head_btn {
		height: 30px;
		padding-left: 20px;
		padding-right: 20px;
		border: 1px solid #dddddd;
		background: -o-linear-gradient(top, #f0f0f0, #dddddd);
		background: -moz-linear-gradient(top, #f0f0f0, #dddddd);
		background: -webkit-linear-gradient(top, #f0f0f0, #dddddd);
		background: -ms-linear-gradient(top, #f0f0f0, #dddddd);
		background: linear-gradient(top, #f0f0f0, #dddddd);
		border-radius: 3px;
		float: right;
		font-size: 21rpx;
		margin-right: 5px;
	}

	/*登陆-注册*/
	.phone_hd .home {
		top: 10px;
	}

	.phone_login {
		width: 96%;
		overflow: hidden;
		padding: 0px 2%;
		margin-top: 10px;
		margin-bottom: 15px;
	}

	.phone_login ul li {
		margin: 10px 0px;
		display: block;
		font-size: 23rpx;
		color: #666;
		overflow: hidden;
		line-height: 30px;
	}

	.phone_login ul li.ip {
		border: 1px solid #ddd;
		background-color: #fff;
		border-radius: 2px;
	}

	.phone_login ul li.ip input {
		width: 99%;
		border: none;
		height: 38px;
		font-size: 23rpx;
		text-indent: 10px;
		border-radius: 2px;
	}

	.phone_login ul li.wx_btn input {
		width: 100%;
		border-radius: 2px;
		background-color: green;
		color: #fff;
		font-size: 24rpx;
		border: none;
		height: 40px;
		line-height: 40px;
		-webkit-appearance: none;
	}

	.phone_login ul li.yzm input {
		width: 50%;
		float: left;
		border: 1px solid #ddd;
		background-color: #fff;
		border-radius: 2px;
		font-size: 23rpx;
		text-indent: 10px;
		height: 38px;
		float: left;
	}

	.phone_login ul li.yzm img {
		margin-left: 10px;
		float: left;
		margin-top: 5px;
	}

	.phone_login ul li.bd_red {
		border-color: #EB512D;
	}

	.phone_login ul li.yz {
		color: #EB512D;
		text-indent: 10px;
		margin: 0px;
	}

	.phone_login ul.yz_step2 li {
		width: 100%;
		float: left;
	}

	.phone_login ul.yz_step2 li.sele_li {
		width: 50%;
	}

	.phone_login ul.yz_step2 .tel_yz {
		width: 50%;
	}

	.phone_login ul li select {
		padding: 3px;
		font-size: 21rpx;
		width: 90%;
		border: 1px solid #ddd;
		float: left;
		color: #666;
	}

	.phone_login ul li select option {
		padding: 3px;
	}

	.phone_login ul li.tel_yz a {
		width: 90%;
		text-align: center;
		height: 30px;
		line-height: 30px;
		background-color: #ccc;
		border-radius: 2px;
		float: left;
		background-image: -moz-linear-gradient(top, #eeeeee, #dddddd);
		border: 1px solid #ddd;
	}

	.deal {
		width: 96%;
		margin-top: 45px;
		font-size: 21rpx;
		line-height: 24px;
		padding: 0px 2%;
		color: #666;
	}

	.phone_login ul li.text textarea {
		width: 98%;
		border: 1px solid #ddd;
		background-color: #fff;
		font-size: 23rpx;
		height: 50px;
		resize: none;
	}

	.phone_login ul li.score .red {
		font-size: 24rpx;
		padding: 0px 2px;
	}

	.phone_login ul li .code {
		width: 45%;
		border: 1px solid #bbb;
		background-color: #ccc;
		height: 38px;
		line-height: 38px;
		text-align: center;
		float: right;
		border-radius: 2px;
		color: #fff;
	}

	.phone_login ul li.att {
		border: 1px solid #EB512D;
		color: #EB512D;
		font-size: 21rpx;
		border-radius: 2px;
		padding: 5px;
		padding-left: 30px;
		/* background:url("./static/images/att.png") no-repeat 5px center; */
	}

	/*推荐品牌*/
	.brand_page {
		width: 100%;
		margin-top: 45px;
		overflow: hidden;
		padding-bottom: 10px;
	}

	.brand_recommend {
		width: 100%;
		overflow: hidden;
		border-top: 1px solid #eee;
		border-bottom: 1px solid #eee;
		background-color: #fff;
		box-shadow: 0px 1px 0px #ccc;
		margin-bottom: 10px;
	}

	.brand_recommend h1 {
		padding: 10px;
		font-weight: normal;
		overflow: hidden;
		font-size: 23rpx;
	}

	.brand_recommend h1 .fl {
		border-left: 3px solid #e22c37;
		padding-left: 8px;
		height: 15px;
		line-height: 15px;
		float: left;
		margin-top: 3px;
	}

	.brand_recommend_box {
		overflow: hidden;
		padding-bottom: 2%;
		margin: 0 10px 5px;
		padding-left: 1px;
		padding-top: 1px;
	}

	.brand_recommend_box span {
		width: 50%;
		display: block;
		float: left;
	}

	.brand_recommend_box span a {
		padding: 1px;
		display: block;
		border: 1px solid #eee;
		margin-top: -1px;
		margin-left: -1px;
	}

	.brand_recommend_box span a img {
		width: 142px;
		height: 66px;
		margin: 0px auto;
		display: block;
	}

	/*品牌列表页*/
	.brand_list {
		width: 100%;
		float: left;
	}

	.brand_list h2 {
		line-height: 30px;
		padding-left: 152px;
		font-size: 23rpx;
		font-weight: normal;
	}

	.brand_list ul {
		width: 100%;
		display: block;
		overflow: hidden;
		border-top: 1px solid #eee;
		border-bottom: 1px solid #ddd;
		background-color: #fff;
	}

	.brand_list ul li {
		width: 100%;
		display: block;
		overflow: hidden;
		border-bottom: 1px dashed #eee;
	}

	.brand_list ul li:last-of-type {
		border-bottom: 0px;
	}

	.brand_list ul li a {
		display: block;
	}

	.brand_list ul li .lg {
		width: 142px;
		height: 66px;
		display: block;
		float: left;
		margin-right: 10px;
	}

	.brand_list ul li img {
		display: block;
		height: 66px;
		margin: 0 auto;
		width: 142px;
		vertical-align: middle;
	}

	.brand_list ul li .name {
		line-height: 66px;
		font-size: 23rpx;
		color: #666;
	}

	.brand_nav_fixed {
		width: 50px;
		line-height: 24px;
		position: fixed;
		top: 55px;
		right: 5px;
		z-index: 3;
	}

	.brand_nav_fixed a {
		width: 100%;
		float: left;
		text-align: right;
		color: #000;
		font-size: 21rpx;
		font-family: Arial;
	}

	.brand_nav_fixed a.this {
		color: #f94d4d;
		font-weight: bold;
		font-size: 23rpx;
	}

	/*收藏店铺列表页*/
	.collect_page {
		width: 96%;
		padding: 10px 2%;
		margin-top: 35px;
		overflow: hidden;
	}

	.collect_page ul li {
		width: 100%;
		float: left;
		display: block;
		font-size: 21rpx;
		margin-top: 10px;
	}

	.collect_page ul li .img {
		width: 120px;
		height: 50px;
		float: left;
		display: block;
		margin-right: 5px;
	}

	.collect_page ul li .img b {
		width: 116px;
		height: 48px;
		border-radius: 4px;
		background-color: #fff;
		padding: 2px;
		border: 1px solid #eee;
		float: left;
	}

	.collect_page ul li .img img {
		width: 116px;
		height: 48px;
		border-radius: 4px;
	}

	.collect_page ul li .name {
		width: 50%;
		float: left;
	}

	.collect_page ul li .name p {
		width: 100%;
		font-size: 21rpx;
		color: #000;
		margin-top: 8px;
		white-space: nowrap;
		overflow: hidden;
		float: left;
		display: block;
		text-overflow: ellipsis;
		-moz-text-overflow: ellipsis;
	}

	.stars {
		width: 88px;
		height: 15px;
		/* background:url("./static/images/love.png") no-repeat 0px -18px; */
		float: left;
	}

	.stars i {
		height: 15px;
		/* background:url("./static/images/love.png") no-repeat 0px 0px; */
		float: left;
	}

	.collect_page ul li .name .num {
		font-size: 21rpx;
		float: left;
		padding-left: 10px;
		color: #999;
	}

	.collect_page ul li .del {
		float: right;
		margin-top: 20px;
	}

	/*购物车-------------------------*/
	.phone_hd .edit {
		height: 20px;
		line-height: 20px;
		position: absolute;
		top: 12px;
		right: 10px;
		font-size: 21rpx;
		color: #fff;
	}

	.phone_hd .edit img {
		float: left;
		margin-right: 5px;
	}

	.car_page {
		width: 100%;
		margin-top: 35px;
		padding: 10px 0px;
		padding-bottom: 80px;
	}

	.car_page ul li {
		background-color: #fff;
		border-top: 1px solid #ddd;
		border-bottom: 1px solid #ddd;
		padding: 10px 5px;
		margin-top: 10px;
		overflow: hidden;
		font-size: 21rpx;
	}

	.car_page ul li .li_box {
		width: 100%;
		overflow: hidden;
		margin: 10px 2px;
	}

	.car_page ul li .check {
		width: 25px;
		float: left;
		margin-top: 20px;
	}

	.car_page ul li .img {
		width: 60px;
		height: 60px;
		border: 1px solid #eee;
		float: left;
		margin-right: 5px;
	}

	.car_page ul li .infro {
		width: 40%;
		overflow: hidden;
		float: left;
	}

	.car_page ul li .infro .name {
		width: 100%;
		float: left;
		height: 2.4em;
		line-height: 1.2em;
		overflow: hidden;
		text-overflow: ellipsis;
		font-size: 22rpx;
	}

	.car_page ul li .num {
		float: left;
		margin-top: 2px;
	}

	.car_page ul li .minus,
	.car_page ul li .plus {
		width: 32px;
		height: 26px;
		line-height: 20px;
		text-align: center;
		border: 1px solid #ccc;
		background-color: #fff;
		float: left;
		font-size: 26rpx;
	}

	.car_page ul li .minus {
		background-color: #f1f1f1;
	}

	.car_page ul li input {
		width: 32px;
		height: 26px;
		border: 1px solid #ccc;
		border-left: none;
		border-right: none;
		float: left;
		text-align: center;
		color: #000;
	}

	.car_page ul li .fr {
		width: 20%;
		text-align: right;
	}

	.car_page ul li .fr span {
		width: 100%;
		float: left;
	}

	.car_page ul li .fr .del {
		margin-top: 10px;
	}

	.car_page ul li .tel {
		line-height: 30px;
		font-weight: normal;
		border-bottom: 1px dashed #ddd;
		margin-bottom: 5px;
	}

	.car_page ul li .life_price {
		color: #F94D4D;
		font-size: 24rpx;
		font-family: Arial, Helvetica, sans-serif;
		margin-top: 15px;
	}

	.car_pay .total .life_pri {
		margin-left: 20px;
		margin-top: 15px;
	}

	.car_page ul li .tel input {
		float: none;
		border: none;
	}

	/*购物车-满减-满赠*/
	.car_page ul li .car_act {
		overflow: hidden;
		line-height: 20px;
	}

	.car_page ul li .car_act .red_bg {
		float: left;
		background-color: #f94d4d;
		color: #fff;
		padding: 0px 5px;
	}

	.car_page ul li .car_act .gray {
		width: 60%;
		float: left;
		color: #999;
		padding-left: 10px;
	}

	.car_page ul li .car_act .get {
		float: right;
	}

	.car_page ul li .car_act .get a {
		padding: 2px 5px;
		border: 1px solid #f94d4d;
		color: #f94d4d;
		white-space: nowrap;
		border-radius: 2px;
	}

	.car_page ul li .car_act .del {
		float: right;
	}

	.car_page ul li .car_act .li_hd {
		margin-left: 25px;
		border-bottom: 1px dashed #ddd;
		overflow: hidden;
		margin-bottom: 5px;
		padding-bottom: 5px;
		padding-top: 1px;
	}

	/*购物车-组合*/
	.car_page ul li.com .car_act {
		margin-left: 0px;
	}

	.car_page ul li.com .car_act .check {
		margin-top: 0px;
	}

	.car_page ul li.com .car_act .gray {
		width: 55%;
	}

	.car_page ul li.com .img {
		margin-left: 25px;
	}

	.car_page ul li.com .li_box {
		margin-bottom: 5px;
	}

	.car_page ul li.com .num {
		margin-top: 0px;
		margin-left: 3px;
	}

	.car_page ul li.com .infro .gray {
		color: #999;
		line-height: 30px;
	}

	/*购物车-结算*/
	.car_pay {
		width: 100%;
		height: 75px;
		background-color: #EAEDF1;
		position: fixed;
		bottom: 0px;
		left: 0px;
		z-index: 2;
	}

	/* .car_pay { width:100%; height:75px; background-color:#EAEDF1; position:relative; bottom:0px; left:0px; z-index:2;} */
	.car_store {
		position: relative;
		height: auto;
		/* min-height:400px;  */
	}

	.car_pay .check {
		width: 15%;
		float: left;
		padding-top: 25px;
		text-align: center;
	}

	.car_pay .total {
		width: 66%;
		float: left;
		line-height: 25px;
	}

	.car_pay .total p {
		font-size: 21rpx;
		color: #999;
		white-space: nowrap;
		text-overflow: ellipsis;
		-moz-text-overflow: ellipsis;
	}

	.car_pay .total .price {
		font-size: 24rpx;
		font-family: Arial, Helvetica, sans-serif;
		color: #f94d4d;
	}

	.car_pay .paymen_btn {
		width: 25%;
		height: 75px;
		line-height: 75px;
		float: right;
		text-align: center;
		font-size: 24rpx;
		color: #fff;
		background-color: #f94d4d;
	}

	.car_pay .total .total_pri {
		line-height: 75px;
	}

	/*购物车-满赠弹出层*/
	.gift_box .black_bg {
		width: 100%;
		height: 100%;
		position: fixed;
		top: 0px;
		left: 0px;
		background-color: #000;
		filter: alpha(opacity=50);
		-moz-opacity: 0.5;
		-khtml-opacity: 0.5;
		opacity: 0.5;
		z-index: 3;
	}

	.gift_div {
		width: 100%;
		position: fixed;
		bottom: 0px;
		left: 0px;
		background-color: #fff;
		z-index: 4
	}

	.gift_header {
		height: 42px;
		line-height: 42px;
		color: #666;
		padding: 0px 10px;
		border-bottom: 1px solid #ccc;
	}

	.gift_header .fl {
		font-size: 20rpx;
	}

	.gift_header .fl b {
		color: #f94d4d;
	}

	.gift_header .fr a {
		padding: 4px 15px;
		background-color: #f94d4d;
		color: #fff;
		border-radius: 4px;
	}

	.gift_bd {
		height: 314px;
		overflow: auto;
	}

	.gift_bd ul li {
		padding: 5px;
		border-bottom: 1px solid #ddd;
		overflow: hidden;
	}

	.gift_bd ul li:last-of-type {
		border-bottom: 0px;
	}

	.gift_bd ul li table {
		width: 100%;
	}

	.gift_bd ul li table td {
		font-size: 21rpx;
		line-height: 22px;
		padding: 2px;
	}

	.gift_bd ul li table td p {
		font-size: 21rpx;
	}

	.gift_bd ul li table td .name {
		height: 44px;
		overflow: hidden;
	}

	.gift_bd ul li table td .gray {
		color: #999;
	}

	.gift_bd ul li table td s {
		color: #999;
	}

	.car_page .gift_bd ul li {
		border-top: 0px;
	}

	/*店铺首页 */
	.store_index {
		width: 100%;
		margin-top: 45px;
		margin-bottom: 10px;
	}

	.store_hd {
		background-color: #fff;
		border-bottom: 1px dashed #eee;
		padding: 8px 5px;
		overflow: hidden;
	}

	.store_logo {
		width: 130px;
		height: 50px;
		padding: 2px;
		border: 1px solid #eee;
		box-shadow: 0px 0px 1px #ddd;
		float: left;
		border-radius: 4px;
		margin-right: 10px;
	}

	.store_name {
		height: 30px;
		line-height: 30px;
		white-space: nowrap;
		font-size: 23rpx;
		overflow: hidden;
		text-overflow: ellipsis;
	}

	.store_stars {
		margin-top: 5px;
		font-size: 21rpx;
	}

	.store_stars b {
		float: left;
		line-height: 15px;
		padding-left: 10px;
		color: #666;
	}

	.store_score {
		padding: 5px;
		border-bottom: 1px solid #eee;
		background-color: #fff;
		overflow: hidden;
	}

	.store_score ul li {
		width: 33%;
		display: block;
		float: left;
		text-align: center;
		line-height: 30px;
		border-right: 1px solid #eee;
	}

	.store_score ul li:last-of-type {
		border-right: 0px;
	}

	.store_score ul li p {
		width: 100%;
		float: left;
		font-size: 21rpx;
	}

	.store_score ul li p span {
		width: 60px;
		margin: 0px auto;
		display: block;
		overflow: hidden;
	}

	.store_score ul li p span i {
		width: 20px;
		height: 20px;
		float: left;
	}

	.store_score ul li p b {
		line-height: 20px;
		color: #666;
	}

	.store_list {
		margin-top: 8px;
		overflow: hidden;
		padding: 5px;
	}

	.store_list h1 {
		font-size: 23rpx;
		border-top: 2px dashed #F5F5F5;
		border-bottom: 2px dashed #F5F5F5;
		background-color: #fff;
		height: 35px;
		line-height: 35px;
		text-align: center;
	}

	.store_ul li {
		width: 48%;
		margin: 0px 1%;
		display: block;
		float: left;
		background-color: #fff;
		box-shadow: 0px 2px 0px #e3e3e3;
		margin-top: 8px;
	}

	.store_ul li a img {
		width: 100%;
	}

	.store_ul li a p.name {
		font-size: 21rpx;
		height: 40px;
		line-height: 20px;
		overflow: hidden;
		padding: 0px 5px;
		color: #666;
	}

	.store_ul li a p.price {
		text-align: center;
		line-height: 24px;
		color: #E43743;
		font-family: Arial, Helvetica, sans-serif;
	}

	/**添加收货地址**/
	.brand_class_box {
		width: 180px;
		position: fixed;
		overflow: hidden;
		height: 30px;
		max-height: none;
		left: 50%;
		margin-top: 0;
		top: 35%;
		margin-left: -90px;
		overflow-y: auto;
		z-index: 99;
		float: left;
		height: 30px;
	}

	.brand_class_box select {
		float: left;
		width: 180px;
		height: 30px;
		line-height: 30px;
	}

	.brand_class_box select option {
		float: left;
	}

	.brand_class_box h3 {
		background: #f3eeee;
		height: 35px;
		line-height: 35px;
	}

	.brand_class_box h3 span {
		padding-left: 10px;
		line-height: 35px;
		color: #333;
		font-size: 23rpx;
		font-weight: lighter
	}

	.brand_class_box h3 span i {
		display: block;
		float: left;
		width: 13px;
		height: 12px;
		/* background:url("./static/images/group_class.png") no-repeat; */
		margin-top: 12px;
		margin-left: 10px;
		background-size: 100%;
	}

	.brand_class_box ul {
		width: 100%;
	}

	.brand_class_box ul li {
		width: 100%;
		line-height: 50px;
		border-bottom: 1px dotted #f5f5f5;
		height: 50px;
		font-size: 22rpx;
		text-indent: 1em;
		float: left
	}

	.brand_class_box ul li:foucs {
		border: none;
	}

	.brand_class_box ul li:hover {
		background: #f5f5f5;
	}

	.brand_class_box ul li a {
		width: 90%;
		display: block;
		height: 50px;
		/* background:url("./static/images/right_a.gif") no-repeat right center; */
		float: left
	}

	.select_left {
		width: 100%;
	}

	.select_right {
		width: 100%;
	}

	.add_area {
		width: 300px;
		border: 1px solid #dddddd;
		text-align: center;
		/* background:url("./static/images/select_arrow.png") no-repeat 270px center #f0f0f0; */
		float: left;
		border-radius: 4px;
		margin-bottom: 5px;
	}

	.brand_class_opcity {
		width: 100%;
		height: 100%;
		background: #000;
		opacity: 0.3;
		position: fixed;
		top: 0px;
		left: 0px;
	}

	/**退货列表**/
	.order_return_list {
		width: 100%;
		margin-top: 45px;
		overflow: hidden;
	}

	.resturn_tab {
		background-color: #fff;
		border-top: 1px solid #eee;
		overflow: hidden;
	}

	.resturn_tab li {
		width: 50%;
		height: 35px;
		line-height: 35px;
		display: block;
		float: left;
		text-align: center;
		border-bottom: 1px solid #eee;
	}

	.resturn_tab li a {
		display: block;
		border-right: 1px solid #eee;
	}

	.resturn_tab li:last-of-type a {
		border-right: 0px;
	}

	.resturn_tab li.this {
		border-bottom: 1px solid #f94d4d;
	}

	.resturn_tab li.this a {
		color: #f94d4d;
	}

	.goods_resturn {
		width: 100%;
		margin-top: 5px;
		overflow: hidden;
	}

	.goods_resturn dl {
		padding: 5px;
		border-top: 1px solid #eee;
		border-bottom: 1px solid #eee;
		background-color: #fff;
		overflow: hidden;
		margin-bottom: 5px;
	}

	.goods_resturn dl dt,
	.goods_resturn dl dd {
		width: 100%;
		display: block;
		float: left;
		line-height: 30px;
	}

	.goods_resturn dl dt {
		border-bottom: 1px dashed #eee;
		font-size: 23rpx;
	}

	.goods_resturn dl dt .orange {
		color: #f60;
	}

	.goods_resturn dl dt .green {
		color: #22cc00;
	}

	.goods_resturn dl dd {
		margin-top: 5px;
	}

	.goods_resturn dl dd .fl {
		width: 75%;
	}

	.goods_resturn dl dd .fr {
		width: 25%;
	}

	.goods_resturn dl dd .fl img {
		width: 60px;
		height: 60px;
		border: 1px solid #eee;
		margin-right: 5px;
		float: left;
	}

	.goods_resturn dl dd .fl p {
		width: 70%;
		float: left;
		font-size: 21rpx;
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
		color: #666;
	}

	.goods_resturn dl dd .fr a {
		float: right;
		padding: 0px 10px;
		background-color: #f60;
		color: #fff;
		border-radius: 4px;
		margin-top: 15px;
	}

	.goods_resturn dl dd .gray a {
		background-color: #ccc;
	}

	/**申请退货**/
	.apply_return {
		width: 100%;
		overflow: hidden;
	}

	.explain {
		border-top: 1px solid #ffecec;
		border-bottom: 1px solid #ffecec;
		padding: 5px 10px;
		color: #f94d4d;
		background-color: #fff9f9;
		font-size: 21rpx;
		line-height: 24px;
		margin-top: 3px;
	}

	.apply_return table {
		width: 100%;
	}

	.apply_return table td {
		padding: 5px;
		line-height: 20px;
		border-bottom: 1px solid #eee;
	}

	.apply_return table textarea {
		width: 100%;
		border: 1px solid #ddd;
		height: 60px;
		resize: none;
	}

	/**服务单详情**/
	.returnlog_ul {
		padding: 5px;
		overflow: hidden;
	}

	.returnlog_ul li {
		line-height: 24px;
		overflow: hidden;
	}

	.returnlog_ul .bg_white {
		background-color: #fff;
		padding: 2px 5px;
		white-space: nowrap;
		text-overflow: ellipsis;
	}

	.returnlog_ul li .orange {
		color: #f60;
		padding: 0px 5px;
	}

	.returnlog_ul li .time {
		padding: 0px 5px;
	}

	.returnlog_ul li .time b {
		color: #f60;
		padding: 0px 2px;
	}

	.returnlog_box {
		padding: 5px;
		margin-top: 5px;
		background-color: #fff;
		overflow: hidden;
	}

	.returnlog_box h2 {
		padding: 5px 0px;
		border-bottom: 1px solid #eee;
	}

	.returnlog_box ul {
		padding: 5px;
		border: 1px solid #eee;
		overflow: hidden;
		margin-top: 5px;
		background-color: #fafafa;
		border-radius: 2px;
	}

	.returnlog_box ul li {
		line-height: 30px;
	}

	.returnlog_box ul li .select {
		width: 200px;
		height: 30px;
		line-height: 30px;
		border: 1px solid #ddd;
		padding: 2px;
	}

	.returnlog_box ul li .select option {
		padding: 2px;
	}

	.returnlog_box ul li .ip {
		width: 200px;
		border: 1px solid #ddd;
		height: 30px;
		line-height: 30px;
		text-indent: 5px;
	}

	.returnlog_box ul.ems li {
		margin-top: 5px;
	}

	/**组合销售**/
	.combination {
		width: 100%;
		margin-top: 45px;
		overflow: hidden;
	}

	.combination dl {
		padding: 5px 10px;
		overflow: hidden;
		background-color: #fff;
		margin-top: 10px;
		border-top: 1px solid #eee;
		border-bottom: 1px solid #eee;
	}

	.combination dl dt {
		width: 100%;
		height: 30px;
		line-height: 30px;
		font-size: 23rpx;
		overflow: hidden;
	}

	.combination dl dt .fl b {
		color: #FF6B6B;
		padding: 0px 5px;
	}

	.combination dl dd {
		width: 100%;
		margin-top: 5px;
		overflow: hidden;
	}

	.combination dl dd.shrink ul li {
		width: 63px;
		height: 63px;
		padding: 1px;
		margin-right: 10px;
		margin-bottom: 10px;
		display: block;
		float: left;
		border: 1px solid #eee;
	}

	.combination dl dd.shrink ul li:nth-child(4n) {
		margin-right: 0px;
	}

	.combination dl dd.unfold ul li {
		width: 100%;
		border-bottom: 1px solid #eee;
		display: block;
		overflow: hidden;
		padding: 5px 0px;
	}

	.combination dl dd.unfold ul li .fl {
		width: 63px;
		height: 63px;
		padding: 1px;
		border: 1px solid #eee;
		margin-right: 10px;
	}

	.combination dl dd.unfold ul li .name {
		width: 70%;
		height: 67px;
		overflow: hidden;
		line-height: 22px;
		float: left;
	}

	.combination dl dd.unfold .price {
		padding: 5px 0px;
		line-height: 30px;
		overflow: hidden;
	}

	.combination dl dd.unfold .price .fl {
		font-size: 23rpx;
		color: #000;
	}

	.combination dl dd.unfold .price .fl b {
		color: #FF6B6B;
		padding-left: 5px;
	}

	.combination dl dd.unfold .price .fr {
		color: #999
	}

	.combination dl dd.unfold .price p {
		width: 100%;
		float: left;
	}

	.combination dl dd.unfold .price p span {
		padding: 2px 10px;
		color: #F24B48;
		border: 1px solid #F29E9E;
		font-size: 21rpx;
		border-radius: 2px;
	}

	.combination dl dd.unfold .red_submit {
		margin-top: 0px;
	}

	/**填写订单**/
	.fill_order {
		width: 100%;
		overflow: hidden;
		margin-bottom: 10px;
	}

	.fill_order h2 {
		padding: 0px 5px;
		margin-top: 10px;
		font-weight: normal;
		color: #666;
	}

	.pay_infro {
		width: 100%;
		overflow: hidden;
		background-color: #fff;
	}

	.pay_infro ul li {
		padding: 5px;
		line-height: 30px;
		display: block;
		border-bottom: 1px solid #eee;
		font-size: 23rpx;
		overflow: hidden;
	}

	.pay_infro ul li .fl {
		line-height: 30px;
	}

	.pay_infro ul li a {
		display: block;
		overflow: hidden;
		height: 30px;
	}

	.pay_infro ul li .fr span {
		float: left;
		font-size: 21rpx;
	}

	.pay_infro ul li.pay_tb td {
		line-height: 24px;
	}

	.pay_infro ul li.pay_tb td p {
		font-size: 21rpx;
	}

	.pay_infro ul li .fl .red {
		font-size: 21rpx;
		padding-left: 5px;
	}

	.pay_infro ul li .fl .gray {
		padding-left: 10px;
		color: #666;
	}

	.pay_infro .ip_time {
		width: 200px;
		border: 1px solid #ffdcdc;
		height: 30px;
		margin: 5px;
		background-color: #F4EAEA;
		padding-left: 5px;
	}

	.pay_infro ul .time i {
		position: absolute;
		top: 8px;
		left: 200px;
	}

	.add_bd {
		border-top: 2px dashed #ffb8b8;
		border-bottom: 2px dashed #ffb8b8;
	}

	.pay_infro ul li .sel {
		border: 1px solid #ddd;
		padding: 2px;
		margin-left: 5px;
	}

	.pay_infro ul li .sel_p {
		padding-left: 15px;
	}

	.pay_infro ul.invoice li .ip150 {
		width: 150px;
		height: 30px;
		border: 1px solid #ddd;
	}

	.pay_infro ul.invoice a {
		display: inline;
	}

	.pay_infro ul.invoice li span {
		width: 100%;
		display: block;
		float: left;
	}

	.pay_infro ul.invoice li span.gray {
		line-height: 24px;
		padding-bottom: 5px;
	}

	.pay_infro ul.invoice li span.gray a {
		margin-left: 70px;
	}

	.get_dl {
		width: 96%;
		padding: 5px 2%;
	}

	.get_dl dl {
		line-height: 24px;
		padding: 5px;
		background-color: #f8f8f8;
		padding: 5px;
		overflow: hidden;
		margin-top: 5px;
	}

	.get_dl dl dt {
		width: 5%;
		display: block;
		float: left;
	}

	#addr_id {
		float: left;
		margin-top: 5px;
		width: 25px;
	}

	.sendadress {
		color: red;
	}

	/**订单商品列表**/
	.pay_shop {
		width: 100%;
		overflow: hidden;
		background-color: #fff;
		margin-top: 8px;
		border-top: 1px solid #eee;
	}

	.pay_shop h3 {
		margin: 0px 5px;
		height: 40px;
		line-height: 40px;
		white-space: nowrap;
		overflow: hidden;
		font-weight: normal;
		border-bottom: 1px solid #eee;
	}

	.pay_shop h3 .fl {
		margin-right: 5px;
		margin-top: 8px;
	}

	.pay_shop ul {
		padding: 0px 5px;
		overflow: hidden;
		display: block;
	}

	.pay_shop ul li {
		width: 100%;
		display: block;
		overflow: hidden;
		border-bottom: 1px solid #eee;
		padding: 5px 0px;
	}

	.pay_shop ul li .img {
		width: 60px;
		height: 60px;
		margin-right: 5px;
		border: 1px solid #eee;
		display: block;
		float: left;
	}

	.pay_shop ul li .mid {
		width: 50%;
		display: block;
		float: left;
	}

	.pay_shop ul li .mid p {
		font-size: 21rpx;
	}

	.pay_shop ul li .mid p.name {
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
	}

	.pay_shop ul li .fr {
		text-align: right;
		line-height: 24px;
	}

	.pay_shop ul li .fr p {
		font-family: Arial, Helvetica, sans-serif;
	}

	.pay_shop ul li .com_li {
		width: 100%;
		float: left;
		margin-bottom: 3px;
	}

	.pay_shop ul.check_ul li {
		height: 30px;
		line-height: 30px;
		padding: 5px 0px;
		font-size: 23rpx;
	}

	.pay_shop ul.check_ul li .fl .red {
		font-size: 21rpx;
		padding-left: 10px;
	}

	.pay_shop ul.check_ul li input {
		margin-left: 10px;
		width: 170px;
		height: 30px;
		border: 1px solid #ddd;
		text-indent: 5px;
	}

	.pay_shop .money {
		padding: 5px;
		overflow: hidden;
		text-align: right;
		line-height: 30px;
		color: #666;
	}

	.pay_shop .money p span {
		font-family: Arial, Helvetica, sans-serif;
	}

	.total_price {
		width: 100%;
		overflow: hidden;
		background-color: #fff;
		margin-top: 8px;
		border-top: 1px dashed #eee;
		border-bottom: 1px dashed #eee;
	}

	.total_price table {
		width: 100%;
	}

	.total_price table td {
		line-height: 30px;
		padding: 2px 5px;
		border: 1px dashed #eee;
	}

	.order_tt {
		height: 45px;
		line-height: 45px;
	}

	.order_tt .total {
		padding-left: 10px;
		line-height: 45px;
	}

	.order_tt .paymen_btn {
		width: 30%;
		height: 45px;
		line-height: 45px;
	}

	/**填写订单-送货时间**/
	.timer {
		position: absolute;
		top: 45px;
		left: 0px;
		width: 100%;
		background-color: #fff;
		z-index: 4;
		padding-bottom: 10px;
	}

	.timer table {
		width: 100%;
	}

	.timer table th,
	.timer table td {
		border-bottom: 1px solid #eee;
		height: 40px;
		line-height: 40px;
		padding: 0px 5px;
	}

	.timer table th {
		background-color: #f1f1f1;
		font-weight: normal;
	}

	.timer .chose {
		width: 100%;
		display: block;
	}

	/**投诉**/
	.complain {
		width: 100%;
		overflow: hidden;
		padding-bottom: 10px;
	}

	.com_tab {
		border-bottom: 1px solid #eee;
		background-color: #fff;
		overflow: hidden;
		padding: 10px 0px;
	}

	.com_tab ul li {
		width: 20%;
		line-height: 25px;
		display: block;
		float: left;
		text-align: center;
	}

	.com_tab ul li.this {
		color: #E4393C;
	}

	.com_box {
		width: 100%;
		overflow: hidden;
	}

	.com_box h2 {
		font-weight: normal;
		line-height: 30px;
		padding-left: 10px;
		margin-top: 5px;
		color: #000;
	}

	.com_box table {
		width: 100%;
		background-color: #fff;
	}

	.com_box table td,
	.com_box table th {
		border: 1px solid #eee;
		padding: 2px 10px;
		line-height: 30px;
	}

	.com_box table th {
		font-weight: normal;
	}

	.com_box table .th {
		/* background:url("./static/images/title.png") no-repeat left center; */
		padding-left: 10px;
		float: left;
	}

	.com_box table .td_hd {
		background-color: #fafafa;
	}

	.com_box table p {
		width: 170px;
		font-size: 21rpx;
		line-height: 20px;
	}

	.com_box table .name {
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
	}

	.com_box table textarea {
		border: 1px solid #ddd;
		margin: 5px;
		resize: none;
		width: 200px;
		height: 40px;
	}

	.com_box table .radio li {
		border-bottom: 1px dashed #ddd;
		padding-bottom: 5px;
	}

	.com_box table .radio li:last-of-type {
		border-bottom: none;
	}

	.com_box table .radio li p {
		color: #666;
	}

	.com_box table .infro_btn {
		background-color: #f60;
		color: #fff;
		border-radius: 4px;
		padding: 0px 30px;
		float: left;
		margin: 5px 0px;
	}

	.com_piclist {
		padding: 10px;
		display: block;
		background-color: #fff;
		border-top: 1px solid #eee;
	}

	.com_piclist li {
		width: 100%;
		padding: 5px 0px;
		border-bottom: 1px solid #eee;
	}

	.com_piclist li img {
		max-width: 100%;
	}

	.file_box {
		width: 100%;
		overflow: hidden;
		padding-bottom: 5px;
	}

	.file_box ul li {
		width: 64px;
		height: 64px;
		margin: 0px 15px;
		display: block;
		float: left;
		position: relative;
		margin-top: 5px;
	}

	.file_box ul li .file {
		width: 64px;
		height: 64px;
		position: absolute;
		top: 0px;
		left: 0px;
		filter: alpha(opacity=0);
		-moz-opacity: 0;
		-khtml-opacity: 0;
		opacity: 0;
		z-index: 1;
	}

	.file_box ul li .btn {
		width: 64px;
		height: 64px;
		/* background:url("./static/images/file.png") no-repeat; */
		position: absolute;
		top: 0px;
		left: 0px;
		border: none;
	}

	.dialogue {
		width: 100%;
		overflow: hidden;
		padding-bottom: 10px;
	}

	.dialogue dl {
		position: relative;
		border-radius: 4px;
		display: block;
		float: left;
		width: 90%;
		margin: 0px 3%;
		margin-top: 10px;
		padding: 5px;
		line-height: 24px;
	}

	.dialogue dl.other {
		background-color: #fff;
		color: #000;
	}

	.dialogue dl.other dt {
		border-bottom: 1px solid #eee;
	}

	.dialogue dl.im {
		background-color: #ddd;
		float: right;
	}

	.dialogue dl.im dt {
		border-bottom: 1px solid #ccc;
	}

	.dialogue dl .arrow {
		position: absolute;
		top: 5px;
		left: -8px;
	}

	.dialogue dl.im .arrow {
		right: -8px;
		left: inherit;
	}

	.dialogue dl.admin {
		background-color: #f60;
		color: #fff;
	}

	.dialogue dl.admin .arrow {}

	.red_submit .sub {
		width: 30%;
		margin: 0px 5px;
	}

	/**F码购物车**/
	.f_code_cart {
		width: 100%;
		overflow: hidden;
	}

	.f_goods {
		padding: 8px;
		border-top: 1px solid #eee;
		border-bottom: 1px solid #eee;
		background-color: #fff;
		overflow: hidden;
	}

	.f_goods dt {
		width: 60px;
		height: 60px;
		border: 1px solid #eee;
		display: block;
		float: left;
		margin-right: 8px;
	}

	.f_goods dd {
		width: 75%;
		display: block;
		float: left;
	}

	.f_goods dd .fl {
		width: 70%;
	}

	.f_goods dd .fr {
		width: 30%;
	}

	.f_goods dd .fr strong {
		font-size: 23rpx;
		font-family: Arial, Helvetica, sans-serif;
		margin-top: 15px;
		float: right;
	}

	.f_goods dd p {
		font-size: 21rpx;
		margin-bottom: 3px;
	}

	.f_goods dd p.name {
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
	}

	.f_cart {
		height: 45px;
		line-height: 45px;
	}

	.f_cart .paymen_btn {
		height: 45px;
		line-height: 45px;
	}

	.f_cart .f_totail {
		width: 70%;
		float: left;
	}

	.f_cart .f_totail b {
		font-size: 24rpx;
		padding-left: 10px;
	}

	.web_popup {
		position: fixed;
		visibility: hidden;
		z-index: 99;
		font-size: .4375rem;
		top: 50%;
		width: 100%;
		text-align: center;
	}

	.web_popup.active {
		visibility: visible
	}

	.error_w {
		padding: .46875rem .625rem;
		background: rgba(0, 0, 0, .8);
		border-radius: .3125rem;
		text-align: center;
		color: #fff;
		width: 60%;
		margin: 0 auto;
	}

	/**咨询**/
	.consult {
		padding: 10px;
		background-color: #fff;
		overflow: hidden;
	}

	.consult ul {
		width: 100%;
		display: block;
		float: left;
		padding: 5px 0px;
		border-bottom: 1px solid #eee;
	}

	.consult ul:last-of-type {
		border-bottom: 0px;
	}

	.consult ul li {
		width: 100%;
		font-size: 23rpx;
		line-height: 24px;
		display: block;
		float: left;
	}

	.consult ul li.name {
		text-align: right;
	}

	.consult ul li.name span {
		padding-left: 10px;
	}

	.consult ul li.a {
		color: #666;
	}

	.consult ul li strong {
		font-family: Arial, Helvetica, sans-serif;
		font-size: 24rpx;
	}

	/**支付页**/
	.pay_page {
		margin: 5px;
		padding: 5px;
		overflow: hidden;
		background-color: #fff;
		border: 1px solid #eee;
	}

	.pay_page ul li {
		line-height: 40px;
		font-size: 23rpx;
		border-bottom: 1px dashed #eee;
	}

	.pay_page ul li:last-of-type {
		border-bottom: 0px;
	}

	.pay_page ul li.red {
		font-size: 26rpx;
		font-family: Arial, Helvetica, sans-serif;
	}

	/**找回密码**/
	.phone_forget {
		float: left;
		width: 98%;
		margin-left: 1%;
		margin-top: 20px;
	}

	.phone_forget ul {
		width: 98%;
		float: left;
		border: 1px solid #dfdfdf;
		background: #fff;
		border-radius: 5px;
		margin-left: 1%;
	}

	.phone_forget ul li {
		width: 100%;
		float: left;
		height: 45px;
	}

	.phone_forget ul li i {
		float: left;
		height: 35px;
		line-height: 35px;
	}

	.phone_forget ul li span {
		float: left;
		width: 100%;
	}

	.phone_forget ul li span b {
		float: left;
		height: 45px;
		width: 73px;
		margin-left: 10px;
	}

	.phone_forget ul li span em {
		float: left;
		width: 32px;
		height: 32px;
		margin-top: 10px;
		margin-left: 10px;
	}

	.phone_forget ul li span input {
		float: left;
		width: 96%;
		border: none;
		height: 45px;
		line-height: 45px;
		padding: 0 2%;
		color: #999;
		font-size: 23rpx;
	}

	.phone_forget ul li.forget_word1 span {
		padding-left: 2%;
		line-height: 45px;
		height: 40px;
		width: 98%;
		color: #adadad;
		/* background:url("./static/right_on.png") no-repeat 98% center */
	}

	.phone_forget ul li.forget_code span input {
		width: 52%;
	}

	.phone_forget ul li.forget_code1 span input {
		width: 42%;
	}

	.phone_forget ul li.forget_code1 span b {
		float: right;
		height: 30px;
		line-height: 30px;
		margin-top: 8px;
		margin-left: 10px;
		width: 135px;
		margin-right: 10px;
	}

	.phone_forget ul li.forget_code1 span b a {
		color: #fff;
		display: block;
		background: #F40000;
		text-align: center;
		border-radius: 3px;
	}

	.phone_forget ul li.forget_code1 span b a.this {
		color: #ccc;
		display: block;
		background: #fff;
		text-align: center;
		border-radius: 3px;
	}

	.phone_forget_button {
		float: left;
		height: 42px;
		width: 98%;
		margin-left: 1%;
		margin-top: 4%;
		text-align: center;
		line-height: 40px;
	}

	.phone_forget_button a {
		display: block;
		border-radius: 4px;
		border: 1px solid #fac672;
		background: #fff;
		font-size: 23rpx;
		color: #fac672;
	}

	.forget_error {
		float: left;
		width: 96%;
		padding-left: 2%;
		margin-left: 1%;
		height: 35px;
		margin-top: 10px;
		line-height: 35px;
		color: #f00;
		background: #FFDCDC;
		border: 1px solid #FF9696
	}

	/**找回密码end**/

	.other_control {
		width: 100%;
		overflow: hidden;
		background-color: #fff;
	}

	.other_control_ul_li {
		padding: 0px 15px;
		display: block;
		overflow: hidden;
		line-height: 45px;
		border-bottom: 1px solid #ddd;
		font-size: 23rpx;
	}

	.other_control_ul_li navigator {
		display: block;
		overflow: hidden;
	}

	.other_control_ul_li .fl image {
		float: left;
		margin-right: 10px;
		margin-top: 10px;
	}

	.other_control_ul_li .fr image {
		margin-top: 8px;
	}

	.index-box1 {
		width: 94%;
		background: #ffffff;
		border-radius: 20rpx;
		margin-left: 3%;
		margin-top: 20rpx;
		overflow: hidden;
	}

	.search-1 {
		border: #dddddd 2rpx solid;
		border-radius: 80rpx;
		display: flex;
		justify-content: space-between;
	}

	::-webkit-scrollbar {
		width: 0;
		height: 0;
		color: transparent;
	}
</style>