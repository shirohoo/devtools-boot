package toy.subscribe.configs.manual;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toy.subscribe.board.repository.FeedBoardRepository;

import java.util.Objects;

import static java.util.Arrays.stream;

@Service
@RequiredArgsConstructor
public class ManualService {
    private final FeedBoardRepository feedBoardRepository;

    public void save() {
        ManualExcelReader manualExcelReader = new ManualExcelReader();

        stream(ManualFilePath.values())
                .map(manualExcelReader::read)
                .filter(Objects::nonNull)
                .forEach(feedBoardRepository::saveAll);
    }
}
