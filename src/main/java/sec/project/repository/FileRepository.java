package sec.project.repository;

import java.util.List;
import sec.project.domain.FileObject;
import org.springframework.data.jpa.repository.JpaRepository;
import sec.project.domain.Account;

public interface FileRepository extends JpaRepository<FileObject, Long> {

    List<FileObject> findByAccount(Account account);

}
