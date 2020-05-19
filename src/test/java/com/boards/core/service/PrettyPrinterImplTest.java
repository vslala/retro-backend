package com.boards.core.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class PrettyPrinterImplTest {

    @Autowired
    BoardServiceImpl boardService;

    PrettyPrinterImpl prettyPrinter = new PrettyPrinterImpl();

    @BeforeEach
    void setUp() {
    }

    @Test
    void it_should_print_all_retro_board() throws IOException {
        prettyPrinter.printRetroBoards(boardService.getRetroBoards());
    }

    @Test
    void it_should_print_all_retro_notes() throws IOException {
        prettyPrinter.printRetroNotes(boardService.getRetroNotes());
    }
}