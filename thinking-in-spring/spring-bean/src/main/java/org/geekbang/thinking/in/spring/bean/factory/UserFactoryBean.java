package org.geekbang.thinking.in.spring.bean.factory;

import org.geekbang.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.FactoryBean;

public class UserFactoryBean implements FactoryBean {

    public static void main(String[] args) {

    }
    @Override
    public Object getObject() throws Exception {
        return User.createUser();
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }
}
