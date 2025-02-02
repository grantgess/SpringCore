package org.example;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static float rangeMin = -100;
    public static float rangeMax = 100;

    public static void createMeasurements(Map<String, Object> sensor) {
        RestTemplate restTemplate = new RestTemplate();
        String url_post = "http://localhost:8080/measurements/add";
        HttpEntity<Map<String,Object>> requestEntity;
        Map<String,Object> requestMap = new HashMap<>();

        for (int i = 0; i < 100; i++) {
            requestMap.put("sensor", sensor);
            putRandomValues(requestMap);
            requestEntity = new HttpEntity<>(requestMap);
            System.out.println(restTemplate.postForEntity(url_post, requestEntity, String.class).getStatusCode());
            requestMap.clear();
        }
    }

    public static  void putRandomValues( Map<String,Object> request) {
        Random random = new Random();
        float randomValue = rangeMin + (rangeMax - rangeMin) * random.nextFloat();
        request.put("raining", random.nextBoolean());
        request.put("value", randomValue);
    }

   public static Map<String, Object> createSensor(String sensorName) {
        Map<String, Object> sensor = new HashMap<>();
        sensor.put("name", sensorName);
        String url_createSensor = "http://localhost:8080/sensors/registration";
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Map<String,Object>> requestEntity = new HttpEntity<>(sensor);
        restTemplate.postForEntity(url_createSensor, requestEntity, String.class);
        return sensor;
   }

   public static void printValues() {
        RestTemplate restTemplate = new RestTemplate();
        String url_getValue = "http://localhost:8080/measurements";
        MeasurementsResponse[] responses = restTemplate.getForObject(url_getValue, MeasurementsResponse[].class);
        List<MeasurementsResponse> responseList = Arrays.asList(responses);
        responseList.stream().map(MeasurementsResponse::getValue).forEach(System.out::println);
   }

   public static int countOfRainyDays(){
        RestTemplate restTemplate = new RestTemplate();
        String url_getRainyDay = "http://localhost:8080/measurements/rainyDays";
        return restTemplate.getForObject(url_getRainyDay, Integer.class);

   }
    public static void main( String[] args )
    {
//        Map<String, Object> sensor = createSensor("sensor1");
//        createMeasurements(sensor);
          //  printValues();
        System.out.println(countOfRainyDays());

    }
}
