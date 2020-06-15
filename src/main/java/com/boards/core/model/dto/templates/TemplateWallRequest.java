package com.boards.core.model.dto.templates;

import com.boards.core.model.dto.retroboard.WallStyleRequest;
import lombok.Data;

import java.util.List;

@Data
public class TemplateWallRequest {

    private String wallTitle;
    private WallStyleRequest wallStyle;
    private Integer wallOrder;
    private List<TemplateNoteRequest> notes;
}
