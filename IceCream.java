import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Enum representing different seasons
enum Season {
    WINTER, SPRING, SUMMER, FALL
}

// Enum representing different ice cream flavors
enum IceCreamFlavor {
    VANILLA(100), CHOCOLATE(150), STRAWBERRY(180), MINT_CHOCOLATE(250), COOKIE_DOUGH(500);

    private double price;

    IceCreamFlavor(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}

// Enum representing different toppings
enum Topping {
    SPRINKLES(50), CHOCOLATE_CHIPS(75), NUTS(50), WHIPPED_CREAM(100);

    private double price;

    Topping(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}

// Enum representing different syrups
enum Syrup {
    CHOCOLATE(50), CARAMEL(80), STRAWBERRY(120), MAPLE(120);

    private double price;

    Syrup(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}

// Class representing seasonal specials
class SeasonalSpecial {
    private Season season;
    IceCreamFlavor discountedFlavor;
    int discountPercentage;

    public SeasonalSpecial(Season season, IceCreamFlavor discountedFlavor, int discountPercentage) {
        this.season = season;
        this.discountedFlavor = discountedFlavor;
        this.discountPercentage = discountPercentage;
    }

    public boolean isApplicable(Season currentSeason, IceCreamFlavor iceCreamFlavor) {
        return this.season == currentSeason && this.discountedFlavor == iceCreamFlavor;
    }

    public double applyDiscount(double originalPrice) {
        double discountAmount = originalPrice * (discountPercentage / 100.0);
        return originalPrice - discountAmount;
    }
}

// IceCreamCombination class representing the final ice cream combination with a total price
class IceCreamCombination implements IceCreamDecorator {
    protected IceCreamFlavor flavor;
    protected List<Topping> toppings;
    protected List<Syrup> syrups;
    protected double totalPrice;
    protected String name;

    public IceCreamCombination(IceCreamFlavor flavor, String name) {
        this.flavor = flavor;
        this.toppings = new ArrayList<>();
        this.syrups = new ArrayList<>();
        this.totalPrice = flavor.getPrice();
        this.name = name;
    }

    public void addTopping(Topping topping) {
        toppings.add(topping);
        totalPrice += topping.getPrice();
    }

    public void addSyrup(Syrup syrup) {
        syrups.add(syrup);
        totalPrice += syrup.getPrice();
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public IceCreamFlavor getFlavor() {
        return flavor;
    }
    
    public String getName() {
        return name;
    }
    
    

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Ice Cream Name: ").append(name).append("\nFlavor: ").append(flavor);

        if (!toppings.isEmpty()) {
            stringBuilder.append("\nToppings: ");
            for (Topping topping : toppings) {
                stringBuilder.append(topping).append(", ");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1); // Remove trailing comma
            stringBuilder.deleteCharAt(stringBuilder.length() - 1); // Remove space after comma
        }

        if (!syrups.isEmpty()) {
            stringBuilder.append("\nSyrups: ");
            for (Syrup syrup : syrups) {
                stringBuilder.append(syrup).append(", ");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1); // Remove trailing comma
            stringBuilder.deleteCharAt(stringBuilder.length() - 1); // Remove space after comma
        }

        stringBuilder.append("\nTotal Price: LKR ").append(String.format("%.2f", getTotalPrice()));
        return stringBuilder.toString();
    }

    @Override
    public double getPrice() {
        return totalPrice;
    }

    @Override
    public String getDescription() {
        return name;
    }
}

// IceCreamCombinationBuilder class to build IceCreamCombination objects
class IceCreamCombinationBuilder {
    private IceCreamFlavor flavor;
    private String name;
    private List<Topping> toppings;
    private List<Syrup> syrups;

    public IceCreamCombinationBuilder(String name) {
        this.name = name;
        this.toppings = new ArrayList<>();
        this.syrups = new ArrayList<>();
    }

    public IceCreamCombinationBuilder withFlavor(IceCreamFlavor flavor) {
        this.flavor = flavor;
        return this;
    }

    public IceCreamCombinationBuilder withTopping(Topping topping) {
        this.toppings.add(topping);
        return this;
    }

    public IceCreamCombinationBuilder withSyrup(Syrup syrup) {
        this.syrups.add(syrup);
        return this;
    }

    public IceCreamCombination build() {
        IceCreamCombination iceCreamCombination = new IceCreamCombination(flavor, name);
        iceCreamCombination.toppings.addAll(toppings);
        iceCreamCombination.syrups.addAll(syrups);
        for (Topping topping : toppings) {
            iceCreamCombination.totalPrice += topping.getPrice();
        }
        for (Syrup syrup : syrups) {
            iceCreamCombination.totalPrice += syrup.getPrice();
        }
        return iceCreamCombination;
    }
}

// Decorator interface
interface IceCreamDecorator {
    double getPrice();  // Decorators should provide their own getPrice method
    String getDescription();  // Decorators should provide their own getDescription method
}

// Concrete decorator for gift wrapping
class GiftWrappingDecorator implements IceCreamDecorator {
    private IceCreamCombination iceCream;

