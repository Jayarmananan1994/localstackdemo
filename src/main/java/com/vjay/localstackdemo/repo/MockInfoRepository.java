package com.vjay.localstackdemo.repo;

import com.vjay.localstackdemo.model.MockInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MockInfoRepository extends JpaRepository<MockInfo, Long> {
}
