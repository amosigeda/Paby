/*
 * JSP generated by Resin Professional 4.0.48 (built Wed, 17 Feb 2016 11:03:24 PST)
 */

package _jsp._sysadmin._monitorinfo;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import com.wtwd.common.lang.*;
import com.godoing.rose.http.common.*;

public class _devicevisitmain__jsp extends com.caucho.jsp.JavaPage
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
    
  PagePys pys = (PagePys) request.getAttribute("PagePys");
  String deviceSwitch = (String)request.getAttribute("deviceSwitch");

    out.write(_jsp_string1, 0, _jsp_string1.length);
    out.print((request.getContextPath()));
    out.write(_jsp_string2, 0, _jsp_string2.length);
    out.print((request.getContextPath()));
    out.write(_jsp_string3, 0, _jsp_string3.length);
    out.print((request.getContextPath()));
    out.write(_jsp_string4, 0, _jsp_string4.length);
    if(request.getAttribute("fNow_date") != null && !"".equals(request.getAttribute("fNow_date"))){ 
    out.write(_jsp_string5, 0, _jsp_string5.length);
    out.print((request.getAttribute("fNow_date") ));
    out.write(_jsp_string6, 0, _jsp_string6.length);
    out.print((request.getAttribute("now_date") ));
    out.write(_jsp_string7, 0, _jsp_string7.length);
    } 
    out.write(_jsp_string8, 0, _jsp_string8.length);
    if(deviceSwitch.equals("0")){ 
    out.write(_jsp_string9, 0, _jsp_string9.length);
    out.print((deviceSwitch ));
    out.write(_jsp_string10, 0, _jsp_string10.length);
    }else{ 
    out.write(_jsp_string11, 0, _jsp_string11.length);
    out.print((deviceSwitch ));
    out.write(_jsp_string10, 0, _jsp_string10.length);
    } 
    out.write(_jsp_string12, 0, _jsp_string12.length);
    CommUtils.printReqByAtt(request,response,"fNow_date");
    out.write(_jsp_string13, 0, _jsp_string13.length);
    CommUtils.printReqByAtt(request,response,"now_date");
    out.write(_jsp_string14, 0, _jsp_string14.length);
    CommUtils.printReqByAtt(request,response,"phone");
    out.write(_jsp_string15, 0, _jsp_string15.length);
    CommUtils.printReqByAtt(request,response,"href");
    out.write(_jsp_string16, 0, _jsp_string16.length);
    CommUtils.printReqByAtt(request,response,"func");
    out.write(_jsp_string17, 0, _jsp_string17.length);
    if(request.getAttribute("costTime1") != null){ 
    out.write(_jsp_string18, 0, _jsp_string18.length);
    out.print((request.getAttribute("costTime1") ));
    out.write(_jsp_string19, 0, _jsp_string19.length);
    }else{ 
    out.write(_jsp_string20, 0, _jsp_string20.length);
    } 
    out.write(_jsp_string21, 0, _jsp_string21.length);
    if(request.getAttribute("costTime2") != null){ 
    out.write(_jsp_string22, 0, _jsp_string22.length);
    out.print((request.getAttribute("costTime2") ));
    out.write(_jsp_string19, 0, _jsp_string19.length);
    }else{ 
    out.write(_jsp_string23, 0, _jsp_string23.length);
    } 
    out.write(_jsp_string24, 0, _jsp_string24.length);
    com.caucho.jsp.BodyContentImpl _jsp_endTagHack3 = null;
    _jsp_IterateTag_0 = _jsp_state.get_jsp_IterateTag_0(pageContext, _jsp_parent_tag);
    int _jspEval5 = _jsp_IterateTag_0.doStartTag();
    if (_jspEval5 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      out = pageContext.pushBody();
      _jsp_endTagHack3 = (com.caucho.jsp.BodyContentImpl) out;
      _jsp_IterateTag_0.setBodyContent(_jsp_endTagHack3);
      do {
        out.write(_jsp_string25, 0, _jsp_string25.length);
        _jsp_WriteTag_1 = _jsp_state.get_jsp_WriteTag_1(pageContext, _jsp_parent_tag);
        _jsp_WriteTag_1.doStartTag();
        out.write(_jsp_string26, 0, _jsp_string26.length);
        _jsp_WriteTag_2 = _jsp_state.get_jsp_WriteTag_2(pageContext, _jsp_parent_tag);
        _jsp_WriteTag_2.setProperty("phone");
        _jsp_WriteTag_2.doStartTag();
        out.write(_jsp_string27, 0, _jsp_string27.length);
        _jsp_WriteTag_2.setProperty("belong_project");
        _jsp_WriteTag_2.doStartTag();
        out.write(_jsp_string27, 0, _jsp_string27.length);
        _jsp_WriteTag_2.setProperty("cost_time");
        _jsp_WriteTag_2.doStartTag();
        out.write(_jsp_string27, 0, _jsp_string27.length);
        _jsp_WriteTag_2.setProperty("function_href");
        _jsp_WriteTag_2.doStartTag();
        out.write(_jsp_string27, 0, _jsp_string27.length);
        _jsp_WriteTag_2.setProperty("function");
        _jsp_WriteTag_2.doStartTag();
        out.write(_jsp_string28, 0, _jsp_string28.length);
      } while (_jsp_IterateTag_0.doAfterBody() == javax.servlet.jsp.tagext.IterationTag.EVAL_BODY_AGAIN);
      out = pageContext.popBody();
    }
    _jsp_IterateTag_0.doEndTag();
    if (_jsp_endTagHack3 != null) {
      pageContext.releaseBody(_jsp_endTagHack3);
      _jsp_endTagHack3 = null;
    }
    out.write(_jsp_string29, 0, _jsp_string29.length);
    
							pys.printGoPage(response, "frmGo");
						
    out.write(_jsp_string30, 0, _jsp_string30.length);
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("sysAdmin/monitorInfo/deviceVisitMain.jsp"), -3777169346781526611L, false);
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
        _jsp_WriteTag_1.setProperty("start_time");
        _jsp_WriteTag_1.setFormat("yyyy-MM-dd HH:mm:ss");
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

  private final static char []_jsp_string5;
  private final static char []_jsp_string9;
  private final static char []_jsp_string20;
  private final static char []_jsp_string21;
  private final static char []_jsp_string23;
  private final static char []_jsp_string1;
  private final static char []_jsp_string14;
  private final static char []_jsp_string10;
  private final static char []_jsp_string15;
  private final static char []_jsp_string30;
  private final static char []_jsp_string16;
  private final static char []_jsp_string11;
  private final static char []_jsp_string17;
  private final static char []_jsp_string8;
  private final static char []_jsp_string13;
  private final static char []_jsp_string27;
  private final static char []_jsp_string7;
  private final static char []_jsp_string0;
  private final static char []_jsp_string26;
  private final static char []_jsp_string19;
  private final static char []_jsp_string24;
  private final static char []_jsp_string3;
  private final static char []_jsp_string2;
  private final static char []_jsp_string12;
  private final static char []_jsp_string18;
  private final static char []_jsp_string22;
  private final static char []_jsp_string4;
  private final static char []_jsp_string6;
  private final static char []_jsp_string29;
  private final static char []_jsp_string28;
  private final static char []_jsp_string25;
  static {
    _jsp_string5 = "\r\n			<table class=\"table_1\" style=\"font-size:14px;margin-bottom:5px;\">\r\n			   <tr>  \r\n			     <td>\r\n			                      Time slot:\r\n			              <font color=\"#FFA500\">\r\n			               <strong >\r\n			               ".toCharArray();
    _jsp_string9 = "\r\n                     <input name=\"switch\" type=\"button\" class=\"but_1\" accesskey=\"a\"\r\n							tabindex=\"a\" value=\"\u5f00\u542f\" onclick=\"changeSwitch(".toCharArray();
    _jsp_string20 = "\r\n						    <input id=\"costTime1\" name=\"costTime1\" type=\"text\" class=\"txt_1\" \r\n						    value=\"\" size=\"4\">\r\n						".toCharArray();
    _jsp_string21 = "\u81f3\r\n						".toCharArray();
    _jsp_string23 = "\r\n						    <input id=\"costTime2\" name=\"costTime2\" type=\"text\" class=\"txt_1\" \r\n						    value=\"\" size=\"4\">\r\n						".toCharArray();
    _jsp_string1 = "\r\n<html>\r\n	<head>\r\n		<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">\r\n		<title>\u65e0\u6807\u9898\u6587\u6863</title>\r\n		<link href=\"".toCharArray();
    _jsp_string14 = "\" onclick=\"WdatePicker()\"\r\n								size=\"9\" readonly>\r\n						IMEI						\r\n						    <input id=\"phone\" name=\"phone\" type=\"text\" class=\"txt_1\" \r\n						    value=\"".toCharArray();
    _jsp_string10 = ");\">\r\n					 ".toCharArray();
    _jsp_string15 = "\" size=\"15\">											    \r\n						\u63a5\u53e3\r\n						    <input id=\"href\" name=\"href\" type=\"text\" class=\"txt_1\" \r\n						    value=\"".toCharArray();
    _jsp_string30 = " \r\n					</td>\r\n				</tr>\r\n				</table>\r\n		</form>\r\n	</body>\r\n</html>".toCharArray();
    _jsp_string16 = "\" size=\"9\">\r\n						\u63a5\u53e3\u8bf4\u660e\r\n						    <input id=\"func\" name=\"func\" type=\"text\" class=\"txt_1\" \r\n						    value=\"".toCharArray();
    _jsp_string11 = "\r\n					 <input name=\"switch\" type=\"button\" class=\"but_1\" accesskey=\"a\"\r\n							tabindex=\"a\" value=\"\u5173\u95ed\" onclick=\"changeSwitch(".toCharArray();
    _jsp_string17 = "\" size=\"9\">\r\n						\u6027\u80fdTime slot\r\n						".toCharArray();
    _jsp_string8 = "\r\n			<table width=\"100%\" class=\"table\" >\r\n			    <tr>\r\n                   <th colspan=\"13\" nowrap=\"nowrap\" align=\"left\">\r\n                                                                      \u8bbe\u5907\u63a5\u53e3\u6027\u80fd\u5de5\u5177 \r\n                       ".toCharArray();
    _jsp_string13 = "\" onclick=\"WdatePicker()\"\r\n								size=\"9\" readonly> -\r\n							<input name=\"endTime\" type=\"text\" class=\"txt_1\" id=\"endTime\" style=\"cursor:text\"\r\n								value=\"".toCharArray();
    _jsp_string27 = "\r\n						</td>\r\n						<td>\r\n							".toCharArray();
    _jsp_string7 = "</strong>\r\n			            </font>\r\n			     </td>\r\n			   </tr>\r\n			</table>\r\n		 ".toCharArray();
    _jsp_string0 = "\r\n\r\n\r\n\r\n\r\n\r\n".toCharArray();
    _jsp_string26 = "\r\n						</td>												\r\n						<td>\r\n							".toCharArray();
    _jsp_string19 = "\" size=\"4\">\r\n						".toCharArray();
    _jsp_string24 = "ms\r\n						<input name=\"find1\" type=\"button\" class=\"but_1\" accesskey=\"f\"\r\n							tabindex=\"f\" value=\"\u641c \u7d22\" onclick=\"javascript:finds()\">\r\n					     <input name=\"clear\" type=\"button\" class=\"but_1\" accesskey=\"c\"\r\n						    tabindex=\"c\"  value=\"\u6e05\u9664\u641c\u7d22\" onclick=\"c()\">\r\n				</tr>\r\n			<tr class=\"title_2\">\r\n					\r\n					<td width=\"15%\">\r\n					     \u8bf7\u6c42\u65f6\u95f4\r\n					</td>					\r\n					<td width=\"20%\">\r\n						IMEI\r\n					</td>				\r\n					<td width=\"13%\">\r\n						\u9879\u76eeID\r\n					</td>\r\n					<td width=\"15%\">\r\n						\u6027\u80fd\u65f6\u95f4(ms)\r\n					</td>\r\n					<td width=\"15%\">\r\n						\u63a5\u53e3\r\n					</td>\r\n					<td width=\"15%\">\r\n						\u63a5\u53e3\u8bf4\u660e\r\n					</td>\r\n				</tr>\r\n				\r\n				".toCharArray();
    _jsp_string3 = "/public/public.js\"></script>\r\n        <script language=\"JavaScript\"\r\n			src=\"".toCharArray();
    _jsp_string2 = "/css/tbls.css\"\r\n			rel=\"stylesheet\" type=\"text/css\">\r\n		<script language=\"JavaScript\"\r\n			src=\"".toCharArray();
    _jsp_string12 = "\r\n                   </th>\r\n                 </tr>\r\n                   <tr class=\"title_3\">\r\n                       <td colspan=\"13\">\r\n						  Time slot\r\n                     <input name=\"startTime\" type=\"text\" class=\"txt_1\"  id=\"startTime\" style=\"cursor:text\"\r\n								value=\"".toCharArray();
    _jsp_string18 = "\r\n						    <input id=\"costTime1\" name=\"costTime1\" type=\"text\" class=\"txt_1\" \r\n						     value=\"".toCharArray();
    _jsp_string22 = "\r\n						    <input id=\"costTime2\" name=\"costTime2\" type=\"text\" class=\"txt_1\" \r\n						    value=\"".toCharArray();
    _jsp_string4 = "/public/My97DatePicker/WdatePicker.js\"></script>\r\n\r\n\r\n	</head>\r\n	<script language=\"javascript\">\r\nfunction finds(){\r\n	var costTime1 = document.getElementById('costTime1').value;\r\n	var costTiem2 = document.getElementById('costTime2').value;\r\n	   if(!isNumber(costTime1.trim()) && costTime1.trim() !=\"\"){\r\n	   alert(\"\u6027\u80fdTime slot\u8bf7\u586b\u5199\u6570\u5b57\");\r\n	    frmGo.costTime1.focus();\r\n	    return false;\r\n	}\r\n	if(!isNumber(costTiem2.trim()) && costTiem2.trim() !=\"\"){\r\n	   alert(\"\u6027\u80fdTime slot\u8bf7\u586b\u5199\u6570\u5b57\");\r\n	    frmGo.costTime2.focus();\r\n	    return false;\r\n	}\r\n	if(parseInt(costTime1.trim()) > parseInt(costTiem2.trim())){\r\n	   alert(\"\u8f93\u5165\u7684\u6027\u80fdTime slot\u6709\u9519,\u8bf7\u91cd\u65b0\u8f93\u5165\");\r\n	   frmGo.costTime2.focus();\r\n	   return false;\r\n	}\r\n	frmGo.submit();\r\n}\r\nfunction c(){\r\n    document.all.startTime.value=\"\";\r\n    document.all.endTime.value=\"\";\r\n    document.all.phone.value=\"\";\r\n    document.all.costTime1.value=\"\";\r\n    document.all.costTime2.value=\"\";\r\n    document.all.href.value=\"\";\r\n    document.all.func.value=\"\";\r\n}\r\n\r\nfunction changeSwitch(obj){\r\n	if(obj == 0){\r\n		if(confirm(\"\u662f\u5426\u5f00\u542f\u5f00\u5173?\")){\r\n			frmGo.action=\"doMonitorInfo.do?method=changeSwitch&type=device&value=1\";\r\n			frmGo.submit();\r\n		}\r\n	}\r\n	if(obj == 1){\r\n		if(confirm(\"\u662f\u5426\u5173\u95ed\u5f00\u5173?\")){\r\n			frmGo.action=\"doMonitorInfo.do?method=changeSwitch&type=device&value=0\";\r\n			frmGo.submit();\r\n		}\r\n	}		\r\n}\r\n\r\n\r\n</script>\r\n	<body>\r\n		<span class=\"title_1\"> </span>\r\n		<form name=\"frmGo\" method=\"post\"\r\n			action=\"doMonitorInfo.do?method=queryVisit&type=1\">\r\n			".toCharArray();
    _jsp_string6 = "		            \r\n			                                                       \u81f3\r\n			               ".toCharArray();
    _jsp_string29 = "\r\n				<tr class=\"title_3\">\r\n					\r\n					<td align=\"left\" colspan=\"6\">	\r\n						".toCharArray();
    _jsp_string28 = "\r\n						</td>\r\n					</tr>\r\n				".toCharArray();
    _jsp_string25 = "\r\n					 <tr class=\"tr_5\" onmouseover='this.className=\"tr_4\"' onmouseout='this.className=\"tr_5\"' >\r\n						\r\n						<td>\r\n							".toCharArray();
  }
}
