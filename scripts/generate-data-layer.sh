generate_model() {
	local table_name=`echo $entity | sed -r 's/([^A-Z])([A-Z])/\1_\2/g' | tr '[:upper:]' '[:lower:]'`
	echo "package cz.tomek.fcblesno.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
* ${javadoc_name}.
*
* @author tomek
*
*/
@Getter
@Setter
@ToString
@Entity
@Table(name = \"$table_name\")
public class $entity extends AppEntity {

}" > model/$entity.java
}

generate_repository() {
	echo "package cz.tomek.fcblesno.repository;

import cz.tomek.fcblesno.model.$entity;

/**
* $javadoc_name repository.
*
* @author tomek
*
*/
public interface ${entity}Repository extends AppEntityRepository<$entity> {

}" > repository/${entity}Repository.java
}

generate_service() {
	echo "package cz.tomek.fcblesno.service;

import cz.tomek.fcblesno.model.$entity;

/**
* $javadoc_name service.
*
* @author tomek
*
*/
public interface ${entity}Service extends AppEntityService<$entity> {

}" > service/${entity}Service.java
}

generate_service_impl() {
	echo "package cz.tomek.fcblesno.service.impl;

import org.springframework.stereotype.Service;

import cz.tomek.fcblesno.model.$entity;
import cz.tomek.fcblesno.service.${entity}Service;

/**
* Default implementation of {@link ${entity}Service}.
*
* @author tomek
*
*/
@Service
public class Jpa${entity}Service extends JpaAppEntityService<$entity> implements ${entity}Service {

}" > service/impl/Jpa${entity}Service.java
}

main() {
	cd ../src/main/java/cz/tomek/fcblesno
	for class in $@; do
		entity=$class
		javadoc_name=`echo $entity | sed -r 's/([^A-Z])([A-Z])/\1 \2/g'`
		generate_model
		generate_repository
		generate_service 
		generate_service_impl
	done
}

main $@
