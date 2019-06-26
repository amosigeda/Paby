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
		frmGo.submit();
	}
	function c(){
   		document.all.serieNo.value="";
   		document.all.belongProject.value=""; 
	} 

	</script>
	<body>
		<span class="title_1"></span>
		<form name="frmGo" method="post" action="doSettingInfo.do?method=querySettingInfo">						
			<table width="100%" class="table" border=0 cellpadding="0" cellspacing="1">
               <tr>
                <th colspan="14" nowrap="nowrap" align="left">
                                            设备设置信息
                </th>
                </tr>            
                 <tr class="title_3">
                       <td colspan="14">					
						IMEI				
						    <input id="serieNo" name="serieNo" type="text" class="txt_1" 
						    value="<%CommUtils.printReqByAtt(request,response,"serieNo");%>" size="15">
						项目ID				
						    <input id="belongProject" name="belongProject" type="text" class="txt_1" 
						    value="<%CommUtils.printReqByAtt(request,response,"belongProject");%>" size="9">
						<input name="find1" type="button" class="but_1" accesskey="f"
							tabindex="f" value="搜 索" onclick="javascript:finds()">
					     <input name="clear" type="button" class="but_1" accesskey="c"
						    tabindex="c"  value="清除搜索" onclick="c()">
				</tr> 
                  <tr class="title_2">     
                  	<td width="10%">设备IMEI</td>
                  	<td width="8%" >项目ID</td>            	
                  	<td width="8%" >GPS开关</td>
                  	<td width="8%" >是否脱落</td>
                  	<td width="8%" >定位频率</td>                  	                           						 
				</tr>
 
				<logic:iterate id="element" name="pageList">
					<tr class="tr_5" onmouseover='this.className="tr_4"' onmouseout='this.className="tr_5"' >
						<td><bean:write name="element" property="serie_no"/></td>						
						<td><bean:write name="element" property="belong_project"/></td> 
						<td>
							<logic:equal name="element" property="gps_on" value="0">关闭</logic:equal>
							<logic:equal name="element" property="gps_on" value="1">打开</logic:equal>
						</td>
						<td>
							<logic:equal name="element" property="fall" value="1">
								<font style="color:green;font-size: 20px;">√</font>
							</logic:equal>
							<logic:equal name="element" property="fall" value="0">
								<font style="color:red;font-size: 20px;">×</font>
							</logic:equal>
						</td>
						<td><bean:write name="element" property="light"/></td>												
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