package com.shv.app.services.author;

import com.shv.app.dto.AuthorDto;
import com.shv.app.entities.Author;
import com.shv.app.entities.User;
import com.shv.app.repositories.author.AuthorRepository;
import com.shv.app.services.base.BaseServiceIpm;
import com.shv.app.services.base.ICrudService;
import com.shv.app.services.user.UserService;
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
public class AuthorServiceIpm extends BaseServiceIpm implements AuthorService{

    private final AuthorRepository authorRepository;
    private final UserService userService;
    private final ModelMapper modelMapper = new ModelMapper();
    @Override
    public AuthorDto save(AuthorDto entity) {
        log.info("save new author!");
        Author author = modelMapper.map(entity,Author.class);
        User userOfAuthor = userService.findByEmail(entity.getUser_email());
        if (userOfAuthor!=null){
            author.setUser(userOfAuthor);
            author.setId(UUID.randomUUID().toString());
        }else {
            return null;
        }
        return this.modelMapper.map(authorRepository.save(author),AuthorDto.class);
    }

    @Override
    public AuthorDto update(AuthorDto entity) {
        return null;
    }

    @Override
    public void removeById(String s) {
        try {
            Optional<Author> author = authorRepository.findById(s);

            if (author.isPresent()){
                log.info("remove author!");
                authorRepository.deleteById(s);
            }else {
                log.info("author not found!");
            }
        }catch (Exception ex){
            log.info(ex.getMessage());
        }
    }

    @Override
    public AuthorDto remove(AuthorDto entity) {
        return null;
    }

    @Override
    public List<AuthorDto> findAll() {
        this.enableFilter("deletedAuthorFilter","isDeleted",true);
        List<AuthorDto> authorDtos =  authorRepository.findAll().stream().map(author -> modelMapper.map(author,AuthorDto.class)).toList();
        this.disableFilter();
        return authorDtos;
    }

    @Override
    public Page<AuthorDto> findByPage(int page, int limit) {
        return null;
    }

    @Override
    public List<AuthorDto> findByKeyWord(String keyword) {
        return null;
    }

    @Override
    public Page<AuthorDto> search(String query, int page, int limit) {
        return null;
    }

    public Author findByName(String author_name) {
        return this.authorRepository.findByName(author_name);
    }
}
