package com.pizzaapp.web.controllers;

import com.pizzaapp.domain.models.service.order.OrderCreateServiceModel;
import com.pizzaapp.domain.models.service.cart.DrinkCartServiceModel;
import com.pizzaapp.domain.models.service.cart.PizzaCartServiceModel;
import com.pizzaapp.domain.models.view.cart.DrinkCartViewModel;
import com.pizzaapp.domain.models.view.cart.PizzaCartViewModel;
import com.pizzaapp.domain.models.view.cart.PizzaOrderViewModel;
import com.pizzaapp.domain.models.view.cart.ShoppingCartItems;
import com.pizzaapp.domain.models.view.menu.DrinkViewModel;
import com.pizzaapp.service.MenuService;
import com.pizzaapp.service.OrderService;
import com.pizzaapp.service.SizeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.util.LinkedList;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cart")
@PreAuthorize("hasRole('ROLE_USER') && !hasRole('ROLE_COURIER')")
public class CartController extends BaseController {

    private final MenuService menuService;
    private final SizeService sizeService;
    private final OrderService orderService;
    private final ModelMapper modelMapper;

    @Autowired
    public CartController(MenuService menuService,
                          SizeService sizeService,
                          OrderService orderService,
                          ModelMapper modelMapper) {
        this.menuService = menuService;
        this.sizeService = sizeService;
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    private ShoppingCartItems retrieveCart(HttpSession session) {
        initCart(session, false);

        ShoppingCartItems cart = (ShoppingCartItems) session.getAttribute("shopping-cart");

        if (cart.getPizzas() == null) {
            cart.setPizzas(new LinkedList<>());
        }

        if (cart.getDrinks() == null) {
            cart.setDrinks(new LinkedList<>());
        }

        return cart;
    }

    private void initCart(HttpSession session, boolean newSession) {
        if (session.getAttribute("shopping-cart") == null || newSession) {
            session.setAttribute("shopping-cart", new ShoppingCartItems());
        }
    }

    private <T> void addItemToCart(T item, ShoppingCartItems cart, String type) {
        if (type.equals("pizza")) {
            PizzaCartViewModel itemToAdd = (PizzaCartViewModel) item;
            for (PizzaCartViewModel pizzaCartViewModel : cart.getPizzas()) {
                if (pizzaCartViewModel.getItem().getId().equals(itemToAdd.getItem().getId())) {
                    pizzaCartViewModel.setQuantity(pizzaCartViewModel.getQuantity() + itemToAdd.getQuantity());
                    return;
                }
            }
            cart.getPizzas().add(itemToAdd);

        } else if (type.equals("drink")) {
            DrinkCartViewModel itemToAdd = (DrinkCartViewModel) item;
            for (DrinkCartViewModel drinkViewModel : cart.getDrinks()) {
                if (drinkViewModel.getItem().getId().equals(itemToAdd.getItem().getId())) {
                    drinkViewModel.setQuantity(drinkViewModel.getQuantity() + itemToAdd.getQuantity());
                    return;
                }
            }
            cart.getDrinks().add(itemToAdd);
        }
    }

    private BigDecimal calcTotal(ShoppingCartItems cart) {
        BigDecimal result = new BigDecimal(0);
        for (PizzaCartViewModel item : cart.getPizzas()) {
            result = result.add(item.getItem().getPrice().multiply(new BigDecimal(item.getQuantity())));
        }

        for (DrinkCartViewModel item : cart.getDrinks()) {
            result = result.add(item.getItem().getPrice().multiply(new BigDecimal(item.getQuantity())));
        }

        return result.setScale(2, RoundingMode.HALF_UP);
    }

    private void removeItemFromCart(String id, ShoppingCartItems cart) {
        cart.getPizzas().removeIf(cartItem -> cartItem.getItem().getId().equals(id));
        cart.getDrinks().removeIf(cartItem -> cartItem.getItem().getId().equals(id));
    }

    private OrderCreateServiceModel prepareOrder(ShoppingCartItems cart, String customer, String addressId, BigDecimal price) {
        OrderCreateServiceModel order = new OrderCreateServiceModel();

        order.setCustomer(customer);
        order.setAddressId(addressId);
        order.setTotalPrice(price);

        order.setPizzas(
                cart.getPizzas().stream()
                .map(pizza -> modelMapper.map(pizza, PizzaCartServiceModel.class))
                .collect(Collectors.toList())
        );

        order.setDrinks(
                cart.getDrinks().stream()
                        .map(drink -> modelMapper.map(drink, DrinkCartServiceModel.class))
                        .collect(Collectors.toList())
        );

        return order;
    }

    @PostMapping("/add-pizza")
    public ModelAndView addPizzaToCartConfirm(String id, int quantity, String dough, String size, HttpSession session) {
        PizzaOrderViewModel pizza = modelMapper.map(menuService.getPizzaById(id), PizzaOrderViewModel.class);
        pizza.setDough(dough);
        pizza.setSize(sizeService.getBySizeName(size));

        double multiplier = sizeService.getBySizeName(size).getMultiplier();
        BigDecimal price = pizza.getPrice().multiply(new BigDecimal(multiplier));

        pizza.setPrice(price.setScale(2, RoundingMode.HALF_UP));

        PizzaCartViewModel pizzaCartViewModel = new PizzaCartViewModel();
        pizzaCartViewModel.setItem(pizza);
        pizzaCartViewModel.setQuantity(quantity);

        ShoppingCartItems cart = retrieveCart(session);
        addItemToCart(pizzaCartViewModel, cart, "pizza");

        return redirect("/menu/order/pizza");
    }

    @PostMapping("/add-drink")
    public ModelAndView addDrinkToCartConfirm(String id, int quantity, HttpSession session) {
        DrinkViewModel pizza = modelMapper.map(menuService.getDrinkById(id), DrinkViewModel.class);

        DrinkCartViewModel pizzaCartViewModel = new DrinkCartViewModel();
        pizzaCartViewModel.setItem(pizza);
        pizzaCartViewModel.setQuantity(quantity);

        ShoppingCartItems cart = retrieveCart(session);
        addItemToCart(pizzaCartViewModel, cart, "drink");

        return redirect("/menu/order/drink");
    }

    @GetMapping("/details")
    public ModelAndView cartDetails(HttpSession session) {
        ShoppingCartItems cart = retrieveCart(session);
        BigDecimal totalPrice = calcTotal(cart);

        return view("menu/cart/cart-details", "totalPrice", totalPrice);
    }

    @DeleteMapping("/remove-product")
    public ModelAndView removeFromCartConfirm(String id, HttpSession session) {
        removeItemFromCart(id, retrieveCart(session));

        return redirect("/cart/details");
    }

    @PostMapping("/checkout")
    public ModelAndView checkoutConfirm(HttpSession session, String addressId, Principal principal) {
        if (addressId == null) {
            return redirect("/cart/details");
        }

        ShoppingCartItems cart = retrieveCart(session);
        BigDecimal totalPrice = calcTotal(cart);

        OrderCreateServiceModel orderServiceModel = prepareOrder(cart, principal.getName(), addressId, totalPrice);
        orderService.createOrder(orderServiceModel);

        initCart(session, true);

        return redirect("/");
    }
}
