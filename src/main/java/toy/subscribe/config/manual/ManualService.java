package toy.subscribe.config.manual;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toy.subscribe.blog.repository.FeedBoardRepository;

import java.util.Objects;

import static java.util.Arrays.stream;

@Service
@RequiredArgsConstructor
public class ManualService {
    private final FeedBoardRepository feedBoardRepository;

    public void save() {
        final ManualExcelReader manualExcelReader = new ManualExcelReader();
        stream(ManualFilePath.values())
                .map(manualExcelReader::read)
                .filter(Objects::nonNull)
                .forEach(feedBoardRepository::saveAll);
    }
}
