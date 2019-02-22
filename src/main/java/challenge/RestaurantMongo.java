package challenge;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.redis.core.index.GeoIndexed;

@Document(collection="restaurant")
public class RestaurantMongo {

	@Id
	private String id;
	private String name;
	
	@GeoIndexed
	private GeoJsonPoint location;
	
	public RestaurantMongo(String id, String name, GeoJsonPoint location) {
        this.id = id;
        this.name = name;
        this.location = location;
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
	public GeoJsonPoint getLocation() {
		return location;
	}
	public void setLocation(GeoJsonPoint location) {
		this.location = location;
	}
}