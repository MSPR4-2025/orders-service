package io.github.MSPR4_2025.orders_service.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Hi", description = "Example controller that says hi")
@RestController
@RequestMapping("/hi")
public class HiController {

    @Operation(
            summary = "Say hi",
            tags = { "hi" })
    @ApiResponse(responseCode = "200", content = { @Content(mediaType = "text/plain") })
    @GetMapping
    public ResponseEntity<String> sayHi() {
        return ResponseEntity.ok("Hi :)");
    }
}
