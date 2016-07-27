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
				<legend>员工基本信息</legend>
				<table
					style="font-size: 12px; font-weight: bold;  color: #666666; border-collapse: collapse; border: #D9D9D9 1px solid; width:100%;">
				<input type="hidden" name="id" id="id" value="${employee.id}"/>

					
					<tr>
                        <td class="right" style="border: #D9D9D9 1px solid;">父名称<input type="hidden" name="id" id="id" value="${employee.id}" /></td>

                        <td>
            <input type="text" id="pid" class="easyui-combotree" name="pid" style="width: 300px;"
                   data-options="valueField:'id',textField:'text',editable:true,url:'${ctx}${entityPath}/getAvailableParents?id=${empty employee.id ? '0' : employee.id}',method:'get',required:false,value:'${empty employee.parent ? '0' : employee.pid}'" /></td>
                    </tr>

					<tr style="border: #D9D9D9 1px solid;">
						<td class="left" style="width: 20%;">姓名</td>
						<td><input type="text" name="name" id="name" class="easyui-textbox default-notnull" value="${employee.name}" />
						</td>
					</tr>
                    <tr style="border: #D9D9D9 1px solid;">
                        <td class="left" style="width: 20%;">编码</td>
						<td><input type="text" id="code" class="easyui-textbox default-notnull" name="code"  value="${employee.code}" />
                        <!-- max length 如何限制input length和type -->
                        </td>
                    </tr>

				<tr style="border: #D9D9D9 1px solid;">
						<td class="left" style="width: 20%;">性别</td>
						<td class="right" style="border: #D9D9D9 1px solid;" colspan=3>
						<c:choose>
							<c:when test="${employee.sex==1}">
							<input type="radio" id="sex" name="sex" value="1" checked="checked"/>男	
							<input type="radio" id="sex" name="sex" value="2"/>女
							</c:when>
							<c:when test="${employee.sex==2}">
							<input type="radio" id="sex" name="sex" value="1"/>男	
							<input type="radio" id="sex" name="sex" value="2"  checked="checked"/>女
							</c:when>
							<c:otherwise>
							<input type="radio" id="sex" name="sex" value="1"/>男	
							<input type="radio" id="sex" name="sex" value="2"/>女
							</c:otherwise>
						</c:choose>
						</td>
					</tr>


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