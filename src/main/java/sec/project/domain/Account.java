package sec.project.domain;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Account extends AbstractPersistable<Long> {

    @Column(unique = true)
    private String username;
    private String password;

    @OneToMany(mappedBy = "account")
    private List<FileObject> fileObjects;

    public List<FileObject> getFileObjects() {
        return fileObjects;
    }

    public void setFileObjects(List<FileObject> fileObjects) {
        this.fileObjects = fileObjects;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
