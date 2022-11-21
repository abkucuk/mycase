package com.getircase.backend.repository;

import com.getircase.backend.domain.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Order entity.
 *
 * When extending this class, extend OrderRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface OrderRepository extends OrderRepositoryWithBagRelationships, JpaRepository<Order, Long> {
    default Optional<Order> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<Order> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<Order> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }

    default List<Order> findAllByCustomer_IdWithEagerRelationships(Long id){
        return this.fetchBagRelationships(this.findAllByCustomer_Id(id));
    }

    @Query("select o from Order o where o.customer.id = :id")
    List<Order> findAllByCustomer_Id(@Param("id") Long id);
   // SELECT * FROM jhi_order WHERE jhi_order.order_date >= TO_TIMESTAMP( '01-11-2022', 'dd-mm-yyyy' ) AND jhi_order.order_date < TO_TIMESTAMP( '30-11-2022' , 'dd-mm-yyyy')
    @Query(value = "SELECT * FROM jhi_order WHERE jhi_order.order_date >= TO_TIMESTAMP( :startDate, 'dd-mm-yyyy' ) AND jhi_order.order_date < TO_TIMESTAMP( :endDate , 'dd-mm-yyyy') ", nativeQuery = true)
    List<Order> findByOrderDatesMounth(@Param("startDate") String startDate, @Param("endDate") String endDate);
}
