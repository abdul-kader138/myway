package com.sotouch.myway.service.item;

import com.sotouch.myway.data.dao.ItemDAO;
import com.sotouch.myway.data.model.Item;
import com.sotouch.myway.data.model.Project;
import java.io.File;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.io.FileUtils;
import org.hurryapp.fwk.data.dao.BaseDAO;
import org.hurryapp.fwk.service.BaseCrudService;
import org.hurryapp.quickstart.data.dao.config.ConfigParameterDAO;

public class ItemService extends BaseCrudService
{

  @Resource(name="configParameterDao")
  private ConfigParameterDAO configParameterDao;

  public void deleteById(Integer id)
    throws Exception
  {
    Item item = (Item)this.entityDao.findById(id);
    super.deleteById(id);

    String itemDir = (String)this.configParameterDao.findParameter("paths", "externalFilesPath") + "/" + "project-exports" + "/" + item.getProject().getKey() + "/" + "items" + "/" + item.getPhotosDir();
    FileUtils.deleteDirectory(new File(itemDir));
  }

  public List<Item> findByProject(Integer projectId)
  {
    return ((ItemDAO)this.entityDao).findByProject(projectId);
  }

  public Long countByProject(Integer projectId) {
    return ((ItemDAO)this.entityDao).countByProject(projectId);
  }

  public List<Item> findByProjectAndNoZone(Integer projectId) {
    return ((ItemDAO)this.entityDao).findByProjectAndNoZone(projectId);
  }

  public List<Item> findByZone(Integer zoneId) {
    return ((ItemDAO)this.entityDao).findByZone(zoneId);
  }

  public List<Item> findByProjectAndType(Integer projectId, Integer typeItemId) {
    return ((ItemDAO)this.entityDao).findByProjectAndType(projectId, typeItemId);
  }
}
