package com.sotouch.myway.view.action.item;

import com.sotouch.myway.data.model.Item;
import com.sotouch.myway.data.model.ItemContent;
import com.sotouch.myway.data.model.Language;
import java.util.List;
import org.hurryapp.fwk.util.DateUtil;
import org.hurryapp.fwk.util.StringUtil;
import org.hurryapp.fwk.view.json.adapter.JSONAdapter;
import org.json.JSONArray;
import org.json.JSONObject;

public class ItemJSONAdapter
  implements JSONAdapter
{
  private String projectKey;
  private List<Language> languages;

  public ItemJSONAdapter(String projectKey, List<Language> languages)
  {
    this.projectKey = projectKey;
    this.languages = languages;
  }

  public JSONObject toJSONObject(Object object) throws Exception {
    JSONObject itemJson = new JSONObject();

    if ((object instanceof Item)) {
      Item item = (Item)object;
      itemJson.put("id", item.getId());
      itemJson.put("logo", item.getLogo());
      itemJson.put("name", item.getName());
      itemJson.put("address", item.getAddress());
      itemJson.put("phoneNumber", item.getPhoneNumber());
      itemJson.put("email", item.getEmail());
      itemJson.put("hourBegin", DateUtil.toString(item.getHourBegin(), "hh:mm a"));
      itemJson.put("hourEnd", DateUtil.toString(item.getHourEnd(), "hh:mm a"));
      itemJson.put("disabledAccess", item.getDisabledAccess());
      itemJson.put("type", item.getType());
      itemJson.put("itemType", item.getItemType().getName());
      itemJson.put("itemTypeId", item.getItemType().getId());
      itemJson.put("projectId", item.getProject().getId());
      itemJson.put("category", item.getCategory() != null ? item.getCategory().getName() : "");
      itemJson.put("categoryId", item.getCategory() != null ? item.getCategory().getId() : "");
      itemJson.put("subCategory", item.getSubCategory() != null ? item.getSubCategory().getName() : "");
      itemJson.put("subCategoryId", item.getSubCategory() != null ? item.getSubCategory().getId() : null);
      itemJson.put("zone", item.getZone() != null ? item.getZone().getName() : "");
      itemJson.put("zoneId", item.getZone() != null ? item.getZone().getId() : null);
      itemJson.put("icon", item.getIcon() != null ? item.getIcon().getName() : "");
      itemJson.put("iconId", item.getIcon() != null ? item.getIcon().getId() : null);
      itemJson.put("photosDir", item.getPhotosDir());

      if (!StringUtil.isEmpty(item.getPhotos())) {
        String[] photosTab = item.getPhotos().split(";");
        JSONArray photosJson = new JSONArray();
        for (String photo : photosTab) {
          photosJson.put(new JSONObject().put("dir", "project-exports/" + this.projectKey + "/" + "items" + "/" + item.getPhotosDir()).put("file", photo));
        }
        itemJson.put("photos", photosJson);
      }

      String visual = "/";
      if (item.getIcon() != null) {
        if (item.getIcon().getItemType() != null) {
          visual = visual + "icons/" + item.getIcon().getId() + "/" + item.getIcon().getIcon();
        }
        else {
          visual = visual + "project-exports/" + this.projectKey + "/" + "icons" + "/" + item.getIcon().getId() + "/" + item.getIcon().getIcon();
        }
      }
      else {
        visual = visual + "project-exports/" + this.projectKey + "/" + "items" + "/" + item.getPhotosDir() + "/" + "icon" + "/" + item.getLogo();
      }
      itemJson.put("visual", visual);

      int i = 0;
      for (Language language : this.languages) {
        ItemContent itemContent = getItemContent(item.getItemContents(), language.getId());
        itemJson.put("itemContents[" + i + "].languageId", language.getId());
        itemJson.put("itemContents[" + i + "].languageCode", language.getFlag());
        if (itemContent != null) {
          itemJson.put("itemContents[" + i + "].id", itemContent.getId());
          itemJson.put("itemContents[" + i + "].name", itemContent.getName());
          itemJson.put("itemContents[" + i + "].description", itemContent.getDescription());
          itemJson.put("itemContents[" + i + "].keywords", itemContent.getKeywords());
          itemJson.put("itemContents[" + i + "].openingDays", itemContent.getOpeningDays());
          itemJson.put("itemContents[" + i + "].url", itemContent.getUrl());

          if (language.getDefaultLanguage().booleanValue())
          {
            itemJson.put("name", itemContent.getName());
            itemJson.put("description", itemContent.getDescription());
          }
        }

        i++;
      }
    }

    return itemJson;
  }

  private ItemContent getItemContent(List<ItemContent> itemContents, Integer languageId) {
    for (ItemContent itemContent : itemContents) {
      if ((itemContent != null) && (itemContent.getLanguage().getId().equals(languageId))) {
        return itemContent;
      }
    }
    return null;
  }
}
