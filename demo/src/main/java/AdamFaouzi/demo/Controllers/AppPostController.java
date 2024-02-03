package AdamFaouzi.demo.Controllers;


import AdamFaouzi.demo.DTO.AppPostDTO;
import AdamFaouzi.demo.Entities.AppPost;
import AdamFaouzi.demo.Services.AppPostService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(path = "posts")
public class AppPostController {

    public AppPostService appPostService;

    @PostMapping(path = "createPost", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createPost(@RequestBody AppPost appPost){
        this.appPostService.createPost(appPost);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Stream<AppPostDTO>> getAllPosts(){
        return ResponseEntity.ok(this.appPostService.getAllPosts());
    }

    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AppPost searchPostById(@PathVariable int id){
        return this.appPostService.searchPostById(id);
    }

    @GetMapping(path = "byDate", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AppPostDTO> getAppPostsByCreationDate(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return appPostService.getAppPostsByCreationDate(startDate, endDate);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> deletePost(@PathVariable int id){
        this.appPostService.deletePost(id);
        return new ResponseEntity<>("Post deleted", HttpStatus.OK);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<String> updatePost(@PathVariable int id, @RequestBody AppPost appPost){
        this.appPostService.updatePost(id, appPost);
        return new ResponseEntity<>("Post Updated", HttpStatus.OK);
    }
}
