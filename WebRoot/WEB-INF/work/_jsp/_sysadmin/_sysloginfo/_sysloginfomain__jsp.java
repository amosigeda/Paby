/*
 * JSP generated by Resin Professional 4.0.48 (built Wed, 17 Feb 2016 11:03:24 PST)
 */

package _jsp._sysadmin._sysloginfo;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import com.godoing.rose.http.common.*;
import com.wtwd.common.lang.*;

public class _sysloginfomain__jsp extends com.caucho.jsp.JavaPage
{
  private static final java.util.HashMap<String,java.lang.reflect.Method> _jsp_functionMap = new java.util.HashMap<String,java.lang.reflect.Method>();
  private boolean _caucho_isDead;
  private boolean _caucho_isNotModified;
  private com.caucho.jsp.PageManager _jsp_pageManager;
  
  public void
  _jspService(javax.servlet.http.HttpServletRequest request,
              javax.servlet.http.HttpServletResponse response)
    throws java.io.IOException, javax.servlet.ServletException
  {
    javax.servlet.http.HttpSession session = request.getSession(true);
    com.caucho.server.webapp.WebApp _jsp_application = _caucho_getApplication();
    com.caucho.jsp.PageContextImpl pageContext = _jsp_pageManager.allocatePageContext(this, _jsp_application, request, response, null, session, 8192, true, false);

    TagState _jsp_state = new TagState();

    try {
      _jspService(request, response, pageContext, _jsp_application, session, _jsp_state);
    } catch (java.lang.Throwable _jsp_e) {
      pageContext.handlePageException(_jsp_e);
    } finally {
      _jsp_state.release();
      _jsp_pageManager.freePageContext(pageContext);
    }
  }
  
