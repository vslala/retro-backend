package com.boards.core.model.dto.templates;

import com.boards.core.model.dto.retroboard.WallStyleResponse;
import lombok.Data;

import java.util.List;

@Data
public class TemplateWallResponse {
    private String wallTitle;
    private WallStyleResponse wallStyle;
    private Integer wallOrder;
    private List<TemplateNoteRequest> notes;
}
