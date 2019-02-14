package challenge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestaurantController {

	@Autowired
	private RestaurantService restauranteService;

	@GetMapping("/restaurants/findInNeighborhood")
	public NeighborhoodRedis findInNeighborhood(@RequestParam double x, @RequestParam double y) {
		return restauranteService.findInNeighborhood(x, y);
	}
}