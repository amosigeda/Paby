<%@ page language="java" contentType="text/html;charset=gb2312"%>
<%@ page import="java.text.*" %>
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
		frmGo.submit();
	}
	function c(){
   		document.all.startTime.value="";
   		document.all.endTime.value=""; 
   		document.getElementById("belongProject").options[0].selected=true;  
	} 

	</script>
	<body>
		<span class="title_1"></span>
		<form name="frmGo" method="post" action="doSaleInfo.do?method=queryDayAddInfo">						
			<table width="100%" class="table" border=0 cellpadding="0" cellspacing="1">
               <tr>
                <th colspan="14" nowrap="nowrap" align="left">
                                            日新增数据
                </th>
                </tr>            
                 <tr class="title_3">
                       <td colspan="14">					
						日期
                     	<input name="startTime" type="text" class="txt_1"  id="startTime" style="cursor:text"
								value="<%CommUtils.printReqByAtt(request,response,"fNow_date");%>" onclick="WdatePicker()"
								size="9" readonly> -
							<input name="endTime" type="text" class="txt_1" id="endTime" style="cursor:text"
								value="<%CommUtils.printReqByAtt(request,response,"now_date");%>" onclick="WdatePicker()"
								size="9" readonly>
						项目名
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
				
                  <tr class="title_2">  
                  	<td width="10%" >项目</td>   
                  	<td width="10%">日期</td>                   	           	
                  	<td width="8%" >新增APP注册</td> 
                  	<td width="8%" >总绑定设备</td>
                  	<td width="8%" >新增绑定</td>
                  	<td width="8%" >新增解绑</td>
                  	<td width="8%" >设备登录</td>
                  	<td width="8%" >设备活跃</td>
                  	<td width="8%" >APP登录</td> 
                  	<td width="8%" >APP活跃</td>                         						 
				</tr>
 				
				<logic:iterate id="element" name="pageList">
					<tr class="tr_5" onmouseover='this.className="tr_4"' onmouseout='this.className="tr_5"' >						
						<td><bean:write name="element" property="project_name"/></td>
						<td><bean:write name="element" property="date_time" format="yyyy-MM-dd"/></td>						
						<td>
							<logic:empty name="element" property="count_appuser">0</logic:empty>
							<logic:notEmpty name="element" property="count_appuser">
								<bean:write name="element" property="count_appuser"/>
							</logic:notEmpty>
						</td>
						<td>
							<logic:empty name="element" property="count_device_active">0</logic:empty>
							<logic:notEmpty name="element" property="count_device_active">
								<bean:write name="element" property="count_device_active"/>
							</logic:notEmpty>
						</td>
						<td>
							<logic:empty name="element" property="bind_count">0</logic:empty>
							<logic:notEmpty name="element" property="bind_count">
								<bean:write name="element" property="bind_count"/>
							</logic:notEmpty>
						</td>
						<td>
							<logic:empty name="element" property="unbind_count">0</logic:empty>
							<logic:notEmpty name="element" property="unbind_count">
								<bean:write name="element" property="unbind_count"/>
							</logic:notEmpty>
						</td>
						<td>
							<logic:empty name="element" property="count_device_login">0</logic:empty>
							<logic:notEmpty name="element" property="count_device_login">
								<bean:write name="element" property="count_device_login"/>
							</logic:notEmpty>
						</td>
						<td>
							<logic:empty name="element" property="active_device">0</logic:empty>
							<logic:notEmpty name="element" property="active_device">
								<bean:write name="element" property="active_device"/>
							</logic:notEmpty>
						</td>
						<td>
							<logic:empty name="element" property="count_userlogin">0</logic:empty>
							<logic:notEmpty name="element" property="count_userlogin">
								<bean:write name="element" property="count_userlogin"/>
							</logic:notEmpty>
						</td>
						<td>
							<logic:empty name="element" property="active_user">0</logic:empty>
							<logic:notEmpty name="element" property="active_user">
								<bean:write name="element" property="active_user"/>
							</logic:notEmpty>
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