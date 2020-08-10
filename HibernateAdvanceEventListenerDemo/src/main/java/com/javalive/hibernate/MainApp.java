package com.javalive.hibernate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.javalive.entity.Book;

public class MainApp {
	private static Logger logger = LogManager.getLogger(MainApp.class);

	public static void main(String[] args) {

		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();

			transaction = session.getTransaction();
			transaction.begin();

			// Save Book
			Book book = new Book();
			book.setTitle("Thinking in Java");
			book.setAuthor("Bruce Eckel");
			session.save(book);

			// Load Book
			Book book2 = session.get(Book.class, book.getId());
			logger.info("Auther name before update - " + book2.getAuthor());

			// Update Book
			Query<?> query = session.createQuery("update Book set author=LOWER(author)" + " where id=:id");
			query.setParameter("id", book2.getId());
			query.executeUpdate();

			// Refresh Book
			session.refresh(book2);
			logger.info("Auther name after update - " + book2.getAuthor());

			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		HibernateUtil.shutdown();
	}

}
