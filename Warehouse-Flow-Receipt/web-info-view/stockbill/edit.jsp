<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="entityPath" value="/stockbill" />

<html>
<head>
<script type="text/javascript" src="${ctx}/static/components/jquery-easyui-1.4/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/components/jquery/validation/jquery.validate.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/static/components/jquery/validation/messages_zh.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/components/jquery-easyui-1.4/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/components/jquery-easyui-1.4/themes/icon.css">
<script type="text/javascript" src="${ctx}/static/components/jquery-easyui-1.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/components/jquery-easyui-1.4/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/static/js/common.js"></script>


<script>
        function DelRow(r){
        var table=document.getElementById("stocktable");
        var i=r.parentNode.parentNode.rowIndex;
        table.deleteRow(i);
        //var length=table.rows.length;
        //table.deleteRow(length-1);
/* 		for (var i=length-1;i>length-3;i--){
			table.deleteRow(i);
		} */
	/* 	if (length==3){
			document.getElementById("stocktableheader").style.display='none';
		}
     */    
     	}

        function addRow(obj)
        {
	       	var table=document.getElementById("stocktable");
	
		//	document.getElementById("stocktableheader").style.display='block';
/* 	       	var newBr=table.insertRow();
			newBr.innerHTML='<br>'; */
	        var newTr = table.insertRow();
	        newTr.align="center";
	
	        var newTd0 = newTr.insertCell();
	        var newTd1 = newTr.insertCell();
	        var newTd2 = newTr.insertCell();
	        var newTd3 = newTr.insertCell();
	        var newTd4 = newTr.insertCell();
	        var newTd5 = newTr.insertCell();
	        var newTd6 = newTr.insertCell();        
	        var newTd7 = newTr.insertCell();
	        var newTd8 = newTr.insertCell();
	        var newTd9 = newTr.insertCell();
	        var newTd10 = newTr.insertCell();
	                
	        var length=table.rows.length;
	        var index=(length-2);
	        newTd0.innerHTML = '<td style="width:50px;" class="cent">'+(index+1)+'</td>'; 
	      	newTd1.innerHTML='<td><input id="se'+index+'" name="billList['+index+'].storageEngid" value=""/></td>'; 
	        newTd2.innerHTML = '<td><input type="text" name="billList['+index+'].heatvalue" id="heatvalue'+index+'" style="width:100px;" value="${BillDetail.heatvalue}" /></td>'; 
	        newTd3.innerHTML = '<td><input type="text" name="billList['+index+'].heatmultiple" id="heatmultiple'+index+'" style="width:100px;" value="${BillDetail.heatmultiple}" /></td>'; 
	        newTd4.innerHTML = '<td><input type="text" name="billList['+index+'].energyvalue" id="energyvalue'+index+'" style="width:100px;" value="${BillDetail.energyvalue}" /></td>'; 
	        newTd5.innerHTML = '<td><input type="text" name="billList['+index+'].energymultiple" id="energymultiple'+index+'" style="width:100px;" value="${BillDetail.energymultiple}" /></td>'; 
	        newTd6.innerHTML = '<td><input type="text" name="billList['+index+'].quantity" id="quantity'+index+'" style="width:100px;" value="${BillDetail.quantity}" /></td>'; 
	        newTd7.innerHTML = '<td><input type="text" name="billList['+index+'].sumbalance" id="sumbalance'+index+'"style="width:150px;" value="${BillDetail.sumbalance}" /></td>'; 
	        newTd8.innerHTML = '<td><input type="text" name="billList['+index+'].sumheat" id="sumheat'+index+'" style="width:150px;" value="${BillDetail.sumheat}" /></td>'; 
	        newTd9.innerHTML = '<td><input type="text" name="billList['+index+'].sumtce" id="sumtce'+index+'" style="width:150px;" value="${BillDetail.sumtce}" /></td>'; 
	        newTd10.innerHTML= '<a href="#" onclick="DelRow(this)"><center><img src="<c:url value="/static/images/icon/16x16/delete.gif"/>" border="0" /></center></a>';
	        
	        
 	    	var storageid=$("#storageid").val();
	        for (var i=0;i<=index;i++){
		        $('#se'+i).combotree({
		        	//onChange: function()
		        	//url: '${ctx}/stockbill/getStorageEnergies?id='+storageid,
		        	url: '${ctx}/stockbill/getStorageEnergies?id=1',		
		        	required: false,
		        	valueField: 'id', 
		        	textField: 'text',
		        	method: 'get',
		        	editable: false
		        });
	        } 
	        $('#heatvalue'+index).textbox({
	        });
	        $('#heatmultiple'+index).textbox({
	        });
	        $('#energyvalue'+index).textbox({
	        });
	        $('#energymultiple'+index).textbox({
	        });
	        $('#quantity'+index).textbox({
	        });
	        $('#sumbalance'+index).textbox({
	        });
	        $('#sumheat'+index).textbox({
	        });
	        $('#sumtce'+index).textbox({
	        });
	        
        }
        
     function pageload(){
        var storageid=$("#storageid").val();
        var table=document.getElementById("stocktable");
        var length=table.rows.length;
        var index=length-1;
        for (var i=0;i<=index;i++){
	        $('#se'+i).combotree({
	        	url: '${ctx}/stockbill/getStorageEnergies?id='+storageid,
	        	required: false,
	        	valueField: 'id', 
	        	textField: 'text',
	        	method: 'get',
	        	editable: false
	        });
	    	$('#se'+i).combotree('setValue', 'billList['+index+'].storageEngid');
        }
/*         for (var i=0;i<=index;i++){
    	$('#se'+i).combotree({
    		onChange: function (n,o) {
    			url = "${ctx}/stockbill/getStorageEnergies?id="+n;
    			$("#tunitid").combotree({
    			    url:url,
    			    method:'get',
    			    valueField:'id',
    			    textField:'text'
    			});
    			$('#se'+i).combotree('setValue', 0);    
    			}
    	}); */
    	
    	
        } 


    </script>
	

