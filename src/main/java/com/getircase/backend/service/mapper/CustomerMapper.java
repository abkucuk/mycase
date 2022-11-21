package com.getircase.backend.service.mapper;

import com.getircase.backend.domain.Customer;
import com.getircase.backend.domain.Location;
import com.getircase.backend.service.dto.CustomerDTO;
import com.getircase.backend.service.dto.LocationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Customer} and its DTO {@link CustomerDTO}.
 */
@Mapper(componentModel = "spring")
public interface CustomerMapper extends EntityMapper<CustomerDTO, Customer> {
    @Mapping(target = "location", source = "location", qualifiedByName = "locationId")
    CustomerDTO toDto(Customer s);

    @Named("locationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LocationDTO toDtoLocationId(Location location);
}
