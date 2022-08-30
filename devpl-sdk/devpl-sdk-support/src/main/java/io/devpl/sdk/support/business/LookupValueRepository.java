package io.devpl.sdk.support.business;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LookupValueRepository extends JpaRepository<LookupedValue, Integer> {

}
