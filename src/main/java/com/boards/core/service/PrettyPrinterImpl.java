package com.boards.core.service;

import com.boards.core.model.Note;
import com.boards.core.model.RetroBoard;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j
@Component
public class PrettyPrinterImpl {
    public void printRetroBoards(List<RetroBoard> retroBoards) {
        StringBuilder sb = new StringBuilder();
        retroBoards.forEach(retroBoard -> {
            sb.append("Title\t:").append(retroBoard.getName()).append(System.lineSeparator())
                    .append("----------------------------------------------").append(System.lineSeparator())
                    .append("Max Likes\t:").append(retroBoard.getMaxLikes()).append(System.lineSeparator())
                    .append("Blur\t\t:").append(retroBoard.getBlur()).append(System.lineSeparator())
                    .append("Board ID\t:").append(retroBoard.getId()).append(System.lineSeparator())
                    .append(System.lineSeparator())
                    .append(System.lineSeparator());
        });
        log.debug(sb.toString());
    }

    public void printRetroNotes(List<Note> retroNotes) {
        StringBuilder sb = new StringBuilder();
        retroNotes.forEach(note -> {
            sb.append("Board ID\t:").append(note.getRetroBoardId()).append(System.lineSeparator())
                    .append("Wall ID\t\t:").append(note.getWallId()).append(System.lineSeparator())
                    .append("Note ID\t\t:").append(note.getNoteId()).append(System.lineSeparator())
                    .append("Note\t\t:").append(note.getNoteText()).append(System.lineSeparator())
                    .append("Created By\t:").append(note.getCreatedBy()).append(System.lineSeparator())
                    .append(System.lineSeparator())
                    .append(System.lineSeparator())
                    .append("----------------------------------------------")
                    .append(System.lineSeparator());
        });
        log.debug(sb.toString());
    }
}
