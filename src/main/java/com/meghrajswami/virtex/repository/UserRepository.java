package com.meghrajswami.virtex.repository;

import com.meghrajswami.virtex.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by megh on 5/7/2017.
 */
//@RepositoryRestResource(exported = false)
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findByUsername(@Param("username") String username);

    @Query("select u from User u where u.paidOrderId > 0 and u.name like %?1%")
    List<User> findByNameContainingAndPaidOrderIdGreaterThan(@Param("q") String q, Pageable pageable);

    User findFirstByFbIdOrderByIdAsc(@Param("fbId") Long fbId);

    User findFirstByGoogleIdOrderByIdAsc(@Param("googleId") String googleId);

}
