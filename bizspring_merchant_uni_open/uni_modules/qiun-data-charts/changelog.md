## 2.5.0-20230101（2023-01-01）
- 秋云图表组件 修改条件编译顺序，确保uniapp的cli方式的项目依赖不完整时可以正常显示
- 秋云图表组件 恢复props属性directory的使用，以修复vue3项目中，开启echarts后，echarts目录识别错误的bug
- uCharts.js 修复区域图、混合图只有一个数据时图表显示不正确的bug
- uCharts.js 修复折线图、区域图中时间轴类别图表tooltip指示点显示不正确的bug
- uCharts.js 修复x轴使用labelCount时，并且boundaryGap = 'justify' 并且关闭Y轴显示的时候，最后一个坐标值不显示的bug
- uCharts.js 修复折线图只有一组数据时 ios16 渲染颜色不正确的bug
- uCharts.js 修复玫瑰图半径显示不正确的bug
- uCharts.js 柱状图、山峰图增加正负图功能，y轴网格如果需要显示0轴则由 min max 及 splitNumber 确定，后续版本优化自动显示0轴
- uCharts.js 柱状图column增加 opts.extra.column.labelPosition，数据标签位置，有效值为 outside外部, insideTop内顶部, center内中间, bottom内底部
- uCharts.js 雷达图radar增加 opts.extra.radar.labelShow，否显示各项标识文案是，默认true
- uCharts.js 提示窗tooltip增加 opts.extra.tooltip.boxPadding，提示窗边框填充距离，默认3px
- uCharts.js 提示窗tooltip增加 opts.extra.tooltip.fontSize，提示窗字体大小配置，默认13px
- uCharts.js 提示窗tooltip增加 opts.extra.tooltip.lineHeight，提示窗文字行高，默认20px
- uCharts.js 提示窗tooltip增加 opts.extra.tooltip.legendShow，是否显示左侧图例，默认true
- uCharts.js 提示窗tooltip增加 opts.extra.tooltip.legendShape，图例形状，图例标识样式，有效值为 auto自动跟随图例, diamond◆, circle●, triangle▲, square■, rect▬, line-
- uCharts.js 标记线markLine增加 opts.extra.markLine.labelFontSize，字体大小配置，默认13px
- uCharts.js 标记线markLine增加 opts.extra.markLine.labelPadding，标签边框内填充距离，默认6px
- uCharts.js 折线图line增加 opts.extra.line.linearType，渐变色类型，可选值 none关闭渐变色，custom 自定义渐变色。使用自定义渐变色时请赋值serie.linearColor作为颜色值
- uCharts.js 折线图line增加 serie.linearColor，渐变色数组，格式为2维数组[起始位置，颜色值]，例如[[0,'#0EE2F8'],[0.3,'#2BDCA8'],[0.6,'#1890FF'],[1,'#9A60B4']]
- uCharts.js 折线图line增加 opts.extra.line.onShadow，是否开启折线阴影，开启后请赋值serie.setShadow阴影设置
- uCharts.js 折线图line增加 serie.setShadow，阴影配置，格式为4位数组：[offsetX,offsetY,blur,color]
- uCharts.js 折线图line增加 opts.extra.line.animation，动画效果方向，可选值为vertical 垂直动画效果，horizontal 水平动画效果
- uCharts.js X轴xAxis增加 opts.xAxis.lineHeight，X轴字体行高，默认20px
- uCharts.js X轴xAxis增加 opts.xAxis.marginTop，X轴文字距离轴线的距离，默认0px
- uCharts.js X轴xAxis增加 opts.xAxis.title，当前X轴标题
- uCharts.js X轴xAxis增加 opts.xAxis.titleFontSize，标题字体大小，默认13px
- uCharts.js X轴xAxis增加 opts.xAxis.titleOffsetY，标题纵向偏移距离，负数为向上偏移，正数向下偏移
- uCharts.js X轴xAxis增加 opts.xAxis.titleOffsetX，标题横向偏移距离，负数为向左偏移，正数向右偏移
- uCharts.js X轴xAxis增加 opts.xAxis.titleFontColor，标题字体颜色，默认#666666

