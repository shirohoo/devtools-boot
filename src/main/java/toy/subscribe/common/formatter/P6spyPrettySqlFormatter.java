package toy.subscribe.common.formatter;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.thymeleaf.util.StringUtils;

import java.util.Locale;

public class P6spyPrettySqlFormatter implements MessageFormattingStrategy {
    
    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        return format(category, sql);
    }
    
    private String format(String category, String sql) {
        if(StringUtils.isEmptyOrWhitespace(sql.trim())) {
            return sql;
        }
        
        if(Category.STATEMENT.getName().equals(category)) {
            String s = sql.trim().toLowerCase(Locale.ROOT);
            if("create".startsWith(s) || "alter".startsWith(s) || "comment".startsWith(s)) {
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
        return "\n=============================================================" + sql.toUpperCase() + "\n=============================================================";
    }
    
}
