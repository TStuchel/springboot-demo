package com.dbs.demo.customer.api

import com.dbs.demo.Application
import com.dbs.demo.BaseSpecification
import com.dbs.demo.RestExceptionHandler
import com.dbs.demo.customer.api.contract.CustomerDTO
import com.dbs.demo.customer.app.CustomerService
import com.dbs.demo.customer.model.Customer
import com.dbs.demo.exception.BusinessException
import org.apache.commons.lang3.RandomUtils
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * DEVELOPER NOTE: The @ExtendWith(SpringExtension.class) and @ContextConfiguration(classes = {Application.class}) is
 * needed so that a "real" SpringBoot context will be initialized. We want this so that we can make "real" REST calls.
 * While it is technically possible to test directly against the CustomerController class, it's useful to make sure that
 * we've wired up all of those @RestController annotations correctly, including properly handling HTTP status code
 * responses, URL mappings, and content type configurations.
 */
@ExtendWith(SpringExtension)
@ContextConfiguration(classes = [Application, RestExceptionHandler])
class CustomerControllerSpec extends BaseSpecification {

    // ------------------------------------------------- DEPENDENCIES --------------------------------------------------

    private CustomerTranslator customerTranslator = new CustomerTranslator()
    private CustomerService customerService


    // ----------------------------------------------- MEMBER VARIABLES ------------------------------------------------

    private static final String V1_GET_CUSTOMER_URI = "/v1/customers/%s"

    /**
     * Create a "mock" client that can call the SpringExtend-ed instance of SpringBoot.
     * DEVELOPER NOTE: We could have used the annotation @AutoConfigureMockMvc on this class and used @Autowired on this
     * field, but due to the preferred form of explicitly building and spying the tested CustomerController we want to
     * manually wire this up in setup() instead.
     */
    private MockMvc mockMvc

    // Class under test
    private CustomerController customerController


    // ------------------------------------------------ SPECIFICATIONS -------------------------------------------------

    def setup() {

        // Inject
        customerService = Mock(CustomerService)
        customerController = new CustomerController(customerTranslator, customerService)

        // Set up Mock MVC for HTTP calls. DEVELOPER NOTE: This makes Spring send requests to our spied controller,
        // instead of whatever controller component it would have created and wired up itself.
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).setControllerAdvice(new RestExceptionHandler()).build()
    }

    def "Find a customer by its ID"() {

        // --
        given: "a customer with a particular ID is in the system"
        def customer = podamFactory.manufacturePojo(Customer)
        def customerId = customer.getCustomerId()

        // --
        when: "the customer API endpoint is called"
        String uri = String.format(V1_GET_CUSTOMER_URI, customerId)
        def result = mockMvc.perform(get(uri))

        // --
        then: "the service should be called to return the customer"
        1 * customerService.getCustomer(customerId) >> customer

        and: "the response HTTP status should be OK"
        result.andExpect(status().isOk())

        and: "the expected customer should be returned"
        def response = result.andReturn().getResponse() // Get the response and deserialize
        def actualCustomerDto = objectMapper.readValue(response.getContentAsByteArray(), CustomerDTO)

        // Spot-check fields
        assert actualCustomerDto.getId() == customerId
        assert actualCustomerDto.getFullName() == actualCustomerDto.getFullName()

    }

    def "Missing customer IDs should throw a NOT_FOUND"() {

        // --
        given: "an ID of a customer that is not in the system"
        def customerId = RandomUtils.nextInt(1, 100)

        // --
        when: "the customer API endpoint is called"
        String uri = String.format(V1_GET_CUSTOMER_URI, customerId)
        def result = mockMvc.perform(get(uri))

        // --
        then: "the service should be called, but will return null"
        1 * customerService.getCustomer(customerId) >> null

        and: "the response HTTP status should be NOT_FOUND"
        result.andExpect(status().isNotFound())

        and: "an error body should be empty"
        def response = result.andReturn().getResponse() // Get the response and deserialize
        response.getContentAsByteArray().length == 0
    }

    def "Invalid customer IDs should throw a BAD_REQUEST"() {

        // --
        given: "an invalid customer ID"
        def customerId = -RandomUtils.nextInt(0, 100)
        def message = String.format(CustomerService.INVALID_CUSTOMER_ID, customerId)

        // --
        when: "the customer API endpoint is called"
        String uri = String.format(V1_GET_CUSTOMER_URI, customerId)
        def result = mockMvc.perform(get(uri))

        // --
        then: "an exception will be thrown by the service"
        1 * customerService.getCustomer(customerId) >> { throw new BusinessException(message) }

        and: "the response HTTP status should be BAD_REQUEST"
        result.andExpect(status().isBadRequest())

        and: "an error body should be returned"
        def response = result.andReturn().getResponse() // Get the response and deserialize
        def error = objectMapper.readValue(response.getContentAsByteArray(), Error)

        and: "it should contain the error message"
        error.getMessage() == message
    }

    def "Unexpected errors should return INTERNAL_SERVER_ERROR"() {

        // --
        given: "a customer ID"
        def customerId = RandomUtils.nextInt(1, 100)

        // --
        when: "the customer API endpoint is called"
        String uri = String.format(V1_GET_CUSTOMER_URI, customerId)
        def result = mockMvc.perform(get(uri))

        then: "if an exception is thrown by the service"
        1 * customerService.getCustomer(customerId) >> { throw new NullPointerException() }

        and: "the response HTTP status should be INTERNAL_SERVER_ERROR"
        result.andExpect(status().isInternalServerError())

        and: "an error body should be returned"
        def response = result.andReturn().getResponse() // Get the response and deserialize
        def error = objectMapper.readValue(response.getContentAsByteArray(), Error)

        and: "it should contain the error"
        error.getMessage().contains("NullPointerException")

    }

}
