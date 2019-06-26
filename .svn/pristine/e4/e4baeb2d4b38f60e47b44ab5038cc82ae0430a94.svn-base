<%@ page language="java" contentType="text/html;charset=gb2312"%>
<%@ page import="com.godoing.rose.http.common.*"%>
<%@ page import="com.godoing.rose.lang.*"%>
<%@ page import="com.wtwd.common.lang.*"%>
<%@ page import="com.wtwd.common.config.Config"%>
<%@ page import="com.wtwd.app.LoginUser"%>
<%@ taglib uri="/WEB-INF/struts-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic" prefix="logic"%>
<%@ page autoFlush="true"%>
<%
	/*页面属性*/
	PagePys pys = (PagePys) request.getAttribute("PagePys");
	int count = 1;	
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>无标题文档</title>
		<link href="<%=request.getContextPath()%>/css/tbls.css"
			rel="stylesheet" type="text/css">
		<script language="JavaScript"
			src="<%=request.getContextPath()%>/public/public.js"></script>
		<script language="JavaScript"
			src="<%=request.getContextPath()%>/public/My97DatePicker/WdatePicker.js"></script>
	</head>
	<script language="javascript">
function finds(){
    var st = new Date(frmGo.startTime.value.replace(/-/g,'/'));
	var et = new Date(frmGo.endTime.value.replace(/-/g,'/'));
	if(Date.parse(st) - Date.parse(et)>0){
		alert("开始时间不能大于结束时间!");
		return false;
	}
	   frmGo.submit();
}

function c(){
    document.all.startTime.value="";
    document.all.endTime.value="";
    document.all.userId.value="";
    document.all.belongProject.value="";
}

</script>
	<body>
		<form name="frmGo" method="post" action="doFeedBackInfo.do?method=queryFeedBackInfo">
			<table width="100%" class="table" border=0 cellpadding="0" cellspacing="1">
               <tr>
                <th colspan="13" nowrap="nowrap" align="left">
                                                            意见反馈
                </th>
                </tr>
                   <tr class="title_3">			
				    <td colspan="13">
				    	反馈时间		
    					<input name="startTime" type="text" class="txt_1"  id="startTime" style="cursor:text"
								value="<%CommUtils.printReqByAtt(request,response,"fNow_date");%>" onclick="WdatePicker()"
								size="7" readonly> -
				    	<input name="endTime" type="text" class="txt_1"  id="endTime" style="cursor:text"
								value="<%CommUtils.printReqByAtt(request,response,"now_date");%>" onclick="WdatePicker()"
								size="7" readonly>
						用户ID
						    <input id="userId" name="userId" type="text" class="txt_1" 
						    value="<%CommUtils.printReqByAtt(request,response,"userId");%>" size="9">
						项目ID
						    <input id="belongProject" name="belongProject" type="text" class="txt_1" 
						    value="<%CommUtils.printReqByAtt(request,response,"belongProject");%>" size="9">
						<input name="find1" type="button" class="but_1" accesskey="f"
							tabindex="f" value="搜 索" onclick="javascript:finds()">
							 <input name="clear" type="button" class="but_1" accesskey="c"
						    tabindex="c"  value="清除搜索" onclick="c()">
						</td>					
				</tr>
                 <tr class="title_2">                 	
                    <td width="6%">用户ID</td>
                    <td width="6%">项目ID</td>                  
					<td width="20%">反馈内容</td>	
					<td width="10%">反馈时间</td>															
				</tr>
				<logic:iterate id="element" name="pageList">
					<tr class="tr_5" onmouseover='this.className="tr_4"' onmouseout='this.className="tr_5"' >					 							
						<td>
							<bean:write name="element" property="user_id" />
						</td>
						<td>
							<bean:write name="element" property="belong_project" />
						</td>
						<td align="left">
							<bean:write name="element" property="user_feedback_content" />
						</td>
						<td>
							<bean:write name="element" property="date_time" format="yyyy-MM-dd HH:mm:ss"/>
						</td>
						
					</tr>
				</logic:iterate>
				
				<tr class="title_3">			
					<td colspan="6" align="left">
					  <% 
					  pys.printGoPage(response, "frmGo");
						%>
					</td>
				</tr>
				
			</table>
		</form>
	</body>
</html>
