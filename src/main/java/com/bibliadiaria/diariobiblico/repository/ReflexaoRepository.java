package com.bibliadiaria.diariobiblico.repository;

import com.bibliadiaria.diariobiblico.model.Reflexao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReflexaoRepository extends JpaRepository<Reflexao, Long> {
}
