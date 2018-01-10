<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<base href="<%=basePath%>">
<%@ include file="inc/ace_head.inc" %>
<script type="text/javascript" src="assets/styles/js/head.js"></script>
<script type="text/javascript" src="js/daterangepicker.js"></script>
<link rel="stylesheet" href="js/daterangepicker-bs3.css">
<script type="text/javascript" src="js/moment.js"></script>
<script src="assets/js/date-time/moment.min.js"></script>
<script src="assets/js/date-time/daterangepicker.min.js"></script>

<style>
    body {
        background-color: Transparent;
    }

    .table {
        font-size: 14px;
    }

    a {
        cursor: pointer;
    }

    .pagination > li > a > i {
        line-height: 18px;
    }

    .daterangepicker .ranges {
        width: 175px;
    }

    .daterangepicker .ranges .input-mini {
        width: 80px;
    }
</style>
<div class="col-xs-12" style="padding-top: 32px;">
    <!-- 	<h3 class="header smaller lighter blue">主页</h3> -->
    <div class="form-horizontal">
        <!-- 按区域查询 -->
        <div id="areaQuery" class="form-group" style="display: block;">
            <div class="col-xs-1" style="text-align: right;">
                <label class="control-label no-padding-right"
                       style="margin-right: -12px">行政区:</label>
            </div>
            <div class="col-xs-1">
                <select id="district" class="form-control combox" name="district">
                    <c:forEach items="${dis}" var="dDto">
                        <option value="${dDto.value}"
                                <c:if test="${district == dDto.value}">selected="selected"</c:if>>${dDto.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-xs-1" style="text-align: right;">
                <label class="control-label no-padding-right"
                       style="margin-right: -12px">关键词:</label>
            </div>
            <div class="col-xs-2">
                <input id="keyword" class="form-control input"/>
            </div>

            <div class="col-xs-1" style="text-align: right;">
                <label class="control-label no-padding-right"
                       style="margin-right: -12px">查询类型:</label>
            </div>
            <div class="col-xs-1">
                <select id="type" class="form-control">
                    <option value="1">上游</option>
                    <option value="2">下游</option>
                </select>
            </div>

            <div class="col-xs-1" style="text-align: right;">
                <label class="control-label no-padding-right"
                       style="margin-right: -12px">查询时间:</label>
            </div>

            <div class="col-xs-4" style="display: flex;">
                <div id="reportrange" class="input-prepend input-group" data-date-format="yyyy-mm-dd hh:ii:ss">

                    <input id="beginDate" name="beginDate" value="${beginDate}" type="hidden">
                    <input id="endDate" name="endDate" value="${endDate}" type="hidden">
                    <input type="text" style="width: 200px" name="timeSection" id="rangeDate" class="form-control"
                           value="" class="span4"/>
                </div>

                <div style="margin-left: 20px" style="margin: auto 0;">
                    <button onclick="search()" class="icon-search btn btn-sm btn-info">查询</button>
                </div>
            </div>
        </div>
    </div>
    <div class="hr hr16 hr-dotted"></div>
    <div id="content">
        <table id="role-table"
               class="table table-striped table-bordered table-hover dataTable"
               aria-describedby="role-table_info">
            <thead>
            <tr role="row">
                <th class="sorting" width="5%">
                    <small style="font-size: small;">序号</small>
                </th>
                <th class="sorting" width="45%">
                    <small style="font-size: small;">页面名称</small>
                </th>
                <th class="sorting" width="25%">
                    <small style="font-size: small;">pv</small>
                </th>
                <th class="sorting" width="25%">
                    <small style="font-size: small;">uv</small>
                </th>
            </tr>
            </thead>
            </tbody>
        </table>
    </div>
</div>
<script type="text/javascript" src="assets/js/jquery.dataTables.min.js"></script>
<script type="text/javascript"
        src="assets/js/jquery.dataTables.bootstrap.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        if ($("#beginDate").val() == null || $("#beginDate").val() == '') {
            $("#beginDate").val(new Date(new Date().getTime() - 60 * 60 * 24 * 1 * 1000).Format("yyyy-MM-dd"));
        }
        if ($("#endDate").val() == null || $("#endDate").val() == '') {
            $("#endDate").val(new Date().Format("yyyy-MM-dd"));
        }
        $("#rangeDate").val($("#beginDate").val() + " - " + $("#endDate").val());
    });

    // 对Date的扩展，将 Date 转化为指定格式的String
    // 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
    // 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
    Date.prototype.Format = function (fmt) { //author: meizz
        var o = {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "H+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds()
            //毫秒
        };
        if (/(y+)/.test(fmt))
            fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
                    .substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt))
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
                        : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    }
