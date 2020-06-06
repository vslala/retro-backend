package com.boards.core.controllers;

import com.boards.core.model.dto.shareditems.SharedItemRequest;
import com.boards.core.model.dto.shareditems.SharedItemResponse;
import com.boards.core.services.ShareService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static com.boards.core.configuration.AppConfig.EXPOSE_LOCATION;
import static com.boards.core.configuration.AppUtil.getLoggedInUser;

@CrossOrigin(exposedHeaders = EXPOSE_LOCATION)
@RestController
@RequestMapping("/share")
public class SharedItemController {

    private ShareService shareService;

    public SharedItemController(ShareService shareService) {
        this.shareService = shareService;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> shareWith(@RequestBody SharedItemRequest sharedItemRequest) {
        URI uri = shareService.shareItem(getLoggedInUser(), sharedItemRequest);
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{itemId}")
    public ResponseEntity getSharedItemsTeams(@PathVariable String itemId) {
        SharedItemResponse sharedItemsResponse = shareService.getSharedItems(itemId);
        return ResponseEntity.ok(sharedItemsResponse);
    }
}
