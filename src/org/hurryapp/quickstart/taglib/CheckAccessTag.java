package org.hurryapp.quickstart.taglib;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.quickstart.Constants;
import org.hurryapp.quickstart.data.model.Utilisateur;
import org.hurryapp.quickstart.view.AccessUtil;

public class CheckAccessTag extends TagSupport {

	private String groups;
	private String resources;

	public CheckAccessTag() {
		super();
	}

	@Override
	public int doStartTag() throws JspException {
		boolean canAccess = false;
		HttpSession session = this.pageContext.getSession();

		try {
			Utilisateur user = (Utilisateur) session.getAttribute(Constants.SESSION_KEY_USER);

			if (user != null) {
				//WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(this.pageContext.getServletContext());
				//GroupeService groupeService = (GroupeService) wac.getBean("groupeService");
				//RessourceService ressourceService = (RessourceService) wac.getBean("ressourceService");

				//-------------------------------------------------------------
				// Vérification des groupes
				//-------------------------------------------------------------
				if (!StringUtil.isEmpty(this.groups)) {
					int indexOfAnd = this.groups.indexOf("&&");
					String[] groupsTab = null;

					// AND
					if (indexOfAnd != -1) {
						canAccess = true;
						groupsTab = this.groups.split("&&");
						for (String group : groupsTab) {
							if (!AccessUtil.canAccessGroup(user, Integer.valueOf(group))) {
								canAccess = false;
								break;
							}
						}
					}
					// OR
					else {
						groupsTab = this.groups.split("\\|\\|");
						for (String group : groupsTab) {
							if (AccessUtil.canAccessGroup(user, Integer.valueOf(group))) {
								canAccess = true;
								break;
							}
						}
					}
				}
				//-------------------------------------------------------------
				// Vérification des ressources
				//-------------------------------------------------------------
				else if (!StringUtil.isEmpty(this.resources)) {
					int indexOfAnd = this.resources.indexOf("&&");
					String[] resourcesTab = null;

					// AND
					if (indexOfAnd != -1) {
						canAccess = true;
						resourcesTab = this.resources.split("&&");
						for (String resource : resourcesTab) {
							if (!AccessUtil.canAccessResource(user, resource)) {
								canAccess = false;
								break;
							}
						}
					}
					// OR
					else {
						resourcesTab = this.resources.split("\\|\\|");
						for (String resource : resourcesTab) {
							if (AccessUtil.canAccessResource(user, resource)) {
								canAccess = true;
								break;
							}
						}
					}
				}
			}
		}
		catch(Exception e) {
			throw new JspException("Erreur lors de la génération du menu", e);
		}


		if (canAccess) {
			return EVAL_BODY_INCLUDE;
		}
		else {
			return SKIP_BODY;
		}
	}

	public String getGroups() {
		return this.groups;
	}
	public void setGroups(String groups) {
		this.groups = groups;
	}

	public String getResources() {
		return this.resources;
	}
	public void setResources(String resources) {
		this.resources = resources;
	}
}
