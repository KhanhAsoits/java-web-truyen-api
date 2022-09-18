package com.shv.app.services.chapter;

import com.shv.app.dto.ChapterDto;
import com.shv.app.entities.Book;
import com.shv.app.entities.Chapter;
import com.shv.app.repositories.book.BookRepository;
import com.shv.app.repositories.chapter.ChapterRepository;
import com.shv.app.services.book.BookServiceIpm;
import com.shv.app.utils.SlugGenerator.SlugGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ChapterServiceIpm implements ChapterService{
    private final ChapterRepository chapterRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private final BookServiceIpm bookServiceIpm;
    @Override
    public ChapterDto save(ChapterDto entity) {
        log.info("Save new chapter!");
        //get book
        Book book = bookServiceIpm.findByName(entity.getBook_name());

        if (!Objects.isNull(book)){
            Chapter chapter = modelMapper.map(entity,Chapter.class);
            chapter.setId(UUID.randomUUID().toString());
            chapter.setSlug(SlugGenerator.toSlug(chapter.getTitle()));
            chapter.setNumberOfChapter(findMaxChapter() + 1);
            chapter.setBook(book);
            return modelMapper.map(chapterRepository.save(chapter),ChapterDto.class);
        }else {
            return null;
        }
    }

    public int findMaxChapter(){
        Chapter chapter = chapterRepository.findMaxChapter();
        if (chapter!=null){
            return chapter.getNumberOfChapter();
        }
        return 0;
    }
    @Override
    public ChapterDto update(ChapterDto entity) {
        return null;
    }

    @Override
    public void removeById(String s) {

    }

    @Override
    public ChapterDto remove(ChapterDto entity) {
        return null;
    }

    @Override
    public List<ChapterDto> findAll() {
        return null;
    }

    @Override
    public Page<ChapterDto> findByPage(int page, int limit) {
        return null;
    }

    @Override
    public List<ChapterDto> findByKeyWord(String keyword) {
        return null;
    }

    @Override
    public Page<ChapterDto> search(String query, int page, int limit) {
        return null;
    }
}
