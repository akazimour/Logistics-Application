package hu.webuni.spring.logistics.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "delay")
public class DelayConfiguration {

    private int percent5;
    private int percent10;
    private int percent15;
    private  double limit30;
    private  double limit60;
    private  double limit120;

    public int getPercent5() {
        return percent5;
    }

    public void setPercent5(int percent5) {
        this.percent5 = percent5;
    }

    public int getPercent10() {
        return percent10;
    }

    public void setPercent10(int percent10) {
        this.percent10 = percent10;
    }

    public int getPercent15() {
        return percent15;
    }

    public void setPercent15(int percent15) {
        this.percent15 = percent15;
    }

    public double getLimit30() {
        return limit30;
    }

    public void setLimit30(double limit30) {
        this.limit30 = limit30;
    }

    public double getLimit60() {
        return limit60;
    }

    public void setLimit60(double limit60) {
        this.limit60 = limit60;
    }

    public double getLimit120() {
        return limit120;
    }

    public void setLimit120(double limit120) {
        this.limit120 = limit120;
    }
}
