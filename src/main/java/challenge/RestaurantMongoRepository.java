package challenge;

import java.util.List;

import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RestaurantMongoRepository extends MongoRepository<RestaurantMongo, String>{

	List<RestaurantMongo> findAllByLocationWithin(GeoJsonPolygon geoJsonPolygon);
	
}
