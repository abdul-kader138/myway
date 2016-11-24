package org.hurryapp.quickstart.view.action.certificate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hurryapp.fwk.annotation.Unsecured;
import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.view.action.BaseAction;
import org.hurryapp.fwk.view.json.JSONError;

import com.sotouch.myway.Constants;
import com.sotouch.myway.service.map.MapService;

import org.hurryapp.quickstart.data.dao.config.ConfigParameterDAO;
import org.hurryapp.quickstart.data.model.Utilisateur;
import org.hurryapp.quickstart.service.mail.MailService;
import org.hurryapp.quickstart.service.utilisateur.UtilisateurService;
import org.hurryapp.quickstart.utils.CertificateUtils;
import org.hurryapp.quickstart.utils.ThreeDESCode;
import org.hurryapp.quickstart.view.AccessUtil;
import org.hurryapp.quickstart.view.action.utilisateur.UtilisateurAction;

/**
 * Class which manages login
 */
@Unsecured
public class CertificateAction extends BaseAction<CertificateViewBean> {
	
	/**
	 * Init
	 * @return
	 */
	@Override
	public String init() {
		return SUCCESS;
	}

	@Override
	protected void doInit() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doExecute() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	//=========================================================================
	// ACCESSEURS
	//=========================================================================
	
	//=========================================================================
	// ACCESSEURS
	//=========================================================================

	public String getUniqueKey() {
		try {
			return super.getTexts("global-messages").getString("login.cetificate.message").replace("%s", CertificateUtils.getServerUniqueKey());
		} catch(Exception e) {
			return super.getTexts("global-messages").getString("login.cetificate.get.failed") + e;
		}
	}

}
