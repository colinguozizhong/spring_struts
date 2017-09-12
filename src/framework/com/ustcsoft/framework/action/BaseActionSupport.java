package com.ustcsoft.framework.action;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.googlecode.jsonplugin.JSONException;
import com.googlecode.jsonplugin.JSONUtil;
import com.googlecode.jsonplugin.annotations.JSON;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.ustcsoft.framework.exception.BusinessException;
import com.ustcsoft.framework.service.IBaseServiceIbatis;
import com.ustcsoft.framework.util.Constants;
import com.ustcsoft.framework.vo.BaseVO;
import com.ustcsoft.framework.vo.PubUserVO;
import com.ustcsoft.framework.vo.UserVO;

public class BaseActionSupport extends ActionSupport implements
		ServletRequestAware, ServletResponseAware {

	private static final long serialVersionUID = 7834736236105975470L;

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	protected UserVO userInfo;
	protected PubUserVO pubUserInfo;
	
	private Vector<String> errMessage;
	private String focusId = "";
	protected String jsonString;

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	@SuppressWarnings("rawtypes")
	protected IBaseServiceIbatis baseService;

	protected int start;
	protected int limit;

	public void setServletRequest(HttpServletRequest req) {
		this.request = req;
		this.session = request.getSession();
		this.userInfo = (UserVO) session.getAttribute("userInfo");
		this.pubUserInfo = (PubUserVO)session.getAttribute("pubUserInfo");
	}

	public void setServletResponse(HttpServletResponse res) {
		this.response = res;
	}
	
	public PubUserVO getPubUserInfo() {
		return pubUserInfo;
	}

	public void setPubUserInfo(PubUserVO pubUserInfo) {
		this.pubUserInfo = pubUserInfo;
	}

	public UserVO getUserInfo() {
		return userInfo;
	}
	
	public void setUserInfo(UserVO userInfo) {
		this.userInfo = userInfo;
	}

	
	public void writeError() {
		try {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter writer = response.getWriter();
			writer.write("{\"success\":false,\"errors\":true,\"msg\":'");
			for (String msg : errMessage) {
				writer.write(msg);
				writer.write("<br/>");
			}
			writer.write("'");
			writer.write(",\"focusId\":'" + focusId);
			focusId = "";
			errMessage.clear();
			writer.write("'}");
			writer.flush();
			writer.close();
		} catch (java.io.IOException exc) {
			exc.printStackTrace();
		}
	}

	public void writeJson(Object obj) {
		try {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter writer = response.getWriter();
			writer.write(JSONUtil.serialize(obj, null, null, false, true).replace("<", "&lt;").replace(">", "&gt;"));
//			writer.write(JSONUtil.serialize(obj));
			writer.flush();
			writer.close();
		} catch (java.io.IOException exc) {
			exc.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void writeTreeJson(String obj) {
		try {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter writer = response.getWriter();
			writer.write(obj);
			writer.flush();
			writer.close();
		} catch (java.io.IOException exc) {
			exc.printStackTrace();
		}
	}

	public void writeJson(Object obj, boolean isSuccess) {
		try {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter writer = response.getWriter();
			if (isSuccess) {
				writer.write("{\"success\":true,\"data\":");
			} else {
				writer.write("{\"success\":false,\"errors\":true,\"data\":");
			}
			if (obj instanceof BaseVO) {
				writer.write(JSONUtil.serialize(obj, null, null, false, true));
			} else {
				writer.write(JSONUtil.serialize(obj, null, null, true, true));
			}
			writer.write("}");
			writer.flush();
			writer.close();
		} catch (java.io.IOException exc) {
			exc.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void write(String outStr) {
		try {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter writer = response.getWriter();
			writer.write(outStr);
			writer.flush();
			writer.close();
		} catch (java.io.IOException exc) {
			exc.printStackTrace();
		}
	}

	public void writeXml(String outStr) {
		try {
			response.setContentType("text/xml;charset=gbk");
			PrintWriter writer = response.getWriter();
			writer.write(outStr);
			writer.flush();
			writer.close();
		} catch (java.io.IOException exc) {
			exc.printStackTrace();
		}
	}

	/**
	 * 获取session Map
	 * 
	 * @return Map
	 */

	@SuppressWarnings("rawtypes")
	@JSON(serialize = false)
	public Map getSession() {
		return ActionContext.getContext().getSession();
	}

	/**
	 * 向MODEL中添加当前用户信息
	 * 
	 * @param model
	 *            要插入的对象
	 * @throws Exception
	 */
	public void addUserOrgDeptToModel(Object model) throws BusinessException {
		try {
			UserVO userInfo = (UserVO) getSession().get("userInfo");
			if (null == userInfo) {
				userInfo = new UserVO();
			}
			Class<?> c = model.getClass();
			Method setter = c.getMethod("setUserOrgId", String.class);
			setter.invoke(model, userInfo.getOrgId());
	
			setter = c.getMethod("setUserDeptId", String.class);
			setter.invoke(model, userInfo.getDeptId());
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	/**
	 * 向MODEL中添加当前用户信息
	 * 
	 * @param model
	 *            要插入的对象
	 * @throws Exception
	 */
	public void addUserInfoToModel(Object model) throws Exception {
		UserVO userInfo = (UserVO) getSession().get("userInfo");
		if (null == userInfo) {
			userInfo = new UserVO();
		}

		Class<?> c = model.getClass();
		Method getter = null;
		Method setter = null;

		// 创建者
		getter = c.getMethod("getCreater");
		setter = c.getMethod("setCreater", String.class);
		if (getter.invoke(model) == null) {
			setter.invoke(model, String.valueOf(userInfo.getUserId()));
		}

		// 创建时间
		getter = c.getMethod("getCreateTime");
		setter = c.getMethod("setCreateTime", Date.class);
		if (getter.invoke(model) == null) {
			setter.invoke(model, new Date());
		}
		addUserInfoToUpdateModel(model);
		addUserOrgDeptToModel(model);
		/*// 更新者
		setter = c.getMethod("setUpdater", String.class);
		setter.invoke(model, String.valueOf(userInfo.getUserId()));

		// 更新时间
		setter = c.getMethod("setUpdateTime", Date.class);
		setter.invoke(model, new Date());
*/
		setter = c.getMethod("setRowVersion", Long.class);
		setter.invoke(model, 1L);

		setter = c.getMethod("setDelFlg", String.class);
		setter.invoke(model, Constants.DELETE_STATUS_NO);
	}

	/**
	 * 向MODEL中添加当前用户信息
	 * 
	 * @param model
	 *            更新的对象
	 * @throws Exception
	 */
	public void addUserInfoToUpdateModel(Object model) throws Exception {
		UserVO userInfo = (UserVO) getSession().get("userInfo");
		if (null == userInfo) {
			userInfo = new UserVO();
		}

		Class<?> c = model.getClass();
		Method setter = null;

		// 更新者
		setter = c.getMethod("setUpdater", String.class);
		setter.invoke(model, String.valueOf(userInfo.getUserId()));

		// 更新时间
		setter = c.getMethod("setUpdateTime", Date.class);
		setter.invoke(model, new Date());

	}

	/**
	 * 向MODEL中添加当前用户信息
	 * 
	 * @param model
	 *            要插入的对象
	 * @throws Exception
	 */
	public void addPubUserInfoToModel(Object model) throws Exception {
		PubUserVO userInfo = (PubUserVO) session.getAttribute("pubUserInfo");
		if (null == userInfo) {
			userInfo = new PubUserVO();
		}

		Class<?> c = model.getClass();
		Method getter = null;
		Method setter = null;

		// 创建者
		getter = c.getMethod("getCreater");
		setter = c.getMethod("setCreater", String.class);
		if (getter.invoke(model) == null) {
			setter.invoke(model, String.valueOf(userInfo.getUserId()));
		}

		// 创建时间
		getter = c.getMethod("getCreateTime");
		setter = c.getMethod("setCreateTime", Date.class);
		if (getter.invoke(model) == null) {
			setter.invoke(model, new Date());
		}
		addUserInfoToUpdateModel(model);
		addUserOrgDeptToModel(model);
		// 更新者
		setter = c.getMethod("setUpdater", String.class);
		setter.invoke(model, String.valueOf(userInfo.getUserId()));

		// 更新时间
		setter = c.getMethod("setUpdateTime", Date.class);
		setter.invoke(model, new Date());

		setter = c.getMethod("setRowVersion", Long.class);
		setter.invoke(model, 1L);

		setter = c.getMethod("setDelFlg", String.class);
		setter.invoke(model, Constants.DELETE_STATUS_NO);
	}
	
	/**
	 * 向MODEL中添加当前用户信息
	 * 
	 * @param model
	 *            更新的对象
	 * @throws Exception
	 */
	public void addPubUserInfoToUpdateModel(Object model) throws Exception {
		PubUserVO userInfo = (PubUserVO) getSession().get("pubUserInfo");
		if (null == userInfo) {
			userInfo = new PubUserVO();
		}

		Class<?> c = model.getClass();
		Method setter = null;

		// 更新者
		setter = c.getMethod("setUpdater", String.class);
		setter.invoke(model, String.valueOf(userInfo.getUserId()));

		// 更新时间
		setter = c.getMethod("setUpdateTime", Date.class);
		setter.invoke(model, new Date());

	}
	
	/**
	 * 向MODEL列表中添加当前用户信息
	 * 
	 * @param <T>
	 *            指定类型
	 * @param models
	 *            要插入的对象列表
	 * @throws Exception
	 */
	public <T extends BaseVO> void addUserInfoToModels(List<T> models)
			throws Exception {

		if (models != null) {

			for (Object model : models) {
				addUserInfoToModel(model);
			}
		}
	}
	
	/**
	 * 向MODEL列表中添加当前用户信息
	 * 
	 * @param <T>
	 *            指定类型
	 * @param models
	 *            要更新的对象列表
	 * @throws Exception
	 */
	public <T extends BaseVO> void addUserInfoToUpdateModels(List<T> models)
			throws Exception {
		if (models != null) {
			for (Object model : models) {
				addUserInfoToUpdateModel(model);
			}
		}
	}

	protected void addError(String msgId, Object... params) {
		if (errMessage == null) {
			errMessage = new Vector<String>();
		}
		errMessage.add(String.format(getText(msgId), params));
	}

	/**
	 * 校验出错后,画面指定id的控件获取焦点
	 * 
	 * @param id
	 */
	protected void addFocus(String id) {
		focusId = id;
	}

	public boolean isInvalid() {
		if (errMessage != null && errMessage.size() > 0) {
			writeError();
			return true;
		}
		return false;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	@SuppressWarnings("rawtypes")
	public IBaseServiceIbatis getBaseService() {
		return baseService;
	}

	@SuppressWarnings("rawtypes")
	public void setBaseService(IBaseServiceIbatis baseService) {
		this.baseService = baseService;
	}
}
