package toy.feed.service;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import toy.feed.domain.FeedBoard;
import toy.feed.repository.FeedBoardRepository;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ManualInput {
    
    private final FeedBoardRepository feedBoardRepository;
    
    @Transactional
    public void woowa () throws Exception {
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
        
        feedBoardRepository.saveAll(list);
        
    }
    
}
