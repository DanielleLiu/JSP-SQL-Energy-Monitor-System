<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="entityPath" value="/aaCompanyEnergyRelation" />

<html>
<head>
<script type="text/javascript" src="${ctx}/static/components/jquery-easyui-1.4/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/components/jquery/validation/jquery.validate.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/static/components/jquery/validation/messages_zh.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/components/jquery-easyui-1.4/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/components/jquery-easyui-1.4/themes/icon.css">
<script type="text/javascript" src="${ctx}/static/components/jquery-easyui-1.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/validate_.js"></script> 
</head>
<body>


	<div class="result">
		<form id="editform" name="editform"  action="${ctx}${entityPath}/save" method="post">



			<fieldset>
				<legend>企业能源关联信息</legend>
				<table
					style="font-size: 12px; font-weight: bold;  color: #666666; border-collapse: collapse; border: #D9D9D9 1px solid; width:100%;">
				<input type="hidden" name="id" id="id" value="${aaCompanyEnergyRelation.id}"/>
				
					<tr style="border: #D9D9D9 1px solid;" >
					<td  style="border: #D9D9D9 1px solid;" >企业名称</td>
					<td   colspan="3"    style="border: #D9D9D9 1px solid;" >
						<input type="text" id="companyid" class="easyui-combotree" name="companyid" 
			             data-options="valueField:'id',textField:'text',editable:false,url:'${ctx}/aaCompany/getAvailableCompanies?id=${empty aaCompanyEnergyRelation.companyid ? '0' : aaCompanyEnergyRelation.companyid}',method:'get',required:false,value:'${empty aaCompanyEnergyRelation.companyid ? '0' : aaCompanyEnergyRelation.companyid}'" />
						</td>
					</tr>
					
					<tr style="border: #D9D9D9 1px solid;" >
					<td  style="border: #D9D9D9 1px solid;" >能源名称</td>
					<td   colspan="3"    style="border: #D9D9D9 1px solid;" >
						<input type="text" id="energyid" class="easyui-combotree" name="energyid" multiple="true" 
			             data-options="valueField:'id',textField:'text',editable:false,url:'${ctx}/aaEnergy/getAvailableEnergies?id=${empty aaCompanyEnergyRelation.companyid ? '0' : aaCompanyEnergyRelation.companyid}',method:'get',required:false,value:''" />
						</td>
					</tr>
					<!-- empty aaCompanyER.companyid ? '0' : aaCompanyEnergyRelation.companyid url:根据url寻找需要调用的方法，pass parameter: 以上选中的companyid -->
					
				</table>
			</fieldset>
			<br>

			<div style="float: left;margin-left: 200px">
				<a id="submitlink" href="#" class="easyui-linkbutton"
					data-options="iconCls:'icon-save'" onclick="submitEditForm()">保存</a>
				<a href="#" class="easyui-linkbutton"
					data-options="iconCls:'icon-back'" onclick="gosearch()">返回</a>
			</div>
		</form>
	</div>
</body>
</html>