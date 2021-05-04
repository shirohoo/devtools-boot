package toy.subscribe.common.manual;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import toy.subscribe.domain.board.model.FeedBoard;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ExcelReaderTest {
    
    @Test
    @DisplayName("크롤링_엑셀데이터를_역직렬화한다")
    public void menualWoowabros() throws Exception {
        // given
        ExcelReader excelReader = new ExcelReader();
        
        // then
        for(FilePath company : FilePath.values()) {
            List<FeedBoard> feedBoards = excelReader.readFeedBoardsFromXLSX(company);
            
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