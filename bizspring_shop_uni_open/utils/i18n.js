export async function load_messages() {		this.bizRequest({
		url: '/upms/i18n/message?terminal=shop&language=zh_CN',
		method: 'GET',
	}).then(res => {
		if (res.status == '200') {
			let messages = res.data.data.i18n;
			uni.setStorageSync('shop_messages', JSON.stringify(messages));
		} else {
			uni.showToast({
				title: res.data.message,
				duration: 2000
			});
		}
		this.loading = true;
	}).catch(err => {
		console.error(err);
		uni.showToast({
			title: err,
			duration: 2000
		});
	})
}



module.exports = {
	load_messages,
}
