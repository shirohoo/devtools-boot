package toy.subscribe.common.manual;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import toy.subscribe.domain.board.model.FeedBoard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * RSS 피드는 최신 글 10개만 제공해주는데,
 * 신규 기술블로그 추가 시 추가하려는 기술 블로그의 기존 글이 너무 많을 경우
 * 10개 이상의 글을 읽을 수 없는 문제가 발생하기 때문에
 * 기존 게시글을 DB에 직접 입력해주기 위한 클래스
 * </pre>
 */
@Slf4j
public class ManualExcelReader {
    
    public List<FeedBoard> read(ManualFilePath company) {
        List<FeedBoard> feedBoards = new ArrayList<>();
        
        File file = new File(company.XLSX);
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(new FileInputStream(file));
        }
        catch(FileNotFoundException e) {
            log.error("File not found!");
            return null;
        }
        catch(IOException e) {
            log.error("XSSFWorkbook IOException!");
            return null;
        }
        
        Sheet sheet = workbook.getSheetAt(0);
        
        for(Row rows : sheet) {
            feedBoards.add(FeedBoard.builder()
                                    .title(rows.getCell(0).getStringCellValue())
                                    .guid(rows.getCell(1).getStringCellValue())
                                    .company(rows.getCell(2).getStringCellValue())
                                    .imgPath(company.IMG)
                                    .build());
        }
        return feedBoards;
    }
    
}
