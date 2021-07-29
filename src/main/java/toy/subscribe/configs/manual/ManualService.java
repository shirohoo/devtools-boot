package toy.subscribe.configs.manual;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import toy.subscribe.domain.board.model.FeedBoard;
import toy.subscribe.domain.board.repository.FeedBoardRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManualService {
    private final FeedBoardRepository feedBoardRepository;

    public void save() {
        ManualExcelReader manualExcelReader = new ManualExcelReader();

        for(ManualFilePath company : ManualFilePath.values()) {
            List<FeedBoard> feedBoards = manualExcelReader.read(company);
            if(feedBoards != null) {
                feedBoardRepository.saveAll(feedBoards);
            }
        }
    }
}