  private void
  _jspService(javax.servlet.http.HttpServletRequest request,
              javax.servlet.http.HttpServletResponse response,
              com.caucho.jsp.PageContextImpl pageContext,
              javax.servlet.ServletContext application,
              javax.servlet.http.HttpSession session,
              TagState _jsp_state)
    throws Throwable
  {
    javax.servlet.jsp.JspWriter out = pageContext.getOut();
    final javax.el.ELContext _jsp_env = pageContext.getELContext();
    javax.servlet.ServletConfig config = getServletConfig();
    javax.servlet.Servlet page = this;
    javax.servlet.jsp.tagext.JspTag _jsp_parent_tag = null;
    com.caucho.jsp.PageContextImpl _jsp_parentContext = pageContext;
    response.setContentType("text/html;charset=gb2312");
    org.apache.struts.taglib.logic.IterateTag _jsp_IterateTag_0 = null;
    org.apache.struts.taglib.bean.WriteTag _jsp_WriteTag_1 = null;
    org.apache.struts.taglib.bean.WriteTag _jsp_WriteTag_2 = null;

    out.write(_jsp_string0, 0, _jsp_string0.length);
    
	/*\u9875\u9762\u5c5e\u6027*/
	PagePys pys = (PagePys) request.getAttribute("PagePys");

    out.write(_jsp_string1, 0, _jsp_string1.length);
    out.print((request.getContextPath()));
    out.write(_jsp_string2, 0, _jsp_string2.length);
    out.print((request.getContextPath()));
    out.write(_jsp_string3, 0, _jsp_string3.length);
    out.print((request.getContextPath()));
    out.write(_jsp_string4, 0, _jsp_string4.length);
    out.print((request.getContextPath()));
    out.write(_jsp_string5, 0, _jsp_string5.length);
    if(request.getAttribute("fNow_date") != null && !"".equals(request.getAttribute("fNow_date"))){ 
    out.write(_jsp_string6, 0, _jsp_string6.length);
    out.print((request.getAttribute("fNow_date") ));
    out.write(_jsp_string7, 0, _jsp_string7.length);
    out.print((request.getAttribute("now_date") ));
    out.write(_jsp_string8, 0, _jsp_string8.length);
    } 
    out.write(_jsp_string9, 0, _jsp_string9.length);
    CommUtils.printReqByAtt(request,response,"fNow_date");
    out.write(_jsp_string10, 0, _jsp_string10.length);
    CommUtils.printReqByAtt(request,response,"now_date");
    out.write(_jsp_string11, 0, _jsp_string11.length);
    if(request.getAttribute("store")!=null){ 
    out.write(_jsp_string12, 0, _jsp_string12.length);
    out.print((request.getAttribute("store")));
    out.write(_jsp_string13, 0, _jsp_string13.length);
    }else{
    out.write(_jsp_string14, 0, _jsp_string14.length);
    }
    out.write(_jsp_string15, 0, _jsp_string15.length);
    if(request.getAttribute("store2")!=null){ 
    out.write(_jsp_string16, 0, _jsp_string16.length);
    out.print((request.getAttribute("store2")));
    out.write(_jsp_string13, 0, _jsp_string13.length);
    }else{
    out.write(_jsp_string17, 0, _jsp_string17.length);
    }
    out.write(_jsp_string18, 0, _jsp_string18.length);
    com.caucho.jsp.BodyContentImpl _jsp_endTagHack3 = null;
    _jsp_IterateTag_0 = _jsp_state.get_jsp_IterateTag_0(pageContext, _jsp_parent_tag);
    int _jspEval5 = _jsp_IterateTag_0.doStartTag();
    if (_jspEval5 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      out = pageContext.pushBody();
      _jsp_endTagHack3 = (com.caucho.jsp.BodyContentImpl) out;
      _jsp_IterateTag_0.setBodyContent(_jsp_endTagHack3);
      do {
        out.write(_jsp_string19, 0, _jsp_string19.length);
        _jsp_WriteTag_1 = _jsp_state.get_jsp_WriteTag_1(pageContext, _jsp_parent_tag);
        _jsp_WriteTag_1.setProperty("id");
        _jsp_WriteTag_1.doStartTag();
        out.write(_jsp_string20, 0, _jsp_string20.length);
        _jsp_WriteTag_1.setProperty("userName");
        _jsp_WriteTag_1.doStartTag();
        out.write(_jsp_string21, 0, _jsp_string21.length);
        _jsp_WriteTag_2 = _jsp_state.get_jsp_WriteTag_2(pageContext, _jsp_parent_tag);
        _jsp_WriteTag_2.doStartTag();
        out.write(_jsp_string22, 0, _jsp_string22.length);
        _jsp_WriteTag_1.setProperty("ip");
        _jsp_WriteTag_1.doStartTag();
        out.write(_jsp_string23, 0, _jsp_string23.length);
        _jsp_WriteTag_1.setProperty("logs");
        _jsp_WriteTag_1.doStartTag();
        out.write(_jsp_string24, 0, _jsp_string24.length);
      } while (_jsp_IterateTag_0.doAfterBody() == javax.servlet.jsp.tagext.IterationTag.EVAL_BODY_AGAIN);
      out = pageContext.popBody();
    }
    _jsp_IterateTag_0.doEndTag();
    if (_jsp_endTagHack3 != null) {
      pageContext.releaseBody(_jsp_endTagHack3);
      _jsp_endTagHack3 = null;
    }
    out.write(_jsp_string25, 0, _jsp_string25.length);
    
							pys.printGoPage(response, "frmGo");
						
    out.write(_jsp_string26, 0, _jsp_string26.length);
  }

  private com.caucho.make.DependencyContainer _caucho_depends
    = new com.caucho.make.DependencyContainer();

  public java.util.ArrayList<com.caucho.vfs.Dependency> _caucho_getDependList()
  {
    return _caucho_depends.getDependencies();
  }

  public void _caucho_addDepend(com.caucho.vfs.PersistentDependency depend)
  {
    super._caucho_addDepend(depend);
    _caucho_depends.add(depend);
  }

  protected void _caucho_setNeverModified(boolean isNotModified)
  {
    _caucho_isNotModified = true;
  }