</head>
<body>
<body onload="pageload();">

	<div class="result">
		<form id="editform" name="editform"  action="${ctx}${entityPath}/save/${billtype}" method="post">

			<fieldset>
				<c:choose>
		        	<c:when test="${billtype=='1'}">
						<legend>入库单信息</legend>
					</c:when>
			        <c:when test="${billtype=='-1'}">
						<legend>出库单信息</legend>
					</c:when>
					<c:otherwise>
						<legend>盘库单信息</legend>
					</c:otherwise>
				</c:choose>	
				
				<table 
					style="font-size: 12px; font-weight: bold;  color: #666666; border-collapse: collapse; border: #D9D9D9 1px solid; width:100%;">
				<input type="hidden" name="id" id="id" value="${StockBill.id}"/>
				<input type="hidden" name="billtype" id="billtype" value="${StockBill.billtype}"/>
				<!-- add有type，edit无type，不可edit type -->
				
					<tr style="border: #D9D9D9 1px solid;" >
					<td  style="border: #D9D9D9 1px solid;" >仓库名称</td>
					<td   colspan="3"    style="border: #D9D9D9 1px solid;" >
						<input type="text" id="storageid" class="easyui-combotree" name="storageid" 
			             data-options="valueField:'id',textField:'text',editable:false,url:'${ctx}/energyaccountunit/getAreas_unitorstorage?id=${empty EnergyAccountUnit.id ? '0' :  EnergyAccountUnit.id}',method:'get',required:false,value:'${empty StockBill.storageid ? '0' : StockBill.storageid}'" />
			<!-- 				$('#tunitid').combotree('setValue', tunitid); -->
						</td>
					</tr>
					
					<c:choose>
		        	<c:when test="${billtype=='1'}">
						<tr style="border: #D9D9D9 1px solid;">
							<td class="left" style="width: 20%;">入库时间</td>
							<td class="right" style="border: #D9D9D9 1px solid;" colspan=3>
							<input class="easyui-datebox" id="busTime" name="busTime"  value="${StockBill.busTime}"/>
							</td>
						</tr>
					</c:when>
		        	<c:when test="${billtype=='-1'}">
						<tr style="border: #D9D9D9 1px solid;">
							<td class="left" style="width: 20%;">出库时间</td>
							<td class="right" style="border: #D9D9D9 1px solid;" colspan=3>
							<input class="easyui-datebox" type="text" id="busTime" name="busTime" value="${StockBill.busTime}"/>
							</td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr style="border: #D9D9D9 1px solid;">
							<td class="left" style="width: 20%;">入库时间</td>
							<td class="right" style="border: #D9D9D9 1px solid;" colspan=3>
							<input class="easyui-datebox" type="text" id="busTime" name="busTime" value="${StockBill.busTime}"/>
							</td>
						</tr>
					</c:otherwise>
					</c:choose>
					
					<tr style="border: #D9D9D9 1px solid;">
						<td class="left" style="width: 20%;">备注</td>
						<td><input type="text" name="memo" id="memo" class="easyui-textbox" value="${StockBill.memo}" />
						</td>
					</tr>
					
