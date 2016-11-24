package com.sotouch.myway.data.dao;

import java.util.List;

import org.hurryapp.fwk.data.dao.BaseDAO;

import com.sotouch.myway.data.model.NewsletterEmail;

public class NewsletterEmailDAO extends BaseDAO<NewsletterEmail> {

	public NewsletterEmailDAO() {
		super();
	}
	
	public List<NewsletterEmail> findByProject(Integer projectId) {
		return super.findByNamedQuery("select.newsLetterEmail.by.project", new Object[]{projectId});
	}

	public NewsletterEmail findByEmail(String email) {
		return (NewsletterEmail) super.findUniqueResultByNamedQuery("select.newsLetterEmail.by.email", new Object[]{email});
	}

	public Long countByProject(Integer projectId) {
		return (Long) super.findUniqueResultByNamedQuery("count.newsLetterEmail.by.email", new Object[]{projectId});
	}
}
