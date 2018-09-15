package com.github.hatimiti.spring.rest;

import com.github.hatimiti.spring.common.db.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

/**
 * Spring MVC supports for RESTful applications (GET/PUT/POST/DELETE)
 */
@RestController
public class SampleRestController {

    private static final Logger LOG = LoggerFactory.getLogger(SampleRestController.class);

    public static final String URI = "/sample/rest";

    private final UserRepository userRepository;

    @Autowired
    public SampleRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * curl -X GET http://localhost:8080/sample/rest/123
     */
    //@RequestMapping(path = "/sample/rest/{id}", method = RequestMethod.GET)
    //@ResponseBody
    @GetMapping(value = URI + "/{id}", produces = "application/json")
    public User doGet(@PathVariable long id, @RequestHeader("User-Agent") String userAgent) {
        LOG.info("User-Agent: {}", userAgent);
        return userRepository.findById(id).orElse(new User());
    }

    /**
     * curl -X GET http://localhost:8080/sample/rest/
     */
    @GetMapping(value = URI, produces = "application/json")
    public List<User> doGet(@RequestHeader("User-Agent") String userAgent) {
        LOG.info("User-Agent: {}", userAgent);
        return userRepository.findAll();
    }

    /**
     * Creates a new resource.
     * curl -X POST http://localhost:8080/sample/rest -H "Content-Type: application/json" -d '{ "userId": 1, "name": "sample", "password": "hogefuga" }'
     *
     * POST is required to return the location of the newly created resource.
     */
    @PostMapping(value = URI, consumes = "application/json")
    public ResponseEntity<Void> doPost(@RequestBody User user) {
        userRepository.insert(user);
        return ResponseEntity.created(ServletUriComponentsBuilder
            .fromCurrentRequestUri()
            .path("/{id}")
            .buildAndExpand(user.userId)
            .toUri()).build();
    }

    /**
     * curl -X POST http://localhost:8080/sample/rest -H "Content-Type: application/json" -d '{ "userId": 1, "name": "sample", "password": "hogefuga" }'
     */
    @PutMapping(value = URI, consumes = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void doPut(@RequestBody User user) {
        userRepository.update(user);
    }

    /**
     * curl -X DELETE http://localhost:8080/sample/rest -H "Content-Type: application/json" -d '{ "userId": 1, "name": "sample", "password": "hogefuga" }'
     */
    @DeleteMapping(URI + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void doDelete(@PathVariable long id) {
        userRepository.delete(id);
    }

    @DeleteMapping(URI)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void doDeleteAll() {
        userRepository.deleteAll();
    }
}
