package com.sotouch.myway;


public class Constants {
	// Session
	public final static String SESSION_KEY_PROJECT_ID   = "SESSION_PROJECT_ID";
	public final static String SESSION_KEY_PROJECT_NAME = "SESSION_PROJECT_NAME";
	public final static String SESSION_KEY_PROJECT_KEY  = "SESSION_PROJECT_KEY";
	public final static String SESSION_KEY_PROJECTS     = "SESSION_PROJECTS";

	// Groups
	public final static Integer GROUPE_SUPER_ADMIN = 1;
	public final static Integer GROUPE_ADMIN       = 2;
	public final static Integer GROUPE_USER        = 3;

	// Item types
	public final static Integer ITEM_TYPE_LOCATION   = 1;
	public final static Integer ITEM_TYPE_ACCESS     = 2;
	public final static Integer ITEM_TYPE_FUTO       = 3;
	public final static Integer ITEM_TYPE_WEBVIEW    = 4;
	public final static Integer ITEM_TYPE_TERMINAL   = 7;

	// Language english
	public final static String LANGUAGE_CODE_ENGLISH = "gb";
	public final static String LANGUAGE_NAME_ENGLISH = "English";

	// Config
	public final static String CONFIG_DOMAIN_PATHS = "paths";
	public final static String CONFIG_PARAMETER_EXTERNAL_FILES_PATH = "externalFilesPath";
	public final static String CONFIG_PARAMETER_BUY_ADS_FEATURE_URL = "buyAdsFeatureUrl";
	public final static String CONFIG_RENEW_PROJECT_URL = "renewProjectUrl";
	public final static String CONFIG_BUY_LICENSE_URL = "buyLicenseUrl";

	// Resource directories
	public final static String DIR_PROJECTS                = "project-exports";
	public final static String DIR_PROJECT_LOGO            = "logo";
	public final static String DIR_PROJECT_NEWSLETTER_LOGO = "newsletterLogo";
	public final static String DIR_PROJECT_CREDITS_IMAGE   = "creditsImage";
	public final static String DIR_FLOORS                  = "floors";
	public final static String DIR_ZONES                   = "zones";
	public final static String DIR_ICONS                   = "icons";
	public final static String DIR_ICON                    = "icon";
	public final static String DIR_ITEMS                   = "items";
	public final static String DIR_PROMOTIONS              = "promotions";
	public final static String DIR_LICENSES                = "licenses";
	public final static String DIR_EVENTS                  = "events";
	public final static String DIR_ADS                     = "ads";
	public final static String DIR_ADS_FULLSCREEN          = "fullscreen";
	public final static String DIR_ADS_BANNER              = "banner";
	public final static String DIR_THEME	               = "theme";
	
	// Date formats
	public final static String HOUR_FORMAT = "hh:mm a";
	public final static String DAY_FORMAT = "MM/dd/yyyy";
	
	// Image sizes
	public final static int IMAGE_SIZE_MAX_WEIGHT             = 10; // Mo
	public final static int IMAGE_SIZE_PROJECT_LOGO_WIDTH     = 290;
	public final static int IMAGE_SIZE_PROJECT_LOGO_HEIGHT    = 80;
	public final static int IMAGE_SIZE_NEWSLETTER_LOGO_WIDTH  = 250;
	public final static int IMAGE_SIZE_NEWSLETTER_LOGO_HEIGHT = 70;
	public final static int IMAGE_SIZE_CREDITS_LOGO_WIDTH     = 250;
	public final static int IMAGE_SIZE_CREDITS_LOGO_HEIGHT    = 70;
	public final static int IMAGE_SIZE_FLOOR_WIDTH            = 8191;
	public final static int IMAGE_SIZE_FLOOR_HEIGHT           = 8191;
	public final static int IMAGE_SIZE_FLOOR_NB_PIXELS        = 16500000;
	public final static int IMAGE_SIZE_ICON_WIDTH             = 100;
	public final static int IMAGE_SIZE_ICON_HEIGHT            = 100;
	public final static int IMAGE_SIZE_GALLERY_ITEM_WIDTH     = 1600;
	public final static int IMAGE_SIZE_GALLERY_ITEM_HEIGHT    = 1024;
	public final static int IMAGE_SIZE_AD_WIDTH               = 8191;
	public final static int IMAGE_SIZE_AD_HEIGHT              = 8191;
	public final static int IMAGE_SIZE_AD_NB_PIXELS           = 16500000;
	
	// Google API
	public final static String GOOGLE_API_REFERER             = "sotouch-myway.com";
	public final static String GOOGLE_API_KEY                 = "AIzaSyDMXXbsoO72mZe4ygopLKfcHKUZ-6UBOe8";
	public final static int GOOGLE_API_CHAR_LIMIT			  = 1500;
}
