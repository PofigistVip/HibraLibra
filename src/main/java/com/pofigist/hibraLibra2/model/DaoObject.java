package com.pofigist.hibraLibra2.model;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.pofigist.hibraLibra2.entities.Library;

public class DaoObject<T> {
	private void inTransaction(SessionExpression expr) {
		Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        expr.execute(session);
        tx1.commit();
        session.close();
	}
	
	public void save(T obj) {
		inTransaction((ses) -> ses.save(obj));
	}
	
	public void update(T obj) {
        inTransaction((ses) -> ses.update(obj));
    }
	
	public void delete(T obj) {
        inTransaction((ses) -> ses.delete(obj));
    }
	
	protected List<T> getAll(Class<T> cls) {
		return HibernateUtil.getSessionFactory().openSession().createQuery("From " + cls.getName(), cls).list();
	}
	
	interface SessionExpression {
		void execute(Session session);
	}
}


