package andersen.messagereadingservice.repository;

import andersen.messagereadingservice.model.AuthKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthKeyRepository extends CrudRepository <AuthKey, UUID>{
}
