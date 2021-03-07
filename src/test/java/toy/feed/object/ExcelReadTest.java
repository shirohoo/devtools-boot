package toy.feed.object;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import toy.feed.domain.FeedBoard;
import toy.feed.repository.FeedBoardRepository;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ExcelReadTest {
    
    @Autowired FeedBoardRepository feedBoardRepository;
    
    @Test
    @DisplayName ("기술블로그_엑셀읽기")
    public void read () throws Exception {
        // given
        List<FeedBoard> list = new ArrayList<>();
        
        File file = new File("src/test/resources/woowa.xlsx");
        Workbook workbook = new XSSFWorkbook(new FileInputStream(file));
        Sheet sheet = workbook.getSheetAt(0);
        
        for (Row rows : sheet) {
            list.add(FeedBoard.builder()
                              .title(rows.getCell(0).getStringCellValue())
                              .guid(rows.getCell(1).getStringCellValue())
                              .company(rows.getCell(2).getStringCellValue())
                              .imgPath("/images/woowabros.png")
                              .build());
        }
        
        // when
        List<FeedBoard> feedBoards = feedBoardRepository.saveAll(list);
        
        // then
        assertThat(feedBoards.size()).isEqualTo(224);
    }
    
}
