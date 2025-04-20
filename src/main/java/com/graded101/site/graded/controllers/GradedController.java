package com.graded101.site.graded.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/graded")
@RequiredArgsConstructor
@Tag(name="Graded")
public class GradedController {
}
