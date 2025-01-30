package org.example.Project2Boot.controllers;


import jakarta.validation.Valid;
import org.example.Project2Boot.models.Book;
import org.example.Project2Boot.models.Person;
import org.example.Project2Boot.services.BooksService;
import org.example.Project2Boot.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/books")
public class BooksController {
    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }


    @GetMapping
    public String index(Model model,
                        @RequestParam(value = "sort_by_year", required = false) boolean sort_by_year,
                        @RequestParam(value="page", required = false) Integer page,
                        @RequestParam(value = "books_per_page", required = false) Integer books_per_page) {
        if (page == null || books_per_page == null) {
            model.addAttribute("books", booksService.findAll(sort_by_year));
        } else {
            model.addAttribute("books", booksService.findAllWithPaginationAndSort(page, books_per_page, sort_by_year));
        }
        return "books/index";
    }

    @GetMapping("/search")
    public String searchPage() {
        return "books/search";
    }
    @PatchMapping("/search")
    public String search(@RequestParam String search, Model model) {
        model.addAttribute("books", booksService.findByTitleStartingWith(search));
        return "books/search";
    }

    @GetMapping("/{bookID}")
    public String show(@PathVariable("bookID") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", booksService.findById(id));

        Person owner = peopleService.findByBookId(id);
        if (owner != null) {
            model.addAttribute("owner", owner);
        } else {
            model.addAttribute("people", peopleService.findAll());
        }
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
        booksService.save(book);
        return "redirect:/books";
    }
    @GetMapping("/{bookID}/edit")
    public String edit(Model model, @PathVariable("bookID") int id) {
        model.addAttribute("book", booksService.findById(id));
        return "books/edit";
    }

    @PatchMapping("/{bookID}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult, @PathVariable("bookID") int id) {
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }
        booksService.update(id, book);
        return "redirect:/books";
    }

    @PatchMapping("/{bookID}/release")
    public String releaseBook(@PathVariable("bookID") int id) {
        booksService.releaseBook(id);
        return "redirect:/books/"+id;
    }

    @PatchMapping("/{bookID}/assign")
    public String assignBook(@PathVariable("bookID") int id, @ModelAttribute("person") Person selectedPerson) {
        booksService.assignBookToPerson(id, selectedPerson);
        return "redirect:/books/"+id;
    }

    @DeleteMapping("/{bookID}")
    public String delete(@PathVariable("bookID") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }



}
