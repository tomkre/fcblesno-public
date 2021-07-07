package cz.tomek.fcblesno.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.Transactional;

import cz.tomek.fcblesno.model.AppEntity;
import cz.tomek.fcblesno.repository.AppEntityRepository;
import cz.tomek.fcblesno.service.AppEntityService;

/**
 * Default implementation of {@link AppEntityService}.
 * 
 * @author tomek
 *
 */
public abstract class JpaAppEntityService<T extends AppEntity> implements AppEntityService<T> {
	
	protected static final int ITEMS_PER_PAGE = 14;

	protected static final Pageable FIRST_RECORD = PageRequest.of(0, 1);
	
	@Autowired
	private AppEntityRepository<T> repository;
	
	@Override
	@Transactional(readOnly = true)
	public T getOne(String id) {
		return repository.getOne(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<T> getAll() {
		return repository.findAll(getSort());
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<T> getByPage(int page) {
		Pageable pageRequest = PageRequest.of(page, ITEMS_PER_PAGE, getSort());
		return repository.findAll(pageRequest).getContent();
	}
	
	@Override
	@Transactional(readOnly = true)
	public int getNumberOfPages() {
		long count = repository.count();
		int result = (int) count / ITEMS_PER_PAGE;
		if (count % ITEMS_PER_PAGE > 0) {
			result++;
		}
		return result;
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean exists(String id) {
		return repository.existsById(id);
	}
	
	@Override
	@Transactional
	public T save(T entity) {
		return repository.save(entity);
	}
	
	@Override
	@Transactional
	public void delete(String id) {
		repository.deleteById(id);
	}
	
	@Override
	public boolean isDeletionPermitted(String id) {
		return false;
	}

	/**
	 * Gets sort
	 * 
	 * @return sort of concrete entity type
	 */
	protected Sort getSort() {
		return Sort.by(Direction.ASC, "id");
	}

}
