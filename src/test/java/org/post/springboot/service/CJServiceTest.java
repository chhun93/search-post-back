package org.post.springboot.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.post.springboot.dto.ParcelDetailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CJServiceTest {

    @Autowired
    private CJService service;

    @Test
    public void CSRF토큰_파싱() {
        //given
        String body = "<html lang=\"ko\"><head></head><body>" +
                "<form name=\"frmUnifiedSearch\" id=\"frmUnifiedSearch\" method=\"post\">\n" +
                "\t\t<input type=\"hidden\" title=\"인증키\" name=\"_csrf\" value=\"f34f8a9f-4738-4aeb-92b7-6900d9594ae0\">" +
                "</form>" +
                "</body>" +
                "</html>";

        //when
        String csrf = service.getCSRF(body);

        //then
        assertThat(csrf).isEqualTo("f34f8a9f-4738-4aeb-92b7-6900d9594ae0");
    }

    @Test
    public void CSRF토큰_없는값_파싱() {
        //given
        String body = "<html lang=\"ko\"><head></head><body>" +
                "<form name=\"frmUnifiedSearch\" id=\"frmUnifiedSearch\" method=\"post\">\n" +
                "\t\t<input type=\"hidden\" title=\"인증키\">" +
                "</form>" +
                "</body>" +
                "</html>";

        //when
        String csrf = service.getCSRF(body);

        //then
        assertThat(csrf).isEmpty();
    }

    @Test
    public void 세션_올바른값_파싱() {
        //given
        List<String> cookies = new ArrayList<>();
        cookies.add("cjlogisticsFrontLangCookie=ko; Expires=Tue, 11-Jan-2022 14:48:05 GMT; Path=/");
        cookies.add("JSESSIONID=130F6636D0A5FFD5922E42296FBDA2E1.front21; Path=/; HttpOnly");

        //when
        String session = service.getSession(cookies);

        //then
        assertThat(session).isEqualTo("130F6636D0A5FFD5922E42296FBDA2E1.front21");
    }

    @Test
    public void 세션_없는값_파싱() {
        //given
        List<String> cookies = new ArrayList<>();
        cookies.add("cjlogisticsFrontLangCookie=ko; Expires=Tue, 11-Jan-2022 14:48:05 GMT; Path=/");

        //when
        String session = service.getSession(cookies);

        //then
        assertThat(session).isEmpty();
    }

    @Test
    public void API_호출_성공() {
        //given
        String URL = "https://www.cjlogistics.com/ko/tool/parcel/tracking";
        ResponseEntity<String> responseEntity = new RestTemplate().getForEntity(URL, String.class);
        String csrf = service.getCSRF(responseEntity.getBody());
        String session = service.getSession(responseEntity.getHeaders().get(HttpHeaders.SET_COOKIE));

        //when
        Object result = service.doPost(csrf, "553825406594", session);

        //then
        assertThat(result).isNotNull();
    }

    @Test
    public void JSON_올바른_값_파싱() {
        //given
        String body = "{\"parcelResultMap\":{\"resultList\":[{\"invcNo\":\"553825406594\",\"sendrNm\":\"교**\",\"qty\":\"1\",\"itemNm\":\"도서\",\"rcvrNm\":\"이**\",\"rgmailNo\":\"\",\"oriTrspbillnum\":\"\",\"rtnTrspbillnum\":\"\",\"nsDlvNm\":\"91\"}],\"paramInvcNo\":\"553825406594\"}," +
                "\"parcelDetailResultMap\":{\"resultList\":[{\"nsDlvNm\":\"\",\"crgNm\":\"보내시는 고객님으로부터 상품을 인수받았습니다\",\"crgSt\":\"11\",\"dTime\":\"2021-12-28 11:05:25.0\",\"empImgNm\":\"EMP_IMG_NM\",\"regBranId\":\"8257\",\"regBranNm\":\"[집화]케이비\",\"scanNm\":\"집화처리\"}], \"paramInvcNo\":\"553825406594\"}}";

        //when
        List<ParcelDetailDto> list = service.getProcessList(body);

        //then
        assertThat(list.size()).isEqualTo(1);
        assertThat(list.get(0).getDate().toString()).isEqualTo("2021-12-28");
        assertThat(list.get(0).getTime().format(DateTimeFormatter.ofPattern("HH:mm"))).isEqualTo("11:05");
        assertThat(list.get(0).getPosition()).isEqualTo("[집화]케이비");
        assertThat(list.get(0).getState()).isEqualTo("집화처리");
    }

}
