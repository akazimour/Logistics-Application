package hu.webuni.spring.logistics.web;

import hu.webuni.spring.logistics.model.Milestone;
import hu.webuni.spring.logistics.repository.MilestoneRepository;
import hu.webuni.spring.logistics.repository.SectionPropertiesRepository;
import hu.webuni.spring.logistics.repository.SectionRepository;
import hu.webuni.spring.logistics.repository.TransportPlanRepository;
import hu.webuni.spring.logistics.service.InitDbService;
import hu.webuni.spring.logistics.service.LogisticsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureTestDatabase
//Megjegyzés: a futtatáshoz a LogisticsApplication main run metódusban ki kell kommentelni ezt -> initDbService.insertAddress();
//spring.jpa.hibernate.ddl-auto=create - módban

public class LogisticsControllerIT {

    private static final String BASE_URI = "/api/addresses/api/transportPlans/1/delay";
    @Autowired
    WebTestClient webTestClient;
    @Autowired
    TransportPlanRepository transportPlanRepository;
    @Autowired
    SectionRepository sectionRepository;
    @Autowired
    LogisticsService logisticsService;
    @Autowired
    MilestoneRepository milestoneRepository;
    @Autowired
    SectionPropertiesRepository sectionPropertiesRepository;
    @Autowired
    InitDbService initDbService;

@Transactional
    private void postMilestone(Long id,Milestone milestone) {
        webTestClient
                .post()
                .uri(BASE_URI)
                .bodyValue(milestone)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void testThatAddedDelayIsAdded() throws Exception {

        initDbService.insertAddress();

        Long id = 1L;
        int ProfitInitial = logisticsService.getFinalProfit(1L);
        assertThat(ProfitInitial).isEqualTo(1000000);

        LocalDateTime localDateTime1 = logisticsService.saveBeforeState();

        Milestone postedDto = new Milestone(id, LocalDateTime.of(2022, 12, 06, 10, 50, 00), null, null);
        postMilestone(postedDto.getId(), postedDto);

        LocalDateTime localDateTime2 = logisticsService.saveAfterState();

        assertThat(localDateTime1).isNotEqualTo(localDateTime2);
        assertThat(localDateTime2).isEqualTo(postedDto.getPlannedTime());

    }

        @Test
        void testThatProfitReducedByFivePercent() throws Exception {

            int finalProfit = logisticsService.getFinalProfit(1L);
            assertThat(finalProfit).isEqualTo(950000);

        }

    @Test
    void testThatProfitReducedByTenPercent() throws Exception {
        initDbService.insertAddress();

        Long id = 1L;
        Milestone postedDto = new Milestone(id, LocalDateTime.of(2022, 12, 06, 11, 45, 00), null, null);
        postMilestone(postedDto.getId(), postedDto);

        int finalProfit = logisticsService.getFinalProfit(1L);
        assertThat(finalProfit).isEqualTo(900000);

    }

    @Test
    void testThatProfitReducedByFifteenPercent() throws Exception {
        initDbService.insertAddress();

        Long id = 1L;
        Milestone postedDto = new Milestone(id, LocalDateTime.of(2022, 12, 06, 12, 45, 00), null, null);
        postMilestone(postedDto.getId(), postedDto);

        int finalProfit = logisticsService.getFinalProfit(1L);
        assertThat(finalProfit).isEqualTo(850000);

    }

            }








