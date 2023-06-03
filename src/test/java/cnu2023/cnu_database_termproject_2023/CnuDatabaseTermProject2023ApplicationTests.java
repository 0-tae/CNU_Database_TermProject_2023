package cnu2023.cnu_database_termproject_2023;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@SpringBootTest
class CnuDatabaseTermProject2023ApplicationTests {

    @Test
    void contextLoads() throws URISyntaxException, IOException, InterruptedException {

        HttpClient httpClient = HttpClient.newHttpClient();

        // 요청 바디 설정
        String requestBody = "AUTH_KEY=21526D14653648DF9DED5FB5558B00B35B776E7F";

        // POST 요청 보내기정
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.cnu.ac.kr/svc/offcam/pub/MobileInfo"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // 응답 처리
        String responseBody = response.body();
        System.out.println(responseBody);

    }
}
