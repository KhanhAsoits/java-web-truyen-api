package com.shv.app.utils.Mapper;

import org.modelmapper.ModelMapper;

import javax.management.modelmbean.ModelMBean;

public class ObjectMapper<M, T> {

    ModelMapper mapper;

    public ObjectMapper() {
        this.mapper = new ModelMapper();
    }

    public T Map(M mapperObject, Class<T> mapperTarget) {
        return this.mapper.map(mapperObject, mapperTarget);
    }

}
