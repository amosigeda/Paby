<%@ page language="java" contentType="text/html;charset=gb2312"%>
<%@ page import="com.godoing.rose.http.common.*"%>
<%@ page import="com.godoing.rose.lang.*"%>
<%@ page import="com.wtwd.common.lang.*"%>
<%@ page import="com.wtwd.common.config.Config"%>
<%@ page import="com.wtwd.app.LoginUser"%>
<%@ taglib uri="/WEB-INF/struts-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic" prefix="logic"%>
<jsp:useBean id = "checkInfo" scope = "request" class = "com.godoing.rose.lang.DataMap"/>
<%@ page autoFlush="true"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>无标题文档</title>
		<link href="<%=request.getContextPath()%>/css/tbls.css"
			rel="stylesheet" type="text/css">
		<script language="JavaScript"
			src="../../public/public.js"></script>
		<script type="text/javascript" language="JavaScript" src="../../js/jquery-1.8.2.js"></script>
	</head>
	<script language="javascript">
function onUpdate(){
    if(frmGo.file1.value.length == 0){
    	if(frmGo.downloadPath.value.length == 0){
    		alert("请选择一个APK文件！");
    	return false;
    	}    	    	
    }else{
    	tagChange();
    	if(frmGo.remarks.value.trim().length > 30){
		alert("备注不能超过30字");
		frmGo.remarks.focus();
		return false;
		}
	}
    	frmGo.target="";               //设置目标窗口不存在
    	frmGo.action="doCheckInfo.do?method=updateCheckInfo";
    	frmGo.submit();       
}
function onView(){
	var download = frmGo.downloadPath.value;
	if(download.length == 0){
		alert("没有上传APK文件，无法下载！");
		return false;
	}
	frmGo.action="doCheckInfo.do?method=downloadApk&download="+download;
   	frmGo.submit();
}
function callback(msg)
{
	if(msg == 0){
		frmGo.versionName.value="";
		frmGo.packageName.value="";	
		frmGo.versionCode.value="";
		frmGo.functionCap.value="";
		frmGo.downloadPath.value="";
	}else{
		var apkInfos=msg.split("@");
		
		frmGo.versionName.value=apkInfos[0];
		frmGo.packageName.value=apkInfos[1];	
		frmGo.versionCode.value=apkInfos[2];
		if(apkInfos.length == 4){
			frmGo.functionCap.value="";
		}
		if(apkInfos.length == 5){
			frmGo.functionCap.value=apkInfos[3];
			frmGo.downloadPath.value=apkInfos[4];
		}
	}
}
function tagChange(){
    frmGo.functionCap.innerHTML = '';
	var filePaths=frmGo.file1.value.split("\\");
	//alert(frmGo.file1.value);
	var apkName=filePaths[filePaths.length-1];
	if(apkName.split(".")[1]!="apk"&&apkName.split(".")[1]!="APK"){
		alert("不能上传非APK文件！");
		frm.file.focus();
		return false;
	}
	frmGo.action="doCheckInfo.do?method=getApkMessage";
	frmGo.submit();
}
function preViews(){
     document.getElementById('preView').style.display="none"
	 document.getElementById('rePicture').style.display="none"
	 document.getElementById('pictureView').style.display="block"
}
var xmlHttp;
function changeProject(obj){
	var url = "doCheckInfo.do?method=getCheckInfo&belongProject="+obj;
	getHttpObject();
	xmlHttp.open("get",url,false);
	xmlHttp.onreadystatechange=handleStateChange;
	xmlHttp.send(null);
}

function getHttpObject(){
	try{
		xmlHttp = new XMLHttpRequest();
	}catch(e){
		try{
			xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
		}catch(e){
			xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
		}
	}
}

 // 处理返回信息函数
function handleStateChange(){ 
  	if (xmlHttp.readyState == 4) { // 判断对象状态 			 		 
  	       if (xmlHttp.status == 200) { // 信息 已经成功返回，开始处理信息 	        
  	           var result = xmlHttp.responseText; 	        
  	           callback(result);   
  	       } 
  	   }
  }
  
function changePro(obj){
	frmGo.downloadPath.value="";
	frmGo.versionName.value="";
	frmGo.packageName.value="";	
	frmGo.versionCode.value="";
	frmGo.functionCap.value="";
	frmGo.action="doCheckInfo.do?method=queryCheckInfo&belongProject="+obj;
	frmGo.submit();
}


