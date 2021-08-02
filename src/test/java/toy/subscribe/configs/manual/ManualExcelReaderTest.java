package toy.subscribe.configs.manual;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import toy.subscribe.board.model.FeedBoard;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ManualExcelReaderTest {
    @Test
    @DisplayName("크롤링_엑셀데이터를_역직렬화한다")
    public void menualWoowabros() throws Exception {
        // given
        ManualExcelReader manualExcelReader = new ManualExcelReader();

        // when
        List<FeedBoard> feedBoards = manualExcelReader.read(ManualFilePath.WOOWABROS);

        // then
        assertThat(feedBoards.size()).isEqualTo(224);
    }
}
