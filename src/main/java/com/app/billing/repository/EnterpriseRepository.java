package com.app.billing.repository;

import com.app.billing.models.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EnterpriseRepository extends JpaRepository<Enterprise, UUID>, PagingAndSortingRepository<Enterprise, UUID> {
}
