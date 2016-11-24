package org.hurryapp.quickstart.taglib;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.quickstart.Constants;
import org.hurryapp.quickstart.data.model.Menu;
import org.hurryapp.quickstart.data.model.Utilisateur;
import org.hurryapp.quickstart.service.menu.MenuService;
import org.hurryapp.quickstart.view.AccessUtil;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class MenuTag extends TagSupport {

	public MenuTag() {
		super();
	}

	@Override
	public int doStartTag() throws JspException {
		JspWriter out = this.pageContext.getOut();
		HttpSession session = this.pageContext.getSession();

		try {
			Utilisateur user = (Utilisateur) session.getAttribute(Constants.SESSION_KEY_USER);
			if (user != null) {
				// Récupération du menu en session
				String jsMenu = (String) session.getAttribute(Constants.SESSION_KEY_MENU);

				if (jsMenu == null) {
					// Création du menu
					StringBuffer jsMenuSb = new StringBuffer();
					jsMenuSb.append("<script type=\"text/javascript\">");
					jsMenuSb.append("Ext.onReady(function(){");
					jsMenuSb.append("var menuBar = new Ext.Toolbar({");
					jsMenuSb.append("renderTo: 'menu-panel',");
					jsMenuSb.append("items: ['->']");
					jsMenuSb.append("});");

					// Récupération du menu en DB
					WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(this.pageContext.getServletContext());
					MenuService menuService = (MenuService) wac.getBean("menuService");
					Menu root = (Menu) menuService.findById(1);
					Menu menu = root;

					// Parcours des menus de niveau 1
					if (menu.getChildren().size() > 0) {
						List<Menu> childs = (List<Menu>) menu.getChildren();
						for(Menu children: childs) {
							if (children != null) {
								this.printTree(children, root, jsMenuSb, user);
							}
						}
					}

					jsMenuSb.append("menuBar.doLayout();"); // TODO : uncomment to ExtJS 3 migration
					jsMenuSb.append("});");
					jsMenuSb.append("</script>");

					jsMenu = jsMenuSb.toString();

					// Le menu est stocké en session
					session.setAttribute(Constants.SESSION_KEY_MENU, jsMenu);
				}

				// Affichage du menu
				out.println(jsMenu);
			}
		}
		catch(Exception e) {
			throw new JspException("Erreur lors de la génération du menu", e);
		}

		return SKIP_BODY;
	}

	private void printTree(Menu menu, Menu root, StringBuffer jsMenu, Utilisateur user) {
		if (AccessUtil.canAccessResource(user, menu.getRessource().getLibelle())) {
			// Début du noeud
			if(menu.getParent().getId().equals(root.getId())) {
				String action = "";
				if (!"".equals(menu.getUrl())) {
					action = menu.getUrl().substring(1);
					action = action.substring(0, action.indexOf("/"));
				}
				jsMenu.append("menuBar.add(new Ext.Toolbar.Button({text: '"+menu.getLibelle()+"',id: 'menu-"+action+"'");
				//jsMenu.append("menuBar.addButton({text: '"+menu.getLibelle()+"'");
				jsMenu.append(",listeners: {'mouseover': function(){this.showMenu()}}");
			}
			else {
				jsMenu.append("{text: '"+menu.getLibelle()+"'");
			}

			if (!StringUtil.isEmpty(menu.getUrl())) {
				jsMenu.append(",handler: function(){goTo(myAppContext+'"+menu.getUrl()+"');}");
			}

			// Parcours des fils
			if (menu.getChildren().size() > 0) {
				jsMenu.append(",menu : {");
				jsMenu.append("subMenuAlign: 'tl-tr?',");
				jsMenu.append("items: [");

				List<Menu> childs = (List<Menu>) menu.getChildren();
				for(Menu children: childs) {
					if (children != null) {
						this.printTree(children, root, jsMenu, user);
					}
				}

				jsMenu.append("]");
				jsMenu.append("}");
			}

			// Fin du noeud
			jsMenu.append("}");
			if(menu.getParent().getId().equals(root.getId())) {
				//jsMenu.append(");");
				jsMenu.append("));");
			}
			else {
				if (this.existsBrother(menu, (List<Menu>) menu.getParent().getChildren(), user)) {
					jsMenu.append(",");
				}
			}
		}
	}

	private boolean existsBrother(Menu menu, List<Menu> brothers, Utilisateur user) {
		boolean existsBrother = false;
		int indexOfMenu = brothers.indexOf(menu);
		if (indexOfMenu > -1 && indexOfMenu < brothers.size()-1) {
			for(int i = indexOfMenu+1; i < brothers.size(); i++) {
				if (AccessUtil.canAccessResource(user, brothers.get(i).getRessource().getLibelle())) {
					existsBrother = true;
					break;
				}
			}
		}
		return existsBrother;
	}
}
