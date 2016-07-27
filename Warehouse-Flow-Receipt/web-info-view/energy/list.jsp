<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ec" uri="http://www.extremecomponents.org"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

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
		<form class="form-inline" role="form"
			style="margin: 0 0 3px 0;display:inline-block;*display:inline;zoom:1;"
			id="queryform" name="queryform" method="post"
			action="${ctx}/energy/selectOne">
			&nbsp;&nbsp;按名称 <input id="cc" name="name"
				value="${name}"   style="border-radius:6px;border: 1px solid #7AA7EB;width: 220px;height: 25px"  
				onkeypress="enter();"> &nbsp;&nbsp; <a
				href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-search'" onclick="submitForm()">查询</a>
		</form>
		<span> <c:if test="${not empty message}">
				<div style="color: red;display:inline-block;*display:inline;zoom:1;"
					id="message">&nbsp;&nbsp;&nbsp;&nbsp;${message}</div>
			</c:if>
		</span> <span class="btn green fileinput-button" style="float: right;">
			<i class="icon-plus icon-white"></i> <A href="${ctx}/energy/add"
			class="easyui-linkbutton" iconCls="icon-add" style="color: blue;">增加</A>&nbsp;&nbsp;&nbsp;&nbsp;
		</span>


	</div>

	<div class="table-responsive">
		<ec:table items="energyInfo" var="energy" action="${ctx}/energy/list"
			retrieveRowsCallback="limit" sortRowsCallback="limit"
			rowsDisplayed="20" filterable="false" sortable="false"
			autoIncludeParameters="${empty param.autoInc?'true':'false'}">
			<ec:row>
				<ec:exportXls fileName="userInfo.xls" tooltip="导出 Excel" />
				<ec:column property="rowcount" cell="rowCount" sortable="false"
					title="序号"  style="text-align:center;" />

				<%--  <ec:column property="ID" title="序号"/> --%>
				<ec:column property="code" title="编码"  style="text-align:center;" />
				<ec:column property="name" title="名称" />
				<c:choose>
					<c:when test="${not empty energy.unit.cname }">
						<ec:column property="unitID" title="实物量计量单位">${energy.unit.cname}</ec:column>
					</c:when>
					<c:when test="${energy.unitID == 0 }">
						<ec:column property="unitID" title="实物量计量单位">未设置计量单位</ec:column>
					</c:when>
					<c:when test="${ empty energy.unit }">
						<ec:column property="unitID" title="实物量计量单位">计量单位没有找到，编号为：${energy.unitID}</ec:column>
					</c:when>
					<c:when test="${ empty energy.unit.cname }">
						<ec:column property="unitID" title="实物量计量单位">此计量单位没有名字，编号为：${energy.unitID}</ec:column>
					</c:when>
					<c:otherwise>
						<ec:column property="unitID" title="计量单位" />
					</c:otherwise>
				</c:choose>
				<%-- <c:choose>
					<c:when test="${not empty energy.parent.name }">
						<ec:column property="PID" title="上级能源介质">${energy.parent.name}</ec:column>
					</c:when>
					<c:when test="${energy.PID == 0 }">
						<ec:column property="PID" title="上级能源介质">没有上级能源介质</ec:column>
					</c:when>
					<c:when test="${ empty energy.parent }">
						<ec:column property="PID" title="上级能源介质">上级能源介质没有找到，编号为：${energy.PID}</ec:column>
					</c:when>
					<c:when test="${ empty energy.parent.name }">
						<ec:column property="PID" title="上级能源介质">此上级能源介质没有名字，编号为：${energy.PID}</ec:column>
					</c:when>
					<c:otherwise>
						<ec:column property="PID" title="上级能源介质" />
					</c:otherwise>
				</c:choose> --%>
				<ec:column property="dataAccuracy" title="实物量数值精度"  style="text-align:center;" />
				<ec:column property="referRatio" title="参考拆标系数"  style="text-align:center;" />
				 <c:if test="${empty energy.standardNumID}">
		                 <ec:column property="standardNumID" title="拆标单位分子名称" >未设置拆标单位分子</ec:column>
		            </c:if>
		             <c:if test="${not empty energy.standardNumID}">
		             <ec:column property="standardNumID" title="拆标单位分子名称" >${energy.standardNum.cname}</ec:column>
		            </c:if>
		            <c:if test="${empty energy.standardDenID}">
		               <ec:column property="standardDenID" title="拆标单位分母名称" >未设置拆标单位分母</ec:column>
		            </c:if>
		             <c:if test="${not empty energy.standardDenID}">
		           <ec:column property="standardDenID" title="拆标单位分母名称" >${energy.standardDen.cname}</ec:column>
		            </c:if>

				<ec:column property="edit" title="修改" sortable="false"
					viewsAllowed="html" style="width: 30px">
					<A href="${ctx}/energy/edit/${energy.ID}"><center>
							<img src="<c:url value="/static/images/icon/16x16/modify.gif"/>"
								border="0" />
						</center></A>
				</ec:column>
				<ec:column property="checkbox" title="删除" sortable="false"
					viewsAllowed="html" style="width: 30px">
					<A href="${ctx}/energy/delete/${energy.ID}"
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
