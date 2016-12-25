package sec.project.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import sec.project.domain.Blog;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    Blog findByName(String name);
}
