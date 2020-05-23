package com.boards.core.service;

import com.boards.core.model.entities.Note;
import com.boards.core.model.entities.RetroBoard;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;

public class BoardServiceImpl {
    private static final String resources = "src/main/resources";
    private final CacheManagerImpl cacheManagerImpl;

    {
        List<File> cacheFiles = List.of(
                Paths.get(resources.concat("/cache/boards")).toFile(),
                Paths.get(resources.concat("/cache/walls")).toFile(),
                Paths.get(resources.concat("/cache/notes")).toFile()
        );

        for (File cacheFile : cacheFiles) {
            if (!cacheFile.getParentFile().exists())
                cacheFile.getParentFile().mkdir();

            LocalDateTime lastModifiedDate = Instant.ofEpochMilli(cacheFile.lastModified()).atZone(ZoneId.systemDefault()).toLocalDateTime();
            if (Duration.between(LocalDateTime.now(), lastModifiedDate).toMinutes() > 60)
                cacheFile.delete();
        }

    }

    private final String api = "https://retro-board-ebce6.firebaseio.com";
    private final FirebaseDatabase defaultDb;

    public BoardServiceImpl(FirebaseApp firebaseApp, CacheManagerImpl cacheManagerImpl) {
        this.defaultDb = FirebaseDatabase.getInstance(firebaseApp);
        this.cacheManagerImpl = cacheManagerImpl;
    }

    private String getPath(String path) {
        return api.concat(path);
    }

    private DataSnapshot getData(String path) {
        ObjectHolder<DataSnapshot> holder = new ObjectHolder<>();

        defaultDb.getReference(path).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                holder.setData(snapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        while (Objects.isNull(holder.getData())) {
            try {
                System.out.println("returned obj: " + holder.getData());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return holder.getData();
    }

    public List<RetroBoard> getRetroBoards() throws IOException {
        File boardsFile = Paths.get(resources.concat("/cache/boards")).toFile();
        List<RetroBoard> result = cacheManagerImpl.readCache(boardsFile, RetroBoard.class).result();

        if (!result.isEmpty())
            return result;

        getData("/boards").getChildren().forEach(children -> {
            if (children.exists()) {
                children.getChildren().forEach(child -> result.add(child.getValue(RetroBoard.class)));
            }
        });

        cacheManagerImpl.writeCache(boardsFile, result);
        return result;
    }

    public List<Note> getRetroNotes() throws IOException {
        File notesCacheFile = Paths.get(resources.concat("/cache/notes")).toFile();
        List<Note> result = cacheManagerImpl
                .readCache(notesCacheFile, Note.class)
                .result();

        if (!result.isEmpty())
            return result;

        DataSnapshot notesSnapshot = getData("/notes");
        if (notesSnapshot.hasChildren())
            notesSnapshot.getChildren().forEach(child -> {
                if (child.hasChildren())
                    child.getChildren().forEach(grandChild -> result.add(grandChild.getValue(Note.class)));
            });

        cacheManagerImpl.writeCache(notesCacheFile, result);
        return result;
    }

    private static class ObjectHolder<T> {
        private T data;

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }
    }
}
