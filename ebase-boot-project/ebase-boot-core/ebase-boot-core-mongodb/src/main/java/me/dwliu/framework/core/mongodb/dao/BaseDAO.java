package me.dwliu.framework.core.mongodb.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 基础Dao
 *
 * @author eric
 * @since 1.0.0
 */
@NoRepositoryBean
public interface BaseDAO<T, ID> extends MongoRepository<T, ID> {

}
