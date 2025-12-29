package com.wanghui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class CalculatorApplication {

    @Autowired
    private CalculationRecordRepository calculationRecordRepository;

    public static void main(String[] args) {
        SpringApplication.run(CalculatorApplication.class, args);
    }

    @PostMapping("/api/add")
    public Result addNumbers(@RequestBody Numbers numbers) {
        double result = numbers.getNumber1() + numbers.getNumber2();
        saveCalculationRecord(numbers.getNumber1(), numbers.getNumber2(), result);
        return new Result(result);
    }

    private void saveCalculationRecord(double number1, double number2, double result) {
        CalculationRecord record = new CalculationRecord();
        record.setNumber1(number1);
        record.setNumber2(number2);
        record.setResult(result);
        calculationRecordRepository.save(record);
    }

    public static class Numbers {
        private double number1;
        private double number2;

        public double getNumber1() {
            return number1;
        }

        public void setNumber1(double number1) {
            this.number1 = number1;
        }

        public double getNumber2() {
            return number2;
        }

        public void setNumber2(double number2) {
            this.number2 = number2;
        }
    }

    public static class Result {
        private double result;

        public Result(double result) {
            this.result = result;
        }

        public double getResult() {
            return result;
        }

        public void setResult(double result) {
            this.result = result;
        }
    }
}