## 报错TypeError: Cannot read properties of undefined (reading 'length')
- 如果是uni-modules版本组件，请先登录HBuilderX账号；
- 在HBuilderX中的manifest.json，点击重新获取uniapp的appid，或者删除appid重新粘贴，重新运行；
- 如果是cli项目请使用码云上的非uniCloud版本组件；
- 或者添加uniCloud的依赖；
- 或者使用原生uCharts；
## 2.4.5-20221130（2022-11-30）
- uCharts.js 优化tooltip当文字很多变为左侧显示时，如果画布仍显显示不下，提示框错位置变为以左侧0位置起画
- uCharts.js 折线图修复特殊情况下只有单点数据，并改变线宽后点变为圆形的bug
- uCharts.js 修复Y轴disabled启用后无效并报错的bug
- uCharts.js 修复仪表盘起始结束角度特殊情况下显示不正确的bug
- uCharts.js 雷达图新增参数 opts.extra.radar.radius , 自定义雷达图半径
- uCharts.js 折线图、区域图增加tooltip指示点，opts.extra.line.activeType/opts.extra.area.activeType，可选值"none"不启用激活指示点,"hollow"空心点模式,"solid"实心点模式
## 2.4.4-20221102（2022-11-02）
- 秋云图表组件 修复使用echarts时reload、reshow无法调用重新渲染的bug，[详见码云PR](https://gitee.com/uCharts/uCharts/pulls/40)
- 秋云图表组件 修复使用echarts时，初始化时宽高不正确的bug，[详见码云PR](https://gitee.com/uCharts/uCharts/pulls/42)
- 秋云图表组件 修复uniapp的h5使用history模式时，无法加载echarts的bug
- 秋云图表组件 小程序端@complete、@scrollLeft、@scrollRight、@getTouchStart、@getTouchMove、@getTouchEnd事件增加opts参数传出，方便一些特殊需求的交互获取数据。

- uCharts.js 修复calTooltipYAxisData方法内formatter格式化方法未与y轴方法同步的问题，[详见码云PR](https://gitee.com/uCharts/uCharts/pulls/43)
- uCharts.js 地图新增参数opts.series[i].fillOpacity，以透明度方式来设置颜色过度效果，[详见码云PR](https://gitee.com/uCharts/uCharts/pulls/38)
- uCharts.js 地图新增参数opts.extra.map.active，是否启用点击激活变色
- uCharts.js 地图新增参数opts.extra.map.activeTextColor，是否启用点击激活变色
- uCharts.js 地图新增渲染完成事件renderComplete
- uCharts.js 漏斗图修复当部分数据相同时tooltip提示窗点击错误的bug
- uCharts.js 漏斗图新增参数series.data[i].centerText 居中标签文案
- uCharts.js 漏斗图新增参数series.data[i].centerTextSize 居中标签文案字体大小，默认opts.fontSize
- uCharts.js 漏斗图新增参数series.data[i].centerTextColor 居中标签文案字体颜色，默认#FFFFFF
- uCharts.js 漏斗图新增参数opts.extra.funnel.minSize 最小值的最小宽度，默认0
- uCharts.js 进度条新增参数opts.extra.arcbar.direction，动画方向，可选值为cw顺时针、ccw逆时针
- uCharts.js 混合图新增参数opts.extra.mix.line.width，折线的宽度，默认2
- uCharts.js 修复tooltip开启horizentalLine水平横线标注时，图表显示错位的bug
- uCharts.js 优化tooltip当文字很多变为左侧显示时，如果画布仍显显示不下，提示框错位置变为以左侧0位置起画
- uCharts.js 修复开启滚动条后X轴文字超出绘图区域后的隐藏逻辑
- uCharts.js 柱状图、条状图修复堆叠模式不能通过{value,color}赋值单个柱子颜色的问题
- uCharts.js 气泡图修复不识别series.textSize和series.textColor的bug

## 报错TypeError: Cannot read properties of undefined (reading 'length')
1. 如果是uni-modules版本组件，请先登录HBuilderX账号；
2. 在HBuilderX中的manifest.json，点击重新获取uniapp的appid，或者删除appid重新粘贴，重新运行；
3. 如果是cli项目请使用码云上的非uniCloud版本组件；
4. 或者添加uniCloud的依赖；
5. 或者使用原生uCharts；
## 2.4.3-20220505（2022-05-05）
- 秋云图表组件 修复开启canvas2d后将series赋值为空数组显示加载图标时，再次赋值后画布闪动的bug
- 秋云图表组件 修复升级hbx最新版后ECharts的highlight方法报错的bug
- uCharts.js 雷达图新增参数opts.extra.radar.gridEval，数据点位网格抽希，默认1
- uCharts.js 雷达图新增参数opts.extra.radar.axisLabel，	是否显示刻度点值，默认false
- uCharts.js 雷达图新增参数opts.extra.radar.axisLabelTofix，刻度点值小数位数，默认0
- uCharts.js 雷达图新增参数opts.extra.radar.labelPointShow，是否显示末端刻度圆点，默认false
- uCharts.js 雷达图新增参数opts.extra.radar.labelPointRadius，刻度圆点的半径，默认3
- uCharts.js 雷达图新增参数opts.extra.radar.labelPointColor，刻度圆点的颜色，默认#cccccc
- uCharts.js 雷达图新增参数opts.extra.radar.linearType，渐变色类型，可选值"none"关闭渐变,"custom"开启渐变
- uCharts.js 雷达图新增参数opts.extra.radar.customColor，自定义渐变颜色，数组类型对应series的数组长度以匹配不同series颜色的不同配色方案，例如["#FA7D8D", "#EB88E2"]
- uCharts.js 雷达图优化支持series.textColor、series.textSize属性
- uCharts.js 柱状图中温度计式图标，优化支持全圆角类型，修复边框有缝隙的bug，详见官网【演示】中的温度计图表
- uCharts.js 柱状图新增参数opts.extra.column.activeWidth，当前点击柱状图的背景宽度，默认一个单元格单位
- uCharts.js 混合图增加opts.extra.mix.area.gradient 区域图是否开启渐变色
- uCharts.js 混合图增加opts.extra.mix.area.opacity 区域图透明度，默认0.2
- uCharts.js 饼图、圆环图、玫瑰图、漏斗图，增加opts.series[0].data[i].labelText，自定义标签文字，避免formatter格式化的繁琐，详见官网【演示】中的饼图
- uCharts.js 饼图、圆环图、玫瑰图、漏斗图，增加opts.series[0].data[i].labelShow，自定义是否显示某一个指示标签，避免因饼图类别太多导致标签重复或者居多导致图形变形的问题，详见官网【演示】中的饼图
- uCharts.js 增加opts.series[i].legendText/opts.series[0].data[i].legendText（与series.name同级）自定义图例显示文字的方法
- uCharts.js 优化X轴、Y轴formatter格式化方法增加形参，统一为fromatter:function(value,index,opts){}
- uCharts.js 修复横屏模式下无法使用双指缩放方法的bug
- uCharts.js 修复当只有一条数据或者多条数据值相等的时候Y轴自动计算的最大值错误的bug
- 【官网模板】增加外部自定义图例与图表交互的例子，[点击跳转](https://www.ucharts.cn/v2/#/layout/info?id=2)

## 注意：非unimodules 版本如因更新 hbx 至 3.4.7 导致报错如下，请到码云更新非 unimodules 版本组件，[点击跳转](https://gitee.com/uCharts/uCharts/tree/master/uni-app/uCharts-%E7%BB%84%E4%BB%B6)
> Error in callback for immediate watcher "uchartsOpts": "SyntaxError: Unexpected token u in JSON at position 0"
## 2.4.2-20220421（2022-04-21）
- 秋云图表组件 修复HBX升级3.4.6.20220420版本后echarts报错的问题
## 2.4.2-20220420（2022-04-20）
## 重要！此版本uCharts新增了很多功能，修复了诸多已知问题
- 秋云图表组件 新增onzoom开启双指缩放功能（仅uCharts），前提需要直角坐标系类图表类型，并且ontouch为true、opts.enableScroll为true，详见实例项目K线图
- 秋云图表组件 新增optsWatch是否监听opts变化，关闭optsWatch后，动态修改opts不会触发图表重绘
- 秋云图表组件 修复开启canvas2d功能后，动态更新数据后画布闪动的bug
- 秋云图表组件 去除directory属性，改为自动获取echarts.min.js路径（升级不受影响）
- 秋云图表组件 增加getImage()方法及@getImage事件，通过ref调用getImage()方法获，触发@getImage事件获取当前画布的base64图片文件流。
- 秋云图表组件 支付宝、字节跳动、飞书、快手小程序支持开启canvas2d同层渲染设置。
- 秋云图表组件 新增加【非uniCloud】版本组件，避免有些不需要uniCloud的使用组件发布至小程序需要提交隐私声明问题，请到码云[【非uniCloud版本】](https://gitee.com/uCharts/uCharts/tree/master/uni-app/uCharts-%E7%BB%84%E4%BB%B6)，或npm[【非uniCloud版本】](https://www.npmjs.com/package/@qiun/uni-ucharts)下载使用。
- uCharts.js 新增dobuleZoom双指缩放功能
- uCharts.js 新增山峰图type="mount"，数据格式为饼图类格式，不需要传入categories，具体详见新版官网在线演示
- uCharts.js 修复折线图当数据中存在null时tooltip报错的bug
- uCharts.js 修复饼图类当画布比较小时自动计算的半径是负数报错的bug
- uCharts.js 统一各图表类型的series.formatter格式化方法的形参为(val, index, series, opts)，方便格式化时有更多参数可用
- uCharts.js 标记线功能增加labelText自定义显示文字，增加labelAlign标签显示位置（左侧或右侧），增加标签显示位置微调labelOffsetX、labelOffsetY
- uCharts.js 修复条状图当数值很小时开启圆角后样式错误的bug
- uCharts.js 修复X轴开启disabled后，X轴仍占用空间的bug
- uCharts.js 修复X轴开启滚动条并且开启rotateLabel后，X轴文字与滚动条重叠的bug
- uCharts.js 增加X轴rotateAngle文字旋转自定义角度，取值范围(-90至90)
- uCharts.js 修复地图文字标签层级显示不正确的bug
- uCharts.js 修复饼图、圆环图、玫瑰图当数据全部为0的时候不显示数据标签的bug
- uCharts.js 修复当opts.padding上边距为0时，Y轴顶部刻度标签位置不正确的bug

## 另外我们还开发了各大原生小程序组件，已发布至码云和npm
[https://gitee.com/uCharts/uCharts](https://gitee.com/uCharts/uCharts)
[https://www.npmjs.com/~qiun](https://www.npmjs.com/~qiun)

## 对于原生uCharts文档我们已上线新版官方网站，详情点击下面链接进入官网
[https://www.uCharts.cn/v2/](https://www.ucharts.cn/v2/)
## 2.3.7-20220122（2022-01-22）
## 重要！使用vue3编译，请使用cli模式并升级至最新依赖，HbuilderX编译需要使用3.3.8以上版本
- uCharts.js 修复uni-app平台组件模式使用vue3编译到小程序报错的bug。
## 2.3.7-20220118（2022-01-18）
## 注意，使用vue3的前提是需要3.3.8.20220114-alpha版本的HBuilder！
## 2.3.67-20220118（2022-01-18）
- 秋云图表组件 组件初步支持vue3，全端编译会有些问题，具体详见下面修改：
1. 小程序端运行时，在uni_modules文件夹的qiun-data-charts.js中搜索 new uni_modules_qiunDataCharts_js_sdk_uCharts_uCharts.uCharts，将.uCharts去掉。
2. 小程序端发行时，在uni_modules文件夹的qiun-data-charts.js中搜索 new e.uCharts，将.uCharts去掉，变为 new e。
3. 如果觉得上述步骤比较麻烦，如果您的项目只编译到小程序端，可以修改u-charts.js最后一行导出方式，将 export default uCharts;变更为 export default { uCharts: uCharts }; 这样变更后，H5和App端的renderjs会有问题，请开发者自行选择。（此问题非组件问题，请等待DC官方修复Vue3的小程序端）
## 2.3.6-20220111（2022-01-11）
- 秋云图表组件 修改组件 props 属性中的 background 默认值为 rgba(0,0,0,0)
## 2.3.6-20211201（2021-12-01）
- uCharts.js 修复bar条状图开启圆角模式时，值很小时圆角渲染错误的bug
## 2.3.5-20211014（2021-10-15）
- uCharts.js 增加vue3的编译支持（仅原生uCharts，qiun-data-charts组件后续会支持，请关注更新）
## 2.3.4-20211012（2021-10-12）
- 秋云图表组件 修复 mac os x 系统 mouseover 事件丢失的 bug
## 2.3.3-20210706（2021-07-06）
- uCharts.js 增加雷达图开启数据点值（opts.dataLabel）的显示
## 2.3.2-20210627（2021-06-27）
- 秋云图表组件 修复tooltipCustom个别情况下传值不正确报错TypeError: Cannot read property 'name' of undefined的bug
## 2.3.1-20210616（2021-06-16）
- uCharts.js 修复圆角柱状图使用4角圆角时，当数值过大时不正确的bug
## 2.3.0-20210612（2021-06-12）
- uCharts.js 【重要】uCharts增加nvue兼容，可在nvue项目中使用gcanvas组件渲染uCharts，[详见码云uCharts-demo-nvue](https://gitee.com/uCharts/uCharts)
- 秋云图表组件 增加tapLegend属性，是否开启图例点击交互事件
- 秋云图表组件 getIndex事件中增加返回uCharts实例中的opts参数，以便在页面中调用参数
- 示例项目 pages/other/other.vue增加app端自定义tooltip的方法，详见showOptsTooltip方法
## 2.2.1-20210603（2021-06-03）
- uCharts.js 修复饼图、圆环图、玫瑰图，当起始角度不为0时，tooltip位置不准确的bug
- uCharts.js 增加温度计式柱状图开启顶部半圆形的配置
## 2.2.0-20210529（2021-05-29）
- uCharts.js 增加条状图type="bar"
- 示例项目 pages/ucharts/ucharts.vue增加条状图的demo
## 2.1.7-20210524（2021-05-24）
- uCharts.js 修复大数据量模式下曲线图不平滑的bug
## 2.1.6-20210523（2021-05-23）
- 秋云图表组件 修复小程序端开启滚动条更新数据后滚动条位置不符合预期的bug
## 2.1.5-2021051702（2021-05-17）
- uCharts.js 修复自定义Y轴min和max值为0时不能正确显示的bug
## 2.1.5-20210517（2021-05-17）
- uCharts.js 修复Y轴自定义min和max时，未按指定的最大值最小值显示坐标轴刻度的bug
## 2.1.4-20210516（2021-05-16）
- 秋云图表组件 优化onWindowResize防抖方法
- 秋云图表组件 修复APP端uCharts更新数据时，清空series显示loading图标后再显示图表，图表抖动的bug
- uCharts.js 修复开启canvas2d后，x轴、y轴、series自定义字体大小未按比例缩放的bug
- 示例项目 修复format-e.vue拼写错误导致app端使用uCharts渲染图表
## 2.1.3-20210513（2021-05-13）
- 秋云图表组件 修改uCharts变更chartData数据为updateData方法，支持带滚动条的数据动态打点
- 秋云图表组件 增加onWindowResize防抖方法 fix by ど誓言，如尘般染指流年づ 
- 秋云图表组件 H5或者APP变更chartData数据显示loading图表时，原数据闪现的bug
- 秋云图表组件 props增加errorReload禁用错误点击重新加载的方法
- uCharts.js 增加tooltip显示category（x轴对应点位）标题的功能，opts.extra.tooltip.showCategory，默认为false
- uCharts.js 修复mix混合图只有柱状图时，tooltip的分割线显示位置不正确的bug
- uCharts.js 修复开启滚动条，图表在拖动中动态打点，滚动条位置不正确的bug
- uCharts.js 修复饼图类数据格式为echarts数据格式，series为空数组报错的bug
- 示例项目 修改uCharts.js更新到v2.1.2版本后，@getIndex方法获取索引值变更为e.currentIndex.index
- 示例项目 pages/updata/updata.vue增加滚动条拖动更新（数据动态打点）的demo
- 示例项目 pages/other/other.vue增加errorReload禁用错误点击重新加载的demo
## 2.1.2-20210509（2021-05-09）
秋云图表组件 修复APP端初始化时就传入chartData或lacaldata不显示图表的bug
## 2.1.1-20210509（2021-05-09）
- 秋云图表组件 变更ECharts的eopts配置在renderjs内执行，支持在config-echarts.js配置文件内写function配置。
- 秋云图表组件 修复APP端报错Prop being mutated: "onmouse"错误的bug。
- 秋云图表组件 修复APP端报错Error: Not Found：Page[6][-1,27] at view.umd.min.js:1的bug。
## 2.1.0-20210507（2021-05-07）
- 秋云图表组件 修复初始化时就有数据或者数据更新的时候loading加载动画闪动的bug
- uCharts.js 修复x轴format方法categories为字符串类型时返回NaN的bug
- uCharts.js 修复series.textColor、legend.fontColor未执行全局默认颜色的bug
## 2.1.0-20210506（2021-05-06）
- 秋云图表组件 修复极个别情况下报错item.properties undefined的bug
- 秋云图表组件 修复极个别情况下关闭加载动画reshow不起作用，无法显示图表的bug
- 示例项目 pages/ucharts/ucharts.vue 增加时间轴折线图（type="tline"）、时间轴区域图（type="tarea"）、散点图（type="scatter"）、气泡图demo（type="bubble"）、倒三角形漏斗图（opts.extra.funnel.type="triangle"）、金字塔形漏斗图（opts.extra.funnel.type="pyramid"）
- 示例项目 pages/format-u/format-u.vue 增加X轴format格式化示例
- uCharts.js 升级至v2.1.0版本
- uCharts.js 修复 玫瑰图面积模式点击tooltip位置不正确的bug
- uCharts.js 修复 玫瑰图点击图例，只剩一个类别显示空白的bug
- uCharts.js 修复 饼图类图点击图例，其他图表tooltip位置某些情况下不准的bug
- uCharts.js 修复 x轴为矢量轴（时间轴）情况下，点击tooltip位置不正确的bug
- uCharts.js 修复 词云图获取点击索引偶尔不准的bug
- uCharts.js 增加 直角坐标系图表X轴format格式化方法（原生uCharts.js用法请使用formatter）
- uCharts.js 增加 漏斗图扩展配置，倒三角形（opts.extra.funnel.type="triangle"），金字塔形（opts.extra.funnel.type="pyramid"）
- uCharts.js 增加 散点图（opts.type="scatter"）、气泡图（opts.type="bubble"）
- 后期计划 完善散点图、气泡图，增加markPoints标记点，增加横向条状图。
## 2.0.0-20210502（2021-05-02）
- uCharts.js 修复词云图获取点击索引不正确的bug
## 2.0.0-20210501（2021-05-01）
- 秋云图表组件 修复QQ小程序、百度小程序在关闭动画效果情况下，v-for循环使用图表，显示不正确的bug
## 2.0.0-20210426（2021-04-26）
- 秋云图表组件 修复QQ小程序不支持canvas2d的bug
- 秋云图表组件 修复钉钉小程序某些情况点击坐标计算错误的bug
- uCharts.js 增加 extra.column.categoryGap 参数，柱状图类每个category点位（X轴点）柱子组之间的间距
- uCharts.js 增加 yAxis.data[i].titleOffsetY 参数，标题纵向偏移距离，负数为向上偏移，正数向下偏移
- uCharts.js 增加 yAxis.data[i].titleOffsetX 参数，标题横向偏移距离，负数为向左偏移，正数向右偏移
- uCharts.js 增加 extra.gauge.labelOffset 参数，仪表盘标签文字径向便宜距离，默认13px
## 2.0.0-20210422-2（2021-04-22）
秋云图表组件 修复 formatterAssign 未判断 args[key] == null 的情况导致栈溢出的 bug
## 2.0.0-20210422（2021-04-22）
- 秋云图表组件 修复H5、APP、支付宝小程序、微信小程序canvas2d模式下横屏模式的bug
## 2.0.0-20210421（2021-04-21）
- uCharts.js 修复多行图例的情况下，图例在上方或者下方时，图例float为左侧或者右侧时，第二行及以后的图例对齐方式不正确的bug
## 2.0.0-20210420（2021-04-20）
- 秋云图表组件 修复微信小程序开启canvas2d模式后，windows版微信小程序不支持canvas2d模式的bug
- 秋云图表组件 修改非uni_modules版本为v2.0版本qiun-data-charts组件
## 2.0.0-20210419（2021-04-19）
## v1.0版本已停更，建议转uni_modules版本组件方式调用，点击右侧绿色【使用HBuilderX导入插件】即可使用，示例项目请点击右侧蓝色按钮【使用HBuilderX导入示例项目】。
## 初次使用如果提示未注册&lt;qiun-data-charts&gt;组件，请重启HBuilderX，如仍不好用，请重启电脑；
## 如果是cli项目，请尝试清理node_modules，重新install，还不行就删除项目，再重新install。
## 此问题已于DCloud官方确认，HBuilderX下个版本会修复。
## 其他图表不显示问题详见[常见问题选项卡](https://demo.ucharts.cn)
## <font color=#FF0000> 新手请先完整阅读帮助文档及常见问题3遍，右侧蓝色按钮示例项目请看2遍！ </font> 
## [DEMO演示及在线生成工具（v2.0文档）https://demo.ucharts.cn](https://demo.ucharts.cn)
## [图表组件在项目中的应用参见 UReport数据报表](https://ext.dcloud.net.cn/plugin?id=4651) 
- uCharts.js 修复混合图中柱状图单独设置颜色不生效的bug
- uCharts.js 修复多Y轴单独设置fontSize时，开启canvas2d后，未对应放大字体的bug
## 2.0.0-20210418（2021-04-18）
- 秋云图表组件 增加directory配置，修复H5端history模式下如果发布到二级目录无法正确加载echarts.min.js的bug
## 2.0.0-20210416（2021-04-16）
## v1.0版本已停更，建议转uni_modules版本组件方式调用，点击右侧绿色【使用HBuilderX导入插件】即可使用，示例项目请点击右侧蓝色按钮【使用HBuilderX导入示例项目】。
## 初次使用如果提示未注册&lt;qiun-data-charts&gt;组件，请重启HBuilderX，如仍不好用，请重启电脑；
## 如果是cli项目，请尝试清理node_modules，重新install，还不行就删除项目，再重新install。
## 此问题已于DCloud官方确认，HBuilderX下个版本会修复。
## 其他图表不显示问题详见[常见问题选项卡](https://demo.ucharts.cn)
## <font color=#FF0000> 新手请先完整阅读帮助文档及常见问题3遍，右侧蓝色按钮示例项目请看2遍！ </font> 
## [DEMO演示及在线生成工具（v2.0文档）https://demo.ucharts.cn](https://demo.ucharts.cn)
## [图表组件在项目中的应用参见 UReport数据报表](https://ext.dcloud.net.cn/plugin?id=4651) 
- 秋云图表组件 修复APP端某些情况下报错`Not Found Page`的bug，fix by 高级bug开发技术员
- 示例项目 修复APP端v-for循环某些情况下报错`Not Found Page`的bug，fix by 高级bug开发技术员
- uCharts.js 修复非直角坐标系tooltip提示窗右侧超出未变换方向显示的bug
## 2.0.0-20210415（2021-04-15）
- 秋云图表组件 修复H5端发布到二级目录下echarts无法加载的bug
- 秋云图表组件 修复某些情况下echarts.off('finished')移除监听事件报错的bug
## 2.0.0-20210414（2021-04-14）
## v1.0版本已停更，建议转uni_modules版本组件方式调用，点击右侧绿色【使用HBuilderX导入插件】即可使用，示例项目请点击右侧蓝色按钮【使用HBuilderX导入示例项目】。
## 初次使用如果提示未注册&lt;qiun-data-charts&gt;组件，请重启HBuilderX，如仍不好用，请重启电脑；
## 如果是cli项目，请尝试清理node_modules，重新install，还不行就删除项目，再重新install。
## 此问题已于DCloud官方确认，HBuilderX下个版本会修复。
## 其他图表不显示问题详见[常见问题选项卡](https://demo.ucharts.cn)
## <font color=#FF0000> 新手请先完整阅读帮助文档及常见问题3遍，右侧蓝色按钮示例项目请看2遍！ </font> 
## [DEMO演示及在线生成工具（v2.0文档）https://demo.ucharts.cn](https://demo.ucharts.cn)
## [图表组件在项目中的应用参见 UReport数据报表](https://ext.dcloud.net.cn/plugin?id=4651) 
- 秋云图表组件 修复H5端在cli项目下ECharts引用地址错误的bug
- 示例项目 增加ECharts的formatter用法的示例(详见示例项目format-e.vue)
- uCharts.js 增加圆环图中心背景色的配置extra.ring.centerColor
- uCharts.js 修复微信小程序安卓端柱状图开启透明色后显示不正确的bug
## 2.0.0-20210413（2021-04-13）
- 秋云图表组件 修复百度小程序多个图表真机未能正确获取根元素dom尺寸的bug
- 秋云图表组件 修复百度小程序横屏模式方向不正确的bug
- 秋云图表组件 修改ontouch时，@getTouchStart@getTouchMove@getTouchEnd的触发条件
- uCharts.js 修复饼图类数据格式series属性不生效的bug
- uCharts.js 增加时序区域图 详见示例项目中ucharts.vue
## 2.0.0-20210412-2（2021-04-12）
## v1.0版本已停更，建议转uni_modules版本组件方式调用，点击右侧绿色【使用HBuilderX导入插件】即可使用，示例项目请点击右侧蓝色按钮【使用HBuilderX导入示例项目】。
## 初次使用如果提示未注册&lt;qiun-data-charts&gt;组件，请重启HBuilderX。如仍不好用，请重启电脑，此问题已于DCloud官方确认，HBuilderX下个版本会修复。
## [DEMO演示及在线生成工具（v2.0文档）https://demo.ucharts.cn](https://demo.ucharts.cn)
## [图表组件在uniCloudAdmin中的应用 UReport数据报表](https://ext.dcloud.net.cn/plugin?id=4651) 
- 秋云图表组件 修复uCharts在APP端横屏模式下不能正确渲染的bug
- 示例项目 增加ECharts柱状图渐变色、圆角柱状图、横向柱状图（条状图）的示例
## 2.0.0-20210412（2021-04-12）
- 秋云图表组件 修复created中判断echarts导致APP端无法识别，改回mounted中判断echarts初始化
- uCharts.js 修复2d模式下series.textOffset未乘像素比的bug
## 2.0.0-20210411（2021-04-11）
## v1.0版本已停更，建议转uni_modules版本组件方式调用，点击右侧绿色【使用HBuilderX导入插件】即可使用，示例项目请点击右侧蓝色按钮【使用HBuilderX导入示例项目】。
## 初次使用如果提示未注册<qiun-data-charts>组件，请重启HBuilderX，并清空小程序开发者工具缓存。
## [DEMO演示及在线生成工具（v2.0文档）https://demo.ucharts.cn](https://demo.ucharts.cn)
## [图表组件在uniCloudAdmin中的应用 UReport数据报表](https://ext.dcloud.net.cn/plugin?id=4651) 
- uCharts.js 折线图区域图增加connectNulls断点续连的功能，详见示例项目中ucharts.vue
- 秋云图表组件 变更初始化方法为created，变更type2d默认值为true，优化2d模式下组件初始化后dom获取不到的bug
- 秋云图表组件 修复左右布局时，右侧图表点击坐标错误的bug，修复tooltip柱状图自定义颜色显示object的bug
## 2.0.0-20210410（2021-04-10）
- 修复左右布局时，右侧图表点击坐标错误的bug，修复柱状图自定义颜色tooltip显示object的bug
- 增加标记线及柱状图自定义颜色的demo
## 2.0.0-20210409（2021-04-08）
## v1.0版本已停更，建议转uni_modules版本组件方式调用，点击右侧【使用HBuilderX导入插件】即可体验，DEMO演示及在线生成工具（v2.0文档）[https://demo.ucharts.cn](https://demo.ucharts.cn)
## 图表组件在uniCloudAdmin中的应用 [UReport数据报表](https://ext.dcloud.net.cn/plugin?id=4651) 
- uCharts.js 修复钉钉小程序百度小程序measureText不准确的bug，修复2d模式下饼图类activeRadius为按比例放大的bug
- 修复组件在支付宝小程序端点击位置不准确的bug
## 2.0.0-20210408（2021-04-07）
- 修复组件在支付宝小程序端不能显示的bug（目前支付宝小程不能点击交互，后续修复）
- uCharts.js 修复高分屏下柱状图类，圆弧进度条 自定义宽度不能按比例放大的bug
## 2.0.0-20210407（2021-04-06）
## v1.0版本已停更，建议转uni_modules版本组件方式调用，点击右侧【使用HBuilderX导入插件】即可体验，DEMO演示及在线生成工具（v2.0文档）[https://demo.ucharts.cn](https://demo.ucharts.cn)
## 增加 通过tofix和unit快速格式化y轴的demo add by `howcode`
## 增加 图表组件在uniCloudAdmin中的应用 [UReport数据报表](https://ext.dcloud.net.cn/plugin?id=4651) 
## 2.0.0-20210406（2021-04-05）
# 秋云图表组件+uCharts v2.0版本同步上线，使用方法详见https://demo.ucharts.cn帮助页
## 2.0.0（2021-04-05）
# 秋云图表组件+uCharts v2.0版本同步上线，使用方法详见https://demo.ucharts.cn帮助页
