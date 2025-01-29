package com.crio.rentvideo.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/videos")
public class VideoController {

   
    // Get all available videos
    @GetMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<String> getAvailableVideos() {
       
        return new ResponseEntity<>("Available videos", HttpStatus.OK);
    }

    // Get details of a specific video by ID
    @Secured("ROLE_ADMIN")
    @GetMapping("/{id}")
    public ResponseEntity<String> getVideoById(@PathVariable("id") Long id) {
       
        return new ResponseEntity<>("Video"+id, HttpStatus.OK);
    }


}
