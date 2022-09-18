package com.shv.app.utils.Json;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class PageBuilder {
    public static PageRequest createReq(int page, int limit) {
        return PageRequest.of(page - 1, limit);
    }

    public static PageRequest createReq(int page, int limit, String sortTar, String sortDir) {
        Sort.Direction direction = sortDir.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return PageRequest.of(page, limit, Sort.by(direction, sortTar));
    }
}