    public GiftWrappingDecorator(IceCreamCombination iceCream) {
        this.iceCream = iceCream;
    }

    @Override
    public double getPrice() {
        return iceCream.getTotalPrice() + 50;  // Assuming gift wrapping costs an additional 50 LKR
    }

    @Override
    public String getDescription() {
        return iceCream.getName() + " with Gift Wrapping";
    }
}

// Concrete decorator for special packaging
class SpecialPackagingDecorator implements IceCreamDecorator {
    private IceCreamCombination iceCream;

    public SpecialPackagingDecorator(IceCreamCombination iceCream) {
        this.iceCream = iceCream;
    }

    @Override
    public double getPrice() {
        return iceCream.getTotalPrice() + 30;  // Assuming special packaging costs an additional 30 LKR
    }

    @Override
    public String getDescription() {
        return iceCream.getName() + " with Special Packaging";
    }
}

// Observer interface to be implemented by observers (users)
interface OrderObserver {
    void update(Order order);
}

// State interface representing different states of an order
interface OrderState {
    void process(Order order);
}

// Concrete State class representing the "Placed" state
class PlacedState implements OrderState {
    @Override
    public void process(Order order) {
        order.setStatus("Order Placed");
        order.notifyObservers();
    }
}

// Concrete State class representing the "In Preparation" state
class InPreparationState implements OrderState {
    @Override
    public void process(Order order) {
        order.setStatus("Ice Cream preparation in process.......");
        order.notifyObservers();
    }
}

// Concrete State class representing the "Out for Delivery" state
class OutForDeliveryState implements OrderState {
    @Override
    public void process(Order order) {
        order.setStatus("Out for Delivery");
        order.notifyObservers();
    }
}

// Concrete State class representing the "Delivered" state
class DeliveredState implements OrderState {
    @Override
    public void process(Order order) {
        order.setStatus("Delivered");
        order.notifyObservers();
    }
}

// Subject interface representing an observable subject
interface OrderSubject {
    void registerObserver(OrderObserver observer);
    void removeObserver(OrderObserver observer);
    void notifyObservers();
}

// Class representing pickup details
class Pickup {
    private String pickupLocation;

    public Pickup(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }
}

// Enum representing different delivery methods
enum DeliveryMethod {
    DELIVERY, PICKUP
}

// Concrete Subject class representing an order
class Order implements OrderSubject {
    private String status;
    private List<String> order;
    private DeliveryMethod deliveryMethod;
    private Pickup pickup;  // Pickup details
    private String deliveryAddress;
    private List<OrderObserver> observers;
    private OrderState currentState;

    public Order(DeliveryMethod deliveryMethod) {
        this.status = "";
        this.order = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.currentState = new PlacedState(); // Initial state is "Placed"
        this.deliveryMethod = deliveryMethod;

    }

    
        // Overriding the addItem method to handle regular items and customized ice creams differently
   
    public void addItem(String item) {
            this.order.add(item);
        
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getItems() {
        return order;
    }


    public void setDeliveryAddress(String address) {
        this.deliveryAddress = address;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickup = new Pickup(pickupLocation);
    }

    public void processOrder() {
        currentState.process(this);
    }

    public void nextState() {
        // Transition to the next state
        if (currentState instanceof PlacedState) {
            currentState = getNextStateForDeliveryMethod();
        }  else if (currentState instanceof InPreparationState) {
            currentState = new OutForDeliveryState();
        }else if (currentState instanceof OutForDeliveryState) {
            currentState = new DeliveredState();
        }
        // Add more states as needed
        currentState.process(this); // Process the new state
    }

    private OrderState getNextStateForDeliveryMethod() {
        // Determine the next state based on the delivery method
        if (deliveryMethod == DeliveryMethod.DELIVERY) {
            return new InPreparationState();
        } else if (deliveryMethod == DeliveryMethod.PICKUP) {
            return new ReadyForPickupState();
        }
        // Add more conditions as needed
        return null;
    }
    
    
    
    
    
    class ReadyForPickupState implements OrderState {
    @Override
    public void process(Order order) {
        order.setStatus("Ready for Pickup");
        order.notifyObservers();
    }
}

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public DeliveryMethod getDeliveryMethod() {
        return deliveryMethod;
    }

    public Pickup getPickup() {
        return pickup;
    }

    @Override
    public void registerObserver(OrderObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(OrderObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (OrderObserver observer : observers) {
            observer.update(this);
        }
    }
}

// Concrete Observer class representing a user
class User implements OrderObserver {
    private String username;

    public User(String username) {
        this.username = username;
    }

    @Override
    public void update(Order order) {
        System.out.println("Hello " + username + "! Order status: " + order.getStatus());
        if (order.getStatus().equals("Order Placed")) {
            System.out.println("Order : " + order.getItems());

            if (order.getDeliveryMethod() == DeliveryMethod.DELIVERY) {
                System.out.println("Delivery Address: " + order.getDeliveryAddress());
            } else if (order.getDeliveryMethod() == DeliveryMethod.PICKUP) {
                System.out.println("Pickup Location: " + order.getPickup().getPickupLocation());
            }

            System.out.println("------------------------------");
        }
    }
}

// PaymentStrategy interface
interface PaymentStrategy {
    void pay(double amount);
}

// Concrete payment strategies
class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    private String name;
    private String expirationDate;
    private String cvv;

    public CreditCardPayment(String cardNumber, String name, String expirationDate, String cvv) {
        this.cardNumber = cardNumber;
        this.name = name;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
    }

    @Override
    public void pay(double amount) {
        // Implement credit card payment logic
        System.out.println("Paid " + amount + " with Credit Card");
    }
}

class DigitalWalletPayment implements PaymentStrategy {
    private String walletId;
    private String password;

