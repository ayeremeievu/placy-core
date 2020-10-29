package com.placy.placycore.core.repositories;

import com.placy.placycore.reviewscore.model.ReviewModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.QueryHint;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface PageableRepository<MODEL, PK> extends JpaRepository<MODEL, PK> {
    @QueryHints(value = { @QueryHint(name = org.hibernate.annotations.QueryHints.FLUSH_MODE, value = "COMMIT") })
    Optional<MODEL> findFirstByOrderByPkAsc();

    @QueryHints(value = { @QueryHint(name = org.hibernate.annotations.QueryHints.FLUSH_MODE, value = "COMMIT") })
    List<MODEL> findAllByPkGreaterThanAndCreatedAtGreaterThanOrderByPkAscCreatedAtAsc(
            PK pk, Date createdAt, Pageable pageable);
}
