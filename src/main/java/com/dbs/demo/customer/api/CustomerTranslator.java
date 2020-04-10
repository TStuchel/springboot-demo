package com.dbs.demo.customer.api;

import com.dbs.demo.customer.api.contract.CustomerDTO;
import com.dbs.demo.customer.model.Customer;
import org.springframework.stereotype.Component;

/**
 * Translate to and from CustomerDTO web service contracts and Customer business entities.
 * <p>
 * DEVELOPER NOTE: This layer of abstraction may seem like overkill. However, if you tie your business logic to web
 * service contract classes, then it becomes increasingly difficult to version your web service endpoints. Having a
 * separate entity class, even if it's nearly identical to the contract class, allows for an evolving layer of business
 * logic while at the same time remaining backward compatible with any API versioning that is necessary. Also, having
 * proper business entity classes allows for implementing the "tell, don't ask" design principle.
 */
@Component
public class CustomerTranslator {

    // ------------------------------------------------ PUBLIC METHODS -------------------------------------------------

    /**
     * Translate the given CustomerDTO to a new Customer entity.
     */
    public Customer toEntity(CustomerDTO customerDto) {

        Customer customer = new Customer();
        customer.setFullName(customerDto.getFullName());
        customer.setCustomerId(customerDto.getId());
        customer.setLastReadTimestamp(customerDto.getLastReadTimestamp());
        customer.setStreetAddress("Unknown");
        return customer;
    }

    /**
     * Translate the given Customer to a new CustomerDTO contract.
     */
    public CustomerDTO toContract(Customer customer) {
        return CustomerDTO.builder()
                .fullName(customer.getFullName())
                .id(customer.getCustomerId())
                .lastReadTimestamp(customer.getLastReadTimestamp())
                .build();
    }

}
