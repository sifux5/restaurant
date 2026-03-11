package com.cgi.restaurant;

import com.cgi.restaurant.model.Table;
import com.cgi.restaurant.service.RecommendationService;
import com.cgi.restaurant.service.TableService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecommendationServiceTest {

    @Mock
    private TableService tableService;

    @InjectMocks
    private RecommendationService recommendationService;

    @Test
    void parimSoovitusOnAknakohtLaud() {
        Table aknaLaud = new Table();
        aknaLaud.setId(1L);
        aknaLaud.setName("A1");
        aknaLaud.setCapacity(4);
        aknaLaud.setWindowSeat(true);
        aknaLaud.setQuietCorner(false);
        aknaLaud.setAccessible(false);
        aknaLaud.setNearPlayground(false);
        aknaLaud.setZone("INDOOR");

        Table tavalinenLaud = new Table();
        tavalinenLaud.setId(2L);
        tavalinenLaud.setName("A2");
        tavalinenLaud.setCapacity(4);
        tavalinenLaud.setWindowSeat(false);
        tavalinenLaud.setQuietCorner(false);
        tavalinenLaud.setAccessible(false);
        tavalinenLaud.setNearPlayground(false);
        tavalinenLaud.setZone("INDOOR");

        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusHours(2);

        when(tableService.getAvailableTables(start, end, 2, null))
                .thenReturn(List.of(aknaLaud, tavalinenLaud));

        List<Table> soovitused = recommendationService.recommendTables(
                2, start, end, null, true, false, false, false);

        assertEquals("A1", soovitused.get(0).getName());
    }

    @Test
    void tyhiNimekiriKuiPoleVabadLaudu() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusHours(2);

        when(tableService.getAvailableTables(start, end, 2, null))
                .thenReturn(List.of());

        List<Table> soovitused = recommendationService.recommendTables(
                2, start, end, null, false, false, false, false);

        assertTrue(soovitused.isEmpty());
    }
}