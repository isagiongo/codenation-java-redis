package challenge;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RestaurantServiceImpl implements RestaurantService {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private RedisTemplate<String, NeighborhoodRedis> redisTemplate;
	
	//Busca o bairro atraves dos pontos x e y
	//Busca se está salvo no redis esse bairro
	//se não está salvo, busca todos os restaurantes do bairro, no mongoDB
	@Override
	public NeighborhoodRedis findInNeighborhood(double x, double y) {
		NeighborhoodMongo neighborhoodMongo = findNeighborhood(x, y);
		String key = "neighborhood:" + neighborhoodMongo.getId();
        NeighborhoodRedis neighborhoodRedis = redisTemplate.opsForValue().get(key);
        if (neighborhoodRedis == null) {
            neighborhoodRedis = NeighborhoodRedis.fromMongo(neighborhoodMongo);
            neighborhoodRedis.setRestaurants(findAllInNeighborhood(neighborhoodMongo)
                            .stream().sequential()
                            .map(this::restaurantMongoToRedis)
                            .collect(Collectors.toList()));
            redisTemplate.opsForValue().set(key, neighborhoodRedis);
        }
        return neighborhoodRedis;
    }

	//seta as infos do restaurante no redis com as infos que estao no mongoDB
    private RestaurantRedis restaurantMongoToRedis(RestaurantMongo restaurantMongo) {
        RestaurantRedis restaurantRedis = new RestaurantRedis();
        restaurantRedis.setId(restaurantMongo.getId());
        restaurantRedis.setName(restaurantMongo.getName());
        restaurantRedis.setX(restaurantMongo.getLocation().getX());
        restaurantRedis.setY(restaurantMongo.getLocation().getY());
        return restaurantRedis;
    }
    
    //busca o bairro, passando as coordenadas - pontos x e y - Latitude e Longitude
	private NeighborhoodMongo findNeighborhood(double x, double y) {
		GeoJsonPoint point = new GeoJsonPoint(new Point(x, y));
		Query query = new Query().addCriteria(Criteria.where("geometry").intersects(point));
		return mongoTemplate.findOne(query, NeighborhoodMongo.class);
    }
	
	//busca todos os restaurantes, dentro de uma área (polígono - classe GeoJsonPolygon - mongoDB)
	//Para ser um polígono, o primeiro e o último par de coordenadas tem que ser iguais
	//Ordena por nome, alfabeticamente, ascendente
    private List<RestaurantMongo> findAllInNeighborhood(NeighborhoodMongo neighborhoodMongo) {
    	Query query = new Query().addCriteria(Criteria.where("location")
    			.within(neighborhoodMongo.getGeometry()))
    			.with(Sort.by("name").ascending());
    	return mongoTemplate.find(query, RestaurantMongo.class);
    }
}