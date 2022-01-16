package io.github.shirohoo.devtools.config;

public interface HttpLogQueryRepository {
    Long findVisitorsOfDay();

    Long findVisitorsOfTotal();
}
