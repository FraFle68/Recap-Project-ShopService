import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        OrderRepo orderRepo = new OrderMapRepo();
        ProductRepo productRepo = new ProductRepo();
        ShopService neueFischeMerch = new ShopService(productRepo, orderRepo);

        productRepo.addProduct(new Product("01", "Kaffee Tasse"));
        productRepo.addProduct(new Product("02", "Plüsch Florian"));
        productRepo.addProduct(new Product("03", "Mouspad"));
        productRepo.addProduct(new Product("04", "gebrauchtes MacBook"));
        productRepo.addProduct(new Product("05", "Fußmatte"));
        productRepo.addProduct(new Product("06", "Bierkrug"));
        productRepo.addProduct(new Product("07", "Wimpel"));
        productRepo.addProduct(new Product("08", "Poster"));
        productRepo.addProduct(new Product("09", "Hintergrundbilder"));
        productRepo.addProduct(new Product("10", "Abschlusszertifikat"));

        List<String> order1 = new ArrayList<>();
        order1.add("01");
        order1.add("06");

        List<String> order2 = new ArrayList<>();
        order1.add("03");
        order1.add("04");
        order1.add("09");
        order1.add("10");

        List<String> order3 = new ArrayList<>();
        order1.add("02");
        order1.add("05");
        order1.add("08");

        neueFischeMerch.addOrder(order1);
        neueFischeMerch.addOrder(order2);
        neueFischeMerch.addOrder(order3);
    }
}
