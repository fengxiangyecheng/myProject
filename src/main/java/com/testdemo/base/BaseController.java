package com.testdemo.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.testdemo.config.AllConfig;
import com.testdemo.init.Config;
import com.testdemo.util.UploadUtil;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.testdemo.entity.system.Page;
import com.testdemo.entity.system.PageData;
import com.testdemo.util.Tools;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
public class BaseController {

	private static final long serialVersionUID = 6357869213649815390L;
	// 全局session
	@Autowired
	protected HttpSession session;

	// 全局request
	@Autowired
	protected HttpServletRequest request;
	// 全局请求路径项目名
	protected String content;

	// 404 not found 页面跳转
	protected final String NOT_FOUND = "/404";

	/**
	 * 通用返回json分页数据
	 * */
	public JSONObject viewReturnPageData(Page page, List<PageData> pageLst) {
		JSONObject json = new JSONObject();// 返回数据必须包含这个格式
		json.put("total", page.getTotalResult());
		json.put("rows", pageLst);
		return json;
	}

	/**
	 * 获取request
	 *
	 * @return HttpServletRequest实例
	 */
	public HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
	}

	/**
	 * 得到分页列表的信息
	 */
	public Page getPage() {
		String currentResult = request.getParameter("offset");//管理分页编码
		String currentPage = request.getParameter("currentPage");//业务分页编码
		Page page = new Page();
		if(!Tools.isObjEmpty(request.getParameter("showCount"))){//未传入每页显示条数的,默认10条一页
			page.setShowCount(Integer.parseInt(request.getParameter("showCount")));
		}
		if(Tools.isObjEmpty(currentPage)){
			if(Tools.isObjEmpty(currentResult)){
				page.setCurrentResult(0);// 分页开始位置
			}else {
				if(Tools.isObjEmpty(currentResult)){
					page.setCurrentResult(0);// 分页开始位置
				}else {
					page.setCurrentResult(Integer.parseInt(currentResult));// 分页开始位置
				}
			}
		}else{
			//业务功能分页
			int startLimt = (Integer.parseInt(currentPage)-1) * page.getShowCount(); //当前页数
			page.setCurrentResult(startLimt);// 分页开始位置
		}

		return page;
	}

	/**
	 * 得到PageData
	 */
	public PageData getPageData() {
		return new PageData(this.getRequest());
	}


	/**
	 * 将json对象中包含的null和JSONNull属性修改成""
	 * @param jsonObj
	 */
	public JSONObject filterNull(JSONObject jsonObj){
		Iterator<String> it = jsonObj.keys();
		Object obj = null;
		String key = null;
		while (it.hasNext()) {
			key = it.next();
			obj = jsonObj.get(key);
			if(obj instanceof JSONObject){
				filterNull((JSONObject)obj);
			}
			if(obj == null || obj instanceof JSONNull){
				jsonObj.put(key, "");
			}
		}
		return jsonObj;
	}

	/**
	 * 获取当前登录的用户
	 * @return
	 * 		当前用户的Pd
	 */
	public PageData getUserPd(){
		PageData pd = (PageData)request.getSession().getAttribute(AllConfig.SESSION_KEY);
		return pd;
	}

	/**
	 * 写入当前登录用户 id，供业务写入审计等使用。
	 * 注意：不要把 role_manage_id 写进分页查询 pd——会与「按角色筛选」等请求参数同名，
	 * 导致 Sys_userMapper / Permission_menuMapper 误加 and role_manage_id=?，列表只剩与当前登录人同角色的记录。
	 */
	public PageData putUserPd(PageData pd){
		final PageData userPd = getUserPd();
		if(!Tools.isObjEmpty(userPd)){
			Object uid = userPd.get("id");
			if (uid == null) {
				uid = userPd.get("ID");
			}
			if (uid != null) {
				String sid = String.valueOf(uid).trim();
				if (!sid.isEmpty() && !"null".equalsIgnoreCase(sid)) {
					// 兼容业务入参本身就有 user_id（如团队用户关联中的被邀请用户），避免被当前登录人覆盖。
					if (Tools.isEmpty(pd.getString("user_id"))) {
						pd.put("user_id", sid);
					}
					// 统一保留当前登录人 id 供审计/数据范围使用。
					pd.put("login_user_id", sid);
				}
			}
		}
		return pd;
	}

	/** 仅用于需要「非系统管理员按本人数据范围过滤」的列表（如团队用户、任务进度） */
	public PageData putUserPdDataScope(PageData pd) {
		putUserPd(pd);
		final PageData userPd = getUserPd();
		if (!Tools.isObjEmpty(userPd) && userPd.get("role_manage_id") != null) {
			pd.put("role_manage_id", userPd.get("role_manage_id"));
		}
		return pd;
	}

	/**
	 * 上传文件获取路径
	 * @param file
	 * @return
	 */
	@SuppressWarnings("unused")
	public JSONObject uploadPhoto(MultipartFile file) {
		JSONObject json = new JSONObject();
		if (file.isEmpty()) {
			return this.getFalJson();
		}
		// 获取文件名
		String fileName = file.getOriginalFilename();
		// 获取文件的后缀名
		String suffixName = fileName.substring(fileName.lastIndexOf("."));
		String path = uploadAndWriteImg(file);
		//成功，方便调用者的判断
		json.put("msg", 200);
		json.put("fileName", fileName);
		json.put("path", path);
		return json;
	}

	/**
	 * 获取操作失败返回的json
	 * @return
	 */
	public JSONObject getFalJson(){
		JSONObject json = new JSONObject();
		json.put("msg", "500");
		json.put("result", "filed");
		return json;
	}

	/**
	 * 上传生成文件
	 *
	 * @return String 上传后文件可访问路径
	 * */
	public String uploadAndWriteImg(MultipartFile file){
		try {
			String 	path = UploadUtil.uploadFile(file, "ybu014");
			path = Config.getStaticFilesAccessURL(path);
			return path;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}



}
