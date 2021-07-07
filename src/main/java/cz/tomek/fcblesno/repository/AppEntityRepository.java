package cz.tomek.fcblesno.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import cz.tomek.fcblesno.model.AppEntity;

/**
 * App Entity repository.
 * 
 * @author tomek
 *
 */
@NoRepositoryBean
public interface AppEntityRepository<T extends AppEntity> extends JpaRepository<T, String> {

}
