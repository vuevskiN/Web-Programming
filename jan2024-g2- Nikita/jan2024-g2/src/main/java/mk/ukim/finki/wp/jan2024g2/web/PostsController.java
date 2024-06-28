package mk.ukim.finki.wp.jan2024g2.web;

import mk.ukim.finki.wp.jan2024g2.model.Post;
import mk.ukim.finki.wp.jan2024g2.model.PostType;
import mk.ukim.finki.wp.jan2024g2.service.PostService;
import mk.ukim.finki.wp.jan2024g2.service.TagService;
import net.sourceforge.htmlunit.xpath.operations.Mod;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class PostsController {
    private final PostService postService;
    private final TagService tagService;

    public PostsController(PostService postService, TagService tagService) {
        this.postService = postService;
        this.tagService = tagService;
    }

    /**
     * This method should use the "list.html" template to display all posts.
     * The method should be mapped on paths '/' and '/posts'.
     * The arguments that this method takes are optional and can be 'null'.
     * In the case when the arguments are not passed (both are 'null') all posts should be displayed.
     * If one, or both of the arguments are not 'null', the posts that are the result of the call
     * to the method 'filterPosts' from the PostsService should be displayed.
     *
     * @param tagId
     * @param postType
     * @return The view "list.html".
     */

    @GetMapping({"/","/posts"})
    public String listAll(@RequestParam(required = false) Long tagId, @RequestParam(required = false) PostType postType, Model model) {
        List<Post> posts;
        if(tagId == null && postType == null){
            posts = postService.listAll();
        }
        else{
            posts = postService.filterPosts(tagId,postType);
        }

        model.addAttribute("posts",posts);
        model.addAttribute("tags",this.tagService.listAll());
        model.addAttribute("types",PostType.values());
        return "list";
    }

    /**
     * This method should display the "form.html" template.
     * The method should be mapped on path '/posts/add'.
     *
     * @return The view "form.html".
     */
    @GetMapping({"/posts/add"})
    public String showAdd(Model model) {
        model.addAttribute("tags",this.tagService.listAll());
        model.addAttribute("types",PostType.values());
        return "form";
    }

    /**
     * This method should display the "form.html" template.
     * However, in this case, all 'input' elements should be filled with the appropriate value for the post that is updated.
     * The method should be mapped on path '/posts/edit/[id]'.
     *
     * @return The view "form.html".
     */
    @GetMapping({"/posts/edit/{id}"})
    public String showEdit(@PathVariable Long id, Model model) {
        model.addAttribute("post",this.postService.findById(id));
        model.addAttribute("tags",this.tagService.listAll());
        model.addAttribute("types",PostType.values());
        return "form";
    }

    /**
     * This method should create a new post given the arguments it takes.
     * The method should be mapped on path '/posts'.
     * After the post is created, all posts should be displayed.
     *
     * @return The view "list.html".
     */
    @PostMapping({"/posts"})
    public String create(
           @RequestParam String title,
           @RequestParam String content,
           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateCreated,
           @RequestParam PostType postType,
           @RequestParam List<Long> tags)
    {
        postService.create(title,content,dateCreated,postType,tags);
        return "redirect:/posts";
    }

    /**
     * This method should update a post given the arguments it takes.
     * The method should be mapped on path '/posts/[id]'.
     * After the post is updated, all posts should be displayed.
     *
     * @return The view "list.html".
     */
    @PostMapping({"/posts/{id}"})
    public String update(@PathVariable Long id,
                         @RequestParam String title,
                         @RequestParam String content,
                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateCreated,
                         @RequestParam PostType postType,
                         @RequestParam List<Long> tags
    ) {
        postService.update(id,title,content,dateCreated,postType,tags);
        return "redirect:/posts";
    }

    /**
     * This method should delete the application that has the appropriate identifier.
     * The method should be mapped on path '/posts/delete/[id]'.
     * After the post is deleted, all posts should be displayed.
     *
     * @return The view "list.html".
     */
    @PostMapping({"/posts/delete/{id}"})
    public String delete(@PathVariable Long id) {
        postService.delete(id);
        return "redirect:/posts";
    }

    /**
     * This method should increase the number of likes of the appropriate post by 1.
     * The method should be mapped on path '/posts/like/[id]'.
     * After the operation, all posts should be displayed.
     *
     * @return The view "list.html".
     */
    @PostMapping({"/posts/like/{id}"})
    public String like(@PathVariable Long id) {
        postService.like(id);
        return "redirect:/posts";
    }
}
