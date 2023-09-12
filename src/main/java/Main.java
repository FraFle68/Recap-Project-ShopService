import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        OrderRepo orderRepo = new OrderMapRepo();
        ProductRepo productRepo = new ProductRepo();
        IdService idService = new IdService();
        ShopService neueFischeMerch = new ShopService(productRepo, orderRepo, idService);

        List<String> allLines = null;
        try {
            allLines = Files.readAllLines(Paths.get("transactions.txt"));

        } catch (IOException exception) {
            exception.printStackTrace();
        }

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

        Map<String, String> orderKeys = new HashMap<>();

        assert allLines != null : "Die eingelesene Datei ist leer";

        for (String line : allLines) {
            String[] elements = line.split(" ");
            switch(elements[0]){
                case "addOrder":
                    List<String> products = new ArrayList<>();
                    for (int i = 2; i < elements.length; i++) {
                        products.add(elements[i]);
                    }
                    orderKeys.put(elements[1], neueFischeMerch.addOrder(products).id());
                    break;
                case "setStatus":
                    neueFischeMerch.updateOrder(orderKeys.get(elements[1]), OrderStatus.valueOf(elements[2]));
                    break;
                case "printOrders":
                    for (Order order : orderRepo.getOrders()) {
                        System.out.println(order);
                    }
                    break;
            }
            System.out.println(orderKeys);
        }

    }
}
