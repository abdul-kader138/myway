package com.sotouch.myway.view.interceptor;

import org.hurryapp.fwk.view.action.BaseAction;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.sotouch.myway.Constants;
import com.sotouch.myway.data.model.Project;

public class RequestParametersInterceptor extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		BaseAction action = (BaseAction)invocation.getAction();

		//------------------------------------------------------------------------------
		// Put selected project in session
		//------------------------------------------------------------------------------
		Integer projectId = action.getProjectId();
		if (projectId != null) {
			action.getSession().put(Constants.SESSION_KEY_PROJECT_ID, projectId);
			Project project = action.getProject();
			action.getSession().put(Constants.SESSION_KEY_PROJECT_NAME, project.getName());
			action.getSession().put(Constants.SESSION_KEY_PROJECT_KEY, project.getKey());
		}

		//------------------------------------------------------------------------------
		// Invoke action method
		//------------------------------------------------------------------------------
		return invocation.invoke();
	}
}