    public DigitalWalletPayment(String walletId, String password) {
        this.walletId = walletId;
        this.password = password;
    }

    @Override
    public void pay(double amount) {
        // Implement digital wallet payment logic
        System.out.println("Paid " + amount + " with Digital Wallet");
    }
}

class CashOnDelivery implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        // Implement cash on delivery payment logic
        System.out.println("Paid " + amount + " with Cash on Delivery");
    }
}

// LoyaltyProgram interface
interface LoyaltyProgram {
    void earnPoints(double amount);
    double getEarnedPoints();
}

// Concrete loyalty program implementation
class SimpleLoyaltyProgram implements LoyaltyProgram {
    private Map<String, Double> pointsMap;

    public SimpleLoyaltyProgram() {
        this.pointsMap = new HashMap<>();
    }

    @Override
    public void earnPoints(double amount) {
        // Assume 1 point for every LKR 10 spent
        double pointsEarned = amount / 100.0;
        pointsMap.put("Anbu", pointsMap.getOrDefault("Anbu", 0.0) + pointsEarned);
    }

    @Override
    public double getEarnedPoints() {
        return pointsMap.getOrDefault("Anbu", 0.0);
    }
}

// Command interface representing a user action
interface PaymentCommand {
    void execute();
}

// Concrete Command class representing providing feedback
class FeedbackCommand implements PaymentCommand {
    private LoyaltyProgram loyaltyProgram;
    private String feedbackMessage;

    public FeedbackCommand(LoyaltyProgram loyaltyProgram, String feedbackMessage) {
        this.loyaltyProgram = loyaltyProgram;
        this.feedbackMessage = feedbackMessage;
    }

    @Override
    public void execute() {
        // Implement feedback logic here
        System.out.println("Feedback: " + feedbackMessage);
    }
}

// PaymentProcessor using the Strategy and Command patterns
class PaymentProcessor {
    private PaymentStrategy paymentStrategy;
    private LoyaltyProgram loyaltyProgram;
    private List<PaymentCommand> commandQueue;

    public PaymentProcessor(PaymentStrategy paymentStrategy, LoyaltyProgram loyaltyProgram) {
        this.paymentStrategy = paymentStrategy;
        this.loyaltyProgram = loyaltyProgram;
        this.commandQueue = new ArrayList<>();
    }

    public void processPayment(double amount) {
        // Process payment using the selected strategy
        paymentStrategy.pay(amount);

        // Update loyalty points
        loyaltyProgram.earnPoints(amount);
    }

    public void queueCommand(PaymentCommand command) {
        // Queue a command for later execution
        commandQueue.add(command);
    }

    public void executeCommands() {
        // Execute all queued commands
        for (PaymentCommand command : commandQueue) {
            command.execute();
        }
        commandQueue.clear(); // Clear the command queue after execution
    }

    public double getEarnedPoints() {
        // Get the total earned loyalty points
        return loyaltyProgram.getEarnedPoints();
    }
}

// Client code
public class IceCream {

