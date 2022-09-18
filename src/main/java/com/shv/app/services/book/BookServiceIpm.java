package com.shv.app.services.book;

import com.shv.app.dto.BookDto;
import com.shv.app.dto.ImageDto;
import com.shv.app.entities.Author;
import com.shv.app.entities.Book;
import com.shv.app.entities.Category;
import com.shv.app.repositories.book.BookRepository;
import com.shv.app.services.author.AuthorServiceIpm;
import com.shv.app.services.base.BaseServiceIpm;
import com.shv.app.services.category.CategoryServiceIpm;
import com.shv.app.services.image.ImageService;
import com.shv.app.services.image.ImageServiceIpm;
import com.shv.app.utils.Json.PageBuilder;
import com.shv.app.utils.SlugGenerator.SlugGenerator;
import com.shv.app.utils.Uploader.Uploader;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BookServiceIpm extends BaseServiceIpm implements BookService {

    private final BookRepository bookRepository;
    private final Uploader uploader = new Uploader();

    private final CategoryServiceIpm categoryServiceIpm;
    private final AuthorServiceIpm authorServiceIpm;

    private final ImageServiceIpm imageServiceIpm;

    private final ModelMapper modelMapper = new ModelMapper();


    public Book findByName(String name){
        return bookRepository.findByTitle(name);
    }

    @Override
    public BookDto save(BookDto entity) throws ResponseStatusException{
        try{
            Optional<Book> exitBook = bookRepository.findBookByTitle(entity.getTitle());
            if (exitBook.isEmpty()) {
                Book book = modelMapper.map(entity, Book.class);
                book.setId(UUID.randomUUID().toString());
                book.setSlug(SlugGenerator.toSlug(book.getTitle()));

//            add category to book

                Category category = categoryServiceIpm.findByName(entity.getCategory_name());
                Author author = authorServiceIpm.findByName(entity.getAuthor_name());
                if (category != null) {
                    book.setCategory(category);
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"No category found!");
                }
                if (author != null) {
                    book.setAuthor(author);
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"No author found!");
                }

                try {
                    if (entity.getFile() != null) {
                        String filePath = uploader.Upload(entity.getFile());
                        ImageDto imageDto = new ImageDto();
                        imageDto.setFile(entity.getFile());
                        imageDto.setRelation_id(book.getId());
                        imageServiceIpm.save(imageDto);
                        log.info("Save book image success!");
                    }
                } catch (Exception ex) {
                    log.info("Can't save uploaded image!");
                }
                return this.modelMapper.map(bookRepository.save(book),BookDto.class);
            }
            return null;
        }catch (Exception exception){
            log.info(exception.getMessage());
            return null;
        }
    }

    @Override
    public BookDto update(BookDto entity) {
        return null;
    }


    public void removeById(String id) {
        Optional<Book> removeBook = bookRepository.findById(id);
        if (removeBook.isPresent()) {
            bookRepository.deleteById(id);
            log.info("remove success!");
            return;
        }
        log.info("remove failed , Book not found! ");
    }

    @Override
    public BookDto remove(BookDto entity) {
        Optional<Book> removeBook = bookRepository.findById(entity.getId());
        if (removeBook.isPresent()) {
            bookRepository.deleteById(removeBook.get().getId());
            log.info("remove success!");
            ModelMapper mapper = new ModelMapper();
            return mapper.map(removeBook, BookDto.class);
        }
        log.info("remove failed , Book not found! ");
        return null;
    }

    @Override
    public List<BookDto> findAll() {
        log.info("get all book!");
        this.enableFilter("deletedBookFilter", "isDeleted", true);
        List<Book> books = bookRepository.findAll();
        this.disableFilter();
//        cast to dto
        ModelMapper mapper = new ModelMapper();
        return books.stream().map(book -> mapper.map(book, BookDto.class)).toList();
    }

    @Override
    public Page<BookDto> findByPage(int page, int limit) {
        Pageable pageable = PageBuilder.createReq(page, limit);
        this.enableFilter("deletedBookFilter", "isDeleted", true);
        Page<Book> books = bookRepository.findAll(pageable);
        this.disableFilter();
        //cast
        return new PageImpl<>(books.stream().map(book -> this.modelMapper.map(book, BookDto.class)).toList());
    }

    @Override
    public List<BookDto> findByKeyWord(String keyword) {
        return null;
    }

    @Override
    public Page<BookDto> search(String query, int page, int limit) {
        Pageable pageable = PageBuilder.createReq(page, limit);
        this.enableFilter("deletedBookFilter", "isDeleted", true);
        Page<Book> books = bookRepository.findByKeyWord(query, pageable);
        this.disableFilter();
        //cast
        return new PageImpl<>(books.stream().map(book -> this.modelMapper.map(book, BookDto.class)).toList());
    }
}
