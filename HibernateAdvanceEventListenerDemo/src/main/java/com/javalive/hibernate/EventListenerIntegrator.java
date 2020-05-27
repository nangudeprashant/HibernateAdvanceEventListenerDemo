package com.javalive.hibernate;

import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;

import com.javalive.event.LoadEventListenerImp;
import com.javalive.event.RefreshEventListenerImp;
import com.javalive.event.SaveUpdateEventListenerImp;

public class EventListenerIntegrator implements Integrator {

	@Override
	public void integrate(Metadata metadata, SessionFactoryImplementor sessionFactory,
			SessionFactoryServiceRegistry serviceRegistry) {

		EventListenerRegistry eventListenerRegistry = serviceRegistry.getService(EventListenerRegistry.class);

		eventListenerRegistry.getEventListenerGroup(EventType.SAVE).appendListener(new SaveUpdateEventListenerImp());

		eventListenerRegistry.getEventListenerGroup(EventType.LOAD).appendListener(new LoadEventListenerImp());

		eventListenerRegistry.getEventListenerGroup(EventType.REFRESH).appendListener(new RefreshEventListenerImp());
	}

	@Override
	public void disintegrate(SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {

	}

}
