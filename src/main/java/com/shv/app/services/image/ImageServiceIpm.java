package com.shv.app.services.image;

import com.shv.app.dto.ImageDto;
import com.shv.app.entities.Image;
import com.shv.app.repositories.image.ImageRepository;
import com.shv.app.utils.Uploader.Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ImageServiceIpm implements ImageService{

    private final ImageRepository imageRepository;

    private final ModelMapper mapper = new ModelMapper();
    private final Uploader uploader = new Uploader();


    public List<Image> findByRelationId(String id){
        Optional<List<Image>> images = imageRepository.findByRelationId(id);
        if (images.isPresent()){
            return images.get();
        }
        return null;
    }

    @Override
    public ImageDto save(ImageDto entity) {
        try{
            Image image = this.mapper.map(entity,Image.class);
            // save in source
            String filePath = this.uploader.Upload(entity.getFile());
            image.setPath(filePath);
            log.info("filesave success! path : {}",filePath);
//            save in database
            image.setId(UUID.randomUUID().toString());
            return this.mapper.map(imageRepository.save(image),ImageDto.class);
        }catch (Exception ex){
            log.info("Error : {}",ex.getMessage());
            return null;
        }
    }

    @Override
    public ImageDto update(ImageDto entity) {
        return null;
    }

    @Override
    public void removeById(Long aLong) {

    }

    @Override
    public ImageDto remove(ImageDto entity) {
        return null;
    }

    @Override
    public List<ImageDto> findAll() {
        return null;
    }

    @Override
    public Page<ImageDto> findByPage(int page, int limit) {
        return null;
    }

    @Override
    public List<ImageDto> findByKeyWord(String keyword) {
        return null;
    }

    @Override
    public Page<ImageDto> search(String query, int page, int limit) {
        return null;
    }
}