</script>
	<body>
		<span class="title_1"></span>
		<form name="frmGo" method="post" action="doCheckInfo.do?method=updateCheckInfo" encType="multipart/form-data" target="hidden_frame">
			<input name="downloadPath" type="hidden" value="<%=checkInfo.getAt("download_path")%>" />
			<table width="100%" class="tbl_11" border=0 cellpadding="0" cellspacing="1" >
               <tr>
                  <th colspan="13" nowrap="nowrap" align="left">
                                                                检查更新
                  </th>
                </tr>
    <tr class="tb1_11">
    	<td  width="8%" align="left">&nbsp;&nbsp;&nbsp;项目</td>
    	<td  width="20%">
    		<%int i=0; %>
    		<logic:iterate id="element" name="projectList">
    		<bean:define id="projectId" name="element" property="id" type="java.lang.Integer"/>
    		<%
    		i++; 
    		if(i%5==1 && i!=1){%>
    		<br/>
    		<%}   		
    		if(checkInfo.getAt("belong_project").equals(projectId)){ 
    		%>    		
    		<input type="radio" id='project'<bean:write name="element" property="id"/> name='project' value=<bean:write name="element" property="id"/> checked="checked" onclick="changeProject(this.value);"/>
    		<%}else{ %>
    		<input type="radio" id='project'<bean:write name="element" property="id"/> name='project' value=<bean:write name="element" property="id"/> onclick="changeProject(this.value);"/>   		
    		<%} %>   
    		<bean:write name="element" property="project_name" />		    	
   			 </logic:iterate>
    	</td>
    </tr>
    <tr class="tbl_11">
    <td  width="8%" align="left">&nbsp;&nbsp;&nbsp;APK路径</td>
    <td  width="20%"><div align="left">
    	<%if(checkInfo.getAt("package_name") != null && !checkInfo.getAt("package_name").equals("")){ %>
		<input  name="rePicture" id="rePicture" value="重新上传" type="button" onclick="preViews();"/>
		<%}else{ %>
		<input  name="rePicture" id="rePicture" value="上传" type="button" onclick="preViews();"/>
		<%} %>
		<div id="pictureView" style="display:none;" >
		  <input name="file1" type="file" onchange="tagChange();"/>
		</div>
    	<input name="preView" id="preView" value="下载"  type="button" onclick="onView();" />
        <iframe name='hidden_frame' id="hidden_frame" style='display:none'></iframe>
    </div></td>
    </tr>
    <tr class="tbl_11">
    <td  width="8%" align="left">&nbsp;&nbsp;&nbsp;包名称</td>
    <td  width="20%"><div align="left">
		 <input name="packageName" type="text" value="<%=checkInfo.getAt("package_name")%>" class="txt_1" maxlength="100" readonly>
         <font color="red">*</font>
    </div></td>
    </tr>
    
    <tr class="tbl_11">
    <td  width="8%" align="left">&nbsp;&nbsp;&nbsp;版本名称</td>
    <td  width="20%" align="left">
		 <input name="versionName" type="text" value="<%=checkInfo.getAt("version_name")%>" class="txt_1" maxlength="100" readonly>
         <font color="red">*</font>
    </td>
    <td></td>
    </tr>
    
    <tr class="tbl_11">
    <td  width="8%" align="left">&nbsp;&nbsp;&nbsp;版本号</td>
    <td  width="20%" align="left">
		 <input name="versionCode" type="text" value="<%=checkInfo.getAt("version_code")%>" class="txt_1" maxlength="100" readonly>
         <font color="red">*</font>
    </td>
    <td></td>
    </tr>
    <tr class="tbl_11">
    <td  width="8%" align="left">&nbsp;&nbsp;&nbsp;版本说明</td>
    <td  width="20%">	 
         <textarea rows="5" cols="50" name="functionCap" id="remarks" > <%=checkInfo.getAt("function_cap")%></textarea>
    </td>
    <td><font color="red">(字数不能超过30字)</font></td>
   
    </tr>
    <tr class="tbl_11">
      <td>&nbsp;</td>
      <td>
        <input type="button" name="ok" accesskey="y" tabindex="y"  value="保存" onclick="onUpdate();" class="but_1">
      </td>
      <td><br></td>
    </tr>
			</table>
		</form>
	</body>
</html>
