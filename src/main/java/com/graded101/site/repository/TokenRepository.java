package com.graded101.site.repository;

import com.graded101.site.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {

    Optional<Token> findByToken(String token);

    @Query("""
select t from Token t inner join User u on t.user.id = u.id
where u.id = :userId and (t.expired = false and t.revoked = false)
            """)
    List<Token> findAllValidTokensByUser(Integer userId);
}
