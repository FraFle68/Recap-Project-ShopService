import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ShopService {
    private ProductRepo productRepo = new ProductRepo();
    private OrderRepo orderRepo = new OrderMapRepo();

    public Order addOrder(List<String> productIds) {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Optional<Product> productToOrder = productRepo.getProductById(productId);
            if (productToOrder.isEmpty()) {
                throw new RuntimeException("Product mit der Id: " + productId + " konnte nicht bestellt werden!");
            }
            products.add(productToOrder.get());
        }

        Order newOrder = new Order(UUID.randomUUID().toString(), products, OrderStatus.PROCESSING, ZonedDateTime.now());

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
}
