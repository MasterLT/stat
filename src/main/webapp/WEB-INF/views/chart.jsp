<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">

	<head>
		<base href="<%=basePath%>">
		<meta charset="utf-8">
		<title>区域分析</title>
		<style>
			body {
				font-family: Microsoft YaHei, Menlo, Consolas, monospace;
				color: #444;
				font-size: 14px;
			}

			.nav1,
			.nav2,
			.nav3,
			.nav4,
			.nav5,
			.nav6 {
				position: absolute;
				height: 18px;
				line-height: 18px;
				margin-top: -6px;
				color: #000;
				background-color: #AECC7A;
				font-family: Microsoft YaHei, Menlo, Consolas, monospace;
			}

			.nav1 {
				background-color: #F09700;
			}

			.nav3 {
				background-color: #AECC7A;
			}

			.nav4 {
				background-color: #AECC7A;
				border: 2px solid #FDEF06;
			}

			.nav5 {
				background-color: #AFA276;
			}

			.nav6 {
				background-color: #F0CD99;
			}

			.nav2 .navP {
				color: #fff;
				background: #FD5810;
				padding: 0 2px;
			}

			.nav1 .navP {
				color: #fff;
				background: #00923A;
				padding: 0 2px;
			}

			.nav3 .navP {
				color: #fff;
				background: #FD5810;
				padding: 0 2px;
			}

			.nav4 .navP {
				color: #fff;
				background: #FD5C10;
				padding: 0 2px;
			}

			.nav5 .navP {
				color: #fff;
				background: #00923A;
				padding: 0 2px;
			}

			.nav6 .navP {
				color: #fff;
				background: #00923A;
				padding: 0 2px;
			}

			.nav1 .navU,
			.nav2 .navU,
			.nav3 .navU,
			.nav4 .navU,
			.nav5 .navU,
			.nav6 .navU {
				color: #fff;
				background: #3487F4;
				padding: 0 2px;
			}

			.tagLB,
			.tagLT,
			.tagRB,
			.tagRT {
				width: 240px;
				border: 3px solid #217DFD;
				border-radius: 10px;
				position: absolute;
				left: 0;
				top: 36px;
				padding: 10px 15px;
				z-index: 1111111;
				background-color: #CDD6DB;
				font-family: Microsoft YaHei, Menlo, Consolas, monospace;
			}

			.tagLT {
				top: auto;
				bottom: 40px;
			}

			.tagRB {
				left: auto;
				right: 0;
			}

			.tagRT {
				top: auto;
				bottom: 40px;
				left: auto;
				right: 0;
			}

			.arrow {
				position: absolute;
				width: 40px;
				height: 40px;
				top: -40px;
				left: 30px;
			}

			.tagLT .arrow {
				top: auto;
				bottom: -40px;
			}

			.tagRB .arrow {
				left: auto;
				right: 30px;
			}

			.tagRT .arrow {
				top: auto;
				bottom: -40px;
				left: auto;
				right: 30px;
			}

			.arrow * {
				display: block;
				border-width: 20px;
				position: absolute;
				border-style: solid dashed dashed dashed;
				font-size: 0;
				line-height: 0;
			}

			.arrow em {
				border-color: transparent transparent #217DFD;
			}

			.tagLT .arrow em,
			.tagRT .arrow em {
				border-color: #217DFD transparent transparent;
			}

			.arrow span {
				border-color: transparent transparent #CDD6DB;
				top: 4px;
			}

			.tagLT .arrow span,
			.tagRT .arrow span {
				border-color: #CDD6DB transparent transparent;
				top: -4px;
			}

			.fade-enter-active,
			.fade-leave-active {
				transition: opacity .5s
			}

			.fade-enter,
			.fade-leave-to
				/* .fade-leave-active 在低于版本 2.1.8 中 */

			{
				opacity: 0
			}

		</style>
		<!-- Delete ".min" for console warnings in development -->
		<script type="text/javascript" src="js/vue.js"></script>
		<script src="js/axios.min.js"></script>
		<script src="js/lodash.min.js"></script>
		<script src="assets/js/jquery-2.0.3.min.js"></script>

	</head>

	<body bgcolor="transparent" style="background-repeat: no-repeat; font-family: '黑体';">
		<div id="app">
			<nav-tpl v-for="item in navs" :mnames="item.list" :key="item.id" :mid="item.id" :mpv="item.pv" :muv="item.uv" :mleft="item.left" v-bind:mtop="item.top">
				</nav-tpl>
		</div>
		<template id="nav_tpl">
			<div :id="mid" :class="subId" @mouseenter="popup" @mouseleave="popup" v-bind:style="{left:mleft+'px',top:computeTop+'px'}"><span class="navP">P</span>{{ format(mpv) }}<span class="navU">U</span>{{ format(muv) }}
				<!-- nav1 nav2 nav3 nav4 nav5 nav6 -->
				<div v-show="isShow" :class="computeTag">
					<!--tagLB:左下 tagLT:左上 tagRB:右下 tagRT:右上-->
					<div class="arrow"><em></em><span></span></div>
					<template v-for="nitem in mnames">
						{{ nitem.name+' PV：'+nitem.pv+' UV：'+nitem.uv }}<br />
					</template>
				</div>
			</div>
		</template>
	<input id="beginDate" name="beginDate" value="${beginDate}" type="hidden">
		<input id="endDate" name="endDate" value="${endDate}" type="hidden">
		<input id="region" name="region" value="${region}" type="hidden">
		<script type="text/javascript">
			//#用组件构建（应用）,必须在new Vue前定义
			Vue.component('nav-tpl', {
				template: '#nav_tpl',
				props: ['mid', 'mleft', 'mtop', 'showed', 'mpv', 'muv', 'muv', 'mnames'], //不能用key
				data: function() {
					return {
						isShow: false
					}
				},
				computed: {
					subId: function() {
						return this.mid.length > 4 ? this.mid.substring(0, 4) : this.mid;
					},
					computeTag: function() {
						var left = Number(this.mleft);
						var top = Number(this.mtop);
						return left >= 640 ? (top >= 360 ? 'tagRT' : 'tagRB') : (top >= 360 ? 'tagLT' : 'tagLB');
					},
					computeTop: function() {
						if(this.mid.indexOf('nav1_') >= 0 && Number(this.mid.substring(5)) % 2 == 1) {
							return Number(this.mtop) + 20;
						}
						return this.mtop;
					}
				},
				methods: {
					format: function(digitStr) {
						var arr = digitStr.split('');
						if(arr.length < 4)
							return digitStr;
						if(arr.length > 5)
							return arr.slice(0, -4).join('') + 'w';
						return(arr[1] == '0' ? arr[0] : arr.slice(0, 2).join('.')) + (arr.length == 4 ? 'k' : 'w');
					},
					popup: function() {
						if(this.isShow) {
							var self = this;
							setTimeout(function() {
								self.isShow = !self.isShow;
							}, 300)
						} else {
							this.isShow = !this.isShow;

						}
					}
				}
			})

			//--------------------
			var app = new Vue({
				el: '#app',
				data: {
					serverUrl: '<%=basePath%>data/getIndexData/',
					pngUrl: '${mapDataUrl}',
					regionKey: '${region}', //hebei
					endDate:'${endDate}',
					message: 'Hello Vue!',
					navs: []
				},
				methods: {
					loadData: function(url) {
						var vm = this;
						axios.get(url)
							.then(function(response) {
								vm.navs = response.data;
							})
							.catch(function(error) {
								vm.message = 'Error! Could not reach the API. ' + error
							})
					}
				},
				created: function() {
					var rkey = $('#region').val();
					if(rkey) {
						this.regionKey = rkey;
					}
					var url = this.pngUrl + this.regionKey + "-" +this.endDate + ".png";
					document.body.style.backgroundImage = "url(" + url + ")";
				},
				mounted: function() {
					this.$nextTick(function() {

						var beginDate = $('#beginDate').val() == null ? null : $('#beginDate').val();
						var endDate = $('#endDate').val() == null ? null : $('#endDate').val();
						var region = $('#region').val();
						var url = this.serverUrl + region + "?beginDate=" + beginDate + "&endDate=" + endDate;
						this.loadData(url);
					})
				}
			})
		</script>
	</body>

</html>