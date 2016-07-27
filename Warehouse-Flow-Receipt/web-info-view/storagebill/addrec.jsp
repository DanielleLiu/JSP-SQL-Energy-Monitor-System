<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="entityPath" value="/storagebill" />

<html>
<head>
<script language="javascript" type="text/javascript"
	src="${ctx}/static/components/My97DatePicker/WdatePicker.js"></script>
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
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/css/editstyle.css">
<script type="text/javascript"
	src="${ctx}/static/components/jquery-easyui-1.4/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/components/jquery-easyui-1.4/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/static/js/common.js"></script>
</head>
<body >
	<div class="editErrorBox">
	</div> 
	<div class="result">
		<form id="editform" name="editform"  action="${ctx}${entityPath}/saverec" method="post">


	
					 <fieldset>
				<legend>仓库存储能源介质信息</legend>
				<table style="font-size: 12px; font-weight: bold;  color: #666666; border-collapse: collapse; border: #D9D9D9 1px solid; width:100%;"><!-- r -->
				
				<input type="hidden" id="billID" name="billID" value="${billid}"/>
					<tr style="border: #D9D9D9 1px solid;">
						<td class="left" style="width: 20%;">仓库</td>
						<td class="right" style="border: #D9D9D9 1px solid;" colspan=3>
							<input type="text" id="storageEngID" class="easyui-combotree"
							name="storageEngID"
							data-options="valueField:'id',textField:'text',editable:true,url:'${ctx}/energystorage/getAreas_storageeng?id=${billid}',method:'get',required:false,value:'0'" />
						</td>
					</tr>
					<tr style="border: #D9D9D9 1px solid;">
						<td class="left" style="width: 20%;">热值</td>
						<td class="right" style="border: #D9D9D9 1px solid;" colspan=3>
						<input  class="easyui-textbox"   type="text" id="heatValue"  name="heatValue"   />
						</td>
					</tr>
					<tr style="border: #D9D9D9 1px solid;">
						<td class="left" style="width: 20%;">热值系数</td>
						<td class="right" style="border: #D9D9D9 1px solid;" colspan=3>
						<input  class="easyui-textbox"   type="text" id="heatmultiple"  name="heatmultiple"   />
						</td>
					</tr>
					<tr style="border: #D9D9D9 1px solid;">
						<td class="left" style="width: 20%;">折标量</td>
						<td class="right" style="border: #D9D9D9 1px solid;" colspan=3>
						<input  class="easyui-textbox"   type="text" id="energyValue"  name="energyValue"   />
						</td>
					</tr>
					<tr style="border: #D9D9D9 1px solid;">
						<td class="left" style="width: 20%;">折标系数</td>
						<td class="right" style="border: #D9D9D9 1px solid;" colspan=3>
						<input  class="easyui-textbox"   type="text" id="energyMultiple"  name="energyMultiple"   />
						</td>
					</tr>
					<tr style="border: #D9D9D9 1px solid;">
						<td class="left" style="width: 20%;">数量</td>
						<td class="right" style="border: #D9D9D9 1px solid;" colspan=3>
						<input  class="easyui-textbox"   type="text" id="quantity"  name="quantity"   />
						</td>
					</tr>
					<tr style="border: #D9D9D9 1px solid;">
						<td class="left" style="width: 20%;">当前库存数</td>
						<td class="right" style="border: #D9D9D9 1px solid;" colspan=3>
						<input  class="easyui-textbox"   type="text" id="sumBalance"  name="sumBalance"   />
						</td>
					</tr>
					<tr style="border: #D9D9D9 1px solid;">
						<td class="left" style="width: 20%;">当前库存热量</td>
						<td class="right" style="border: #D9D9D9 1px solid;" colspan=3>
						<input  class="easyui-textbox"   type="text" id="sumHeat"  name="sumHeat"   />
						</td>
					</tr>
					<tr style="border: #D9D9D9 1px solid;">
						<td class="left" style="width: 20%;">当前库存折标量</td>
						<td class="right" style="border: #D9D9D9 1px solid;" colspan=3>
						<input  class="easyui-textbox"   type="text" id="sumtce"  name="sumtce"   />
						</td>
					</tr>
				</table>
			</fieldset> 
<div style="float: left;margin-left: 200px"><a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="submitEditForm()">保存</a> <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-back'" onclick="javascript:history.go(-1);">返回</a></div>		</form>
	</div>
</body>
</html>