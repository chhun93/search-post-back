package org.post.springboot.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
}
