<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<script type="text/javascript" src="${ctx}/static/components/jquery-easyui-1.4/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/components/jquery/validation/jquery.validate.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/static/components/jquery/validation/messages_zh.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/components/jquery-easyui-1.4/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/components/jquery-easyui-1.4/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/editstyle.css">
<script type="text/javascript" src="${ctx}/static/components/jquery-easyui-1.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/components/jquery-easyui-1.4/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/static/js/common.js"></script>
<style type="text/css">
.rpt_header {
	border-top:solid 1px #AAAAAA;
	border-right: solid 1px #AAAAAA;
	border-bottom: solid 1px #AAAAAA;
}
.rpt_cell{
	border-right: solid 1px #AAAAAA;
	border-bottom: solid 1px #AAAAAA;
}
</style>
<script type="text/javascript">
	function myformatter(date){
		var y = date.getFullYear();
		var m = date.getMonth()+1;
		var d = 1;
		return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
	}
	
	function myparser(s){
		if (!s) return new Date();
		var ss = (s.split('-'));
		var y = parseInt(ss[0],10);
		var m = parseInt(ss[1],10);
		var d = parseInt(ss[2],10);
		if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
			return new Date(y,m-1,d);
		} else {
			return new Date();
		}
	}

	function initYears() {
		var today = new Date();
		
		var y = today.getFullYear();
		var years = [];
		
		for(var i=0; i<5; i++) {
			years[i] = {id:y,text:y};
			y--;
		}
		
		$("#search_startYear").combobox({
		    data:years,
		    valueField:'id',
		    textField:'text'
		});
	}

	function changeDimension(obj) {
		var dim = $(obj).val();
		if(dim=='year') {
			$(".search1").show();
			$(".search2").hide();
		}else if(dim=='month') {
			$(".search1").hide();
			$(".search2").show();
		}
	}
	
	function initEntKPIList() {
		var checks = $(".entKPIDs");
		for(var i=0; i<checks.length; i++) {
			var checkObj = checks[i];
			if(contains($(checkObj).val())) {
				$(checkObj).attr("checked",'true');
			}
		}
	}
	
	function contains(id) {
		for(var i=0; i<selIds.length; i++) {
			if(selIds[i] == id) {
				return true;
			}
		}	
		return false;
	}
	
	function pageLoaded() {
		initYears();
		
		initEntKPIList();
	}
	
	var selIds = ${selected_kpi};
	
</script>
</head>
<body onload="pageLoaded();">
	<form class="form-inline" role="form" style="margin: 0 0 3px 0;" id="queryform" name="queryform" method="get" action="${ctx}/entkpi/kpirpt">
	<div class="operations" style="margin-bottom:12px;">
		<div class="operationquery">
				<span>
					<input type='radio' id='dim_year' name='timedimension' value='year' onclick='changeDimension(this);' <c:if test="${dimension=='year'}">checked</c:if>/><label for="dim_year">按年统计</label>
					<input type='radio' id='dim_month' name='timedimension' value='month' onclick='changeDimension(this);' <c:if test="${dimension=='month'}">checked</c:if>/><label for="dim_month">按月统计</label>
				</span>
				<span class="search1" <c:if test="${dimension=='month'}">style="display:none;"</c:if>>
					 <input style="border: #D9D9D9 1px solid; " id='search_startYear' name="search_startYear" class="easyui-combotree" size="10" data-options="valueField:'id',textField:'text',method:'get',panelHeight:'auto',required:false,value:'${search_startYear}'">
				</span>
				<span class="search2" <c:if test="${dimension!='month'}">style="display:none;"</c:if>>
					<input type="text"   data-options="formatter:myformatter,parser:myparser" class="easyui-datebox" name="search_startMonth" value="${search_startMonth}" />
				</span>&nbsp;&nbsp;
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="submitForm()">查询</a>
		</div>
	</div>
	<div class="result">
		<div style="float:left;min-height:400px;max-width:300px;padding:4px;margin:2px 8px;overflow:auto;border:solid 1px #DDDDDD">
			<table cellspacing='4' cellpadding='2' >
			<tr>
				<th colspan='2' style="border-bottom:solid 1px #AAAAAA">选择对标指标</th>
			</tr>
			<c:forEach items="${EntKPIList}" var="EntKPI" varStatus="status">
				<tr>
					<td><input class="entKPIDs" type="checkbox" id="ent_kpi${status.index}" name="entKPIIds" value="${EntKPI.ID}"/></td>
					<td><label for="ent_kpi${status.index}">${EntKPI.entKPIName }</label></td>
				</tr>
			</c:forEach>
			</table>
		</div>
	</form>
		<div style="float:left;">
		<table cellpading='2'>
			<tr>
				<th colspan='10'><h1>能效对标指标表</h1></th>
			</tr>
			<tr>
				<td colspan='8'>企业名称：${EntName}</td>
				<td colspan='2'><div style="float:right">对标时间：${KPITime}</div></td>
			</tr>
			<tr>
				<th class="rpt_header" style="border-left:1px solid #AAAAAA">能效指标</th>
				<th class="rpt_header">指标值</th>
				<th class="rpt_header">子项值</th>
				<th class="rpt_header">母项值</th>
				<th class="rpt_header">${KPIKind["01"] }</th>
				<th class="rpt_header">${KPIKind["02"] }</th>
				<th class="rpt_header">${KPIKind["03"] }</th>
				<th class="rpt_header">${KPIKind["04"] }</th>
				<th class="rpt_header">${KPIKind["05"] }</th>
				<th class="rpt_header">${KPIKind["06"] }</th>
			</tr>
			<c:forEach items="${RPTItems}" var="Item" varStatus="status">
				<tr>
					<td class="rpt_cell"  style="border-left:1px solid #AAAAAA">${Item["Name"] }</td>
					<td class="rpt_cell">
					<fmt:formatNumber type="number" value="${Item['Value'] }" maxFractionDigits="6"/>
					</td>
					<td class="rpt_cell">
					<fmt:formatNumber type="number" value="${Item['NumValue'] }" maxFractionDigits="6"/>
					</td>
					<td class="rpt_cell">
					<fmt:formatNumber type="number" value="${Item['DenValue'] }" maxFractionDigits="6"/>
					</td>
					<td class="rpt_cell">${Item["01"] }</td>
					<td class="rpt_cell">${Item["02"] }</td>
					<td class="rpt_cell">${Item["03"] }</td>
					<td class="rpt_cell">${Item["04"] }</td>
					<td class="rpt_cell">${Item["05"] }</td>
					<td class="rpt_cell">${Item["06"] }</td>
				</tr>
			</c:forEach>
		</table>
		</div>
	</div>
</body>
</html>