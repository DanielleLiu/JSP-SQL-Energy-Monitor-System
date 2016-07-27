<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ec" uri="http://www.extremecomponents.org"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="entityPath" value="/storagebill" />

<html>
<head>
<script type="text/javascript"
	src="${ctx}/static/components/jquery-easyui-1.4/jquery.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/components/jquery-easyui-1.4/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/components/jquery-easyui-1.4/themes/icon.css">
<script type="text/javascript"
	src="${ctx}/static/components/jquery-easyui-1.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/common.js"></script>
<link
	href="<c:url value="/static/components/extremetable/Styles/extremecomponents.css"/>"
	type="text/css" rel="stylesheet">
</head>

<body>
	<div class="operations">
			<c:if test="${biitype=='1'}">
					<form class="form-inline" role="form"
			style="margin: 0 0 3px 0;display:inline-block;*display:inline;zoom:1;"
			id="queryform" name="queryform"
			action="${ctx}${entityPath}/listjoin">
					</c:if>
					<c:if test="${biitype=='-1'}">
					<form class="form-inline" role="form"
			style="margin: 0 0 3px 0;display:inline-block;*display:inline;zoom:1;"
			id="queryform" name="queryform"
			action="${ctx}${entityPath}/listout">
					</c:if>
					<c:if test="${biitype=='0'}">
					<form class="form-inline" role="form"
			style="margin: 0 0 3px 0;display:inline-block;*display:inline;zoom:1;"
			id="queryform" name="queryform"
			action="${ctx}${entityPath}/listset">
					</c:if>
			&nbsp;&nbsp;仓库
				<input type="text" id="storageID" class="easyui-combotree"
							name="storageID"
							data-options="valueField:'id',textField:'text',editable:true,url:'${ctx}/energyaccountunit/getAreas_unitorstorage?id=',method:'get',required:false,value:'${storageID}'" />
				 &nbsp;&nbsp;单据编号
				   <input id="billNum" name="billNum"  value="${billNum}"   style="border-radius:6px;border: 1px solid #7AA7EB;width: 220px;height: 25px"  
				onkeypress="enter();">
				 <a href="javascript:void(0)" class="easyui-linkbutton" id="subcon"
				data-options="iconCls:'icon-search'" onclick="submitForm()">查询</a>
		</form> 
		<span> <c:if test="${not empty message}">
				<div style="color: red;display:inline-block;*display:inline;zoom:1;"
					id="message">&nbsp;&nbsp;&nbsp;&nbsp;${message}</div>
			</c:if>
		</span> <span class="btn green fileinput-button" style="float: right;">
			<i class="icon-plus icon-white"></i> <A
			href="${ctx}${entityPath}/add/${biitype}" class="easyui-linkbutton"
			iconCls="icon-add" style="color: blue;">增加</A>&nbsp;&nbsp;&nbsp;&nbsp;
		</span>
	</div>
	<div class="table-responsive">
		<ec:table items="storageBillList" var="user"
			action="${ctx}${entityPath}/list" retrieveRowsCallback="limit"
			sortRowsCallback="limit" rowsDisplayed="20" filterable="false"
			sortable="false"
			autoIncludeParameters="${empty param.autoInc?'true':'false'}">
			<ec:row>
				<ec:exportXls fileName="userInfo.xls" tooltip="导出 Excel">
				</ec:exportXls>
				<ec:column property="rowcount" cell="rowCount" sortable="false"
					title="序号" />
				<ec:column property="unit.name" title="仓库" />
				<ec:column property="billNum" title="单据编号" />
				 <c:choose>
					<c:when test="${biitype=='1'}">
						<ec:column property="busTime" title="入库时间"/>
					</c:when>
					<c:when test="${biitype=='-1'}">
						<ec:column property="busTime" title="出库时间"/>
					</c:when>
					<c:when test="${biitype=='0'}">
						<ec:column property="busTime" title="盘库时间"/>
					</c:when>
					<c:otherwise>  
					<ec:column property="busTime" title="入库时间"/>
   					</c:otherwise>  
				</c:choose> 
				<ec:column property="billTime" title="开单时间" />
				 <c:choose>
					<c:when test="${biitype=='1'}">
						<ec:column property="billType" title="单据类型">入库</ec:column>
					</c:when>
					<c:when test="${biitype=='-1'}">
						<ec:column property="billType" title="单据类型">出库</ec:column>
					</c:when>
					<c:when test="${biitype=='0'}">
						<ec:column property="billType" title="单据类型">盘库</ec:column>
					</c:when>
					<c:otherwise>  
					<ec:column property="entryType" title="单据类型">无</ec:column>
   					</c:otherwise>  
				</c:choose> 
				<ec:column property="memo" title="备注" />

				<ec:column property="edit" title="修改" sortable="false"
					viewsAllowed="html" style="width: 30px">
					<A href="${ctx}${entityPath}/edit/${user.id}"><center>
							<img src="<c:url value="/static/images/icon/16x16/modify.gif"/>"
								border="0" />
						</center></A>
				</ec:column>
				<ec:column property="checkbox" title="删除" sortable="false"
					viewsAllowed="html" style="width: 30px">
					<A href="${ctx}${entityPath}/delete/${user.id}"
						onclick="return(confirm('确定刪除？'))">
						<center>
							<img src="<c:url value="/static/images/icon/16x16/delete.gif"/>"
								border="0" />
						</center>
					</A>
				</ec:column>
			</ec:row>
		</ec:table>

	</div>
</body>
</html>
