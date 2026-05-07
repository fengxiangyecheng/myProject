package com.testdemo.service;
import com.testdemo.common.ServerResponse;
import com.testdemo.entity.system.Page;
import com.testdemo.entity.system.PageData;
import com.testdemo.mapper.App_schedule_commentMapper;
import com.testdemo.mapper.App_teamMapper;
import com.testdemo.mapper.App_team_scheduleMapper;
import com.testdemo.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class App_schedule_commentService extends BaseService {

	private static final int MAX_CONTENT_LEN = 500;
	private static final int MAX_ID_FIELD_LEN = 32;

	@Autowired
	private App_schedule_commentMapper _mapper;
	@Autowired
	private App_teamMapper _teamMapper;
	@Autowired
	private App_team_scheduleMapper _teamScheduleMapper;

	/** 仅保留可序列化字符串，避免把 session_user 等对象传入 MyBatis 导致 OGNL/绑定异常 */
	private static String toParamStr(Object o) {
		if (o == null) {
			return "";
		}
		if (o instanceof String[]) {
			String[] a = (String[]) o;
			if (a.length == 0) {
				return "";
			}
			String s = a[0] != null ? String.valueOf(a[0]).trim() : "";
			return "null".equalsIgnoreCase(s) ? "" : s;
		}
		String s = String.valueOf(o).trim();
		return "null".equalsIgnoreCase(s) ? "" : s;
	}

	private PageData buildCleanCommentWritePd(PageData source) {
		PageData m = new PageData();
		m.put("schedule_id", toParamStr(source.get("schedule_id")));
		m.put("user_id", toParamStr(source.get("user_id")));
		m.put("content", toParamStr(source.get("content")));
		String pid = toParamStr(source.get("parent_id"));
		m.put("parent_id", Tools.isEmpty(pid) ? "0" : pid);
		return m;
	}

	private PageData buildCleanCommentUpdatePd(PageData source) {
		PageData m = buildCleanCommentWritePd(source);
		m.put("id", toParamStr(source.get("id")));
		return m;
	}

	/** 与库字段长度一致，避免超长导致 MySQL 抛错、前端只显示「网络错误」 */
	private ServerResponse<String> validateCleanCommentRow(PageData row, boolean requireId) {
		if (requireId) {
			String id = toParamStr(row.get("id"));
			if (Tools.isEmpty(id)) {
				return ServerResponse.createByErrorMessage("主键id不能为空");
			}
			if (id.length() > MAX_ID_FIELD_LEN) {
				return ServerResponse.createByErrorMessage("主键id过长");
			}
		}
		String sid = toParamStr(row.get("schedule_id"));
		if (Tools.isEmpty(sid)) {
			return ServerResponse.createByErrorMessage("请选择日程");
		}
		if (sid.length() > MAX_ID_FIELD_LEN) {
			return ServerResponse.createByErrorMessage("日程ID过长");
		}
		String uid = toParamStr(row.get("user_id"));
		if (uid.length() > MAX_ID_FIELD_LEN) {
			return ServerResponse.createByErrorMessage("留言人ID过长");
		}
		String pid = toParamStr(row.get("parent_id"));
		if (pid.length() > MAX_ID_FIELD_LEN) {
			return ServerResponse.createByErrorMessage("父留言ID过长");
		}
		String content = toParamStr(row.get("content"));
		if (Tools.isEmpty(content)) {
			return ServerResponse.createByErrorMessage("请填写留言内容");
		}
		if (content.length() > MAX_CONTENT_LEN) {
			return ServerResponse.createByErrorMessage("留言内容不超过" + MAX_CONTENT_LEN + "字");
		}
		return null;
	}

	private static String strLoginId(PageData sessionUser) {
		if (sessionUser == null) {
			return "";
		}
		Object id = sessionUser.get("id");
		if (id == null) {
			id = sessionUser.get("ID");
		}
		if (id == null) {
			return "";
		}
		String s = String.valueOf(id).trim();
		return "null".equalsIgnoreCase(s) ? "" : s;
	}

	private boolean isSysAdminRole(PageData u) {
		return u != null && "1".equals(String.valueOf(u.get("role_manage_id")));
	}

	private boolean canManageTeamById(PageData userPd, String teamId) {
		if (Tools.isEmpty(teamId) || userPd == null) {
			return false;
		}
		if (isSysAdminRole(userPd)) {
			return true;
		}
		PageData q = new PageData();
		q.put("id", teamId);
		List<PageData> teams = _teamMapper.queryApp_teamKey(q);
		if (teams == null || teams.isEmpty()) {
			return false;
		}
		PageData t = teams.get(0);
		String uid = strLoginId(userPd);
		if (Tools.isEmpty(uid)) {
			return false;
		}
		return uid.equals(String.valueOf(t.get("admin_id"))) || uid.equals(String.valueOf(t.get("creator_id")));
	}

	private boolean canProxyCommentForSchedule(PageData sessionUser, String scheduleId) {
		if (sessionUser == null || Tools.isEmpty(scheduleId)) {
			return false;
		}
		if (isSysAdminRole(sessionUser)) {
			return true;
		}
		PageData q = new PageData();
		q.put("id", scheduleId);
		List<PageData> schs = _teamScheduleMapper.queryApp_team_scheduleKey(q);
		if (schs == null || schs.isEmpty()) {
			return false;
		}
		String teamId = String.valueOf(schs.get(0).get("team_id"));
		return canManageTeamById(sessionUser, teamId);
	}

	private void fillScheduleIdForCommentUpdate(PageData pd) {
		if (!Tools.isEmpty(toParamStr(pd.get("schedule_id"))) || Tools.isEmpty(toParamStr(pd.get("id")))) {
			return;
		}
		PageData k = new PageData();
		k.put("id", toParamStr(pd.get("id")));
		PageData sessionUser = (PageData) pd.get("session_user");
		if (sessionUser != null) {
			Object r = sessionUser.get("role_manage_id");
			if (r != null) {
				k.put("role_manage_id", String.valueOf(r));
			}
			String lid = strLoginId(sessionUser);
			if (!Tools.isEmpty(lid)) {
				k.put("login_user_id", lid);
			}
		}
		List<PageData> rows = _mapper.queryApp_schedule_commentKey(k);
		if (rows != null && !rows.isEmpty() && rows.get(0).get("schedule_id") != null) {
			pd.put("schedule_id", String.valueOf(rows.get(0).get("schedule_id")));
		}
	}

	/** 非系统管理员仅能操作本人可访问团队下的日程（与列表 SQL 数据范围一致） */
	private ServerResponse<String> assertScheduleAccessible(PageData sessionUser, String scheduleId) {
		if (sessionUser == null) {
			return ServerResponse.createByErrorMessage("请先登录");
		}
		if (isSysAdminRole(sessionUser)) {
			return null;
		}
		if (Tools.isEmpty(scheduleId)) {
			return ServerResponse.createByErrorMessage("请选择日程");
		}
		PageData q = new PageData();
		q.put("id", scheduleId);
		q.put("login_user_id", strLoginId(sessionUser));
		Object r = sessionUser.get("role_manage_id");
		if (r != null) {
			q.put("role_manage_id", String.valueOf(r));
		}
		List<PageData> schs = _teamScheduleMapper.queryApp_team_scheduleKey(q);
		if (schs == null || schs.isEmpty()) {
			return ServerResponse.createByErrorMessage("无权限操作该团队日程或日程不存在");
		}
		return null;
	}

	private ServerResponse<String> enforceScheduleCommentActor(PageData pd) {
		PageData sessionUser = (PageData) pd.get("session_user");
		if (sessionUser == null) {
			return ServerResponse.createByErrorMessage("请先登录");
		}
		String loginId = strLoginId(sessionUser);
		if (Tools.isEmpty(loginId)) {
			return ServerResponse.createByErrorMessage("当前登录人无效");
		}
		fillScheduleIdForCommentUpdate(pd);
		String req = toParamStr(pd.get("user_id"));
		if (Tools.isEmpty(req) || loginId.equals(req)) {
			pd.put("user_id", loginId);
			return null;
		}
		if (Tools.isEmpty(toParamStr(pd.get("schedule_id")))) {
			return ServerResponse.createByErrorMessage("代发留言须关联有效日程");
		}
		if (!canProxyCommentForSchedule(sessionUser, toParamStr(pd.get("schedule_id")))) {
			return ServerResponse.createByErrorMessage("无代发权限：仅系统管理员或该团队的管理员可为他人留言");
		}
		return null;
	}

	@Transactional
	public ServerResponse<String> addApp_schedule_commentNo(PageData pd) {
		if (Tools.isObjEmpty(pd)) {
			return ServerResponse.createByErrorMessage("参数错误");
		}
		ServerResponse<String> deny = enforceScheduleCommentActor(pd);
		if (deny != null) {
			return deny;
		}
		PageData ins = buildCleanCommentWritePd(pd);
		ServerResponse<String> bad = validateCleanCommentRow(ins, false);
		if (bad != null) {
			return bad;
		}
		ServerResponse<String> scopeDeny = assertScheduleAccessible((PageData) pd.get("session_user"), toParamStr(ins.get("schedule_id")));
		if (scopeDeny != null) {
			return scopeDeny;
		}
		PageData dupQ = new PageData();
		dupQ.put("schedule_id", toParamStr(ins.get("schedule_id")));
		dupQ.put("user_id", toParamStr(ins.get("user_id")));
		dupQ.put("content", toParamStr(ins.get("content")));
		dupQ.put("parent_id", toParamStr(ins.get("parent_id")));
		PageData sessionUser = (PageData) pd.get("session_user");
		if (sessionUser != null) {
			String lid = strLoginId(sessionUser);
			if (!Tools.isEmpty(lid)) {
				dupQ.put("login_user_id", lid);
			}
			Object r = sessionUser.get("role_manage_id");
			if (r != null) {
				dupQ.put("role_manage_id", String.valueOf(r));
			}
		}
		List<PageData> list = _mapper.queryApp_schedule_commentKey(dupQ);
		if (list.size() > 0) {
			return ServerResponse.createByErrorMessage("团队日程留言交互管理已存在");
		}
		try {
			int rowCount = _mapper.addApp_schedule_comment(ins);
			if (rowCount > 0) {
				return ServerResponse.createBySuccessMessage("添加团队日程留言交互管理成功");
			}
			return ServerResponse.createByErrorMessage("添加团队日程留言交互管理失败");
		} catch (Exception e) {
			return ServerResponse.createByErrorMessage("添加失败：" + (e.getMessage() != null ? e.getMessage() : "数据异常"));
		}
	}

	@Transactional
	public ServerResponse<String> addApp_schedule_commentAll(PageData pd) {
		if (Tools.isObjEmpty(pd)) {
			return ServerResponse.createByErrorMessage("参数错误");
		}
		ServerResponse<String> deny = enforceScheduleCommentActor(pd);
		if (deny != null) {
			return deny;
		}
		PageData ins = buildCleanCommentWritePd(pd);
		ServerResponse<String> bad = validateCleanCommentRow(ins, false);
		if (bad != null) {
			return bad;
		}
		ServerResponse<String> scopeDeny = assertScheduleAccessible((PageData) pd.get("session_user"), toParamStr(ins.get("schedule_id")));
		if (scopeDeny != null) {
			return scopeDeny;
		}
		try {
			int rowCount = _mapper.addApp_schedule_comment(ins);
			if (rowCount > 0) {
				return ServerResponse.createBySuccessMessage("添加团队日程留言交互管理成功");
			}
			return ServerResponse.createByErrorMessage("添加团队日程留言交互管理失败");
		} catch (Exception e) {
			return ServerResponse.createByErrorMessage("添加失败：" + (e.getMessage() != null ? e.getMessage() : "数据异常"));
		}
	}

	public ServerResponse<String> deleteApp_schedule_comment(PageData pd) {
		if (Tools.isEmpty(toParamStr(pd.get("id")))) {
			return ServerResponse.createByErrorMessage("删除团队日程留言交互管理参数错误");
		}
		PageData sessionUser = (PageData) pd.get("session_user");
		if (sessionUser == null) {
			return ServerResponse.createByErrorMessage("请先登录");
		}
		PageData k = new PageData();
		k.put("id", toParamStr(pd.get("id")));
		if (sessionUser != null) {
			String lid = strLoginId(sessionUser);
			if (!Tools.isEmpty(lid)) {
				k.put("login_user_id", lid);
			}
			Object r = sessionUser.get("role_manage_id");
			if (r != null) {
				k.put("role_manage_id", String.valueOf(r));
			}
		}
		List<PageData> exist = _mapper.queryApp_schedule_commentKey(k);
		if (exist == null || exist.isEmpty()) {
			return ServerResponse.createByErrorMessage("删除失败或无权操作该留言");
		}
		PageData del = new PageData();
		del.put("id", toParamStr(pd.get("id")));
		try {
			int rowCount = _mapper.deleteApp_schedule_commentId(del);
			if (rowCount > 0) {
				return ServerResponse.createBySuccessMessage("删除团队日程留言交互管理成功");
			}
			return ServerResponse.createByErrorMessage("删除团队日程留言交互管理失败");
		} catch (Exception e) {
			return ServerResponse.createByErrorMessage("删除失败：" + (e.getMessage() != null ? e.getMessage() : "数据异常"));
		}
	}

	@Transactional
	public ServerResponse<String> updateApp_schedule_comment(PageData pd) {
		if (Tools.isEmpty(toParamStr(pd.get("id")))) {
			return ServerResponse.createByErrorMessage("修改团队日程留言交互管理参数错误");
		}
		ServerResponse<String> deny = enforceScheduleCommentActor(pd);
		if (deny != null) {
			return deny;
		}
		PageData up = buildCleanCommentUpdatePd(pd);
		ServerResponse<String> bad = validateCleanCommentRow(up, true);
		if (bad != null) {
			return bad;
		}
		ServerResponse<String> scopeDeny = assertScheduleAccessible((PageData) pd.get("session_user"), toParamStr(up.get("schedule_id")));
		if (scopeDeny != null) {
			return scopeDeny;
		}
		try {
			int rowCount = _mapper.updateApp_schedule_comment(up);
			if (rowCount > 0) {
				return ServerResponse.createBySuccessMessage("修改团队日程留言交互管理成功");
			}
			return ServerResponse.createByErrorMessage("修改团队日程留言交互管理失败");
		} catch (Exception e) {
			return ServerResponse.createByErrorMessage("保存失败：" + (e.getMessage() != null ? e.getMessage() : "数据异常"));
		}
	}

	public ServerResponse<List<PageData>> queryApp_schedule_commentKey(PageData pd) {
		if (Tools.isObjEmpty(pd)) {
			return ServerResponse.badArgument();
		}
		List<PageData> list = _mapper.queryApp_schedule_commentKey(pd);
		return ServerResponse.createBySuccess(list);
	}

	public List<PageData> queryPageApp_schedule_commentKeyList(Page pd) {
		List<PageData> list = _mapper.queryPageApp_schedule_commentKeyList(pd);
		return list;
	}
}
