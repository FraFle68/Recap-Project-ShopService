import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;
import java.util.*;

@RequiredArgsConstructor
public class ShopService {
    private final ProductRepo productRepo;
    private final OrderRepo orderRepo;
    private final IdService idService;

    public Order addOrder(List<String> productIds) {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Optional<Product> productToOrder = productRepo.getProductById(productId);
            if (productToOrder.isEmpty()) {
                throw new RuntimeException("Product mit der Id: " + productId + " konnte nicht bestellt werden!");
            }
            products.add(productToOrder.get());
        }

        Order newOrder = new Order(idService.generateId().toString(), products, OrderStatus.PROCESSING, ZonedDateTime.now());

        return orderRepo.addOrder(newOrder);
    }

    public List<Order> listOrderByOrderStatus (OrderStatus status) {
        return orderRepo.getOrders().stream()
                .filter(order -> order.status() == status)
                .toList();
    }

    public void updateOrder(String orderId, OrderStatus status){
        Order updatedOrder = orderRepo.getOrderById(orderId).withStatus(status);
        orderRepo.removeOrder(orderId);
        orderRepo.addOrder(updatedOrder);
    }

    public Map<OrderStatus, Order> getOldestOrderPerStatus() {
        Map<OrderStatus, Order> result = new HashMap<>();
        for (int i = 0; i < OrderStatus.values().length; i++) {
            OrderStatus statusToCheck = OrderStatus.values()[i];
            orderRepo.getOrders().stream()
                    .filter(order -> order.status().equals(statusToCheck))
                    .min(Order::compareTo)
                    .ifPresent(order -> result.put(order.status(), order));
        }
        return result;
    }
}
