package com.sotouch.myway.data.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hurryapp.fwk.data.model.BaseDataBean;


public class Item extends BaseDataBean {

	private String name;
	private String logo;
	private String address;
	private String phoneNumber;
	private String email;
	private Date hourBegin;
	private Date hourEnd;
	private String photos;
	private String photosDir;
	private Boolean disabledAccess;
	private Integer type;
	private ItemType itemType;
	private Project project;
	private Category category;
	private Zone zone;
	private SubCategory subCategory;
	private Icon icon;
	private List<ItemContent> itemContents = new ArrayList<ItemContent>();
	private Set<Event> events = new LinkedHashSet<Event>();
	private Set<Promotion> promotions = new LinkedHashSet<Promotion>();
	private Set<ItemMap> itemMaps = new LinkedHashSet<ItemMap>();

	public Item() {
		super();
	}

	public Item(Integer id) {
		super(id);
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getAddress() {
		return this.address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public Date getHourBegin() {
		return this.hourBegin;
	}
	public void setHourBegin(Date hourBegin) {
		this.hourBegin = hourBegin;
	}

	public Date getHourEnd() {
		return this.hourEnd;
	}
	public void setHourEnd(Date hourEnd) {
		this.hourEnd = hourEnd;
	}

	public String getPhotos() {
		return this.photos;
	}
	public void setPhotos(String photos) {
		this.photos = photos;
	}

	public String getPhotosDir() {
		return photosDir;
	}
	public void setPhotosDir(String photosDir) {
		this.photosDir = photosDir;
	}

	public Boolean getDisabledAccess() {
		return disabledAccess;
	}
	public void setDisabledAccess(Boolean disabledAccess) {
		this.disabledAccess = disabledAccess;
	}

	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}

	public ItemType getItemType() {
		return this.itemType;
	}
	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

	public Project getProject() {
		return this.project;
	}
	public void setProject(Project project) {
		this.project = project;
	}

	public Category getCategory() {
		return this.category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}

	public SubCategory getSubCategory() {
		return this.subCategory;
	}
	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}

	public Zone getZone() {
		return this.zone;
	}
	public void setZone(Zone zone) {
		this.zone = zone;
	}
	
	public Icon getIcon() {
		return icon;
	}
	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	public List<ItemContent> getItemContents() {
		return itemContents;
	}
	public void setItemContents(List<ItemContent> itemContents) {
		this.itemContents = itemContents;
	}

	public Set<Event> getEvents() {
		return events;
	}
	public void setEvents(Set<Event> events) {
		this.events = events;
	}

	public Set<Promotion> getPromotions() {
		return promotions;
	}
	public void setPromotions(Set<Promotion> promotions) {
		this.promotions = promotions;
	}

	public Set<ItemMap> getItemMaps() {
		return itemMaps;
	}
	public void setItemMaps(Set<ItemMap> itemMaps) {
		this.itemMaps = itemMaps;
	}
}
