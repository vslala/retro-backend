package com.boards.core.service;

import com.boards.core.model.entities.Note;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j
@Component
public class CacheManagerImpl<T> {
    private ObjectMapper objectMapper;
    private List<Note> result;

    private CacheManagerImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<Note> result() {
        return result;
    }

    public CacheManagerImpl readCache(File file, Class type) throws IOException {
        log.debug("Reading cache file: " + file.getAbsolutePath());
        result = file.exists() ?
                objectMapper
                        .readValue(file, objectMapper.getTypeFactory()
                                .constructCollectionType(List.class, type))
                : new ArrayList<>();
        log.debug(result);
        return this;
    }

    public void writeCache(File file, T data) throws IOException {
        objectMapper.writeValue(file, data);
    }
}