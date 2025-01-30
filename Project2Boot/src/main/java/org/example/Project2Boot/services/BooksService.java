package org.example.Project2Boot.services;

import org.example.Project2Boot.models.Book;
import org.example.Project2Boot.models.Person;
import org.example.Project2Boot.repositories.BooksRepository;
import org.example.Project2Boot.repositories.PeopleRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository, PeopleRepository peopleRepository) {
        this.booksRepository = booksRepository;
        this.peopleRepository = peopleRepository;
    }
    public List<Book> findAll(boolean sort) {
        return sort ? booksRepository.findAll(Sort.by("year")) : booksRepository.findAll();
    }
    public List<Book> findAllWithPaginationAndSort(int page, int booksPerPage, boolean sort) {
        if (sort) {
            return booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
        } else {
            return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
        }
    }
    public List<Book> findByTitleStartingWith(String title) {
        System.out.println(title);
        return booksRepository.findAllByTitleStartingWith(title);
    }

    public Book findById(int id) {
        return booksRepository.findById(id).orElse(null);
    }

    public void checkDelay(List <Book> books) {
        Date now = new Date();
        for (Book book : books) {
            int diffInDays = (int) (now.getTime() - book.getBookTakenTime().getTime()) / (1000 * 60 * 60 * 24);
            if (diffInDays > 10) {
                book.setDelay(true);
            } else book.setDelay(false);
        }
    }
    public List<Book> findByPersonId(int id) {
        Optional<Person> person = peopleRepository.findById(id);
        if (person.isPresent()) {
            Hibernate.initialize(person.get().getBooks());
            checkDelay(person.get().getBooks());
            return person.get().getBooks();
        } else {
            return Collections.emptyList();
        }
    }
    @Transactional
    public void releaseBook(int id) {
        Book book = findById(id);
        book.setBookTakenTime(null);
        book.setOwner(null);
    }
    @Transactional
    public void assignBookToPerson(int id, Person person) {
        Book book = findById(id);
        book.setBookTakenTime(new Date());
        book.setOwner(person);
    }
    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }
    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }
    @Transactional
    public void update(int id, Book book) {
        Book bookToBeUpdated = findById(id);

        book.setBookID(id);
        book.setOwner(bookToBeUpdated.getOwner());
        booksRepository.save(book) ;
    }
}
