package AdamFaouzi.demo.Services;

import AdamFaouzi.demo.DTO.AppPostDTO;
import AdamFaouzi.demo.Entities.AppPost;
import AdamFaouzi.demo.Entities.AppUser;
import AdamFaouzi.demo.MapperDTO.AppPostDTOMapper;
import AdamFaouzi.demo.Repositories.AppPostRepository;
import AdamFaouzi.demo.Repositories.AppUserRepository;
import AdamFaouzi.demo.Security.JwtService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Data
@AllArgsConstructor
public class AppPostService {

    public AppPostRepository appPostRepository;
    public AppPostDTOMapper appPostDTOMapper;
    public AppUserRepository appUserRepository;
    public JwtService jwtService;

    public void createPost(AppPost appPost) {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        appPost.setAppUser(appUser);
        this.appPostRepository.save(appPost);
    }

    ;

    public Stream<AppPostDTO> getAllPosts() {
        return this.appPostRepository.findAllByOrderByCreationDateDesc()
                .stream()
                .map(appPostDTOMapper);
    }

    public AppPost searchPostById(int id) {
        Optional<AppPost> optionalPost = this.appPostRepository.findById(id);
        if (optionalPost.isPresent()) {
            return optionalPost.get();
        } else {
            throw new IllegalStateException("No post has been found");
        }
    }

    public List<AppPostDTO> getAppPostsByCreationDate(Date startDate, Date endDate) {
        List<AppPost> appPosts = appPostRepository.findByCreationDateBetween(startDate, endDate);
        return appPosts.stream()
                .map(appPostDTOMapper)
                .collect(Collectors.toList());
    }

    public void deletePost(int id) {
        boolean postExist = appPostRepository.existsById(id);

        if (!postExist) {
            throw new IllegalStateException("This post does not exist");
        }

        AppPost currentPost = appPostRepository.findById(id).orElseThrow(() -> new IllegalStateException("Post not found"));
        AppUser loggedInUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean isAdmin = loggedInUser.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

        if (Objects.equals(currentPost.getAppUser().getId(), loggedInUser.getId()) || isAdmin) {
            // L'utilisateur est l'auteur du post ou un administrateur, autorisé à supprimer le post.
            this.appPostRepository.deleteById(id);
        } else {
            throw new AccessDeniedException("Accès refusé pour la suppression du post");
        }
    }

    public void updatePost(int id, AppPost appPost) {
        Optional<AppPost> optionalPost = this.appPostRepository.findById(id);

        if (optionalPost.isPresent()) {

            AppPost currentPost = optionalPost.get();
            AppUser loggedInUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            boolean isAdmin = loggedInUser.getAuthorities().stream()
                    .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

            if (Objects.equals(currentPost.getAppUser().getId(), loggedInUser.getId())) {
                currentPost.setCategory(appPost.getCategory());
                currentPost.setTitle(appPost.getTitle());
                currentPost.setPost(appPost.getPost());
                this.appPostRepository.save(currentPost);

            } else if (isAdmin) {
                currentPost.setCategory(appPost.getCategory());
                this.appPostRepository.save(currentPost);

            } else {
                throw new AccessDeniedException("Access denied");
            }

        } else {
            throw new IllegalStateException("No post has been found");
        }
    }


}



