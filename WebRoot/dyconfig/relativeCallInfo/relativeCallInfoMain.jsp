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
	/*ҳ������*/
	PagePys pys = (PagePys) request.getAttribute("PagePys");
	int count = 1;	
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>�ޱ����ĵ�</title>
		<link href="<%=request.getContextPath()%>/css/tbls.css"
			rel="stylesheet" type="text/css">
		<script language="JavaScript"
			src="<%=request.getContextPath()%>/public/public.js"></script>
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
    document.all.serieNo.value="";
    document.all.phoneNumber.value="";
}

</script>
	<body>
		<form name="frmGo" method="post" action="doRelativeCallInfo.do?method=queryRelativeCallInfo">
			<table width="100%" class="table" border=0 cellpadding="0" cellspacing="1">
               <tr>
                <th colspan="13" nowrap="nowrap" align="left">
                                                            �������
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
						�������<input id="phoneNumber" name="phoneNumber" type="text" class="txt_1" 
						    value="<%CommUtils.printReqByAtt(request,response,"phoneNumber");%>" size="9">		    	
						IMEI<input id="serieNo" name="serieNo" type="text" class="txt_1" 
						    value="<%CommUtils.printReqByAtt(request,response,"serieNo");%>" size="15">								    		
						<input name="find1" type="button" class="but_1" accesskey="f"
							tabindex="f" value="�� ��" onclick="javascript:finds()">
							 <input name="clear" type="button" class="but_1" accesskey="c"
						    tabindex="c"  value="�������" onclick="c()">
						</td>					
				</tr>
                 <tr class="title_2">  
                 	<td width="10%">�������</td>               	
                    <td width="12%">�豸IMEI</td>
                    <td width="6%">�û�ID</td>                   
					<td width="8%">�ǳ�</td>							
					<td width="8%">�뱦����ϵ</td>	
					<td width="10%">����ʱ��</td>													
				</tr>
				<logic:iterate id="element" name="pageList">
					<tr class="tr_5" onmouseover='this.className="tr_4"' onmouseout='this.className="tr_5"' >					 							
						<td>
							<bean:write name="element" property="phone_number" />
						</td>
						<td>
							<bean:write name="element" property="serie_no" />
						</td>
						<td >
							<bean:write name="element" property="user_id" />
						</td>
						<td>
							<bean:write name="element" property="nick_name" />
						</td>						
						<td>
							<logic:equal name="element" property="relative_type" value="0">����</logic:equal>
							<logic:equal name="element" property="relative_type" value="1">����</logic:equal>
						</td>
						<td>
							<bean:write name="element" property="add_time" format="yyyy-MM-dd HH:mm:ss"/>
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