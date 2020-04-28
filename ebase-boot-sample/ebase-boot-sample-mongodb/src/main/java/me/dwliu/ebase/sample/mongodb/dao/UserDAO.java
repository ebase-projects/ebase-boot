package me.dwliu.ebase.sample.mongodb.dao;

import me.dwliu.ebase.sample.mongodb.entity.UserDO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Mapper 接口
 *
 * @author Chill
 */
@Repository
public interface UserDAO extends MongoRepository<UserDO, String> {


}
