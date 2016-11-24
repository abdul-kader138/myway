package org.hurryapp.quickstart.view.interceptor;

import org.hurryapp.fwk.view.action.BaseAction;
import org.hurryapp.fwk.view.exception.NotLoggedException;
import org.hurryapp.fwk.view.json.JSONError;
import org.hurryapp.quickstart.Constants;
import org.hurryapp.quickstart.data.model.Utilisateur;
import org.hurryapp.quickstart.utils.CertificateUtils;
import org.hurryapp.quickstart.view.AccessUtil;
import org.hurryapp.quickstart.view.action.login.LoginAction;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class CertificateInterceptor extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		BaseAction action = (BaseAction) invocation.getAction();

		//-----------------------------------------------------------------
		// check certificate
		//-----------------------------------------------------------------
		
		try{
			CertificateUtils certificateUtils = new CertificateUtils();
			if(!CertificateUtils.getServerUniqueKey().equals(certificateUtils.getUniqueKey())) throw new Exception("certificate not match");
		} catch(Exception e) {
			return "certificate_failed";
		}
		
		//------------------------------------------------------------------------------
		// Invoke action method
		//------------------------------------------------------------------------------
		return invocation.invoke();
	}
}