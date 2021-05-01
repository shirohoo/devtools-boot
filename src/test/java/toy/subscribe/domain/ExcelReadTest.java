package toy.subscribe.domain;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import toy.subscribe.domain.board.FeedBoard;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ExcelReadTest {
    
    @Test
    @DisplayName("우아한형제들_엑셀_읽기")
    public void read() throws Exception {
        // given
        List<FeedBoard> list = new ArrayList<>();
        
        File file = new File("src/test/resources/woowa_tech.xlsx");
        Workbook workbook = new XSSFWorkbook(new FileInputStream(file));
        Sheet sheet = workbook.getSheetAt(0);
        
        // when
        for (Row rows : sheet) {
            list.add(FeedBoard.builder()
                              .title(rows.getCell(0).getStringCellValue())
                              .guid(rows.getCell(1).getStringCellValue())
                              .company(rows.getCell(2).getStringCellValue())
                              .imgPath("/images/woowabros.png")
                              .build());
        }
        
        // then
        assertThat(list.size()).isEqualTo(224);
    }
    
}
