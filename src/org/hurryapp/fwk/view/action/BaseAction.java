package org.hurryapp.fwk.view.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.hurryapp.fwk.view.PaginationBean;
import org.hurryapp.fwk.view.json.JSONError;
import org.hurryapp.fwk.view.json.JSONResponse;
import org.w3c.dom.Document;

import com.opensymphony.xwork2.ActionSupport;
import com.sotouch.myway.Constants;
import com.sotouch.myway.data.model.Project;
import com.sotouch.myway.service.project.ProjectService;

/**
 * Base class for every actions
 */
public abstract class BaseAction<T> extends ActionSupport implements SessionAware, ServletResponseAware, ServletRequestAware {

	public final static String SUCCESS_JSON = "success-json";
	public final static String DATAS_JSON   = "datas-json";
	public final static String ERROR_JSON   = "error-json";
	public final static String LOGIN_JSON   = "login-json";
	public final static String EXCEL        = "excel";
	public final static String SUCCESS_XML  = "success-xml";
	public final static String SUCCESS_TXT  = "success-txt";
	public final static String ERROR_TXT    = "error-txt";

	protected Map<String, Object> session;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected ResourceBundle resources = super.getTexts("global-messages");
	protected JSONResponse jsonResponse = new JSONResponse();
	protected InputStream xmlResponse;

	protected Class<T> viewBeanClass;
	protected BaseViewBean viewBean;
	protected PaginationBean paginationBean = new PaginationBean();

	@Resource (name="projectService")
	protected ProjectService projectService;

	//@RemoteProperty(jsonAdapter="properties:id,name")
	//public Set<Project> projects;

	protected Integer projectId;

	public BaseAction() {
		super();
		Type genericSuperclass = this.getClass().getGenericSuperclass();

		if (genericSuperclass instanceof ParameterizedType) {
			this.viewBeanClass = (Class<T>) ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
			try {
				this.viewBean = (BaseViewBean) this.viewBeanClass.newInstance();
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 */
	public String init() throws Exception {
		// Init
		this.doInit();

		// Get user's projects
		//this.projects = (Set<Project>) this.session.get(Constants.SESSION_KEY_PROJECTS);

		return SUCCESS;
	}

	/**
	 */
	protected abstract void doInit() throws Exception;

	/**
	 */
	@Override
	public String execute() throws Exception {
		//---------------------------------------------------------------------
		// Form validation
		//---------------------------------------------------------------------
		List<JSONError> errors = this.viewBean.validate(this.resources);

		if (!errors.isEmpty()) {
			//-----------------------------------------------------------------
			// Error messages
			//-----------------------------------------------------------------
			this.jsonResponse.setErrors(errors);
		}
		else {
			this.doExecute();
		}

		return SUCCESS_JSON;
	}

	/**
	 */
	protected abstract void doExecute() throws Exception;

	/**
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	protected Document stringToDocument(String xml) throws Exception {
        ByteArrayInputStream baos = new ByteArrayInputStream(xml.getBytes("UTF-8"));
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document doc = factory.newDocumentBuilder().parse(baos);
        return doc;
	}

	//=========================================================================
	// ACCESSORS
	//=========================================================================

	public Map getSession() {
		return this.session;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public HttpServletRequest getServletRequest() {
		return this.request;
	}
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getServletResponse() {
		return this.response;
	}
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public JSONResponse getJsonResponse() {
		return this.jsonResponse;
	}
	public void setJsonResponse(JSONResponse jsonResponse) {
		this.jsonResponse = jsonResponse;
	}
	public JSONResponse getJsonResponseUpload() {
		JSONResponse jsonResponseUpload = new JSONResponse();
		jsonResponseUpload.addError(new JSONError("name", getText("common.error.file.maxSize")));
		return jsonResponseUpload;
	}

	public InputStream getXmlResponse() {
		return xmlResponse;
	}
	public void setXmlResponse(InputStream xmlResponse) {
		this.xmlResponse = xmlResponse;
	}
	public void setXmlResponse(String xmlResponse) throws UnsupportedEncodingException {
		this.xmlResponse = new ByteArrayInputStream(xmlResponse.getBytes("UTF8"));;
	}

	public BaseViewBean getViewBean() {
		return this.viewBean;
	}
	public void setViewBean(BaseViewBean viewBean) {
		this.viewBean = viewBean;
	}

	public PaginationBean getPaginationBean() {
		return this.paginationBean;
	}
	public void setPaginationBean(PaginationBean paginationBean) {
		this.paginationBean = paginationBean;
	}

	/*
	public Set<Project> getProjects() {
		return projects;
	}
	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}
	*/

	public Project getProject() {
		Integer projectId = (Integer) session.get(Constants.SESSION_KEY_PROJECT_ID);
		if (projectId != null) {
			return ((Project) projectService.findById(projectId));
		}
		else {
			return null;
		}
	}

	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
}
