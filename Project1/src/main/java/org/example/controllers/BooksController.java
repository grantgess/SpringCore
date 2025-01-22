package org.example.controllers;

import org.example.dao.BookDAO;
import org.example.dao.PersonDAO;
import org.example.models.Book;
import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookDAO bookDAO;
    private final JdbcTemplate jdbcTemplate;
    private final PersonDAO personDAO;

    @Autowired
    public BooksController(BookDAO bookDAO, JdbcTemplate jdbcTemplate, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.jdbcTemplate = jdbcTemplate;
        this.personDAO = personDAO;
    }


    @GetMapping
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }
    @GetMapping("/{bookID}")
    public String show(@PathVariable("bookID") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookDAO.show(id));

        Optional<Person> personHasBook = bookDAO.getPersonByBookID(id);
        if (personHasBook.isPresent())
            model.addAttribute("owner", personHasBook.get());
        else
            model.addAttribute("people", personDAO.index());
        return "books/show";
    }
    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/new";
        }
        bookDAO.save(book);
        return "redirect:/books";
    }
    @GetMapping("/{bookID}/edit")
    public String edit(Model model, @PathVariable("bookID") int id) {
        model.addAttribute("book", bookDAO.show(id));
        return "books/edit";
    }

    @PatchMapping("/{bookID}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult, @PathVariable("bookID") int id) {
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }
        bookDAO.update(id,book);
        return "redirect:/books";
    }

    @PatchMapping("/{bookID}/release")
    public String releaseBook(@PathVariable("bookID") int id) {
        bookDAO.releaseBook(id);
        return "redirect:/books/"+id;
    }

    @PatchMapping("/{bookID}/assign")
    public String assignBook(@PathVariable("bookID") int id, @ModelAttribute("person") Person selectedPerson) {
        bookDAO.assignBook(id, selectedPerson);
        return "redirect:/books/"+id;
    }

    @DeleteMapping("/{bookID}")
    public String delete(@PathVariable("bookID") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }



}
