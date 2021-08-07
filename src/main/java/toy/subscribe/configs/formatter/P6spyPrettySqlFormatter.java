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
        for (final String s : EXCLUDE_KEYWORD) {
            if (sql.contains(s)) {
                return "Filtered queries";
            }
        }

        Stack<String> callStack = getStackTrace();

        final StringBuilder callStackBuilder = new StringBuilder();
        int order = 1;
        while (callStack.size() != 0) {
            callStackBuilder.append(NEW_LINE).append("\t\t" + (order++) + ". " + callStack.pop());
        }
        return sqlFormat(sql, category, getMessage(connectionId, elapsed, callStackBuilder));
    }

    private Stack<String> getStackTrace() {
        final Stack<String> callStack = new Stack<>();
        stream(new Throwable().getStackTrace())
                .map(StackTraceElement::toString)
                .filter(trace -> trace.startsWith("toy.subscribe") && !trace.contains("P6spyPrettySqlFormatter"))
                .forEach(callStack::push);
        return callStack;
    }

    private String sqlFormat(String sql, String category, String message) {
        if (Objects.isNull(sql.trim()) || sql.trim().isEmpty()) {
            return "";
        }

        return new StringBuilder().append(NEW_LINE)
                                  .append(getUpperSql(sql, category))
                                  .append(message)
                                  .toString();
    }

    private String getUpperSql(String sql, final String category) {
        if (Category.STATEMENT.getName().equals(category)) {
            final String s = sql.trim().toLowerCase(Locale.ROOT);
            if (s.startsWith("create") || s.startsWith("alter") || s.startsWith("comment")) {
                return sql = FormatStyle.DDL
                        .getFormatter()
                        .format(sql);
            }
            return FormatStyle.BASIC
                    .getFormatter()
                    .format(sql);
        }
        return sql.toUpperCase(Locale.ROOT);
    }

    private String getMessage(final int connectionId, final long elapsed, final StringBuilder callStackBuilder) {
        return new StringBuilder().append(NEW_LINE).append(NEW_LINE).append("\tConnection ID: ").append(connectionId)
                                  .append(NEW_LINE).append("\tExecution Time: ").append(elapsed).append(" ms").append(NEW_LINE)
                                  .append(NEW_LINE).append("\tCall Stack (number 1 is entry point): ").append(callStackBuilder).append(NEW_LINE)
                                  .append(NEW_LINE).append("----------------------------------------------------------------------------------------------------")
                                  .toString();
    }
}
