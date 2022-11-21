package com.getircase.backend.service.mapper;

import com.getircase.backend.domain.Book;
import com.getircase.backend.domain.Customer;
import com.getircase.backend.domain.Order;
import com.getircase.backend.service.dto.BookDTO;
import com.getircase.backend.service.dto.CustomerDTO;
import com.getircase.backend.service.dto.OrderDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Order} and its DTO {@link OrderDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrderMapper extends EntityMapper<OrderDTO, Order> {
    @Mapping(target = "books", source = "books", qualifiedByName = "bookIdSet")
    @Mapping(target = "customer", source = "customer", qualifiedByName = "customerId")
    OrderDTO toDto(Order s);

    @Mapping(target = "removeBook", ignore = true)
    Order toEntity(OrderDTO orderDTO);

    @Named("bookId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BookDTO toDtoBookId(Book book);

    @Named("bookIdSet")
    default Set<BookDTO> toDtoBookIdSet(Set<Book> book) {
        return book.stream().map(this::toDtoBookId).collect(Collectors.toSet());
    }

    @Named("customerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CustomerDTO toDtoCustomerId(Customer customer);
}
