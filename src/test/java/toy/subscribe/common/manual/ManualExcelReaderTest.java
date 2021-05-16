package toy.subscribe.common.manual;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import toy.subscribe.domain.board.model.FeedBoard;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ManualExcelReaderTest {
    
    @Test
    @DisplayName("크롤링_엑셀데이터를_역직렬화한다")
    public void menualWoowabros() throws Exception {
        // given
        ManualExcelReader manualExcelReader = new ManualExcelReader();
        
        // then
        for(ManualFilePath company : ManualFilePath.values()) {
            List<FeedBoard> feedBoards = manualExcelReader.readFeedBoardsFromXLSX(company);
            
            switch(company) {
                case WOOWABROS:
                    assertThat(feedBoards.size()).isEqualTo(224);
                    break;
                
                case TOSS:
                    assertThat(feedBoards.size()).isEqualTo(68);
                    break;
            }
            
        }
    }
    
}