package com.boards.core.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateResponse {
    private String resourceUrl;
}
