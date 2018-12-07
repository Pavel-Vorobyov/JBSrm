package com.pavel.jbsrm.client.repository;

import com.pavel.jbsrm.client.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {

}
