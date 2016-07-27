<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="entityPath" value="/storagebill" />
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
</script>
</head>
<body>
	
	<div class="editErrorBox">
	</div> 
	<div class="result">
		<form id="editform" name="editform" action="${ctx}${entityPath}/save" method="post">
		<fieldset>
		<c:if test="${storagebill.billType=='1'}">
		<legend>入库单</legend>
		</c:if>
		<c:if test="${storagebill.billType=='-1'}">
		<legend>出库单</legend>
		</c:if>
		<c:if test="${storagebill.billType=='0'}">
		<legend>盘库单</legend>
		</c:if>
				<table
					style="font-size: 12px; font-weight: bold;  color: #666666; border-collapse: collapse; border: #D9D9D9 1px solid; width:100%;">
					<!-- r -->
					<tr style="border: #D9D9D9 1px solid;">
						<td class="left" style="width: 20%;">仓库</td>
						<td class="right" style="border: #D9D9D9 1px solid;" colspan=3>
							<input type="text" id="storageID" class="easyui-combotree"
							name="storageID"
							data-options="valueField:'id',textField:'text',editable:true,url:'${ctx}/energyaccountunit/getAreas_unitorstorage?id=',method:'get',required:false,value:'${storagebill.storageID}'" />
						</td>
					</tr>
					<tr style="border: #D9D9D9 1px solid;">
						<td class="left" style="width: 20%;">单据号</td>
						<td class="right" style="border: #D9D9D9 1px solid;" colspan=3>
							<input  type="text" id="billNum" readonly="readonly" 
							 style="border-radius:6px;border: 1px solid #7AA7EB;width:172px;height: 23px;background-color: #E2E2D9;"  
							name="billNum" value="${storagebill.billNum}"/>
						</td>
					</tr>
					<c:if test="${storagebill.billType=='1'}">
					<tr style="border: #D9D9D9 1px solid;">
						<td class="left" style="width: 20%;">入库时间</td>
						<td class="right" style="border: #D9D9D9 1px solid;" colspan=3>
							<input class="easyui-datebox" type="text" id="busTime"
							name="busTime" value="${storagebill.busTime}"/>
						</td>
					</tr>
					</c:if>
					<c:if test="${storagebill.billType=='-1'}">
					<tr style="border: #D9D9D9 1px solid;">
						<td class="left" style="width: 20%;">出库时间</td>
						<td class="right" style="border: #D9D9D9 1px solid;" colspan=3>
							<input class="easyui-datebox" type="text" id="busTime"
							name="busTime" value="${storagebill.busTime}"/>
						</td>
					</tr>
					</c:if>
					<c:if test="${storagebill.billType=='0'}">
					<tr style="border: #D9D9D9 1px solid;">
						<td class="left" style="width: 20%;">盘库时间</td>
						<td class="right" style="border: #D9D9D9 1px solid;" colspan=3>
							<input class="easyui-datebox" type="text" id="busTime"
							name="busTime" value="${storagebill.busTime}"/>
						</td>
					</tr>
					</c:if>
					<tr style="border: #D9D9D9 1px solid;">
						<td class="left" style="width: 20%;">开单时间</td>
						<td class="right" style="border: #D9D9D9 1px solid;" colspan=3>
							<input class="easyui-datebox" type="text" id="billTime"
							name="billTime"  value="${storagebill.billTime}"/>
						</td>
					</tr>
					<tr style="border: #D9D9D9 1px solid;">
						<td class="left" style="width: 20%;">备注</td>
						<td class="right" style="border: #D9D9D9 1px solid;" colspan=3>
							<input class="easyui-textbox" type="text" id="memo" name="memo" value="${storagebill.memo}"/>
							<input type="hidden" id="billType" name="billType" value="${storagebill.billType}"/>
							<input type="hidden" id="id" name="id"  value="${storagebill.id}"/>
						</td>
					</tr>
				</table>
			</fieldset>
			<div style="clear:both;float: left;margin-left: 200px;margin-top:20px;"><a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="submit();">保存</a>
			 <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-back'" onclick="javascript:history.go(-1);">返回</a>
			 </div>
		</form>
	</div>
</body>
</html>