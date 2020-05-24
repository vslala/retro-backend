package com.boards.core.model.repositories;

import com.boards.core.model.entities.Vote;
import org.springframework.data.repository.CrudRepository;

public interface VoteRepository extends CrudRepository<Vote, Vote.VoteId> {

}
