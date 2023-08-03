package com.meghrajswami.virtex.repository;

import com.meghrajswami.virtex.domain.NetPosition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Meghraj
 */
public interface NetPositionRepository extends PagingAndSortingRepository<NetPosition, Long> {

    Page<NetPosition> findByUserId(Long userId, Pageable pageable);

}
