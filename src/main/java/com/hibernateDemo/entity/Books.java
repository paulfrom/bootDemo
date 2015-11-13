package com.hibernateDemo.entity;

import com.hibernateDemo.service.BookService;
import org.springframework.validation.BindingResult;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static org.springframework.validation.ValidationUtils.rejectIfEmptyOrWhitespace;

/**
 * Created with IntelliJ IDEA.
 * User: pual
 * Date: 2015/10/26
 * Desc:
 */
@Entity
@Table(name="book")
public class Books  implements Serializable {

    protected Books(){}

    public Books(String name,double price){
        super();
        this.name=name;
        this.price=price;
    }
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private String isbn;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    private Date createdTime;
    @Column(nullable = false)
    private Boolean expired;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void validateUser(BindingResult bindingResult,BookService bookService){
        //对应resource的messages.properties的信息
        rejectIfEmptyOrWhitespace(bindingResult,"author","author.null");
        bookService.findBookByName(getName()).ifPresent(
                books -> bindingResult.rejectValue("author", "author.alredy.exist"));
    }
}
