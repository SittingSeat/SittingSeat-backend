package com.sittingseat.sittingseat.repository;

import com.sittingseat.sittingseat.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
