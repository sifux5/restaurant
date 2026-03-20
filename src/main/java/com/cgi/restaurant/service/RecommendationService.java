package com.cgi.restaurant.service;

import com.cgi.restaurant.model.Table;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final TableService tableService;

    // AI-assisted: scoring algorithm structure and stream sorting logic generated with Claude AI
    public List<Table> recommendTables(
            int partySize,
            LocalDateTime startTime,
            LocalDateTime endTime,
            String zone,
            boolean windowSeat,
            boolean quietCorner,
            boolean accessible,
            boolean nearPlayground) {

        List<Table> availableTables = tableService.getAvailableTables(startTime, endTime, partySize, zone);

        return availableTables.stream()
                .sorted(Comparator.comparingInt(table ->
                        -calculateScore((Table) table, partySize, windowSeat, quietCorner, accessible, nearPlayground)))
                .toList();
    }

    // Scoring logic: rewards exact fit, penalizes empty seats, adds points for matching preferences
    private int calculateScore(Table table, int partySize, boolean windowSeat, boolean quietCorner, boolean accessible, boolean nearPlayground) {
        int score = 0;

        int emptySeats = table.getCapacity() - partySize;
        if (emptySeats == 0) score += 10;
        else if (emptySeats <= 2) score += 5;
        else score -= emptySeats;

        if (windowSeat && table.isWindowSeat()) score += 8;
        if (quietCorner && table.isQuietCorner()) score += 8;
        if (accessible && table.isAccessible()) score += 8;
        if (nearPlayground && table.isNearPlayground()) score += 8;

        return score;
    }
}