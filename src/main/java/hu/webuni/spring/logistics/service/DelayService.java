package hu.webuni.spring.logistics.service;

import hu.webuni.spring.logistics.configuration.DelayConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
@EnableConfigurationProperties(DelayConfiguration.class)
public class DelayService {

    @Autowired
    DelayConfiguration delayConfiguration;

    public int getFinalProfit(int currentProfit, long minutes){
        double currentProfit1 = currentProfit;
        int percent;
        if (minutes >= delayConfiguration.getLimit30() && minutes < delayConfiguration.getLimit60()){
            percent = delayConfiguration.getPercent5();
        } else if (minutes >= delayConfiguration.getLimit60() && minutes < delayConfiguration.getLimit120()) {
            percent = delayConfiguration.getPercent10();
        }else
            percent = delayConfiguration.getPercent15();
        double i = (percent / 100.0) * currentProfit1;
        double v = currentProfit - i;
        return ((int) v);

    }
}
