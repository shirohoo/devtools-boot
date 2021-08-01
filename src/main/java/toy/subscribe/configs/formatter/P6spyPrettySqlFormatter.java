package toy.subscribe.configs.formatter;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.hibernate.engine.jdbc.internal.FormatStyle;

import java.util.Locale;
import java.util.Objects;
import java.util.Stack;

import static java.util.Arrays.stream;

public class P6spyPrettySqlFormatter implements MessageFormattingStrategy {
    private static final String[] EXCLUDE_KEYWORD = {"feed_board", "request_log"};
    private static final String NEW_LINE = System.lineSeparator();

    @Override
    public String formatMessage(final int connectionId, final String now, final long elapsed, final String category, final String prepared, final String sql, final String url) {
        for (int i = 0; i < EXCLUDE_KEYWORD.length; i++) {
            if (sql.contains(EXCLUDE_KEYWORD[i])) {
                return "Filtered queries";
            }
        }

        Stack<String> callStack = new Stack<>();
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();

        stream(stackTrace)
                .map(StackTraceElement::toString)
                .filter(trace -> trace.startsWith("toy.subscribe") && !trace.contains("P6spyPrettySqlFormatter"))
                .forEach(callStack::push);

        StringBuilder callStackBuilder = new StringBuilder();
        int order = 1;
        while (callStack.size() != 0) {
            callStackBuilder.append(NEW_LINE).append("\t\t" + (order++) + ". " + callStack.pop());
        }

        String message = new StringBuilder().append(NEW_LINE).append(NEW_LINE).append("\tConnection ID: ").append(connectionId)
                                            .append(NEW_LINE).append("\tExecution Time: ").append(elapsed).append(" ms").append(NEW_LINE)
                                            .append(NEW_LINE).append("\tCall Stack (number 1 is entry point): ").append(callStackBuilder).append(NEW_LINE)
                                            .append(NEW_LINE).append("----------------------------------------------------------------------------------------------------")
                                            .toString();

        return sqlFormat(sql, category, message);
    }

    private String sqlFormat(String sql, String category, String message) {
        if (Objects.isNull(sql.trim()) || sql.trim().isEmpty()) {
            return "";
        }

        if (Category.STATEMENT.getName().equals(category)) {
            String s = sql.trim().toLowerCase(Locale.ROOT);
            if (s.startsWith("create") || s.startsWith("alter") || s.startsWith("comment")) {
                sql = FormatStyle.DDL
                        .getFormatter()
                        .format(sql);
            }
            else {
                sql = FormatStyle.BASIC
                        .getFormatter()
                        .format(sql);
            }
        }

        return new StringBuilder().append(NEW_LINE)
                                  .append(sql.toUpperCase())
                                  .append(message)
                                  .toString();
    }
}
