package com.mkh.equationapp.repository;

import com.mkh.equationapp.domain.Root;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RootRepository extends JpaRepository<Root, Long> {
}
