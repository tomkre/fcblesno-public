package cz.tomek.fcblesno.controller.admin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import cz.tomek.fcblesno.model.AppEntity;
import cz.tomek.fcblesno.model.marking.ContainsPhoto;
import cz.tomek.fcblesno.service.AppEntityService;
import lombok.extern.slf4j.Slf4j;

/**
 * App entity controller.
 * 
 * @author tomek
 *
 */
@Slf4j
@SessionAttributes(AppEntityController.FORM_FEEDBACK)
public abstract class AppEntityController<T extends AppEntity> {
	
	protected static final String FORM_FEEDBACK = "formFeedback";
	
	protected static final String FORM_FEEDBACK_OK = "ok";
	
	protected static final String FORM_FEEDBACK_ERROR = "error";
	
	private static final String ADMIN_VIEW = "admin/list";
	
	private static final String NEW_ENTITY_ID = "new";
	
	@Autowired
	private AppEntityService<T> appEntityService;
	
	private Class<T> entityClass;
	
	public AppEntityController(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
	
	@GetMapping
	public String list(@PathVariable String contextUrl, @RequestParam Map<String, String> requestParams, Model model) {
		requestParams.putIfAbsent("page", "1");
		model.addAttribute("currentPage", Integer.valueOf(requestParams.get("page")));
		model.addAttribute("contextUrl", contextUrl);
		addListModelAttributes(model, requestParams);
		return ADMIN_VIEW;
	}

	/**
	 * Adds entity specific list model attributes.
	 * 
	 * @param model
	 * @param requestParams
	 */
	protected void addListModelAttributes(Model model, Map<String, String> requestParams) {
		int page = Integer.valueOf(requestParams.get("page"));
		model.addAttribute("entities", appEntityService.getByPage(page - 1));
		model.addAttribute("numOfPages", appEntityService.getNumberOfPages());
		model.addAttribute("specialParams", "");
	}
	
	@GetMapping("{id}")
	public String form(@PathVariable String contextUrl, @PathVariable String id, Model model) {
		String entityName = contextUrl.replaceAll("s$", "");
		model.addAttribute("entity", getEntityById(id));
		model.addAttribute("contextUrl", contextUrl);
		model.addAttribute("isDeletionPermitted", isNotNewEntity(id) && appEntityService.isDeletionPermitted(id));
		addFormModelAttributes(model);
		return "admin/form/" + entityName;
	}
	
	private T getEntityById(String id) {
		T entity = null;
		try {
			entity = isNotNewEntity(id) ? appEntityService.getOne(id) : entityClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			String msg = String.format("Error during %s entity instantiation via reflection!", entityClass.getSimpleName());
			throw new RuntimeException(msg, e);
		}
		return entity;
	}
	
	private boolean isNotNewEntity(String entityId) {
		return !NEW_ENTITY_ID.equals(entityId);
	}
	
	@PostMapping("{id}")
	public String save(@PathVariable String contextUrl, @PathVariable String id,
				@Valid @ModelAttribute("entity") T entity, BindingResult result, 
				@RequestParam(name = "photo", required = false) MultipartFile file, Model model) {
		if (result.hasErrors()) {
			log.debug("Entity {} contains erros!", entity);
			String entityName = contextUrl.replaceFirst("s$", "");
			model.addAttribute("isDeletionPermitted", isNotNewEntity(id) && appEntityService.isDeletionPermitted(id));
			addFormModelAttributes(model);
			model.addAttribute(FORM_FEEDBACK, FORM_FEEDBACK_ERROR);
			return "admin/form/" + entityName;
		}
		preSave(entity);
		entity = appEntityService.save(entity);
		log.debug("Entity {} has been saved.", entity);
		if (file != null && !file.isEmpty()) {
			boolean success = processPhoto(file, contextUrl, entity.getId());
			if (success) {
				model.addAttribute(FORM_FEEDBACK, FORM_FEEDBACK_OK);
			} else {
				model.addAttribute(FORM_FEEDBACK, FORM_FEEDBACK_ERROR);
			}
		} else {
			model.addAttribute(FORM_FEEDBACK, FORM_FEEDBACK_OK);
		}
		return "redirect:/admin/" + contextUrl;
	}

	/**
	 * Updates entity data before persisting to DB.
	 * 
	 * @param entity
	 */
	protected void preSave(T entity) {
	}

	/**
	 * Stores image into file system. Can be overriden in subclass in order to override
	 * context url or file name
	 * 
	 * @param file
	 * @param contextUrl
	 * @param entityId
	 * @return true if image processing was successful, false otherwise
	 */
	protected boolean processPhoto(MultipartFile file, String contextUrl, String entityId) {
		Path path = getEntityPhoto(contextUrl, entityId);
		try {
			Files.write(path, file.getBytes());
			log.debug("Photo {} has been successfully saved into file system.", path.getFileName());
			return true;
		} catch (IOException e) {
			log.error("Error has occured during saving photo {} into file system: {}", path.getFileName(), e);
			return false;
		}
	}

	@GetMapping("{id}/delete")
	public String delete(@PathVariable String contextUrl, @PathVariable String id, Model model) {
		appEntityService.delete(id);
		log.debug("Entity [id={}] has been deleted.", id);
		deletePhotoIfExists(contextUrl, id);
		return "redirect:/admin/" + contextUrl;
	}
	
	@ModelAttribute("formFeedback")
	private String formFeedBack() {
		return null;
	}

	private void deletePhotoIfExists(String contextUrl, String entityId) {
		boolean containsPhoto = Arrays.stream(entityClass.getInterfaces()).anyMatch(i -> i == ContainsPhoto.class);
		if (containsPhoto) {
			Path path = getEntityPhoto(contextUrl, entityId);
			try {
				Files.deleteIfExists(path);
				log.debug("Photo {} has been successfully deleted from file system.", path.getFileName());
			} catch (IOException e) {
				log.error("Error has occured during deleting photo {} from file system: {}", path.getFileName(), e);
			}
		}
	}
	
	private Path getEntityPhoto(String contextUrl, String entityId) {
		return Paths.get("src/main/resources/static/images", contextUrl, entityId + ".jpg");
//		return Paths.get("webapps/ROOT/WEB-INF/classes/static/images", contextUrl, entityId + ".jpg");
	}

	/**
	 * Adds entity specific form model attributes.
	 * 
	 * @param model
	 */
	protected void addFormModelAttributes(Model model) {
	}
	
}
