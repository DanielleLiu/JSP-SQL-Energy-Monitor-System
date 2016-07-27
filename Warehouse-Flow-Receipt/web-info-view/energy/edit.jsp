<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<script type="text/javascript"
	src="${ctx}/static/components/jquery-easyui-1.4/jquery.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/components/jquery/validation/jquery.validate.min.js"
	type="text/javascript"></script>
<script type="text/javascript"
	src="${ctx}/static/components/jquery/validation/messages_zh.js"></script>
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/components/jquery-easyui-1.4/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/components/jquery-easyui-1.4/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/editstyle.css">
<script type="text/javascript"
	src="${ctx}/static/components/jquery-easyui-1.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/common.js"></script>
</head>
<body>
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
		<form id="editform" name="editform" action="${ctx}/energy/save"
			method="post">


			<fieldset>
				<legend>基本信息</legend>
				<table
					style="font-size: 12px; font-weight: bold;  color: #666666; border-collapse: collapse; border: #D9D9D9 1px solid; width:100%;">
					<!-- r -->
					<tr style="border: #D9D9D9 1px solid;">
						<td class="left" style="width: 20%;">编码</td>
						<td class="right" style="border: #D9D9D9 1px solid;" colspan=3>
							<input class="easyui-textbox default-notnull primary-key" id="cc" name="code"
							value="${energyInfo.code}">

						</td>
					</tr>
					<tr style="border: #D9D9D9 1px solid;">
						<td class="left" style="width:20%;">名称</td>
						<td class="right" style="border: #D9D9D9 1px solid;" colspan=3>
							<input class="easyui-textbox default-notnull" id="cc" name="name"
							value="${energyInfo.name}">

						</td>
					</tr>
					<tr style="border: #D9D9D9 1px solid;">
						<td class="left" style="width: 20%;">实物量数值精度</td>
						<td class="right" style="border: #D9D9D9 1px solid;" colspan=3>
						<c:if test="${empty energyInfo.dataAccuracy}">
						<input class="easyui-textbox default-notnull" value="0.01"  id="cc" name="dataAccuracy"
							>
						</c:if>
						<c:if test="${not empty energyInfo.dataAccuracy}">
						<input class="easyui-textbox default-notnull" value="${energyInfo.dataAccuracy}"  id="cc" name="dataAccuracy"
							>
						</c:if>
						</td>
					</tr>
						<tr style="border: #D9D9D9 1px solid;">
						<td class="left" style="width: 20%;">参考折标系数</td>
						<td class="right" style="border: #D9D9D9 1px solid;" colspan=3>
						<c:if test="${empty energyInfo.referRatio}">
						<input class="easyui-textbox default-notnull" value="0"  id="ReferRatio" name="ReferRatio"
							>
						</c:if>
						<c:if test="${not empty energyInfo.referRatio}">
						<input class="easyui-textbox default-notnull" value="${energyInfo.referRatio}"  id="ReferRatio" name="ReferRatio"
							>
						</c:if>
						</td>
						
					</tr>
					<tr style="border: #D9D9D9 1px solid;">
						<td class="left" style="width: 20%;"><input type="hidden"
							  name="ID" id="userID" value="${energyInfo.ID}" /> 实物量计量单位</td>
						<td class="right" style="border: #D9D9D9 1px solid;">
						<input type="text" id="unitID" class="easyui-combobox" name="unitID"
							data-options="valueField:'id',textField:'text',url:'${ctx}/energy/getAvailableUnits?id=${empty energyInfo.ID ? '0' : energyInfo.ID}',method:'get',required:false,editable:false,value:'${empty energyInfo.unit ? '0' : energyInfo.unitID}'" />
							</td>
					</tr>
					<tr style="border: #D9D9D9 1px solid;">
						<td class="left" style="width: 20%;">拆标单位分子名称</td>
						<td class="right" style="border: #D9D9D9 1px solid;"><input
							type="text" id="unitID" class="easyui-combobox " name="StandardNumID"
						data-options="valueField:'id',textField:'text',url:'${ctx}/energy/getAvailableUnits?id=${empty energyInfo.ID ? '0' : energyInfo.ID}',method:'get',required:false,editable:false,value:'${empty energyInfo.unit ? '0' : energyInfo.standardNumID}'" />
							
							</td>
					</tr>
					<tr style="border: #D9D9D9 1px solid;">
						<td class="left" style="width: 20%;"> 拆标单位分母名称</td>
						<td class="right" style="border: #D9D9D9 1px solid;"><input
							type="text" id="unitID" class="easyui-combobox" name="StandardDenID"
													data-options="valueField:'id',textField:'text',url:'${ctx}/energy/getAvailableUnits?id=${empty energyInfo.ID ? '0' : energyInfo.ID}',method:'get',required:false,editable:false,value:'${empty energyInfo.unit ? '0' : energyInfo.standardDenID}'" />
														</td>
					</tr>
					<%-- <tr style="border: #D9D9D9 1px solid;">
						<td class="left" style="width: 20%;">上级能源介质</td>
						<td class="right" style="border: #D9D9D9 1px solid;" colspan=3>
							<input type="text" id="PID" class="easyui-combotree" name="PID"
							data-options="valueField:'id',textField:'text',url:'${ctx}/energy/getAvailableParents?id=${empty energyInfo.ID ? '0' : energyInfo.ID}',method:'get',required:false,editable:false,value:'${empty energyInfo.parent ? '0' : energyInfo.PID}'" />
						</td>
					</tr> --%>
					
					
				</table>
			</fieldset>
			<br>

			<div style="float: left;margin-left: 200px">
				<a href="#" class="easyui-linkbutton"
					data-options="iconCls:'icon-save'" onclick="submitEditForm()">保存</a>
				<a href="#" class="easyui-linkbutton"
					data-options="iconCls:'icon-back'" onclick="gosearch()">返回</a>
			</div>
		</form>
	</div>
</body>
</html>