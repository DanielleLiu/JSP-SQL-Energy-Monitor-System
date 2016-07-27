<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ec" uri="http://www.extremecomponents.org"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<link href="<c:url value="/static/components/extremetable/Styles/extremecomponents.css"/>" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${ctx}/static/components/jquery-easyui-1.4/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/components/jquery-easyui-1.4/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/components/jquery-easyui-1.4/themes/icon.css">
<script type="text/javascript" src="${ctx}/static/components/jquery-easyui-1.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/common.js"></script>
<script type="text/javascript">
//双击名称编辑
function goViewList(value){
			  var url ="${ctx}/entkpi/edit/"+value;
			  window.location.href=url;
}
</script>
</head>

<body>
<div class="operations">
		<div class="operationquery">
			<form class="form-inline" role="form" style="margin: 0 0 3px 0;" id="queryform" name="queryform" method="post" action="${ctx}/entkpi/list">
				&nbsp;&nbsp;企业指标名称
				 <input id="name"    name="name"   value="${name}"   style="border-radius:6px;border: 1px solid #7AA7EB;width: 220px;height: 25px"  
				onkeypress="enter();">
				&nbsp;&nbsp;
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="submitForm()">查询</a>
			</form>
		</div>
		</div>
<div>
			<span style="float: left;"> 
				<c:if test="${not empty message}">
					<div style="color: red;" id="message">&nbsp;&nbsp;&nbsp;&nbsp;${message}</div>
				</c:if>
			</span>
			<span class="btn green fileinput-button" style="float: right;"> <i class="icon-plus icon-white"></i> <A href="${ctx}/entkpi/add" class="easyui-linkbutton" iconCls="icon-add">增加</A>&nbsp;&nbsp;&nbsp;&nbsp;
			</span>
		</div>



		<div class="table-responsive">
		<ec:table items="list1" var="entKPI" action="${ctx}/entkpi/list"
		    		retrieveRowsCallback="limit" 
		            sortRowsCallback="limit" 
		            rowsDisplayed="20"
		            filterable="false"
		            sortable="false"
		            autoIncludeParameters="${empty param.autoInc?'true':'false'}">
		        <ec:row>
		        		<ec:exportXls fileName="list1.xls" tooltip="导出 Excel" />
		            <ec:column property="rowcount" cell="rowCount" sortable="false" title="序号" style="text-align:center;" />
		            		       
		             <ec:column property="entKPIName" title="企业指标名称  (可双击编辑)"><p ondblclick="goViewList(${entKPI.ID})">${entKPI.entKPIName}</p></ec:column>
		          
		            <ec:column property="entKPI" title="能效指标">${entKPI.energyKPI.name} </ec:column>
		            <ec:column property="edit" title="修改" sortable="false" viewsAllowed="html" style="width: 30px">
		               <A href="${ctx}/entkpi/edit/${entKPI.ID}"><center> <img src="<c:url value="/static/images/icon/16x16/modify.gif"/>" border="0" /></center></A>
		            </ec:column>
		            <ec:column property="checkbox" title="删除" sortable="false" viewsAllowed="html" style="width: 30px">
		                <A href="${ctx}/entkpi/delete/${entKPI.ID}" onclick="return(confirm('确定刪除？'))"> <center><img src="<c:url value="/static/images/icon/16x16/delete.gif"/>" border="0" /></center>
							</A>
		            </ec:column>
		        </ec:row>
		    </ec:table>
		    </div>
</body>
</html>
