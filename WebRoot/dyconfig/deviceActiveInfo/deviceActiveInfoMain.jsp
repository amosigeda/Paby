<%@ page language="java" contentType="text/html;charset=gb2312"%>
<%@ page import="com.godoing.rose.http.common.*"%>
<%@ page import="com.wtwd.common.lang.*"%>
<%@ page import="com.wtwd.common.config.Config"%>
<%@ page import="com.wtwd.app.LoginUser"%>
<%@ taglib uri="/WEB-INF/struts-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic" prefix="logic"%>
<%@ page autoFlush="true"%>
<%
	/*页面属性*/
	PagePys pys = (PagePys) request.getAttribute("PagePys");
	LoginUser loginUser = (LoginUser)request.getSession().getAttribute(Config.SystemConfig.LOGINUSER); 
%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>无标题文档</title>
		<link href="<%=request.getContextPath()%>/css/tbls.css"
			rel="stylesheet" type="text/css">
		<script language="JavaScript"
			src="<%=request.getContextPath()%>/public/public.js"></script>   <!-- 调用此方法 -->
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
    document.all.serieNo.value="";
    document.all.userName.value="";
    document.getElementById("belongProject").options[0].selected=true;
} 

</script>
	<body>
		<span class="title_1"></span>
		<form name="frmGo" method="post"
			action="doDeviceActiveInfo.do?method=queryDeviceActiveInfo">
			<% if(request.getAttribute("deviceImei") != null && !request.getAttribute("deviceImei").equals("")){%>
			<table class="table_1" style="font-size:14px;margin-bottom:5px;">
			   <tr>  
			     <td>
			                      当前IMEI:
			              <font color="#FFA500">
			               <strong ><%=request.getAttribute("deviceImei") %></strong>
			               <input type="hidden" name="deviceImei" value="<%=request.getAttribute("deviceImei") %>"/>
			            </font>
			     </td>
			   </tr>
			</table>
		 <%} %>		 
			<table width="100%" class="table" border=0 cellpadding="0" cellspacing="1">
               <tr>
                <th colspan="14" nowrap="nowrap" align="left">
                                           激活设备信息
                </th>
                </tr>
                <% if(request.getAttribute("deviceImei") == null){%>
                 <tr class="title_3">
                       <td colspan="14">
					 激活时间
                     <input name="startTime" type="text" class="txt_1"  id="startTime" style="cursor:text"
								value="<%CommUtils.printReqByAtt(request,response,"fNow_date");%>" onclick="WdatePicker()"
								size="9" readonly> -
							<input name="endTime" type="text" class="txt_1" id="endTime" style="cursor:text"
								value="<%CommUtils.printReqByAtt(request,response,"now_date");%>" onclick="WdatePicker()"
								size="9" readonly>						
					IMEI
						 <input id="serieNo" name="serieNo" type="text" class="txt_1" 
						    value="<%CommUtils.printReqByAtt(request,response,"serieNo");%>" size="15">
				        <!--   设备号码
						 <input id="phoneNumber" name="phoneNumber" type="text" class="txt_1" 
						    value="<%CommUtils.printReqByAtt(request,response,"phoneNumber");%>" size="9"> -->
					 主亲情号码
						 <input id="userName" name="userName" type="text" class="txt_1" 
						    value="<%CommUtils.printReqByAtt(request,response,"userName");%>" size="9">	
					项目
							<%String belongProject = (String)request.getAttribute("belongProject"); %>
							<select id="belongProject" name="belongProject">
								<option value="">全部</option>
								<logic:iterate id="pro" name="project">
									<bean:define id="projectId" name="pro" property="id" type="java.lang.Integer" />																	
									<option value='<%=projectId %>' <%=String.valueOf(projectId).equals(belongProject)? "selected" : "" %>><bean:write name="pro" property="project_name"/></option>
								</logic:iterate>
							</select>
						<input name="find1" type="button" class="but_1" accesskey="f"
							tabindex="f" value="搜 索" onclick="javascript:finds()">
					     <input name="clear" type="button" class="but_1" accesskey="c"
						    tabindex="c"  value="清除搜索" onclick="c()">
				</tr> 
				<%} %>
				<%int i=1; %>
                  <tr class="title_2">
                  	<td width="9%" >激活时间</td>
                  	<td width="10%" >IMEI</td>
                  	<td width="6%" >项目</td>
                  	<td width="8%" >设备号码</td> 
                  	<td width="5%" >绑定次数</td>
                  	<td width="5%" >主亲情号码</td>
                  	<td width="6%" >主亲情ID</td>
                  	<td width="5%" >设备ID</td>
                  	<td width="7%" >设备昵称</td>
                  	<!-- <td width="5%" >性别</td>
                  	<td width="8%" >生日</td>
                  	<td width="6%" >身高(cm)</td>
                  	<td width="6%" >体重(kg)</td> -->
                  	<td width="6%" >头像</td>
                  	
				</tr>
 
				<logic:iterate id="element" name="pageList">
					<tr class="tr_5" onmouseover='this.className="tr_4"' onmouseout='this.className="tr_5"' >						
						<td><bean:write name="element" property="device_update_time" format="yyyy-MM-dd HH:mm:ss"/></td>
						<td><bean:write name="element" property="device_imei" /></td>
						<td><bean:write name="element" property="project" /></td>
						<td><bean:write name="element" property="device_phone"/></td>
						<td><bean:write name="element" property="count"/></td>
						<td><bean:write name="element" property="user_name"/></td>						
						<td>
							<a style="color: #0000FF"
								href="../appUserInfo/doAppUserInfo.do?method=queryAppUserInfo&user_id=<bean:write name="element" property="user_id" />">
								<bean:write name="element" property="user_id"/>
							</a>
						</td>
						<td><bean:write name="element" property="id" /></td>					
						<td><bean:write name="element" property="device_name" /></td>
						<%-- <td>
							<logic:equal name="element" property="device_sex" value="0">男</logic:equal> 
							<logic:equal name="element" property="device_sex" value="1">女</logic:equal>
						</td>
						<td><bean:write name="element" property="device_age" /></td>	
						<td><bean:write name="element" property="device_height" /></td>
						<td><bean:write name="element" property="device_weight" /></td> --%>
						<td>
							<logic:equal name="element" property="device_head" value="0">无</logic:equal>
							<logic:notEqual name="element" property="device_head" value="0">
								<a href=# onclick="onView('<bean:write name="element" property="image"/>')" style="color:#0000FF">下载</a>
							</logic:notEqual>							
						</td>								
														 						
					</tr>
				</logic:iterate> 

			  	<tr class="title_3">
					<td colspan="14" align="left" >  
						<%
							pys.printGoPage(response, "frmGo");
						%>
					</td>
				</tr>  
			</table>
		</form>
	</body>
</html>