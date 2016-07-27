<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="entityPath" value="/employee" />

<html>
<head>
<script type="text/javascript" src="${ctx}/static/components/jquery-easyui-1.4/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/components/jquery/validation/jquery.validate.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/static/components/jquery/validation/messages_zh.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/components/jquery-easyui-1.4/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/components/jquery-easyui-1.4/themes/icon.css">
<script type="text/javascript" src="${ctx}/static/components/jquery-easyui-1.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/common.js"></script>
</head>
<body>

	<div class="result">
		<form id="editform" name="editform"  action="${ctx}${entityPath}/save" method="post">
			<fieldset>
				<legend>员工信息</legend>
				<table style="font-size: 12px; font-weight: bold;  color: #666666; border-collapse: collapse; border: #D9D9D9 1px solid; width:100%;">
					<tr style="border: #D9D9D9 1px solid;">
						<td class="left" style="width: 20%;">PID</td>
						<td class="right" style="border: #D9D9D9 1px solid;" colspan=3>
						<input class="easyui-textbox default-notnull"  style="border: #D9D9D9 1px solid; width:160px;" id="pid"  name="pid"  >
						</td>
					</tr>
					<tr style="border: #D9D9D9 1px solid;">
						<td class="left" style="width: 20%;">姓名</td>
						<td class="right" style="border: #D9D9D9 1px solid;" colspan=3>
						<input class="easyui-textbox default-notnull"  style="border: #D9D9D9 1px solid; width:160px;" id="name"  name="name"  >
						</td>
					</tr>
					<tr style="border: #D9D9D9 1px solid;">
						<td class="left" style="width: 20%;">编码</td>
						<td class="right" style="border: #D9D9D9 1px solid;" colspan=3>
						<input class="easyui-textbox default-notnull"  style="border: #D9D9D9 1px solid; width:160px;" id="code"  name="code"  >
						</td>
					</tr>
					<tr style="border: #D9D9D9 1px solid;">
						<td class="left" style="width: 20%;">性别</td>
						<td class="right" style="border: #D9D9D9 1px solid;" colspan=3>
						<input type="radio" id="sex" name="sex" value="1"/>男	
						<input type="radio" id="sex" name="sex" value="2"/>女
						</td>
					</tr>
					
				</table>
			</fieldset>
			<br>
			
<div style="float: left;margin-left: 200px"><a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="submitEditForm()">保存</a> <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-back'" onclick="gosearch()">返回</a></div>		</form>
	</div>
</body>
</html>