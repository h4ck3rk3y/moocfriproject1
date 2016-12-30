package sec.project.controller;

import sec.project.repository.PostRepository;
import sec.project.domain.PostObject;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Account;
import sec.project.repository.AccountRepository;

@Controller
public class PostController {

    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(value = "/{username}/posts", method = RequestMethod.GET)
    public String listMy(Authentication authentication, Model model, @PathVariable String username) {
        model.addAttribute("posts", accountRepository.findByUsername(username).getPostObjectList());
        return "posts";
    }

    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public String listAll(Authentication authentication, Model model) {
        model.addAttribute("posts", postRepository.findByIsPublic(true));
        model.addAttribute("user", authentication.getName());
        return "posts";
    }

    @RequestMapping(value = "/posts", method = RequestMethod.POST)
    public String addFile(Authentication authentication, @RequestParam("title") String title, @RequestParam("content") String content, @RequestParam("postedBy") String postedBy, @RequestParam(value="isPublic", required=true,defaultValue = "false") boolean isPublic) throws IOException {
        Account account = accountRepository.findByUsername(authentication.getName());

        String by = "";

        if(postedBy == null || postedBy == "")
        {
            by = authentication.getName();
        }

        PostObject postObject= new PostObject();
        postObject.setTitle(title);
        postObject.setContent(content);
        postObject.setPostedBy(by);
        postObject.setAccount(account);
        postObject.setIsPublic(isPublic);

        postRepository.save(postObject);


        return "redirect:/posts";
    }
}
