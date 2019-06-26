<%@ page language="java" contentType="text/html;charset=gb2312"%>
<%@ page import="com.godoing.rose.http.common.*"%>
<%@ page import="com.wtwd.common.lang.*"%>
<%@ page import="com.wtwd.common.config.Config"%>
<%@ page import="com.wtwd.app.LoginUser"%>
<%@ taglib uri="/WEB-INF/struts-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic" prefix="logic"%>
<%@ page autoFlush="true"%>
<%
	/*ҳ������*/
	PagePys pys = (PagePys) request.getAttribute("PagePys");
	LoginUser loginUser = (LoginUser)request.getSession().getAttribute(Config.SystemConfig.LOGINUSER); 
%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>�ޱ����ĵ�</title>
		<link href="<%=request.getContextPath()%>/css/tbls.css"
			rel="stylesheet" type="text/css">
		<script language="JavaScript"
			src="<%=request.getContextPath()%>/public/public.js"></script>   <!-- ���ô˷��� -->
		<script language="JavaScript"
			src="<%=request.getContextPath()%>/public/My97DatePicker/WdatePicker.js"></script>
	</head>
	<script language="javascript">
function finds(){
    var st = new Date(frmGo.startTime.value.replace(/-/g,'/'));
	var et = new Date(frmGo.endTime.value.replace(/-/g,'/'));
	if(Date.parse(st) - Date.parse(et)>0){
		alert("��ʼʱ�䲻�ܴ��ڽ���ʱ��!");
		return false;
	}
	   frmGo.submit();
}
function add(){
	frmGo.action = "doProjectInfo.do?method=initInsert";
	frmGo.submit();
}
function c(){
    document.all.startTime.value="";
    document.all.endTime.value="";
    document.all.projectName.value="";
    document.all.userId.value="";
} 
</script>
	<body>
		<span class="title_1"></span>
		<form name="frmGo" method="post" action="doProjectInfo.do?method=queryProjectInfo">
			<% if(request.getAttribute("companyId") != null && !request.getAttribute("companyId").equals("")){%>
			<table class="table_1" style="font-size:14px;margin-bottom:5px;">
			   <tr>  
			     <td>
			                      ��ǰ�ͻ�ID:
			              <font color="#FFA500">
			               <strong ><%=request.getAttribute("companyId") %></strong>
			               <input type="hidden" name="companyId" value="<%=request.getAttribute("companyId") %>"/>
			            </font>
			     </td>
			   </tr>
			</table>
		 <%} %>
			<table width="100%" class="table" border=0 cellpadding="0" cellspacing="1">
               <tr>
                <th colspan="12" nowrap="nowrap" align="left">
                                               ��Ŀ��Ϣ
                     <input type="button" class="but_1" accesskey="a"
							tabindex="a" value="�� ��" onclick="add()">
                </th>
                </tr>
                 <tr class="title_3">
                       <td colspan="13">
					  ����ʱ��
                     <input name="startTime" type="text" class="txt_1"  id="startTime" style="cursor:text"
								value="<%CommUtils.printReqByAtt(request,response,"fNow_date");%>" onclick="WdatePicker()"
								size="9" readonly> -
							<input name="endTime" type="text" class="txt_1" id="endTime" style="cursor:text"
								value="<%CommUtils.printReqByAtt(request,response,"now_date");%>" onclick="WdatePicker()"
								size="9" readonly>
						��Ŀ����			
						    <input id="projectName" name="projectName" type="text" class="txt_1" 
						    value="<%CommUtils.printReqByAtt(request,response,"projectName");%>" size="10">
						�ͻ�ID			
						    <input id="userId" name="userId" type="text" class="txt_1" 
						    value="<%CommUtils.printReqByAtt(request,response,"userId");%>" size="10">			
						<input name="find1" type="button" class="but_1" accesskey="f"
							tabindex="f" value="�� ��" onclick="javascript:finds()">
					     <input name="clear" type="button" class="but_1" accesskey="c"
						    tabindex="c"  value="�������" onclick="c()">
				</tr> 
				<%int i=1; %>
                  <tr class="title_2">
                 	 <td width="6%">
						��ĿID
					</td>
					<td width="8%">
						��Ŀ���
					</td>
					<td width="8%">
						��Ŀ����
					</td>
					<td width="6%">
						�ͻ�ID
					</td>
					<td width="6%">
						�û�����
					</td>
					<td width="6%">
						�豸����
					</td>
					<!-- <td width="10%">
						��������
					</td> -->					
					<td width="6%">
						״̬
					</td>
					<td width="10%">
						����ʱ��
					</td>
					<td width="10%">
						��ע
					</td>
					<!-- <td width="10%">
						������
					</td> -->
				</tr>
 
				<logic:iterate id="element" name="pageList">
					<tr class="tr_5" onmouseover='this.className="tr_4"'
						onmouseout='this.className="tr_5"'>
						<td>							
							<bean:write name="element" property="id" />
						</td>
						<td>							
							<bean:write name="element" property="project_no" />
						</td>
						<td>							
							<bean:write name="element" property="project_name" />
						</td>
						<td>
							<a style="color: #0000FF" href="../companyInfo/doCompanyInfo.do?method=queryCompanyInfo&companyId=
								<bean:write name="element" property="company_id"/>">
								<bean:write name="element" property="company_id"/>
							</a>							
						</td>
						<td>
							<logic:empty name="element" property="count_user">0</logic:empty>
							<logic:notEmpty name="element" property="count_user">
								<a style="color: #0000FF" href="../appUserInfo/doAppUserInfo.do?method=queryAppUserInfo&belongProject=
									<bean:write name="element" property="belong_project"/>">
									<bean:write name="element" property="count_user"/>
								</a>
							</logic:notEmpty>														
						</td>
						<td>
							<logic:empty name="element" property="count_device">0</logic:empty>
							<logic:notEmpty name="element" property="count_device">
								<a style="color:#0000FF" href="../deviceActiveInfo/doDeviceActiveInfo.do?method=queryDeviceActiveInfo&belongProject=
									<bean:write name="element" property="belong_project"/>">
									<bean:write name="element" property="count_device"/>
								</a>
							</logic:notEmpty>
						</td>
						<!-- <td>							
							<bean:write name="element" property="channel_id" />
						</td> -->						
						<td>
							<logic:equal name="element" property="status" value="0"><font style="color:red;">����</font></logic:equal>							
							<logic:equal name="element" property="status" value="1"><font style="color:green;">����</font></logic:equal>
						</td>
						<td>								
							<bean:write name="element" property="add_time" format="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>							
							<bean:write name="element" property="remark" />
						</td>										 						
						 <!-- <td>
							
						</td> -->
					</tr>
				</logic:iterate>

			  	<tr class="title_3">
					<td colspan="12" align="left" >  
						<%
							pys.printGoPage(response, "frmGo");
						%>
					</td>
				</tr>  
			</table>
		</form>
	</body>
</html>