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

<script type="text/javascript">
	//根据企业名称模糊查询采集点数据
	function selunit() {
		var untiname = $("input[name='unitname']").val();
		var url = "${ctx}${entityPath}/selunitlist";
		$
				.ajax({
					type : "GET",
					url : url,
					data : {
						untiname : untiname
					},
					datatype : "json",
					contentType : "application/json; charset=utf-8",
					success : function(data) {
						$
								.each(
										data,
										function(i, list) {
											var num = parseInt(i) + parseInt(1);
											var _tr = $(" <tr><td class='cent'>"
													+ num
													+ "</td><td>"
													+ list.energyCconsumptionUnit.companyName
													+ "</td><td class='cent'>"
													+ list.id
													+ "</td><td>"
													+ list.name
													+ "</td><td>&nbsp&nbsp&nbsp<input type='radio'  name='collectPointID' value="+list.id+"></td></tr>");
											$("#Pointlist").append(_tr);
										});
					}
				});
	}
</script>
<style type="text/css">
.cent {
	text-align: center;
}

.rightn {
	text-align: right;
}

.font {
	color: #383838
}
</style>
</head>
<body>
	<div class="editErrorBox"></div>
	<div class="result">
		<form id="editform" name="editform" action="${ctx}${entityPath}/save"
			method="post">
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
							name="storageID" readonly="readonly"
							data-options="valueField:'id',textField:'text',editable:true,url:'${ctx}/energyaccountunit/getAreas_unitorstorage?id=',method:'get',required:false,value:'${storagebill.storageID}'" />
						</td>
					</tr>
					<tr style="border: #D9D9D9 1px solid;">
						<td class="left" style="width: 20%;">单据号</td>
						<td class="right" style="border: #D9D9D9 1px solid;" colspan=3>
							<input type="text" id="billNum" readonly="readonly"
							style="border-radius:6px;border: 1px solid #7AA7EB;width:172px;height: 23px;background-color: #E2E2D9;"
							name="billNum" value="${storagebill.billNum}" readonly="readonly"/>
						</td>
					</tr>
					<c:if test="${storagebill.billType=='1'}">
						<tr style="border: #D9D9D9 1px solid;">
							<td class="left" style="width: 20%;">入库时间</td>
							<td class="right" style="border: #D9D9D9 1px solid;" colspan=3>
								<input class="easyui-datebox" type="text" id="busTime"
								name="busTime" value="${storagebill.busTime}" readonly="readonly"/>
							</td>
						</tr>
					</c:if>
					<c:if test="${storagebill.billType=='-1'}">
						<tr style="border: #D9D9D9 1px solid;">
							<td class="left" style="width: 20%;">出库时间</td>
							<td class="right" style="border: #D9D9D9 1px solid;" colspan=3>
								<input class="easyui-datebox" type="text" id="busTime"
								name="busTime" value="${storagebill.busTime}" readonly="readonly"/>
							</td>
						</tr>
					</c:if>
					<c:if test="${storagebill.billType=='0'}">
						<tr style="border: #D9D9D9 1px solid;">
							<td class="left" style="width: 20%;">盘库时间</td>
							<td class="right" style="border: #D9D9D9 1px solid;" colspan=3>
								<input class="easyui-datebox" type="text" id="busTime"
								name="busTime" value="${storagebill.busTime}" readonly="readonly"/>
							</td>
						</tr>
					</c:if>
					<tr style="border: #D9D9D9 1px solid;">
						<td class="left" style="width: 20%;">开单时间</td>
						<td class="right" style="border: #D9D9D9 1px solid;" colspan=3>
							<input class="easyui-datebox" type="text" id="billTime"
							name="billTime" value="${storagebill.billTime}" readonly="readonly"/>
						</td>
					</tr>
					<tr style="border: #D9D9D9 1px solid;">
						<td class="left" style="width: 20%;">备注</td>
						<td class="right" style="border: #D9D9D9 1px solid;" colspan=3>
							<input class="easyui-textbox" type="text" id="memo" name="memo"
							value="${storagebill.memo}" readonly="readonly"/>
							<input type="hidden" id="billType" name="billType"
							value="${storagebill.billType}" /> <input type="hidden" id="id"
							name="id" value="${storagebill.id}" />
						</td>
					</tr>
				</table>
			</fieldset>
			<br>
			<fieldset>
				<legend>单据记录信息</legend>
				<span class="btn green fileinput-button"> <i
					class="icon-plus icon-white"></i> <A
					href="${ctx}${entityPath}/addrec/${storagebill.id}"
					class="easyui-linkbutton" iconCls="icon-add" style="color: blue;">增加</A>&nbsp;&nbsp;&nbsp;&nbsp;
				</span>
				<table id="Pointlist" border='1'
					style="font-size: 12px;border-color: #DBDEDF font-weight: bold;  color: #666666; border-collapse: collapse; border: #D9D9D9 1px solid;">

					<tr style="background-color: #F7E390">
						<th style="display: none"></th>
						<th style="width:100px;">序号</th>
						<th style="width:200px;">仓库介质编号</th>
						<!-- <th style="width:250px">单据编号</th> -->
						<th style="width:200px;">热值</th>
						<th style="width:200px;">热值系数</th>
						<th style="width:200px;">折标量</th>
						<th style="width:200px;">折标系数</th>
						<th style="width:200px;">数量</th>
						<th style="width:200px;">当前库存数</th>
						<th style="width:200px;">当前库存热量</th>
						<th style="width:200px;">当前库存折标量</th>
						<th style="width:100px;text-align:center;">修改</th>
						<th style="width:100px;text-align:center;">删除</th>
					</tr>
					<c:forEach items="${objList}" var="list" varStatus="status">
						<tr>
							<td style="display: none"></td>
							<td class='cent'>${status.index+1}</td>
							<td class="font">${list.entenergy.enterEnergyName}</td>
							<%-- <td class="font">${list.billID}</td> --%>
							<td class="font">${list.heatValue}</td>
							<td class="font">${list.heatmultiple}</td>
							<td class="font">${list.energyValue}</td>
							<td class="font">${list.energyMultiple}</td>
							<td class="font">${list.quantity}</td>
							<td class="font">${list.sumBalance}</td>
							<td class="font">${list.sumHeat}</td>
							<td class="font">${list.sumtce}</td>
							<td><A href="${ctx}${entityPath}/editrec/${list.id}"><center>
										<img
											src="<c:url value="/static/images/icon/16x16/modify.gif"/>"
											border="0" />
									</center></A></td>
							<td><A href="${ctx}${entityPath}/deleterec/${list.id}"
								onclick="return(confirm('确定刪除？'))">
									<center>
										<img
											src="<c:url value="/static/images/icon/16x16/delete.gif"/>"
											border="0" />
									</center>
							</A></td>
						</tr>
					</c:forEach>
				</table>
			</fieldset>
			<c:if test="${storagebill.billType=='1'}">
					<span class="btn green fileinput-button" style="float: left: ;">
				<i class="icon-plus icon-white"></i> <A
				href="${ctx}${entityPath}/listjoin" class="easyui-linkbutton"
				data-options="iconCls:'icon-back'" style="color: blue;">返回</A>&nbsp;&nbsp;&nbsp;&nbsp;
			</span>
				</c:if>
				<c:if test="${storagebill.billType=='-1'}">
					<span class="btn green fileinput-button" style="float: left: ;">
				<i class="icon-plus icon-white"></i> <A
				href="${ctx}${entityPath}/listout" class="easyui-linkbutton"
				data-options="iconCls:'icon-back'" style="color: blue;">返回</A>&nbsp;&nbsp;&nbsp;&nbsp;
			</span>
				</c:if>
				<c:if test="${storagebill.billType=='0'}">
					<span class="btn green fileinput-button" style="float: left: ;">
				<i class="icon-plus icon-white"></i> <A
				href="${ctx}${entityPath}/listset" class="easyui-linkbutton"
				data-options="iconCls:'icon-back'" style="color: blue;">返回</A>&nbsp;&nbsp;&nbsp;&nbsp;
			</span>
			</c:if>
			
		</form>
	</div>
</body>
</html>