package com.shv.app.services.base;

import lombok.RequiredArgsConstructor;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.modelmapper.ModelMapper;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
public class BaseServiceIpm {
    protected ModelMapper modelMapper;
    private EntityManager entityManager;
    protected Session session;

    protected Filter filter;
    protected String filterName;

    protected String paramName;

    protected void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    protected void setSession() {
        this.session = entityManager.unwrap(Session.class);
    }

    protected Filter getFilter(String filterName) {
        return this.session.enableFilter(filterName);
    }

    protected void enableFilter(String filterName, String paramName, Boolean enable) {
        setSession();
        setFilterName(filterName);
        this.paramName = paramName;
        this.filter = getFilter(this.filterName);
        this.filter.setParameter(paramName, enable);
    }

    protected void disableFilter() {
        this.session.disableFilter(this.filterName);
    }
}











