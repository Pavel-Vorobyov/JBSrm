package com.pavel.jbsrm.ttn.repository;

import com.pavel.jbsrm.ttn.Ttn;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TtnRepository extends CrudRepository<Ttn, Long> {

}
