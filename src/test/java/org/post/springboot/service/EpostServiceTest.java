package org.post.springboot.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EpostServiceTest {

    @Autowired
    private EpostService apiService;

    @Test
    public void 올바른_형식_파싱() {
        //given
        String body = "<tbody>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "        <tr>\n" +
                "            <td>2021.08.13</td>\n" +
                "            <td>11:12</td>\n" +
                "            <td><a href=\"#\" onclick=\"return goPostDetail(89958, '배달준비', event)\" onkeypress=\"return goPostDetail(89958, '배달준비', event)\" title=\"새창열림\"><span style=\"color:blue\">우정사업정보센터</span></a>\n" +
                "            \t<br>TEL : 061.338.9999\n" +
                "            </td>\n" +
                "            <td>\n" +
                "\t\t\t\t배달준비 <a href=\"javascript:fncDetailInfo('1231231231231','20210813',3,'89958','1','0','0') \" title=\"새창열림\"><span style=\"color:blue;\">(집배원 정보 보기)</span></a>\n" +
                "            \n" +
                "            \n" +
                "            \n" +
                "\n" +
                "\t\t\t</td>\n" +
                "        </tr>\n" +
                "       \t  \n" +
                "\n" +
                "        \t\n" +
                "\t\t\t\t</tbody>";

        //when
        List<Map<String, String>> result = apiService.getProcessList(body);

        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).get("date")).isEqualTo("2021.08.13");
        assertThat(result.get(0).get("time")).isEqualTo("11:12");
        assertThat(result.get(0).get("state")).isEqualTo("배달준비");
    }

    @Test
    public void 날짜값_없는_형식_파싱() {
        //given
        String body = "<tbody>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "        <tr>\n" +
                "            <td></td>\n" +
                "            <td>11:12</td>\n" +
                "            <td><a href=\"#\" onclick=\"return goPostDetail(89958, '배달준비', event)\" onkeypress=\"return goPostDetail(89958, '배달준비', event)\" title=\"새창열림\"><span style=\"color:blue\">우정사업정보센터</span></a>\n" +
                "            \t<br>TEL : 061.338.9999\n" +
                "            </td>\n" +
                "            <td>\n" +
                "\t\t\t\t배달준비 <a href=\"javascript:fncDetailInfo('1231231231231','20210813',3,'89958','1','0','0') \" title=\"새창열림\"><span style=\"color:blue;\">(집배원 정보 보기)</span></a>\n" +
                "            \n" +
                "            \n" +
                "            \n" +
                "\n" +
                "\t\t\t</td>\n" +
                "        </tr>\n" +
                "       \t  \n" +
                "\n" +
                "        \t\n" +
                "\t\t\t\t</tbody>";

        //when
        List<Map<String, String>> result = apiService.getProcessList(body);

        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).get("date")).isEmpty();
        assertThat(result.get(0).get("time")).isEqualTo("11:12");
        assertThat(result.get(0).get("state")).isEqualTo("배달준비");
    }

    @Test
    public void 시간값_없는_형식_파싱() {
        //given
        String body = "<tbody>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "        <tr>\n" +
                "            <td>1993.04.11</td>\n" +
                "            <td></td>\n" +
                "            <td><a href=\"#\" onclick=\"return goPostDetail(89958, '배달준비', event)\" onkeypress=\"return goPostDetail(89958, '배달준비', event)\" title=\"새창열림\"><span style=\"color:blue\">우정사업정보센터</span></a>\n" +
                "            \t<br>TEL : 061.338.9999\n" +
                "            </td>\n" +
                "            <td>\n" +
                "\t\t\t\t배달준비 <a href=\"javascript:fncDetailInfo('1231231231231','20210813',3,'89958','1','0','0') \" title=\"새창열림\"><span style=\"color:blue;\">(집배원 정보 보기)</span></a>\n" +
                "            \n" +
                "            \n" +
                "            \n" +
                "\n" +
                "\t\t\t</td>\n" +
                "        </tr>\n" +
                "       \t  \n" +
                "\n" +
                "        \t\n" +
                "\t\t\t\t</tbody>";

        //when
        List<Map<String, String>> result = apiService.getProcessList(body);

        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).get("date")).isEqualTo("1993.04.11");
        assertThat(result.get(0).get("time")).isEmpty();
        assertThat(result.get(0).get("state")).isEqualTo("배달준비");
    }

    @Test
    public void 상태값_없는_형식_파싱() {
        //given
        String body = "<tbody>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "        <tr>\n" +
                "            <td>1993.04.11</td>\n" +
                "            <td>11:12</td>\n" +
                "            <td></td>\n" +
                "            <td></td>\n" +
                "        </tr>\n" +
                "       \t  \n" +
                "\n" +
                "        \t\n" +
                "\t\t\t\t</tbody>";

        //when
        List<Map<String, String>> result = apiService.getProcessList(body);

        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).get("date")).isEqualTo("1993.04.11");
        assertThat(result.get(0).get("time")).isEqualTo("11:12");
        assertThat(result.get(0).get("state")).isEqualTo("</td>");
    }
}
