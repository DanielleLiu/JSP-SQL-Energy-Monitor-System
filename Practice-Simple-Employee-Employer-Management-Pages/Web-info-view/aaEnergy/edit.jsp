<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="entityPath" value="/aaEnergy" />

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
				<legend>能源基本信息</legend>
				<table
					style="font-size: 12px; font-weight: bold;  color: #666666; border-collapse: collapse; border: #D9D9D9 1px solid; width:100%;">
				<input type="hidden" name="id" id="id" value="${aaEnergy.id}"/>
				
				<!-- tr style="border: #D9D9D9 1px solid;">
				      <td style="border: #D9D9D9 1px solid; width:15%;"   >上级名称</td>
				     <td  colspan="3"   style="border: #D9D9D9 1px solid;" > 
				      <input  class="easyui-combotree" data-options="url:'${ctx}/energy/getAvailableParents?pkId=${energyInfo.PID}',method:'get',required:false,value:'${energyInfo.PID}'" id="PID" name="PID" value="${energyInfo.PID}"  >
				      </td>
				</tr-->
				
					<tr style="border: #D9D9D9 1px solid;">
						<td class="left" style="width: 20%;">能源名称</td>
						<td><input type="text" name="name" id="name"  required="true" missingMessage="名称不能为空" class="easyui-textbox" value="${aaEnergy.name}" />
						</td>
					</tr>
                    <tr style="border: #D9D9D9 1px solid;">
                        <td class="left" style="width: 20%;">能源编码</td>
						<td><input type="text" id="code" name="code"   class="easyui-validatebox easyui-textbox" validType="length[0,2]" invalidMessage="编码不能超过2位"
						required="true" missingMessage="编码不能为空" value="${aaEnergy.code}" />
                        <!-- max length 如何限制input length和type -->
                        </td>
                    </tr>
                    <tr style="border: #D9D9D9 1px solid;">
                        <td class="left" style="width: 20%;">折标系数</td>
						<td><input type="text" id="standard" class="easyui-textbox" name="standard"  value="${aaEnergy.code}" />
                        <!-- max length 如何限制input length和type -->
                        </td>
                    </tr>
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