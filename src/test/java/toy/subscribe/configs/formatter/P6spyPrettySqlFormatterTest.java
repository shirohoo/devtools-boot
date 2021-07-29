package toy.subscribe.configs.formatter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class P6spyPrettySqlFormatterTest {
    @Test
    @DisplayName("P6Spy_CustomFormatter")
    public void formatMessage() {
        // given
        int connectionId = 4;
        int elapsed = 3;
        String now = "1620315372646";
        String category = "statement";
        String prepared = "select count(feedboard0_.feed_board_id) as col_0_0_ from feed_board feedboard0_ where feedboard0_.guid=?";
        String url = "";
        String sql = "select count(feedboard0_.feed_board_id) as col_0_0_ from feed_board feedboard0_ where feedboard0_.guid='http://thefarmersfront.github.io/blog/welcome-to-kurly/'";

        // when
        P6spyPrettySqlFormatter p6spyPrettySqlFormatter = new P6spyPrettySqlFormatter();
        String actual = p6spyPrettySqlFormatter.formatMessage(connectionId, now, elapsed, category, prepared, sql, url);

        // then
        assertThat(actual).isEqualTo("Filtered queries");
    }

}
