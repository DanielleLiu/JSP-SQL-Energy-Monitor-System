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
<script type="text/javascript">
	$(document).ready(function() {
		$("#areaStandInfo").validate({
			rules : {
				asCode : {
					required : true,
					maxlength : 6,
					remote : {
						url : "${ctx}/es/areaStandInfo/isUnique", //后台处理程序
						type : "get", //数据发送方式
						data : { //要传递的数据
							asCode : function() {
								return $("#asCode").val();
							},
							isUpdate : '${isUpdate}',
							date : new Date()
						}
					}
				},
				asName : {
					required : true,
					maxlength : 40
				}
			},
			messages : {
				asCode : {
					remote : "编号已被占用"
				}
			}
		});
	});
	
	var NumIdx, DenIdx;
	var numMeasureList = [];
	var denMeasureList = [];
	//点击ADD触发function
	function addNumMeasure() {
		NumIdx++;
		addMeasure("#numPointTable","Num", NumIdx);
		loadNumMeasureList("#Num_MeasureID"+NumIdx);
	}
	
	function addDenMeasure() {
		DenIdx++;
		addMeasure("#denPointTable","Den", DenIdx);
		loadDenMeasureList("#Den_MeasureID"+DenIdx);
	}
	
	//ADD点击调用function2,tableObj要增加数据的table，prefix要增加数据的名称，idx目前index； 空白下拉框
	function addMeasure(tableObj, prefix, idx) {
		var tObj = $(tableObj);

		var dataOptions = "valueField:'id',textField:'text',method:'get',editable:false,required:false";

		var tr = "<tr>";
		tr += "<td>";
		tr += "<input type='hidden' id='" + prefix + "_KPIPoint" + idx + "' name='" + prefix + "_KPIPoint" + idx +"' value=''/>";
			tr += "<input type='text' style='width:400px;' id='" + prefix + "_MeasureID" + idx + "' class='easyui-combobox' data-options=\"" + dataOptions + "\" name='" + prefix + "_MeasureID" + idx + "' value='' />";
		tr += "</td>";
		tr += "<td style='padding:0 8px;white-space:nowrap''>";
			tr += "<input type='radio' id='" + prefix + "_Dir_Plus" + idx + "' name='" + prefix + "_DIR" + idx + "' value='plus' checked/><label for='" + prefix + "_Dir_Plus" + idx +"'>+</label>";
			tr += "<input type='radio' id='" + prefix + "_Dir_Minus" + idx + "' name='" + prefix + "_DIR" + idx + "' value='minus'/><label for='" + prefix + "_Dir_Minus" + idx + "'>-</label>";
		tr += "</td>";
		tr += "<td>";
        	tr += "<A href='#' onclick='delMeasure(this);'>";
    		tr += "<center><img src=\"<c:url value='/static/images/icon/16x16/delete.gif'/>\" border='0' /></center>";
    		tr += "</A>";
		tr += "</td>";
		tr += "</tr>";
		
		tObj.append(tr);
	}
	
	//Load 数据到下拉框
	function loadNumMeasureList(id) {
		var obj = $(id);
		if(numMeasureList.length == 0) {
			var kpiid = $("#KPIID").combobox("getValue");
			url = "${ctx}/entkpi/selectMeasure?kpi="+kpiid+"&type=num&rand="+new Date();
			obj.combobox({
			    url:url,
			    valueField:'id',
			    textField:'text'
			});
		}else{
			obj.combobox({
			    data:numMeasureList,
			    valueField:'id',
			    textField:'text'
			});
		}
	}

	function loadDenMeasureList(id) {
		var obj = $(id);
		if(denMeasureList.length == 0) {
			var kpiid = $("#KPIID").combobox("getValue");
			url = "${ctx}/entkpi/selectMeasure?kpi="+kpiid+"&type=den&rand="+new Date();
			obj.combobox({
			    url:url,
			    valueField:'id',
			    textField:'text'
			});
		}else{
			obj.combobox({
			    data:denMeasureList,
			    valueField:'id',
			    textField:'text'
			});
		}
	}
	
	function delMeasure(obj) {
		var link = $(obj);
		if(confirm("确认删除？")) {
			link.parent().parent().remove();
		}
	}
	
	function submit() {
		$("#numSize").val($("#numPointTable tr").length);
		$("#denSize").val($("#denPointTable tr").length);
		$("#numMaxSize").val(NumIdx+1);
		$("#denMaxSize").val(DenIdx+1);
		
		document.forms["editform"].submit();
	}
	
	function initMeasureList() {
		NumIdx = $("#numPointTable tr").length;
		DenIdx = $("#denPointTable tr").length;

		var kpiid = $("#KPIID").combobox("getValue");
		
		url = "${ctx}/entkpi/selectMeasure?kpi="+kpiid+"&type=num&rand="+new Date();
		$.ajax({
			type:"get",
			url:url, 
			dataType:'json',
			success:function(data, status) {
				numMeasureList = data;
				//当前table length==0， 
				if(NumIdx == 0) {
					addNumMeasure();
				}else{
					var measureCombo = $(".num-measure");
					for(var i=0; i<measureCombo.length; i++) {
						loadNumMeasureList(measureCombo[i]);
					}
				}
			},
			error:function(msg){
			}
		});

		url = "${ctx}/entkpi/selectMeasure?kpi="+kpiid+"&type=den&rand="+new Date();
		$.ajax({
			type:"get",
			url:url, 
			dataType:'json',
			success:function(data, status) {
				denMeasureList = data;
				
				if(DenIdx == 0) {
					addDenMeasure();
				}else{
					var measureCombo = $(".den-measure");
					for(var i=0; i<measureCombo.length; i++) {
						loadDenMeasureList(measureCombo[i]);
					}
				}
			},
			error:function(msg){
			}
		});
		
	}
	
	//每次加载页面，直接load 该function
	function pageLoaded() {
		initMeasureList();
		
		$("#KPIID").combobox({ 
			onSelect: function (n,o) {
				var txt = $("#KPIID").combobox("getText");
				$("#entKPIName").textbox("setValue", txt);
				
				numMeasureList = [];
				denMeasureList = [];

				$("#numPointTable").html("");
				
				$("#denPointTable").html("");

				initMeasureList();
			}
		});
		
	}
