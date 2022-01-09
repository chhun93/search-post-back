package org.post.springboot.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

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
}