<%--  					<tr style="border: #D9D9D9 1px solid;" >
					<td  style="border: #D9D9D9 1px solid;" >仓库介质名称</td>
					<td   colspan="3"    style="border: #D9D9D9 1px solid;" >
						<input type="text" id="test" class="easyui-combotree" name="test" 
			             data-options="valueField:'id',textField:'text',editable:false,url:'${ctx}/stockbill/getStorageEnergies?id=1',method:'get',required:false,value:''" />
						</td>
					</tr> --%>
					
				</table>
			</fieldset>
			<br>
			
			<fieldset id="stocktableheader" >
				<legend>单据记录明细</legend>
			<table id="stocktable" border='1'  style='font-size: 12px;border-color: #DBDEDF font-weight: bold;  color: #666666; border-collapse: collapse; border: #D9D9D9 1px solid;'>
              
				<tr style="background-color: #F7E390">
					<th style="display: none"></th>
					<th style="width:50px;">序号</th>
					<th style="width:250px;">仓库介质</th>
					<th style="width:100px;">热值</th>
					<th style="width:100px">热值系数</th>
					<th style="width:100px;">折标量</th>
					<th style="width:100px;">折标系数</th>
					<th style="width:100px;">数量</th>
					<th style="width:150px;">当前库存数</th>
					<th style="width:150px;">当前库存热量</th>
					<th style="width:150px;">当前库存折标量</th>
					<th style="width:40px;text-align:center;">删除</th>
				</tr>
				
				<c:forEach items="${BillDetailList}" var="list" varStatus="status">
	 			<tr>
	 			<td style="display: none"></td>
	 			<td style="width:50px;" >${status.index+1}</td>
	 			<td><input id="se'+index+'" data-options="valueField:'id',textField:'text',editable:false,url:'${ctx}/stockbill/getStorageEnergies?id=${empty list.storageEngid ? '0' :  list.storageEngid}',method:'get',required:false,value:'',method:'get',required:false," 
	 			name="billList[${status.index}].storageEngid" value="${list.storageEngid}"/></td>
				<td><input type="text" class="easyui-textbox" style="width:100px;" name="billList[${status.index}].heatvalue" id="heatvalue" value="${list.heatvalue}"/></td>
				<td><input type="text" class="easyui-textbox" style="width:100px;" name="billList[${status.index}].heatmultiple" id="heatmultiple" value="${list.heatmultiple}"/></td>
				<td><input type="text" class="easyui-textbox" style="width:100px;" name="billList[${status.index}].energyvalue" id="energyvalue" value="${list.energyvalue}"/></td>
				<td><input type="text" class="easyui-textbox" style="width:100px;" name="billList[${status.index}].energymultiple" id="energymultiple" value="${list.energymultiple}"/></td>
				<td><input type="text" class="easyui-textbox" style="width:100px;" name="billList[${status.index}].quantity" id="quantity" value="${list.quantity}"/></td>
				<td><input type="text" class="easyui-textbox" style="width:100px;" name="billList[${status.index}].sumbalance" id="sumbalance" value="${list.sumbalance}"/></td>
				<td><input type="text" class="easyui-textbox" style="width:100px;" name="billList[${status.index}].sumheat" id="sumheat" value="${list.sumheat}"/></td>
				<td><input type="text" class="easyui-textbox" style="width:100px;" name="billList[${status.index}].sumtce" id="sumtce" value="${list.sumtce}"/></td>				
	 			<td><a href="#" onclick="DelRow(this)"><center><img src="<c:url value="/static/images/icon/16x16/delete.gif"/>" border="0" /></center></a></td>

<%-- 	 			<td ><A href="${ctx}${entityPath}/deleteeng/${list.id}" onclick="return(confirm('确定刪除？'))">
	 			<center><img src="<c:url value="/static/images/icon/16x16/delete.gif"/>" border="0" /></center></A></td> --%>

	 			 		
	 			</tr>

	 			</c:forEach>
				
			</table>
			</fieldset>
			<br>
			<br>
				
		
			<div style="float: left;margin-left: 200px">
				<a id="addDetail" href="#"  class="easyui-linkbutton" iconCls="icon-add" onclick="addRow(this)" style="color:blue">添加表单明细</a>&nbsp;&nbsp;&nbsp;&nbsp;
				<a id="submitlink" href="#" class="easyui-linkbutton"
					data-options="iconCls:'icon-save'" onclick="submitEditForm()">保存</a>				
				<a href="#" class="easyui-linkbutton"
					data-options="iconCls:'icon-back'" onclick="javascript:history.go(-1)">返回</a>
			</div>
		</form>
	</div>

</body>
</html>