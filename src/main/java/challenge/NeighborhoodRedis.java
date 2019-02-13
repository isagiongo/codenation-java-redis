package challenge;

import java.util.List;

public class NeighborhoodRedis {

	private String id;
	private String name;
	private List<RestaurantRedis> restaurants;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<RestaurantRedis> getRestaurants() {
		return restaurants;
	}
	public void setRestaurants(List<RestaurantRedis> restaurants) {
		this.restaurants = restaurants;
	}
}