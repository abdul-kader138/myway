package org.hurryapp.quickstart.junit;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.quickstart.data.model.Menu;
import org.hurryapp.quickstart.service.menu.MenuService;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

public class TestMenuWriter extends TestCase {

	private XmlBeanFactory bf;
	private Menu root;
	private MenuService menuService;

	/**
	 */
	public TestMenuWriter() {
	}

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		bf = new XmlBeanFactory(new ClassPathResource("applicationContext.xml"));
		menuService = (MenuService) bf.getBean("menuService");
		root = (Menu) menuService.findById(1);
	}

	/**
	 */
	public void testDisplayTreeRec() {
		try {
			Menu menu = this.root;
			if (menu.getChildren().size() > 0) {
				List<Menu> childs = (List<Menu>) menu.getChildren();
				for(Menu children: childs) {
					displayTreeRec(children);
				}
			}
		}
		catch(Exception e) {
			assertTrue("ERROR : " + e.getMessage().toString(), false);
			e.printStackTrace();
		}
	}

	/**
	 */
	private void displayTreeRec(Menu menu) {
		// début du noeud
		if(menu.getParent().getId().equals(root.getId())) {
			System.out.println("menuBar.add(new Ext.Toolbar.Button({text: '"+menu.getLibelle()+"'");
			System.out.println(",listeners: {'mouseover': function(){this.showMenu()}}");
		}
		else {
			System.out.println("{text: '"+menu.getLibelle()+"'");
		}
		
		if (!StringUtil.isEmpty(menu.getUrl())) {
			System.out.println(",handler: function(){window.location=myAppContext+'"+menu.getUrl()+"'}");
		}

		// parcours des fils
		if (menu.getChildren().size() > 0) {
			System.out.println(",menu : {");
			System.out.println("subMenuAlign: 'tl-tr?',");
			System.out.println("items: [");
			List<Menu> childs = (List<Menu>) menu.getChildren();
			for(Menu children: childs) {
				displayTreeRec(children);
			}
			System.out.println("]");
			System.out.println("}");
		}
		
		// fin du noeud
		System.out.println("}");
		if(menu.getParent().getId().equals(root.getId())) {
			System.out.println("));");
		}
		else {
			if (existsBrother(menu, (List<Menu>) menu.getParent().getChildren())) {
				System.out.println(",");
			}
		}
	}

	/**
	 */
	@SuppressWarnings("unchecked")
	public void testDisplayTreeIter() {
		try {
			System.out.println("Root :"+this.root.getLibelle());
			Menu menu = this.root;
			List childs = new ArrayList();
			do {
				if(menu.getId().equals(root.getId())) {
					if (menu.getChildren().size() > 0) {
						childs = menu.getChildren();
						menu = (Menu) childs.get(0);
					}
				}
				else {
					if(menu.getParent().getId().equals(root.getId())) {
						System.out.println("menuBar.add(new Ext.Toolbar.Button({text: '"+menu.getLibelle()+"'");
						System.out.println(",listeners: {'mouseover': function(){this.showMenu()}}");
					}
					else {
						System.out.println("{text: '"+menu.getLibelle()+"'");
					}
					
					if (!StringUtil.isEmpty(menu.getUrl())) {
						System.out.println(",handler: function(){window.location=myAppContext+'"+menu.getUrl()+"'}");
					}
					
					// le menu est un noeud
					if (menu.getChildren().size() > 0) {
						System.out.println(",menu : {");
						System.out.println("subMenuAlign: 'tl-tr?',");
						System.out.println("items: [");
						childs = menu.getChildren();
						menu = (Menu) childs.get(0);
					}
					// le menu est une feuille
					else {
						// => remonter au 1er père qui a un fils non encore visité
						if(!menu.getId().equals(root.getId()) && !existsBrother(menu, childs)) {
							if(menu.getParent().getId().equals(root.getId())) {
								System.out.println("}");
								System.out.println("));");
							}
							else {
								System.out.println("}");
								System.out.println("]");
								System.out.println("}");
							}
							menu = (Menu) menu.getParent();
							if (menu.getParent() != null) {
								childs = menu.getParent().getChildren();
							}
						}
						
						if (existsBrother(menu, childs)) {
							if(menu.getParent().getId().equals(root.getId())) {
								System.out.println("}");
								System.out.println("));");
							}
							else {
								System.out.println("},");
							}
					
							if (existsBrother(menu, childs)) {
								menu = getBrother(menu, childs);
							}
						}
					}
				}
			} while(!menu.getId().equals(root.getId()));
		}
		catch(Exception e) {
			assertTrue("ERROR : " + e.getMessage().toString(), false);
			e.printStackTrace();
		}
	}

	private boolean existsBrother(Menu menu, List<Menu> brothers) {
		boolean existsBrother = false;
		if (brothers.indexOf(menu) > -1 && brothers.indexOf(menu) < brothers.size()-1) {
			existsBrother = true;
		}
		return existsBrother;
	}
	
	private Menu getBrother(Menu menu, List<Menu> brothers) {
		Menu brother = null;
		if (brothers.indexOf(menu) > -1 && brothers.indexOf(menu) < brothers.size()-1) {
			brother = brothers.get(brothers.indexOf(menu)+1);
		}
		return brother;
	}
	
}
