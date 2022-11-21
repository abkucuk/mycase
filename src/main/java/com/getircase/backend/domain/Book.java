package com.getircase.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Book.
 */
@Entity
@Table(name = "book")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "author_name", nullable = false)
    private String authorName;

    @Column(name = "page_count")
    private Integer pageCount;

    @NotNull
    @Column(name = "price", nullable = false)
    private Float price;

    @NotNull
    @Column(name = "stock_count", nullable = false)
    private Integer stockCount;

    @ManyToMany(mappedBy = "books")
    @JsonIgnoreProperties(value = { "books", "customer" }, allowSetters = true)
    private Set<Order> orders = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Book id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorName() {
        return this.authorName;
    }

    public Book authorName(String authorName) {
        this.setAuthorName(authorName);
        return this;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Integer getPageCount() {
        return this.pageCount;
    }

    public Book pageCount(Integer pageCount) {
        this.setPageCount(pageCount);
        return this;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Float getPrice() {
        return this.price;
    }

    public Book price(Float price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getStockCount() {
        return this.stockCount;
    }

    public Book stockCount(Integer stockCount) {
        this.setStockCount(stockCount);
        return this;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Set<Order> getOrders() {
        return this.orders;
    }

    public void setOrders(Set<Order> orders) {
        if (this.orders != null) {
            this.orders.forEach(i -> i.removeBook(this));
        }
        if (orders != null) {
            orders.forEach(i -> i.addBook(this));
        }
        this.orders = orders;
    }

    public Book orders(Set<Order> orders) {
        this.setOrders(orders);
        return this;
    }

    public Book addOrder(Order order) {
        this.orders.add(order);
        order.getBooks().add(this);
        return this;
    }

    public Book removeOrder(Order order) {
        this.orders.remove(order);
        order.getBooks().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Book)) {
            return false;
        }
        return id != null && id.equals(((Book) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Book{" +
            "id=" + getId() +
            ", authorName='" + getAuthorName() + "'" +
            ", pageCount=" + getPageCount() +
            ", price=" + getPrice() +
            ", stockCount=" + getStockCount() +
            "}";
    }
}
