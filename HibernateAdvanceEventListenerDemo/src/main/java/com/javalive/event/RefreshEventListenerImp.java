package com.javalive.event;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.event.spi.RefreshEvent;
import org.hibernate.event.spi.RefreshEventListener;

import com.javalive.entity.Book;

public class RefreshEventListenerImp implements RefreshEventListener {

	private static final long serialVersionUID = 1L;
	private static Logger logger = LogManager.getLogger(RefreshEventListenerImp.class);

	@Override
	public void onRefresh(RefreshEvent e) throws HibernateException {
		logger.info("onRefresh is called.");
		Object obj = e.getObject();
		if (obj instanceof Book) {
			Book book = (Book) obj;
			logger.info(book);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void onRefresh(RefreshEvent e, Map refreshedAlready) throws HibernateException {
		logger.info("onRefresh is called.");
	}

}
