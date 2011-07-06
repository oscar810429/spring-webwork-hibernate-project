/*
 * @(#)LinkDAOHibernate.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.core.dao.hibernate;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.type.Type;
import org.springframework.orm.ObjectRetrievalFailureException;
import com.painiu.core.dao.LinkDAO;
import com.painiu.core.model.link.Link;
import com.painiu.core.model.link.PartnerImage;
import com.painiu.core.model.link.PictureLink;
import com.painiu.core.model.link.TagLink;
import com.painiu.core.model.link.TextLinkage;
import com.painiu.core.search.Result;

/**
 * <p>
 * <a href="LinkDAOHibernate.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: LinkDAOHibernate.java 35 2010-06-01 01:53:10Z zhangsf $
 */
public class LinkDAOHibernate extends BaseDAOHibernate implements LinkDAO{

	//~ Static fields/initializers =============================================
	
	//~ Instance fields ========================================================
	
	//~ Constructors ===========================================================
	
	//~ Methods ================================================================
	/* (non-Javadoc)
	 * @see com.painiu.core.dao.LinkDAO#getActivityImage(int, int)
	 */
	public Result getActivityImage(int start, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.LinkDAO#getAdvertisement(int, int)
	 */
	public Result getAdvertisement(int start, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.LinkDAO#getGroupNews(int, int)
	 */
	public Result getGroupNews(int start, int limit) {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see com.painiu.core.dao.LinkDAO#getImageLink(int, int)
	 */
	public Result getImageLink(int start, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.LinkDAO#getImportantNews(int, int)
	 */
	public Result getImportantNews(int start, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.LinkDAO#getIndexImage(int, int)
	 */
	public Result getIndexImage(int start, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.LinkDAO#getLink(java.lang.String)
	 */
	public Link getLink(String id) {
		Link link = (Link) getHibernateTemplate().get(Link.class, id);
		if (link == null) {
			log.warn("uh oh, link[" + id + "] not found...");
            throw new ObjectRetrievalFailureException(Link.class, id);
		}
		
		return link;
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.LinkDAO#getPartnerImage(int, int)
	 */
	public Result getPartnerImage(int start, int limit) {
		String countSql = "select count(p.id) from PartnerImage p";
        String sql = "from PartnerImage p  order by p.createdDate desc";
        return find(countSql, sql,
        		null,null,
    			start, limit);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.LinkDAO#getPictureLink(int, int)
	 */
	public Result getPictureLink(int start, int limit) {
		String countSql = "select count(p.id) from PictureLink p";
        String sql = "from PictureLink p  order by p.createdDate desc";
        return find(countSql, sql,
        		null,null,
    			start, limit);
	}



	/* (non-Javadoc)
	 * @see com.painiu.core.dao.LinkDAO#getSystemNews(int, int)
	 */
	public Result getSystemNews(int start, int limit) {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see com.painiu.core.dao.LinkDAO#getTagNews(int, int)
	 */
	public Result getTagNews(int start, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.LinkDAO#getTextLink(int, int)
	 */
	public Result getTextLink(int start, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.LinkDAO#getTextLinkage(int, int)
	 */
	public Result getTextLinkage(int start, int limit) {
		String countSql = "select count(text.id) from TextLinkage text";
        String sql = "from TextLinkage text  order by text.createdDate desc";
        return find(countSql, sql,
        		null,null,
    			start, limit);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.LinkDAO#removeLink(com.painiu.core.model.link.Link)
	 */
	public void removeLink(Link link) {
		if (log.isDebugEnabled()) {
			log.debug("removing " + link);
		}
		getHibernateTemplate().delete(link);
		
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.LinkDAO#saveLink(com.painiu.core.model.link.Link)
	 */
	public void saveLink(Link link) {
		if (log.isDebugEnabled()) {
			log.debug("saving " + link);
		}
		getHibernateTemplate().saveOrUpdate(link);
		
	}
	//~ Accessors ==============================================================

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.LinkDAO#getPartnerImage(java.lang.String)
	 */
	public PartnerImage getPartnerImage(String id) {
		PartnerImage partnerImage = (PartnerImage) getHibernateTemplate().get(PartnerImage.class, id);
		if (partnerImage == null) {
			log.warn("uh oh, partnerImage[" + id + "] not found...");
            throw new ObjectRetrievalFailureException(PartnerImage.class, id);
		}
		
		return partnerImage;
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.LinkDAO#getPictureLink(java.lang.String)
	 */
	public PictureLink getPictureLink(String id) {
		PictureLink pictureLink = (PictureLink) getHibernateTemplate().get(PictureLink.class, id);
		if (pictureLink == null) {
			log.warn("uh oh, pictureLink[" + id + "] not found...");
            throw new ObjectRetrievalFailureException(PictureLink.class, id);
		}
		
		return pictureLink;
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.LinkDAO#getTextLinkage(java.lang.String)
	 */
	public TextLinkage getTextLinkage(String id) {
		TextLinkage textLinkage = (TextLinkage) getHibernateTemplate().get(TextLinkage.class, id);
		if (textLinkage == null) {
			log.warn("uh oh, textLinkage[" + id + "] not found...");
            throw new ObjectRetrievalFailureException(TextLinkage.class, id);
		}
		
		return textLinkage;
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.LinkDAO#getPartnerImage(int)
	 */
	public List getPartnerImage(int amount) {
		return find("from PartnerImage p order by p.order asc",amount);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.LinkDAO#getPictureLink(int)
	 */
	public List getPictureLink(int amount) {
		return find("from PictureLink p order by p.order asc",amount);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.LinkDAO#getTextLinkage(int)
	 */
	public List getTextLinkage(int amount) {
		return find("from TextLinkage t order by t.createdDate desc",amount);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.LinkDAO#getRandomTag(int, int)
	 */
	public List getRandomTag(int kind, int amount) {
		return find("from TagLink tag where tag.kind = " + kind +" order by rand()",amount);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.LinkDAO#getTag(java.lang.String)
	 */
	public TagLink getTag(String id) {
		TagLink tag = (TagLink) getHibernateTemplate().get(TagLink.class, id);
		if (tag == null) {
			log.warn("uh oh, Tag[" + id + "] not found...");
            throw new ObjectRetrievalFailureException(TagLink.class, id);
		}		
		return tag;
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.LinkDAO#getTagAmountByTitle(int, java.lang.String)
	 */
	public int getTagAmountByTitle(int kind, String title) {
		String sql = "select count(tag.id) from TagLink tag where tag.kind = ? and tag.title = ?";
        return count(sql,
        		new Object[] {kind,title},
				new Type[] {Hibernate.INTEGER,Hibernate.STRING} );	
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.LinkDAO#getTagList(int, int)
	 */
	public List getTagList(int kind, int amount) {
		return find("from TagLink tag where tag.kind = "+ kind +" order by tag.order asc",amount);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.LinkDAO#getTagResult(int, int, int)
	 */
	public Result getTagResult(int kind, int start, int limit) {
		String countSql = "select count(tag.id) from TagLink tag where tag.kind = ?";
        String sql = "from TagLink tag where tag.kind = ?  order by tag.order asc";
        return find(countSql, sql,
        		new Object[] {kind},
				new Type[] {Hibernate.INTEGER},
    			start, limit);
	}

	/* (non-Javadoc)
	 * @see com.painiu.core.dao.LinkDAO#getTagByTitle(int, java.lang.String)
	 */
	public TagLink getTagByTitle(int kind, String title) {
		String countSql = "select count(tag.id) from TagLink tag where tag.kind = ? and tag.title = ? ";
        String sql = "from TagLink tag where tag.kind = ?  and tag.title = ? ";
        List list = find(countSql, sql,
        		new Object[] {kind,title},
				new Type[] {Hibernate.INTEGER,Hibernate.STRING},
    			-1, 1).getData();
		if(list !=null && list.size() > 0){
			return (TagLink)(list.get(0));
		}
		return null;
	}

}
