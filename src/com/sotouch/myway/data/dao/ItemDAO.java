package com.sotouch.myway.data.dao;

import com.sotouch.myway.Constants;
import com.sotouch.myway.data.model.Item;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hurryapp.fwk.data.dao.BaseDAO;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class ItemDAO extends BaseDAO<Item>
{
  public List<Item> findByProject(Integer projectId)
  {
    return super.findByNamedQuery("select.item.by.project", new Object[] { projectId, Constants.ITEM_TYPE_TERMINAL });
  }

  public List<Item> findByProjectAndType(Integer projectId, Integer typeItemId) {
    return super.findByNamedQuery("select.item.by.project.and.type", new Object[] { projectId, typeItemId });
  }

  public List<Item> findByCategory(Integer categoryId) {
    return super.findByNamedQuery("select.item.by.category", new Object[] { categoryId });
  }

  public List<Item> findBySubCategory(Integer subCategoryId) {
    return super.findByNamedQuery("select.item.by.subCategory", new Object[] { subCategoryId });
  }

  public List<Item> findByProjectAndNoZone(Integer projectId) {
    return super.findByNamedQuery("select.item.by.project.and.no.zone", new Object[] { projectId });
  }

  public List<Item> findByZone(Integer zoneId) {
    return super.findByNamedQuery("select.item.by.zone", new Object[] { zoneId });
  }

  public Long countByProject(Integer projectId) {
    return (Long)super.findUniqueResultByNamedQuery("count.item.by.project", new Object[] { projectId });
  }

  protected Criteria getPageCriteria(Map<String, Object> criteriaMap, String order, String dir)
  {
    Criteria criteria = super.getPageCriteria(criteriaMap, order, dir);

    criteria.add(Restrictions.ne("itemType.id", Constants.ITEM_TYPE_TERMINAL));

    String search = (String)criteriaMap.get("\\search");
    if (search != null) {
      Criteria criteriaInner = super.getPageCriteria(criteriaMap, order, dir);
      criteriaInner.add(Restrictions.ne("itemType.id", Constants.ITEM_TYPE_TERMINAL));
      criteriaInner.createAlias("itemContents", "ic");
      Criterion name = Restrictions.like("ic.name", "%" + search + "%");
      Criterion keywords = Restrictions.like("ic.keywords", "%" + search + "%");
      LogicalExpression or = Restrictions.or(name, keywords);
      criteriaInner.add(or);
      Integer start = (Integer)criteriaMap.get("start");
      Integer limit = (Integer)criteriaMap.get("limit");
      if ((start != null) && (limit != null)) {
        criteriaInner.setFirstResult(start.intValue());
        criteriaInner.setMaxResults(limit.intValue());
      }
      criteriaInner.setProjection(Projections.distinct(Projections.property("id")));
      List elements = criteriaInner.list();
      if (elements.size() == 0) {
        elements.add(new Integer(-1));
      }
      criteria.add(Restrictions.in("id", elements));
    }

    return criteria;
  }

  public void deleteContent(Integer languageId)
  {
    Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery("delete from ItemContent as c where c.language.id = ?");
    query.setInteger(0, languageId.intValue());
    query.executeUpdate();
  }
}
