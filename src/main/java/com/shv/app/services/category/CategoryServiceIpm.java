package com.shv.app.services.category;

import com.shv.app.dto.CategoryDto;
import com.shv.app.dto.ImageDto;
import com.shv.app.entities.Category;
import com.shv.app.entities.Image;
import com.shv.app.repositories.category.CategoryRepository;
import com.shv.app.services.base.BaseServiceIpm;
import com.shv.app.services.image.ImageServiceIpm;
import com.shv.app.utils.Json.PageBuilder;
import com.shv.app.utils.SlugGenerator.SlugGenerator;
import com.shv.app.utils.Uploader.Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.boot.Banner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceIpm extends BaseServiceIpm implements CategoryService {

    @Bean
    public ModelMapper onCreateMapper() {
        return new ModelMapper();
    }

    private final CategoryRepository categoryRepository;

    private final ImageServiceIpm imageServiceIpm;

    private final Uploader uploader = new Uploader();

    private final ModelMapper modelMapper = this.onCreateMapper();

    @Override
    public CategoryDto save(CategoryDto entity) {
        try {
            Category category = this.modelMapper.map(entity, Category.class);
            category.setId(UUID.randomUUID().toString());
            category.setSlug(SlugGenerator.toSlug(entity.getTitle()));
            Category saveEntity = categoryRepository.save(category);
//        save image

            Set<String> thumbnail = new HashSet<>();
            entity.getFiles().forEach(file -> {
                ImageDto imageDto = new ImageDto();
                imageDto.setFile(file);
                imageDto.setRelation_id(String.valueOf(saveEntity.getId()));
                ImageDto imageDtoSaved = imageServiceIpm.save(imageDto);
                thumbnail.add(imageDtoSaved.getPath());
                log.info("save image !");
            });

//        convert to base 64 encode
            CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);

            thumbnail.forEach(thumb -> {
                String base64Encoder = this.uploader.CreateBase64FromImage(thumb);
                if (base64Encoder != null) {
                    categoryDto.getThumbnails().add(base64Encoder);
                }
            });
            return categoryDto;
        } catch (Exception e) {
            log.info(e.getMessage());
            return null;
        }
    }

    @Override
    public CategoryDto update(CategoryDto entity) {
        Optional<Category> update_category = categoryRepository.findById(entity.getId());
        if (update_category.isPresent()) {
            log.info("update category : {}", update_category.get().getTitle());
            update_category.get().setTitle(entity.getTitle());
            update_category.get().setSlug(SlugGenerator.toSlug(entity.getTitle()));
            return this.modelMapper.map(update_category, CategoryDto.class);
        }
        return null;
    }

    @Override
    public void removeById(String aLong) {
        log.info("Remove category!");
        Optional<Category> category = categoryRepository.findById(aLong);
        if (category.isPresent()) {
            categoryRepository.deleteById(aLong);
        }
    }

    @Override
    public CategoryDto remove(CategoryDto entity) {
        return null;
    }

    @Override
    public List<CategoryDto> findAll() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDto> categoryDtos  = new ArrayList<>();
        categories.forEach(category -> {
//            get image by category
            List<Image> imagesOfCategory = imageServiceIpm.findByRelationId(category.getId());

            CategoryDto categoryDto = modelMapper.map(category,CategoryDto.class);

            imagesOfCategory.forEach(image -> {

                String base64Img = uploader.CreateBase64FromImage(image.getPath());
                categoryDto.getThumbnails().add(base64Img);

            });
            categoryDtos.add(categoryDto);
        });
        return categoryDtos;
    }

    @Override
    public Page<CategoryDto> findByPage(int page, int limit) {
        this.enableFilter("deletedCategoryFilter", "isDeleted", true);
        Pageable pageable = PageBuilder.createReq(page, limit);
        Page<Category> categories = categoryRepository.findAll(pageable);
        this.disableFilter();
        return new PageImpl<>(categories.stream().map(category -> modelMapper.map(category, CategoryDto.class)).toList());
    }

    @Override
    public List<CategoryDto> findByKeyWord(String keyword) {
        return null;
    }

    @Override
    public Page<CategoryDto> search(String query, int page, int limit) {
        Pageable pageable = PageBuilder.createReq(page, limit);
        this.enableFilter("deletedCategoryFilter", "isDeleted", true);
        Page<Category> categories = categoryRepository.findAll(query, pageable);
        this.disableFilter();
        return new PageImpl<>(categories.stream().map(category -> modelMapper.map(category, CategoryDto.class)).toList());
    }

    public CategoryDto findById(String id) {
        Optional<Category> category = categoryRepository.findById(id);
        return modelMapper.map(category, CategoryDto.class);
    }

    public Category findByName(String category_name) {
        return this.categoryRepository.findByTitle(category_name);
    }
}
