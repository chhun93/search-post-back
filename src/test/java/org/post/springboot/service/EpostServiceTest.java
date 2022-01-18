package org.post.springboot.service;


import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.post.springboot.dto.ParcelDetailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EpostServiceTest {

    @Autowired
    private EpostService apiService;

    @Test
    public void 올바른_형식_파싱() {
        //given
        String body = "<table id=\"processTable\" class=\"table_col detail_off>\"" +
                "<tbody>\n" +
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
                "\t\t\t\t</tbody>" +
                "</table>";

        //when
        List<ParcelDetailDto> result = apiService.getProcessList(body);

        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getDate().toString()).isEqualTo("2021-08-13");
        assertThat(result.get(0).getTime()).isEqualTo("11:12");
        assertThat(result.get(0).getPosition()).isEqualTo("우정사업정보센터");
        assertThat(result.get(0).getState()).isEqualTo("배달준비");
    }

    @Test
    public void 날짜값_없는_형식_파싱() {
        //given
        String body = "<table id=\"processTable\" class=\"table_col detail_off>\"" +
                "<tbody>\n" +
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
                "\t\t\t\t</tbody>" +
                "</table>";

        //when
        List<ParcelDetailDto> result = apiService.getProcessList(body);

        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getDate()).isNull();
        assertThat(result.get(0).getTime()).isEqualTo("11:12");
        assertThat(result.get(0).getPosition()).isEqualTo("우정사업정보센터");
        assertThat(result.get(0).getState()).isEqualTo("배달준비");
    }

    @Test
    public void 시간값_없는_형식_파싱() {
        //given
        String body = "<table id=\"processTable\" class=\"table_col detail_off>\"" +
                "<tbody>\n" +
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
                "\t\t\t\t</tbody>" +
                "</table>";

        //when
        List<ParcelDetailDto> result = apiService.getProcessList(body);

        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getDate().toString()).isEqualTo("1993-04-11");
        assertThat(result.get(0).getTime()).isNull();
        assertThat(result.get(0).getPosition()).isEqualTo("우정사업정보센터");
        assertThat(result.get(0).getState()).isEqualTo("배달준비");
    }

    @Test
    public void 상태값_없는_형식_파싱() {
        //given
        String body = "<table id=\"processTable\" class=\"table_col detail_off>\"" +
                "<tbody>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "        <tr>\n" +
                "            <td>1993.04.11</td>\n" +
                "            <td>11:12</td>\n" +
                "            <td><a href=\"#\" onclick=\"return goPostDetail(89958, '배달준비', event)\" onkeypress=\"return goPostDetail(89958, '배달준비', event)\" title=\"새창열림\"><span style=\"color:blue\">우정사업정보센터</span></a>\n" +
                "            \t<br>TEL : 061.338.9999\n" +
                "            </td>\n" +
                "            <td></td>\n" +
                "        </tr>\n" +
                "       \t  \n" +
                "\n" +
                "        \t\n" +
                "\t\t\t\t</tbody>" +
                "</table>";

        //when
        List<ParcelDetailDto> result = apiService.getProcessList(body);

        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getDate().toString()).isEqualTo("1993-04-11");
        assertThat(result.get(0).getTime()).isEqualTo("11:12");
        assertThat(result.get(0).getPosition()).isEqualTo("우정사업정보센터");
        assertThat(result.get(0).getState()).isEmpty();
    }

    @Test
    public void 위치_없는_형식_파싱() {
        //given
        String body = "<table id=\"processTable\" class=\"table_col detail_off>\"" +
                "<tbody>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "        <tr>\n" +
                "            <td>1993.04.11</td>\n" +
                "            <td>11:12</td>\n" +
                "            <td></td>\n" +
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
                "\t\t\t\t</tbody>" +
                "</table>";

        //when
        List<ParcelDetailDto> result = apiService.getProcessList(body);

        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getDate().toString()).isEqualTo("1993-04-11");
        assertThat(result.get(0).getTime()).isEqualTo("11:12");
        assertThat(result.get(0).getPosition()).isEmpty();
        assertThat(result.get(0).getState()).isEqualTo("배달준비");
    }

    @Test
    public void processTable_없는_형식_파싱() {
        //given
        String body = "<table id=test class=\"table_col detail_off>\"" +
                "<tbody>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "        <tr>\n" +
                "            <td>1993.04.11</td>\n" +
                "            <td>11:12</td>\n" +
                "            <td></td>\n" +
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
                "\t\t\t\t</tbody>" +
                "</table>";

        //when
        List<ParcelDetailDto> result = apiService.getProcessList(body);

        //then
        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    public void 올바른_인덱스_Element_반환() {
        //given
        Elements list = new Elements();
        list.add(new Element("td"));
        list.add(new Element("td"));

        //when
        Element element = apiService.getElementOfList(list, 0);

        //then
        assertThat(element).isEqualTo(list.get(0));
    }

    @Test
    public void 벗어난_인덱스_Element_Null_반환() {
        //given
        Elements list = new Elements();
        list.add(new Element("td"));
        list.add(new Element("td"));

        //when
        Element element = apiService.getElementOfList(list, 2);

        //then
        assertThat(element).isNull();
    }

    @Test
    public void 올바른_Date_값_파싱() {
        //given
        Elements list = new Elements();
        list.add(new Element("td").appendText("1993.04.11"));

        //when
        LocalDate date = apiService.getParseDate(list);

        //then
        assertThat(date.toString()).isEqualTo("1993-04-11");
    }

    @Test
    public void 틀린_형식_Date_값_파싱() {
        //given
        Elements list = new Elements();
        list.add(new Element("td").appendText("1993-04-11"));

        //when then
        assertThatThrownBy(() -> {
            apiService.getParseDate(list);
        }).isInstanceOf(DateTimeParseException.class);
    }

    @Test
    public void 비어있는_형식_Date_값_파싱() {
        //given
        Elements list = new Elements();
        list.add(new Element("td").appendText(""));

        //when
        LocalDate date = apiService.getParseDate(list);

        //then
        assertThat(date).isNull();
    }

    @Test
    public void 올바른_Time_값_파싱() {
        //given
        Elements list = new Elements();
        list.add(new Element("td").appendText("1993.04.11"));
        list.add(new Element("td").appendText("12:00"));

        //when
        LocalTime time = apiService.getParseTime(list);

        //then
        assertThat(time.toString()).isEqualTo("12:00");
    }

    @Test
    public void 틀린_형식_Time_값_파싱() {
        //given
        Elements list = new Elements();
        list.add(new Element("td").appendText("1993.04.11"));
        list.add(new Element("td").appendText("01:77"));

        //when then
        assertThatThrownBy(() -> {
            apiService.getParseTime(list);
        }).isInstanceOf(DateTimeParseException.class);
    }

    @Test
    public void 비어있는_형식_Time_값_파싱() {
        //given
        Elements list = new Elements();
        list.add(new Element("td").appendText("1993.04.11"));
        list.add(new Element("td").appendText(""));

        //when
        LocalTime time = apiService.getParseTime(list);

        //then
        assertThat(time).isNull();
    }

    @Test
    public void Span태그_존재_올바른_Position_값_파싱() {
        //given
        Elements list = new Elements();
        list.add(new Element("td").appendText("1993.04.11"));
        list.add(new Element("td").appendText("12:00"));
        list.add(new Element("td").appendElement("span").appendText("우체국"));

        //when
        String position = apiService.getParsePosition(list);

        //then
        assertThat(position).isEqualTo("우체국");
    }

    @Test
    public void Span태그_미존재_올바른_Position_값_파싱() {
        //given
        Elements list = new Elements();
        list.add(new Element("td").appendText("1993.04.11"));
        list.add(new Element("td").appendText("12:00"));
        list.add(new Element("td").appendElement("a").appendText("우체국"));

        //when
        String position = apiService.getParsePosition(list);

        //then
        assertThat(position).isEqualTo("우체국");
    }

    @Test
    public void 틀린_형식_Position_값_파싱() {
        //given
        Elements list = new Elements();
        list.add(new Element("td").appendText("1993.04.11"));
        list.add(new Element("td").appendText("12:00"));
        list.add(new Element("td").appendText("우체국"));

        //when
        String position = apiService.getParsePosition(list);

        //then
        assertThat(position).isEmpty();
    }

    @Test
    public void 비어있는_형식_Position_값_파싱() {
        //given
        Elements list = new Elements();
        list.add(new Element("td").appendText("1993.04.11"));
        list.add(new Element("td").appendText("12:00"));
        list.add(new Element("td").appendText(""));

        //when
        String position = apiService.getParsePosition(list);

        //then
        assertThat(position).isEmpty();
    }

    @Test
    public void 공백_존재_올바른_State_값_파싱() {
        //given
        Elements list = new Elements();
        list.add(new Element("td").appendText("1993.04.11"));
        list.add(new Element("td").appendText("12:00"));
        list.add(new Element("td").appendElement("a").appendText("우체국"));
        list.add(new Element("td").appendText("배달준비 "));

        //when
        String state = apiService.getParseState(list);

        //then
        assertThat(state).isEqualTo("배달준비");
    }

    @Test
    public void 공백_미존재_올바른_State_값_파싱() {
        //given
        Elements list = new Elements();
        list.add(new Element("td").appendText("1993.04.11"));
        list.add(new Element("td").appendText("12:00"));
        list.add(new Element("td").appendElement("a").appendText("우체국"));
        list.add(new Element("td").appendText("배달준비"));

        //when
        String state = apiService.getParseState(list);

        //then
        assertThat(state).isEqualTo("배달준비");
    }

    @Test
    public void 비어있는_형식_State_값_파싱() {
        //given
        Elements list = new Elements();
        list.add(new Element("td").appendText("1993.04.11"));
        list.add(new Element("td").appendText("12:00"));
        list.add(new Element("td").appendElement("a").appendText("우체국"));
        list.add(new Element("td").appendText(""));

        //when
        String state = apiService.getParseState(list);

        //then
        assertThat(state).isEmpty();
    }
}
