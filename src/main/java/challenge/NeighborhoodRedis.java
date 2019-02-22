package challenge;

import java.util.List;

import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "neighborhood")
public class NeighborhoodRedis {

	private String id;
	private String name;
	private List<RestaurantRedis> restaurants;
	
	public NeighborhoodRedis() {}
	
	public NeighborhoodRedis(String id, String name) {
		this.id = id;
		this.name = name;
	}
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
	public static NeighborhoodRedis fromMongo(NeighborhoodMongo neighborhoodMongo) {
        final NeighborhoodRedis neighborhoodRedis = new NeighborhoodRedis();
        neighborhoodRedis.setId(neighborhoodMongo.getId());
        neighborhoodRedis.setName(neighborhoodMongo.getName());
        return neighborhoodRedis;
    }
}