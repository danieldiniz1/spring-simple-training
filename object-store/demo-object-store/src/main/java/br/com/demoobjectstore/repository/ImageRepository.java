package br.com.demoobjectstore.repository;

import br.com.demoobjectstore.model.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageModel, Long> {
}
