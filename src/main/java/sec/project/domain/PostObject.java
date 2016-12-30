package sec.project.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class PostObject extends AbstractPersistable<Long> {

    private String title;
    private String content;
    private String postedBy;
    private boolean isPublic;

    @ManyToOne
    private Account account;


    public String getTitle() {
        return title;
    }

    public void getTitle(String title) {
        this.title= title;
    }

    public String getContent() {return content;}

    public void setContent(String content) {this.content = content;}

    public String getPostedBy() {return postedBy;}

    public void setPostedBy(String postedBy) {this.postedBy = postedBy;}

    public boolean getIsPublic() {return isPublic;}

    public setIsPublic(boolean isPublic) { this.isPublic = isPublic;}

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
