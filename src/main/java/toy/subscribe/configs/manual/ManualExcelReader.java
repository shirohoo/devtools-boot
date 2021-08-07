package toy.subscribe.configs.manual;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import toy.subscribe.feedboard.model.FeedBoard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private static final int FIRST_EXCEL_SHEET = 0;

    public List<FeedBoard> read(ManualFilePath company) {
        Workbook workbook = getWorkbook(new File(company.getXLSX()));

        if (Objects.isNull(workbook)) {
            return null;
        }

        List<FeedBoard> feedBoards = new ArrayList<>();
        for (Row rows : workbook.getSheetAt(FIRST_EXCEL_SHEET)) {
            feedBoards.add(FeedBoard.builder()
                                    .title(rows.getCell(0).getStringCellValue())
                                    .guid(rows.getCell(1).getStringCellValue())
                                    .company(rows.getCell(2).getStringCellValue())
                                    .imgPath(company.getIMG())
                                    .build());
        }
        return feedBoards;
    }

    private Workbook getWorkbook(File file) {
        Workbook workbook;
        try {
            workbook = new XSSFWorkbook(new FileInputStream(file));
        }
        catch (FileNotFoundException e) {
            log.warn("failed to create XSSWorkbook because file not found. please check ManualFilePath.");
            return null;
        }
        catch (IOException e) {
            log.warn("IOException occureed for XSSFWorkbook.");
            return null;
        }
        return workbook;
    }
}
