package roomescape.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import roomescape.domain.Theme;
import roomescape.dto.ThemeRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ThemeServiceTest {

    @Autowired
    ThemeService themeService;

    @Test
    @DisplayName("테마를 저장할 수 있다.")
    void save() {
        final Theme theme = themeService.save(new ThemeRequest("레벨2 탈출", "우테코 레벨2를 탈출하는 내용입니다.", "https://i.pinimg.com/236x/6e/bc/46/6ebc461a94a49f9ea3b8bbe2204145d4.jpg"));

        assertThat(theme).isNotNull();
    }

    @Test
    @DisplayName("전체 테마를 조회할 수 있다.")
    void findAll() {
        themeService.save(new ThemeRequest("레벨2 탈출", "우테코 레벨2를 탈출하는 내용입니다.", "https://i.pinimg.com/236x/6e/bc/46/6ebc461a94a49f9ea3b8bbe2204145d4.jpg"));

        final List<Theme> theme = themeService.findAll();

        assertThat(theme).hasSize(1);
    }

    @Test
    @DisplayName("테마를 삭제할 수 있다.")
    void delete() {
        themeService.save(new ThemeRequest("레벨2 탈출", "우테코 레벨2를 탈출하는 내용입니다.", "https://i.pinimg.com/236x/6e/bc/46/6ebc461a94a49f9ea3b8bbe2204145d4.jpg"));

        themeService.delete(1L);
        final List<Theme> theme = themeService.findAll();

        assertThat(theme).hasSize(0);
    }

    @Test
    @DisplayName("많이 예약한 순으로 10개를 추출한다.")
    @Sql("/testdata.sql")
    void rankingTest() {
        List<Theme> topRanking = themeService.findTopRanking();
        Assertions.assertAll(
                () -> topRanking.get(0).getName().equals("테마 2"),
                () -> topRanking.get(0).getName().equals("테마 1"),
                () -> topRanking.get(0).getName().equals("테마 3"),
                () -> topRanking.get(0).getName().equals("테마 4"),
                () -> topRanking.get(0).getName().equals("테마 5"),
                () -> topRanking.get(0).getName().equals("테마 6"),
                () -> topRanking.get(0).getName().equals("테마 7"),
                () -> topRanking.get(0).getName().equals("테마 8"),
                () -> topRanking.get(0).getName().equals("테마 9"),
                () -> topRanking.get(0).getName().equals("테마 10")
        );
    }
}
