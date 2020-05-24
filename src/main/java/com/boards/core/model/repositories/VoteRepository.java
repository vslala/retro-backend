package com.boards.core.model.repositories;

import com.boards.core.model.entities.Vote;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VoteRepository extends CrudRepository<Vote, Vote.VoteId> {

    List<Vote> findAllByItemIdAndType(String noteId, String note);

    void deleteByItemIdAndType(String noteId, String voteType);
}