</script>


</head>
<body onload="pageLoaded();">
	
	<div class="editErrorBox">
		<%-- <spring:hasBindErrors name="areaStandInfo">
			<c:if test="${errors.fieldErrorCount > 0}">  
				        字段错误：<br />
				<c:forEach items="${errors.fieldErrors}" var="error">
					<spring:message var="message" code="${error.code}" arguments="${error.arguments}" text="${error.defaultMessage}" />
					<fmt:message key='${error.field}' />${message}<br />
				</c:forEach>
			</c:if>

			<c:if test="${errors.globalErrorCount > 0}">  
			        全局错误：<br />
				<c:forEach items="${errors.globalErrors}" var="error">
					<spring:message var="message" code="${error.code}" arguments="${error.arguments}" text="${error.defaultMessage}" />
					<c:if test="${not empty message}">  
			                ${message}<br />
					</c:if>
				</c:forEach>
			</c:if>
		</spring:hasBindErrors> --%>
	</div> 
	<div class="result">
		<form id="editform" name="editform" action="${ctx}/entkpi/save" method="post">
			<fieldset>
				<legend>基本信息</legend>
				<table style="font-size: 12px; font-weight: bold;  color: #666666; border-collapse: collapse; border: #D9D9D9 1px solid; width:100%;">
					
					<tr style="border: #D9D9D9 1px solid;display:none">
						<td><input type="hidden" name="ID" id="ID" value="${entKPI.ID}" /></td>
						</tr>		
						<tr style="border: #D9D9D9 1px solid;">
						<td class="right" style="border: #D9D9D9 1px solid;">能效指标</td>
						
						<td><input type="text" id="KPIID" style="width: 300px;" class="easyui-combobox" data-options="valueField:'id',textField:'text',url:'${ctx}/entkpi/selectEnergyKPI',method:'get',required:false" name="KPIID" value="${entKPI.KPIID}" /></td>
					</tr>
					<tr style="border: #D9D9D9 1px solid;">
						<td class="right" style="border: #D9D9D9 1px solid;">指标名称</td>
						
						<td>
							<input type="text" id="entKPIName" style="width:300px;" class="easyui-textbox" name="entKPIName" value="${entKPI.entKPIName}"  />
						</td>
					</tr>
					
				</table>
			</fieldset>
			<table>
			<tr>
			<td>
			<fieldset style="float:left;">
				<legend>分子规划点</legend>
				<span class="btn green fileinput-button" style="float: right;">
					<i class="icon-plus icon-white"></i> <A href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="addNumMeasure()">增加</A>&nbsp;&nbsp;&nbsp;&nbsp;
				</span>
				<input type="hidden" id="numSize" name="numSize" value=""/>
				<input type="hidden" id="numMaxSize" name="numMaxSize" value=""/>
				<table id="numPointTable" style="clear:both;float:left;">
				<c:forEach items="${numMeasurePoint}" var="numpoint" varStatus="status">
				<tr>
					<td>
						<input type='hidden' id='Num_KPIPoint${status.index+1}' name='Num_KPIPoint${status.index+1}' value='${numpoint.ID }'/>
						<input type='text' style='width:400px;' id='Num_MeasureID${status.index+1}' class='easyui-combobox num-measure' data-options="valueField:'id',textField:'text',method:'get',editable:false,required:false" name='Num_MeasureID${status.index+1}' value='${numpoint.pointID }' />
					</td>
					<td style='padding:0 8px;white-space:nowrap'>
						<input type='radio' id='Num_Dir_Plus${status.index+1}' name='Num_DIR${status.index+1}' value='plus' <c:if test="${numpoint.calDir==1}">checked</c:if>/><label for='Num_Dir_Plus${status.index+1}'>+</label>
						<input type='radio' id='Num_Dir_Minus${status.index+1}' name='Num_DIR${status.index+1}' value='minus' <c:if test="${numpoint.calDir==0}">checked</c:if>/><label for='Num_Dir_Minus${status.index+1}'>-</label>
					</td>
					<td>
			        	<A href='#' onclick='delMeasure(this);'>
			    		<center><img src="<c:url value='/static/images/icon/16x16/delete.gif'/>" border='0' /></center>
			    		</A>
					</td>
				</tr>
				</c:forEach>
				</table>
			</fieldset>
			</td>
			<td>
			<fieldset style="float:left;">
				<legend>分母规划点</legend>
				<span class="btn green fileinput-button" style="float: right;">
					<i class="icon-plus icon-white"></i> <A href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="addDenMeasure()">增加</A>&nbsp;&nbsp;&nbsp;&nbsp;
				</span>
				<input type="hidden" id="denSize" name="denSize" value=""/>
				<input type="hidden" id="denMaxSize" name="denMaxSize" value=""/>
				<table id="denPointTable" style="clear:both;float:left;">
				<c:forEach items="${denMeasurePoint}" var="denpoint" varStatus="status">
				<tr>
					<td>
						<input type='hidden' id='Den_KPIPoint${status.index+1}' name='Den_KPIPoint${status.index+1}' value='${denpoint.ID }'/>
						<input type='text' style='width:400px;' id='Den_MeasureID${status.index+1}' class='easyui-combobox den-measure' data-options="valueField:'id',textField:'text',method:'get',editable:false,required:false" name='Den_MeasureID${status.index+1}' value='${denpoint.pointID}' />
					</td>
					<td style='padding:0 8px;white-space:nowrap'>
						<input type='radio' id='Den_Dir_Plus${status.index+1}' name='Den_DIR${status.index+1}' value='plus' <c:if test="${denpoint.calDir==1}">checked</c:if>/><label for='Den_Dir_Plus${status.index+1}'>+</label>
						<input type='radio' id='Den_Dir_Minus${status.index+1}' name='Den_DIR${status.index+1}' value='minus' <c:if test="${denpoint.calDir==0}">checked</c:if>/><label for='Den_Dir_Minus${status.index+1}'>-</label>
					</td>
					<td>
			        	<A href='#' onclick='delMeasure(this);'>
			    		<center><img src="<c:url value='/static/images/icon/16x16/delete.gif'/>" border='0' /></center>
			    		</A>
					</td>
				</tr>
				</c:forEach>
				</table>
			</fieldset>
			</td>
			</tr>
			</table>
			<div style="clear:both;float: left;margin-left: 200px;margin-top:20px;"><a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="submit();">保存</a> <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-back'" onclick="gosearch()">返回</a></div>
		</form>
	</div>
</body>
</html>