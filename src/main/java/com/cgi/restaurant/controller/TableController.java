package com.cgi.restaurant.controller;

import com.cgi.restaurant.model.Table;
import com.cgi.restaurant.service.RecommendationService;
import com.cgi.restaurant.service.TableMergeService;
import com.cgi.restaurant.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tables")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class TableController {

    private final TableService tableService;
    private final RecommendationService recommendationService;
    private final TableMergeService tableMergeService;

    @GetMapping
    public List<Table> getAllTables() {
        return tableService.getAllTables();
    }

    @GetMapping("/available")
    public List<Table> getAvailableTables(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
            @RequestParam int partySize,
            @RequestParam(required = false) String zone) {
        return tableService.getAvailableTables(startTime, endTime, partySize, zone);
    }

    @GetMapping("/recommend")
    public List<Table> recommendTables(
            @RequestParam int partySize,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
            @RequestParam(required = false) String zone,
            @RequestParam(defaultValue = "false") boolean windowSeat,
            @RequestParam(defaultValue = "false") boolean quietCorner,
            @RequestParam(defaultValue = "false") boolean accessible,
            @RequestParam(defaultValue = "false") boolean nearPlayground) {
        return recommendationService.recommendTables(partySize, startTime, endTime, zone, windowSeat, quietCorner, accessible, nearPlayground);
    }

    @GetMapping("/merge")
    public ResponseEntity<?> findMergeableTables(
            @RequestParam int partySize,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        List<List<Table>> mergeableTables = tableMergeService.findMergeableTables(partySize, startTime, endTime);
        if (mergeableTables.isEmpty()) {
            return ResponseEntity.ok(List.of());
        }
        return ResponseEntity.ok(mergeableTables);
    }
}