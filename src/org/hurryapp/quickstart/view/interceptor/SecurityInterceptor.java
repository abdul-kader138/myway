package org.hurryapp.quickstart.view.interceptor;

import org.hurryapp.fwk.annotation.Unsecured;
import org.hurryapp.fwk.view.action.BaseAction;
import org.hurryapp.fwk.view.exception.NotLoggedException;
import org.hurryapp.quickstart.Constants;
import org.hurryapp.quickstart.data.model.Utilisateur;
import org.hurryapp.quickstart.view.AccessUtil;
import org.hurryapp.quickstart.view.action.login.LoginAction;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class SecurityInterceptor extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		BaseAction action = (BaseAction) invocation.getAction();

		//------------------------------------------------------------------------------
		// Check authentification
		//------------------------------------------------------------------------------
		if (!action.getClass().isAnnotationPresent(Unsecured.class)) {
			if (action.getSession().get(Constants.SESSION_KEY_USER) == null) {
				throw new NotLoggedException();
			}
		}

		//------------------------------------------------------------------------------
		// Check trial version rights
		//------------------------------------------------------------------------------
		Utilisateur user = (Utilisateur) action.getSession().get(Constants.SESSION_KEY_USER);
		String projectKey = (String) action.getSession().get(com.sotouch.myway.Constants.SESSION_KEY_PROJECT_KEY);
		String method = invocation.getProxy().getMethod();
		if (!(action instanceof LoginAction) &&
			user != null && !AccessUtil.canAccessGroup(user, com.sotouch.myway.Constants.GROUPE_SUPER_ADMIN) &&
			projectKey != null && projectKey.startsWith("trial") &&
			!method.startsWith("init") && !method.startsWith("load") && !method.equals("edit") && !method.equals("logout")) {
			action.getJsonResponse().setStatus(1001);
			return BaseAction.SUCCESS_JSON;
		}
		else {
			//------------------------------------------------------------------------------
			// Invoke action method
			//------------------------------------------------------------------------------
			return invocation.invoke();
		}
	}
}