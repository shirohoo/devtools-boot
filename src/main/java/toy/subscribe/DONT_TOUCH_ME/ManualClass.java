package toy.subscribe.DONT_TOUCH_ME;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import toy.subscribe.domain.entity.FeedBoard;
import toy.subscribe.repository.FeedBoardRepository;

/**
 * <pre>
 * RSS 피드는 최신 글 10개만 제공해주는데,
 * 신규 기술블로그 추가 시 추가하려는 기술 블로그의 기존 글이 너무 많을 경우
 * 10개 이상의 글을 읽을 수 없는 문제가 발생하기 때문에
 * 기존 게시글을 DB에 직접 입력해주기 위한 클래스
 * </pre>
 */
@Component
@RequiredArgsConstructor
public class ManualClass {
    
    private final FeedBoardRepository feedBoardRepository;
    
    @Transactional
    public void menualWoowabros() throws Exception {
        List<FeedBoard> list = new ArrayList<>();
        
        File file = new File("src/test/resources/woowa_tech.xlsx");
        Workbook workbook = new XSSFWorkbook(new FileInputStream(file));
        Sheet sheet = workbook.getSheetAt(0);
        
        for(Row rows : sheet) {
            list.add(FeedBoard.builder()
                              .title(rows.getCell(0).getStringCellValue())
                              .guid(rows.getCell(1).getStringCellValue())
                              .company(rows.getCell(2).getStringCellValue())
                              .imgPath("/images/woowabros.png")
                              .build());
        }
        feedBoardRepository.saveAll(list);
    }
    
    @Transactional
    public void menualToss() throws Exception {
        List<FeedBoard> list = new ArrayList<>();
        
        File file = new File("src/test/resources/toss_tech.xlsx");
        Workbook workbook = new XSSFWorkbook(new FileInputStream(file));
        Sheet sheet = workbook.getSheetAt(0);
        
        for(Row rows : sheet) {
            list.add(FeedBoard.builder()
                              .title(rows.getCell(0).getStringCellValue())
                              .guid(rows.getCell(1).getStringCellValue())
                              .company(rows.getCell(2).getStringCellValue())
                              .imgPath("/images/toss.png")
                              .build());
        }
        feedBoardRepository.saveAll(list);
    }
    
}
