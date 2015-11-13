package com.hibernateDemo.controller;

import com.hibernateDemo.entity.Books;
import com.hibernateDemo.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * User: pual
 * Date: 2015/10/26
 * Desc:
 */
@Controller
public class IndexController {

    @Resource
    BookService bookService;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index(Model model){
        return "home";
    }

    @RequestMapping(value = "/findbooks/{name}",method = RequestMethod.GET)
    public String findBooks(Model model,@PathVariable("name") String name){
        //使用Optional取值，可自动检测null,java8
        Optional<Books> item = bookService.findBookByName(name);
        model.addAttribute("item",item.get());
        return "findbooks";
    }

    @RequestMapping(value = "/findbooksByName",method = RequestMethod.GET)
    public String findBooks(Model model,String name, @PageableDefault(size = 10) Pageable pageable){
//        Optional<List<Books>> booksList=bookService.findBooks(name,price);
//        System.out.println(booksList.toString());
        Page<Books> page=bookService.find(name, pageable);
        bookService.findAll();
        model.addAttribute("books",page);
        return "pageBooksAndAddBooks";
    }

    @RequestMapping(value = "/addBooks",method = RequestMethod.POST)
    public String addBooks(Books books,BindingResult bindingResult,Model model){
        books.validateUser(bindingResult,bookService);//值校验
        if(bindingResult.hasErrors()){
            return "pageBooksAndAddBooks";
        }
        Books books1 = bookService.save(books);
        return "pageBooksAndAddBooks";
    }
}
