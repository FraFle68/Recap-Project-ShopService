import lombok.With;

import java.time.ZonedDateTime;
import java.util.List;

@With
public record Order (
        String id,
        List<Product> products,
        OrderStatus status,
        ZonedDateTime orderTimestamp
) implements Comparable<Order> {
    @Override
    public int compareTo(Order o) {
        return this.orderTimestamp.compareTo(o.orderTimestamp);
    }
}
