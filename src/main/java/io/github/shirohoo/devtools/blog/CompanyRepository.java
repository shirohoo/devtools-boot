package io.github.shirohoo.devtools.blog;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Query("select c.url from Company c")
    List<String> findAllUrl();
}
