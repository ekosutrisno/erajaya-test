package com.erajayaapi.service;

import com.erajayaapi.generator.SimpleDisplayNameGenerator;
import com.erajayaapi.model.OrderEntity;
import com.erajayaapi.repository.OrderRepository;
import com.erajayaapi.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extensions;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Eko Sutrisno
 * @create 13/11/2020 16:43
 */

@Extensions({@ExtendWith(MockitoExtension.class)})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayNameGeneration(SimpleDisplayNameGenerator.class)
class OrderEntityServiceTest {

    @Mock
    private OrderRepository orderRepository;

    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderServiceImpl(orderRepository);
    }

    @Test
    @Order(1)
    void getAllOrder() {
        //Set Mock Example Data
        List<OrderEntity> orders = List.of(
                new OrderEntity(UUID.randomUUID().toString(), "TRX-0001", "ORD-XX1", "Buy IPhone 12 Max", new Date(), "Eko C1", new Date(), "Eko M1", true),
                new OrderEntity(UUID.randomUUID().toString(), "TRX-0002", "ORD-XX2", "Buy IPhone 12 Max Pro", new Date(), "Eko C2", new Date(), "Eko M2", false),
                new OrderEntity(UUID.randomUUID().toString(), "TRX-0003", "ORD-XX3", "Buy IPhone 12 Max Pro", new Date(), "Eko C3", new Date(), "Eko M3", true)
        );

        // Mocking Data
        when(orderRepository.findAll()).thenReturn(orders);
        List<OrderEntity> orderResult = orderService.getAllOrder();

        //Assert Some Condition
        assertEquals(2, orderResult.size());
        assertEquals("TRX-0003", orderResult.get(1).getInvoiceNumber());

        //Verify Mock
        verify(orderRepository, Mockito.times(1)).findAll();

    }

    @Test
    @Order(2)
    void getAllOrderWIthPagination() {
        // Set Mock Example Data
        List<OrderEntity> orders = List.of(
                new OrderEntity(UUID.randomUUID().toString(), "TRX-0001", "ORD-XX1", "Buy IPhone 12 Max", new Date(), "Eko C1", new Date(), "Eko M1", true),
                new OrderEntity(UUID.randomUUID().toString(), "TRX-0002", "ORD-XX2", "Buy IPhone 12 Max Pro", new Date(), "Eko C2", new Date(), "Eko M2", false),
                new OrderEntity(UUID.randomUUID().toString(), "TRX-0003", "ORD-XX3", "Buy IPhone 12 Max Pro", new Date(), "Eko C3", new Date(), "Eko M3", true)
        );

        // Convert to Page Object
        Page<OrderEntity> orderListPaging = new PageImpl<>(
                orders.stream().filter(OrderEntity::getStatus)
                        .collect(Collectors.toList())
        );

        // Set Pageable Parameter
        Pageable pageable = PageRequest.of(0, 10);

        //Mocking
        when(orderRepository.findAllOrderWithPagination(pageable)).thenReturn(orderListPaging);
        Page<OrderEntity> orderPageResult = orderService.getAllOrderWIthPagination(pageable);

        // Assert
        assertNotNull(orderPageResult.getContent());
        assertEquals(2, orderPageResult.getContent().size());

        //Verify
        verify(orderRepository, times(1)).findAllOrderWithPagination(pageable);

    }

    @Test
    @Order(3)
    void getDataOrderById() {
        //Initial Example Data
        Optional<OrderEntity> optionalOrderEntity = Optional.of(new OrderEntity(UUID.randomUUID().toString(), "TRX-0001", "ORD-XX1", "Buy IPhone 12 Max", new Date(), "Eko C1", new Date(), "Eko M1", true));

        String orderId = optionalOrderEntity.get().getOrderId();
        //Mocking Data
        when(orderRepository.findById(orderId)).thenReturn(optionalOrderEntity);
        Optional<OrderEntity> optionalOrder = orderService.getDataOrderById(orderId);

        //Assert Condition
        assertTrue(optionalOrder.isPresent());
        assertEquals("ORD-XX1", optionalOrder.get().getOrderName());

        //Verify
        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    @Order(4)
    void saveDataOrder() {
        //Util
        String orderId = UUID.randomUUID().toString();

        // Initialize example data
        OrderEntity orderEntity = new OrderEntity(orderId, "TRX-0001", "ORD-XX1", "Buy IPhone 12 Max", new Date(), "Eko C1", new Date(), "Eko M1", true);

        //Mocking
        when(orderRepository.save(orderEntity)).thenReturn(orderEntity);
        OrderEntity orderResult = orderService.saveDataOrder(orderEntity);

        //Assert Condition
        assertNotNull(orderResult);
        assertEquals(orderId, orderResult.getOrderId());

        //Verify
        System.out.println(mockingDetails(orderResult).getMock());
        verify(orderRepository, times(1)).save(orderEntity);

    }

    @Test
    @Order(5)
    void updateDataOrder() {
    }

    @Test
    @Order(6)
    void deleteDataOrder() {
    }
}