package com.github.hatimiti.spring.actuator;

import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@RestControllerEndpoint(id = "example")
public class SampleRestControllerEndpoint {

	@GetMapping("/echo/{text}")
	public ResponseEntity<String> echo(@PathVariable("text") String text) {
		return ResponseEntity.ok().header("echo", text).body(text);
	}

}