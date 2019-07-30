package org.zerofruit.ecommerce;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.zerofruit.ecommerce.domain.generic.money.Money;
import org.zerofruit.ecommerce.service.order.Cart;
import org.zerofruit.ecommerce.service.order.Cart.CartItem;
import org.zerofruit.ecommerce.service.order.Cart.CartOption;
import org.zerofruit.ecommerce.service.order.Cart.CartOptionGroup;
import org.zerofruit.ecommerce.service.order.OrderService;

@SpringBootApplication
public class EcommerceApplication implements CommandLineRunner {

	private static Logger LOG = LoggerFactory.getLogger(EcommerceApplication.class);

	private OrderService orderService;

	public EcommerceApplication(OrderService orderService) {
		this.orderService = orderService;
	}

	public static void main(String[] args) {
		LOG.info("STARTING THE APPLICATION");
		SpringApplication.run(EcommerceApplication.class, args);
		LOG.info("APPLICATION FINISHED");
	}

	@Override
	public void run(String... args) throws Exception {
		Cart cart = new Cart(1L, 1L,
				new CartItem(1L, "Fusion5 ProGlide Power razors", 2,
						new CartOptionGroup("Razor blade",
								new CartOption("4 blade", Money.wons(24000)))));

		orderService.placeOrder(cart);

		orderService.payOrder(1L);

		orderService.shippingOrder(1L);
	}
}
