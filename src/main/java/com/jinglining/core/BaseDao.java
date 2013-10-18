package com.jinglining.core;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;


public class BaseDao<T,PK extends Serializable> {

	private Class<?> clazz;
	
	public BaseDao() {
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		Type[] types = type.getActualTypeArguments();
		clazz = (Class<?>) types[0];
		System.out.println(clazz.getName());
	}
	
	private SessionFactory sessionFactory;
	
	@Inject
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public void save(T pojo) {
		getSession().saveOrUpdate(pojo);
	}
	
	
	@SuppressWarnings("unchecked")
	public T findById(PK id) {
		return (T) getSession().get(clazz, id);
	}
	
	@SuppressWarnings("unchecked")
	public T findByProperty(String propertyName,Object value) {
		Criteria c = getSession().createCriteria(clazz);
		c.add(Restrictions.eq(propertyName, value));
		
		return (T) c.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findListByProperty(String propertyName,Object value) {
		Criteria c = getSession().createCriteria(clazz);
		c.add(Restrictions.eq(propertyName, value));
		return c.list();
	}
	
	public void del(PK id) {
		getSession().delete(findById(id));
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		Criteria c = getSession().createCriteria(clazz);
		return c.list();
	}
	@SuppressWarnings("unchecked")
	public List<T> findAll(List<Condition> conditions) {
		return builderCriteriaByConditions(conditions).list();
	}
	
	@SuppressWarnings("unchecked")
	public Page<T> findAll(Page<T> page,List<Condition> conditions) {
		Criteria c = builderCriteriaByConditions(conditions);
		
		Long rowCount = getRowContByCriteria(c);
		page.setTotalCount(rowCount);
		c.setFirstResult(page.getOffset());
		c.setMaxResults(page.getPagesize());
		
		//鎺掑簭
		if(page.getOrder() != null && page.getOrderBy() != null) {
			String[] orders = page.getOrder().split(","); //id,name
			String[] orderBys = page.getOrderBy().split(",");
			if(orders.length == orderBys.length) {
				for (int i = 0; i < orderBys.length; i++) {
					if(orderBys[i].equalsIgnoreCase("asc")) {
						c.addOrder(Order.asc(orders[i]));
					} else if(orderBys[i].equalsIgnoreCase("desc")) {
						c.addOrder(Order.desc(orders[i]));
					}
				}
			} else {
				throw new IllegalArgumentException("");
			}
		}
		
		List<T> list = c.list();
		page.setResult(list);
		return page;
	}

	private Long getRowContByCriteria(Criteria c) {
		
		@SuppressWarnings("static-access")
		ResultTransformer rt = c.ROOT_ENTITY;
		
		c.setProjection(Projections.rowCount());
		Long result = (Long) c.uniqueResult();
		
		c.setProjection(null);
		c.setResultTransformer(rt);
		
		return result == null ? 0 : result;
	}

	private Criteria builderCriteriaByConditions(List<Condition> conditions) {
		Criteria c = getSession().createCriteria(clazz);
		for(Condition condition : conditions) {
			Criterion criterion = builderCriterionByCondition(condition);
			c.add(criterion);
		}
		return c;
	}

	private Criterion builderCriterionByCondition(Condition condition) {
		String propertyName = condition.getPropertyName();
		String matchType = condition.getMatchType();
		String value = condition.getValue();
		
		if(propertyName.contains("_OR_")) {
			String[] pNames = propertyName.split("_OR_");
			
			Disjunction disjunction = Restrictions.disjunction();
			for (int i = 0; i < pNames.length; i++) {
				Criterion c = bilderCriterion(pNames[i],matchType,value);
				disjunction.add(c);
			}
			return disjunction;
		} else {
			return bilderCriterion(propertyName, matchType, value);
		}
		
		
	}

	private Criterion bilderCriterion(String propertyName, String matchType,
			String value) {
		if(matchType.equalsIgnoreCase("EQ")) {
			return Restrictions.eq(propertyName, value);
		} else if(matchType.equalsIgnoreCase("LE")) {
			return Restrictions.le(propertyName, value);
		} else if(matchType.equalsIgnoreCase("LT")) {
			return Restrictions.lt(propertyName, value);
		} else if(matchType.equalsIgnoreCase("GE")) {
			return Restrictions.ge(propertyName, value);
		} else if(matchType.equalsIgnoreCase("GT")) {
			return Restrictions.gt(propertyName, value);
		} else if(matchType.equalsIgnoreCase("LIKE")) {
			return Restrictions.like(propertyName, value, MatchMode.ANYWHERE);
		}else if(matchType.equalsIgnoreCase("IN")){
			String[] str = value.split(",");
 			return Restrictions.in(propertyName, str);
		} else if(matchType.equalsIgnoreCase("BETWEEN")){
			String [] str = value.split(",");
			return Restrictions.between(propertyName, str[0], str[1]);
			
		}else{
			throw new IllegalArgumentException("");
		}
		
	}
	
	
}
