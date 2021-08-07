package toy.subscribe.configs.manual;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import toy.subscribe.feedboard.model.FeedBoard;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ManualExcelReaderTest {
    @Test
    @DisplayName("크롤링 엑셀데이터를 역직렬화한다")
    public void menualWoowabros() throws Exception {
        // given
        ManualExcelReader manualExcelReader = new ManualExcelReader();

        // when
        List<FeedBoard> feedBoards = manualExcelReader.read(ManualFilePath.WOOWABROS);

        // then
        assertThat(feedBoards.size()).isEqualTo(224);
    }
}
