package com.boards.core.model.repositories.retroboard;

import com.boards.core.model.entities.retroboard.Vote;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VoteRepository extends CrudRepository<Vote, Vote.VoteId> {

    List<Vote> findAllByItemIdAndType(String noteId, String note);

    void deleteByItemIdAndType(String noteId, String voteType);
}
