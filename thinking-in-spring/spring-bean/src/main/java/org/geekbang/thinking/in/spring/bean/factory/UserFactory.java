package org.geekbang.thinking.in.spring.bean.factory;

import org.geekbang.thinking.in.spring.ioc.overview.domain.User;

/**
 * {@link org.geekbang.thinking.in.spring.ioc.overview.domain.User} 工厂类
 */
public interface UserFactory {
default User createUser(){return User.createUser();}
}