    public static void main(String[] args) {
        // Creating a basic ice cream combination
        IceCreamCombination basicIceCream = new IceCreamCombinationBuilder("Chocolate Caramel")
                .withFlavor(IceCreamFlavor.CHOCOLATE)
                .withTopping(Topping.SPRINKLES)
                .withSyrup(Syrup.CARAMEL)
                .build();

        // Displaying the basic ice cream combination
        System.out.println("Ice Cream Customization:\n" + basicIceCream);

        // Apply seasonal discount if applicable (e.g., discount on chocolate flavor in winter)
        Season currentSeason = Season.WINTER;
        IceCreamFlavor iceCreamFlavor = basicIceCream.getFlavor();
        System.out.println("------------------------------");
        
                // Create decorators for additional features
        IceCreamDecorator giftWrappingIceCream = new GiftWrappingDecorator(basicIceCream);
        IceCreamDecorator specialPackagingIceCream = new SpecialPackagingDecorator(basicIceCream);
        

        // Display prices and descriptions
        System.out.println("Basic Ice Cream: " + basicIceCream.getDescription() + " - LKR " + basicIceCream.getTotalPrice());
        System.out.println("------------------------------");
        System.out.println("Ice Cream with Gift Wrapping: " + giftWrappingIceCream.getDescription() + " - LKR " + giftWrappingIceCream.getPrice());
        System.out.println("Ice Cream with Special Packaging: " + specialPackagingIceCream.getDescription() + " - LKR " + specialPackagingIceCream.getPrice());
         System.out.println("------------------------------");

        // Define seasonal specials
        List<SeasonalSpecial> seasonalSpecials = new ArrayList<>();
        seasonalSpecials.add(new SeasonalSpecial(Season.WINTER, IceCreamFlavor.CHOCOLATE, 10));

        double discountedPrice = basicIceCream.getTotalPrice(); // Initialize with the original price
        // Check if any seasonal special is applicable
        for (SeasonalSpecial seasonalSpecial : seasonalSpecials) {
            if (seasonalSpecial.isApplicable(currentSeason, iceCreamFlavor)) {
                discountedPrice = seasonalSpecial.applyDiscount(discountedPrice);
                System.out.println("\nApplying Seasonal Discount: " + seasonalSpecial.discountedFlavor +
                        " - " + seasonalSpecial.discountPercentage + "% off");
                System.out.println("Discounted Price: LKR " + String.format("%.2f", discountedPrice ));
                break; // Break after applying the first applicable discount
            }
        }
        System.out.println("------------------------------");
        
                // Creating an order with delivery
        Order deliveryOrder = new Order(DeliveryMethod.DELIVERY);
        deliveryOrder.addItem("Chocolate Caramel");
        deliveryOrder.setDeliveryAddress("Bambapitiya, Colombo");

        // Creating an order with pickup
        Order pickupOrder = new Order(DeliveryMethod.PICKUP);
        pickupOrder.addItem("Chocolate Caramel");
        pickupOrder.setPickupLocation("IceCreamShop");
        

        // Creating users after the orders are submitted
        User user1 = new User("Anbu");

        // Adding users as observers
        deliveryOrder.registerObserver(user1);
        pickupOrder.registerObserver(user1);

        // Simulating the order process for delivery
        deliveryOrder.processOrder();
        deliveryOrder.nextState();
        deliveryOrder.nextState();
        deliveryOrder.nextState();
        
        System.out.println("------------------------------");

        // Simulating the order process for pickup
        pickupOrder.processOrder();
        pickupOrder.nextState();
        System.out.println("------------------------------");
        
        // Create payment strategy (Cash on Delivery)
        PaymentStrategy creditCardPayment = new CreditCardPayment("1234-5678-9101-1121", "John Doe", "12/23", "123");
        PaymentStrategy walletPayment = new DigitalWalletPayment("john_wallet", "password");

        PaymentStrategy codPayment = new CashOnDelivery();

        // Create loyalty program
        LoyaltyProgram loyaltyProgram = new SimpleLoyaltyProgram();

        // Create PaymentProcessor instance with the cash on delivery strategy
        PaymentProcessor creditCardProcessor = new PaymentProcessor(creditCardPayment, loyaltyProgram);
        PaymentProcessor walletProcessor = new PaymentProcessor(walletPayment, loyaltyProgram);
        PaymentProcessor codProcessor = new PaymentProcessor(codPayment, loyaltyProgram);

        // Process payment 
        walletProcessor.processPayment(discountedPrice);
        System.out.println("------------------------------");
        creditCardProcessor.processPayment(discountedPrice);
        System.out.println("------------------------------");
        codProcessor.processPayment(discountedPrice);
        System.out.println("------------------------------");

        FeedbackCommand feedbackCommand = new FeedbackCommand(loyaltyProgram, "yummmy :)");
        codProcessor.queueCommand(feedbackCommand);

        // Execute queued commands and display earned points
        codProcessor.executeCommands();
        double earnedPoints = codProcessor.getEarnedPoints();
        System.out.println("Loyalty Points Earned: " + earnedPoints);
    }
}
