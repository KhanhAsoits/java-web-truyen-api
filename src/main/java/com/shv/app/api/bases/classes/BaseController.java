package com.shv.app.api.bases.classes;

import org.springframework.http.ResponseEntity;

public class BaseController {
    protected ResponseEntity<?> InternalServerResponse(Exception exception) {
        return ResponseEntity.internalServerError().body(exception.getMessage());
    }
}