  public boolean _caucho_isModified()
  {
    if (_caucho_isDead)
      return true;

    if (_caucho_isNotModified)
      return false;

    if (com.caucho.server.util.CauchoSystem.getVersionId() != -8002497470487589159L)
      return true;

    return _caucho_depends.isModified();
  }

  public long _caucho_lastModified()
  {
    return 0;
  }

  public void destroy()
  {
      _caucho_isDead = true;
      super.destroy();
    TagState tagState;
  }

  public void init(com.caucho.vfs.Path appDir)
    throws javax.servlet.ServletException
  {
    com.caucho.vfs.Path resinHome = com.caucho.server.util.CauchoSystem.getResinHome();
    com.caucho.vfs.MergePath mergePath = new com.caucho.vfs.MergePath();
    mergePath.addMergePath(appDir);
    mergePath.addMergePath(resinHome);
    com.caucho.loader.DynamicClassLoader loader;
    loader = (com.caucho.loader.DynamicClassLoader) getClass().getClassLoader();
    String resourcePath = loader.getResourcePathSpecificFirst();
    mergePath.addClassPath(resourcePath);
    com.caucho.vfs.Depend depend;
    depend = new com.caucho.vfs.Depend(appDir.lookup("sysAdmin/sysLogInfo/sysLogInfoMain.jsp"), 1110483174850270658L, false);
    _caucho_depends.add(depend);
    loader.addDependency(depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("WEB-INF/tld/struts-logic.tld"), 8151595965222989444L, false);
    _caucho_depends.add(depend);
    loader.addDependency(depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("WEB-INF/tld/struts-bean.tld"), -6431408965588936799L, false);
    _caucho_depends.add(depend);
    loader.addDependency(depend);
    _caucho_depends.add(new com.caucho.make.ClassDependency("org.apache.struts.taglib.bean.WriteTag", 8911154127381070432L));
    _caucho_depends.add(new com.caucho.make.ClassDependency("org.apache.struts.taglib.logic.IterateTag", -9049918858482870556L));
    _caucho_depends.add(new com.caucho.make.ClassDependency("org.apache.struts.taglib.logic.IterateTei", -5937946434527624669L));
  }

