package alex.weatherinfo;

import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import java.net.URLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.json.JSONObject;
public class HelloController {
    @FXML
    private Text temp;
    @FXML
    private Text feels_like;
    @FXML
    private Text temp_min;
    @FXML
    private Text temp_max;
    @FXML
    private Text pressure;
    @FXML
    private Text humidity;
    @FXML
    private Text wind1;
    @FXML
    private Text wind2;
    @FXML
    private TextField city;
    @FXML
    private Button getData;

    public HelloController() {
    }
    @FXML
    void initialize() {
        getData.setOnAction(event -> {
            String getUserCity = city.getText().trim();
            if(!getUserCity.equals("")) {
                String output = getUrlContent("https://api.openweathermap.org/data/2.5/weather?q=" + getUserCity + "&APPID=0861692433dcb45f87bbff6d50bb1597&units=metric");
                if(!output.isEmpty()) {
                    JSONObject obj = new JSONObject(output);
                    double hPaPressure = obj.getJSONObject("main").getDouble("pressure");
                    double mmHgPressure = hPaPressure/1.333;
                    long round_mmHgPressure = Math.round(mmHgPressure);
                    long roundTemp = Math.round(obj.getJSONObject("main").getDouble("temp"));
                    long round_feels_like = Math.round(obj.getJSONObject("main").getDouble("feels_like"));
                    long round_temp_min = Math.round(obj.getJSONObject("main").getDouble("temp_min"));
                    long round_temp_max = Math.round(obj.getJSONObject("main").getDouble("temp_max"));
                    long round_humidity = Math.round(obj.getJSONObject("main").getDouble("humidity"));
                    long round_speed = Math.round(obj.getJSONObject("wind").getDouble("speed"));
                    long round_gust = Math.round(obj.getJSONObject("wind").getDouble("gust"));

                    temp.setText(""+roundTemp);
                    feels_like.setText(""+round_feels_like);
                    temp_min.setText(""+round_temp_min);
                    temp_max.setText(""+round_temp_max);
                    pressure.setText(""+round_mmHgPressure);
                    humidity.setText(""+round_humidity);
                    wind1.setText(""+round_speed);
                    wind2.setText(""+round_gust);
                }
            }
        });
    }
    private  String getUrlContent(String urlAddress) {
        StringBuilder content = new StringBuilder();
        try {
            URL url = new URL(urlAddress);
            URLConnection urlConn = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) !=null){
                content.append(line);
            }
            bufferedReader.close();
        } catch(Exception e) {
            city.setText("There is no such city!");
        }
        return content.toString();
    }
   }
