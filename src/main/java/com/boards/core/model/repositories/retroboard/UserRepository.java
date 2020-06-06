package com.boards.core.model.repositories.retroboard;

import com.boards.core.model.entities.retroboard.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findByEmail(String teamMemberEmail);

    List<User> findAllByUidIn(List<String> uids);
}
