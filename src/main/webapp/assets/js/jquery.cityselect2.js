/*
Ajax 三级省市联动
日期：2012-7-18

settings 参数说明
-----
url:省市数据josn文件路径
prov:默认省份
city:默认城市
dist:默认地区（县）
dist2:默认地区(县)2
nodata:无数据状态
required:必选项
------------------------------ */

	$.fn.citySelect=function(settings){
		if(this.length<1){return;};

		// 默认值
		settings=$.extend({
			url:"js/city.min.js",
			prov:null,
			city:null,
			dist:null,
			dist2:null,
			nodata:null,
			required:true
		},settings);

		var box_obj=this;
		var prov_obj=box_obj.find(".prov");
		var city_obj=box_obj.find(".city");
		var dist_obj=box_obj.find(".dist");
		var dist_obj2=box_obj.find(".dist2");
		var prov_val=settings.prov;
		var city_val=settings.city;
		var dist_val=settings.dist;
		var dist_val2=settings.dist2;
		var select_prehtml=(settings.required) ? "" : "<option value=''>请选择</option>";
		var city_json;

		// 赋值市级函数
		var cityStart=function(){
			var prov_id=prov_obj.get(0).selectedIndex;
			if(!settings.required){
				prov_id--;
			};
			city_obj.empty().attr("disabled",true);
			dist_obj.empty().attr("disabled",true);
			dist_obj2.empty().attr("disabled",true);

			if(prov_id<0||typeof(city_json.citylist[prov_id].org)=="undefined"){
				if(settings.nodata=="none"){
					city_obj.css("display","none");
					dist_obj.css("display","none");
					dist_obj2.css("display","none");
				}else if(settings.nodata=="hidden"){
					city_obj.css("visibility","hidden");
					dist_obj.css("visibility","hidden");
					dist_obj2.css("visibility","hidden");
				};
				return;
			};
			
			// 遍历赋值市级下拉列表
			temp_html=select_prehtml;
			$.each(city_json.citylist[prov_id].org,function(i,city){
				temp_html+="<option value='"+city.stdOrgCode+"'>"+city.stdOrgName+"</option>";
			});
			city_obj.html(temp_html).attr("disabled",false).css({"display":"","visibility":""});
			distStart();
		};
		var count=0;
		// 赋值地区（县）函数
		var distStart=function(){
			count++;
			var prov_id=prov_obj.get(0).selectedIndex;
			var city_id=city_obj.get(0).selectedIndex;
			if(!settings.required){
				prov_id--;
				city_id--;
			};
			dist_obj.empty().attr("disabled",true);

			if(prov_id<0||city_id<0||typeof(city_json.citylist[prov_id].org[city_id].child)=="undefined"){
				if(settings.nodata=="none"){
					dist_obj.css("display","none");
				}else if(settings.nodata=="hidden"){
					dist_obj.css("visibility","hidden");
				};
				return;
			};
			
			// 遍历赋值市级下拉列表
			temp_html=select_prehtml;
			$.each(city_json.citylist[prov_id].org[city_id].child,function(i,dist){
				temp_html+="<option value='"+dist.stdOrgCode+"'>"+dist.stdOrgName+"</option>";

			});
			dist_obj.html(temp_html).attr("disabled",false).css({"display":"","visibility":""});
			if(count>2){
				dist_obj.trigger("change");
			}
		};
		// 赋值地区（县）2函数
		var count2=0;
		var distStart2=function(){
			console.log("count2: "+count2);
			count2++;
			var prov_id=prov_obj.get(0).selectedIndex;
			var city_id=city_obj.get(0).selectedIndex;
			var dist_id=dist_obj.get(0).selectedIndex;
			if(!settings.required){
				prov_id--;
				city_id--;
				dist_id--;
			};
			dist_obj2.empty().attr("disabled",true);

			if(prov_id<0||city_id<0||typeof(city_json.citylist[prov_id].org[city_id].child)=="undefined"){
				if(settings.nodata=="none"){
					dist_obj2.css("display","none");
				}else if(settings.nodata=="hidden"){
					dist_obj2.css("visibility","hidden");
				};
				return;
			};
			
			// 遍历赋值市级下拉列表
			temp_html=select_prehtml;
			$.each(city_json.citylist[prov_id].org[city_id].child[dist_id].child,function(i,dist2){
				temp_html+="<option value='"+dist2.stdOrgCode+"'>"+dist2.stdOrgName+"</option>";

			});
			dist_obj2.html(temp_html).attr("disabled",false).css({"display":"","visibility":""});
			if(count2>1){
				dist_obj2.trigger("change");
			}
		};
		var init=function(){
			// 遍历赋值省份下拉列表
			temp_html=select_prehtml;
			$.each(city_json.citylist,function(i,prov){
				temp_html+="<option value='"+prov.stdProdLineVerCode+"'>"+prov.stdProdLineVerName+"</option>";
			});
			prov_obj.html(temp_html);

			//若有传入省份与市级的值，则选中。（setTimeout为兼容IE6而设置）
			setTimeout(function(){
				if(settings.prov!=null){
					prov_obj.val(settings.prov);
					cityStart();
					setTimeout(function(){
						if(settings.city!=null){
							city_obj.val(settings.city);
							distStart();
							setTimeout(function(){
								if(settings.dist!=null){
									dist_obj.val(settings.dist);
									distStart2();
									setTimeout(function(){
										if(settings.dist2!=null){
											dist_obj2.val(settings.dist2);
										}
									},1)
								};
							},1);
						};
					},1);
				};
			},1);
			
			// 选择省份时发生事件
			prov_obj.bind("change",function(){
				cityStart();
			});
			// 选择市级时发生事件
			city_obj.bind("change",function(){
				distStart();
			});
			//选择县级时发生的事件
			dist_obj.bind("change",function(){
				distStart2();
			});
		};

		// 设置省市json数据
		if(typeof(settings.url)=="string"){
			$.getJSON(settings.url,function(json){
				city_json=json;
				init();
			});
		}else{
			city_json=settings.url;
			init();
		};
	};