  static {
    try {
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  final static class TagState {
    private org.apache.struts.taglib.logic.IterateTag _jsp_IterateTag_0;

    final org.apache.struts.taglib.logic.IterateTag get_jsp_IterateTag_0(PageContext pageContext, javax.servlet.jsp.tagext.JspTag _jsp_parent_tag) throws Throwable
    {
      if (_jsp_IterateTag_0 == null) {
        _jsp_IterateTag_0 = new org.apache.struts.taglib.logic.IterateTag();
        _jsp_IterateTag_0.setPageContext(pageContext);
        if (_jsp_parent_tag instanceof javax.servlet.jsp.tagext.Tag)
          _jsp_IterateTag_0.setParent((javax.servlet.jsp.tagext.Tag) _jsp_parent_tag);
        else if (_jsp_parent_tag instanceof javax.servlet.jsp.tagext.SimpleTag)
          _jsp_IterateTag_0.setParent(new javax.servlet.jsp.tagext.TagAdapter((javax.servlet.jsp.tagext.SimpleTag) _jsp_parent_tag));
        else
          _jsp_IterateTag_0.setParent((javax.servlet.jsp.tagext.Tag) null);
        _jsp_IterateTag_0.setId("element");
        _jsp_IterateTag_0.setName("pageList");
      }

      return _jsp_IterateTag_0;
    }
    private org.apache.struts.taglib.bean.WriteTag _jsp_WriteTag_1;

    final org.apache.struts.taglib.bean.WriteTag get_jsp_WriteTag_1(PageContext pageContext, javax.servlet.jsp.tagext.JspTag _jsp_parent_tag) throws Throwable
    {
      if (_jsp_WriteTag_1 == null) {
        _jsp_WriteTag_1 = new org.apache.struts.taglib.bean.WriteTag();
        _jsp_WriteTag_1.setPageContext(pageContext);
        _jsp_WriteTag_1.setParent((javax.servlet.jsp.tagext.Tag) _jsp_IterateTag_0);
        _jsp_WriteTag_1.setName("element");
      }

      return _jsp_WriteTag_1;
    }
    private org.apache.struts.taglib.bean.WriteTag _jsp_WriteTag_2;

    final org.apache.struts.taglib.bean.WriteTag get_jsp_WriteTag_2(PageContext pageContext, javax.servlet.jsp.tagext.JspTag _jsp_parent_tag) throws Throwable
    {
      if (_jsp_WriteTag_2 == null) {
        _jsp_WriteTag_2 = new org.apache.struts.taglib.bean.WriteTag();
        _jsp_WriteTag_2.setPageContext(pageContext);
        _jsp_WriteTag_2.setParent((javax.servlet.jsp.tagext.Tag) _jsp_IterateTag_0);
        _jsp_WriteTag_2.setName("element");
        _jsp_WriteTag_2.setProperty("logDate");
        _jsp_WriteTag_2.setFormat("yyyy-MM-dd HH:mm:ss");
      }

      return _jsp_WriteTag_2;
    }

    void release()
    {
      if (_jsp_IterateTag_0 != null) {
        _jsp_IterateTag_0.release();
        _jsp_IterateTag_0 = null;
      }
      if (_jsp_WriteTag_1 != null) {
        _jsp_WriteTag_1.release();
        _jsp_WriteTag_1 = null;
      }
      if (_jsp_WriteTag_2 != null) {
        _jsp_WriteTag_2.release();
        _jsp_WriteTag_2 = null;
      }
    }
  }

  public java.util.HashMap<String,java.lang.reflect.Method> _caucho_getFunctionMap()
  {
    return _jsp_functionMap;
  }

  public void caucho_init(ServletConfig config)
  {
    try {
      com.caucho.server.webapp.WebApp webApp
        = (com.caucho.server.webapp.WebApp) config.getServletContext();
      init(config);
      if (com.caucho.jsp.JspManager.getCheckInterval() >= 0)
        _caucho_depends.setCheckInterval(com.caucho.jsp.JspManager.getCheckInterval());
      _jsp_pageManager = webApp.getJspApplicationContext().getPageManager();
      com.caucho.jsp.TaglibManager manager = webApp.getJspApplicationContext().getTaglibManager();
      manager.addTaglibFunctions(_jsp_functionMap, "bean", "/WEB-INF/struts-bean");
      manager.addTaglibFunctions(_jsp_functionMap, "logic", "/WEB-INF/struts-logic");
      com.caucho.jsp.PageContextImpl pageContext = new com.caucho.jsp.InitPageContextImpl(webApp, this);
    } catch (Exception e) {
      throw com.caucho.config.ConfigException.create(e);
    }
  }

  private final static char []_jsp_string20;
  private final static char []_jsp_string6;
  private final static char []_jsp_string19;
  private final static char []_jsp_string18;
  private final static char []_jsp_string1;
  private final static char []_jsp_string26;
  private final static char []_jsp_string24;
  private final static char []_jsp_string16;
  private final static char []_jsp_string25;
  private final static char []_jsp_string17;
  private final static char []_jsp_string3;
  private final static char []_jsp_string4;
  private final static char []_jsp_string11;
  private final static char []_jsp_string10;
  private final static char []_jsp_string21;
  private final static char []_jsp_string0;
  private final static char []_jsp_string5;
  private final static char []_jsp_string22;
  private final static char []_jsp_string12;
  private final static char []_jsp_string2;
  private final static char []_jsp_string15;
  private final static char []_jsp_string9;
  private final static char []_jsp_string14;
  private final static char []_jsp_string7;
  private final static char []_jsp_string23;
  private final static char []_jsp_string8;
  private final static char []_jsp_string13;
  static {
    _jsp_string20 = "'>\r\n					    </td>\r\n						<td >\r\n						    ".toCharArray();
    _jsp_string6 = "\r\n			<table class=\"table_1\" style=\"font-size:14px;margin-bottom:5px;\">\r\n			   <tr>  \r\n			     <td>\r\n			                      Time slot:\r\n			              <font color=\"#FFA500\">\r\n			               <strong >\r\n			               ".toCharArray();
    _jsp_string19 = "\r\n					<tr class=\"tr_5\" onmouseover='this.className=\"tr_4\"' onmouseout='this.className=\"tr_5\"' >\r\n						<td align=\"center\" > \r\n						 <input name=\"crow\" type=\"checkbox\" id=\"crow\" onclick=\"selectRow();\"\r\n								value='".toCharArray();
    _jsp_string18 = "\r\n					  \r\n						<input name=\"find1\" type=\"button\" class=\"but_1\" accesskey=\"f\"\r\n							tabindex=\"f\" value=\"\u641c \u7d22\" onclick=\"javascript:finds()\">\r\n						 <input name=\"clear\" type=\"button\" class=\"but_1\" accesskey=\"c\"\r\n						    tabindex=\"c\"  value=\"\u6e05\u9664\u641c\u7d22\" onclick=\"c()\">\r\n					</td>\r\n				</tr>\r\n				<tr class=\"title_2\">\r\n					<td align=\"center\" width=\"10%\">&nbsp; \r\n					 <input name=\"allrow\" type=\"checkbox\" id=\"allrow\" value=\"checkbox\"\r\n							onClick=\"selectAllRow();\" style=\"display:none\">\r\n					</td>\r\n					<td width=\"15%\"> \r\n						\u767b\u9646\u8d26\u53f7\r\n					</td>\r\n					<td width=\"15%\">  \r\n						\u65f6\u95f4\r\n					</td>\r\n					<td width=\"15%\">\r\n					    ip\r\n					</td>\r\n					<td width=\"40%\" align=\"left\">\r\n						\u63cf\u8ff0\r\n					</td>                  \r\n				</tr>\r\n				".toCharArray();
    _jsp_string1 = "\r\n<html>\r\n	<head>\r\n		<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">\r\n		<title>\u65e0\u6807\u9898\u6587\u6863</title>\r\n		<link href=\"".toCharArray();
    _jsp_string26 = "\r\n					</td>\r\n				</tr>\r\n			</table>\r\n		</form>\r\n	</body>\r\n</html>\r\n".toCharArray();
    _jsp_string24 = "\r\n						</td>					\r\n					</tr>\r\n				".toCharArray();
    _jsp_string16 = "\r\n							<input name=\"logs\" type=\"text\" class=\"txt_1\" id=\"logs\"\r\n								value=\"".toCharArray();
    _jsp_string25 = "\r\n                <tr class=\"title_3\">\r\n                	<td align=\"left\" colspan=\"2\">\r\n						<a href=# onClick=\"selectAll();\" style=\"color:#0000FF;text-align:left\">\u5168\u9009</a>/\r\n						<a href=# onClick=\"selectCanAll();\" style=\"color:#0000FF;text-align:left\">\u53d6\u6d88\u5168\u9009</a>-\r\n					    <a href=\"#\" onclick=\"ondel('',1)\" style=\"color:#0000FF;text-align:left;\">\u6279\u91cf\u5220\u9664</a>\r\n					</td>\r\n					<td colspan=\"5\" align=\"left\" >\r\n						".toCharArray();
    _jsp_string17 = "\r\n									<input id=\"logs\" name=\"logs\" type=\"text\" class=\"txt_1\" id=\"logs\"\r\n								value=\"\" size=\"10\">\r\n							".toCharArray();
    _jsp_string3 = "/public/public.js\"></script>\r\n		<script language=\"JavaScript\"\r\n			src=\"".toCharArray();
    _jsp_string4 = "/js/jquery-1.8.2.js\"></script> \r\n        <script language=\"JavaScript\"\r\n			src=\"".toCharArray();
    _jsp_string11 = "\" onclick=\"WdatePicker()\"\r\n								size=\"9\" readonly>\r\n						\u767b\u5f55\u8d26\u53f7\r\n					    ".toCharArray();
    _jsp_string10 = "\" onclick=\"WdatePicker()\"\r\n								size=\"9\" readonly> -\r\n							<input name=\"endTime\" type=\"text\" class=\"txt_1\" id=\"endTime\" style=\"cursor:text\"\r\n								value=\"".toCharArray();
    _jsp_string21 = "\r\n						</td>\r\n						<td >						  \r\n							".toCharArray();
    _jsp_string0 = "\r\n\r\n\r\n\r\n\r\n\r\n".toCharArray();
    _jsp_string5 = "/public/My97DatePicker/WdatePicker.js\"></script>\r\n	</head>\r\n	<script language=\"javascript\">\r\nfunction finds(){\r\n	if((frmGo.startTime.value.trim() != '')||(frmGo.endTime.value.trim() != '')){\r\n	    if(frmGo.startTime.value.trim() == ''){\r\n			alert(\"\u5f00\u59cb\u65f6\u95f4\u4e0d\u80fd\u4e3a\u7a7a\uff01\");\r\n			frmGo.tmBt0.focus();\r\n			return false;			 \r\n		}\r\n		if(frmGo.endTime.value.trim() == ''){\r\n			alert(\"\u7ed3\u675f\u65f6\u95f4\u4e0d\u80fd\u4e3a\u7a7a\uff01\");\r\n			frmGo.tmBt1.focus();\r\n			return false;			 \r\n		}\r\n		var st = new Date(frmGo.startTime.value.replace(/-/g,'/'));\r\n		var et = new Date(frmGo.endTime.value.replace(/-/g,'/'));\r\n		if(Date.parse(st) - Date.parse(et)>0){\r\n			alert(\"\u5f00\u59cb\u65f6\u95f4\u4e0d\u80fd\u5927\u4e8e\u7ed3\u675f\u65f6\u95f4!!\u8bf7\u91cd\u65b0\u8f93\u5165\");\r\n			return false;\r\n		}\r\n	\r\n	    frmGo.submit();\r\n	}else{\r\n		  frmGo.submit();\r\n	}\r\n}\r\n\r\nfunction querySwitch(){\r\n     frmGo.submit();\r\n}\r\nfunction del(){\r\n	if(countBox()>0){\r\n		if(confirm(\"\u786e\u8ba4\u5220\u9664\u5417\uff0c\u6b64\u64cd\u4f5c\u4e0d\u53ef\u6062\u590d\"))\r\n		{\r\n			frmGo.action = \"doSysLogInfo.do?method=deleteSysLogInfo\";\r\n			frmGo.submit();\r\n		}\r\n	}else{alert(\"\u6ca1\u6709\u9009\u62e9\u8bb0\u5f55\");}\r\n}\r\nfunction c(){\r\n	document.all.userName.value=\"\";\r\n	document.all.logs.value=\"\";\r\n	document.all.endTime.value=\"\";\r\n    document.all.startTime.value=\"\";\r\n\r\n}\r\nfunction selectAll(){\r\n   if(document.getElementById('allrow').checked){\r\n   \r\n   }else{\r\n      if(document.all) {\r\n        document.getElementById('allrow').click(); \r\n      } else { \r\n        var evt = document.createEvent(\"MouseEvents\"); \r\n        evt.initEvent(\"click\", true, true);\r\n        document.getElementById('allrow').dispatchEvent(evt); \r\n      }\r\n      document.getElementById('allrow').checked = true;          //\u653e\u5728\u540e\u9762\r\n   }\r\n    \r\n}\r\n\r\nfunction selectCanAll(){\r\n    if(document.getElementById('allrow').checked){\r\n      if(document.all) {\r\n        document.getElementById('allrow').click(); \r\n       } else { \r\n        var evt = document.createEvent(\"MouseEvents\"); \r\n        evt.initEvent(\"click\", true, true);\r\n        document.getElementById('allrow').dispatchEvent(evt); \r\n       }\r\n         document.getElementById('allrow').checked = false;          //\u653e\u5728\u540e\u9762\r\n   }else{\r\n   \r\n   } \r\n}\r\nfunction ondel(uni,tag){\r\n   if(uni != ''){\r\n      if(confirm(\"\u786e\u8ba4\u5220\u9664\u5417\uff0c\u6b64\u64cd\u4f5c\u4e0d\u53ef\u6062\u590d\")){\r\n         frmGo.action=\"doSysLogInfo.do?method=deleteSysLogInfo&uni=\"+uni+\"&tag=\"+tag;\r\n         frmGo.submit();\r\n      }\r\n   }else{\r\n      if(countBox()>0){\r\n		if(confirm(\"\u786e\u8ba4\u5220\u9664\u5417\uff0c\u6b64\u64cd\u4f5c\u4e0d\u53ef\u6062\u590d\"))\r\n		{\r\n            frmGo.action=\"doSysLogInfo.do?method=deleteSysLogInfo&uni=\"+uni+\"&tag=\"+tag;\r\n            frmGo.submit();\r\n        }\r\n	  }else{alert(\"\u6ca1\u6709\u9009\u62e9\u8bb0\u5f55\");}\r\n   }  \r\n}\r\n</script>\r\n	<body>\r\n		<form name=\"frmGo\" method=\"post\" action=\"doSysLogInfo.do?method=querySysLogInfo\">\r\n		".toCharArray();
    _jsp_string22 = "\r\n						</td>\r\n                        <td>\r\n                           ".toCharArray();
    _jsp_string12 = "\r\n							<input name=\"userName\" type=\"text\" class=\"txt_1\" id=\"userName\"\r\n								value=\"".toCharArray();
    _jsp_string2 = "/css/tbls.css\"\r\n			rel=\"stylesheet\" type=\"text/css\">\r\n		<script language=\"JavaScript\"\r\n			src=\"".toCharArray();
    _jsp_string15 = "\r\n						\u63cf\u8ff0\r\n					    ".toCharArray();
    _jsp_string9 = "\r\n			<table width=\"100%\" class=\"table\" >\r\n			     <tr>\r\n                   <th colspan=\"13\" nowrap=\"nowrap\" align=\"left\">\r\n                                                                      \u7cfb\u7edf\u65e5\u5fd7\r\n                        <!-- <input name=\"\" class=\"but_1\" type=\"button\" accesskey=\"\" value=\"\u6e05\u9664\u5e76\u5907\u4efd\u65e5\u5fd7\" onclick=\"location.href='doSysLogInfo.do?method=queryBeifenRecord'\";>  -->                                             \r\n                   </th>\r\n                 </tr>\r\n				<tr class=\"title_3\">\r\n					<td colspan=\"5\">\r\n					  Time slot\r\n                     <input name=\"startTime\" type=\"text\" class=\"txt_1\"  id=\"startTime\" style=\"cursor:text\"\r\n								value=\"".toCharArray();
    _jsp_string14 = "\r\n							<input id=\"userName\" name=\"userName\" type=\"text\" class=\"txt_1\"\r\n								value=\"\" size=\"10\">\r\n							".toCharArray();
    _jsp_string7 = "		            \r\n			                                                       \u81f3\r\n			               ".toCharArray();
    _jsp_string23 = "\r\n                        </td>\r\n						<td align=\"left\" id=\"wrap\" >\r\n							".toCharArray();
    _jsp_string8 = "</strong>\r\n			            </font>\r\n			     </td>\r\n			   </tr>\r\n			</table>\r\n		".toCharArray();
    _jsp_string13 = "\" size=\"10\">\r\n						".toCharArray();
  }
}