</script>
<script type="text/javascript">
    function districtChange() {

        var districtId = $("#district").val();
        $("#street").empty();
        $("#community").empty();
        $("#area").empty();

        if (districtId != null && districtId != "") {

            $.ajax({
                type: "POST",
                url: "oss/index/getArea",
                dataType: 'json',
                data: {
                    "district": districtId
                },
                cache: false,
                success: function (data) {
                    $("#street").append("<option value = \"\">");
                    $.each(data, function (i) {
                        if (data[i]) {

                            $("#street").append(
                                    "<option value = \"" + data[i].value + "\">"
                                    + data[i].name + "</option>");
                        }
                    });
                    $("#community").append("<option> </option>");
                    $("#area").append("<option> </option>");

                }
            });
        } else {
            $("#street").append("<option> </option>");
            $("#community").append("<option> </option>");
            $("#area").append("<option> </option>");
        }
    }

    function streetChange() {

        var streetId = $("#street").val();
        $("#community").empty();
        $("#area").empty();

        if (streetId != null && streetId != "") {

            $.ajax({
                type: "POST",
                url: "oss/index/getArea",
                dataType: 'json',
                data: {
                    "street": streetId
                },
                cache: false,
                success: function (data) {
                    $("#community").append("<option value = \"\">");
                    $.each(data, function (i) {
                        if (data[i]) {

                            $("#community").append(
                                    "<option value = \"" + data[i].value + "\">"
                                    + data[i].name + "</option>");
                        }
                    });
                    $("#area").append("<option> </option>");

                }
            });
        } else {
            $("#community").append("<option> </option>");
            $("#area").append("<option> </option>");
        }
    }

    function communityChange() {
        var communityId = $("#community").val();
        $("#area").empty();

        if (communityId != null && communityId != "") {

            $.ajax({
                type: "POST",
                url: "oss/index/getArea",
                dataType: 'json',
                data: {
                    "community": communityId
                },
                cache: false,
                success: function (data) {
                    $("#area").append("<option value = \"\">");
                    $.each(data, function (i) {
                        if (data[i]) {
                            $("#area").append(
                                    "<option value = \"" + data[i].value + "\">"
                                    + data[i].name + "</option>");
                        }
                    });
                }
            });
        } else {
            $("#area").append("<option> </option>");
        }
    }
</script>
<script type="text/javascript">
    $('#reportrange').daterangepicker(
            {

                dateLimit: {
                    days: 7
                },
                maxDate: moment(), //最大时间
                showDropdowns: true,
                showWeekNumbers: false, //是否显示第几周
                timePicker: false, //是否显示小时和分钟
                timePickerIncrement: 60, //时间的增量，单位为分钟
                timePicker12Hour: false, //是否使用12小时制来显示时间
                ranges: {
                    '默认': [moment().subtract('days', 1).startOf('day'),
                        moment()],
// 					'最近1小时' : [ moment().subtract('hours', 1), moment() ],
                    '今日': [moment().startOf('day'), moment()],
                    '昨日': [moment().subtract('days', 1).startOf('day'),
                        moment().subtract('days', 1).endOf('day')],
                    '最近7日': [moment().subtract('days', 6), moment()]
                },
                opens: 'right', //日期选择框的弹出位置
                buttonClasses: ['btn btn-default'],
                applyClass: 'btn-small btn-primary blue',
                cancelClass: 'btn-small',
                format: 'YYYY-MM-DD', //控件中from和to 显示的日期格式
                separator: ' to ',
                locale: {
                    applyLabel: '确定',
                    cancelLabel: '取消',
                    fromLabel: '起始时间',
                    toLabel: '结束时间',
                    customRangeLabel: '自定义',
                    daysOfWeek: ['日', '一', '二', '三', '四', '五', '六'],
                    monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月',
                        '八月', '九月', '十月', '十一月', '十二月'],
                    firstDay: 1
                }
            },
            //格式化日期显示框
            function (start, end, label) {
                $('#rangeDate').val(
                        start.format('YYYY-MM-DD') + ' - '
                        + end.format('YYYY-MM-DD'));
                $('#beginDate').val(start.format('YYYY-MM-DD'));
                $('#endDate').val(end.format('YYYY-MM-DD'));
            });
</script>

<script type="text/javascript">
    var oTable1;
    oTable1 = $('#role-table').DataTable({
//        serverSide: true,
        "oLanguage": {
            "sProcessing": "载入中...",
            "sLengthMenu": "显示 _MENU_ 项结果",
            "sZeroRecords": "没有匹配结果",
            "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
            "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
            "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
            "sInfoPostFix": "",
            "sSearch": "搜索:",
            "sUrl": "",
            "sEmptyTable": "表中数据为空",
            "sLoadingRecords": "载入中...",
            "sInfoThousands": ",",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "上页",
                "sNext": "下页",
                "sLast": "末页"
            },
            "oAria": {
                "sSortAscending": ": 以升序排列此列",
                "sSortDescending": ": 以降序排列此列"
            }
        },
        "ajax": {
            "url": "resource/getDetailFromData",
            "dataSrc": "aaData",
            "data": function (d) {
                var region = $('#district').val();
                var beginDate = $('#beginDate').val() == null ? null : $('#beginDate').val();
                var endDate = $('#endDate').val() == null ? null : $('#endDate').val();
                var keyword = $('#keyword').val();
                var dept = $('#dept').val();
                var type = $('#type').val();
                //添加额外的参数传给服务器
                d.region = region;
                d.beginDate = beginDate;
                d.endDate = endDate;
                d.keyword = keyword;
                d.type = type;
            }
        },
        "columns": [
            {"data": null},
            {"data": "rpcode"},
            /*{"data": "cpcode"},*/
            /*{"data": "url"},*/
            {"data": "pv"},
            {"data": "uv"},
        ],
        "processing": true, //打开数据加载时的等待效果
        "bPaginate": true, //翻页功能
        "bLengthChange": false, //改变每页显示数据数量
        "bFilter": false,
        "fnDrawCallback": function () {
            this.api().column(0).nodes().each(function (cell, i) {
                cell.innerHTML = i + 1;
            });
        },
    });

    function search() {
        oTable1.ajax.reload();
//        var beginDate = $('#beginDate').val() == null ? null : $('#beginDate').val();
//        var endDate = $('#endDate').val() == null ? null : $('#endDate').val();
//        var district = $('#district').val();
//        var keyword = $('#keyword').val();
//        var data1 = {beginDate: beginDate, endDate: endDate}
//        var url = "/getMapData/" + $('#district').val();
//
//        window.open("resource/getDetailFromData/" + district + "?beginDate=" + beginDate + "&endDate=" + endDate + "&keyword=" + keyword);

    }
</script>


