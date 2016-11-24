package com.sotouch.myway.view.action.imagegalery;

import java.io.File;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.hurryapp.fwk.util.DateUtil;
import org.hurryapp.fwk.util.ImageResizer;
import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.view.action.BaseAction;
import org.hurryapp.fwk.view.json.JSONError;
import org.hurryapp.fwk.view.json.JSONResponse;
import org.hurryapp.quickstart.service.config.ConfigParameterService;

import com.sotouch.myway.Constants;

/**
 * Class which manages the image galeries
 */
public class ImageGalleryAction extends BaseAction {
	
	private String dir;
	private String image;
	//private String imageContentType;
	private String imageFileName;
	private String fileNames;

	private static int MIN_WIDTH  = 100;
	private static int MIN_HEIGHT = 100;

	@Resource (name="configParameterService")
	private ConfigParameterService configParameterService;

	@Override
	protected void doExecute() throws Exception {}

	@Override
	protected void doInit() throws Exception {}

	public String upload() throws Exception {
		if (image != null) {
			ImageResizer imageResizer = new ImageResizer(image);
			// Check minimum size
			if (imageResizer.getOriginalImageWidth() < MIN_WIDTH || imageResizer.getOriginalImageHeight() < MIN_HEIGHT) {
				jsonResponse.addError(new JSONError(getText("item.photos.tooltip.min")));
			}
			else {
				if (ImageResizer.getWeight(image) > Constants.IMAGE_SIZE_MAX_WEIGHT) {
					jsonResponse.addError(new JSONError(getText("common.error.image.maxSize")));
				}
				else {
					//String uploadPath = (String) configParameterService.findParameter(Constants.CONFIG_DOMAIN_PATHS, Constants.CONFIG_PARAMETER_UPLOAD_PATH);
					String uploadPath = (String) configParameterService.findParameter(Constants.CONFIG_DOMAIN_PATHS, Constants.CONFIG_PARAMETER_EXTERNAL_FILES_PATH);

					// Rename file 
					int indexOfDot = imageFileName.lastIndexOf('.');
					String newImageFileName = imageFileName.substring(0, indexOfDot)+DateUtil.toString(new Date(), "yyyyMMddhhmmssS")+imageFileName.substring(indexOfDot);
					
					// Copy the file into the requested directory
					String filePath = uploadPath+"/"+dir+"/"+newImageFileName;
					FileUtils.copyFile(new File(image), new File(filePath));

					// Resize mage
					filePath = ImageResizer.resize(filePath, Constants.IMAGE_SIZE_GALLERY_ITEM_WIDTH, Constants.IMAGE_SIZE_GALLERY_ITEM_HEIGHT, null);
					newImageFileName = filePath.substring(filePath.lastIndexOf('/')+1);

					// Return file name
					jsonResponse.setData(newImageFileName);
				}
			}
		}
		return SUCCESS_JSON;
	}

	public String delete() throws Exception {
		if (!StringUtil.isEmpty(fileNames)) {
			String uploadPath = (String) configParameterService.findParameter(Constants.CONFIG_DOMAIN_PATHS, Constants.CONFIG_PARAMETER_EXTERNAL_FILES_PATH);
			String[] fileNamesTab = fileNames.split(";");
			for (String fileName : fileNamesTab) {
				String filePath = uploadPath+"/"+dir+"/"+fileName;
				File file = new File(filePath);
				if (file.exists()) {
					file.delete();
				}
			}
		}
		return SUCCESS_JSON;
	}

	//=========================================================================
	// SPECIFIC METHODS
	//=========================================================================


	//=========================================================================
	// ACCESSORS
	//=========================================================================
	public JSONResponse getJsonResponse() {
		return this.jsonResponse;
	}
	public void setJsonResponse(JSONResponse jsonResponse) {
		this.jsonResponse = jsonResponse;
	}

	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

	public String getImageFileName() {
		return imageFileName;
	}
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public String getFileNames() {
		return fileNames;
	}
	public void setFileNames(String fileNames) {
		this.fileNames = fileNames;
	}
}
