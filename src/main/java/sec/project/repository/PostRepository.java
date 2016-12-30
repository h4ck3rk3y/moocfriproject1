package sec.project.repository;

import java.util.List;
import sec.project.domain.PostObject;
import org.springframework.data.jpa.repository.JpaRepository;
import sec.project.domain.Account;

public interface PostRepository extends JpaRepository<PostObject, Long> {

    List<PostObject> findByAccount(Account account);
    List<PostObject> findByIsPublic(boolean isPublic);

}
